package dev.gruff.hardstop.api;

public interface RavenPOM {

    public RavenPropertySet properties();

    RavenCoordinate coordinates();

    RavenDependencySet dependencies();

    RavenPOM parent();
}
