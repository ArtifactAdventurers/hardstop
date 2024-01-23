package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.RavenArtifactSource;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenPOM;

import java.io.File;

public class LocalMavenCache implements RavenArtifactSource {

    private File root;
    @Override
    public RavenPOM pom(RavenCoordinate pdep) {
        throw new RuntimeException("fix me");

    }

    public LocalMavenCache(File f) {
        this.root=f;
    }
}
