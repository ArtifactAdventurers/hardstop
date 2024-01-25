package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSComponent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestDuplicateClasses {


    @Test
    public void testDups() throws IOException {
        File f=new File(System.getProperty("user.home"));
        f=new File(f,".m2");
        f=new File(f,"repository/junit");

        ComponentLoader mb=new ComponentLoader();
        HSComponent model=mb.addRoot(f).build();

        ReportDuplicateClasses rdc=new ReportDuplicateClasses();
       // rdc.report(model);

    }
}
