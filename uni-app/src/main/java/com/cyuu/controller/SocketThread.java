package com.cyuu.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送线程使用interrupt方式来停止，在catch中需要二次使用interrupt才能清除中断标识
 */
public class SocketThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SocketThread.class);

    private WebSocketHandler wsh;
    private String message;

    public SocketThread(WebSocketHandler wsh, String message) {
        this.wsh = wsh;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            Map<String, Long> map = new HashMap<>();
            map.put("dataSource", Long.valueOf(message));
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread curr = Thread.currentThread();
            //再次调用interrupt方法中断自己，将中断状态设置为“中断”
            curr.interrupt();
//            System.out.println("SocketThread IsInterrupted: " + curr.isInterrupted());
//            System.out.println("Static Call: " + Thread.interrupted());//clear status
//            System.out.println("---------After Interrupt Status Cleared----------");
//            System.out.println("Static Call: " + Thread.interrupted());
//            System.out.println("SocketThread IsInterrupted: " + curr.isInterrupted());
        }

        logger.info("SocketThread stopped.");
    }
}