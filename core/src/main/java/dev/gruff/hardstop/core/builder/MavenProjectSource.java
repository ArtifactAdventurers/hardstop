package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.*;
import dev.gruff.hardstop.core.impl.HSComponentMetaSetImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MavenProjectSource implements HSArtifactSource {

    protected static final Logger LOG = LogManager.getLogger();
    private File root=null;
    private Map<HSCoordinate, HSComponentMetaSetImpl> cache=null;

    public String toString() {
        return root.getAbsolutePath();
    }
    private Map<String,File> candidates=new HashMap<>();
    public MavenProjectSource(File p) {
        if(p==null) throw new RuntimeException("root is null");
        if(!p.exists()) throw new RuntimeException("root does not exist");
        if(!p.isDirectory()) throw new RuntimeException("root is not a directory");

        this.root=p;
    }

    @Override
    public HSComponentMetaSet meta(HSCoordinate pdep) {

        Map<HSCoordinate, HSComponentMetaSetImpl> data=getCache();
        HSComponentMetaSet result=data.get(pdep);
        if(result==null) result=new HSComponentMetaSetImpl();

        return result;
    }

    @Override
    public HSComponentSet components(HSCoordinate pdep) {
        throw new RuntimeException("fixme ");
    }

    private synchronized Map<HSCoordinate, HSComponentMetaSetImpl> getCache() {
        if(cache!=null) return cache;
        cache=new TreeMap<>();
        buildCache(root);
        return cache;
    }

    private HSComponentMeta toPOM(File f) {
            LOG.debug("toPOM for ["+f.getAbsolutePath()+"]");
            HSComponentMeta rp= POMLoader.loader().fileOnly(f);
            if(rp==null) {
                LOG.info("No POM for ["+f.getAbsolutePath()+"]");
            } else {
                LOG.debug("created POM " + rp.coordinates());
            }
            System.out.println(f.getAbsolutePath()+ "-> "+rp.coordinates());
            return rp;

    }
    private void buildCache(File root) {

        LOG.debug("building cache for ["+root.getAbsolutePath()+"]");
        try {
            Files.walk(root.toPath(), FileVisitOption.FOLLOW_LINKS)
                    .map(p-> p.toFile())
                    .parallel()
                    .filter(File::isFile)
                    .filter(f -> f.getName().equals("pom.xml"))
                     .forEach( this::savePOM);

            LOG.info("build cache for ["+root.getAbsolutePath()+"] with "+cache.size()+" entries");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void savePOM(File f) {

        HSComponentMeta p=toPOM(f);
        if(p!=null) {
            HSComponentMetaSetImpl ms = cache.computeIfAbsent(p.coordinates(), k -> new HSComponentMetaSetImpl());
            ms.add(p);
        }
    }
}
