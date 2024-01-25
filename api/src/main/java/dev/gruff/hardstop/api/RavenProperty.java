package dev.gruff.hardstop.api;

public interface RavenProperty {

    String key();
    String value();
    HSComponentMeta source();

}
