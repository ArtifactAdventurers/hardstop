package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenDependencySet;
import dev.gruff.hardstop.api.RavenDependency;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RavenDependencySetImpl implements RavenDependencySet {

    private Set<RavenDependency> deps=new HashSet<>();

    public void add(RavenDependency rd) {
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
    public Iterator<RavenDependency> iterator() {
        return deps.iterator();
    }

    @Override
    public void forEach(Consumer<? super RavenDependency> action) {
       deps.forEach(action);
    }

    @Override
    public Spliterator<RavenDependency> spliterator() {
        return deps.spliterator();
    }
}
