package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.core.impl.HSClassImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LiteralStore {

    private Map<String, Set<HSClassImpl>> literals=new HashMap<>();
        public void addLiteral(HSClassImpl owner, String literal) {

            Set<HSClassImpl> s = literals.computeIfAbsent(literal, k -> new HashSet<>());
            s.add(owner);
        }
}
