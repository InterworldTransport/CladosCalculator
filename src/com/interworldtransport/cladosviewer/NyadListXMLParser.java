package com.interworldtransport.cladosviewer;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class NyadListXMLParser 
{
    public static void main(String[] args) throws Exception 
    {
 
    	final String xmlFilePath = "Snapshot.txt";
        
        //Use method to convert XML string content to XML Document object
        Document doc = convertXMLFileToXMLDocument( xmlFilePath );
         
        //Verify XML document is build correctly
        System.out.println(doc.getFirstChild().getNodeName());
        
    }
    
    private static Document convertXMLFileToXMLDocument(String filePath) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new File(filePath));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}    
