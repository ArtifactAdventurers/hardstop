package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenDependencySet;
import dev.gruff.hardstop.api.RavenPOM;
import dev.gruff.hardstop.api.RavenPropertySet;


import java.io.File;

public class RavenPOMImpl implements RavenPOM {
        public RavenCoordinate coordinates;
        public RavenPOM parent;
       File source;

       public RavenPropertySet properties=new RavenPropertySetImpl();
    public RavenDependencySetImpl dependencies=new RavenDependencySetImpl();


    public RavenPOMImpl(File pom) {
        this.source=pom;
    }

    public String toString() {
        return source.getAbsolutePath();
    }
    @Override
    public RavenPropertySet properties() {
        return properties;
    }

    @Override
    public RavenCoordinate coordinates() {
        return coordinates;
    }

    @Override
    public RavenDependencySet dependencies() {
        return dependencies;
    }

    @Override
    public RavenPOM parent() {
        return parent;
    }

}
