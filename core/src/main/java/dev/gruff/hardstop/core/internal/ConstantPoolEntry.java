package dev.gruff.hardstop.core.internal;

import java.util.HashMap;
import java.util.Map;

public enum ConstantPoolEntry {
    CONSTANT_Class(7),
    CONSTANT_Fieldref ( 9),
    CONSTANT_Methodref ( 10),
    CONSTANT_InterfaceMethodref ( 11),
    CONSTANT_String ( 8),
    CONSTANT_Integer ( 3),
    CONSTANT_Float ( 4),
    CONSTANT_Long ( 5),
     CONSTANT_Double ( 6),
     CONSTANT_NameAndType ( 12),
     CONSTANT_Utf8 ( 1),
    CONSTANT_MethodHandle ( 15),
     CONSTANT_MethodType ( 16),
     CONSTANT_InvokeDynamic ( 18),

    CONSTANT_Unknown(0),
    CONSTANT_Module(19),
    CONSTANT_Package(20),
    CONSTANT_EMPTY(999);

    private int tagID;

    private static final Map<Integer, ConstantPoolEntry> tagMap = new HashMap<>();

    static {
        for (ConstantPoolEntry e: values()) {
            tagMap.put(e.tagID, e);
        }
    }
    private ConstantPoolEntry(int tagID ) {
        this.tagID=tagID;
    }


    public static ConstantPoolEntry entry(int tag) {
            ConstantPoolEntry e=tagMap.get(tag);
            if(e==null) e=ConstantPoolEntry.CONSTANT_Unknown;
            return e;
    }
}
