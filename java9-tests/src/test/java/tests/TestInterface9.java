package tests;


import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;
import testcode.InterfaceExample9;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInterface9 {

        @Test
        public void testInterface() throws IOException {

            HSClass z= ClassReader.readClass(TestHelper.getFile(InterfaceExample9.class));
            assertEquals("9.0.0", JavaVersionInspector.version(z).toString());
            assertTrue(z.isInterface());
            assertTrue(z.isPublic());

        }
}
