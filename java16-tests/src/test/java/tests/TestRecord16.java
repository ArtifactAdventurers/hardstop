package tests;

import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;
import testcode.SimpleRecord16;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecord16 {


    @Test
    public void testStructure() throws IOException {

        HSClass z= ClassReader.readClass(TestHelper.getFile(SimpleRecord16.class));
            assertEquals("16.0.0", JavaVersionInspector.version(z).toString());
            assertFalse(z.isInterface());
            assertTrue(z.isPublic());

    }
}
