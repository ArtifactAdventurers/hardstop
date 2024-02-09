package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.SemanticVersion;

public class SemanticVersionImpl implements SemanticVersion {

    private int major;
    private int minor;
    private int patch;


    public SemanticVersionImpl() {

    }
    public SemanticVersionImpl(int j,int o,int p) {
        major=j;
        minor=o;
        patch=p;
    }


    public boolean equals(Object o) {
        if(o==this) return true;
        if(o instanceof SemanticVersion) {
            SemanticVersion so= (SemanticVersion) o;
            return so.compareTo(this)==0;
        }
        return false;
    }
    public int compareMajorMinorVersion(SemanticVersion o) {

        if(major>o.major()) return 1;
        if(major<o.major()) return -1;

        if(minor>o.minor()) return 1;
        if(minor<o.minor()) return -1;

        return 0;

    }
    @Override
    public int compareTo(SemanticVersion o) {

        int c=compareMajorMinorVersion(o);
        if(c!=0) return c;
        if(patch>o.patch()) return 1;
        if(patch<o.patch()) return -1;

        return 0;

    }

    public String toString() {
        return major+"."+minor+"."+patch;
    }

    @Override
    public int major() {
        return major;
    }

    @Override
    public int minor() {
        return minor;
    }

    @Override
    public int patch() {
        return patch;
    }
}
