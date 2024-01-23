package dev.gruff.hardstop.api;

public interface HSStore {
    RavenDependency resolveDependency(RavenCoordinate rc);

    RavenPOM resolvePom(RavenCoordinate pdep);

    public void addSource(RavenArtifactSource as);


}
