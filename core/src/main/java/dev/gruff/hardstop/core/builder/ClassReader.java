package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.core.impl.ClassFileReaderImpl;
import dev.gruff.hardstop.core.internal.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ClassReader {


    public static HSClass readClass(InputStream in) throws IOException {
        try (InputStream is = in) {
            return new ClassFileReaderImpl(false).parse(is);
        }

    }


    public static HSClass readClass(File f) throws IOException{
        if(!f.exists()) throw new FileNotFoundException(f.getAbsolutePath() +" cannot be found");
        return readClass(Files.newInputStream(f.toPath()));
    }


    public static HSClass readClass(Class cl) throws IOException {
        String rc=cl.getCanonicalName();
        rc=rc.replace(".","/");
        rc="/"+rc+".class";
        InputStream is=cl.getResourceAsStream(rc);
        if(is==null) throw new ParseException("cannot locate resource ["+rc+"]");
        return readClass(is);
    }
}
