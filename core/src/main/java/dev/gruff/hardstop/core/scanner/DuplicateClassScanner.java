package dev.gruff.hardstop.core.scanner;

import dev.gruff.hardstop.api.RavenClass;
import dev.gruff.hardstop.api.RavenComponent;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class DuplicateClassScanner {

    public void scan(RavenComponent model) {
           Collection<Set<RavenClass>> r= model.classNames()
                    .stream()
                    .parallel()
                    .map(model::classInstances)
                   .collect(Collectors.toSet());
    }
}
