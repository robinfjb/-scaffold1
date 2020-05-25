package robin.scaffold.baisc.security.util;




import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/*字符串 DESede(3DES) 加密 
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位 
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的 
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加 
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的 
 * 密钥，P代表明文，C代表密表，这样， 
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P))) 
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C))) 
 * */
public class DES3Utils {
	/**
	 * @param args在java中调用sun公司提供的3DES加密解密算法时
	 *            ，需要使 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包： jce.jar
	 *            security/US_export_policy.jar security/local_policy.jar
	 *            ext/sunjce_provider.jar 定义加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String ALGO = "DESede";
	private static final String key = "";
	public static void main(String[] args) {

		System.out.println("姓名：\n加密：" + do3Des("信而富") + "\n解密："
				+ de3Des("yDe/gdPcOgxXuBQibSU3Wg==")+"\n\n手机号：\n加密："+do3Des("13661953529")+ "\n解密："
				+ de3Des("rmYcCl5lQWUAEy7F43ym4Q=="));
	}


	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGO);
			// 加密
			Cipher c1 = Cipher.getInstance(ALGO);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src); // 在单一方面的加密或解密
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGO);
			// 解密
			Cipher c1 = Cipher.getInstance(ALGO);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	private static String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + ":";
			}
		}
		return hs.toUpperCase();
	}

	// 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位

	private static byte[] getKeyBytes(String strKey) throws Exception {
		/*
		 * if (null == strKey || strKey.length() < 1) { throw new
		 * Exception("key is null or empty!"); } java.security.MessageDigest alg
		 * = java.security.MessageDigest .getInstance("MD5");
		 * alg.update(strKey.getBytes()); byte[] bkey = alg.digest(); int start
		 * = bkey.length; byte[] bkey24 = new byte[24]; for (int i = 0; i <
		 * start; i++) { bkey24[i] = bkey[i]; } for (int i = start; i < 24; i++)
		 * { // 为了与.net16位key兼容 bkey24[i] = bkey[i - start]; }
		 */
		return strKey.getBytes("UTF-8");
	}

	/**
	 * 解密
	 * @param base64Msg
	 * @return
	 */
	public static String de3Des(String base64Msg) {
		String result = "";
		byte[] enk = null;
		try {
			enk = getKeyBytes(key);
			byte[] data2 = getByteStr(base64Msg);
			byte[] srcBytes = decryptMode(enk, data2);
			result = new String(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 解密
	 * @param base64Msg
	 * @return
	 */
	public static String de3Des(String base64Msg, String key) {
		String result = "";
		byte[] enk = null;
		try {
			enk = getKeyBytes(key);
			byte[] data2 = getByteStr(base64Msg);
			byte[] srcBytes = decryptMode(enk, data2);
			result = new String(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}




	/**
	 * 加密
	 * @param base64Msg
	 * @return
	 */
	public static String do3Des(String base64Msg) {
		String result = "";
		byte[] enk = null;
		try {
			enk = getKeyBytes(key);
			byte[] data2 = getByteStr(base64Msg);
			byte[] srcBytes = encryptMode(enk, base64Msg.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 加密
	 * @param base64Msg
	 * @return
	 */
	public static String do3Des(String base64Msg, String key) {
		String result = "";
		byte[] enk = null;
		try {
			enk = getKeyBytes(key);
			byte[] data2 = getByteStr(base64Msg);
			byte[] srcBytes = encryptMode(enk, base64Msg.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static byte[] getByteStr(String base64Msg) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(base64Msg);
		} catch (Exception var3) {
			var3.printStackTrace();
			return null;
		}
	}
}
