package dev.gruff.hardstop.api;


public interface HSComponentMetaSet extends Iterable<HSComponentMeta> {
    int size();

    void forEach(Object o);

    HSComponentMeta only();

  //  void addAll(HSComponentMetaSet pom);


}
