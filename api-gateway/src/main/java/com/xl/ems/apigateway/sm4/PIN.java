package com.xl.ems.apigateway.sm4;

import java.util.Arrays;

public class PIN {
    public static void printHexString(String hint, byte[] b) {
        System.out.print(hint);
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    public static String printHexString2(String hint, byte[] b) {
        System.out.print(hint);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
            sb.append(hex.toUpperCase());
        }
        System.out.println("");

        return sb.toString();
    }

    /**
     * transfer byte to hex string
     * @param b
     * @return
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }


    /**
     *
     * @param src0
     * @param src1
     * @return
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }


    /**
     * transfer hex string to byte[]
     * @param src
     * @return
     */
    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[8];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < 8; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * get pinblock8 for gj
     * @param pin
     * @param accno
     * @return
     */
    public static byte[] GenPinBlock8(String pin, String accno) {
        byte arrAccno[] = getHAccno(accno);
        byte arrPin[] = getHPin(pin);
        byte arrRet[] = new byte[8];

        for (int i = 0; i < 8; i++) {
            arrRet[i] = (byte) (arrPin[i] ^ arrAccno[i]);
        }
        printHexString("PinBlock：", arrRet);
        return arrRet;
    }


    /**
     * get pinblock16 for gm
     * @param pin
     * @param accno
     * @return
     */
    public static byte[] GenPinBlock16(String pin, String accno) {
        byte arrAccno[] = getHAccno16(accno);
        byte arrPin[] = getHPin16(pin);
        byte arrRet[] = new byte[16];
        for (int i = 0; i < 16; i++) {
            arrRet[i] = (byte) (arrPin[i] ^ arrAccno[i]);
        }

        printHexString("PinBlock16：", arrRet);
        return arrRet;
    }


    /**
     * get pin8 for gj
     * @param pin
     * @return
     */
    public static byte[] getHPin(String pin) {
        byte arrPin[] = pin.getBytes();
        byte encode[] = new byte[8];
        encode[0] = (byte) 0x06;
        encode[1] = (byte) uniteBytes(arrPin[0], arrPin[1]);
        encode[2] = (byte) uniteBytes(arrPin[2], arrPin[3]);
        encode[3] = (byte) uniteBytes(arrPin[4], arrPin[5]);
        encode[4] = (byte) 0xFF;
        encode[5] = (byte) 0xFF;
        encode[6] = (byte) 0xFF;
        encode[7] = (byte) 0xFF;
        printHexString("encoded pin：", encode);
        return encode;
    }

    /**
     * get pin8 for gm
     * @param pin
     * @return
     */
    public static byte[] getHPin16(String pin) {
        byte pin8[] = getHPin(pin);
        byte pin16[] = new byte[16];
        Arrays.fill(pin16, (byte) 0xFF);
        System.arraycopy(pin8, 0, pin16, 0, 8);
        printHexString("encoded pin16：", pin16);
        return pin16;
    }

    /**
     * get acctno for gj
     * @param accno
     * @return
     */
    public static byte[] getHAccno(String accno) {
        int len = accno.length();
        byte arrTemp[] = accno.substring(len < 13 ? 0 : len - 13, len - 1).getBytes();
        byte arrAccno[] = new byte[12];
        for (int i = 0; i < 12; i++) {
            arrAccno[i] = (i <= arrTemp.length ? arrTemp[i] : (byte) 0x00);
        }
        byte encode[] = new byte[8];
        encode[0] = (byte) 0x00;
        encode[1] = (byte) 0x00;
        encode[2] = (byte) uniteBytes(arrAccno[0], arrAccno[1]);
        encode[3] = (byte) uniteBytes(arrAccno[2], arrAccno[3]);
        encode[4] = (byte) uniteBytes(arrAccno[4], arrAccno[5]);
        encode[5] = (byte) uniteBytes(arrAccno[6], arrAccno[7]);
        encode[6] = (byte) uniteBytes(arrAccno[8], arrAccno[9]);
        encode[7] = (byte) uniteBytes(arrAccno[10], arrAccno[11]);
        printHexString("encoded accno：", encode);
        return encode;
    }

    /**
     * get accno for gm
     * @param accno
     * @return
     */
    public static byte[] getHAccno16(String accno) {
        byte[] accno8 = getHAccno(accno);
        byte[] accno16 = new byte[16];
        System.arraycopy(accno8, 0, accno16, 8, 8);
        return accno16;
    }

    /**
     * test for get pinblock8 pinblock16
     * @param args
     */
    public static void main(String[] args) {
        //16进制转字符串
     //   System.out.println(Util.hexStringToString("30363132333435364646464646464646",2));
        //字符串转16进制
        byte[] sss = getHPin("abcdef");
        String dfdg = printHexString2("",sss);
        System.out.println("sdaff:"+dfdg);
    }
}