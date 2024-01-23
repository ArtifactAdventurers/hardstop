package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.api.*;
import dev.gruff.hardstop.core.impl.HSCoordinateImpl;
import dev.gruff.hardstop.core.impl.RavenPOMImpl;
import dev.gruff.hardstop.core.impl.RavenPropertySetImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class POMFileParser {

    private static DocumentBuilderFactory dbf;
    private DocumentBuilder db;

   static {
        dbf = DocumentBuilderFactory.newInstance();


   }

    private HSStore store;

    public POMFileParser(HSStore store) {

        this.store=store;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private Document loadXML(File pom) {

        try {
            return  db.parse(pom);
        } catch (Exception e) {
           throw new RuntimeException(e);

        }
    }


    private static List<Element> getKids(Element owner, String childTag) {
        List<Element> kids=new LinkedList<>();
        NodeList nl=owner.getChildNodes();
        for(int i=0;i<nl.getLength();i++) {
            Node n=nl.item(i);
            if(n instanceof Element) {
                Element e= (Element) n;
                if(childTag==null || e.getNodeName().equals(childTag)) kids.add(e);
            }
        }
        return kids;
    }


    private static String subs(RavenPropertySet props, String textContent) {

        final String[] t =new String[]{textContent};
        props.keys().forEach( k -> {
            RavenProperty p=props.property(k);
            String key="${"+p.key()+"}";
            t[0]=t[0].replace(key,p.value());
        });

        if(t[0].contains("${")) {
            System.out.println("no sub for "+t[0]);
        }
        return t[0];
    }

    private static RavenPropertySet loadProps(RavenPOM owner, Element node) {

        RavenPropertySet  results=new RavenPropertySetImpl();
        List<Element> kids=getKids(node,null);
        for(Element k:kids) {
            String key=k.getTagName();
            String value=k.getTextContent();
            RavenProperty rp= new RavenPropertyImpl(key, value, owner);
            results.addProperty(rp);
        }

        return results;
    }


    private static RavenCoordinate getCoords(RavenPropertySet props, Element e) {

        String g=null;
        String a=null;
        String v=null;
        String s=null;

        NodeList nl=e.getChildNodes();
        for(int i=0;i<nl.getLength();i++) {
            Node n=nl.item(i);
            switch(n.getNodeName()) {
                case "groupId" : g=subs(props,n.getTextContent()); break;
                case "artifactId" : a=subs(props,n.getTextContent()); break;
                case "version" : v=subs(props,n.getTextContent()); break;
                case "scope" : s=subs(props,n.getTextContent()); break;
            }
        }

        if(g==null) g=subs(props,props.value("parent.groupId"));
        String finalA = a;
        String finalG = g;
        String finalV = v;
        return new HSCoordinateImpl(finalA, finalG, finalV);

    }

    private Element getSingleElement(Element owner, String childTag) {

        List<Element> kids=getKids(owner,childTag);
        if(kids.size()>1) throw new RuntimeException("more than one "+childTag+" as child of "+owner.getNodeName());
        if(kids.isEmpty()) return null;
        return kids.get(0);
    }
    public RavenPOM parse(File pom) {

        if(pom==null) throw new RuntimeException("pom is null");
        if(!pom.exists()) throw new RuntimeException("pom does not exist ["+pom.getAbsolutePath()+"]");
        if(!pom.isFile())  throw new RuntimeException("pom is not a file ["+pom.getAbsolutePath()+"]");

        // load pom as XML file ...
        Document pomDoc = loadXML(pom);
        if(pomDoc==null) {

            return null;
        }

        // is it a maven file?

        Element top = pomDoc.getDocumentElement();
        top.normalize();
        if (!top.getNodeName().equals("project")) {
            return null;
        }

        // looks like we have a xml file with a project tag
        // read any properties
        Element properties=getSingleElement(top,"properties");

        RavenPOMImpl rp = new RavenPOMImpl(pom);
        rp.properties=loadProps(rp,properties);

        // load coordinates

        // do we have a parent?
        // if so then it provides properties, profiles and
        // default values for coords.

        RavenPOM parentPom=null;

        Element parent=getSingleElement(top,"parent");
        if(parent!=null) {

            RavenCoordinate pdep=getCoords(rp.properties,parent);

            // is there a relative ref for the parent?
            Element parentRef=getSingleElement(parent,"relativePath");
            if(parentRef!=null) {
                String parentFileLoc=parentRef.getTextContent();
                File pr=new File(pom.getParentFile(),parentFileLoc);
                parentPom=parse(pr);
            } else {
                // no parent ref.
                parentPom=store.resolvePom(pdep);
            }
            if(parentPom==null) parentPom=store.resolvePom(pdep);
            if(parentPom==null) throw new RuntimeException("cannot locate parent ["+pdep+"] pref="+parentRef);
            rp.parent=parentPom;
            // add parent coords as parameters to this pom
            rp.properties.addProperty(new RavenPropertyImpl("parent.artifactId",pdep.artifactId(),rp));
            rp.properties.addProperty(new RavenPropertyImpl("parent.groupId",pdep.groupId(),rp));
            rp.properties.addProperty(new RavenPropertyImpl("parent.version",pdep.version(),rp));
            // link properties together
            rp.properties.parent(rp.parent.properties());
        }
        rp.coordinates=getCoords(rp.properties,top);



        // load dependencies
        Element dependencies=getSingleElement(top,"dependencies");
        if(dependencies!=null) {
            List<Element> deps=getKids(dependencies,"dependency");
            for(Element e:deps) {
                RavenCoordinate rc=getCoords(rp.properties,e);
                RavenDependency rd=store.resolveDependency(rc);
                rp.dependencies.add(rd);
            }
        }

        return rp;


    }

    private static class RavenPropertyImpl implements RavenProperty {
        private final String key;
        private final String value;
        private final RavenPOM owner;

        public RavenPropertyImpl(String key, String value, RavenPOM owner) {
            this.key = key;
            this.value = value;
            this.owner = owner;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String value() {
            return value;
        }

        @Override
        public RavenPOM source() {
            return owner;
        }

        public String toString() {
            return key()+"="+value+"@"+source();
        }
    }


}
