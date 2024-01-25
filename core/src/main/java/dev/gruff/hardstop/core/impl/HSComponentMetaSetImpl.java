package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class HSComponentMetaSetImpl implements HSComponentMetaSet {


    public HSComponentMetaSetImpl() {

    }
    private Set<HSComponentMeta> deps=new HashSet<>();

    public void add(HSComponentMeta rd) {
        deps.add(rd);
    }

    @Override
    public int size() {
        return deps.size();
    }

    @Override
    public void forEach(Object o) {

    }

    @Override
    public HSComponentMeta only() {
        if(deps.isEmpty()) return null;
        if(deps.size()>1) throw new RuntimeException("component meta set has multiple entries");
        return deps.iterator().next();
    }


    public void addAll(HSComponentMetaSet pom) {
        pom.forEach( deps::add);
    }

    @Override
    public Iterator<HSComponentMeta> iterator() {
        return deps.iterator();
    }

    @Override
    public void forEach(Consumer<? super HSComponentMeta> action) {
       deps.forEach(action);
    }

    @Override
    public Spliterator<HSComponentMeta> spliterator() {
        return deps.spliterator();
    }
}
