package robin.scaffold.baisc.util;

import androidx.annotation.NonNull;

import java.net.URLDecoder;

import robin.scaffold.baisc.util.Parameters;


public class UrlUtils {
    @NonNull
    public static Parameters getParamsFromUrl(String url) {
        Parameters parameters = null;
        if (url != null && url.indexOf('?') > -1) {
            String tempUrl = url.substring(url.indexOf('?') + 1);
            int indexSharp = tempUrl.indexOf('#');
            if(indexSharp > -1) {
                parameters = splitUrlQuery(tempUrl.substring(0, indexSharp));
            } else {
                parameters = splitUrlQuery(tempUrl);
            }
        }
        if (parameters == null) {
            parameters = new Parameters();
        }
        return parameters;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query
     *            URL地址
     * @return 参数映射
     */
    private static Parameters splitUrlQuery(String query) {
        Parameters parameters = new Parameters();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    try {
                        parameters.addParameter(URLDecoder.decode(param[0], "UTF-8"), URLDecoder.decode(param[1], "UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return parameters;
    }
}
