# Interface Repository (IR) Manage Installation Guide #

## untar ##
There is no installation application, simply unzip the distribution tar to a folder of your choice.

## Folders of interest ##
The untar'ed structure of interest to the irmanage application:
 
${IR-HOME}/bin/irmanage
${IR-HOME}/config/irmanage
${IR-HOME}/doc/irmanage

This document as well as a [Users Guide](USER-GUIDE.md) can be found in the doc/irmanage folder  
Linux and Windows execution scripts that will run in-place are in the bin/irmanage folder.
The following configuration file exists in the config/irmanage folder and will require modification.

* interface-properties-cli.properties

## Required modifications ##

The interface-properties-cli.properties file requires modification.  Sensitive MySQL Database passwords will be exposed in this properties file.
It is assumed the user will move and possibly rename this file to a root user secure directory.  The irmanage command-line switch -c or --config-path
will then be used to locate the configuration at runtime. 

The following four key/value pairs will need to be modified with your MySQL database connection information:

<ul>
<li>opendof.tools.interface.repository.persist.mysql.user=DB-USER</li>
<li>opendof.tools.interface.repository.persist.mysql.password=DB-USER-PASS</li>
<li>opendof.tools.interface.repository.persist.mysql.host=DB-URL</li>
<li>opendof.tools.interface.repository.persist.mysql.db-name=DB-NAME</li>
</ul>

The logback.xml should only require modification if you desire to change the default logging level from "info"



## Migration from v1.0 ##
If you are migrating from  Interface-Repository v1.0 please see the [Migration Guide](../../interface-repository/migration/2.0/migration.md)

## New installation ##
Install the [MYSQL Interface-Repository V2.0 Schema](../../interface-repository-mysql/schema/2.0/schema.sql)

Depending on your repository type you may need to setup the Subrepository table with the repositories required sub definition.  See the [SubRepo section of the Users Guide](USER-GUIDE.md)  

