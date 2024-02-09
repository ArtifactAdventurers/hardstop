package dev.gruff.hardstop.api;


import dev.gruff.hardstop.api.clazz.AccessFlagInspector;

import java.util.Collection;
import java.util.Set;

public interface HSClass {


  Set<AccessFlagInspector.ClassFlag> accessFlags();
    String className();
    String packageName();

    Type type();
    boolean isClass();

    boolean isEnum();

    boolean isInterface();

    boolean isFinal();

    boolean isInnerClass();
    Collection<String> literals();

    HSContainer container();

    public SemanticVersion compilerVersion();

    Collection<Field> fields();

  Collection<Method> methods();
    String digest();

  boolean hasFields();

  boolean hasField(String reference);

  Field field(String reference);

  boolean hasMethods();

  boolean hasMethod(String ref);

  Method method(String ref);

  int major();

  int minor();

  boolean isPublic();

    String superClassName();

  boolean isAbstract();

  public interface Method {
    String descriptor();
    String name();

    String reference();

    int accessFlags();
  }
  public interface Field {

      String descriptor();

    String name();

    boolean isPublic();

    int accessFlags();

    String reference();
  }

  public interface Attribute {

  }

  public static enum Type {
    CLASS,INTERFACE,ENUM
  }
}
