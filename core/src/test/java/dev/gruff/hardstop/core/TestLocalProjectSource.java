package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSComponentMetaSet;
import dev.gruff.hardstop.core.builder.MavenProjectSource;
import dev.gruff.hardstop.core.impl.HSCoordinateImpl;
import dev.gruff.hardstop.core.impl.HSStoreImpl;
import dev.gruff.hardstop.api.HSCoordinate;
import dev.gruff.hardstop.api.HSComponentMeta;
import dev.gruff.hardstop.api.HSStore;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestLocalProjectSource {


    @Test
    public void testFindNestedPOMs() {
        HSStoreImpl store=new HSStoreImpl();
        store.addSource(new MavenProjectSource(new File("target/test-classes")));

        HSCoordinate rc=new HSCoordinateImpl("testo","P2","V2");

        assertNotNull(store.meta(rc).only());

        rc=new HSCoordinateImpl("testo","P3","V3mak");
        HSComponentMetaSet rd=store.meta(rc);
        assertNotNull(rd.only());

        rc=new HSCoordinateImpl("testo","P1","V1");
        assertNotNull(store.meta(rc).only());

    }

    @Test
    public void test2NotThereNotFound() {
        HSStoreImpl store=new HSStoreImpl();
        store.addSource(new MavenProjectSource(new File("target/test-classes")));
        HSCoordinate rc=new HSCoordinateImpl("G","A","V");
        assertNull(store.meta(rc).only());
    }
}
