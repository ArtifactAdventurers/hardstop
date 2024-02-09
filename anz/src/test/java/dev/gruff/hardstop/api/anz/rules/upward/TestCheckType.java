package dev.gruff.hardstop.api.anz.rules.upward;
import dev.gruff.hardstop.api.anz.RuleTest;
import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCheckType {

    @Test
    public void testCompareClassClass() throws IOException {
        ClassReader cr=new ClassReader();
       HSClass c1= cr.readClass(TestHelper.getFile(C1.class));
        HSClass c2= cr.readClass(TestHelper.getFile(C2.class));
       CheckType r=new CheckType();
        assertTrue(r.check(c1,c2));

    }

    private static class C1 {

    }
    private static class C2 {

    }

}
