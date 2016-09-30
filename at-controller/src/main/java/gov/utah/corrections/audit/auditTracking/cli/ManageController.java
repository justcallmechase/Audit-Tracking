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
