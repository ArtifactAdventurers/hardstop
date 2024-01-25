package dev.gruff.hardstop.core;

import dev.gruff.hardstop.core.builder.ComponentLoader;
import dev.gruff.hardstop.api.HSComponent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestModel {

    @Test

    public void testSimpleModel() throws IOException {

        File f=new File("target/test-classes/testdata");
        ComponentLoader b=new ComponentLoader();
                HSComponent model=b.addRoot(f)
                        .verbose(true)
                .build();


    }
}
