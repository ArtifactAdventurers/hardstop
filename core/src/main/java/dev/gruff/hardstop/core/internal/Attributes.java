package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.core.impl.HSClassImpl;
import dev.gruff.hardstop.api.HSClass;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Attributes {

    private Map<String, HSClass.Attribute> attributeByName = new TreeMap<>();
    private List<HSClass.Attribute> attributes = new LinkedList<>();

    public Attributes() {

    }


     public void add(HSClassImpl.AttributeImpl attribute) {
        attributes.add(attribute);
        String name=attribute.name();
        attributeByName.put(name,attribute);
    }

    public boolean hasAttribute(String name) {
        return attributeByName.containsKey(name);
    }
}
