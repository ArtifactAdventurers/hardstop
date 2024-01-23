package dev.gruff.hardstop.core.impl;



import dev.gruff.hardstop.core.internal.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ClassFileReaderImpl {


    private boolean verbose=false;
    private static final long CF= 0xCAFEBABEL;

    private ConstantPool constantPool;

    private HSComponentImpl model;
    public ClassFileReaderImpl(boolean verbose) {
            this.verbose=verbose;
    }


    public HSClassImpl parse(InputStream is) throws IOException {


        ByteReader br=new ByteReader( is);

        HSClassImpl c = parseHeader(br);

        // read the constant pool

        ConstantPoolParser cpp=new ConstantPoolParser(verbose);
        c.constantPool=cpp.parse(br);

        c.accessFlags=br.readU2("access_flags");

        c.this_class=br.readU2("this_class");
        c.super_class=br.readU2("super_class");

        c.interfaces_count=br.readU2("interface_count");
        c.interfaces=new int[c.interfaces_count];
        if(c.interfaces_count>0) {
            for(int i=0;i<c.interfaces_count;i++) {
                c.interfaces[i]=br.readU2("interface idx "+i);
            }
       }
        c.fields_count=br.readU2("fields_count");
        if(c.fields_count>0) {
           // c.fields=new RavenClassImpl.Field[c.fields_count];
            for(int f=0;f<c.fields_count;f++) {
                HSClassImpl.Field field=readField(f,c,br);
                String ref=field.reference();
                if(c.fields.containsKey(ref)) {
                    throw new ParseException("duplicate field "+ref);
                }
                c.fields.put(ref,field);
            }
        }


    c.methods_count=br.readU2("methods_count");
    if(c.methods_count>0) {
        for(int m=0;m<c.methods_count;m++) {
            HSClassImpl.Method method=readMethod(m,c,br);
           boolean b= c.addMethod(method);
           if(!b) throw new ParseException("duplicate method "+method.reference());
        }
    }

    c.attributes_count=br.readU2("attributes_count");
    c.attributes=readAttributes("class ", c,c.attributes_count, br);

    c.extraneousData=!br.isEOF();

    if(!c.extraneousData)  c.valid=true;
    c.digest=br.digest();

  //  System.out.println("----");
        return c;

    }


    private HSClassImpl parseHeader(ByteReader br) throws IOException {
        HSClassImpl c=new HSClassImpl();

        // look for good old CAFE BABE
        c.header = br.readU4("header");

        if(c.header != ClassFileReaderImpl.CF) {
            c.valid=false;
           return c;
        }

        c.minor=br.readU2("minor");
        c.major=br.readU2("major");

        return c;
    }

    private HSClassImpl.Method readMethod(int mc, HSClassImpl c, ByteReader br) throws IOException {
            HSClassImpl.Method m=c.new Method();
           m.access_flags=br.readU2(mc+":method access_flags");
            m.name_index=br.readU2(mc+":method name_index");
            m.descriptor_index=br.readU2(mc+":method descriptor index");

        m.attributes_count=br.readU2(mc+":method attribute count");


            if(m.attributes_count>0) {
               m.attributes= readAttributes("method " + mc, c,m.attributes_count, br);
            }

            return m;
    }

    private HSClassImpl.Field readField(int x, HSClassImpl c, ByteReader br) throws IOException {

        HSClassImpl.Field f=c.new Field();
            f.access_flags=br.readU2(x+":field access_flags");
            f.name_index=br.readU2(x+":field name_index");
            f.descriptor_index=br.readU2(x+":field descriptor index");



      //  report(0,"Field : "+name+" @ "+descriptor);

      f.attributes_count=br.readU2("field attributes_count");
        if(f.attributes_count>0) {
            f.attributes = readAttributes("field "+x,c,f.attributes_count,br);
        }


        return f;
    }

    private Attributes readAttributes(String s, HSClassImpl c, int attributes_count, ByteReader br) throws IOException {


        Attributes attrs=new Attributes();

        if(attributes_count>0) {
            for(int a=0;a<attributes_count;a++) {
                attrs.add(readAttribute(s,c,br));
            }
        }
        return attrs;
    }

    private HSClassImpl.AttributeImpl readAttribute(String desc, HSClassImpl c, ByteReader br) throws IOException {

        HSClassImpl.AttributeImpl attr=c.new AttributeImpl();
           attr.attribute_name_index=br.readU2(desc+":attribute name index");
          attr.attribute_length=br.readU4(desc+": length");
           attr.data=br.readBytes(attr.attribute_length);


       // report(0,"Attribute N: "+name);
       // report(0,"Attribute L: "+attribute_length);

      //  switch (name) {
       //     case "InnerClasses" :
        //        parseInnerClassesAttribute(data);
         //       break;
         //   case "ConstantValue" :
         //       parseConstantValueAttribute(data);
          //      break;
        //}

        return attr;
    }

    private void parseConstantValueAttribute(byte[] data) throws IOException {
        ByteArrayInputStream bis=new ByteArrayInputStream(data);
        ByteReader b=new ByteReader(bis);
        int  constantValue=b.readU2("constant_value");


    }

    private void parseInnerClassesAttribute(byte[] data) throws IOException {


        ByteArrayInputStream bis=new ByteArrayInputStream(data);
        ByteReader b=new ByteReader(bis);
            int  number_of_classes=b.readU2("inner classes count");
            if(number_of_classes>0) {
                for(int a=0;a<number_of_classes;a++) {
                    report(0,"inner class: "+a);
                    int inner_class_info_index=b.readU2("inner_class_info_index");
                    int outer_class_info_index=b.readU2("outer_class_info_index");
                    int inner_name_index=b.readU2("inner_name_index");
                    int inner_class_access_flags=b.readU2("inner_class_access_flags");
                    //System.out.println(""+inner_class_info_index+"/"+outer_class_info_index+"/"+inner_name_index);
                        if(inner_name_index==0) {
                            report(0,"Inner : ANONYMOUS");
                        } else {
                            String innerClassName = constantPool.getUTF8AsString(inner_name_index);
                            report(0,"Inner : " + innerClassName);
                        }
                }
            }

    }

    private void report(int i, String s) {
        if(verbose) System.out.println(s);
    }



    public String version(int major,int minor) {
        if(major<45 || major>200) return "invalid";
        if(major>48) {
            if(major<51) {
                if(major==50) return "6.0";
                else return "5.0";
            } else {
                int dv=(major-51)+7;
                return ""+dv;
            }
        } else {
            int dv=major-44;
            return "1."+dv;
        }
    }
}
