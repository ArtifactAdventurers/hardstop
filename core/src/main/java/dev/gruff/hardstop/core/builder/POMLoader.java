package dev.gruff.hardstop.core.builder;



import dev.gruff.hardstop.core.impl.RavenPropertySetImpl;
import dev.gruff.hardstop.core.impl.HSStoreImpl;
import dev.gruff.hardstop.core.internal.POMFileParser;
import dev.gruff.hardstop.api.HSStore;
import dev.gruff.hardstop.api.HSCoordinate;
import dev.gruff.hardstop.api.HSComponentMeta;
import dev.gruff.hardstop.api.RavenPropertySet;


import java.io.File;
import java.util.*;

public class POMLoader {


    public static class Config {

        private boolean excludeLocalCache = false;
        private boolean excludeRemoteCache = false;

        private List<File> fileRoots = new LinkedList<>();
        private Properties presetProperties = new Properties();
        private boolean trace=false;


        public Config addProperty(String key, String value) {
            presetProperties.put(key, value);
            return this;
        }


        public Config addProperties(Properties p) {
            if (p != null) {
                presetProperties.putAll(p);
            }
            return this;
        }

        public Config projectRoot(File file) {

            if (file == null) throw new RuntimeException("project root is null");
            if (!file.isDirectory()) throw new RuntimeException("project root is not a directory");
            fileRoots.add(file);
            return this;
        }

        public Config excludeLocalCache(boolean b) {
            excludeLocalCache = b;
            return this;
        }

        public Config excludeRemoteCache(boolean b) {
            excludeRemoteCache = b;
            return this;
        }

        public HSComponentMeta load(File pom) {

            if (pom == null) throw new RuntimeException("pom is null");
            if (!pom.exists()) throw new RuntimeException("pom does not exist [" + pom.getAbsolutePath() + "]");

            if (pom.isDirectory()) {
                pom = new File(pom, "pom.xml");
            }

            if (pom.isFile()) {
                POMFileParser p = configParser();
                return p.parse(pom);
            } else {
                throw new RuntimeException("pom file type is unknown [" + pom.getAbsolutePath() + "]");
            }
        }

        private POMFileParser configParser() {

            HSStoreImpl store = new HSStoreImpl();
            store.trace=trace;

            if (!excludeRemoteCache) store.addSource(new MavenRepoSource());
            if (!excludeLocalCache) store.addSource(new LocalMavenCache());
            for(File p:fileRoots) {
                store.addSource(new MavenProjectSource(p));
            }
            return new POMFileParser(store,true,trace);


        }

        public HSComponentMeta fileOnly(File f) {
            HSStore rs=new HSStoreImpl();
            POMFileParser p=new POMFileParser(rs,false,trace);
            return p.parse(f);
        }

        public Config trace(boolean b) {
            this.trace=b;
            return this;
        }
    }


    private Set<String> activeProfiles = new HashSet<>();


    private RavenPropertySet props = new RavenPropertySetImpl();

    private Map<HSCoordinate, HSComponentMeta> localPOMs = new HashMap<>();


    private POMLoader() {

    }

    public static Config loader() {
      return new Config();

    }

}





