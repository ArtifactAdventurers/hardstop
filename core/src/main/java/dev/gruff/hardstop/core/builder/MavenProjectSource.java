package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.RavenArtifactSource;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenPOM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MavenProjectSource implements RavenArtifactSource {

    protected static final Logger LOG = LogManager.getLogger();
    private File root=null;
    private Map<RavenCoordinate,RavenPOM> cache=null;

    private Map<String,File> candidates=new HashMap<>();
    public MavenProjectSource(File p) {
        if(p==null) throw new RuntimeException("root is null");
        if(!p.exists()) throw new RuntimeException("root does not exist");
        if(!p.isDirectory()) throw new RuntimeException("root is not a directory");

        this.root=p;
    }

    @Override
    public RavenPOM pom(RavenCoordinate pdep) {

        if(pdep==null) throw new RuntimeException("coordinate is null");
        Map<RavenCoordinate,RavenPOM> data=getCache();
        if(!data.containsKey(pdep)) {
            data.keySet().forEach(System.out::println);
        }
        return data.get(pdep);
    }

    private synchronized Map<RavenCoordinate, RavenPOM> getCache() {
        if(cache!=null) return cache;
        cache=new TreeMap<>();
        buildCache(root);
        return cache;
    }

    private RavenPOM toPOM(File f) {
            LOG.debug("toPOM for ["+f.getAbsolutePath()+"]");
            RavenPOM rp= POMLoader.loader().fileOnly(f);
            if(rp==null) {
                LOG.info("No POM for ["+f.getAbsolutePath()+"]");
            } else {
                LOG.debug("created POM " + rp.coordinates());
            }
            //System.out.println(f.getAbsolutePath()+ "-> "+rp.coordinates());
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
                    .map(this::toPOM)
                    .forEach(p -> {
                        if(p!=null)
                        cache.put(p.coordinates(),p);

                    });
            LOG.info("build cache for ["+root.getAbsolutePath()+"] with "+cache.size()+" entries");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
