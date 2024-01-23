package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.MavenProjectSource;
import dev.gruff.hardstop.core.impl.HSCoordinateImpl;
import dev.gruff.hardstop.core.impl.RavenStoreImpl;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenPOM;
import dev.gruff.hardstop.api.HSStore;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestLocalProjectSource {


    @Test
    public void testFindNestedPOMs() {
        HSStore store=new RavenStoreImpl();
        store.addSource(new MavenProjectSource(new File("target/test-classes")));

        RavenCoordinate rc=new HSCoordinateImpl("P2","testo","V2");

        assertNotNull(store.resolvePom(rc));

        rc=new HSCoordinateImpl("P3","testo","V3mak");
        RavenPOM rd=store.resolvePom(rc);
        assertNotNull(rd);

        rc=new HSCoordinateImpl("P1","testo","V1");
        assertNotNull(store.resolvePom(rc));

    }

    @Test
    public void test2NotThereNotFound() {
        HSStore store=new RavenStoreImpl();
        store.addSource(new MavenProjectSource(new File("target/test-classes")));
        RavenCoordinate rc=new HSCoordinateImpl("A","G","V");
        assertNull(store.resolvePom(rc));
    }
}
