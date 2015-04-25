/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdfparser;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Abdul
 */
public class NodePaths {
    String firstNode;
    String nodeClass;
    String parentPath;
    ArrayList properties = new ArrayList<>();
    ArrayList nodeProperties = new ArrayList<>();
    ArrayList nodePaths = new ArrayList<>();
    NodeList nodeList;
    NodeList[] NodesLists;
    
    NodePaths(NodeList nl){
       this.output("Node Paths Called");
       this.nodeList = nl;
    }
    
    private void output(String str ){
        System.out.println(str);
    }
    
    public void setFristNode(String str) {
        this.firstNode = str;
    }
    
    public void setParentPath(String str) {
        this.parentPath = str;
    }
    public void processNode(){
       this.getProperties(this.nodeList);
       if(this.parentPath != null) {
          this.showOutput();
       }
       this.processProperties();
    }
    
    public void getProperties(NodeList nl) {
        String str_class = null;
        HashMap<String,String> keyList= new HashMap<String,String>();

        int len = nl.getLength();
        //this.output("Get Properties is called: "+len);
        for(int i=0; i<len; i++){
           Node tempNode = nl.item(i);
           if("rdf:type".equals(tempNode.getNodeName())){
               Element eElement = (Element) tempNode;
               String clsName = this.getClassName(eElement.getAttribute("rdf:resource"));
               if(!keyList.containsKey(clsName))
                str_class =  eElement.getAttribute("rdf:resource");
               
               if(this.parentPath == null)
                this.nodePaths.add(eElement.getAttribute("rdf:resource"));
               else
                this.nodePaths.add(this.parentPath+eElement.getAttribute("rdf:resource")+"}"); 
               keyList.put(clsName, eElement.getAttribute("rdf:resource"));
               
           } else if(tempNode.getChildNodes().getLength() > 1)  {
               if(!"rdf:Description".equals(tempNode.getNodeName())) {
                    this.properties.add(tempNode.getNodeName());
                    this.nodeProperties.add(tempNode.getChildNodes());
                    //this.processProperties(tempNode);
               }
           }
        }
        if(this.firstNode == null)
            this.firstNode = str_class;
        
        this.nodeClass = str_class;
    }
    
    public String getClassName(String url) {
        String str_class = null;
        String[] pcs = url.split("/");
        str_class = pcs[pcs.length-1];
        return str_class;
    }

    public void showOutput() {
        //System.out.println("First Node: "+this.firstNode);
        //System.out.println("Class: "+this.nodeClass);
        System.out.println("Paths: "+this.nodePaths);
        //System.out.println("Properties: "+this.properties);
        //System.out.println("Parent Path: "+this.parentPath);
        
    }

    void processProperties() {
        //System.out.println(tempNode.getChildNodes().getLength());
        int count = 0;
        for (Object nodePropertie : this.nodeProperties) {
            NodeList tempNode = (NodeList) nodePropertie;
            //System.out.println("processProperties Len: "+tempNode.item(1));
            NodePaths np = new NodePaths(tempNode.item(1).getChildNodes());
            np.setFristNode(this.firstNode);
            if(this.parentPath!=null)
                np.setParentPath(this.parentPath+this.nodeClass+"}{"+this.nodeClass+"["+this.properties.get(count)+"]");
            else
                np.setParentPath("{"+this.nodeClass+"["+this.properties.get(count)+"]");
            np.processNode();
            count++;
        }
        
    }
}
