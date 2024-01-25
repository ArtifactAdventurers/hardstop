package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.*;



import java.util.*;

public class HSStoreImpl implements HSStore {

    public boolean trace;
    Map<HSCoordinate, HSComponentSet> deps=new TreeMap<>();

    LinkedHashSet<HSArtifactSource> sources= new LinkedHashSet<HSArtifactSource>();
    @Override
    public synchronized HSComponentSet components(HSCoordinate rc) {
        trace("component request for "+rc);
        if(rc==null) return null;

        if(deps.containsKey(rc)) {
            HSComponentSet r=deps.get(rc);
            trace("found in cache "+r);
            return r;
        } else {
            trace("not cached ");
        }
        HSComponentSetImpl i=new HSComponentSetImpl(rc);
        if(sources.isEmpty()) {
            trace("no artifact sources");
        }
        for(HSArtifactSource s:sources) {
            trace("asking source "+s);
            HSComponentSet comps=s.components(rc);
            if(comps!=null) {
                trace("source "+s+" response "+comps.size());
                i.addAll(comps);
            }
        };
        trace("found "+i.size()+" components");

        deps.put(rc,i);

        return i;
    }

    @Override
    public HSComponentSet components(String groupId, String artifactId, String version) {
        HSCoordinateImpl c=new HSCoordinateImpl(groupId,artifactId,version);
        return components(c);
    }

    private void trace(String s) {
        if(trace)System.out.println(s);
    }

    public void addSource(HSArtifactSource source) {
        if(source==null)  throw new RuntimeException("source is null");
        trace("added artifact source "+source);
        sources.add(source);
    }

    @Override
    public HSComponentMetaSet meta(HSCoordinate pdep) {

        trace("meta request for "+pdep);
        HSComponentMetaSetImpl poms=new HSComponentMetaSetImpl();
        // can we find a pom ?
        if(sources.isEmpty()) {
            trace("no artifact sources");
            poms.add(new HSPOMImpl(pdep));
        } else {
            for (HSArtifactSource s : sources) {
                trace("source " + s);
                HSComponentMetaSet pom = s.meta(pdep);
                trace("source " + s + " response " + pom);
                if (pom != null) {
                    poms.addAll(pom);
                }
            }
            ;
        }
        return poms;
    }

}
