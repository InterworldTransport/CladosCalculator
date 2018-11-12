# General makefile for construction of the Clados Library
# written by Dr Alfred Differ on 2000/02/04
# last modified on 2000/02/04
# Terribly outdated.  Please do not use this.  Use the Ant build file instead.

Monad:
	javac -verbose com/interworldtransport/clados/Monad.java

clean :
	rm -r ./com/interworldtransport/clados/*.class

run :
	echo "The Monad can not be run independently of a physical application.  There is no main method."

doc :
	javadoc -d ../Cladosdoc -sourcepath . @Makedocpackage -overview com/interworldtransport/overview.html -windowtitle "Clados Documentation" -doctitle "Clados Package Documentation 2000/03/03" -use -header "<a href=\"http://www.interworldtransport.com\">Interworld Transport</a>" -group "Mathematics Packages" "com.interworldtransport.clados" 
#
#
