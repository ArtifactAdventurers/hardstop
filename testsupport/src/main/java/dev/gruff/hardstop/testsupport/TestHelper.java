package dev.gruff.hardstop.testsupport;

import java.io.File;

public class TestHelper {

    public static File getFile(Class c) {

        String name=c.getCanonicalName();

        name=name.replace(".","/");
        name=name+".class";

        File f=new File("target/test-classes");
        f=new File(f,name);
        if(f.exists()) return f;

        // not found look for inner class .
        name=c.getCanonicalName();
        int ld=name.lastIndexOf(".");
        char[] parts=name.toCharArray();
        parts[ld]='$';
        name=new String(parts);
        name=name.replace(".","/")+".class";

        f=new File("target/test-classes");
        f=new File(f,name);

        return f;


    }

}
