package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.*;
import dev.gruff.hardstop.core.RavenStats;
import dev.gruff.hardstop.core.internal.RavenLiteralStore;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

public class HSComponentImpl implements HSComponent {


    public HSComponentImpl(HSCoordinate coord,HSArtifactRef ref) {

        coords=coord;
        this.ref=ref;
    }

    public HSComponentImpl() {

    }

    private HSArtifactRef ref;
    private RavenLiteralStore literals=new RavenLiteralStore();

    private final Map<Path, HSContainerImpl> containers=new TreeMap<>();
    private final Map<String,Set<HSPackageImpl>> packages=new TreeMap<>();
    private final Map<String,HSClassSetImpl> classes=new TreeMap<>();

    private HSCoordinate coords=null;

    @Override
    public HSCoordinate coordinates() {
        return coords;
    }

    @Override
    public HSArtifactRef artifactRef() {
        return ref;
    }

    @Override
    public Set<String> classNames() {
        return Collections.unmodifiableSet(classes.keySet());
    }

    @Override
    public HSClassSet classByName(String className) {
        return classes.get(className);
    }



    private RavenStats stats=new RavenStats();

    public RavenLiteralStore literalStore() {
        return literals;
    }


    public void addFileSystemClass(Path root,Path location, HSClassImpl rrc) {

        HSClassImpl rci=addClass(root,  rrc);
        // check if
    }

    /*
    Converts the raw class file into structured data
     */
    private synchronized HSClassImpl addClass(Path path, HSClassImpl rci) {

        // main container ..
        HSContainerImpl ci=containers.computeIfAbsent(path, f -> new HSContainerImpl(path));
        rci.container(ci);
       String className=rci.className();
        String packageName= rci.packageName();

        HSClassSetImpl s=classes.computeIfAbsent(className, f-> new HSClassSetImpl());
        s.add(rci);

        HSPackageImpl rpi=ci.addClass(packageName,rci);
        Set<HSPackageImpl> pl=packages.computeIfAbsent(packageName, f-> new HashSet<>());
        pl.add(rpi);

        stats.update(rci);

        return rci;

    }

    public HSClassImpl addContainerClass(Path jar, String name, HSClassImpl rrc) {
        return addClass(jar,rrc);
    }

    public HSClass addClass(InputStream is) throws IOException {
        HSClassImpl rrc= new ClassFileReaderImpl(false).parse(is);
        return addClass(null,  rrc);
    }
}
