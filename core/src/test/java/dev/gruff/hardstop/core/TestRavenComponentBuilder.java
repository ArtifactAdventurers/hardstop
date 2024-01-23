package dev.gruff.hardstop.core;


import dev.gruff.hardstop.api.RavenComponent;
import dev.gruff.hardstop.core.builder.ComponentBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestRavenComponentBuilder {

    @Test
   public void loadClass() {

        File pom=new File("target/test-classes/p1/pom.xml");
        RavenComponent r=
                ComponentBuilder.builder().read(pom);


    }
}
