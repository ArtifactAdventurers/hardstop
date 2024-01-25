package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.HSClassSet;

import java.util.HashSet;
import java.util.Iterator;

public class HSClassSetImpl implements HSClassSet {

    private HashSet<HSClass> clazzes=new HashSet<>();

    @Override
    public int size() {
        return clazzes.size();
    }

    @Override
    public void forEach(Object o) {

    }

    @Override
    public HSClass only() {
        return clazzes.iterator().next();
    }

    @Override
    public Iterator<HSClass> iterator() {
        return clazzes.iterator();
    }

    public void add(HSClassImpl rci) {
        clazzes.add(rci);
    }
}
