package org.sharechina.pay.pufa.protocal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.sharechina.pay.pufa.common.ReflectUtil;

public interface PlainData {

	public static final DozerBeanMapper dozer=new DozerBeanMapper();
		
	default String toPlain() {
		return toPlain(this);
	}	
	
	/**
	 * ת��Ϊ'param1=value1|param2=value2'���ַ�������Ҫ���ֿ�ֵ
	 * @param pojo
	 * @return
	 */
	public static String toPlain(Object pojo) {	
		StringBuilder sb=new StringBuilder();
		ReflectUtil.foreachField(pojo, (pair)-> {
			Object name=pair.getKey();
			Object value=pair.getValue();
			if(value==null)return;
			sb.append(name);
			sb.append("=");
			sb.append(value);
			sb.append("|");
		});
		String result=sb.toString();
		return StringUtils.removeEnd(result, "|");
	}
	
	public static <T> T fromPlain(Class<T> tClass, String plain) {
		if(plain==null)return null;
		String[] keyvalues=StringUtils.split(plain, '|');
		Map<String, Object> params=new HashMap<String, Object>();
		for (String keyvalue : keyvalues) {
			String[] pair=StringUtils.split(keyvalue,"=");
			String name=pair[0];
			name=firstToLower(name);
			params.put(name, pair[1]);
		}
		T t=dozer.map(params, tClass);
		return t;
	}
	
	public static String firstToLower(String str) {
		char[] chars=new char[1];
		chars[0]=str.charAt(0);  
        String temp=new String(chars);  
        return str.replaceFirst(temp, temp.toLowerCase());
	}
}
