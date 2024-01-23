package dev.gruff.hardstop.core.internal;

public class Utils {



public static String packageName(String className) {
        int ls=className.lastIndexOf("/");
        if(ls<0) return "<default>";
        return className.substring(0,ls).replace('/','.');

    }

    public static String classAccessFlags(int af) {
        StringBuilder sb=new StringBuilder();
        if((af & 0x0001) == 0x0001) sb.append("ACC_PUBLIC ");
        if((af & 0x0010) == 0x0010) sb.append("ACC_FINAL ");
        if((af & 0x0020) == 0x0020) sb.append("ACC_SUPER ");
        if((af & 0x0200) == 0x0200) sb.append("ACC_INTERFACE ");
        if((af & 0x0400) == 0x0400) sb.append("ACC_ABSTRACT ");
        if((af & 0x1000) == 0x1000) sb.append("ACC_SYNTHETIC ");
        if((af & 0x2000) == 0x2000) sb.append("ACC_ANNOTATION ");
        if((af & 0x4000) == 0x4000) sb.append("ACC_ENUM ");
        if((af & 0x8000) == 0x8000) sb.append("ACC_MODULE ");

        return sb.toString();
    }


    public static String toHexString(byte[] d) {

            StringBuilder result = new StringBuilder();
            for (byte b : d) {

                String hex = Integer.toHexString((int) b & 0xff);
                if (hex.length() % 2 == 1) {
                    hex = "0" + hex;
                }
                result.append(hex.toUpperCase());
            }
            return result.toString();
        }

    public static String fieldAccessFlags(int af) {

        StringBuilder sb=new StringBuilder();
        if((af & 0x0001) == 0x0001) sb.append("ACC_PUBLIC ");
        if((af & 0x0002) == 0x0002) sb.append("ACC_PRIVATE ");
        if((af & 0x0004) == 0x0004) sb.append("ACC_PROTECTED ");
        if((af & 0x0008) == 0x0008) sb.append("ACC_STATIC ");
        if((af & 0x0010) == 0x0010) sb.append("ACC_FINAL ");
        if((af & 0x0040) == 0x0040) sb.append("ACC_VOLATILE ");
        if((af & 0x0080) == 0x0080) sb.append("ACC_TRANSIENT ");
        if((af & 0x1000) == 0x1000) sb.append("ACC_SYNTHETIC ");
        if((af & 0x4000) == 0x4000) sb.append("ACC_ENUM ");


        return sb.toString();
    }

    public static String methodAccessFlags(int af) {
        StringBuilder sb=new StringBuilder();
        if((af & 0x0001) == 0x0001) sb.append("ACC_PUBLIC ");
        if((af & 0x0002) == 0x0002) sb.append("ACC_PRIVATE ");
        if((af & 0x0004) == 0x0004) sb.append("ACC_PROTECTED ");
        if((af & 0x0008) == 0x0008) sb.append("ACC_STATIC ");
        if((af & 0x0010) == 0x0010) sb.append("ACC_FINAL ");
        if((af & 0x0020) == 0x0020) sb.append("ACC_SYNCHRONIZED ");
        if((af & 0x0040) == 0x0040) sb.append("ACC_BRIDGE ");
        if((af & 0x0080) == 0x0080) sb.append("ACC_VARARGS ");
        if((af & 0x0100) == 0x0100) sb.append("ACC_NATIVE ");
        if((af & 0x0400) == 0x0400) sb.append("ACC_ABSTRACT ");
        if((af & 0x0800) == 0x0800) sb.append("ACC_STRICT ");
        if((af & 0x1000) == 0x1000) sb.append("ACC_SYNTHETIC ");


        return sb.toString();
    }
}
