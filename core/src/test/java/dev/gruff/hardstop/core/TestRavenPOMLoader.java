package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSComponentMetaSet;
import dev.gruff.hardstop.core.builder.POMLoader;
import dev.gruff.hardstop.api.HSCoordinate;
import dev.gruff.hardstop.api.HSComponentSet;
import dev.gruff.hardstop.api.HSComponentMeta;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRavenPOMLoader {

    @Test
    public void testSimpleCoordinatesNoParent() {

        File pom=new File("target/test-classes/p1/pom.xml");
        HSComponentMeta rp= POMLoader.loader()
                .excludeLocalCache(true)
                .excludeRemoteCache(true)
                .load(pom);

        HSCoordinate rc=rp.coordinates();

        assertEquals("P1",rc.artifactId());
        assertEquals("testo",rc.groupId());
        assertEquals("V1",rc.version());

    }
@Test
    public void testSimpleDepsNoParent() {

        File pom=new File("target/test-classes/p1/pom.xml");
        HSComponentMeta rp= POMLoader.loader()
                .excludeLocalCache(true)
                .excludeRemoteCache(true)
              //  .trace(true)
                .load(pom);

        HSComponentMetaSet rds=rp.dependencies();

        assertEquals(1,rds.size());

         HSCoordinate rc=rds.only().coordinates();
        assertEquals("junit-jupiter-engine",rc.artifactId());



    }

    @Test
    public void testSimpleDepsWithParent() {

        File pom=new File("target/test-classes/p1/p2/pom.xml");
        HSComponentMeta rp= POMLoader.loader()
                .excludeLocalCache(true)
                .excludeRemoteCache(true)
                .trace(true)
                .projectRoot(new File("target/test-classes"))

                .load(pom);

       HSComponentMeta parent=rp.parent();

       HSCoordinate coods=parent.coordinates();

       assertEquals("testo",coods.groupId());



    }
}
