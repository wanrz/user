package com.util;

import org.springframework.web.util.HtmlUtils;

/**
* @Description:   对 out.print(object) 的object 清洗特殊的html字符
 *                  预防xss攻击
* @Author:         tianyuan
* @CreateDate:
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0
*/
public class XssUtils {
    /**
     * 对输出对象是stirng类型的做html字符清洗,防止xss跨域攻击
     * @param object
     * @return
     */
    public  static  Object escapeHtml(Object object){
        if(object instanceof  String){
           String  value  = (String)object;
            return HtmlUtils.htmlEscape(value);
        }
        return object;
    }

    public static void main(String[] args) {
        String s = "<script>window.parent.location.href=";
        System.out.println(escapeHtml(s));
    }
}
