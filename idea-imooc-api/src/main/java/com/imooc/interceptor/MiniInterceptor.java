package com.imooc.interceptor;

import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.interceptor
 * @ClassName: MiniInterceptor
 * @Author: hulincloud
 * @Description: 拦截器
 * @Date: 2019/7/1 22:05
 * @Version: 1.0
 */
public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redisOperator;
    public static final String USER_REDIS_SESSION = "user-redis-session";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)){

            String uniqueToken = redisOperator.get(USER_REDIS_SESSION + ":" + "userId");

            if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)){
                System.out.println("请登录");
                returnErrorResponse(response, HulincloudJSONResult.errorMsg("登录已失效，请重新登录"));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)){
                    System.out.println("别的手机登录");
                    returnErrorResponse(response, HulincloudJSONResult.errorTokenMsg("别的手机登录"));
                    return false;
                }
            }

        }else {
            returnErrorResponse(response, HulincloudJSONResult.errorTokenMsg("请登录"));
            return false;
        }

        return true;
    }

    public void returnErrorResponse(HttpServletResponse response, HulincloudJSONResult result) throws IOException, UnsupportedEncodingException {
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            outputStream = response.getOutputStream();
            outputStream.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            outputStream.flush();
        }finally {
            if (outputStream != null){
                outputStream.close();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
