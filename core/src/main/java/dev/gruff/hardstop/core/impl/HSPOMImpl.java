package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.*;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class HSPOMImpl implements HSComponentMeta {

        public HSComponentMeta parent;
        private HSCoordinate coords;

        private HSArtifactRef ref;


       public RavenPropertySet properties=new RavenPropertySetImpl();
        public HSComponentMetaSetImpl dependencies=new HSComponentMetaSetImpl();


    public HSPOMImpl(HSArtifactRef ref) {
        this.ref=ref;
    }

    public HSPOMImpl(HSCoordinate coord) {
        this.coords = coord;
    }
    public String toString() {
        if(ref==null) return "<synthetic>/"+coords;
        return ref.toString()+"/"+coords;
    }

    @Override
    public RavenPropertySet properties() {
        return properties;
    }

    @Override
    public HSCoordinate coordinates() {
        return coords;
    }
    @Override
    public HSComponentMetaSet dependencies() {
        return dependencies;
    }

    @Override
    public HSComponentMeta parent() {
        return parent;
    }

    @Override
    public HSArtifactRef artifactRef() {
        return ref;
    }

    public void coordinates(HSCoordinate coords) {
        this.coords=coords;
    }
}
