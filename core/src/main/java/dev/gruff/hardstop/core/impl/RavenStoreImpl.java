package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.*;



import java.util.*;

public class RavenStoreImpl implements HSStore {

    Map<RavenCoordinate, DependencyImpl> deps=new TreeMap<>();

    LinkedHashSet<RavenArtifactSource> sources= new LinkedHashSet<RavenArtifactSource>();
    @Override
    public RavenDependency resolveDependency(RavenCoordinate rc) {
        if(rc==null) return null;

        return deps.computeIfAbsent(rc,k-> new DependencyImpl(rc));

    }

    @Override
    public RavenPOM resolvePom(RavenCoordinate pdep) {

        RavenDependency rd=resolveDependency(pdep);
        // can we find a pom ?
        for(RavenArtifactSource s:sources) {

            RavenPOM pom=s.pom(pdep);
            if(pom!=null) {
                return pom;
            }
        };

        return null;
    }

    @Override
    public void addSource(RavenArtifactSource as) {
            if(as!=null) sources.add(as);
    }

}
