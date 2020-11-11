package com.interworldtransport.cladosviewer;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class NyadListXMLParser 
{
    public static void main(String[] args) throws Exception 
    {
 
    	final String xmlFilePath = "SavedObjects.xml";
        
        //Use method to convert XML string content to XML Document object
        Document doc = loadXMLObjectsToXMLDocument( xmlFilePath );
        
        XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		
		String path4NyadCount = "count(//Nyad)";
		String path4NyadOrder1 = "count(//Nyad[";
		String path4NyadOrder2 = "]/MonadList/Monad)";
		//String path4UsedCladosField = "/NyadList/Nyad[1]/MonadList/Monad[1]/Algebra/";
		
		//XPathExpression expr = xpath.	.compile(path4NyadCount);
		String result = xpath.evaluate(path4NyadCount, doc);
		System.out.println("Nyad count is "+result);
		int count = Integer.parseInt(result);
		
		
		xpath.reset();
		XPathExpression expr = xpath.compile("//Algebra/*[@cardinal]");
		NodeList fields = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		for (int k=0; k<fields.getLength(); k++)
		{
			System.out.println(fields.item(k).getNodeName());
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
