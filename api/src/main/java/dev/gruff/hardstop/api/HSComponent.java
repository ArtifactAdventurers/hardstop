package dev.gruff.hardstop.api;

import java.util.Set;

public interface HSComponent {


    HSCoordinate coordinates();

    HSArtifactRef artifactRef();

    Set<String> classNames();

    HSClassSet classByName(String className);


}
