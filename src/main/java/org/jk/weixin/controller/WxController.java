package org.jk.weixin.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jk.weixin.common.AccessTokenInfo;
import org.jk.weixin.service.WxService;

import org.jk.weixin.util.HttpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/23.
 */
@Controller
@RequestMapping("/weixin/")
public class WxController {

    @Autowired
    private WxService wxService;

    @RequestMapping(value = "toWeixinMsgPage")
    String toWeixinMsgPage() {
        return "weixin/weixin_msg";
    }



    @RequestMapping(value = "weixinSendImageMsg", method = RequestMethod.POST)
    void weixinSendImageMsg(MultipartFile multipartFile, HttpServletRequest request) {
        //获取项目上下文路径
        ServletContext servletContext = request.getSession().getServletContext();
        String realPath = servletContext.getRealPath("/media/image/");
        File f = new File(realPath);
        if (!f.exists())
            f.mkdirs();

        //文件后缀名(sdfsfsfs.fdfdfed.fsdfs.sfsfs.jdsdsd...mp3)
        String originalFilename = multipartFile.getOriginalFilename();
        //截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        File file = new File(realPath + fileName);
        try {
            //把上传的文件保存在服务器上
            multipartFile.transferTo(file);

            //把保存好的文件上传到素材中心
            JsonObject image = HttpsUtil.uploadMedia(file, AccessTokenInfo.accessToken.getAccessToken(), "image");
            //获取到微信返回的文件media_id
            String media_id = image.get("media_id").getAsString();
            //把media_id保存到数据库中（掠过）
            //获取所有关注者的OpenID
            String httpsResponse = HttpsUtil.getHttpsResponse("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + AccessTokenInfo.accessToken.getAccessToken() + "&next_openid=",
                    null);
            JsonArray asString = new JsonParser().parse(httpsResponse).getAsJsonObject()
                    .get("data").getAsJsonObject()
                    .get("openid").getAsJsonArray();
            //把media_id推送向刚刚获取到的所有的OpenID
            Map<String, Object> map = new HashMap<>();
            map.put("touser", asString);
            map.put("msgtype", "image");
            Map<String, String> map1 = new HashMap<>();
            map1.put("media_id", media_id);
            map.put("image", map1);
            String json = new Gson().toJson(map);
            HttpsUtil.post("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + AccessTokenInfo.accessToken.getAccessToken(),
                    json,
                    "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
