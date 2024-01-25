package dev.gruff.hardstop.api;

public interface HSComponentMeta {

    public RavenPropertySet properties();

    HSCoordinate coordinates();

    HSComponentMetaSet dependencies();

    HSComponentMeta parent();

    HSArtifactRef artifactRef();
}
