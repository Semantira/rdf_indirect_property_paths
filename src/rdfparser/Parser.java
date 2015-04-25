/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdfparser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static rdfparser.Rdfparser.getClassName;
import static rdfparser.Rdfparser.getTopLevelClass;

/**
 *
 * @author Abdul
 */
public class Parser {
    private HashMap<String,String> clsList = new HashMap<String,String>();
    private ArrayList<String> paths = new ArrayList<>();
    private String property;
    private String path;
    private final String file;
    private int totalcount = 0;
    
    public Parser(String filepath) {
        this.file=filepath;
    }
     
    public void readRDF() {
        try {
 
        File file = new File(this.file);

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                                 .newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        if (doc.hasChildNodes()) {
            //System.out.println(doc.getDocumentElement().getChildNodes().item(4).getNodeName());
            //System.out.println(doc .getElementsByTagName("rdf:Description").getLength());
            this.parseDocument(doc.getDocumentElement().getChildNodes());
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void parseDocument(NodeList nodeList) {
        String tlClass = null;
        //System.out.println(nodeList.getLength());
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                NodePaths np = new NodePaths(tempNode.getChildNodes());
                np.processNode();
                /*
                if("rdf:Description".equals(tempNode.getNodeName())){
                    tlClass = this.getTopLevelClass(tempNode.getChildNodes());
                }
                if (tempNode.getChildNodes().getLength() > 1) {
                    // loop again if has child nodes
                    if(!"rdf:Description".equals(tempNode.getNodeName())) {
                       property = tempNode.getNodeName();
                       //this.clsList.put(property, tlClass);
                       //this.showOutPut(property, paths);
                       System.out.println(tlClass + property+" [-open]");
                    }
                   // parseDocument(tempNode.getChildNodes());
                    
                    if(!"rdf:Description".equals(tempNode.getNodeName())) {
                        System.out.println(tlClass + tempNode.getNodeName()+" [Close-]");
                    }
                }
                System.out.println(tlClass);*/
            }
        }
    }
    
    public String getTopLevelClass(NodeList nodeList) {
        String str_class = null;
        this.paths = new ArrayList<>();
        System.out.println(nodeList.getLength());
        HashMap<String,String> keyList= new HashMap<String,String>();

        int len = nodeList.getLength();
        for(int i=0; i<len; i++){
           Node tempNode = nodeList.item(i);
           if("rdf:type".equals(tempNode.getNodeName())){
               Element eElement = (Element) tempNode;
               String clsName = this.getClassName(eElement.getAttribute("rdf:resource"));
               
               if(!keyList.containsKey(clsName))
                str_class =  eElement.getAttribute("rdf:resource");
               
               if(property!=null)
                  this.paths.add(eElement.getAttribute("rdf:resource"));
               
               keyList.put(clsName, eElement.getAttribute("rdf:resource"));
           }
        }
        System.out.println(keyList);
        this.showOutPut(property, paths);
        this.clsList.put(property, str_class);
        return str_class;
    }
    
    public String getClassName(String url) {
        String str_class = null;
        String[] pcs = url.split("/");
        str_class = pcs[pcs.length-1];
        return str_class;
    }
    
    public void showOutPut(String _property, ArrayList _paths) {
      int len = _paths.size();
      int clsLen = this.clsList.size();
      //System.out.println("output starts");
      for(int i=0; i<len; i++) {
          System.out.print(_paths.get(i)+", "+_property+", ");
          for(int c=0; c<clsLen; c++) {
              //System.out.print("{"+this.clsList+"["+_property+"]"+_paths.get(i)+"}");
          }
          System.out.println();
      }
      //System.out.println("output ends");
    }
}
