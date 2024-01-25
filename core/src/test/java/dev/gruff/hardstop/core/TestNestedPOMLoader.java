package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.POMLoader;
import dev.gruff.hardstop.api.HSComponentMeta;
import dev.gruff.hardstop.api.RavenProperty;
import dev.gruff.hardstop.api.RavenPropertySet;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestNestedPOMLoader {

    @Test
    public void testInheritedProperties() {
        File pom=new File("target/test-classes/p1/p2/p3/pom.xml");
        HSComponentMeta rp= POMLoader.loader()
                .excludeLocalCache(true)  // no .m2 thank you
                .excludeRemoteCache(true) // no maven central thank you
                .projectRoot(new File("target/test-classes"))
                .load(pom);

       RavenPropertySet p= rp.properties();

       for(String key:p.keys()) {
           RavenProperty pr=p.property(key);
           System.out.println(pr.key()+"="+pr.value()+" -> "+pr.source());
       }


    }
}
