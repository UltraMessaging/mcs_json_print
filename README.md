# mcs_json_print
A Java module for the UM MCS service that prints monitoring
data in JSON format.

# Table of contents

<!-- mdtoc-start -->
&bull; [mcs_json_print](#mcs_json_print)  
&bull; [Table of contents](#table-of-contents)  
&bull; [COPYRIGHT AND LICENSE](#copyright-and-license)  
&bull; [REPOSITORY](#repository)  
&bull; [INTRODUCTION](#introduction)  
&bull; [THE FILES](#the-files)  
&bull; [BUILD](#build)  
&bull; [TEST](#test)  
<!-- TOC created by '../mdtoc/mdtoc.pl README.md' (see https://github.com/fordsfords/mdtoc) -->
<!-- mdtoc-end -->

# COPYRIGHT AND LICENSE

All of the documentation and software included in this and any
other Informatica Ultra Messaging GitHub repository
Copyright (C) Informatica, 2022. All rights reserved.

Permission is granted to licensees to use
or alter this software for any purpose, including commercial applications,
according to the terms laid out in the Software License Agreement.

This source code example is provided by Informatica for educational
and evaluation purposes only.

THE SOFTWARE IS PROVIDED "AS IS" AND INFORMATICA DISCLAIMS ALL WARRANTIES
EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF
NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR
PURPOSE.  INFORMATICA DOES NOT WARRANT THAT USE OF THE SOFTWARE WILL BE
UNINTERRUPTED OR ERROR-FREE.  INFORMATICA SHALL NOT, UNDER ANY CIRCUMSTANCES,
BE LIABLE TO LICENSEE FOR LOST PROFITS, CONSEQUENTIAL, INCIDENTAL, SPECIAL OR
INDIRECT DAMAGES ARISING OUT OF OR RELATED TO THIS AGREEMENT OR THE
TRANSACTIONS CONTEMPLATED HEREUNDER, EVEN IF INFORMATICA HAS BEEN APPRISED OF
THE LIKELIHOOD OF SUCH DAMAGES.

# REPOSITORY

See https://github.com/UltraMessaging/mcs_json_print for code and documentation.

# INTRODUCTION

Informatica recommends that Ultra Messaging users enable the
automatic monitoring feature in their UM-based applications and most
UM daemons (Store, DRO, etc.).
The UM [MCS](https://ultramessaging.github.io/currdoc/doc/Operations/monitoring.html#monitoringcollectorservicemcs)
is a convenient centralized Java service that collects monitoring data
from UM.
By default, it writes the monitoring data to an "sqlite" database.
However, you can replace the "sqlite" interface with your own
user-written plugin.

This repository is an example user-written MCS plugin that receives
monitoring data and prints it in JSON format to a text file.
The expectation is that you will replace the text file printing
with your own monitoring data management system.

# THE FILES

* README.md - this documentation file.
* JsonPrint.java - source code for the example MCS plugin.
* bld.sh - example build script for the module.
* lbm.sh.example - example environment setting script.

# BUILD

1. Make sure your Java environment is Java 9 or beyond.
2. Copy "lbm.sh.example" to "lbm.sh"
3. Modify "lbm.sh" for your environment.
If your UM installation uses the standard directory structure,
you can probably just change the "L=" line.
4. Run the "bld.sh" script.

You should how have a "JsonPrint.jar" file.

# DEMO

Download the https://github.com/UltraMessaging/mcs_demo repo.
In it is a sub-directory named "json_print".

Copy "JsonPrint.jar" to the "json_print" sub-directory and follow the instructions
in the README.md

# STARTING THE MCS

The demo mentioned above demonstrates starting the MCS with this plugin.
Here's a description:

* In the MCS xml configuration file, set the type to "class:JsonPrint":
<ul>
````
  ...
  <connector>
    <type>class:JsonPrint</type>
    <properties-file>mcs.properties</properties-file>
  </connector>
  ...
````
</ul>
* In the above-referenced properties file, specify the desired name of the output file
that the plugin should write the JSON data to. For example:
<ul>
````
outFilePath=/tmp/tst.json
````
</ul>
* As of UM version 6.16, the "MCS" script include with the UM package cannot be
used to launch the MCS program with a user-written plugin.
This is because the "MCS" script hard-codes the jar files for the class path.
Fortunately, the "MCS" script does little more than just assemble the proper
command line, which you can reproduce easily-enough in your own startup script.
Note the inclusion of "JsonPrint.jar" in the following example:
<ul>
````
# The following must be set for your environment.
export LBM_LICENSE_INFO="Product=LBM,UME,UMQ,UMDRO:Organization=xxxx:Expiration-Date=never:License-Key=xxxx xxxx xxxx xxxx"
L=$HOME/UMP_6.15/
export LD_LIBRARY_PATH=$L/Linux-glibc-2.17-x86_64/lib
export LBM_XML_CONFIG_FILENAME=um.xml  # Path to UM library configuration file.
export LBM_XML_CONFIG_APPNAME=mcs      # MCS applicaton name that "um.xml" references.

# Run the MCS program.
java -classpath $L/MCS/lib/MCS.jar:$L/MCS/lib/UMS_6.15.jar:$L/MCS/lib/UMSMON_PROTO3.jar:./JsonPrint.jar:$L/MCS/lib/um-mondb-common.jar:$L/MCS/lib/protobuf-java-util-4.0.0-rc-2.jar:$L/MCS/lib/protobuf-java-4.0.0-rc-2.jar:$L/MCS/lib/gson-2.8.5.jar:$L/MCS/lib/java-getopt-1.0.13.jar:$L/MCS/lib/log4j-api-2.14.1.jar:$L/MCS/lib/log4j-core-2.14.1.jar:$L/MCS/lib/guava-24.1.1-jre.jar com.informatica.um.monitoring.UMMonitoringCollector -Z$L/MCS/bin/ummon.db mcs.xml
````
</ul>
