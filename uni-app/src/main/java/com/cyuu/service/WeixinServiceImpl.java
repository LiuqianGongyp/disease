package com.cyuu.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.cyuu.dataObject.WXAuth;
import com.cyuu.dataObject.WxUserInfo;
import com.cyuu.utils.RedisKey;
import com.cyuu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Slf4j
@Service
public class WeixinServiceImpl implements IWeixinService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    WxService wxService;

    @Override
    public String getSessionId(String code) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("status", 200);

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", "").replace("{1}", "").replace("{2}", code);
        String res = HttpUtil.get(replaceUrl);
//        if(StringUtils.contains(res, "errcode")){
//            // 校验出错
//            result.put("status", 500);
//            result.put("msg", "登陆失败");
//            return result;
//        }
        String s = UUID.randomUUID().toString();
        System.out.println("用户要传给前端的sessionId" + s);
        // 把sessionId存储到redis中
        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + s, res, Duration.ofDays(7));
//        result.put("sessionId", RedisKey.WX_SESSION_ID + s);
        return s;
    }

    @Override
    public Result authLogin(WXAuth wxAuth) {
        try {
            String wxRes = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            log.info("用户信息："+wxRes);
            //用户信息：{"openId":"o20","nickName":"juana","gender":2,"language":"zh_CN","city":"Changsha","province":"Hunan","country":"China","avatarUrl":"头像链接","watermark":{"timestamp":dsfs,"appid":"应用id"}}
            WxUserInfo wxUserInfo = JSON.parseObject(wxRes, WxUserInfo.class);
            // 业务操作：你可以在这里利用数据对数据库进行查询， 如果数据库中没有这个数据，就添加进去，即实现微信账号注册
            // 如果是已经注册过的，就利用数据，生成jwt 返回token，实现登录状态
            return Result.SUCCESS(wxUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.FAIL();
    }
}
