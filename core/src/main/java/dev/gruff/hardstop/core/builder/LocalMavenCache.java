package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.*;
import dev.gruff.hardstop.core.impl.HSComponentSetImpl;

import java.io.File;

public class LocalMavenCache implements HSArtifactSource {

    private File root;
    @Override
    public HSComponentMetaSet meta(HSCoordinate pdep) {
        throw new RuntimeException("fix me");
    }

    @Override
    public HSComponentSet components(HSCoordinate pdep) {
        return new HSComponentSetImpl(pdep);
        //throw new RuntimeException("fix me");
    }

    public LocalMavenCache(File f) {
        this.root=f;
    }

    public LocalMavenCache() {
        this(new File(new File(System.getProperty("user.home")),".m2"));
    }

}
