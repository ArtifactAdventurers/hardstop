package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.core.impl.HSClassImpl;

import java.util.*;
import java.util.stream.Stream;

public class RavenLiteralStore {


    private final Map<String, Set<HSClassImpl>> literals=new TreeMap<>();
    public void addLiteral(HSClassImpl owner) {

        for(String l:owner.literals()) {
            Set<HSClassImpl> s = literals.computeIfAbsent(l, k -> new HashSet<>());
            s.add(owner);
        }
    }

    public Set<HSClassImpl> get(String s) {
        return literals.get(s);
    }

    public Set<HSClassImpl> contains(String s) {
        Set<HSClassImpl> r=new HashSet<>();
        literals.keySet().stream().forEach( l -> {
            if(l.contains(s)) r.addAll(literals.get(l));
        });
        return r;
    }

    public Stream<String> match(String s) {
        return literals.keySet().stream().filter( l -> l.contains(s));
    }
}
