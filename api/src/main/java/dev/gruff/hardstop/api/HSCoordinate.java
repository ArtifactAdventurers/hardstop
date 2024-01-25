package dev.gruff.hardstop.api;

public interface HSCoordinate extends Comparable<HSCoordinate>{

    public String artifactId();
    public String groupId();

    public String version();


}
