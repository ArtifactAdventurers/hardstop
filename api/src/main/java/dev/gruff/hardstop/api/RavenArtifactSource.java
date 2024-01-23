package dev.gruff.hardstop.api;

public interface RavenArtifactSource {
    RavenPOM pom(RavenCoordinate pdep);
}
