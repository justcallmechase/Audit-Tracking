package gov.utah.corrections.audit.auditTracking.cli.cmds.customer;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import com.ccc.tools.TabToLevel;
import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

@SuppressWarnings("javadoc")
public class AddCustomerCommand extends CustomerCommand
{
    // c, h, l, v used

    public static final String RepoShortCl = "r";
    public static final String RepoLongCl = "repository";
//    public static final String InterfaceShortCl = "i";
//    public static final String InterfaceLongCl = "interface-id";
//    public static final String VersionShortCl = "n";
//    public static final String VersionLongCl = "version";
//
//    public static final String AccessGroupShortCl = "g";
//    public static final String AccessGroupLongCl = "access-group";
//    public static final String PublishedShortCl = "p";
//    public static final String PublishedLongCl = "published";
//    public static final String SubmitterShortCl = "s";
//    public static final String SubmitterLongCl = "submitter";
//    public static final String XmlFileShortCl = "x";
//    public static final String XmlFileLongCl = "xml-file";
//
//    public static final String V1FilesShortCl = "f";
//    public static final String V1FilesLongCl = "xml-files";

    private volatile AddCmdData addCmdData;

    public AddCustomerCommand(CliBase cliBase, CliCommand previousCommand, String commandName)
    {
        super(cliBase, previousCommand, commandName);
    }

    public AddCmdData getAddCmdData()
    {
        return addCmdData;
    }

    @Override
    protected void cliSetup()
    {
        super.cliSetup();
        //@formatter:off
        options.addOption(
            Option.builder(RepoShortCl)
                .desc("Repository type. i.e. <opendof || allseen>.  Typically can be determined by the iid and is optional in these cases.  Optional.")
                .longOpt(RepoLongCl)
                .hasArg()
                .build());
//        options.addOption(
//            Option.builder(InterfaceShortCl)
//            .desc("The interface ID.  The --" + XmlFileLongCl + " and --" + V1FilesLongCl + " can also be used to provide the interface ID.   If none of these are selected a normal working allocation is done.  --" + RepoLongCl + " is required for normal working allocation.  Optional.")
//            .longOpt(InterfaceLongCl)
//            .hasArgs()
//            .valueSeparator(' ')
//            .build());
//        options.addOption(
//            Option.builder(VersionShortCl)
//                .desc("The version of the given interfaces. Defaults to 1 if not given. Optional.")
//                .longOpt(VersionLongCl)
//                .hasArg()
//                .build());
//
//        options.addOption(
//            Option.builder(AccessGroupShortCl)
//                .desc("The access group where <arg> is the group name. If not given it defaults to annonymous (public).  If specified the group must already exist in the db.  Optional.")
//                .longOpt(AccessGroupLongCl)
//                .hasArg()
//                .build());
//        options.addOption(
//            Option.builder(PublishedShortCl)
//                .desc("The published flag where <arg> is <true | false>. Defaults to false if not given.  Optional.")
//                .longOpt(PublishedLongCl)
//                .hasArg()
//                .build());
//        options.addOption(
//            Option.builder(SubmitterShortCl)
//                .desc("The submitter where <arg> is the submitter's email to use. If not given admin@opendof.org will be used. If given the submitter must already exist in the db.  Optional.")
//                .longOpt(SubmitterLongCl)
//                .hasArg()
//                .build());
//        options.addOption(
//            Option.builder(XmlFileShortCl)
//                .desc("The xml file where <arg> is a single file.  Can be used to obtain the iid but must match --" + InterfaceLongCl + " if it is also given.  Optional.")
//                .longOpt(XmlFileLongCl)
//                .hasArg()
//                .build());
//
//        options.addOption(
//            Option.builder(V1FilesShortCl)
//                .desc("Add interfaces where <arg> is a space separated list of file/dir paths. This is version 1.0 functionality.  Cannot be used in combination with --" + InterfaceLongCl + " or --" + XmlFileLongCl + ".  Optional")
//                .longOpt(V1FilesLongCl)
//                .hasArgs()
//                .valueSeparator(' ')
//                .build());
        //@formatter:on
    }

    @Override
    protected void customInit()
    {
        super.customInit();

        String repository = null;
        String[] iids = null;
        String version = "1";

//        String submitterEmail = DataAccessor.AdminEmail;
//        String accessGroup = DataAccessor.AnonymousGroupName;
        Boolean published = false;
        File xmlFile = null;

        File[] files = null;

        TabToLevel format = cliBase.getInitFormat();
        if(commandline.hasOption(RepoShortCl))
        {
            repository = commandline.getOptionValue(RepoShortCl);
            format.ttl("--", RepoLongCl, " = ", repository);
        }

        addCmdData = new AddCmdData(repository, iids, version, null, null, published, xmlFile, files);
        String msg = addCmdData.validate(commandline);
        if(msg != null)
            help(CliBase.InvalidCommand, msg);
    }

    public class AddCmdData
    {
        public final String repository;
        public final String[] iids;
        public final String version;

//        public final String accessGroup;
//        public final String submitterEmail;
        public final Boolean published;
        public final File xmlFile;

        public final File[] files;
//        public final SubmitterData submitterData;
//        public final SubmitterData accessGroupData;

        //@formatter:off
        public AddCmdData(
                        String repository, String[] iids, String version,
                        String submitterEmail, String accessGroup, Boolean published, File xmlFile,
                        File[] files)
        //@formatter:on
        {
            this.repository = repository;
            this.iids = iids;
            this.version = version;

            this.published = published;
            this.xmlFile = xmlFile;

            this.files = files;
//            if(submitterEmail != null)
//                submitterData = new SubmitterData(null, submitterEmail, null);
//            else
//                submitterData = DataAccessor.OpendofAdmin;
//            if(accessGroup != null)
//                accessGroupData = new SubmitterData(accessGroup, accessGroup, null);
//            else
//                accessGroupData = DataAccessor.AnonymousGroup;
        }

        public boolean isLegacy()
        {
            return files != null;
        }

        public String validate(CommandLine commandline)
        {
//            if(commandline.hasOption(XmlFileShortCl) && commandline.hasOption(V1FilesShortCl))
//                return "Either V1.0 style adds or V1.1 style adds can be specified, not both (--" + V1FilesLongCl + " given)";
//
//            if(!commandline.hasOption(V1FilesShortCl) && !commandline.hasOption(XmlFileShortCl) && !commandline.hasOption(InterfaceShortCl))
//                return "--" + XmlFileLongCl + " and/or --" + InterfaceLongCl + " or --" + V1FilesLongCl + " must be given";

            return null;
        }
    }
}