package tests;

import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import org.junit.jupiter.api.Test;
import testcode.SimpleRecord16;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecord16 {


    @Test
    public void testStructure() throws IOException {

        HSClass z= ComponentLoader.readClass(SimpleRecord16.class);
            assertEquals("16", JavaVersionInspector.version(z));
            assertFalse(z.isInterface());
            assertTrue(z.isPublic());

    }
}
