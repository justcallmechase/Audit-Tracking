package gov.utah.corrections.audit.auditTracking.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.AddCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.DeleteCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.ListCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.UpdateCustomerCommand;

@SuppressWarnings("javadoc")
public class ManageController
{
    protected final Logger log;
//    protected final CoreController controller;

    public ManageController(Object controller)
    {
//        this.controller = controller;
    	log = LoggerFactory.getLogger(getClass());
    }


    public void customerAdd(AddCustomerCommand.AddCmdData data) throws Exception
    {
//        DynamicData dynamicData = getDynamicData(data.repository, data.iids, data.xmlFile, true);
//        InterfaceData iface = new InterfaceData(null, dynamicData.iid, dynamicData.xml, data.version, data.submitterData, data.accessGroupData, dynamicData.repository, null, null, data.published);
//        if(dynamicData.iid != null)
//            log.info("adding interface ID: " + dynamicData.iid + " version: " + data.version);
//        iface = controller.addInterface(CoreController.CliUser, dynamicData.repository, iface, dynamicData.rdn);
//        if(dynamicData.iid == null)
//            log.info("Allocated interface ID: " + iface.iid + " v" + iface.version);
    }

    public void customerDelete(DeleteCustomerCommand.DeleteCmdData data) throws Exception
    {
//        for(int i=0; i < data.iids.length; i++)
//        {
//            DynamicData dynamicData = getDynamicData(data.repository, data.iids, null);
//            log.info("deleting interface ID: " + dynamicData.iid + " version: " + data.version);
//            AuthenticatedUser user = CoreController.CliUser;
//            if(data.force)
//                user = new AuthenticatedUser(CoreController.CliUser.name, CoreController.CliUser.email, CoreController.CliUser.description, new ForceRights());
//            controller.deleteInterface(user, dynamicData.repository, dynamicData.iid, data.version);
//        }
    }

    public void customerList(ListCustomerCommand.ListCmdData data) throws Exception
    {
//        List<InterfaceData> list = controller.getAllInterfaces(CoreController.CliUser, data.repository, data.submitterEmail, data.accessGroup, data.published);
//        StringBuilder sb = new StringBuilder();
//        for(InterfaceData idata : list)
//            sb.append(idata.toString());
//        sb.append("   Total interfaces: " + list.size());
//        log.info("\n"+sb.toString());
    }

    public void customerUpdate(UpdateCustomerCommand.UpdateCmdData data) throws Exception
    {
//        DynamicData dynamicData = getDynamicData(data.repository, data.iids, data.xmlFile);
//        log.info("updating interface ID: " + dynamicData.iid + " version: " + data.version);
//        SubmitterData submitterData = null;
//        SubmitterData accessGroupData = null;
//
//        if(data.submitter != null)
//            submitterData = controller.getSubmitterData(data.submitter);
//        if(data.accessGroup != null)
//            accessGroupData = controller.getGroupData(data.accessGroup);
//
//        AuthenticatedUser user = CoreController.CliUser;
//        if(data.force)
//            user = new AuthenticatedUser(CoreController.CliUser.name, CoreController.CliUser.email, CoreController.CliUser.description, new ForceRights());
//        controller.updateInterface(user, dynamicData.repository, dynamicData.iid, dynamicData.xml, data.version, submitterData, accessGroupData, data.published);
    }
}
