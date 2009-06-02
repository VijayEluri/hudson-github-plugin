Hudson Github Plugin
====================

This is a WIP Github plugin for the Hudson continuous integration server.

There is no public release yet, it's still an early WIP.

Stay tuned...


Development
===========

Start the local Hudson instance:

    mvn hpi:run


Hudson PLugin Maven goals
-------------------------

	hpi:create  Creates a skeleton of a new plugin.
	
	hpi:hpi Builds the .hpi file

	hpi:hpl Generates the .hpl file

	hpi:run Runs Hudson with the current plugin project

	hpi:upload Posts the hpi file to java.net. Used during the release.
	
	
How to install
--------------

Run 

	mvn hpi:hpi
	
to create the plugin .hpi file.


To install:

1. copy the resulting ./target/rdoc.hpi file to the $HUDSON_HOME/plugins directory. Don't forget to restart Hudson afterwards.
	
2. or use the plugin management console (http://example.com:8080/pluginManager/advanced) to upload the hpi file. You have to restart Hudson in order to find the pluing in the installed plugins list.



Ressources
----------

Hudson Plugins:

 * http://wiki.hudson-ci.org/display/HUDSON/Plugin+structure
 * http://wiki.hudson-ci.org/display/HUDSON/Extension+points
