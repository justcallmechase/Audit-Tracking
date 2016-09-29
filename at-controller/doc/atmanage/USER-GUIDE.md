# Interface Repository (IR) Manage Users Guide #

## Command Line Syntax ##

The Linux and Windows execution scripts found at ${IR-HOME}/bin/irmanage can be used to run the irmanage application.
Those scripts execute the Java class org.opendof.tools.repository.interfaces.cli.InterfaceRepositoryManage.


    Valid commands: irmanage [-v|--version|-h|--help]|<submitter|legacy|interface|group|subrepo>
    -h,--help        Display irmanage help.
    -v,--version     Display irmanage's version information

      Help for any given command can be obtained by:
        submitter [-h|--help]
        legacy [-h|--help]
        interface [-h|--help]
        group [-h|--help]
        subrepo [-h|--help]

The following will assume we are using the scripts and that "Valid commands: irmanage" is the alias for "InterfaceRepositoryManage".

## Interfaces Commands ##
Interface maintenance support.

    Valid commands: irmanage interface [-h|--help]|<sync|list|add|delete|update>
    -h,--help        Display interface help.

    Help for any given interface sub-command can be obtained by:
        sync [-h | --help]
        list [-h | --help]
        add [-h | --help]
        delete [-h | --help]
        update [-h | --help]

### Interface Add ###

    usage: irmanage interface add [-c <arg>] [-f <arg>] [-g <arg>] [-h] [-i <arg>]
       [-n <arg>] [-p <arg>] [-r <arg>] [-s <arg>] [-x <arg>]
    -c,--config-path <arg> Path to configuration file.
    -f,--xml-files <arg>   Add interfaces where <arg> is a space separated list
                           of file/dir paths. This is version 1.0 functionality.
                           Cannot be used in combination with --interface-id or
                           --xml-file.  Optional
    -g,--access-group <arg> The access group where <arg> is the group name. If
                            not given it defaults to annonymous (public).  If
                            specified the group must already exist in the db.
                            Optional.
    -h,--help               Display help.
    -i,--interface-id <arg> The interface ID.  The --xml-file and --xml-files can
                            also be used to provide the interface ID.   If none
                            of these are selected a normal working allocation is
                            done.  --repository is required for normal working
                            allocation.  Optional.
    -n,--version <arg>      The version of the given interfaces. Defaults to 1 if
                           not given. Optional.
    -p,--published <arg>   The published flag where <arg> is <true | false>.
                           Defaults to false if not given.  Optional.
    -r,--repository <arg>  Repository type. i.e. <opendof || allseen>.
                           Typically can be determined by the iid and is
                           optional in these cases.  Optional.
    -s,--submitter <arg>   The submitter where <arg> is the submitter's email to
                           use. If not given admin@opendof.org will be used. If
                           given the submitter must already exist in the db.
                           Optional.
    -x,--xml-file <arg>    The xml file where <arg> is a single file.  Can be
                           used to obtain the iid but must match --interface-id
                           if it is also given.  Optional.

### Interface Delete ###

    usage: irmanage interface delete [-c <arg>] [-f] [-h] -i <arg> -n <arg> [-r
       <arg>]
    -c,--config-path <arg> Path to configuration file.
    -f,--force             Force flag, there are no arguments. Required to
                           delete a published interface, otherwise Optional.
    -h,--help              Display help.
    -i,--interface-id <arg> An interface or interfaces where <arg> is a space
                            separated list of the Interface IDs.  Required.
    -n,--version <arg>     The version of the given interfaces.  If multiple
                           interfaces are specified this version applies to all
                           of them.  Required.
    -r,--repository <arg>  Repository type. i.e. <opendof || allseen>.
                           Typically can be determined by the interface ID and
                           is optional in these cases.  Optional.
### Interface List ###
    usage: irmanage interface list [-c <arg>] [-h] [-p <arg>] [-r <arg>] [-s <arg>]
    -c,--config-path <arg> Path to configuration file.
    -h,--help              Display help.
    -p,--published <arg>   The published flag where <arg> is <true | false>.
                           Defaults to both.  Optional.
    -r,--repository <arg>  Repository type. i.e. <opendof || allseen>.  Defaults
                           to all.  Optional.
    -s,--submitter <arg>   The submitter where <arg> is the submitter's email to
                           use. Defaults to all.  Optional.
