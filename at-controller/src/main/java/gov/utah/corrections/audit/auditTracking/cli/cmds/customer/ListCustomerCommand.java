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
public class ListCustomerCommand extends CustomerCommand
{
    public static final String RepoShortCl = "r";
    public static final String RepoLongCl = "repository";
    public static final String AccessGroupShortCl = "g";
    public static final String AccessGroupLongCl = "access-group";
    public static final String PublishedShortCl = "p";
    public static final String PublishedLongCl = "published";
    public static final String SubmitterShortCl = "s";
    public static final String SubmitterLongCl = "submitter";

    private volatile ListCmdData listCmdData;

    public ListCustomerCommand(CliBase cliBase, CliCommand previousCommand, String commandName)
    {
        super(cliBase, previousCommand, commandName);
    }

    public ListCmdData getListCmdData()
    {
        return listCmdData;
    }

    @Override
    protected void cliSetup()
    {
        super.cliSetup();
        options.addOption(
            Option.builder(RepoShortCl)
                .desc("Repository type. i.e. <opendof || allseen>.  Defaults to all.  Optional.")
                .longOpt(RepoLongCl)
                .hasArg()
                .build());

        options.addOption(
            Option.builder(AccessGroupShortCl)
                .desc("The access group where <arg> is the access group name to use. Defaults to all.  Optional.")
                .longOpt(AccessGroupLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(PublishedShortCl)
                .desc("The published flag where <arg> is <true | false>. Defaults to both.  Optional.")
                .longOpt(PublishedLongCl)
                .hasArg()
                .build());
        options.addOption(
            Option.builder(SubmitterShortCl)
                .desc("The submitter where <arg> is the submitter's email to use. Defaults to all.  Optional.")
                .longOpt(SubmitterLongCl)
                .hasArg()
                .build());
    }

    @Override
    protected void customInit()
    {
        super.customInit();
        String repository = null;
        String accessGroup = null;
        String submitterEmail = null;
        Boolean published = null;

        TabToLevel format = cliBase.getInitFormat();
        if(commandline.hasOption(RepoShortCl))
        {
            repository = commandline.getOptionValue(RepoShortCl);
            format.ttl("--", RepoLongCl, " = ", repository);
        }

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
//
        listCmdData = new ListCmdData(repository, accessGroup, submitterEmail, published);
        String msg = listCmdData.validate(commandline);
        if(msg != null)
            help(CliBase.InvalidCommand, msg);
    }

    public static class ListCmdData
    {
        public final String repository;
        public final String accessGroup;
        public final String submitterEmail;
        public final Boolean published;

        public ListCmdData(String repository, String accessGroup, String submitterEmail, Boolean published)
        {
            this.repository = repository;
            this.accessGroup = accessGroup;
            this.submitterEmail = submitterEmail;
            this.published = published;
        }

        @SuppressWarnings("unused")
        public String validate(CommandLine commandline)
        {
            return null;
        }
    }
}