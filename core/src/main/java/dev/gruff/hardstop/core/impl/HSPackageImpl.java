package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.HSPackage;
import dev.gruff.hardstop.api.HSContainer;
import dev.gruff.hardstop.api.HSClass;


import java.util.*;

public class HSPackageImpl implements HSPackage {

    private final Map<String, HSClassImpl> classes=new TreeMap<>();

    private String name;

    private HSContainerImpl container;


    HSPackageImpl(HSContainerImpl container, String name) {
        this.name = name;
        this.container=container;

    }


    @Override
    public HSContainer container() {
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
    public Collection<HSClass> classes() {
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
