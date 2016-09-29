package gov.utah.corrections.audit.auditTracking.cli;

import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

@SuppressWarnings("javadoc")
public class AuditTrackerManage extends CliBase
{
    private final static String AlternateExecutableName = "irmanage";

//    protected volatile CoreController coreController;
    protected volatile CliCommand command;

    public AuditTrackerManage(String[] args)
    {
        this(args, true, false, false);
    }

    public AuditTrackerManage(String[] args, boolean config, boolean logToFile, boolean skipValidate)
    {
        super(args, CoreController.IrServletConfigKey, config, logToFile, DefaultMaxHelpWidth);
        setExecutableName(AlternateExecutableName);

        IfaceCommand ifaceCmd = new IfaceCommand(this, Command.Interface.command);
        addCommand(ifaceCmd);
        ifaceCmd.addChild(new AddIfaceCommand(this, ifaceCmd, IfaceCommand.Command.Add.command));
        ifaceCmd.addChild(new DeleteIfaceCommand(this, ifaceCmd, IfaceCommand.Command.Delete.command));
        ifaceCmd.addChild(new UpdateIfaceCommand(this, ifaceCmd, IfaceCommand.Command.Update.command));
        ifaceCmd.addChild(new ListIfaceCommand(this, ifaceCmd, IfaceCommand.Command.List.command));
        ifaceCmd.addChild(new SyncIfaceCommand(this, ifaceCmd, IfaceCommand.Command.Sync.command));

        SubmitterCommand submitterCmd = new SubmitterCommand(this, Command.Submitter.command);
        addCommand(submitterCmd);
        submitterCmd.addChild(new AddSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Add.command));
        submitterCmd.addChild(new DeleteSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Delete.command));
        submitterCmd.addChild(new ListSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.List.command));
        submitterCmd.addChild(new UpdateSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Update.command));

        GroupCommand groupCmd = new GroupCommand(this, Command.Group.command);
        addCommand(groupCmd);
        groupCmd.addChild(new AddGroupCommand(this, groupCmd, GroupCommand.Command.Add.command));
        groupCmd.addChild(new DeleteGroupCommand(this, groupCmd, GroupCommand.Command.Delete.command));
        groupCmd.addChild(new ListGroupCommand(this, groupCmd, GroupCommand.Command.List.command));
        groupCmd.addChild(new UpdateGroupCommand(this, groupCmd, GroupCommand.Command.Update.command));

        SubRepoCommand subrepoCmd = new SubRepoCommand(this, Command.SubRepo.command);
        addCommand(subrepoCmd);
        subrepoCmd.addChild(new AddSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Add.command));
        subrepoCmd.addChild(new DeleteSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Delete.command));
        subrepoCmd.addChild(new ListSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.List.command));
        subrepoCmd.addChild(new UpdateSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Update.command));

        if(!skipValidate)
            command = validateCommands();
    }

    @Override
    public void run()
    {
        try
        {
            coreController = new CoreController();
            coreController.init(properties);
            ManageController manage = new ManageController(coreController);
            String cmdName = command.getCommandName();
            if(command instanceof IfaceCommand)
            {
                if(cmdName.equals(IfaceCommand.Command.Add.command))
                    manage.ifaceAdd(((AddIfaceCommand)command).getAddCmdData());
                if(cmdName.equals(IfaceCommand.Command.Delete.command))
                    manage.ifaceDelete(((DeleteIfaceCommand)command).getDeleteCmdData());
                if(cmdName.equals(IfaceCommand.Command.List.command))
                    manage.ifaceList(((ListIfaceCommand)command).getListCmdData());
                if(cmdName.equals(IfaceCommand.Command.Update.command))
                    manage.ifaceUpdate(((UpdateIfaceCommand)command).getUpdateCmdData());
                if(cmdName.equals(IfaceCommand.Command.Sync.command))
                    manage.ifaceSync(new AuthenticatedUser(DataAccessor.CliAdminName, DataAccessor.CliAdminEmail, null, null));
            }else
            if(command instanceof SubmitterCommand)
            {
                if(cmdName.equals(SubmitterCommand.Command.Add.command))
                    manage.submitterAdd(((AddSubmitterCommand)command).getAddCmdData());
                if(cmdName.equals(SubmitterCommand.Command.Delete.command))
                    manage.submitterDelete(((DeleteSubmitterCommand)command).getDeleteCmdData());
                if(cmdName.equals(SubmitterCommand.Command.List.command))
                    manage.submitterList();
                if(cmdName.equals(SubmitterCommand.Command.Update.command))
                    manage.submitterUpdate(((UpdateSubmitterCommand)command).getUpdateCmdData());
            }else
            if(command instanceof GroupCommand)
            {
                if(cmdName.equals(GroupCommand.Command.Add.command))
                    manage.groupAdd(((AddGroupCommand)command).getAddCmdData());
                if(cmdName.equals(GroupCommand.Command.Delete.command))
                    manage.groupDelete(((DeleteGroupCommand)command).getDeleteCmdData());
                if(cmdName.equals(GroupCommand.Command.List.command))
                    manage.groupList(((ListGroupCommand)command).getListCmdData());
                if(cmdName.equals(GroupCommand.Command.Update.command))
                    manage.groupUpdate(((UpdateGroupCommand)command).getUpdateCmdData());
            }else
            if(command instanceof SubRepoCommand)
            {
                if(cmdName.equals(SubRepoCommand.Command.Add.command))
                    manage.subrepoAdd(((AddSubRepoCommand)command).getAddCmdData());
                if(cmdName.equals(SubRepoCommand.Command.Delete.command))
                    manage.subrepoDelete(((DeleteSubRepoCommand)command).getDeleteCmdData());
                if(cmdName.equals(SubRepoCommand.Command.List.command))
                    manage.subrepoList(((ListSubRepoCommand)command).getListCmdData());
                if(cmdName.equals(SubRepoCommand.Command.Update.command))
                    manage.subrepoUpdate(((UpdateSubRepoCommand)command).getUpdateCmdData());
            }else
                throw new Exception("Unexpected command: " + command.getClass().getName());
            log.debug(getClass().getSimpleName() + " exiting OK");
            close(0);
        } catch (Throwable t)
        {
            if (coreController != null)
                coreController.destroy();
            log.error("failed: ", t);
            close(ApplicationError);
        }
    }

    @Override
    public void close(int ccode)
    {
        if (coreController != null)
            coreController.destroy();
        super.close(ccode);
    }

    public enum Command
    {
        Interface("interface"),
        Submitter("submitter"),
        Group("group"),
        SubRepo("subrepo");

        Command(String command)
        {
            this.command = command;
        }

        public static Command getCommand(String arg)
        {
            if(arg.equals(Interface.command))
                return Interface;
            if(arg.equals(Group.command))
                return Group;
            if(arg.equals(SubRepo.command))
                return SubRepo;
            if(arg.equals(Submitter.command))
                return Submitter;
            return null;
        }
        public String command;
    }


    public static void main(String args[])
    {
        new AuditTrackerManage(args).start();
    }
}