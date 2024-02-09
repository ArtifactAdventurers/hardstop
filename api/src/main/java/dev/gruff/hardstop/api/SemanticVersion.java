package dev.gruff.hardstop.api;

public interface SemanticVersion extends Comparable<SemanticVersion> {


    public int major();
    public int minor();
    public int patch();

    public int compareMajorMinorVersion(SemanticVersion o);
}
