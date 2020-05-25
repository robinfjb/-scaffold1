package robin.scaffold.baisc.security.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import robin.scaffold.baisc.security.jni.JniManager;


/**
 * <p>简介：RSA加密，信而富验签
 * <p>
 * <p>使用方法举例： </p>
 * <p> 签名：<br/>
 * Map<String, String> test = new HashMap<String, String>();<br/>
 * test.put("name", "fang");<br/>
 * test.put("age", "100");<br/>
 * //首先要设置APP_SECRET，否则会抛异常<br/>
 * SecurityUtils.setAppSecret(Constants.APP_SECRET);<br/>
 * String sign = SecurityUtils.signature(test);<br/>
 * Log.e("RSA", "sign=" + sign);<br/>
 * </p>
 * <p>加密：<br/>
 * String test = "中文测试"<br/>
 * //首先要设置public key，否则会抛异常<br/>
 * SecurityUtils.setPublickKey(Constants.PUBLIC_KEY);<br/>
 * String encrypt = SecurityUtils.encryptDataByPublicKey(test);<br/>
 * Log.e("RSA", "encrypt=" + encrypt);<br/>
 * </p>
 */
public class SecurityUtils {
    private static final String TAG = "SecurityUtils";

    private static boolean OPEN_ENCRYPT = true;// 是否开启加密

    private static final String APP_SAND_TAG = "hahaha";//沙子
    private static final String UTF8 = "UTF-8";
    private static final String PARAM_SIGN = "signature";

    public static Integer releaseType = 0;// 公钥类型

    public static String signature(Context context, Map<String, Object> params) throws SecurityException {
        if (params == null) return null;
        if (params.containsKey(PARAM_SIGN)) return params.get(PARAM_SIGN).toString();
        StringBuilder sb = new StringBuilder(params.size() * 5);
        List<String> paramNames = new ArrayList<String>(params.keySet());
        Collections.sort(paramNames);
        for (int i = 0; i < paramNames.size(); i++) {
            String key = paramNames.get(i);
            Object valueObj = params.get(key);
            if (valueObj == null) {
                continue;
            }
            String value = valueObj.toString();
            String encodedK = null;
            String encodedV = null;
            try {
                encodedK = URLEncoder.encode(key, UTF8);
                encodedV = URLEncoder.encode(value, UTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(encodedK) /*|| TextUtils.isEmpty(encodedV)*/) continue;
            if (i != 0) {
                sb.append("&");
            }
            sb.append(encodedK);
            sb.append('=');
            sb.append(encodedV);
        }
        JniManager instance = new JniManager();
        String secret = instance.getSecret(context);
        if (secret == null)
            throw new SecurityException("签名不一致");
        //对报文+沙子进行签名
        String test = sb.toString() + APP_SAND_TAG + secret + APP_SAND_TAG;
        return RSAUtil.getSHA256StrJava(test);
    }

    public static String encryptDataByPublicKey(Context context, String data) throws SecurityException {
        if (!OPEN_ENCRYPT) return data;
        if (RSAUtil.isPublicKeyEmpty()) {
            JniManager instance = new JniManager();
            String base64 = instance.getPublicKey(context);
            if (base64 == null)
                throw new SecurityException("签名不一致");
            String publicKey = DES3Utils.de3Des(base64);
            if (publicKey == null)
                throw new SecurityException("DES解密失败");
            RSAUtil.setPublickKey(publicKey);
        }
        return RSAUtil.encryptDataByPublicKey(data);
    }
}
