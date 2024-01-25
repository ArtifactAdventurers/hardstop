package dev.gruff.hardstop.api;

import java.util.Collection;
import java.util.Set;

public interface HSPackage {
    HSContainer container();

    String name();

    int classCount();

    Collection<HSClass> classes();

    Set<String> classNames();
}
