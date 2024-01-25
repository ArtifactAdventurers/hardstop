package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.HSComponent;
import dev.gruff.hardstop.api.HSComponentSet;
import dev.gruff.hardstop.api.HSCoordinate;

import java.util.*;
import java.util.function.Consumer;

public class HSComponentSetImpl implements HSComponentSet {

    public HSComponentSetImpl(HSCoordinate c) {
        this.coordinate=c;
    }

    private HSCoordinate coordinate;
    private Set<HSComponent> deps=new HashSet<>();

    public void add(HSComponent rd) {
        deps.add(rd);
    }

    @Override
    public int size() {
        return deps.size();
    }

    @Override
    public HSCoordinate coordinate() {
        return coordinate;
    }

    @Override
    public void forEach(Object o) {

    }

    @Override
    public boolean isEmpty() {
        return deps.isEmpty();
    }

    @Override
    public HSComponent only() {
        return deps.iterator().next();
    }

    @Override
    public Iterator<HSComponent> iterator() {
        return deps.iterator();
    }

    @Override
    public void forEach(Consumer<? super HSComponent> action) {
       deps.forEach(action);
    }

    @Override
    public Spliterator<HSComponent> spliterator() {
        return deps.spliterator();
    }

    public void addAll(HSComponentSet rd) {
        rd.forEach( deps::add);
    }
}
