package dev.gruff.hardstop.core.impl;

import dev.gruff.hardstop.api.SemanticVersion;
import dev.gruff.hardstop.api.clazz.AccessFlagInspector;
import dev.gruff.hardstop.core.JavaVersionInspector;
import dev.gruff.hardstop.core.internal.ConstantPool;
import dev.gruff.hardstop.api.HSContainer;
import dev.gruff.hardstop.core.internal.Attributes;
import dev.gruff.hardstop.core.internal.Utils;
import dev.gruff.hardstop.api.HSClass;

import java.util.*;

public class HSClassImpl implements HSClass {

    int minor;
    int major;
    ConstantPool constantPool;
     int accessFlags;
     int this_class;
     int super_class;
     int interfaces_count;
     int[] interfaces;
     int fields_count;
     int methods_count;
    private final Map<String, Method> methods=new TreeMap<>();
    final Map<String,Field> fields=new TreeMap<>();
     int attributes_count;
     Attributes attributes;
     boolean extraneousData;
     long header;
     boolean valid;
    byte[] digest;

    private HSContainerImpl container;
    public SemanticVersion compilerVersion() {
        return JavaVersionInspector.version(this);
    }

    @Override
    public Collection<HSClass.Field> fields() {
        return Collections.unmodifiableCollection( fields.values());

    }

    @Override
    public Collection<HSClass.Method> methods() {
        return Collections.unmodifiableCollection(methods.values());
    }

    @Override
    public String digest() {

        return Utils.toHexString(digest);
    }

    @Override
    public boolean hasFields() {
        return !fields.isEmpty();
    }

    @Override
    public boolean hasField(String reference) {
        return fields.containsKey(reference);
    }

    @Override
    public HSClass.Field field(String reference) {
        return fields.get(reference);
    }

    @Override
    public boolean hasMethods() {
        return !methods.isEmpty();
    }

    @Override
    public boolean hasMethod(String ref) {
        return methods.containsKey(ref);
    }

    @Override
    public HSClass.Method method(String ref) {
        return methods.get(ref);
    }

    @Override
    public int major() {
        return major;
    }

    @Override
    public int minor() {
        return minor;
    }

    public HSClassImpl() {

    }
    @Override
    public Set<AccessFlagInspector.ClassFlag> accessFlags() {
        return AccessFlagInspector.asClassAccess(major, accessFlags);
    }

    public String className() {
        return constantPool.getResolvedClassName(this_class);
    }

    @Override
    public String packageName() {
        return Utils.packageName(className());
    }

    @Override
    public Type type() {
        if(isInterface()) return Type.INTERFACE;
        if(isEnum()) return Type.ENUM;
        return Type.CLASS;
    }

    public String[] interfaceNames() {

        String[] data=new String[interfaces_count];
        for(int i=0;i<data.length;i++) {
            data[i] = constantPool.getResolvedClassName(interfaces[i]);
        }
        return data;
    }
    public String superClassName() {

        if (super_class > 0) {

            String superClass = constantPool.getResolvedClassName(super_class);
            return superClass;
        } else {
           // TODO - this class should be java.object
        }
        return null;
    }

    @Override
    public boolean isAbstract() {
        return (accessFlags & 0x0400) == 0x0400;
    }


    public boolean isClass() {
        return !isEnum() && !isInterface();
    }

    public boolean isPublic() {
        return (accessFlags & 0x0001 ) == 0x0001;
    }


    public boolean isInterface() {
        return (accessFlags & 0x0200 ) == 0x0200;
    }
    public boolean isEnum() {
        return (accessFlags & 0x4000 ) == 0x4000;
    }

    public boolean isFinal() {
        return (accessFlags & 0x0010 ) == 0x0010;
    }

    @Override
    public boolean isInnerClass() {
        return attributes.hasAttribute("InnerClasses");
    }

    @Override
    public Collection<String> literals() {
        return new HashSet<>();
    }

    @Override
    public HSContainer container() {
        return container;
    }

    public void container(HSContainerImpl ci) {
        this.container=ci;
    }

    public boolean addMethod(Method method) {
        String ref=method.reference();
        if(methods.containsKey(ref)) return false;
        methods.put(ref,method);
        return true;
    }


    public class Field implements HSClass.Field{
        public int access_flags;
        public int name_index;
        public int descriptor_index;
        public int attributes_count;
        Attributes attributes;

        public String name() {
            return constantPool.getUTF8AsString(name_index);
        }
        public String descriptor() {

            return constantPool.getUTF8AsString(descriptor_index);
        }

        public boolean isPublic() {
            return (accessFlags & 0x0001 ) == 0x0001;
        }

        @Override
        public int accessFlags() {
            return access_flags;
        }

        @Override
        public String reference() {
            return name()+"::"+descriptor();
        }
    }

    public class AttributeImpl implements HSClass.Attribute {

        public int attribute_name_index;
        public long attribute_length;
        public byte[] data;

        public String name() {
           return constantPool.getUTF8AsString(attribute_name_index);
        }
    }

    public  class Method implements HSClass.Method{

        int access_flags;
        int name_index;
        int descriptor_index;
       int attributes_count;
        Attributes attributes;

        public String name() {
            return constantPool.getUTF8AsString(name_index);
        }
        public String descriptor() {

            return constantPool.getUTF8AsString(descriptor_index);
        }


        public String reference() {
            return name()+"::"+descriptor();
        }

        @Override
        public int accessFlags() {
            return access_flags;
        }
    }
}
