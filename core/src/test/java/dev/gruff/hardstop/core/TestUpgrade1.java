package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.RavenClass;
import dev.gruff.hardstop.core.reader.APIComparision;
import org.junit.jupiter.api.Test;
import testdata.SimpleTestClass;

import java.io.IOException;

public class TestUpgrade1 {


    @Test
    public void t1() throws IOException {

     RavenClass a=TestHelper.getClass(SimpleTestClass.I1.class);
        RavenClass b=TestHelper.getClass(SimpleTestClass.I2.class);

        APIComparision ac=new APIComparision();
        ac.compare(a,b);
    }
}