### Interface Update ###
    usage: irmanage interface update [-c <arg>] [-f] [-g <arg>] [-h] -i <arg> -n
       <arg> [-p <arg>] [-r <arg>] [-s <arg>] [-x <arg>]
    -c,--config-path <arg> Path to configuration file.
    -f,--force             Force flag, there are no arguments. Required to
                           modify the published flag, otherwise Optional.
    -g,--access-group <arg> The access group where <arg> is the group name. If
                            not given the existing value will not be modified.
                            If specified the group must already exist in the db.
                            Optional.
    -h,--help               Display help.
    -i,--interface-id <arg> The interface ID to update.  Required.
    -n,--version <arg>      The version of the interface to be updated. Required.
    -p,--published <arg>    The published flag where <arg> is <true | false>. If
                            not given the existing value will not be modified.
                            The --force is required to modify this field.
                            Optional.
    -r,--repository <arg>   Repository type. i.e. <opendof || allseen>.
                            Typically can be determined by the iid and is
                            optional in these cases.  Optional.
    -s,--submitter <arg>    The submitter where <arg> is the submitter's email to
                            use. If not given the existing value will not be
                            modified.  If given the submitter must already exist
                            in the db.  Optional.
    -x,--xml-file <arg>     The xml file where <arg> is a single file.  If not
                            given the existing xml field value will not be
                            modified.  Optional.
### Interface Sync ###
    usage: irmanage interface sync [-c <arg>] [-h]
    -c,--config-path <arg> Path to configuration file.
    -h,--help              Display help.

## Submitter ##
Submitter maintenance support.
    Valid commands: irmanage submitter [-h|--help]|<list|add|delete|update>
    -h,--help        Display submitter help.

    Help for any given submitter sub-command can be obtained by:
      list [-h | --help]
      add [-h | --help]
      delete [-h | --help]
      update [-h | --help]

### Submitter Add ###
    usage: irmanage submitter add [-c <arg>] [-d <arg>] -e <arg> [-h] -n <arg>
    -c,--config-path <arg> Path to configuration file.
    -d,--description <arg> Description of the submitter or group where <arg> is
                           the description.  Optional.
    -e,--email <arg>       Submitter's email where <arg> is the email which must
                           be unique to the IR. Required and must be unique
                           within the IR.
    -h,--help              Display help.
    -n,--name <arg>        Submitter's name where <arg> is the name.  Required

### Submitter Delete ###
    usage: irmanage submitter delete [-c <arg>] -e <arg> [-h]
    -c,--config-path <arg> Path to configuration file.
    -e,--email <arg>       Submitter's email where <arg> is the email address.
                           Required
    -h,--help              Display help.

### Submitter List ###
    usage: irmanage submitter list [-c <arg>] [-h]
    -c,--config-path <arg> Path to configuration file.
    -h,--help              Display help.

### Submitter Update ###
    usage: irmanage submitter update [-c <arg>] [-d <arg>] -e <arg> [-h] [-m <arg>]
       [-n <arg>] [-t <arg>]
    -c,--config-path <arg> Path to configuration file.
    -d,--description <arg> Description of the submitter where <arg> is the new
                           description.  Optional.
    -e,--email <arg>       Submitter's email where <arg> is the current email of
                           submitter being updated.  Required.
    -h,--help              Display help.
    -m,--new-email <arg>   Submitter's new email where <arg> is the new email of
                           submitter being updated.  Optional.
    -n,--name <arg>        Submitter's name where <arg> is the new name.
                           Optional.
    -t,--date <arg>        Creation date where <arg> is in the desired date in
                           mm/dd/yyyy format. Optional

## Group ##
Group maintenance support.

    Valid commands: irmanage group [-h|--help]|<list|add|delete|update>
    -h,--help        Display group help.

    Help for any given group sub-command can be obtained by:
      list [-h | --help]
      add [-h | --help]
      delete [-h | --help]
      update [-h | --help]

### Group Add ###

    usage: irmanage group add [-a <arg>] [-c <arg>] [-d <arg>] -g <arg> [-h] [-m
       <arg>]
    -a,--group-admin <arg> Group administrator where <arg> is the email of the
                           administrator submitter.  Required if adding a new
                           group.  Ignored/optional if adding a member to a group
    -c,--config-path <arg> Path to configuration file.
    -d,--description <arg> Description of the group where <arg> is the new
                           description.  Optional.
    -g,--group <arg>       Group name where <arg> is the name of the group.
                           Required and must be unique within the IR.
    -h,--help              Display help.
    -m,--member <arg>      Submitter to add as a member of the group where <arg>
                           is the email of the submitter to be added.  Optional.
