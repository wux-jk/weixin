package org.jk.weixin.util;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jk.weixin.entity.Music;
import org.jk.weixin.entity.Video;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2017/8/22.
 * 消息处理工具类
 */
public class MessageHandlerUtil {


    /**
     * 解析微信发来的请求（XML）
     * @param request
     * @return map
     * @throws Exception
     */
    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String,String> map = new HashMap();
        // 从request中取得输入流
       //InputStream inputStream = request.getInputStream();
        //System.out.println("获取输入流");
        // 读取输入流
        //SAXReader reader = new SAXReader();
        //Document document = reader.read(inputStream);
        // 得到xml根元素
        //Element root = document.getRootElement();
        // 得到根元素的所有子节点
        //List<Element> elementList = root.elements();

        // 遍历所有子节点
        //for (Element e : elementList) {
            //设置编码格式
            //String text =new String(e.getText().getBytes("GBK"),"UTF-8");
            //System.out.println(e.getName() + "|" + text);
            //map.put(e.getName(), text);
       // }

        // 释放资源
        //inputStream.close();
        //inputStream = null;
        //return map;
        BufferedReader reader1 = request.getReader();
        SAXReader reader = new SAXReader();
        Document document = reader.read(reader1);
        Element rootElement = document.getRootElement();
        List<Element> elementList = rootElement.elements();

        for (Element e : elementList){
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }

