package tests;


import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ComponentLoader;
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

        HSClass z= ComponentLoader.readClass(SimplestClass8.class);
        assertEquals("8", JavaVersionInspector.version(z));
        assertTrue(z.isClass());
        assertTrue(z.isPublic());
        assertFalse(z.isFinal());

    }

    @Test
    public void testParseSimpleInterface() throws IOException {
        HSClass z= ComponentLoader.readClass(SimplestInterface8.class);
       assertEquals("8", JavaVersionInspector.version(z));
        assertTrue(z.isInterface());

    }


    @Test
    public void testParseSimpleEnum() throws IOException {
        HSClass z= ComponentLoader.readClass(SimplestEnum8.class);
        assertEquals("8", JavaVersionInspector.version(z));
        assertTrue(z.isEnum());
    }
}
