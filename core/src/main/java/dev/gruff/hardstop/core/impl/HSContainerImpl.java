package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenContainer;


import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class HSContainerImpl implements RavenContainer {


    private final Map<String, RavenPackageImpl> packages=new TreeMap<>();
    private Path root;

    HSContainerImpl(Path ref) {


        if (ref == null) throw new RuntimeException("missing container ref");
        this.root = ref;

    }


    public String toString() {
        return root.toString();
    }


    public RavenPackageImpl addClass(String packageName, HSClassImpl rci) {
        RavenPackageImpl r= packages.computeIfAbsent(packageName, f-> new RavenPackageImpl(this,packageName));
        r.addClass(rci);
        return r;
    }


}
