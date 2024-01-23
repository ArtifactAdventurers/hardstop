package dev.gruff.hardstop.api;


public interface RavenDependencySet extends Iterable<RavenDependency> {
    int size();

    void forEach(Object o);
}
