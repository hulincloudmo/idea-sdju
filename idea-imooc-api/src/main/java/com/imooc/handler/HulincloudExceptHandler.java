package com.imooc.handler;

import com.imooc.utils.HulincloudJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.handler
 * @ClassName: HulincloudExceptHandler
 * @Author: hulincloud
 * @Description: 异常捕获
 * @Date: 2019/7/2 10:52
 * @Version: 1.0
 */

@RestControllerAdvice
public class HulincloudExceptHandler{


    @ExceptionHandler(value = Exception.class)
    public HulincloudJSONResult defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception {
//        e.printStackTrace();
        return HulincloudJSONResult.errorException("服务器错误，请联系后端开发人员");
    }

}