        if (null != reader1)
            reader1.close();
        reader1 = null;
        return map;

    }

    // 根据消息类型 构造返回消息
    public static String buildXml(Map<String,String> map) {
        String result;
        String msgType = map.get("MsgType").toString();
        System.out.println("MsgType:" + msgType);
        if(msgType.toUpperCase().equals("TEXT")){
            result = buildTextMessage(map, "孤傲苍狼在学习和总结微信开发了,构建一条文本消息:Hello World!");
        }else{
            String fromUserName = map.get("FromUserName");
            // 开发者微信号
            String toUserName = map.get("ToUserName");
            result = String
                    .format(
                            "<xml>" +
                                    "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                    "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                    "<CreateTime>%s</CreateTime>" +
                                    "<MsgType><![CDATA[text]]></MsgType>" +
                                    "<Content><![CDATA[%s]]></Content>" +
                                    "</xml>",
                            fromUserName, toUserName, getUtcTime(),
                            "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
        }

        return result;
    }

    /**
     * 构造文本消息
     *
     * @param map
     * @param content
     * @return
     */
    private static String buildTextMessage(Map<String,String> map, String content) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 文本消息XML数据格式
         * <xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>1348831860</CreateTime>
         <MsgType><![CDATA[text]]></MsgType>
         <Content><![CDATA[this is a test]]></Content>
         <MsgId>1234567890123456</MsgId>
         </xml>
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), content);
       }


    /**
     * 构造图片消息
     * @param map 封装了解析结果的Map
     * @param mediaId 通过素材管理接口上传多媒体文件得到的id
     * @return 图片消息XML字符串
     */
    private static String buildImageMessage(Map<String, String> map, String mediaId) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 图片消息XML数据格式
         *<xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>12345678</CreateTime>
         <MsgType><![CDATA[image]]></MsgType>
         <Image>
         <MediaId><![CDATA[media_id]]></MediaId>
         </Image>
         </xml>
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Image>" +
                        "</xml>",
                fromUserName, toUserName, getUtcTime(), mediaId);
    }


    /**
     * 构造语音消息
     * @param map 封装了解析结果的Map
     * @param mediaId 通过素材管理接口上传多媒体文件得到的id
     * @return 语音消息XML字符串
     */
    private static String buildVoiceMessage(Map<String, String> map, String mediaId) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 语音消息XML数据格式
         *<xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>12345678</CreateTime>
         <MsgType><![CDATA[voice]]></MsgType>
         <Voice>
         <MediaId><![CDATA[media_id]]></MediaId>
         </Voice>
         </xml>
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[voice]]></MsgType>" +
                        "<Voice>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Voice>" +
                        "</xml>",
                fromUserName, toUserName, getUtcTime(), mediaId);
    }

    /**
     * 构造视频消息
     * @param map 封装了解析结果的Map
     * @param video 封装好的视频消息内容
     * @return 视频消息XML字符串
     */
    private static String buildVideoMessage(Map<String, String> map, Video video) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 音乐消息XML数据格式
         *<xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>12345678</CreateTime>
         <MsgType><![CDATA[video]]></MsgType>
         <Video>
         <MediaId><![CDATA[media_id]]></MediaId>
         <Title><![CDATA[title]]></Title>
         <Description><![CDATA[description]]></Description>
         </Video>
         </xml>
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[video]]></MsgType>" +
                        "<Video>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "   <Title><![CDATA[%s]]></Title>" +
                        "   <Description><![CDATA[%s]]></Description>" +
                        "</Video>" +
                        "</xml>",
                fromUserName, toUserName, getUtcTime(), video.mediaId, video.title, video.description);
    }


    /**
     * 构造音乐消息
     * @param map 封装了解析结果的Map
     * @param music 封装好的音乐消息内容
     * @return 音乐消息XML字符串
     */
    private static String buildMusicMessage(Map<String, String> map, Music music) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 音乐消息XML数据格式
         *<xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>12345678</CreateTime>
         <MsgType><![CDATA[music]]></MsgType>
         <Music>
         <Title><![CDATA[TITLE]]></Title>
         <Description><![CDATA[DESCRIPTION]]></Description>
         <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
         <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
         <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
         </Music>
         </xml>
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[music]]></MsgType>" +
                        "<Music>" +
                        "   <Title><![CDATA[%s]]></Title>" +
                        "   <Description><![CDATA[%s]]></Description>" +
                        "   <MusicUrl><![CDATA[%s]]></MusicUrl>" +
                        "   <HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" +
                        "</Music>" +
                        "</xml>",
                fromUserName, toUserName, getUtcTime(), music.title, music.description, music.musicUrl, music.hqMusicUrl);
    }

    private static String getUtcTime() {
        Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
        String nowTime = df.format(dt);
        long dd = (long) 0;
        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {

        }
        return String.valueOf(dd);
    }


    /**
     * 根据消息类型构造返回消息
     * @param map 封装了解析结果的Map
     * @return responseMessage(响应消息)
     */
    public static String buildResponseMessage(Map map) {
        //响应消息
        String responseMessage = "";
        //得到消息类型
        String msgType = map.get("MsgType").toString();
        System.out.println("MsgType:" + msgType);
        //消息类型
        MessageType messageEnumType = MessageType.valueOf(MessageType.class, msgType.toUpperCase());
        switch (messageEnumType) {
            case TEXT:
                //处理文本消息
                responseMessage = handleTextMessage(map);
                break;
            case VOICE:
                //处理语音消息
                responseMessage = handleVoiceMessage(map);
                break;
            default:
                String msgText1 = "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文";
                responseMessage = buildTextMessage(map, msgText1);
                break;
        }
        //返回响应消息
        return responseMessage;
    }


    /**
     * 接收到文本消息后处理
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String handleTextMessage(Map<String, String> map) {
        //响应消息
        String responseMessage;
        // 消息内容
        String content = map.get("Content");
        switch (content) {
            case "文本":
                String msgText = "金科教育，高新就业\n" +
                        "<a href=\"http://www.jinkeit.cn\">金科教育</a>";
                responseMessage = buildTextMessage(map, msgText);
                break;
            case "图片":
                //通过素材管理接口上传图片时得到的media_id
                String imgMediaId = "zFKNR8n5CLZyNrxTLChoktVuw9Z6wKEKRAbsgGr8plwDjeIh028jDPcZP4XZGUF4";
                responseMessage = buildImageMessage(map, imgMediaId);
                break;
            case "语音":
                //通过素材管理接口上传语音文件时得到的media_id
                String voiceMediaId = "hI_f4kAVN5UgVbFwh6Gt2nf3w7Mk3qdisSQnv10KqIYmKbs29q-fM06jwPRv7cEt";
                responseMessage = buildVoiceMessage(map,voiceMediaId);
                break;
            case "视频":
                Video video = new Video();
                video.mediaId = "hiQ_dBM_1W4bOmLLaCeyaSM82oKCfzbRzuY-64gQpMjTdJlET3_TDws893Qo528z";
                video.title = "猴子";
                video.description = "经典搞怪视频";
                responseMessage = buildVideoMessage(map, video);
                break;
            case "音乐":
                Music music = new Music();
                music.title = "赌神驾到";
                music.description = "赌神插曲";
                music.musicUrl = "http://qugvpd.natappfree.cc/media/voice/2.mp3";
                music.hqMusicUrl = "http://qugvpd.natappfree.cc/media/voice/2.mp3";
                responseMessage = buildMusicMessage(map, music);
                break;
            default:
                //responseMessage = buildWelcomeTextMessage(map);
                String msgText1 = "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文";
                responseMessage = buildTextMessage(map, msgText1);
                break;

        }
        //返回响应消息
        return responseMessage;
    }

    /**
     * 接收到语音消息后处理
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String handleVoiceMessage(Map<String, String> map) {
        //响应消息
        String responseMessage;
        // 消息内容
        String content = map.get("Recognition");
        if (null == content || "".equals(content)) {
            String msgText1 = "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文";
            responseMessage = buildTextMessage(map, msgText1);
        } else if (content.contains("文本")) {
            String msgText = "金科教育，高薪就业，信，明天你就高薪就业\n" +
                    "<a href=\"http://www.jinkeit.cn\">金科教育</a>";
            responseMessage = buildTextMessage(map, msgText);
        } else if (content.contains("图片")) {
            //通过素材管理接口上传图片时得到的media_id
            String imgMediaId = "zFKNR8n5CLZyNrxTLChoktVuw9Z6wKEKRAbsgGr8plwDjeIh028jDPcZP4XZGUF4";
            responseMessage = buildImageMessage(map, imgMediaId);
        } else if (content.contains("语音")) {
            //通过素材管理接口上传图片时得到的media_id
            String voiceMediaId = "hI_f4kAVN5UgVbFwh6Gt2nf3w7Mk3qdisSQnv10KqIYmKbs29q-fM06jwPRv7cEt";
            responseMessage = buildVoiceMessage(map, voiceMediaId);
        } else if (content.contains("音乐")) {
            Music music = new Music();
            music.title = "赌神驾到";
            music.description = "赌神插曲";
            music.musicUrl = "http://qugvpd.natappfree.cc/media/voice/2.mp3";
            music.hqMusicUrl = "http://qugvpd.natappfree.cc/media/voice/2.mp3";
            responseMessage = buildMusicMessage(map, music);
        }else if (content.contains("视频")) {
            Video video = new Video();
            video.mediaId = "hiQ_dBM_1W4bOmLLaCeyaSM82oKCfzbRzuY-64gQpMjTdJlET3_TDws893Qo528z";
            video.title = "猴子";
            video.description = "经典搞怪视频";
            responseMessage = buildVideoMessage(map, video);
        } else {
            String msgText1 = "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文";
            responseMessage = buildTextMessage(map, msgText1);
        }
        //返回响应消息
        return responseMessage;

  }

    /**
     * 接收到的消息类型
     */
    public enum MessageType {
        TEXT,//文本消息
        IMAGE,//图片消息
        VOICE,//语音消息
        VIDEO,//视频消息
        SHORTVIDEO,//小视频消息
        LOCATION,//地理位置消息
        LINK,//链接消息
        EVENT//事件消息
    }


}

