package dev.gruff.hardstop.core;


import dev.gruff.hardstop.api.SemanticVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSemanticVersion {


    @Test
    public void testCreate1() {
            SemanticVersion a1=new SemanticVersion();
            SemanticVersion a2=new SemanticVersion();

            assertEquals(a1,a2);
    }

    @Test
    public void testMajorMinor() {
        SemanticVersion a1=new SemanticVersion(0,1,0);
        SemanticVersion a2=new SemanticVersion(0,2,0);

        assertEquals(-1,a1.compareMajorMinorVersion(a2));


    }
@Test
    public void testMajorMinor2() {
        SemanticVersion a1=new SemanticVersion(0,1,1);
        SemanticVersion a2=new SemanticVersion(0,1,2);

        assertEquals(0,a1.compareMajorMinorVersion(a2));


    }
}
