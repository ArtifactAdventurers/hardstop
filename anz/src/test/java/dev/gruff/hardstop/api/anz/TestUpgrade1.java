package dev.gruff.hardstop.api.anz;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.core.builder.ClassReader;
import dev.gruff.hardstop.testsupport.TestHelper;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class TestUpgrade1 {


    @Test
    public void t1() throws IOException {

        HSClass a= ClassReader.readClass(TestHelper.getFile(SimpleTestClass.I1.class));
        HSClass b= ClassReader.readClass(TestHelper.getFile(SimpleTestClass.I2.class));

        APIComparision ac=new APIComparision();
        ac.compare(a,b);
    }
}
