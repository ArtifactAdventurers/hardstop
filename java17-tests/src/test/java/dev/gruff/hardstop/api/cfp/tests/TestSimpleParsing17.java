package dev.gruff.hardstop.api.cfp.tests;



import dev.gruff.hardstop.api.cfp.testdata.SimplestClass17;
import dev.gruff.hardstop.api.cfp.testdata.SimplestEnum17;
import dev.gruff.hardstop.api.cfp.testdata.SimplestInterface17;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimpleParsing17 {

    @Test
    public void testParseSimpleClass() throws IOException {
       HSClass z = ComponentLoader.readClass(SimplestClass17.class);
        assertEquals("17", z.compilerVersion());
        assertTrue(z.isClass());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }

    @Test
    public void testParseSimpleInterface() throws IOException {

        HSClass z = ComponentLoader.readClass(SimplestInterface17.class);
        assertEquals("17", z.compilerVersion());
        assertTrue(z.isInterface());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }


    @Test
    public void testParseSimpleEnum() throws IOException {
        HSClass z = ComponentLoader.readClass(SimplestEnum17.class);
          assertEquals("17", z.compilerVersion());
          assertTrue(z.isEnum());
          assertTrue(z.isPublic());
          assertTrue(z.isFinal());
          assertEquals(SimplestEnum17.class.getCanonicalName().replace('.','/'),z.className());
          assertEquals("java/lang/Enum",z.superClassName());
      }

}
