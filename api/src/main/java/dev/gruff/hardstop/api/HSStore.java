package dev.gruff.hardstop.api;

import java.util.Set;

public interface HSStore {

    HSComponentMetaSet meta(HSCoordinate pdep);

    HSComponentSet components(HSCoordinate rc);

    HSComponentSet components(String groupId,String artifactId,String version);

}
