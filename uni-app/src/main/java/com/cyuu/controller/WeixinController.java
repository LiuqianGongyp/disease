package com.cyuu.controller;

import com.cyuu.dataObject.WXAuth;
import com.cyuu.service.IWeixinService;
import com.cyuu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    @Autowired
    IWeixinService weixinService;

    //这个就是那个使用传code进来的接口
    @GetMapping("/sessionId/{code}")
    @ResponseBody
    public String getSessionId(@PathVariable("code") String code){
//        System.out.println("进入获取sessionId页面");
        return weixinService.getSessionId(code);
    }

    @PostMapping("/authLogin")
    @ResponseBody
    public Result authLogin(@RequestBody WXAuth wxAuth) {
//        System.out.println("进入授权登录页面");
        Result result = weixinService.authLogin(wxAuth);
        log.info("{}",result);
        return result;
    }
}

