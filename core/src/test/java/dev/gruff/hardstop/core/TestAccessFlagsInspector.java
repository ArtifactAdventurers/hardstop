package dev.gruff.hardstop.core;



import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.clazz.AccessFlagInspector;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAccessFlagsInspector {

    @Test
    public void testClassFlags1() throws IOException {

        HSClass rc= ClassReader.readClass(TestHelper.getFile(PublicClass.class));
            Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
            assertTrue(f.contains(AccessFlagInspector.ClassFlag.ACC_PUBLIC));

    }

    @Test
    public void testClassFlags2() throws IOException {

        HSClass rc= ClassReader.readClass(TestHelper.getFile(FinalClass.class));
        Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
        assertTrue(f.contains(AccessFlagInspector.ClassFlag.ACC_FINAL));

    }


    @Test
    public void testClassFlags3() throws IOException {
        HSClass rc= ClassReader.readClass(TestHelper.getFile(AbstractClass.class));
        Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
        assertTrue(f.contains(AccessFlagInspector.ClassFlag.ACC_ABSTRACT));

    }

    abstract class AbstractClass {

    }
    class NoClass {

    }
    private class PrivateClass {

    }
   final class FinalClass {

    }
    public class PublicClass {

    }
    protected class ProtectedClass {

    }

    @Test
    public void testClassFlagsPublic() throws IOException {

        HSClass rc= ClassReader.readClass(TestHelper.getFile(PrivateClass.class));
        Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
        System.out.println(f);
        assertFalse(f.contains(AccessFlagInspector.ClassFlag.ACC_PUBLIC));

    }

    @Test
    public void testClassFlagsProtected() throws IOException {

        HSClass rc= ClassReader.readClass(TestHelper.getFile(ProtectedClass.class));
        Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
        assertTrue(rc.isInnerClass());
        assertTrue(f.contains(AccessFlagInspector.ClassFlag.ACC_PUBLIC));

    }


    @Test
    public void testClassFlags6() throws IOException {

        HSClass rc= ClassReader.readClass(TestHelper.getFile(NoClass.class));
        Set<AccessFlagInspector.ClassFlag> f=rc.accessFlags();
        System.out.println(f);
        assertFalse(f.contains(AccessFlagInspector.ClassFlag.ACC_PUBLIC));

    }
}
