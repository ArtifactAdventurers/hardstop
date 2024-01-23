package dev.gruff.hardstop.core.internal;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ByteReader implements Closeable {

    DataInputStream dis;
    MessageDigest md5er;
    byte[] b1=new byte[1];
    byte[] b2=new byte[2];
    byte[] b4=new byte[4];

    public ByteReader(InputStream is) {

        dis = new DataInputStream(is);
        try {
            md5er=MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] digest() {
        return md5er.digest();
    }

    public long readU4(String desc) throws IOException {
        try {
            long i= readU2();
            i=i << 16;
            i+=readU2();
        //    System.out.println(desc+" :0x"+ Long.toHexString(i));
            return i;
        } catch(IOException e) {
            throw new ParseException(desc);
        }
    }

    public long readU4() throws IOException {


        long result = 0;

        for (int i = 0; i < b2.length; i++) {
            result += b4[i] << 8 * (b4.length - 1 - i);
        }
        return result;


    }

    public int readU2(String desc) throws IOException {
        try {
         int i= readU2();
      //  System.out.println(desc+" :0x"+ Integer.toHexString(i));
        return i;
        } catch(IOException e) {
            throw new ParseException(desc);
        }
    }
    private int readU2() throws IOException {

        readBytes(b2);
        int ch1 = Byte.toUnsignedInt(b2[0]);
        int ch2 = Byte.toUnsignedInt(b2[1]);
        int l= (ch1 << 8) + (ch2 << 0);

        return l;
    }
    public int readU1(String desc) throws IOException {

        try {
            readBytes(b1);
            int i =b1[0];
          //  System.out.println(desc + " :0x" + Long.toHexString(i));
            return i;
        } catch (IOException e) {
            throw new ParseException(desc);
        }
    }


    public void readBytes(byte[] array) throws IOException {

            dis.readFully(array);
            md5er.update(array,0,array.length);

    }

    public byte[] readBytes(long attributeLength) throws IOException {
        byte[] d=new byte[(int) attributeLength];
        readBytes(d);
        return d;
    }

    public boolean isEOF() throws IOException {

            return dis.available()<1;

    }


    @Override
    public void close() throws IOException {
        dis.close();
    }
}
