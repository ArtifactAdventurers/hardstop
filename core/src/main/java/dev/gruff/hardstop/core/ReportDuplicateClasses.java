
package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.RavenComponent;

import java.io.PrintWriter;
import java.util.*;

public class ReportDuplicateClasses {

    private final PrintWriter pw;
    public ReportDuplicateClasses(PrintWriter pw) {
        this.pw=pw;
    }
    public ReportDuplicateClasses() {
        this(new PrintWriter(System.out));
    }

    public void report(RavenComponent m) {



       m.classNames()
                .stream()
                .map(m::classInstances)
                .filter( t -> t.size()>1)
               .map( set -> new Object[]{set.iterator().next().className(),set.size(),set})
               .sorted(new Comparator<Object[]>() {
                   @Override
                   public int compare(Object[] o1, Object[] o2) {
                       int i1= (Integer) o1[1];
                       int i2= (Integer) o2[1];
                       if(i1==i2) return 0;
                       return i1 < i2 ? 1 : -1;

                   }}).forEach(data -> {
                       System.out.println("class "+data[0]+" "+data[1]+" copies");

/*
                        Set<RavenClass> s= (Set<RavenClass>) data[2];
                        List<RavenClass> l = new LinkedList(s);
                        l.sort(VersionMeta.classComparator());
                        RavenClass initial=l.remove(0);
                        String compilerVersion=initial.compilerVersion();
                        VersionMeta vm=initial.container().versionMeta();
                        String digest=initial.digest();
                        System.out.println("1: "+vm.version+" (Java "+compilerVersion+") "+digest);
                        int c=2;
                       for(RavenClass rc:l) {
                           String nxc=rc.compilerVersion();
                           digest=rc.digest();
                           VersionMeta nxcm=rc.container().versionMeta();
                           //
                           PublicSignatureComparitor.compare(initial,rc);
                           System.out.println(""+c+": "+nxcm.version+" (Java "+nxc+") "+digest);
                           c++;


                       }
                       */

               });



    }

}
