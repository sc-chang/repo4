package com.qfedu.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.alipay.api.internal.util.AlipaySignature.rsaCheckV1;
import static java.lang.Character.FORMAT;
import static org.apache.catalina.manager.Constants.CHARSET;

@RestController
public class AlipayController {

    @RequestMapping("/pay")
    @ResponseBody
    public String pay(HttpServletRequest httpRequest, HttpServletResponse httpResponse)   throws ServletException, IOException {
        AlipayClient alipayClient =  new DefaultAlipayClient( "https://openapi.alipaydev.com/gateway.do" , "2021000119632774", "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCURANAFGJUJfZJsQTn70Fnl7jbcQrstZAfV6m4vjY1tQ0w8gqbfkGjZrGHgN6PKZn0gcssqqoUJ5kwJwSQ3nT44m350V+lA2C9H/S0VAj9nr5Q4XcxYUQq+eG4tOjQCB9t7YoraUMZLsqEQmMqqoNCC0QwrJwp7+XiMBf0Q3CvWrlc1eWw5MRlmNnVcvivKg7FCNCs7/chZN7fKNUPtTQqLPJ3/SR0mL8FYzzVBPKRfP3T3EGJEOewiibRiJrKU/wakoWuCxWQUA0JovCPByWLvlBB97rHMJHlYUy9m7QOrHi/jY+Ef/rouIA2YSAIFgx22QP31tpkHsYyIlHIIM15AgMBAAECggEAaIV96Rm1q+7LLm4dsEgpypbaGdEaS38/p+jL3FIzsy78qqmnMrESIthNhOIjg7dnMkih684wcl7sDlcjT+GTgrVVpEym6ZR1CT7S6qFnaSwUrL4VofkY7lcUensGuUlQptVTl4DwbXtgB1cpw7VLLS72Fn5I5QObAxa2fFtiwCrTQ1Oi3rbRyi86ObLe517M3kC0i7DPu9Af4XqOTqmqOaSFGWwdb+AiGQxLpY1EfLxk4HXgLHQTv/QVPumlSvxEPlSBYgVcQWPTEyO6VmpDBZJ+LhGiFir8UJlGGn9Kmb1CuuWA/YPjqzRAvWwwNP4pqf/lA3KYBMVDUhfDto0XgQKBgQDwad6W21eGHOD98+vN+v73NSf8Dz/eArRR2ZfoLkUIhYW47IAH5BuUGS3nfE6swehy5cey4Ms3QbbSV0vW4+5hbLu8IcFbWaWqEcXxk57JgKmLoEVZO6idYRVXdQX2yUXsAP/Qi1WdT3qO4/7SQh6rBDr47vQUtCLZukS6ylUzcQKBgQCd4MUa9+s0cuEudBTegKZSm0/Lu0ZeKx5xGObcqS+fubz4GEdor9H2OgporhDtyr5c4SeNyRnuRTamwu4v5W9pGEcPjNaF12HKkvqxc5a3/dzr8o7Hen35Hj08whuqNyTz5+p/XMmzpl/kiPPEsYBYWqH+Z6UNzTYZJLSo032miQKBgQDWOaBs0JrXGJir8lSmwQe9BJLSmkwQ6lv/q2MfM0u0YjZcJ/GWFn80opMy+KOqcUdOSs596K1sWZQtLjiYDZb3HUrUQ0fmvKVQPHIj56TQbWOm1XO2JeEEC+RWgCKGW6eAuws4q0/o00+kUCla9iwZu7VI6LR6VmHPnZls0KrHIQKBgH8FcJAaF2rDYtj1HDVIXIjp3HGV4y+wkXV9C4FNvbtMvPT+07UugEcSu30tTwGCtVYU8LXqrqo9lZLmEq0Gc3Siy8l/DrnwaAWL65cJ4KF35l8PcECeFOxxn+NaaNeyNlDmrox6Iu4fVdeROE9wYEq14M/Go81O5c+k8JVEUzvBAn84u10StPpEueacDl/VgxskWofr1u6jsgQk5vSXyKwV+0Nr08Z6wjssipaX+VMjB4TUHs5MncpKTGV7CW+/Fj6du0bYdKqa/wrJAMOGBUhaRJkVBGezGtjIlT2xb+LytiABGM76u7+CrvJT83mdTzPPKzeGt1uImSm+DTEZvqqE", "json", "UTF-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlXW2vtnEXwFKhz1+LAVPuQR1rUnW6hicHWCYd95HwemhVFxxVxZ9t/uciBiTWVaoO+r2wT8FlDZRsWpZxh4Vg+flzr4xatphdTfyJm5uiAth+Agh11sp9DZIqxc9sbltHWxCstT4GdBVVuENN06vVfK1kmfLBuT7h1oBIUBd4u7LIhV5qMQcZXZD+LvdC0kD6iRZjGE1Pxs5QHMxQS4pyr0qWqJuTtxQArgQXI4X/Zw95pPriQRV2d3JBIyyu6P5JW9hPmp4wzeTRq5qyUQ7dDDz91XGxRN00QQaZks+SbpswAOqvJgnhSQsFZI46bmtiKPeTm4tOC86ZPFZYGljdwIDAQAB", "RSA2");  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        //支付宝回调地址
        alipayRequest.setReturnUrl( "http://domain.com/CallBack/return_url.jsp" );
        alipayRequest.setNotifyUrl( "http://domain.com/CallBack/notify_url.jsp" ); //在公共参数中设置回跳和通知地址
        //alipayRequest.putOtherTextParam("app_auth_token", "201611BB8xxxxxxxxxxxxxxxxxxxedcecde6");//如果 ISV 代商家接入电脑网站支付能力，则需要传入 app_auth_token，使用第三方应用授权；自研开发模式请忽略
        alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"20150322356562015555\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":188888,"  +
                "    \"subject\":\"玛莎拉蒂\","  +
                "    \"body\":\"玛莎拉蒂\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }" ); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
//        httpResponse.setContentType( "text/html;charset="  + "UTF-8");
//        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
        return form;
    }

    @RequestMapping("/returlCallBack")
    public String returlCallBack(HttpServletRequest request, HttpResponse response) throws AlipayApiException {

        Map<String, String> paramsMap = convertRequestParamsToMap(request);//将异步通知中收到的所有参数都存放到 map 中

        boolean signVerified = rsaCheckV1(paramsMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlXW2vtnEXwFKhz1+LAVPuQR1rUnW6hicHWCYd95HwemhVFxxVxZ9t/uciBiTWVaoO+r2wT8FlDZRsWpZxh4Vg+flzr4xatphdTfyJm5uiAth+Agh11sp9DZIqxc9sbltHWxCstT4GdBVVuENN06vVfK1kmfLBuT7h1oBIUBd4u7LIhV5qMQcZXZD+LvdC0kD6iRZjGE1Pxs5QHMxQS4pyr0qWqJuTtxQArgQXI4X/Zw95pPriQRV2d3JBIyyu6P5JW9hPmp4wzeTRq5qyUQ7dDDz91XGxRN00QQaZks+SbpswAOqvJgnhSQsFZI46bmtiKPeTm4tOC86ZPFZYGljdwIDAQAB", "UTF-8", "RSA2");  //调用SDK验证签名
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            return "success";

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            return "fail";
        }
    }
    //将request中的参数转换为map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }
}
