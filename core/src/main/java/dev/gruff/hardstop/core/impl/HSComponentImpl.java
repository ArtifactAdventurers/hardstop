package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.core.RavenStats;
import dev.gruff.hardstop.core.internal.RavenLiteralStore;
import dev.gruff.hardstop.api.HSPackage;
import dev.gruff.hardstop.api.RavenComponent;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenClass;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

public class HSComponentImpl implements RavenComponent {


    private RavenLiteralStore literals=new RavenLiteralStore();

    private final Map<Path, HSContainerImpl> containers=new TreeMap<>();
    private final Map<String,Set<RavenPackageImpl>> packages=new TreeMap<>();
    private final Map<String,Set<HSClassImpl>> classes=new TreeMap<>();

    private RavenCoordinate coords=null;

    @Override
    public RavenCoordinate coordinates() {
        return coords;
    }

    @Override
    public int containerCount() {
        return containers.size();
    }

    @Override
    public int packageCount() {
        return packages.size();
    }

    @Override
    public Set<String> packageNames() {
        return Collections.unmodifiableSet(packages.keySet());
    }

    @Override
    public Set<HSPackage> getPackage(String packageName) {
        return Collections.unmodifiableSet(packages.get(packageName));
    }

    @Override
    public Set<String> classNames() {
        return Collections.unmodifiableSet(classes.keySet());
    }

    @Override
    public Set<RavenClass> classInstances(String className) {
        Set<HSClassImpl> rc=classes.get(className);
        if(rc==null) rc=new HashSet<>();
        return Collections.unmodifiableSet(rc);
    }

    @Override
    public RavenClass classInstance(String s) {
        Set<RavenClass> rc=classInstances(s);
        if(rc==null) return null;
        return rc.iterator().next();
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

        Set<HSClassImpl> s=classes.computeIfAbsent(className, f-> new HashSet<>());
        s.add(rci);

        RavenPackageImpl rpi=ci.addClass(packageName,rci);
        Set<RavenPackageImpl> pl=packages.computeIfAbsent(packageName,f-> new HashSet<>());
        pl.add(rpi);

        stats.update(rci);

        return rci;

    }

    public HSClassImpl addContainerClass(Path jar, String name, HSClassImpl rrc) {
        return addClass(jar,rrc);
    }

    public RavenClass addClass(InputStream is) throws IOException {
        HSClassImpl rrc= new ClassFileReaderImpl(false).parse(is);
        return addClass(null,  rrc);
    }
}
