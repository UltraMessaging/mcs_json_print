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

# TEST

Download the https://github.com/UltraMessaging/mcs_demo repo.
In it is a sub-directory named "json_print".

Copy "JsonPrint.jar" to that sub-directory and follow the instructions
in the README.md
