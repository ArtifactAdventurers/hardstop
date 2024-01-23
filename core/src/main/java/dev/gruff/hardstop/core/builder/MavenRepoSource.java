package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.RavenArtifactSource;
import dev.gruff.hardstop.api.RavenCoordinate;
import dev.gruff.hardstop.api.RavenPOM;

public class MavenRepoSource implements RavenArtifactSource {
    @Override
    public RavenPOM pom(RavenCoordinate pdep) {
        return null;
    }
}
