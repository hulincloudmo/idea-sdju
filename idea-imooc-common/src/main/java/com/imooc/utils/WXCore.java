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
        String appId = "wxbbc5d7b8d7205814";
        String encryptedData = "nqJ8VJcusmzqgttgmg7m1wPGmXGc5IpntOP7J5a543GpdbRBarJ9HsPG+43JAeQgbDnCCvCxHKxNKd/06E2OY3tKCu/EKEd+XLg4T0PzTt6p3oWXVqf1c24RFFEJiTftZJWfBooh5434KJV4bspDRx/Acz5IKO7XQ5NDmcINIBZ9Gj8qnV2yl0KIugENdXDDkarfcxq++MmMrJWX+mmnFOW/wh5k3c6I2S8pXXXgdNiAM1xYQlBgBL6/cRNbjoqYhTkzDgZXIOC3XdzOEVOft3RCi+C/T8jBYAySP+KC1Xm1KKM5fSVXPQEEtfD3yL1JuWNN1hqE60qXFvWqvkZ/RKiXeqRh3g/mcPMnQ6rYXG45R1ZobGgIi0R8IArCb5UhRYw0HsJy2IB+f3msrsM+YYqM1z7zP+XS2ZzIwErCP7/HxDrSghDGdlgPRH+W1N02wa4DMvbSpa2/DXDtBZ2XH4YPLk5CFVoRMWLv6oyWZeiGwRMO+10cj49dmxFk2ofPVyN6QRH/cfzPRvx8O61m3ug7akVGrTmR8SYHNPbjwGc=";
        String sessionKey = "8xVyTSH62z6AcJZbetvEIg==";
        String iv = "Ii6UY+Pk7M6i5aQloGva+A==";
        System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
    }

}
