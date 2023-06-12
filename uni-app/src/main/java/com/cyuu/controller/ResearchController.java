package com.cyuu.controller;

import com.cyuu.dataObject.CADao;
import com.cyuu.service.CaService;
import com.cyuu.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import it.unisa.dia.gas.jpbc.Field;
import sun.misc.Unsafe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.cyuu.controller.SM2Util.sign;

@Component
@ServerEndpoint(value = "/{ip}")
@Controller("/info")
@RequestMapping("/info")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ResearchController{

    final int BITSIZE = 10000;
    final int n = 1;//其他参与方个数
    private int NUM = 1;
    private Pairing pair = PairingFactory.getPairing("ourmap.properties");
    private Field G1 = pair.getG1();//
    private Field G2 = pair.getGT();
    List<String[]> res= new ArrayList<>();
    private static VolatileArray example = new VolatileArray();
    //private static volatile int flag = 0;
    private static ConcurrentHashMap<String, Session> connections = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Session, Integer> ID = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;

    @Autowired
    private CaService caService;
    private static ResearchController researchController;

    @PostConstruct
    public void init(){
        researchController = this;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ResearchController.applicationContext = applicationContext;
    }
    public static byte[] toByteArray(BigInteger bi) {
        byte[] array = bi.toByteArray();
        // 这种情况是转换的array超过25位
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }

        int N = 2;// 假如转换的byte数组少于N位，则在前面补齐0
        if (array.length < N) {
            byte[] tmp = new byte[N];
            System.arraycopy(array, 0, tmp, N - array.length, array.length);
            array = tmp;
        }
        return array;
    }
    private byte[] byteMerger(byte[] byte_1, byte[] byte_2){
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    private byte[] subByte(byte[] b,int off,int length){
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }
    private BigInteger[] transferBigInteger(String c11){
        String[] strings = c11.split(",");
        BigInteger[] c1 = new BigInteger[strings.length];
        for (int i=0; i<strings.length; i++){
            c1[i] = new BigInteger(strings[i]);
        }
        return c1;
    }
    private Element elementfromstring(String str){
        String[] part = str.split(",");
        BigInteger A = new BigInteger(part[0]);
        BigInteger B = new BigInteger(part[1]);
        int C = Integer.parseInt(part[2]);
        Element temp;
        if(C == 0){
            byte[] aa = toByteArray(A);
            byte[] bb = toByteArray(B);
            byte[] cc = byteMerger(aa,bb);
            temp = G1.newElementFromBytes(cc);
        }else{
            temp = G1.newOneElement();
        }
        return temp;
    }
    private Element element2fromstring(String str){
        int x1 = str.indexOf('=');
        int x2 = str.indexOf(',');
        String a = str.substring(x1+1, x2);
        int y1 = str.lastIndexOf('=');
        int y2 = str.indexOf('}');
        String b= str.substring(y1+1, y2);
        BigInteger A = new BigInteger(a);
        BigInteger B = new BigInteger(b);
        byte[] aa = toByteArray(A);
        byte[] bb = toByteArray(B);
        byte[] cc = byteMerger(aa,bb);
        Element temp = G2.newElementFromBytes(cc);
        return temp;
    }
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = "application/x-www-form-urlencoded" )
    @ResponseBody
    public void createItem(/*@RequestParam(name = "c11") String c11,
                           @RequestParam(name = "c22") String c22,
                           @RequestParam(name = "c33") String c33,
                           @RequestParam(name = "c44") String c44,
                           @RequestParam(name = "c5")int[] c5,*/
            @RequestParam(name = "pk111") String pk111,
            @RequestParam(name = "pk222") String pk222,
            @RequestParam(name = "pk33") int pk33) throws NoSuchFieldException, IllegalAccessException {
//    public void createItem(@RequestBody Map<String, List> hashMap){
//        System.out.println("title"+hashMap.get("title"));
//        System.out.println("aaa" + hashMap.get("aaa"));
        /*BigInteger[] c1 = transferBigInteger(c11);
        BigInteger[] c2 = transferBigInteger(c22);
        BigInteger[] c3 = transferBigInteger(c33);
        BigInteger[] c4 = transferBigInteger(c44);*/
        BigInteger pk11 = new BigInteger(pk111);
        BigInteger pk22 = new BigInteger(pk222);


        Field Zq = pair.getZr();

        String str1 = "169";//""542669622";
        BigInteger a1 = new BigInteger(str1);
        byte[] bb1 = toByteArray(a1);
        String str2 = "30";//""1778955512";
        BigInteger a2 = new BigInteger(str2);
        byte[] bb2 = toByteArray(a2);
        byte[] bb = byteMerger(bb1,bb2);
        Element g = G1.newElementFromBytes(bb);
        str1 = "562";//"2046532834";
        a1 = new BigInteger(str1);
        bb1 = toByteArray(a1);
        str2 = "200";//""1207651391";
        a2 = new BigInteger(str2);
        bb2 = toByteArray(a2);
        bb = byteMerger(bb1,bb2);
        Element egg = G2.newElementFromBytes(bb);
        Element pk_temp;
        if(pk33 == 0){
            byte[] aa = toByteArray(pk11);
            byte[] b = toByteArray(pk22);
            byte[] c = byteMerger(aa,b);
            pk_temp = G1.newElementFromBytes(c);
        }else{
            pk_temp = G1.newOneElement();
        }
        /** 获取当前系统时间*/
//        long startTime0 =  System.currentTimeMillis();


        /* 密钥生成 */
        party[] P = new party[n+1];
        P[0] = new party(pk_temp);
        /*P[0].cipher(BITSIZE);
        for(int i = 0; i < BITSIZE; i++){
            byte[] array1 = toByteArray(c1[i]);
            byte[] array2 = toByteArray(c2[i]);
            byte[] cipher1 = byteMerger(array1,array2);
            byte[] array3 = toByteArray(c3[i]);
            byte[] array4 = toByteArray(c4[i]);
            byte[] cipher2 = byteMerger(array3,array4);
            if(c5[i] == 0){
                P[0].cipher1[i] = G1.newElementFromBytes(cipher1);
                P[0].cipher2[i] = G2.newElementFromBytes(cipher2);
            }
            else {
                P[0].cipher1[i] = G1.newOneElement();
                P[0].cipher2[i] = G2.newElementFromBytes(cipher2);
            }
        }*/
//        for(int i = 1; i <= n; i = i + 1){
//            //每个参与方得到密钥
//            Element sk;
//            while (true){
//                sk = Zq.newRandomElement().getImmutable();
//                if(!sk.isZero()&&!sk.isOne()){
//                    break;
//                }
//            }
//            Element pk = g.duplicate().powZn(sk);
//            P[i] = new party(sk,pk,i+1);
//        }
        Element sk = Zq.newElement(13).getImmutable();
        Element pk = g.duplicate().powZn(sk);
        P[1] = new party(sk,pk,2);


        Element r;
        Element pkr;
        while (true){
            r = Zq.newRandomElement().getImmutable();
            if(!r.isZero()&&!r.isOne()){
                break;
            }
        }
        pkr = P[0].pk.duplicate().powZn(r);//云服务器先算一个pk^r
        send(pkr.toString());
        Element[] pkraa = new Element[n+1];//每一行是一组，第i行是为了都转成第i个参与方公钥加密的密文
        //每个参与方都要进行计算，将自己的私钥的逆含入
        /*for(int j = 1; j <= n; j = j + 1){
            pkra[j] = pkr.duplicate().powZn(P[j].sk.duplicate().invert());//j->i
        }*/
        //阻塞
        while (true) {
            if (example.atomicReferenceArray.get(n-1) != null) {
                break;
            }
        }


        for(int j = 1; j <= n;j = j + 1){
            pkraa[j] = example.atomicReferenceArray.get(j-1).duplicate().powZn(r.duplicate().invert());
        }
        //System.out.println("won");


        /* 参数生成 */
        double FPP = 1e-2;
        long DataSize = 1000;
        MYBF[] PBF = new MYBF[n+1];
        for(int i = 1; i <= n; i = i + 1){
            PBF[i] = new MYBF(FPP,DataSize);
        }
/*
    这里写从数据库中读入密文，下面的计算请求就是要读入的密文
 */
        CADao caDao = new CADao();
        researchController.caService.createCipher(caDao);
// 读取最新一条数据库信息
        for(int i = 1; i <= n;i++){
            P[i].cipher(PBF[i].BITSIZE);
            CADao info = researchController.caService.searchCipher();
            String c1 = info.getCipher1();
            String[] part = c1.split("#");
            int j = 0;
            for(String xc1 : part){
                P[i].cipher1[j] = elementfromstring(xc1);
                j++;
            }
            String c2 = info.getCipher2();
            part = c2.split("#");
            j = 0;
            for(String xc2 : part){
                P[i].cipher2[j] = element2fromstring(xc2);
                j++;
            }
            String pk1 = info.getPk();
            System.out.println("密文1为：" + c1 + "，密文2为：" + c2 + "，公钥为：" + pk1);
        }


        /*计算请求*/
        /*for(int i = 1; i <= n; i = i + 1){
            try (FileReader reader = new FileReader(P[i].pathname);
                 BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    PBF[i].add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Element[][] rij = new Element[n+1][];
        for(int i = 1; i <= n; i = i + 1){
            //每个参与方进行加密
            rij[i] = new Element[PBF[i].BITSIZE];
            P[i].cipher(PBF[i].BITSIZE);
            Element bit = Zq.newElement();
            for(int j = 0; j < PBF[i].BITSIZE; j = j + 1){
                rij[i][j] = Zq.newRandomElement().getImmutable();
                P[i].cipher1[j] = P[i].pk.duplicate().powZn(rij[i][j].duplicate());
                if(PBF[i].bits.get(j)){
                    bit.setToOne();
                }
                else {
                    bit.setToZero();
                }
                P[i].cipher2[j] = egg.duplicate().powZn(bit).duplicate().mul(egg.duplicate().powZn(rij[i][j].duplicate()).duplicate());
            }
        }*/



        /** 程序运行 processRun();*/

/** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
        /*long endTime0 =  System.currentTimeMillis();
        double usedTime0 = (double)(endTime0-startTime0)/1000;
        System.out.println(usedTime0);*/

        /** 获取当前系统时间*/
//        long startTime =  System.currentTimeMillis();




        /* 计算响应 */
//        int tag = 0;//标志发出查询申请的是P1
        for(int i = 1; i < n + 1; i = i+1){
            /*if(i==tag){
                for(int j = 0; j < BITSIZE; j = j + 1){
                    P[0].cipher1[j] = pair.pairing(P[0].cipher1[j], g.duplicate());
                }
                continue;
            }*/
            for(int j = 0; j < BITSIZE; j = j + 1){
                P[i].cipher1[j] = pair.pairing(P[i].cipher1[j], pkraa[i].duplicate());
            }
        }
        Element[] cipher1 = new Element[BITSIZE];
        Element[] cipher2 = new Element[BITSIZE];
        for(int i = 0; i < BITSIZE; i = i +1){
            cipher1[i] = G2.newOneElement();
            cipher2[i] = G2.newOneElement();
        }
        BigInteger[] ciph1 = new BigInteger[BITSIZE];
        BigInteger[] ciph2 = new BigInteger[BITSIZE];
        BigInteger[] ciph3 = new BigInteger[BITSIZE];
        BigInteger[] ciph4 = new BigInteger[BITSIZE];
        String text = "";
        String[] CIPH1 =  new String[BITSIZE];
        String[] CIPH2 =  new String[BITSIZE];
        String[] CIPH3 =  new String[BITSIZE];
        String[] CIPH4 =  new String[BITSIZE];
        for(int i = 0; i < BITSIZE; i = i +1){
            for(int j = 1; j < n + 1; j = j +1){
                cipher1[i] = cipher1[i].mul(P[j].cipher1[i].duplicate());
                cipher2[i] = cipher2[i].mul(P[j].cipher2[i].duplicate());
            }
            byte[] array1 = cipher1[i].toBytes();
            byte[] array2 = cipher2[i].toBytes();
            byte[] array3 = subByte(array1,0,cipher1[i].getLengthInBytes()/2);
            byte[] array4 = subByte(array1,cipher1[i].getLengthInBytes()/2,cipher1[i].getLengthInBytes()/2);
            byte[] array5 = subByte(array2,0,cipher1[i].getLengthInBytes()/2);
            byte[] array6 = subByte(array2,cipher2[i].getLengthInBytes()/2,cipher2[i].getLengthInBytes()/2);
            ciph1[i] = new BigInteger(array3);
            ciph2[i] = new BigInteger(array4);
            ciph3[i] = new BigInteger(array5);
            ciph4[i] = new BigInteger(array6);
            CIPH1[i] = ciph1[i].toString();
            CIPH2[i] = ciph2[i].toString();
            CIPH3[i] = ciph3[i].toString();
            CIPH4[i] = ciph4[i].toString();
            text = text + ciph1[i].toString() + ciph2[i].toString() + ciph3[i].toString() + ciph4[i].toString();
            //System.out.println(cipher1[i]);
        }
        /** 程序运行 processRun();*/

/** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
/*        long endTime =  System.currentTimeMillis();
        double usedTime = (double)(endTime-startTime)/1000;
        System.out.println(usedTime);*/

        String priKey = "484ddc53e3bf8207f48d1cdd0f86d029e0e4f5677ef118babfd9954b81e4aad9";//smKeyPair.getPriKey();
        String pubKey = "04bd2644b35e1263be9491221913d71256597fed4a61a6a94be06bafc1806919fc33ebe6928797b20fc480ee91b218c16ecc82f0eff0e5dd556aea18cd8addad8e";//smKeyPair.getPubKey();

        //签名验签测试
        String[] SIGN = new String[2];
        String sign1 = "";
        try {
            sign1 = sign(priKey, Hex.toHexString(text.getBytes()));
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        String sign2 = "";
        text = "feedback";
        try {
            sign2 = sign(priKey, Hex.toHexString(text.getBytes()));
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        SIGN[0] = sign1;
        SIGN[1] = sign2;
        res.clear();
        res.add(CIPH1);
        res.add(CIPH2);
        res.add(CIPH3);
        res.add(CIPH4);
        res.add(SIGN);
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public List<String[]> get(){
        System.out.println(res);
        return res;
    }

    /**
     * 打开连接
     * @param session
     * @param ip
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("ip") String ip) {
        System.out.println("=====接受到的请求地址=====" + ip);
        // 接收到客户端的请求，可以做一些其他业务逻辑处理，比如可以把该IP存储到数据库
        // 避免当前服务断开后，与客户端服务失去连接
        // 这时就可以使用到预加载处理，项目当中自定义的MyApplicationRunner类
        connections.put(ip, session);
        ID.put(session,NUM);
        NUM++;
        //send("555");
    }

    /**
     * 接收消息
     * @param text
     */
    @OnMessage
    public void onMessage(String text,Session session) throws NoSuchFieldException, IllegalAccessException {
        //System.out.println(text);
        if(text.charAt(0)=='0'){
            text = text.substring(1);
            //将密文存进数据库
            String[] part = text.split("#~~");
//        String[] cipher1 = part[0].split("#");
//        String[] cipher2 = part[1].split("#");
            String cipher1 = part[0];
            String cipher2 = part[1];
            String pk = part[2];
            /*System.out.println("切割得到的cipher1为" + cipher1);
            System.out.println("切割得到的cipher2为" + cipher2);
            System.out.println("切割得到公钥为" + pk);*/
            // 处理发送可接收长消息
//        if(text.charAt(0)=='0'){
            //将密文存进数据库

            CADao caDao = new CADao();
            caDao.setCipher1(cipher1);
            caDao.setCipher2(cipher2);
            caDao.setPk(pk);
//        caDao.setId(1);

            System.out.println(caDao.getCipher1());
            System.out.println(caDao);

            // 错误原因caService为null
        /*
        Spring默认对bean的管理都是单例（singleton），和 websocket （多对象）相冲突。
        项目启动时初始化，会初始化 websocket （非用户连接的），spring 同时会为其注入 service，该对象的 service 不是 null，被成功注入。
        但是，由于 spring 默认管理的是单例，所以只会注入一次 service。当用户建立新的连接时，系统又会创建一个新的 websocket 对象，这时不会再次注入了，所以导致只要是用户连接创建的 websocket 对象，都不能再注入了，后面的注入均为null。
         */
//        if (caService == null){
//            System.out.println("-----------------------------------------");
//        }
            researchController.caService.createCipher(caDao);

            //System.out.println("收到的消息为：" + text);

        }else {
            //计算重加密密钥
            Element temp;
            text = text.substring(1);
            if(text.charAt(text.length()-1)=='1'){
                temp = G1.newOneElement();
            }else {
                int x = text.indexOf(',', 0);
                BigInteger xx = new BigInteger(text.substring(0, x));
                text = text.substring(x+1);
                int y = text.indexOf(',');
                BigInteger yy = new BigInteger(text.substring(0, y));
                byte[] aa = toByteArray(xx);
                byte[] b = toByteArray(yy);
                byte[] c = byteMerger(aa, b);
                temp = G1.newElementFromBytes(c);
            }
            example.atomicReferenceArray.set(ID.get(session)-1, temp);
            /*int a = flag;
            a++;
            flag = a;*/
        }
    }

    /**
     * 异常处理
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 关闭连接
     * @param ip
     */
    @OnClose
    public void onClosing(@PathParam("ip") String ip) throws IOException {
        connections.remove(ip);
    }

    /**
     * 根据IP发送消息
     * @param ip
     * @param text
     */
    public void send(String ip, String text) {
        try {
            Session session = connections.get(ip);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历群发消息
     * @param text
     */
    public void send(String text) {
        for (ConcurrentHashMap.Entry<String, Session> entry : connections.entrySet()) {
            send(entry.getKey(), text);
        }
    }
}
