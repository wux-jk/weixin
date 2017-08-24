/** 
 * <pre>项目名称:ssh-03 
 * 文件名称:JsonOutUtil.java 
 * 包名:common.util 
 * 创建日期:2017年3月15日下午2:06:01 
 * Copyright (c) 2017, chenjh123@gmail.com All Rights Reserved.</pre> 
 */  
package common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** 
 * <pre>项目名称：ssh-03    
 * 类名称：JsonOutUtil    
 * 类描述：    
 * 创建人：陈教授 chenjh123@gmail.com    
 * 创建时间：2017年3月15日 下午2:06:01    
 * 修改人：陈教授 chenjh123@gmail.com     
 * 修改时间：2017年3月15日 下午2:06:01    
 * 修改备注：       
 * @version </pre>    
 */
public class JsonOutUtil {

	public static void jsonOut(String json, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
}
