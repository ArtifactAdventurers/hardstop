package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.core.impl.RavenPOMImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class POMReader {

    private  DocumentBuilder db;
    public POMReader() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       db = dbf.newDocumentBuilder();

    }

    public RavenPOMImpl read(File f) throws IOException, SAXException {
        Document doc = db.parse(f);
        Element top = doc.getDocumentElement();


        top.normalize();

        if (top.getNodeName().equals("project")) {
            RavenPOMImpl pom=new RavenPOMImpl(f);
            loadParent(pom,top);

        } else {
            throw new RuntimeException(f.getAbsolutePath() + " is not a POM file - missing project tag");
        }

        return null;
    }

    private void loadParent(RavenPOMImpl pom, Element project) {

        Element parent=getSingleElement(project,"parent");
        if(parent!=null) {




        }


    }

    private Element getSingleElement(Element owner, String childTag) {

        List<Element> kids=getKids(owner,childTag);
        if(kids.size()>1) throw new RuntimeException("more than one "+childTag+" as child of "+owner.getNodeName());
        if(kids.isEmpty()) return null;
        return kids.get(0);
    }

    private List<Element> getKids(Element owner, String childTag) {
        List<Element> kids=new LinkedList<>();
        NodeList nl=owner.getChildNodes();
        for(int i=0;i<nl.getLength();i++) {
            Node n=nl.item(i);
            if(n instanceof Element) {
                Element e= (Element) n;
                if(e.getNodeName().equals(childTag)) kids.add(e);
            }
        }
        return kids;
    }

}
