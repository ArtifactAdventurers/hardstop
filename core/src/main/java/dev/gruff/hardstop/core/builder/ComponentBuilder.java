package dev.gruff.hardstop.core.builder;


import dev.gruff.hardstop.core.impl.HSPOMImpl;
import dev.gruff.hardstop.core.internal.POMReader;
import dev.gruff.hardstop.api.HSComponent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ComponentBuilder {


    public static ComponentBuilder builder() {
        return new ComponentBuilder();
    }


    private ComponentBuilder() {

    }

    /**
     * Create a RavenComponent from a Maven pom.xml file
     * @param pom
     * @return
     */
    public HSComponent read(File pom) {

        if(!pom.exists()) throw new RuntimeException("pom is null");
        if(!pom.isFile()) throw new RuntimeException(pom.getAbsolutePath()+" is not a file");

        POMReader pr= null;
        try {
            pr = new POMReader();
           HSPOMImpl p=pr.read(pom);
            return null;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
