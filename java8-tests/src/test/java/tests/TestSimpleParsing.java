package tests;


import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;
import testdata.SimplestClass8;
import testdata.SimplestEnum8;
import testdata.SimplestInterface8;


import dev.gruff.hardstop.api.HSClass;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimpleParsing {

    @Test
    public void testParseSimpleClass() throws IOException {

        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestClass8.class));

        assertEquals("8.0.0", JavaVersionInspector.version(z).toString());
        assertTrue(z.isClass());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }

    @Test
    public void testParseSimpleInterface() throws IOException {

        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestInterface8.class));
       assertEquals("8.0.0", JavaVersionInspector.version(z).toString());
        assertTrue(z.isInterface());

    }


    @Test
    public void testParseSimpleEnum() throws IOException {
        HSClass z= ClassReader.readClass(TestHelper.getFile(SimplestEnum8.class));

        assertEquals("8.0.0", JavaVersionInspector.version(z).toString());
        assertTrue(z.isEnum());
    }
}
