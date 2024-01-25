package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSClass;

import java.util.LinkedList;
import java.util.List;

public class PublicSignatureComparitor {
    public static void compare(HSClass first, HSClass second) {

        List<String> errors=new LinkedList<>();

        // does the name differ?
        String f1n=first.className();
        String f2n=second.className();
        if(f1n.equals(f2n)==false) {
            errors.add("class names differ ["+f1n+"] / ["+f2n+"]");
        }

        // are there any missing public fields
        first.fields();

        // missing public methods
        // do methods have new signatures

        // does the class, fields or methods have changed access

        //are there new methods with

        // has the class gone abstract
    }
}
