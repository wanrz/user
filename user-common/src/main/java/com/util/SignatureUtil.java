package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 签名工具类
 * @author zhuyunfei
 *
 */
public class SignatureUtil {

	/**
	 * 参数排序
	 * @param paramMap
	 * @return
	 */
	private static List<Map.Entry<String, String>> sortMapByKey(Map<String, String> paramMap) {  
	    		
		if (paramMap == null || paramMap.isEmpty()) {  
	        return null;  
	    }  
		
		//排序
		List<Map.Entry<String, String>> sortList = new ArrayList<Map.Entry<String, String>>(paramMap.entrySet());  
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）  
        Collections.sort(sortList, new Comparator<Map.Entry<String, String>>() {  
            @Override  
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {  
                return (o1.getKey().toLowerCase()).compareTo(o2.getKey().toLowerCase());  
            }  
        });  
	    
	    return sortList;  
	}  
	  	
	/**
	 * 根据参数生成签名字符串
	 * @param paramMap 参数map
	 * @param key 私钥
	 * @return
	 */
	public static String signatureParamsBymd5(Map<String, String> paramMap,String key) {
		
		//签名后的字符串
		String signatureString = "";
		
		//获取排序的参数map
		List<Map.Entry<String, String>> sortMapList = sortMapByKey(paramMap);
		if(sortMapList == null || sortMapList.size() < 1) {
			return signatureString;
		}
		
		//获取排序后的url键值对参数字符串
		StringBuilder paramStr = new StringBuilder();
		for(Entry<String, String> entry : sortMapList) {
			if(paramStr.length() > 0) {
				paramStr.append("&");
			}
			paramStr.append(entry.getKey()+"="+entry.getValue());
		}
		
		//排序后的url键值对参数+私钥进行md5加密
		paramStr.append("&key="+key);
//		System.out.println(paramStr.toString());
		signatureString = Md5.md5(paramStr.toString());
				
		return signatureString;
	}
	
	public static void main(String[] args) {
		Map<String,String> paramMap = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		sb.append("AQQY/QADAQAAAAAAAgAUNsDjCwLF8WM65P");
		sb.append("jT44Ra1tMYF/86PvNk/BRaTadA3DLAyLaA5");
		sb.append("VL4LADY3ZP/ERCC3TFDsd21b+sk5Ee4qZaF");
		sb.append("7/4KGtS93NmSf60hoXMRX6BBguTCaZdbGApIz");
		sb.append("hjPVZEnR4PCaUigDHm4JH4h/J3/t3g6gnmg");
		sb.append("GmJMzuYK5ECmnx2HeyE9qqRtH8PDcyqUBN");
		sb.append("KHitEVb6yn/6q/5Ja44IcJSVo1x0WElOFX");
		sb.append("1wS/Glr6sdmSQmyTAeKkohHctcxgldoQiHieA");
		sb.append("7e8VGQsHsXKKCK0YeOtFqizcPcOtCfgnoKlQBv0");
		sb.append("suhiXc3EJkSf+S2NuBfAaE1I3fJ9mVq43");
		sb.append("je3MaoBvmkrQpG13Le7LW3w4WSYZHgNZm1J");
		sb.append("HNH1U9vfpNEU5tVcpPGoS4V1XiHCWOYGs");
		sb.append("YH0mZtTooO/FZtVJSNmY3OqgR55+Rt6P");
		sb.append("5d094D9it3wkwfk3wFbHDfYan7p3MxnQVVuHxcDir7");
		sb.append("17DyUtG1QNItT1nB4TlmNnJ/p0lJaO/NNuHwPUbaw");
		sb.append("NdBZurJJfR1VhnLCdl8jyqqsK5PBxw0e7/5IL4xQ");
		sb.append("3o9rjd8IYvqEo8TXdy9bRHyYQcPaQq4rU");
		sb.append("fPQYzXdWCYppIq1aStgAXliprwiIG1f30f6b23L3zb5Dp");
		sb.append("m1rTXCklKZIh3Jzj69M6QJ2vzlWpAnKaKNBPtb8fofM8Hi59Qt");
		paramMap.put("img1", sb.toString());
		paramMap.put("channel", "0408");
		paramMap.put("img2", "IBQ3AAQBZAIAAABANwAEAeEHBwcPFTUAAAAAAAAANwAEAQAAAAAAAAAAAAAAAAAAAAAAAAgDAADIK9UVAAAAAAAAAAA");
		SignatureUtil.signatureParamsBymd5(paramMap, "123454dsgsgshshshshsh");
	}
	
}
