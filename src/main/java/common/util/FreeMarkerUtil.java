package common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/7.
 */
public class FreeMarkerUtil {

    /**
     *
     * @param map：数据
     * @param templateName：模板名称
     * @param fileDir：页面路径
     * @param pageName：页面名称
     */
    public static void createStaticPage(Map<String, Object> map, String templateName, String fileDir, String pageName) {
        //获取ServletContext对象
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        //路径
        File f = new File(fileDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        //生成静态页面
        //实例化Configuration
        Configuration cg = new Configuration();
        cg.setDefaultEncoding("utf-8");
        //设置编码格式
        cg.setOutputEncoding("utf-8");
        //设置模板所在路径
        cg.setServletContextForTemplateLoading(servletContext, "/templates");
        FileWriter w = null;
        BufferedWriter bw = null;
        try {
            //获取模板
            Template template = cg.getTemplate(templateName);
            template.setEncoding("utf-8");
            template.setOutputEncoding("utf-8");
            w = new FileWriter(fileDir + "/" + pageName);
            bw = new BufferedWriter(w);
            template.process(map, bw);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                    bw = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != w) {
                    w.close();
                    w = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
