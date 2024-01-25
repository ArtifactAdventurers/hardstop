package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.HSComponent;
import dev.gruff.hardstop.api.clazz.AccessFlagInspector;
import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.core.internal.Utils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class TestJarComparitor {

    @Test
    public void testJars() throws IOException {

    }

    private void classCompare(HSClass r1, HSClass r2) {

        AccessFlagInspector.ClassFlagDiff fd=AccessFlagInspector.compare(r1.accessFlags(),r2.accessFlags());

        if(!fd.noChanges()) {
            for(AccessFlagInspector.ClassFlag cf:fd.additions) {
                System.out.println("+"+cf.name());
            }
            for(AccessFlagInspector.ClassFlag cf:fd.removals) {
                System.out.println("-"+cf.name());
            }
        }



        if(r1.hasFields()) {
            compareFields(r1, r2,"-");
        }
        if(r2.hasFields()) {
            compareFields(r2,r1,"+");
        }
        if(r1.hasMethods()) {
            compareMethods(r1, r2,"-");
        }
        if(r2.hasMethods()) {
            compareMethods(r2,r1,"+");
        }

    }

    private static void compareFields(HSClass r1, HSClass r2, String diff) {
        for (HSClass.Field field : r1.fields()) {
            String ref = field.reference();
            if (r2.hasField(ref)) {
                HSClass.Field field2 = r2.field(ref);
                int fa1 = field.accessFlags();
                int fa2 = field2.accessFlags();
                if (fa1 != fa2) {
                    System.out.println(Utils.fieldAccessFlags(fa1) + "-->" + Utils.fieldAccessFlags(fa2));
                }
            } else {
                System.out.println(diff+" field " + ref);
            }
        }
    }

    private static void compareMethods(HSClass r1, HSClass r2, String diff) {
        for (HSClass.Method method : r1.methods()) {
            String ref = method.reference();
            if (r2.hasMethod(ref)) {
                HSClass.Method method2 = r2.method(ref);
                int fa1 =  method.accessFlags();
                int fa2 = method2.accessFlags();
                if (fa1 != fa2) {
                    System.out.println("method "+Utils.methodAccessFlags(fa1) + "-->" + Utils.methodAccessFlags(fa2));
                }
            } else {
                System.out.println(diff+" method " + ref);
            }
        }
    }
}
