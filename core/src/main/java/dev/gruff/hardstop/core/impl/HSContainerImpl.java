package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.HSContainer;


import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class HSContainerImpl implements HSContainer {


    private final Map<String, HSPackageImpl> packages=new TreeMap<>();
    private Path root;

    HSContainerImpl(Path ref) {


        if (ref == null) throw new RuntimeException("missing container ref");
        this.root = ref;

    }


    public String toString() {
        return root.toString();
    }


    public HSPackageImpl addClass(String packageName, HSClassImpl rci) {
        HSPackageImpl r= packages.computeIfAbsent(packageName, f-> new HSPackageImpl(this,packageName));
        r.addClass(rci);
        return r;
    }


}
