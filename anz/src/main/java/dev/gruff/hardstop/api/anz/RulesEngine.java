package dev.gruff.hardstop.api.anz;

import dev.gruff.hardstop.api.anz.rules.upward.CheckFinalState;
import dev.gruff.hardstop.api.anz.rules.upward.CheckType;
import dev.gruff.hardstop.api.anz.rules.upward.CompilerVersion;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class RulesEngine {

    private static Class<RuleTest> rules[]=new Class[]{
            CheckFinalState.class, CheckType.class, CompilerVersion.class};

    public RulesEngine() {

    }
    public void runTests() {

        for(Class c:rules) {
           if(c.isAnnotationPresent(Rule.class)) {
               Annotation a = c.getAnnotation(Rule.class);
               Rule r= (Rule) a;
               System.out.println(r.id()+" == "+r.title());
                for(Method m:c.getMethods()) {
                    if(m.isAnnotationPresent(Rule.class)) {
                        Annotation am=m.getAnnotation(Rule.class);
                        Rule rm= (Rule) am;
                        System.out.println(r.id()+" / "+rm.id()+" "+rm.title());
                    }
                }
           }

        }
    }


    public static void main(String args[]) {
        RulesEngine re=new RulesEngine();
        re.runTests();
    }
}
