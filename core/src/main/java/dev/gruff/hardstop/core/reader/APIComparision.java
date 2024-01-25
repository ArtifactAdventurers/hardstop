package dev.gruff.hardstop.core.reader;


import dev.gruff.hardstop.api.HSClass;

public class APIComparision {

    public APIComparision() {


    }

    public void compare(HSClass a, HSClass b) {

        // compare versions ...
      String v1= a.compilerVersion();
      String v2= b.compilerVersion();

      System.out.println("Version A Compiled to "+v1);
      System.out.println("Version B Compiled to "+v2);
      System.out.println("Version Match "+v1.compareTo(v2));

      if(v2.compareTo(v1)>0) System.out.println("Compiler upgraded");

      // access changed?

      if(a.accessFlags()!=b.accessFlags()) {
            System.out.println("access changed");
      }

      // check interfaces / superclass

      // check fields

      // check methods




    }

}
