package dev.gruff.hardstop.core.internal;

public class ConstantPool {



    private Object[][] table;
    private int tIndex;

    private boolean verbose=false;
    public ConstantPool(int cpc) {
        table=new Object[cpc][];
    }
    public void addClass(int index) {

        insert(new Object[]{ConstantPoolEntry.CONSTANT_Class,index});

        if(verbose) System.out.println(tIndex+" Constant: Class "+index);
    }

    private void insert(Object[] entry) {
        if(entry==null) throw new RuntimeException("entry is null");
        if(entry.length<1) throw new RuntimeException("entry is short "+entry.length);
        if(entry[0]==null) throw new RuntimeException("entry is missing type");
        tIndex++;
        table[tIndex]=entry;
        if(verbose) System.out.println("added "+tIndex+" : "+entry[0]);
    }

    public void addFieldref(int class_index, int name_and_type_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Fieldref,class_index,name_and_type_index});
    }
    public void addMethodref(int class_index, int type_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Methodref,class_index,type_index});
        if(verbose) System.out.println(tIndex+" Constant: Method Ref "+class_index+"/"+type_index);
    }
    public void addInterfaceMethodref(int class_index, int name_and_type_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_InterfaceMethodref,class_index,name_and_type_index});
    }
    public void addString(int string_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_String,string_index});
        if(verbose) System.out.println(tIndex+" Constant: String  "+string_index);

    }
    public void addInteger(long ivalue) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Integer,ivalue});
        if(verbose) System.out.println(tIndex+" Constant: Integer  "+ivalue);


    }
    public void addFloat(long f) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Float,f});
        if(verbose) System.out.println(tIndex+" Constant: Float");

    }
    public void addLong	(byte[] l) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Long,l});
        if(verbose) System.out.println(tIndex+" Constant: Long");
        insert(new Object[]{ConstantPoolEntry.CONSTANT_EMPTY});

    }
    public void addDouble(byte[] d) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Double,d});
        if(verbose) System.out.println(tIndex+" Constant: Double");
        insert(new Object[]{ConstantPoolEntry.CONSTANT_EMPTY});
    }
    public void addNameAndType	(int name_index, int descriptor) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_NameAndType,name_index,descriptor});
        if(verbose) System.out.println(tIndex+" Constant: Name and Type "+name_index+"/"+descriptor);
    }
    public void addUtf8(byte[] data) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Utf8,data});
        if(verbose) System.out.println(tIndex+" Constant: UTF8 String "+data.length+" long ("+new String(data));
    }
    public void addMethodHandle	(int reference_kind, int reference_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_MethodHandle,reference_kind,reference_index});
    }
    public void addMethodType(int di) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_MethodType,di});
    }
    public void addInvokeDynamic(int bootstrap_method_attr_index, int name_and_type_index) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_InvokeDynamic,bootstrap_method_attr_index,name_and_type_index});
    }

    public void addModule(int ni) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Module,ni});
    }


    public void addPackage(int ni) {
        insert(new Object[]{ConstantPoolEntry.CONSTANT_Package,ni});
    }
    public int getClassReference(int idx) {
        Object[] data=table[idx];
        return (int) table[idx][1];
    }

    public int getStringReference(int idx) throws ParseException {
        Object[] data=table[idx];
        checkType(idx,data, ConstantPoolEntry.CONSTANT_String);
        return (int) table[idx][1];
    }

    private void checkType(int idx,Object[] data, ConstantPoolEntry expected) throws ParseException {
        ConstantPoolEntry type= (ConstantPoolEntry) data[0];
        if(type!=expected) throw new ParseException("constant pool entry "+idx+" expected "+expected+" found "+type);
    }

    public ConstantPoolEntry getEntryType(int idx) {
        Object[] data=table[idx];
        return (ConstantPoolEntry) table[idx][0];
    }

    public int getMethodRefClass(int idx) {
        Object[] data=table[idx];
        return (int) table[idx][1];
    }

    public int getMethodRefType(int idx) {
        Object[] data=table[idx];
        return (int) table[idx][2];
    }

    public int getNameTypeReference(int idx) {
        Object[] data=table[idx];
        return (int) table[idx][2];
    }

    public byte[] getUTF8(int idx) {
         Object[] data=table[idx];
         if(data==null) throw new RuntimeException("empty at "+idx);
            if(data[0]!= ConstantPoolEntry.CONSTANT_Utf8) throw new RuntimeException(" got "+data[0]);
        return (byte[]) table[idx][1];
    }

    public String getUTF8AsString(int idx) {
       return new String(getUTF8(idx));
    }

    public int getString(int idx) {
        Object[] data=table[idx];
        return (int) table[idx][1];
    }

    public String getResolvedClassName(int idx) {

        int classRef= getClassReference(idx);
        return getUTF8AsString(classRef);

    }

    public String getResolvedMethodName(int idx) {

        int class_index=getMethodRefClass(idx);
        int type_index=getMethodRefType(idx);
        int classRef=getClassReference(class_index);
        int methodRef=getNameTypeReference(type_index);
        String className=getUTF8AsString(classRef);
        String methodName=getUTF8AsString(methodRef);

        return className+"@"+methodName;

    }

    public String getResolvedStringRef(int idx) throws ParseException {
         int string_index=getStringReference(idx);
         return getUTF8AsString(string_index);

    }

}
