package dev.gruff.hardstop.api;

public interface HSArtifactSource {
    HSComponentMetaSet meta(HSCoordinate pdep);

    HSComponentSet components(HSCoordinate pdep);
}
