Chart Service for the GLIMMPSE Software System.  Creates
scatter plots.
  
 Copyright (C) 2010 Regents of the University of Colorado.  
 
 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 
------------------------------
1. INTRODUCTION
------------------------------

This web service process requests for power curve scatter plots from
the Glimmpse user interface.  It is a component of the Glimmpse software system 
(http://www.glimmpse.com/)

------------------------------
2.  LATEST VERSION
------------------------------

Version 1.0.0

------------------------------
3.  DOCUMENTATION
------------------------------

Documentation is available from the project web site:

http://www.glimmpse.com/

The web service interface documentation is included with the source distribution in:

${CHART_SERVICE_HOME}/docs/api.html

------------------------------
4. DEPENDENCIES
------------------------------

This web service has been tested in Apache Tomcat 6.x

==Third-party dependencies==

Java Runtime Environment 1.6.0 or higher
Restlet 1.1.6 (org.restlet.jar, com.noelios.restlet.jar, com.noelios.restlet.ext.servlet_2.5.jar)
JFreechart 1.0
Apache Commons Codec 1.4
JUnit 4.7
Log4j 1.2.15
Apache Ant 1.8.1

------------------------------
5.  SUPPORT
------------------------------

This web service is provided without warranty.

For questions regarding this web service, please email sarah.kreidler@ucdenver.edu

------------------------------
6.  ANT BUILD SCRIPT
------------------------------

The main build.xml script is located in the ${CHART_SERVICE_HOME}/build
directory.  To compile the library, cd to ${CHART_SERVICE_HOME}/build
and type

cd to ${CHART_SERVICE_HOME}/build
ant

The resulting war file is called

${CHART_SERVICE_HOME}/build/bin/chart.war

The build script assumes that third party libraries are installed in a 
thirdparty directory at the same level as ${CHART_SERVICE_HOME}.
The thirdparty distribution in the correct format can be downloaded 
from http://www.glimmpse.com/

------------------------------
7. CONTRIBUTORS / ACKNOWLEDGEMENTS
------------------------------

This library was created by Dr. Sarah Kreidler and Dr. Deb Glueck
at the University of Colorado Denver.

Special thanks to the following individuals were instrumental in completion of this project:
Dr. Keith Muller
Dr. Anis Karimpour-Fard
Dr. Jackie Johnson

