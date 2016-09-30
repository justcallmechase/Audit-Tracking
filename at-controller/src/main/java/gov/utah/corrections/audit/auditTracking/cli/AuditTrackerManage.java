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
package gov.utah.corrections.audit.auditTracking.cli;

import com.ccc.tools.cli.CliBase;
import com.ccc.tools.cli.CliCommand;

import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.AddCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.CustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.DeleteCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.ListCustomerCommand;
import gov.utah.corrections.audit.auditTracking.cli.cmds.customer.UpdateCustomerCommand;

@SuppressWarnings("javadoc")
public class AuditTrackerManage extends CliBase
{
    private final static String AlternateExecutableName = "atmanage";
    public final static  String AtConfigKey = "corrections.audit.audit-tracking.config";

//    protected volatile CoreController coreController;
    protected volatile CliCommand command;

    public AuditTrackerManage(String[] args)
    {
        this(args, true, false, false);
    }

    public AuditTrackerManage(String[] args, boolean config, boolean logToFile, boolean skipValidate)
    {
        super(args, AtConfigKey, config, logToFile, DefaultMaxHelpWidth);
        setExecutableName(AlternateExecutableName);

        CustomerCommand ifaceCmd = new CustomerCommand(this, Command.Interface.command);
        addCommand(ifaceCmd);
        ifaceCmd.addChild(new AddCustomerCommand(this, ifaceCmd, CustomerCommand.Command.Add.command));
        ifaceCmd.addChild(new DeleteCustomerCommand(this, ifaceCmd, CustomerCommand.Command.Delete.command));
        ifaceCmd.addChild(new UpdateCustomerCommand(this, ifaceCmd, CustomerCommand.Command.Update.command));
        ifaceCmd.addChild(new ListCustomerCommand(this, ifaceCmd, CustomerCommand.Command.List.command));

//        SubmitterCommand submitterCmd = new SubmitterCommand(this, Command.Submitter.command);
//        addCommand(submitterCmd);
//        submitterCmd.addChild(new AddSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Add.command));
//        submitterCmd.addChild(new DeleteSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Delete.command));
//        submitterCmd.addChild(new ListSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.List.command));
//        submitterCmd.addChild(new UpdateSubmitterCommand(this, submitterCmd, SubmitterCommand.Command.Update.command));
//
//        GroupCommand groupCmd = new GroupCommand(this, Command.Group.command);
//        addCommand(groupCmd);
//        groupCmd.addChild(new AddGroupCommand(this, groupCmd, GroupCommand.Command.Add.command));
//        groupCmd.addChild(new DeleteGroupCommand(this, groupCmd, GroupCommand.Command.Delete.command));
//        groupCmd.addChild(new ListGroupCommand(this, groupCmd, GroupCommand.Command.List.command));
//        groupCmd.addChild(new UpdateGroupCommand(this, groupCmd, GroupCommand.Command.Update.command));
//
//        SubRepoCommand subrepoCmd = new SubRepoCommand(this, Command.SubRepo.command);
//        addCommand(subrepoCmd);
//        subrepoCmd.addChild(new AddSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Add.command));
//        subrepoCmd.addChild(new DeleteSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Delete.command));
//        subrepoCmd.addChild(new ListSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.List.command));
//        subrepoCmd.addChild(new UpdateSubRepoCommand(this, subrepoCmd, SubRepoCommand.Command.Update.command));

        if(!skipValidate)
            command = validateCommands();
    }

    @Override
    public void run()
    {
        try
        {
//            coreController = new CoreController();
//            coreController.init(properties);
            ManageController manage = new ManageController(null);
            String cmdName = command.getCommandName();
            if(command instanceof CustomerCommand)
            {
                if(cmdName.equals(CustomerCommand.Command.Add.command))
                    manage.customerAdd(((AddCustomerCommand)command).getAddCmdData());
                if(cmdName.equals(CustomerCommand.Command.Delete.command))
                    manage.customerDelete(((DeleteCustomerCommand)command).getDeleteCmdData());
                if(cmdName.equals(CustomerCommand.Command.List.command))
                    manage.customerList(((ListCustomerCommand)command).getListCmdData());
                if(cmdName.equals(CustomerCommand.Command.Update.command))
                    manage.customerUpdate(((UpdateCustomerCommand)command).getUpdateCmdData());
            }
//            else
//            if(command instanceof SubmitterCommand)
//            {
//                if(cmdName.equals(SubmitterCommand.Command.Add.command))
//                    manage.submitterAdd(((AddSubmitterCommand)command).getAddCmdData());
//                if(cmdName.equals(SubmitterCommand.Command.Delete.command))
//                    manage.submitterDelete(((DeleteSubmitterCommand)command).getDeleteCmdData());
//                if(cmdName.equals(SubmitterCommand.Command.List.command))
//                    manage.submitterList();
//                if(cmdName.equals(SubmitterCommand.Command.Update.command))
//                    manage.submitterUpdate(((UpdateSubmitterCommand)command).getUpdateCmdData());
//            }else
//            if(command instanceof GroupCommand)
//            {
//                if(cmdName.equals(GroupCommand.Command.Add.command))
//                    manage.groupAdd(((AddGroupCommand)command).getAddCmdData());
//                if(cmdName.equals(GroupCommand.Command.Delete.command))
//                    manage.groupDelete(((DeleteGroupCommand)command).getDeleteCmdData());
//                if(cmdName.equals(GroupCommand.Command.List.command))
//                    manage.groupList(((ListGroupCommand)command).getListCmdData());
//                if(cmdName.equals(GroupCommand.Command.Update.command))
//                    manage.groupUpdate(((UpdateGroupCommand)command).getUpdateCmdData());
//            }else
//            if(command instanceof SubRepoCommand)
//            {
//                if(cmdName.equals(SubRepoCommand.Command.Add.command))
//                    manage.subrepoAdd(((AddSubRepoCommand)command).getAddCmdData());
//                if(cmdName.equals(SubRepoCommand.Command.Delete.command))
//                    manage.subrepoDelete(((DeleteSubRepoCommand)command).getDeleteCmdData());
//                if(cmdName.equals(SubRepoCommand.Command.List.command))
//                    manage.subrepoList(((ListSubRepoCommand)command).getListCmdData());
//                if(cmdName.equals(SubRepoCommand.Command.Update.command))
//                    manage.subrepoUpdate(((UpdateSubRepoCommand)command).getUpdateCmdData());
//            }else
//                throw new Exception("Unexpected command: " + command.getClass().getName());
            log.debug(getClass().getSimpleName() + " exiting OK");
            close(0);
        } catch (Throwable t)
        {
//            if (coreController != null)
//                coreController.destroy();
            log.error("failed: ", t);
            close(ApplicationError);
        }
    }

    @Override
    public void close(int ccode)
    {
//        if (coreController != null)
//            coreController.destroy();
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