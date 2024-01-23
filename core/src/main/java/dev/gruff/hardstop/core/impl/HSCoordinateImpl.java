package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenCoordinate;

public class HSCoordinateImpl implements RavenCoordinate {
    private final String finalA;
    private final String finalG;
    private final String finalV;

    public HSCoordinateImpl(String finalA, String finalG, String finalV) {
        this.finalA = finalA;
        this.finalG = finalG;
        this.finalV = finalV;
    }

    @Override
    public String artifactId() {
        return finalA;
    }

    @Override
    public String groupId() {
        return finalG;
    }

    @Override
    public String version() {
        return finalV;
    }

    public String toString() {
        return groupId() + "/" + artifactId() + "@" + version();
    }

    @Override
    public int compareTo(RavenCoordinate o) {

        int g=this.groupId().compareTo(o.groupId());
        if(g!=0) return g;
        int a=this.artifactId().compareTo(o.artifactId());
        if(a!=0) return a;
        return this.version().compareTo(o.version());

    }
}
