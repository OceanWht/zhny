package com.xl.ems.ynnhjc.sm4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by
 * SMS4函数方法
 *
 * @Auther:liuzhengliang
 * @Date: 2018/11/12
 */
public class SMS4Main {
    private String secretKey = "";
    private String iv = "";
    private boolean hexString = false;


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public boolean isHexString() {
        return hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    public SMS4Main() {

    }

    /**
     * SM4 Map加密
     *
     * @param map
     * @return String
     */
    public static String sm4MapEncrypt(Map map) {
        String jsonString = JSON.toJSONString(map);
        return new SMS4Main().encryptData_CBC(jsonString);
    }

    /**
     * SM4 解密到Map
     *
     * @param entext
     * @return
     */
    public static Map<String, Object> sm4DecryptToMap(String entext) {
        String dada = new SMS4Main().decryptData_CBC(entext);
        JSONObject jsonObject = JSONObject.parseObject(dada);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        return map;
    }

    public String encryptData_ECB(String plainText) {

        try {
            SMS4Context ctx = new SMS4Context();
            ctx.isPadding = true;
            ctx.mode = SMS4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = SMS4Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SMS4 SMS4 = new SMS4();
            SMS4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = SMS4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
            String sss = SMS4Util.getHexString(encrypted,true);
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            SMS4Context ctx = new SMS4Context();
            ctx.isPadding = true;
            ctx.mode = SMS4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = SMS4Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SMS4 SMS4 = new SMS4();
            SMS4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = SMS4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText) {
        try {
            SMS4Context ctx = new SMS4Context();
            ctx.isPadding = true;
            ctx.mode = SMS4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = SMS4Util.hexStringToBytes(secretKey);
                ivBytes = SMS4Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SMS4 SMS4 = new SMS4();
            SMS4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = SMS4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
          //  String cipherText = new BASE64Encoder().encode(encrypted);

            String cipherText = SMS4Util.getHexString(encrypted,true);
            /*if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }*/
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText) {
        try {
            SMS4Context ctx = new SMS4Context();
            ctx.isPadding = true;
            ctx.mode = SMS4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = SMS4Util.hexStringToBytes(secretKey);
                ivBytes = SMS4Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SMS4 SMS4 = new SMS4();
            SMS4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = SMS4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String initPass = "123456";
        String pinBlock = PIN.printHexString2("", PIN.getHPin(initPass));
        SMS4Main sms4Main = new SMS4Main();
        sms4Main.setSecretKey("DqIaOoP6FW4XKsyr");
        String iv = "9153250179986756";
        sms4Main.setIv(iv);
        String entext = sms4Main.encryptData_CBC(pinBlock);
        entext = entext.substring(0, 32);
        System.out.println(entext);

    }
}
