package dev.gruff.hardstop.api.anz;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

    public static File repo=null;

    public static void main(String[] args) throws IOException, ParserConfigurationException {


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();


        // generate the API diff def for a set of APIs in
        // local repo cache..

      //  File store=new File(args[0]);

        File m2=new File(System.getProperty("user.home"));
        m2=new File(m2,".m2");
        repo=new File(m2,"repository");
        Path repoPath=repo.toPath();
        int c[]=new int[1];

        Files.walk(repoPath,FileVisitOption.FOLLOW_LINKS).forEach(p -> {
            File f=p.toFile();
            if(f.isFile() && f.getName().toLowerCase().endsWith(".pom")) {
                printPOM(p, f, db);
                c[0]++;
            }
        });
        System.out.println("processed "+c[0]);





    }

    private static void printPOM(Path p, File f, DocumentBuilder db) {
       // System.out.println(f.getAbsolutePath());
        try {
            Document doc = db.parse(f);
            Element top=doc.getDocumentElement();

            top.normalize();
            if(top.getNodeName().equals("project")) {

                Properties props=loadProps(top);
                Coord coords=getCoords(props,top);
                addProjectProps(props,coords);

               List<Node> projectDeps=getGrandKids(top,"dependencies","dependency");
               for(Node node:projectDeps) {
                    // printDependency(props,node);
               }
            } else {
                System.err.println("ignored "+ p);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addProjectProps(Properties props, Coord coords) {

        if(coords.artifactId!=null) props.put("${project.artifactId}",coords.artifactId);
        if(coords.groupId!=null) props.put("${project.groupId}",coords.groupId);
        if(coords.version!=null) props.put("${project.version}",coords.version);
        if(coords.scope!=null) props.put("${project.scope}",coords.scope);
    }

    private static Coord getCoords(Properties props,Element e) {

        Coord c=new Coord();
        NodeList nl=e.getChildNodes();
        for(int i=0;i<nl.getLength();i++) {
            Node n=nl.item(i);
            switch(n.getNodeName()) {
                case "groupId" : c.groupId=subs(props,n.getTextContent()); break;
                case "artifactId" : c.artifactId=subs(props,n.getTextContent()); break;
                case "version" : c.version=subs(props,n.getTextContent()); break;
                case "scope" : c.scope=subs(props,n.getTextContent()); break;
            }
        }
        return c;
    }

    private static List<Node> getGrandKids(Element grandparent, String parent, String kidName) {
        List<Node> grandkids=new LinkedList<>();
        NodeList nl=grandparent.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node c=nl.item(i);
            if(c.getNodeName().equals(parent)) {
                NodeList kids=c.getChildNodes();
                for(int j=0;j<kids.getLength();j++) {
                    Node gk=kids.item(j);
                    if(gk.getNodeName().equals(kidName)) {
                        grandkids.add(gk);
                    }
                }
                return grandkids;
            }
        }
        return grandkids;
    }

    private static Properties loadProps(Element top) {

        Properties p=new Properties();
        p.put("maven.compiler.source","1.8");
        p.put("maven.compiler.target","1.8");

        NodeList nl=top.getElementsByTagName("properties");
        if(nl.getLength()>0) {
            Node node =  nl.item(0);
            NodeList vl=node.getChildNodes();
            for(int vi=0;vi<vl.getLength();vi++) {
                Node pn=vl.item(vi);
                String key=pn.getNodeName();
                String value=pn.getTextContent();
                p.put("${"+key+"}",value);

            }
        }

        return p;
    }

    private static void printNodeNames(NodeList nl) {
        for(int i=0;i<nl.getLength();i++) {
            Node n = nl.item(i);
            System.out.println(n.getNodeName());
        }
    }

    private static void printDependency(Properties props, Node d) {
            Coord c=getCoords(props, (Element) d);
            System.out.println("    "+c.groupId+":"+c.artifactId+":"+c.version+"::"+c.scope);

    }

    private static String subs(Properties props, String textContent) {

        final String[] t =new String[]{textContent};
        props.forEach( (k,v) -> {
           t[0]=t[0].replace(k.toString(),v.toString());
        });

        if(t[0].contains("${")) {
            System.out.println("no sub for "+t[0]);
        }
        return t[0];
    }

    private static class Coord {

        String groupId=null;
        String artifactId=null;
        String version=null;
        String scope=null;
    }
}