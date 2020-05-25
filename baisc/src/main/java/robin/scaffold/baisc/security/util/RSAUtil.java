package robin.scaffold.baisc.security.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加密解密，分别支持私钥加密公钥解密，以及公钥加密私钥解密
 *  RSA私钥签名，公钥验签
 */
class RSAUtil {
    private static final String RSA = "RSA";
    private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式,兼容java端
    private static final int KEY_SIZE = 1024;
    private static final int MAX_ENCRYPT_BLOCK = (KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数
    private static final int MAX_DECRYPT_BLOCK = (KEY_SIZE / 8);
    private static final String UTF8 = "UTF-8";

    private static String mPublicKeyStr;
    private static PublicKey mPublicKey;

    public static void setPublickKey(String publickKey) {
        mPublicKeyStr = publickKey;
    }

    public static boolean isPublicKeyEmpty() {
        return TextUtils.isEmpty(mPublicKeyStr);
    }

    /**
     * 公钥加密
     * 注：公钥加密每次结果会不一样
     * @param data 需加密数据的数据 String
     * @return 加密后的数据 String
     */
    public static String encryptDataByPublicKey(String data) throws SecurityException {
        if(TextUtils.isEmpty(data))
            return data;
        if(mPublicKey == null) {
            if(mPublicKeyStr == null) {
                throw new SecurityException("you need set public key first!!!");
            }
            try {
                mPublicKey = getPublicKey(mPublicKeyStr);
            } catch (Exception e) {
                throw new SecurityException(e);
            }
        }
        if(mPublicKey == null) return null;
        byte[] resultBytes = null;
        try {
            resultBytes = encryptData(data.getBytes(UTF8), mPublicKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(resultBytes == null) return null;
        try {
            return new String(Base64.encode(resultBytes, Base64.NO_WRAP), UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥解密
     *
     * @param encryptData 加密的数据
     * @return
     */
    public static String decryptDataByPublicKey(String encryptData) throws SecurityException {
        if(TextUtils.isEmpty(encryptData))
            return encryptData;
        if(mPublicKey == null) {
            if(mPublicKeyStr == null) {
                throw new SecurityException("you need set public key first!!!");
            }
            try {
                mPublicKey = getPublicKey(mPublicKeyStr);
            } catch (Exception e) {
                throw new SecurityException(e);
            }
        }
        if(mPublicKey == null) return null;
        byte[] resultBytes = decryptData(Base64.decode(encryptData.getBytes(), Base64.NO_WRAP), mPublicKey);
        if(resultBytes == null) return null;
        try {
            return new String(resultBytes, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptData(byte[] data, PublicKey publicKey) {
        if(data == null || publicKey == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {//待加密的字节数不能超过密钥的长度值除以 8 再减去 11（即：KeySize / 8 - 11）
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptData(byte[] data, PrivateKey privateKey) {
        if(data == null || privateKey == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {//待加密的字节数不能超过密钥的长度值除以 8 再减去 11（即：KeySize / 8 - 11）
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        if(encryptedData == null || privateKey == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {//待解密的字节数不能超过密钥的长度值除以 8
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decryptData(byte[] encryptedData, PublicKey publicKey) {
        if(encryptedData == null || publicKey == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {//待解密的字节数不能超过密钥的长度值除以 8
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(key, Base64.NO_WRAP));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(key, Base64.NO_WRAP));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String getSHA256StrJava(String str) {
        String encodeStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}
