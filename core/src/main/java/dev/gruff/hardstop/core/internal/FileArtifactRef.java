package dev.gruff.hardstop.core.internal;

import dev.gruff.hardstop.api.HSArtifactRef;

import java.io.File;

public class FileArtifactRef implements HSArtifactRef {

    private File file;

    public FileArtifactRef(File f) {
        this.file=f;
    }
}
