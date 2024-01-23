package dev.gruff.hardstop.core.internal;

import java.io.IOException;

public class ConstantPoolParser {

    private boolean verbose=false;
    public ConstantPoolParser(boolean verbose) {
        this.verbose=verbose;
    }

    public ConstantPool parse(ByteReader p) throws IOException {

        int cpc= p.readU2("constant pool count");

           report(0,"constants :"+cpc);

        if(cpc<0) throw new ParseException("unexpected < 0 constant pool count");

        ConstantPool cp=new ConstantPool(cpc);


        for(int c=0;c<(cpc-1);c++) {
            int tag= p.readU1("CONSTANT_MethodHandle reference kind");
            int mapIndex=c+1;
            ConstantPoolEntry cpeTag=ConstantPoolEntry.entry(tag);

            switch(cpeTag) {
                case CONSTANT_Unknown:
                    report(0,"unknown constant pool tag entry :"+tag);
                    throw new ParseException("unknown constant pool tag entry :"+tag);
                case CONSTANT_Class:
                    int index= p.readU2("CONSTANT_Class index");
                    cp.addClass(index);
                    break;
                case CONSTANT_Methodref:
                    int class_index= p.readU2("CONSTANT_Methodref class index");
                    int type_index= p.readU2("CONSTANT_Methodref type index");
                    cp.addMethodref(class_index,type_index);
                    break;
                case CONSTANT_Utf8:
                    int utf8Length= p.readU2("CONSTANT_Utf8 length");
                    byte[] data =new byte[utf8Length];
                    p.readBytes(data);
                    cp.addUtf8(data);
                    break;
                case CONSTANT_NameAndType:
                    int name_index= p.readU2("CONSTANT_NameAndType name index");
                    int descriptor= p.readU2("CONSTANT_NameAndType descriptor");
                    cp.addNameAndType(name_index,descriptor);
                    break;
                case CONSTANT_String:
                    int string_index= p.readU2("CONSTANT_String index");
                    cp.addString(string_index);
                       break;
                case CONSTANT_Fieldref:
                    class_index=p.readU2("CONSTANT_Fieldref class index");
                    int name_and_type_index=p.readU2("CONSTANT_Fieldref name and type index");
                    cp.addFieldref(class_index,name_and_type_index);
                    break;
                case CONSTANT_InterfaceMethodref:
                    class_index=p.readU2("CONSTANT_InterfaceMethodref class_index");
                    name_and_type_index=p.readU2("CONSTANT_InterfaceMethodref name_and_type_index");
                    cp.addInterfaceMethodref(class_index,name_and_type_index);
                    break;
                case CONSTANT_InvokeDynamic:
                    int bootstrap_method_attr_index=p.readU2("CONSTANT_InvokeDynamic bootstrap_method_attr_index");
                    name_and_type_index=p.readU2("CONSTANT_InvokeDynamic name_and_type_index");
                    cp.addInvokeDynamic(bootstrap_method_attr_index,name_and_type_index);
                    break;
                case CONSTANT_MethodHandle:
                    int reference_kind=p.readU1("CONSTANT_MethodHandle reference kind");
                    int reference_index=p.readU2("CONSTANT_MethodHandle reference index");
                    cp.addMethodHandle(reference_kind,reference_index);
                    break;
                case CONSTANT_Integer:
                    long ivalue=p.readU4("CONSTANT_Integer");
                    cp.addInteger(ivalue);
                    break;
                case CONSTANT_Long:
                    byte[] l=p.readBytes(8);
                    cp.addLong(l);
                    c++;
                    break;
                case CONSTANT_Double:
                    byte[] d=p.readBytes(8);
                    cp.addDouble(d);
                    c++;
                    break;
                case CONSTANT_Float:
                    long fvalue=p.readU4("CONSTANT_Float");
                    cp.addFloat(fvalue);
                    break;
                case CONSTANT_MethodType:
                    int di=p.readU2("CONSTANT_MethodType");
                    cp.addMethodType(di);
                    break;
                case CONSTANT_Module:
                    int ni=p.readU2("CONSTANT_Module");
                    cp.addModule(ni);
                    break;
                case CONSTANT_Package:
                    ni=p.readU2("CONSTANT_Package");
                    cp.addPackage(ni);
                    break;

                default: throw new ParseException("? on "+tag);
            }
        }


        // now validate

        for(int c=1;c<(cpc-1);c++) {
            ConstantPoolEntry ceTag=cp.getEntryType(c);

            switch( ceTag) {
                case CONSTANT_Class:
                    String className=cp.getResolvedClassName(c);
                    report(0,"Class Ref "+className);
                    break;
                case CONSTANT_Methodref:
                    String methodName=cp.getResolvedMethodName(c);
                    report(0,"Method Ref "+methodName);
                    break;

                case CONSTANT_String:

                    String s=cp.getResolvedStringRef(c);
                 report(0,"String Ref ["+s+"]");
                    break;
                case CONSTANT_Utf8:
                case CONSTANT_NameAndType:
                case CONSTANT_Fieldref:
                case CONSTANT_InterfaceMethodref:
                case CONSTANT_InvokeDynamic:
                case CONSTANT_MethodHandle:
                case CONSTANT_Integer:
                case CONSTANT_Long:
                case CONSTANT_Double:
                case CONSTANT_Float:
                case CONSTANT_EMPTY:
                case CONSTANT_MethodType:
                case CONSTANT_Module:
                case CONSTANT_Package:
                    break;
                default: throw new ParseException("? "+ceTag);
            }

        }

        return cp;

    }

    private void report(int i, String s) {
        if(verbose) System.out.println(s);
    }


}
