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

import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

@SuppressWarnings("javadoc")
public class CustomerCommand extends CliCommand
{
    // CliBase declares the following shorts. Best not to override them here.
    // 'h',"help"
    // 'c', "config-path"
    // 'v', "version"
    // 'l', "log-path"

    public CustomerCommand(CliBase cliBase, String commandName)
    {
        super(cliBase, commandName);
    }

    public CustomerCommand(CliBase cliBase, CliCommand previousCommand, String commandName)
    {
        super(cliBase, previousCommand, commandName);
        previousCommand = null;
    }

    @Override
    protected void cliSetup()
    {
        cliBase.cliReservedSetup(options, cliBase.isLogToFile(), cliBase.isConfigSwitch());
    }

    @Override
    protected void customInit()
    {
    }

    public enum Command
    {
        Add("add"), Delete("delete"), Update("update"), List("list"), Sync("sync");

        Command(String command)
        {
            this.command = command;
        }

        public static Command getCommand(String arg)
        {
            if(arg.equals(Add.command))
                return Add;
            if(arg.equals(Update.command))
                return Update;
            if(arg.equals(Delete.command))
                return Delete;
            if(arg.equals(List.command))
                return List;
            return null;
        }
        public String command;
    }
}