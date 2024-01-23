package dev.gruff.hardstop.api;

import java.util.Collection;
import java.util.Set;

public interface HSPackage {
    RavenContainer container();

    String name();

    int classCount();

    Collection<RavenClass> classes();

    Set<String> classNames();
}
