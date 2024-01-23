package dev.gruff.hardstop.api;

import java.util.Set;

public interface RavenPropertySet {
    void put(String key, String value);

    public void addProperty(RavenProperty rp);

    void parent(RavenPropertySet properties);

    String value(String s);

    RavenProperty property(String s);

    Set<String> keys();

}
