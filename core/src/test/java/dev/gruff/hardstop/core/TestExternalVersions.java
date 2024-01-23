package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.RavenComponent;
import dev.gruff.hardstop.api.HSPackage;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.IntStream;

public class
TestExternalVersions {



    public void testA1() throws IOException {
        File f=new File(System.getProperty("user.home"));
        f=new File(f,".m2");
        f=new File(f,"repository/log4j");

        ComponentLoader mb=new ComponentLoader();
        RavenComponent model=mb.addRoot(f)
              //  .versionInfoOnly(true)
               // .verbose(true)
              //  .filterFilename(ff -> {
               //      return ff.endsWith("Main.class");})
                .build();
        System.out.println("containers = "+model.containerCount());
        System.out.println("packages   = "+model.packageCount());
        for(String rp:model.packageNames()) {
            Set<HSPackage> l=model.getPackage(rp);
            System.out.println("package name :"+rp);
            System.out.println("instances :"+l.size());
            long sum=l.stream().flatMapToInt(ps-> IntStream.of(ps.classCount())).sum();
            System.out.println("classes :"+sum);
            if(sum>0) {
                l.forEach(pi -> {
                    pi.classes().forEach(cf-> System.out.println(cf.className()));
                });
            }

        }

    }
}
