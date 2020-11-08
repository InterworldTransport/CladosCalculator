package com.interworldtransport.cladosviewer;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class NyadListXMLParser 
{
    public static void main(String[] args) throws Exception 
    {
 
    	final String xmlFilePath = "Snapshot.xml";
        
        //Use method to convert XML string content to XML Document object
        Document doc = loadXMLObjectsToXMLDocument( xmlFilePath );
         
        //Verify XML document is build correctly
        System.out.println(doc.getBaseURI());
        System.out.println("NyadLists= "+doc.getChildNodes().getLength());
        System.out.println("Nyad Count= "+doc.getFirstChild().getAttributes().getNamedItem("size").getTextContent());
        
        for (int m = 0; m<doc.getFirstChild().getChildNodes().getLength(); m++)
        {
        	System.out.print(doc.getFirstChild().getChildNodes().item(m).getNodeName());
        	System.out.print("\t");
        	System.out.println(doc.getFirstChild().getChildNodes().item(m).getChildNodes().getLength());
        }
        
        
    }
    
    private static Document loadXMLObjectsToXMLDocument(String filePath) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
      
        //Create DocumentBuilder with default configuration
        try {
			return factory.newDocumentBuilder().parse(new File(filePath));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
        return null;
           
            
        


    }
}    
