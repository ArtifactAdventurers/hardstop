package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.POMLoader;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenDependencySet;
import dev.gruff.hardstop.api.RavenPOM;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRavenPOMLoader {

    @Test
    public void testSimpleCoordinatesNoParent() {

        File pom=new File("target/test-classes/p1/pom.xml");
        RavenPOM rp= POMLoader.loader()
                .load(pom);

        RavenCoordinate rc=rp.coordinates();

        assertEquals("P1",rc.artifactId());
        assertEquals("testo",rc.groupId());
        assertEquals("V1",rc.version());

    }
@Test
    public void testSimpleDepsNoParent() {

        File pom=new File("target/test-classes/p1/pom.xml");
        RavenPOM rp= POMLoader.loader()
                .load(pom);

        RavenDependencySet rds=rp.dependencies();

        assertEquals(1,rds.size());

        rds.forEach( rd -> {
            RavenCoordinate rc=rd.coordinates();
            assertEquals("junit-jupiter-engine",rc.artifactId());
        });


    }

    @Test
    public void testSimpleDepsWithParent() {

        File pom=new File("target/test-classes/p1/p2/pom.xml");
        RavenPOM rp= POMLoader.loader()
                .projectRoot(new File("target/test-classes"))

                .load(pom);

       RavenPOM parent=rp.parent();

       RavenCoordinate coods=parent.coordinates();

       assertEquals("testo",coods.groupId());



    }
}
