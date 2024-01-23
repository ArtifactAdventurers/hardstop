package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.HSPackage;
import dev.gruff.hardstop.api.RavenContainer;
import dev.gruff.hardstop.api.RavenClass;


import java.util.*;

public class RavenPackageImpl implements HSPackage {

    private final Map<String, HSClassImpl> classes=new TreeMap<>();

    private String name;

    private HSContainerImpl container;


    RavenPackageImpl(HSContainerImpl container, String name) {
        this.name = name;
        this.container=container;

    }


    @Override
    public RavenContainer container() {
        return container;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int classCount() {
        return classes.size();
    }

    @Override
    public Collection<RavenClass> classes() {
        return Collections.unmodifiableCollection(classes.values());
    }

    @Override
    public Set<String> classNames() {
        return Collections.unmodifiableSet(classes.keySet());
    }


    public void addClass(HSClassImpl clazz) {

        classes.put(clazz.className(),clazz);

    }
}
