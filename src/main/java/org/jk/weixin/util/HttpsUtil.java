package org.jk.weixin.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.http.HttpStatus;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
public class HttpsUtil {

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param url     请求地址
     * @param content 参数
     * @param charset 编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] post(String url, String content, String charset)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return outStream.toByteArray();
        }
        return null;
    }

    /**
     * 发起Https请求
     *
     * @param reqUrl        请求的URL地址
     * @param requestMethod
     * @return 响应后的字符串
     */
    public static String getHttpsResponse(String reqUrl, String requestMethod) {
        URL url;
        InputStream is;
        String resultData = "";
        try {
            url = new URL(reqUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {new TrustAnyTrustManager()};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });


            con.setDoInput(true); //允许输入流，即允许下载

            //在android中必须将此项设置为false
            con.setDoOutput(false); //允许输出流，即允许上传
            con.setUseCaches(false); //不使用缓冲
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod); //使用指定的方式
            } else {
                con.setRequestMethod("GET"); //使用get请求
            }
            is = con.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 微信服务器素材上传
     *
     * @param file  表单名称media
     * @param token access_token
     * @param type  type只支持四种类型素材(video/image/voice/thumb)
     */
    public static JsonObject uploadMedia(File file, String token, String type) {
        if (file == null || token == null || type == null) {
            return null;
        }

        if (!file.exists()) {
            System.out.println("上传文件不存在,请检查!");
            return null;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/media/upload";
        JsonObject jsonObject = null;
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        FilePart media;
        HttpClient httpClient = new HttpClient();
        //信任任何类型的证书
        Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);

        try {
            media = new FilePart("media", file);
            Part[] parts = new Part[]{new StringPart("access_token", token),
                    new StringPart("type", type), media};
            MultipartRequestEntity entity = new MultipartRequestEntity(parts,
                    post.getParams());
            post.setRequestEntity(entity);
            int status = httpClient.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                String text = post.getResponseBodyAsString();
                jsonObject = new JsonParser().parse(text).getAsJsonObject();
            } else {
                System.out.println("upload Media failure status is:" + status);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static void main(String[] args) {
        String tokent = "g3f-XwS06SCB4VEyySPgSMcNZ2dyiDHx82xTeY8jyXs4kaTYCHqir9kbFQfOVgPGzuowoypy5YBeSqEqtBNROMQ3NBbmWAROT_JZR1WBvVIINLbAHATYW";
//        try {
//            post("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + tokent,
//                    "{\"touser\":[\"o1txrwmgHHRActvXr37c2l7ypvJI\",\"o1txrwq0gX0PwUSzLkPB46z2HqC4\",\"o1txrwpskSBfxYpvoY952wz2aoM4\"],\"msgtype\":\"text\",\"text\":{\"content\":\"拿不到20k薪资，誓死不回家\"}}",
//                    "utf-8");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String httpsResponse = getHttpsResponse("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + tokent + "&next_openid=", null);
        System.out.println(httpsResponse);

        JsonArray asString = new JsonParser().parse(httpsResponse).getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("openid").getAsJsonArray();
        System.out.println(asString);

        Map<String, Object> map = new HashMap<>();
        map.put("touser", asString);
        map.put("msgtype", "image");
        Map<String, String> map1 = new HashMap<>();
        map1.put("media_id", "zFKNR8n5CLZyNrxTLChoktVuw9Z6wKEKRAbsgGr8plwDjeIh028jDPcZP4XZGUF4");
        map.put("image", map1);

        String s = new Gson().toJson(map);


        try {
            post("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + tokent,
                    s,
                    "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
