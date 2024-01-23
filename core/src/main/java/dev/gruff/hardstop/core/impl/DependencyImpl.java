package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenDependency;

public class DependencyImpl implements RavenDependency, Comparable<DependencyImpl> {

    private RavenCoordinate coord;
    public DependencyImpl(RavenCoordinate rci) {
        coord=rci;
    }
    @Override
    public int compareTo(DependencyImpl o) {

        RavenCoordinate orc=o.coord;
        return coord.compareTo(orc);

    }

    @Override
    public RavenCoordinate coordinates() {
        return coord;
    }
}
