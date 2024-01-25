package tests;


import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;
import org.junit.jupiter.api.Test;
import testcode.InterfaceExample9;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInterface9 {

        @Test
        public void testInterface() throws IOException {

            HSClass z= ComponentLoader.readClass(InterfaceExample9.class);
            assertEquals("9", JavaVersionInspector.version(z));
            assertTrue(z.isInterface());
            assertTrue(z.isPublic());

        }
}
