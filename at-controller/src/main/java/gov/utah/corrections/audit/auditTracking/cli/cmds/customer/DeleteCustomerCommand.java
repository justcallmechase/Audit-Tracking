/*
**  Copyright (c) 2016, Chase Adams.
**
**  This program is free software: you can redistribute it and/or modify
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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import com.ccc.tools.TabToLevel;
import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

@SuppressWarnings("javadoc")
public class DeleteCustomerCommand extends CustomerCommand
{
    public static final String RepoShortCl = "r";
    public static final String RepoLongCl = "repository";
    public static final String InterfaceShortCl = "i";
    public static final String InterfaceLongCl = "interface-id";
    public static final String VersionShortCl = "n";
    public static final String VersionLongCl = "version";

    public static final String ForceShortCl = "f";
    public static final String ForceLongCl = "force";

    private volatile DeleteCmdData deleteCmdData;

    public DeleteCustomerCommand(CliBase cliBase, CliCommand previousCommand, String commandName)
    {
        super(cliBase, previousCommand, commandName);
    }


    public DeleteCmdData getDeleteCmdData()
    {
        return deleteCmdData;
    }


    @Override
    protected void cliSetup()
    {
        super.cliSetup();
        //@formatter:off
        options.addOption(
            Option.builder(RepoShortCl)
                .desc("Repository type. i.e. <opendof || allseen>.  Typically can be determined by the interface ID and is optional in these cases.  Optional.")
                .longOpt(RepoLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(InterfaceShortCl)
            .desc("An interface or interfaces where <arg> is a space separated list of the Interface IDs.  Required.")
            .longOpt(InterfaceLongCl)
            .hasArgs()
            .required()
            .valueSeparator(' ')
            .build());
        options.addOption(
            Option.builder(VersionShortCl)
                .desc("The version of the given interfaces.  If multiple interfaces are specified this version applies to all of them.  Required.")
                .longOpt(VersionLongCl)
                .required()
                .hasArg()
                .build());

        options.addOption(
            Option.builder(ForceShortCl)
                .desc("Force flag, there are no arguments. Required to delete a published interface, otherwise Optional.")
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
        String version = "1";
        boolean force = false;

        TabToLevel format = cliBase.getInitFormat();
        if(commandline.hasOption(RepoShortCl))
        {
            repository = commandline.getOptionValue(RepoShortCl);
            format.ttl("--", RepoLongCl, " = ", repository);
        }
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
//        if(commandline.hasOption(ForceShortCl))
//        {
//            force = true;
//            StrH.ttl(initsb, 1, "--", ForceLongCl, " = ", force);
//        }
//
        deleteCmdData = new DeleteCmdData(repository, iids, version, force);
        String msg = deleteCmdData.validate(commandline);
        if(msg != null)
            help(CliBase.InvalidCommand, msg);
    }

    public class DeleteCmdData
    {
        public final String repository;
        public final String[] iids;
        public final String version;
        public final boolean force;

        public DeleteCmdData(String repository, String[] iids, String version, boolean force)
        {
            this.repository = repository;
            this.iids = iids;
            this.version = version;
            this.force = force;
        }

        public String validate(CommandLine commandline)
        {
            if(!commandline.hasOption(InterfaceShortCl))
                return "--" + InterfaceLongCl + " is required";
            if(!commandline.hasOption(VersionShortCl))
                return "--" + VersionLongCl + " is required";
            return null;
        }
    }
}