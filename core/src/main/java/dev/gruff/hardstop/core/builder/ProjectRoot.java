package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.RavenArtifactSource;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenPOM;

import java.io.File;

public class ProjectRoot implements RavenArtifactSource {
    @Override
    public RavenPOM pom(RavenCoordinate pdep) {
        return null;
    }

    private File root;
    public ProjectRoot(File root) {
        this.root=root;
    }

}
