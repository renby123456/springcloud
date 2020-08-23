package com.jk.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jk.entity.UserEntity;
import com.jk.service.UserService;
import com.jk.utils.OrderCodeFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @program: guangyudeliversmedicine
 * @author: pcc
 * @created: 2020/08/04 11:41
 */
@Controller
@RequestMapping("pay")
public class PayController {

    private final String APP_ID = "2021000120606503";
    //私钥
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDOwydpOQeDUJL5MN+hE3IY1wRX/kS7XKLB76jNgdkJhwDHaC3TVzL5rC1DqilP7We4iO+tpZFub3ZjSo/gKbUugVFu81sdCgs50HCwFrbC+fXNzvq3h5WRDyfjTypxbE12yKdvzPrCpe1znQj/ic/KVLvm3TptaX76WTZlca9Fb7R0qFAX8X7FONmEj9ySp9lTDE+VTWYupXHyEd/5C1nhBlOuSrMmWvv6hB9WIMBT3d1DgkIo/y/F3Y4cFP6YCRjLyRzYN10R06wa4CFyE5hArfJwVy6VD36hB43quuVwlReMG6jUHZCuc8UX5FcS9f5DKGL5YZeHENZjcQFR9OAtAgMBAAECggEAbaxw3Eym4o272DiHdr6E2A49vJsgRC4oGy0P4iqNQJN+40csf50SagNdYzYAgegRC35/fCGF2gl/x71GQgZ9l7R6yJqHawjn2XvqOk1iFlxWeRvgO7fMq0xZwSY+AjoLgQWAYzpcjSFMqfXTTxD/ynhVz2jr1BuQgiUOSJMcAH0CEQ0Iwhwz/LJcKJD53FUGI/XtmJU2MswQxtByhxev1ia9aXnKWxHiRTO0MwcD9HzuzgVzTE7Y8EPMK/OteMsoU4PMmtRyeFV0bn4ZS7pUNnXTQ8DM2/ewgDVcWfH+NPtQVxm4Vb6tRk7KIfo8XBBidMuNILoCVrk7yPNx0T9TgQKBgQD9Fz7kx1g80DPWFlvndx0ck3h867pTls86EUfQPUu+9SlVOWL7FLRuUhIyPivM4t/liMRCagGzl7oP665ozNmNsY3wIXm4TkStCbSlXGRSD9CP2FMPvLJqGQKDAaHMsaxzWjDBa9GLD9epDnw96FZWyJmShZQ1Amy5h4KC4QUyzQKBgQDRI5STFIeRd/WBbJ5hit3eIzTfb3kG+PBEtvWS3sc0muH/WJxPtIlYORtKk3tF8DGh5o6X9wA3IhP+23xde+JHHnjTktFtysiKo+WMhdQnf6dP6+LM8Vp972fySFYIrWZ8KRDAobj3kaI505JsyqYHGdZIr0+crL/XpkKOsjQi4QKBgBaWcYWHiQd8aXPs8Ozag52eR72WaD23ku0Sx779CXqUdMjnYz8XXPcPmhIj3FLf2K2qpCFtRpOZsgBjv2AZED2GIC3TPwLE8XokCJxNf6KXSLzdyKP56k5vxvqcNa2IGeT9oqZQkhYAJvYulcYMq0/5fUNHaq/EdKGmR26Y2JbtAoGAP1dgY19/Y86KO8Wy/mX5ppLRK67uX+87jgQ/wljYypTyR80FoppSUrcRDfg3CMqjrZ5Pp7T2irw2hCTjuMiRkuqmu6Wm6nLVZDAZkQLja4cdHHab/WhH+QZuXBIOhJhIBujFGyKgC9M+5/atTWfe+x2XKfJ20UP84oqHdAKDCeECgYA7dASGF6S2k8A8JGFqUC/fmik2OlyDdRHIDnlvx4M2WREJwaanaSucjgwpyvDM8WizmPrZ9lGSSMJLH3pCXqOPFXGX+M59SiREFIIZhPdkNvrIpmkB7osk9c9RgBBLAjvHGY3fdgbbkaSLw+gUlZgeBlXUqs7Vh5puvUCfhUZ/tQ==";
    private final String CHARSET = "UTF-8";
    //公钥
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzsMnaTkHg1CS+TDfoRNyGNcEV/5Eu1yiwe+ozYHZCYcAx2gt01cy+awtQ6opT+1nuIjvraWRbm92Y0qP4Cm1LoFRbvNbHQoLOdBwsBa2wvn1zc76t4eVkQ8n408qcWxNdsinb8z6wqXtc50I/4nPylS75t06bWl++lk2ZXGvRW+0dKhQF/F+xTjZhI/ckqfZUwxPlU1mLqVx8hHf+QtZ4QZTrkqzJlr7+oQfViDAU93dQ4JCKP8vxd2OHBT+mAkYy8kc2DddEdOsGuAhchOYQK3ycFculQ9+oQeN6rrlcJUXjBuo1B2QrnPFF+RXEvX+Qyhi+WGXhxDWY3EBUfTgLQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://openapi.alipay.com/gateway.do";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1:8061/user/toselect";


    @Resource
    private UserService userService;

    @RequestMapping("alipay")
    public void alipay(HttpServletResponse httpResponse, Integer userId , HttpSession session,Integer money) throws IOException {

        Random r = new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

       /* UserEntity userEntity = (UserEntity)session.getAttribute(session.getId());
        System.out.println(userEntity);*/

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = OrderCodeFactory.generateUnionPaySn();
        //付款金额，必填
        Integer total_amount = money;
        //订单名称，必填
        String subject = "钱";
        //商品描述，可空
        String body = "";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }



}