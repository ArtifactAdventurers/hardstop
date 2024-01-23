package dev.gruff.hardstop.api;

public interface RavenCoordinate extends Comparable<RavenCoordinate>{

    public String artifactId();
    public String groupId();

    public String version();


}
