package com.imooc.utils;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;


/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.utils
 * @ClassName: WXCore
 * @Author: hulincloud
 * @Description: WXCore
 * @Date: 2019/5/31 16:02
 * @Version: 1.0
 */
public class WXCore {
    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";
    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if(!appId.equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) throws Exception{
        String appId = "wxcb11a98d2ba83be8";
        String encryptedData = "lQhNZ4v4b5iPDKtug4UlZXqzFClOKJLCqIB/DYZSble8/+1ZGYFz/Sew2aucTv646+0eNBZCa1IsetxoNURzdV35RMLqXDCYj9qdFubf18tkdrdptyfMEtI7MayQVkDEz4mmGikOa8u3kMMXR5hOnclQFs5+G7u/RFUwkQzlVelhFb+IUqTeDrIqfz0XA5ILeeFzc0jTnDwWhQmJzLSgH/YTIHG9n13OmCFXAhQO6M1BxSFKdr1dVHy/ykWlCs7Z8uEOUaxo+XalursA+Ex/moO7LwBcmGqb3xmaN/lhj4SOi8weuq00YiwvG4Y/eYvx1aGurOEueaFWOSIMuccdyd9fmKmK87oSwQ0v+C4M6xhsBUl2M8FdI3leFP9fq0Ecy3hl9GppqN6D5icEg3EUqWpJ+OgTZKXM1LZtjd1OtoFaQc/cOi6Wjnq5iezlxEcp2MvhQFGavEpOJh3ttE3UcUP5y/4S9TvLed9QTtETMhlD2oxmIj+ojXC8ZCZ2SbAQt/X24yaZSaQTnN/Xw1wdwH/hgbfPFDnGDA1/JYMdp/A=";
        String sessionKey = "7mEsAa79WzvhHCruQ4TUaA==";
        String iv = "FdsNKTxoWqLOukAju6LrFA==";
        System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
    }

}
