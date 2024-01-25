package dev.gruff.hardstop.api;

public interface HSClassSet extends Iterable<HSClass> {
    int size();

    void forEach(Object o);

    HSClass only();
}
