package gov.utah.corrections.audit.auditTracking.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.opendof.core.oal.DOFInterfaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.tools.TabToLevel;

import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.AddIfaceCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.AddIfaceCommand.AddCmdData;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.DeleteIfaceCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.ListIfaceCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.UpdateIfaceCommand;


@SuppressWarnings("javadoc")
public class ManageController
{
    protected final Logger log;
    protected final CoreController controller;

    public ManageController(CoreController controller)
    {
        this.controller = controller;
    	log = LoggerFactory.getLogger(getClass());
    }

    public static String fileToString(File file) throws Exception
    {
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            InputStream istream = new FileInputStream(file);
            Reader reader = new InputStreamReader(istream, "UTF-8");
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                sb.append(line).append("\n");
        }
        finally
        {
            if(bufferedReader != null)
                try{bufferedReader.close();} catch (IOException e)
                {
                    LoggerFactory.getLogger(ManageController.class).warn("failed to close bufferedReader cleanly", e);
                }
        }
        return sb.toString();
    }

    private DynamicData getDynamicData(String repository, String[] iids, File xmlFile) throws Exception
    {
        return getDynamicData(repository, iids, xmlFile, false);
    }

    private DynamicData getDynamicData(String repository, String[] iids, File xmlFile, boolean noiidOk) throws Exception
    {
        // first was iid given?
        String iid = null;
        if(iids != null && iids.length > 0)
            iid = iids[0];
        if(iids != null && iids.length > 1)
            throw new Exception("Mixup of v1.0 and v2.0 iid's given");
        if(iid != null)
            iid = OpendofController.dofUriToStandardForm(iid);

        // was xmlFile given if so obtain contents?
        String xml = null;
        if(xmlFile != null)
            xml = fileToString(xmlFile);

        if(iid == null && xml == null && !noiidOk)
            throw new Exception("either the iid must be specified or an xml file provided to obtain it from");

        String supportingRepo = null;
        // do we need to obtain the iid from the xml, if so validate/obtain repository as well
        if(iid == null && xml != null)
        {
            List<String> iidList = controller.getIidFromXmlForRepositories(xml);
            if(iidList.size() == 0)
                throw new Exception("Given XML file did not contain a known Interface ID");
            iid = iidList.get(0);
            HashMap<String, String> unique = new HashMap<String, String>();
            for(String nextIid : iidList)
            {
                List<String> repoTypes = controller.getSupportingRepositories(nextIid);
                for(String repo : repoTypes)
                {
                    unique.put(repo, repo);
                    supportingRepo = repo;
                }
            }
            if(unique.size() > 1)
            {
                StringBuilder sb = new StringBuilder("Given Interface definition is supported by multiple repositories:");
                for(Entry<String,String> repo : unique.entrySet())
                    sb.append(" " + repo);
                throw new Exception(sb.toString());
            }
        }

        if(supportingRepo == null && repository == null)
        {
            List<String> repoTypes = controller.getSupportingRepositories(iid);
            supportingRepo = repoTypes.get(0);
            if(repoTypes.size() == 0)
                throw new Exception("Given Interface ID is not known by existing repositories: " + iid);
            if(repoTypes.size() > 1)
            {
                StringBuilder sb = new StringBuilder("Given Interface ID: " + iid + " is known by multiple repositories:");
                for(String repo : repoTypes)
                    sb.append(" " + repo);
                throw new Exception("Given Interface ID is known by multiple repositories: " + iid);
            }
        }
        if(repository == null)
            repository = supportingRepo;
        if(repository == null)
            throw new Exception("failed to obtain the repository from the interface ID");

        // now calculate the rdn
        String rdn = null;
        if(repository.equals(CoreController.OpenDofRepo) && iid != null)
        {
            DOFInterfaceID diid = DOFInterfaceID.create(iid);
            long id = diid.getIdentifier();
            int byteSize = 4;
            if(id < 256)
                byteSize = 1;
            else if (id < 65536)
                byteSize = 2;
            rdn = diid.getRegistry() + "/" + byteSize;
        }
        return new DynamicData(repository, iid, xml, rdn);
    }

    public void ifaceSync(AuthenticatedUser user) throws Exception
    {
        RepoType repoType = controller.getRepoType(CoreController.OpenDofRepo);
        ((OpendofController)repoType.controller).synch(user);
    }

    public void ifaceAdd(AddIfaceCommand.AddCmdData data) throws Exception
    {
        if(data.isLegacy())
        {
            addUpdate(data, false);
            return;
        }
        DynamicData dynamicData = getDynamicData(data.repository, data.iids, data.xmlFile, true);
        InterfaceData iface = new InterfaceData(null, dynamicData.iid, dynamicData.xml, data.version, data.submitterData, data.accessGroupData, dynamicData.repository, null, null, data.published);
        if(dynamicData.iid != null)
            log.info("adding interface ID: " + dynamicData.iid + " version: " + data.version);
        iface = controller.addInterface(CoreController.CliUser, dynamicData.repository, iface, dynamicData.rdn);
        if(dynamicData.iid == null)
            log.info("Allocated interface ID: " + iface.iid + " v" + iface.version);
    }

    public void ifaceDelete(DeleteIfaceCommand.DeleteCmdData data) throws Exception
    {
        for(int i=0; i < data.iids.length; i++)
        {
            DynamicData dynamicData = getDynamicData(data.repository, data.iids, null);
            log.info("deleting interface ID: " + dynamicData.iid + " version: " + data.version);
            AuthenticatedUser user = CoreController.CliUser;
            if(data.force)
                user = new AuthenticatedUser(CoreController.CliUser.name, CoreController.CliUser.email, CoreController.CliUser.description, new ForceRights());
            controller.deleteInterface(user, dynamicData.repository, dynamicData.iid, data.version);
        }
    }

    public void ifaceList(ListIfaceCommand.ListCmdData data) throws Exception
    {
        List<InterfaceData> list = controller.getAllInterfaces(CoreController.CliUser, data.repository, data.submitterEmail, data.accessGroup, data.published);
        StringBuilder sb = new StringBuilder();
        for(InterfaceData idata : list)
            sb.append(idata.toString());
        sb.append("   Total interfaces: " + list.size());
        log.info("\n"+sb.toString());
    }

    public void ifaceUpdate(UpdateIfaceCommand.UpdateCmdData data) throws Exception
    {
        DynamicData dynamicData = getDynamicData(data.repository, data.iids, data.xmlFile);
        log.info("updating interface ID: " + dynamicData.iid + " version: " + data.version);
        SubmitterData submitterData = null;
        SubmitterData accessGroupData = null;

        if(data.submitter != null)
            submitterData = controller.getSubmitterData(data.submitter);
        if(data.accessGroup != null)
            accessGroupData = controller.getGroupData(data.accessGroup);

        AuthenticatedUser user = CoreController.CliUser;
        if(data.force)
            user = new AuthenticatedUser(CoreController.CliUser.name, CoreController.CliUser.email, CoreController.CliUser.description, new ForceRights());
        controller.updateInterface(user, dynamicData.repository, dynamicData.iid, dynamicData.xml, data.version, submitterData, accessGroupData, data.published);
    }

    public void addUpdate(AddCmdData data, boolean update) throws Exception
    {
        for(int i=0; i < data.files.length; i++)
        {
            File[] files = new File[1];
            files[0] = data.files[i];
            if(files[0].isDirectory())
                files = files[0].listFiles();
            for(int j=0; j < files.length; j++)
            {
                File currentfile = files[j];
                DynamicData dynamicData = getDynamicData(data.repository, data.iids, currentfile);
                String version = data.version;
                if(version == null)
                    version = "1";
                if(!update)
                {
                    InterfaceData iface = new InterfaceData(null, dynamicData.iid, dynamicData.xml, version, data.submitterData, data.accessGroupData, dynamicData.repository, null, null, data.published);
                    log.info("adding interface from file: " + currentfile.getAbsolutePath());
                    controller.addInterface(CoreController.CliUser, dynamicData.repository, iface, dynamicData.rdn);
                    continue;
                }
                log.info("updating interface from file: " + currentfile.getAbsolutePath());
                controller.updateInterface(CoreController.CliUser, dynamicData.repository, dynamicData.iid, dynamicData.xml, version, null, null, data.published);
            }
        }
    }

    public void submitterAdd(AddSubmitterCommand.AddCmdData data) throws Exception
    {
        controller.addSubmitter(CoreController.CliUser, data.getSubmitterData());
    }

    public void submitterDelete(DeleteSubmitterCommand.DeleteCmdData data) throws Exception
    {
        controller.deleteSubmitter(CoreController.CliUser, data.email);
    }

    public void submitterList() throws Exception
    {
        List<SubmitterData> list = controller.listSubmitters(CoreController.CliUser);
        TabToLevel format = new TabToLevel();
        for(SubmitterData data : list)
        {
            data.toString(format);
            format.ttl("");
        }
        log.info(format.toString());
    }

    public void submitterUpdate(UpdateSubmitterCommand.UpdateCmdData data) throws Exception
    {
        controller.updateSubmitter(CoreController.CliUser, data.email, data.getSubmitterData());
    }


    public void groupAdd(AddGroupCommand.AddCmdData data) throws Exception
    {
        if(data.manager != null)
            controller.addGroup(CoreController.CliUser, data.manager, data.groupData);
        else
            controller.addSubmitterToGroup(CoreController.CliUser, data.member, data.group);
    }

    public void groupDelete(DeleteGroupCommand.DeleteCmdData data) throws Exception
    {
        controller.deleteGroup(CoreController.CliUser, data.group, data.member, data.force);
    }

    public void groupList(ListGroupCommand.ListCmdData data) throws Exception
    {
        TabToLevel format = new TabToLevel();
        if(data.group == null)
        {
            format.ttl("Existing Groups:");
            format.level.incrementAndGet();
            List<GroupData> groups = controller.listGroups(CoreController.CliUser);
            for(GroupData group : groups)
                group.toString(format).toString();
            log.info(format.toString());
            return;
        }
        format.ttl("Members of group Group: ", data.group);
        format.level.incrementAndGet();
        List<SubmitterData> subs = controller.listMembers(CoreController.CliUser, data.group);
        for(SubmitterData sub: subs)
            sub.toString(format);
        log.info(format.toString());
    }

    public void groupUpdate(UpdateGroupCommand.UpdateCmdData data) throws Exception
    {
        controller.updateGroup(CoreController.CliUser, data.group, data.admin, data.description, data.date);
    }

    public void subrepoAdd(AddSubRepoCommand.AddCmdData data) throws Exception
    {
        controller.addSubRepositoryNode(CoreController.CliUser, data.rdn, data.child);
    }

    public void subrepoDelete(DeleteSubRepoCommand.DeleteCmdData data) throws Exception
    {
        controller.deleteSubRepositoryNode(CoreController.CliUser, data.parentRdn, data.subrepo);
    }

    public void subrepoList(ListSubRepoCommand.ListCmdData data) throws Exception
    {
        SubRepositoryNode node = controller.getSubRepositoryNode(data.repoType, data.rdn);
        TabToLevel format = new TabToLevel();
        format.ttl("\nSubRepository branch: " + data.fdn);
        format.level.incrementAndGet();
        node.toString(format);
        log.info(format.toString());
    }

    public void subrepoUpdate(UpdateSubRepoCommand.UpdateCmdData data) throws Exception
    {
        controller.updateSubRepositoryNode(CoreController.CliUser, data.rdn, data.subrepo);
    }

    private class DynamicData
    {
        public final String repository;
        public final String iid;
        public final String xml;
        public final String rdn;

        private DynamicData(String repository, String iid, String xml, String rdn)
        {
            this.repository = repository;
            this.iid = iid;
            this.xml = xml;
            this.rdn = rdn;
        }
    }

    private class ForceRights implements UserRights
    {
        private ForceRights()
        {
        }

        @Override
        public TabToLevel toString(TabToLevel format)
        {
            format.ttl("cli force");
            return format;
        }
    }
}