### Group Delete ###
    usage: irmanage group delete [-c <arg>] [-f] -g <arg> [-h] [-m <arg>]
    -c,--config-path <arg> Path to configuration file.
    -f,--force             Force flag, there are no arguments. Required to delete
                           a group that currently has members, otherwise
                           Optional.
    -g,--group <arg>       Group where <arg> is the name of the group.  If given
                           alone the group will be deleted.  --force is required
                           to remove a group that has members.  Required.
    -h,--help              Display help.
    -m,--member <arg>      Member where <arg> is the name of the member to be
                           removed from the group.  Optional.

### Group List ###
    usage: irmanage group list [-c <arg>] [-g <arg>] [-h]
    -c,--config-path <arg> Path to configuration file.
    -g,--group <arg>       Group where <arg> is the name of the group.  If given
                           the members of the group are returned, otherwise a
                           list of existing groups are returned. Optional.
    -h,--help              Display help.

### Group Update ###
    usage: irmanage group update [-a <arg>] [-c <arg>] [-d <arg>] -g <arg> [-h] [-t
       <arg>]
    -a,--group-admin <arg> The group administrator where <arg> is in the
                           administrator submitter's email.  Required
    -c,--config-path <arg> Path to configuration file.
    -d,--description <arg> Description of the submitter where <arg> is the new
                           description.  Optional.
    -g,--description <arg> Group to update where <arg> is the name of the group.
                           Required.
    -h,--help              Display help.
    -t,--date <arg>        Creation date where <arg> is in the desired date in
                           mm/dd/yyyy format. Optional
## SubRepo ##
Sub-Repository maintenance support.

    Valid commands: irmanage subrepo [-h|--help]|<list|add|delete|update>
    -h,--help        Display subrepo help.

    Help for any given subrepo sub-command can be obtained by:
      list [-h | --help]
      add [-h | --help]
      delete [-h | --help]
      update [-h | --help]

### SubRepo Add ###
    usage: irmanage subrepo add -b <arg> [-c <arg>] [-g <arg>] [-h] -p <arg>
    -b,--label <arg>       The human friendly label to be associated with the
                           node.  Required
    -c,--config-path <arg> Path to configuration file.
    -g,--group <arg>       The access group to be associated with the node where
                           <arg> is the name of an existing group in the IR.
                           Optional
    -h,--help              Display help.
    -p,--path <arg>        Full path name of the node to be added where <arg> is
                           a '/' seperated path with either opendof | allseen as
                           the root.  For example 'opendof/1/4 would expect
                           'opendof/1' to already exist and 4 will be added as a
                           child to it.  Required

### SubRepo Delete ###
    usage: irmanage subrepo delete -b <arg> [-c <arg>] [-h] -p <arg>
    -b,--label <arg>       The human friendly label associated with the node.
                           Required
    -c,--config-path <arg> Path to configuration file.
    -h,--help              Display help.
    -p,--path <arg>        Full path name of the node to be deleted where <arg>
                           is a '/' seperated path with either opendof | allseen
                           as the root.  For example 'opendof/1/4 would delete 4
                           and leave 'opendof/1'.  Required
### SubRepo List ###
    usage: irmanage subrepo list [-c <arg>] [-h] -p <arg>
    -c,--config-path <arg> Path to configuration file.
    -h,--help              Display help.
    -p,--path <arg>        Full path name of the node to be listed from where
                           <arg> is a '/' seperated path with either opendof |
                           allseen as the root.  For example 'opendof' would list
                           the whole tree and 'opendof/1' would list registry 1.
                           At least the root node is required
### SubRepo Update ###
    usage: irmanage subrepo update [-b <arg>] [-c <arg>] [-g <arg>] [-h] -p <arg>
    -b,--label <arg>       The new human friendly label to be associated with the
                           node.  Optional
    -c,--config-path <arg> Path to configuration file.
    -g,--group <arg>       The access group to be associated with the node where
                           <arg> is the name of an existing group in the IR.
                           Optional
    -h,--help              Display help.
    -p,--path <arg>        Full path name of the node to be updated where <arg>
                           is a '/' seperated path with either opendof | allseen
                           as the root.  For example 'opendof/1/4'.  Required
