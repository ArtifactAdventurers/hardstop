package dev.gruff.hardstop.api.anz;


import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.anz.rules.upward.CheckFinalState;
import dev.gruff.hardstop.api.anz.rules.upward.CompilerVersion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;


public class APIComparision {

    private static Object[] rules=new Object[]{
            new CompilerVersion(),
            new CheckFinalState()
    };

    public APIComparision() {

            List<Object> roots=new LinkedList<>();

    for(Object o:rules) {
        List<Annotation> annos=new LinkedList<>();
            if(o.getClass().isAnnotationPresent(Rule.class)) {
                Rule r=o.getClass().getAnnotation(Rule.class);
                System.out.println(o.getClass().getCanonicalName()+" "+r.id()+" "+r.title());
                for(Method m:o.getClass().getMethods()) {
                    if(m.isAnnotationPresent(Rule.class)) {
                        Rule mr=m.getAnnotation(Rule.class);
                        System.out.println(m.getName()+" "+r.id()+"/"+mr.id()+" "+mr.title());
                    }
                }
            }
        }


    }

    /* Compares two classes for API differences
          produces a list of results

          BREAK - API changes mean existing code will fail
          UPGRADE - Class changes mean runtime upgrades needed (new JVM version)
          DOWNGRADE - Class changes mean runtime downgrade possible (older JVM version)
     */

    public APIComparisionReport compare(HSClass a, HSClass b) {

        APIComparisionReport r=new APIComparisionReport(a,b);




      // check interfaces / superclass

      // check fields

      // check methods


    return r;

    }



    abstract static class Result {

        private String desc;


    }

    static class Downgrade extends Result {

    }

    static class Upgrade extends Result {

    }

    static class Warning extends Result {

    }


    static class Break extends Result {

    }
}
