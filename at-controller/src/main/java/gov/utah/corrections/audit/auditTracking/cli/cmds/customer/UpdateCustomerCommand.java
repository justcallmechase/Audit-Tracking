/*
**  Copyright (c) 2016, Chase Adams.
**
**  Boaha is free software: you can redistribute it and/or modify
**  it under the terms of the GNU General Public License as
**  published by the Free Software Foundation, either version 3 of the
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 license along
**  with this program.  If not, see http://www.gnu.org/licenses
*/
package gov.utah.corrections.audit.auditTracking.cli.cmds.customer;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import com.ccc.tools.TabToLevel;
import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

@SuppressWarnings("javadoc")
public class UpdateCustomerCommand extends CustomerCommand
{
    // c, h, l, v used

    public static final String RepoShortCl = "r";
    public static final String RepoLongCl = "repository";
    public static final String InterfaceShortCl = "i";
    public static final String InterfaceLongCl = "interface-id";
    public static final String VersionShortCl = "n";
    public static final String VersionLongCl = "version";

    public static final String AccessGroupShortCl = "g";
    public static final String AccessGroupLongCl = "access-group";
    public static final String PublishedShortCl = "p";
    public static final String PublishedLongCl = "published";
    public static final String SubmitterShortCl = "s";
    public static final String SubmitterLongCl = "submitter";
    public static final String XmlFileShortCl = "x";
    public static final String XmlFileLongCl = "xml-file";
    public static final String ForceShortCl = "f";
    public static final String ForceLongCl = "force";

    private volatile UpdateCmdData updateCmdData;

    public UpdateCustomerCommand(CliBase cliBase, CliCommand previousCommand, String commandName)
    {
        super(cliBase, previousCommand, commandName);
    }

    public UpdateCmdData getUpdateCmdData()
    {
        return updateCmdData;
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
        options.addOption(
            Option.builder(InterfaceShortCl)
            .desc("The interface ID to update.  Required.")
            .longOpt(InterfaceLongCl)
            .hasArgs()
            .required()
            .valueSeparator(' ')
            .build());
        options.addOption(
            Option.builder(VersionShortCl)
                .desc("The version of the interface to be updated. Required.")
                .longOpt(VersionLongCl)
                .hasArg()
                .required()
                .build());

        options.addOption(
            Option.builder(AccessGroupShortCl)
                .desc("The access group where <arg> is the group name. If not given the existing value will not be modified.  If specified the group must already exist in the db.  Optional.")
                .longOpt(AccessGroupLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(PublishedShortCl)
                .desc("The published flag where <arg> is <true | false>. If not given the existing value will not be modified.  The --" + ForceLongCl + " is required to modify this field.  Optional.")
                .longOpt(PublishedLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(SubmitterShortCl)
                .desc("The submitter where <arg> is the submitter's email to use. If not given the existing value will not be modified.  If given the submitter must already exist in the db.  Optional.")
                .longOpt(SubmitterLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(XmlFileShortCl)
                .desc("The xml file where <arg> is a single file.  If not given the existing xml field value will not be modified.  Optional.")
                .longOpt(XmlFileLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(ForceShortCl)
                .desc("Force flag, there are no arguments. Required to modify the published flag, otherwise Optional.")
                .longOpt(ForceLongCl)
                .build());
        //@formatter:on
    }

    @Override
    protected void customInit()
    {
        super.customInit();
        String repository = null;
        String[] iids = null;
        String version = null;
        String submitterEmail = null;
        String accessGroup = null;
        Boolean published = null;
        File xmlFile = null;
        boolean force = false;

        TabToLevel format = cliBase.getInitFormat();
        if(commandline.hasOption(RepoShortCl))
        {
            repository = commandline.getOptionValue(RepoShortCl);
            format.ttl("--", RepoLongCl, " = ", repository);
        }
//        StringBuilder initsb = cliBase.getInitsb();
//        if(commandline.hasOption(RepoShortCl))
//        {
//            repository = commandline.getOptionValue(RepoShortCl);
//            StrH.ttl(initsb, 1, "--", RepoLongCl, " = ", repository);
//        }
//        if(commandline.hasOption(InterfaceLongCl))
//        {
//            iids = commandline.getOptionValues(InterfaceShortCl);
//            StrH.ttl(initsb, 1, "--", InterfaceLongCl, " = ", commandline.getOptionValues(InterfaceShortCl));
//        }
//        if(commandline.hasOption(VersionShortCl))
//        {
//            version = commandline.getOptionValue(VersionShortCl);
//            StrH.ttl(initsb, 1, "--", VersionLongCl, " = ", version);
//        }
//
//
//        if(commandline.hasOption(AccessGroupShortCl))
//        {
//            accessGroup = commandline.getOptionValue(AccessGroupShortCl);
//            StrH.ttl(initsb, 1, "--", AccessGroupLongCl, " = ", accessGroup);
//        }
//        if(commandline.hasOption(PublishedShortCl))
//        {
//            published = Boolean.parseBoolean(commandline.getOptionValue(PublishedShortCl));
//            StrH.ttl(initsb, 1, "--", PublishedLongCl, " = ", published);
//        }
//        if(commandline.hasOption(SubmitterShortCl))
//        {
//            submitterEmail = commandline.getOptionValue(SubmitterShortCl);
//            StrH.ttl(initsb, 1, "--", SubmitterLongCl, " = ", submitterEmail);
//        }
//        if(commandline.hasOption(XmlFileShortCl))
//        {
//            String path = commandline.getOptionValue(XmlFileShortCl);
//            xmlFile = new File(path);
//            StrH.ttl(initsb, 1, "--", XmlFileLongCl, " = ", commandline.getOptionValue(XmlFileShortCl));
//        }
//        if(commandline.hasOption(ForceShortCl))
//        {
//            force = true;
//            StrH.ttl(initsb, 1, "--", ForceLongCl, " = ", force);
//        }

        updateCmdData = new UpdateCmdData(repository, iids, version, submitterEmail, accessGroup, published, xmlFile, force);
        String msg = updateCmdData.validate(commandline);
        if(msg != null)
            help(CliBase.InvalidCommand, msg);
    }

    public class UpdateCmdData
    {
        public final String repository;
        public final String[] iids;
        public final String version;
        public final Boolean published;
        public final File xmlFile;
        public final String submitter;
        public final String accessGroup;
        public final boolean force;

        //@formatter:off
        public UpdateCmdData(
                        String repository, String[] iids, String version,
                        String submitterEmail, String accessGroup, Boolean published, File xmlFile,
                        boolean force)
        //@formatter:on
        {
            this.repository = repository;
            this.iids = iids;
            this.version = version;

            this.published = published;
            this.xmlFile = xmlFile;
            submitter = submitterEmail;
            this.accessGroup = accessGroup;
            this.force = force;
        }

        public String validate(CommandLine commandline)
        {
            if(commandline.hasOption(PublishedShortCl) && !commandline.hasOption(ForceShortCl))
                return "The --" + ForceLongCl + " must be specified when modifying the published flag";

            if(!commandline.hasOption(XmlFileShortCl) && !commandline.hasOption(InterfaceShortCl))
                return XmlFileLongCl + " and/or " + InterfaceShortCl + " must be given";

            if(!commandline.hasOption(AccessGroupShortCl) && !commandline.hasOption(PublishedShortCl) && !commandline.hasOption(SubmitterShortCl) && !commandline.hasOption(XmlFileShortCl))
                return "You have not specified anything to be modified";
            return null;
        }
    }
}