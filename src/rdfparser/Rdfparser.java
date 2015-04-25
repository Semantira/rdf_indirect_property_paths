/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdfparser;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author Abdul
 */
public class Rdfparser {
    static String property = null;
    /**
     * @param args the command line
     * arguments
     */
    public static void main(String[] args) {
        Parser  parser = new Parser("data/test.rdf");
        parser.readRDF();
        //readXML();
        // TODO code application logic here
        /*try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("G:\\abdul\\projects\\rdfparser\\src\\rdfparser\\test.rdf"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
           
            NodeList listOfPersons = doc.getElementsByTagName("rdf:RDF");
            int totalPersons = listOfPersons.getLength();
            for(int s=0; s<listOfPersons.getLength() ; s++){
                Node firstPersonNode = listOfPersons.item(s);
                Element firstPersonElement = (Element)firstPersonNode;
                NodeList textFNList = firstPersonElement.getChildNodes();
                
                int sublenght = textFNList.getLength();
                System.out.println("Node : "+s+" value:" +sublenght);
                for(int si=0; si<sublenght ; si++){
                    Node firstPersonNodes = textFNList.item(si);
                    Element nelements = (Element)firstPersonNodes;
                    System.out.print("Count: "+si+",  Value:"+nelements.getNodeName());
                    
                }
            }
            System.exit(1);
           

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }*/
    }
    static void readXML() {
        try {
 
        File file = new File("data/test.rdf");

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                                 .newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        if (doc.hasChildNodes()) {
            printNote(doc.getDocumentElement().getChildNodes());
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList) {
        String tlClass = null;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                
                if("rdf:Description".equals(tempNode.getNodeName())){
                    tlClass = getTopLevelClass(tempNode.getChildNodes());
                }
                if(null != property && "rdf:type".equals(tempNode.getNodeName())){
                    Element eElement = (Element) tempNode;
                  //  System.out.println("Node value =" + eElement.getAttribute("rdf:resource"));
                }
               // System.out.println("Node Value =" + tempNode.getTextContent());
                /*if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        
                        //System.out.println("attr name : " + node.getNodeName());
                        //System.out.println("attr value : " + node.getNodeValue());
                    }
                }*/
                if (tempNode.getChildNodes().getLength() > 1) {
                    // loop again if has child nodes
                    if(!"rdf:Description".equals(tempNode.getNodeName())) {
                       property = tempNode.getNodeName();
                       System.out.println(tlClass + property+" [-open]");
                    }
                    printNote(tempNode.getChildNodes());
                    
                    if(!"rdf:Description".equals(tempNode.getNodeName()))
                        System.out.println(tlClass + tempNode.getNodeName()+" [Close-]");
                }
            }
        }
    }
    static String getTopLevelClass(NodeList nodeList) {
        String str_class = null;
        ArrayList<String> paths = new ArrayList<>();
        HashMap<String,String> keyList= new HashMap<String,String>();

        int len = nodeList.getLength();
        for(int i=0; i<len; i++){
           Node tempNode = nodeList.item(i);
           if("rdf:type".equals(tempNode.getNodeName())){
               Element eElement = (Element) tempNode;
               String clsName = getClassName(eElement.getAttribute("rdf:resource"));
               if(!keyList.containsKey(clsName))
                str_class =  eElement.getAttribute("rdf:resource");
               if(property!=null)
                  paths.add("["+property+"]"+eElement.getAttribute("rdf:resource"));
               keyList.put(clsName, eElement.getAttribute("rdf:resource"));
           }
        }
        System.out.println(paths);
        System.out.println(str_class);
        return str_class;
    }
    static void getPaths(NodeList nodeList, String str){
        
    }
    
    static String getClassName(String url) {
        String str_class = null;
        String[] pcs = url.split("/");
        str_class = pcs[pcs.length-1];
        return str_class;
    }
    private void parseNode(Element node){
        NodeList childs = node.getChildNodes();
        int length = childs.getLength();
        for(int i=0; i<length; i++) {
            Node firstPersonNode = childs.item(i);
            Element nelement = (Element)firstPersonNode;
            System.out.print("Count: "+i+",  Value:"+nelement.getNodeName());
        }
    } 
}
