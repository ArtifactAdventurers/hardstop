package dev.gruff.hardstop.api.clazz;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AccessFlagInspector {


    public static boolean isValidFieldAccess(int major,int flags) {
        for(FieldFlag f:FieldFlag.values()) {
            if(f.match(flags) && !f.isValid()) return false;
        }

        return true;
    }

    public static boolean isValidMethodAccess(int major,int flags) {
        for(MethodFlag f:MethodFlag.values()) {
            if(f.match(flags) && !f.isValid()) return false;
        }

        return true;
    }

    public static Set<FieldFlag> asFieldAccess(int major, int flags) {
        HashSet<FieldFlag> r=new HashSet<>();
        for(FieldFlag f:FieldFlag.values()) {
            if(f.match(flags)) r.add(f);
        }
        return Collections.unmodifiableSet(r);
    }

    public static Set<ClassFlag> asClassAccess(int major, int flags) {
        HashSet<ClassFlag> r=new HashSet<>();
        for(ClassFlag cf:ClassFlag.values()) {
            if(cf.match(flags)) r.add(cf);
        }
        return Collections.unmodifiableSet(r);
    }

    public static Set<MethodFlag> asMethodAccess(int major, int flags) {
        HashSet<MethodFlag> r=new HashSet<>();
        for(MethodFlag cf:MethodFlag.values()) {
            if(cf.match(flags)) r.add(cf);
        }
        return Collections.unmodifiableSet(r);
    }

    public static class ClassFlagDiff {
        public Set<ClassFlag> additions;
        public Set<ClassFlag> removals;

        public boolean noChanges() {
            return additions.isEmpty() && removals.isEmpty();
        }
    }

    public static ClassFlagDiff compare(Set<ClassFlag> initial, Set<ClassFlag> second) {
            ClassFlagDiff cd=new ClassFlagDiff();
            cd.additions=new HashSet<>();
            cd.removals=new HashSet<>();
            cd.additions.addAll(initial);
            cd.removals.addAll(second);
            cd.additions.removeAll(second);
            cd.removals.removeAll(initial);
            cd.additions=Collections.unmodifiableSet(cd.additions);
            cd.removals=Collections.unmodifiableSet(cd.removals);
            return cd;
    }

    private static enum FieldFlag  {
         ACC_PUBLIC(0x0001,true),
        ACC_PRIVATE(0x0002,true),
        ACC_PROTECTED(0x0004,true),
        ACC_STATIC(0x0008,true),
        ACC_FINAL(0x0010,true),
        ACC_UNKNOWN6(0x0020,false),
        ACC_VOLATILE(0x0040,true),
        ACC_TRANSIENT(0x0080,true),
        ACC_UNKNOWN9(0x0100,false),
        ACC_UNKNOWN10(0x0200,false),
        ACC_UNKNOWN11(0x0400,false),
        ACC_UNKNOWN12(0x0800,false),

       ACC_SYNTHETIC(0x1000,true),
        ACC_UNKNOWN2(0x2000,false),
        ACC_ENUM(0x4000,true),
        ACC_UNKNOWN4(0x8000,false);

        private final int flag;
        private final boolean valid;

        private  FieldFlag(int flag,boolean valid) {
           this.flag=flag;
           this.valid=valid;
        }


        public boolean isValid() { return valid; }


        public boolean match(int af) {
            return  (af & flag) ==flag;
        }
    }

    private static enum MethodFlag  {




        ACC_PUBLIC(0x0001,true),
        ACC_PRIVATE(0x0002,true),
        ACC_PROTECTED(0x0004,true),
        ACC_STATIC(0x0008,true),
        ACC_FINAL(0x0010,true),
        ACC_SYNCHRONIZED(0x0020,true),
        ACC_BRIDGE(0x0040,true),
        ACC_VARARGS(0x0080,true),
        ACC_NATIVE(0x0100,true),
        ACC_UNKNOWN10(0x0200,false),
        ACC_ABSTRACT(0x0400,true),
        ACC_STRICT(0x0800,true),

        ACC_SYNTHETIC(0x1000,true),
        ACC_UNKNOWN14(0x2000,false),
        ACC_UNKNOWN15(0x4000,false),
        ACC_UNKNOWN16(0x8000,false);

        private final int flag;
        private final boolean valid;

        private MethodFlag(int flag,boolean valid) {
            this.flag=flag;
            this.valid=valid;
        }


        public boolean isValid() { return valid; }


        public boolean match(int af) {
            return  (af & flag) ==flag;
        }
    }
    public static enum ClassFlag  {


        ACC_PUBLIC(0x0001,true),
        ACC_UNKNOWN2(0x0002,false),
        ACC_UNKNOWN3(0x0004,false),
        ACC_UNKNOWN4(0x0008,false),
        ACC_FINAL(0x0010,true),
        ACC_SUPER(0x0020,true),
        ACC_UNKNOWN7(0x0040,false),
        ACC_UNKNOWN8(0x0080,false),
        ACC_UNKNOWN9(0x0100,false),
        ACC_UNKNOWN10(0x0200,false),
        ACC_ABSTRACT(0x0400,false),
        ACC_UNKNOWN12(0x0800,false),
        ACC_SYNTHETIC(0x1000,false),
        ACC_ANNOTATION(0x2000,true),
        ACC_ENUM(0x4000,true),
        ACC_MODULE(0x8000,false);

        private final int flag;
        private final boolean valid;

        private  ClassFlag(int flag,boolean valid) {
            this.flag=flag;
            this.valid=valid;
        }


        public boolean isValid() { return valid; }


        public boolean match(int af) {
            return  (af & flag) ==flag;
        }
    }
}

