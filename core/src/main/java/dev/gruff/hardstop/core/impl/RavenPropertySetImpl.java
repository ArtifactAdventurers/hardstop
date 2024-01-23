package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenPropertySet;
import dev.gruff.hardstop.api.RavenPOM;
import dev.gruff.hardstop.api.RavenProperty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class RavenPropertySetImpl implements RavenPropertySet {

    private TreeMap<String,RavenProperty> props=new TreeMap<>();

    private RavenPropertySet parent=null;
    @Override
    public void put(String key, String value) {
        RavenProperty rp=new RavenProperty() {
            @Override
            public String key() {
                return key;
            }

            @Override
            public String value() {
                return value;
            }

            @Override
            public RavenPOM source() {
                return null;
            }
        };

        addProperty(rp);
    }

    public void addProperty(RavenProperty rp) {

        if(rp==null) return;
        if(rp.key()==null) return;
        props.put(rp.key(),rp);
    }

    @Override
    public void parent(RavenPropertySet properties) {
        this.parent=properties;
    }

    @Override
    public String value(String s) {
        RavenProperty rp=property(s);
        if(rp==null) return null;
        return rp.value();
    }

    @Override
    public RavenProperty property(String s) {
        RavenProperty rp=props.get(s);
        if(rp==null && parent!=null) rp=parent.property(s);
        return rp;
    }

    @Override
    public Set<String> keys() {
        Set<String> data=new HashSet<>();
        data.addAll(props.keySet());
        if(parent!=null) data.addAll(parent.keys());
        return Collections.unmodifiableSet(data);
    }
}
