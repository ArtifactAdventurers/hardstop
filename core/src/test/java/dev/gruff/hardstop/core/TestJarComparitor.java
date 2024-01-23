package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.RavenClass;
import dev.gruff.hardstop.api.RavenComponent;
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
        File f=new File(System.getProperty("user.home"));
        f=new File(f,".m2");
        File f1=new File(f,"repository/junit/junit/3.8.1/junit-3.8.1.jar");
        File f2=new File(f,"repository/junit/junit/4.4/junit-4.4.jar");

        ComponentLoader mb=new ComponentLoader();
        RavenComponent first=mb.addRoot(f1).build();

        Set<String> classNames1=first.classNames();
        System.out.println(classNames1.size());

        RavenComponent second=mb.addRoot(f2).build();
        Set<String> classNames2=second.classNames();
        System.out.println(classNames2.size());

       Set<String> common=new TreeSet<>();
       classNames1.forEach(s ->{ if( classNames2.contains(s)) common.add(s);});
       System.out.println("common "+common.size());

       int unchanged=0;
       int changed=0;

       for(String s:common) {
           RavenClass r1=first.classInstance(s);
           RavenClass r2=second.classInstance(s);
           if(!r1.digest().equals(r2.digest())) {
       changed++;
            System.out.println(s);
            classCompare(r1,r2);
           } else {
               unchanged++;
           }
       }

       System.out.println("unchanged "+unchanged);
        System.out.println("changed "+changed);
    }

    private void classCompare(RavenClass r1, RavenClass r2) {

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

    private static void compareFields(RavenClass r1, RavenClass r2,String diff) {
        for (RavenClass.Field field : r1.fields()) {
            String ref = field.reference();
            if (r2.hasField(ref)) {
                RavenClass.Field field2 = r2.field(ref);
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

    private static void compareMethods(RavenClass r1, RavenClass r2,String diff) {
        for (RavenClass.Method method : r1.methods()) {
            String ref = method.reference();
            if (r2.hasMethod(ref)) {
                RavenClass.Method method2 = r2.method(ref);
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
