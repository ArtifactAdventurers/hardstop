package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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

    public static HSClass getClass(Class clazz) throws IOException {
        try (InputStream is = getInputStream(clazz)) {
            return ComponentLoader.readClass(is);
        }
    }
    public static InputStream getInputStream(Class clazz) throws IOException {
        File f=getFile(clazz);
        if(!f.exists()) throw new FileNotFoundException("class "+clazz.getCanonicalName()+" cannot be found in target build area");
        return Files.newInputStream(f.toPath());
    }
}
