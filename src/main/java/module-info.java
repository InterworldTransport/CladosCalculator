/**
 * The Clados Calculator is packaged as one module.
 * 
 * @author Dr Alfred W Differ
 * @version 2.0
 */

module org.interworldtransport.cladoscalculator {
	exports org.interworldtransport.cladosviewer;
	exports org.interworldtransport.cladosviewerEvents;
	exports org.interworldtransport.cladosviewerExceptions;
	
	requires transitive java.desktop;
	requires transitive java.xml;
	requires transitive org.interworldtransport.clados;
}