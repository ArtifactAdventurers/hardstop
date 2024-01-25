HardStop is a tool to evaluate Java API breakages

The tool can examine classes from various locations
and provide comparison details

As a necessity - since classes come packaged in many ways
and their formal version info is also often missing or in various forms -  Hardstop will attempt to figure out the coordinates by reading poms etc

Key artifacts

* HSStore - the root object for holding all metadata about the classes, the containers they are in, what components and what versions etc.
  HSStore creates Coordinate, Component and Package objects while everything comes from Artifact stores
  
* HSArtifactSource - used by the store to find artifacts (HSContainers, HSClasses or HSComponentMeta )
  ArtifactSources represent public repos (using Maven Central format) , Local repo cache (Maven format)
  projects (local file systems in a maven project format) and archives (zips, jars) etc. 
  There is also a File system Artifact source that will scan the filesystem and discover content formats

* HSComponent : represents a group of Java classes with a specific coordinate. Conceptually equal to a dependency on maven
  Multiple versions of HSCComponent can exist in the store with the same HSCoordinate. They are differentiated by a ArtifactStore reference  

* HSCoordinate - a standalone definition of a maven group/artifact/version. Components can have a Coordinate but it is not mandatory.  

* HSClass - represents a Java class. The class represents a class discovered within a container 

* HSContainer - the holder of the class. Each HSClass has a container. Containers can be file systems or archives. 

* HSPackage - represents the package that the class belongs to. HSPackages can reference classes at multiple versions and from multiple containers

* HSComponentMeta - represents the metadata for a Component beyond the basic Coordinates. Properties and Dependencies are included here. HSComponentMeta may have a Component but can exist standalone. 
ComponentMeta are created and registered by artifact sources





Index: 

Create a HSStore
    add artifact sources
    
scan() to build an index from all artifact sources (or a named one)


Local POM 

Create an HSStore
    add a project source
    load(pom file) get an (HSComponentMeta) output tree.

    


