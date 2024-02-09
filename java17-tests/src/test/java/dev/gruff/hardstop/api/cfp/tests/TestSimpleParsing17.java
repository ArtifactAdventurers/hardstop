package dev.gruff.hardstop.api.cfp.tests;



import dev.gruff.hardstop.api.cfp.testdata.SimplestClass17;
import dev.gruff.hardstop.api.cfp.testdata.SimplestEnum17;
import dev.gruff.hardstop.api.cfp.testdata.SimplestInterface17;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimpleParsing17 {

    @Test
    public void testParseSimpleClass() throws IOException {

        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestClass17.class));
        assertEquals("17.0.0", z.compilerVersion().toString());
        assertTrue(z.isClass());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }

    @Test
    public void testParseSimpleInterface() throws IOException {


        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestInterface17.class));
        assertEquals("17.0.0", z.compilerVersion().toString());
        assertTrue(z.isInterface());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }


    @Test
    public void testParseSimpleEnum() throws IOException {

        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestEnum17.class));
          assertEquals("17.0.0", z.compilerVersion().toString());
          assertTrue(z.isEnum());
          assertTrue(z.isPublic());
          assertTrue(z.isFinal());
          assertEquals(SimplestEnum17.class.getCanonicalName().replace('.','/'),z.className());
          assertEquals("java/lang/Enum",z.superClassName());
      }

}
