package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.core.impl.ClassFileReaderImpl;
import dev.gruff.hardstop.core.impl.HSClassImpl;
import dev.gruff.hardstop.core.impl.HSComponentImpl;
import dev.gruff.hardstop.core.internal.ParseException;
import dev.gruff.hardstop.api.RavenComponent;
import dev.gruff.hardstop.api.RavenClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.jar.JarFile;

public class ComponentLoader {


    private final Set<Path> fileRoots = new TreeSet<>();
    private List<Predicate<File>> fileFilters = new LinkedList<>();
    private List<Predicate<String>> packageFilters = new LinkedList<>();

    private List<Predicate<String>> fileNameFilters = new LinkedList<>();
    private boolean verbose=false;
    private boolean versionInfoOnly=false;
    public ComponentLoader() {

    }


    public ComponentLoader recordCodeAttribute(boolean b) {
        return this;
    }

    public ComponentLoader recordVersionInfoOnly(boolean b) {
        return this;
    }

    public ComponentLoader ignoreErrors(boolean b) {
        return this;
    }

    public ComponentLoader recordErrors() {
        return this;
    }

    public ComponentLoader addRoot(File root) {
        fileRoots.add(root.toPath());
        return this;
    }

    public ComponentLoader addRoot(Path root) {
        fileRoots.add(root);
        return this;
    }

    public ComponentLoader filterFile(Predicate<File> pe) {
        fileFilters.add(pe);
        return this;
    }

    public ComponentLoader filterPackage(Predicate<String> pe) {
        packageFilters.add(pe);
        return this;
    }

    public ComponentLoader includeContainers(boolean t) {
        return this;
    }

    public RavenComponent build() throws IOException {


        HSComponentImpl m = new HSComponentImpl();
        fileRoots.stream().parallel().forEach(r -> {
            try {
                handleRoot(m,r);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return m;

    }

    private void handleZip(HSComponentImpl m, Path jar) throws IOException {

        // do we have a side POM?
        File jarFile=jar.toFile();
        String fname=jarFile.getName();
        String prefix=fname.substring(0,fname.lastIndexOf('.'));
        File parent=jarFile.getParentFile();
        File pomFile=new File(parent,prefix+".pom");
    /*
        if(pomFile.exists()) {
            vm=readPOM(pomFile);
        } else {
            vm = null;
        }
*/
        try (JarFile jf = new JarFile(jar.toFile())) {

            String root = jar.toFile().getAbsolutePath();


            jf.stream().forEach(je -> {
                String name = je.getName();
                if (name.equalsIgnoreCase("module-info.class")) return;
                if (name.endsWith(".class") && allowed(name)) {

                    try (InputStream is = jf.getInputStream(je)) {
                        report(0,"reading class "+name+" from "+root);
                        HSClassImpl rrc= new ClassFileReaderImpl(false).parse(is);
                        m.addContainerClass(jar,name,rrc);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            });
        }
    }

    private boolean allowed(String name) {
        for(Predicate<String> p:fileNameFilters) {
            if(!p.test(name)) return false;
        }
        return true;
    }


    private void handleFileSystemClass(HSComponentImpl m, Path root, Path file) {
        report(0,"classfile "+file);
        String fileName=file.toFile().getName();
        try (InputStream is = Files.newInputStream(file)) {

            report(0,"reading class "+fileName+" from "+root);
            HSClassImpl rrc=new ClassFileReaderImpl(false).parse(is);
            m.addFileSystemClass(root,file,rrc);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleRoot(HSComponentImpl m, Path r) throws IOException {

        report(0,"root :"+r);

        Files.walkFileTree(r, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = file.toFile().getName().toLowerCase();
                int lastDot = name.lastIndexOf('.');
                if (lastDot >= 0) {
                    String type = name.substring(lastDot + 1);

                    switch (type) {

                        case "class":
                            handleFileSystemClass(m,r,file);
                            break;
                        case "jar":
                        case "zip":
                            handleZip(m,file);
                            break;

                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return null;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }


    private void report(int i, String s) {
        if(verbose) System.out.println(s);
    }

    public ComponentLoader verbose(boolean b) {
        this.verbose=b;
        return this;
    }

    public ComponentLoader filterFilename(Predicate<String> pe) {
        fileNameFilters.add(pe);
        return this;
    }

    public ComponentLoader versionInfoOnly(boolean b) {
        this.versionInfoOnly=b;
        return this;
    }

    public static RavenClass readClass(InputStream is) throws IOException {
        return new ClassFileReaderImpl(false).parse(is);
    }


    public static RavenClass readClass(Class cl) throws IOException {
        String rc=cl.getCanonicalName();
        rc=rc.replace(".","/");
        rc="/"+rc+".class";
        InputStream is=cl.getResourceAsStream(rc);
        if(is==null) throw new ParseException("cannot locate resource ["+rc+"]");
        return new ClassFileReaderImpl(false).parse(is);
    }
}
