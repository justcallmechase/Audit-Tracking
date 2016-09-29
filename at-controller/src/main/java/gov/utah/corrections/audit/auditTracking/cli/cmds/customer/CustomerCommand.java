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