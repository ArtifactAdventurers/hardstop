package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.*;
import dev.gruff.hardstop.core.impl.HSComponentSetImpl;

public class MavenRepoSource implements HSArtifactSource {
    @Override
    public HSComponentMetaSet meta(HSCoordinate pdep) {
        return null;
    }

    @Override
    public HSComponentSet components(HSCoordinate pdep) {
        return new HSComponentSetImpl(pdep);
      //throw new RuntimeException("fixe me");
    }
}
