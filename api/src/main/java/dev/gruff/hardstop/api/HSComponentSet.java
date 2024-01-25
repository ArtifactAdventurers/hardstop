package dev.gruff.hardstop.api;


public interface HSComponentSet extends Iterable<HSComponent> {
    int size();

    HSCoordinate coordinate();
    void forEach(Object o);

    boolean isEmpty();

    HSComponent only();
}
