package dev.gruff.hardstop.api;

import java.util.Set;

public interface RavenComponent {


    RavenCoordinate coordinates();

    int containerCount();

    int packageCount();

    Set<String> packageNames();

    Set<HSPackage> getPackage(String packageName);

    Set<String> classNames();

    Set<RavenClass> classInstances(String className);

    RavenClass classInstance(String s);
}
