package org.woodwhales.generator.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 字符串处理工具类
 * @author woodwhales
 */
public class StringTools {

	/**
	 * 根据 separators 依次截取 str 之后的内容
	 * @param str
	 * @param separators
	 * @return
	 */
	public static String substringAfter(String str, List<String> separators) {
		if(CollectionUtils.isEmpty(separators)) {
			return str;
		}

		for (String separator : separators) {
			if (StringUtils.startsWith(str, separator)) {
				return StringUtils.substringAfter(str, separator);
			}
		}
		
		return str;
	}

	
	/**
	 * 将下划线之后的字母转换成大写字母，首个字母会转成大写
	 * @param str
	 * @return
	 */
	public static String upper(String str) {
		// 字符串缓冲区
		StringBuffer sbf = new StringBuffer();
		// 如果字符串包含 下划线
		if (str.contains("_")) {
			// 按下划线来切割字符串为数组
			String[] split = str.split("_");
			// 循环数组操作其中的字符串
			for (int i = 0, index = split.length; i < index; i++) {
				// 递归调用本方法
				String upperTable = upper(split[i]);
				// 添加到字符串缓冲区
				sbf.append(upperTable);
			}
		} else {// 字符串不包含下划线
				// 转换成字符数组
			char[] ch = str.toCharArray();
			// 判断首字母是否是字母
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				// 利用ASCII码实现大写
				ch[0] = (char) (ch[0] - 32);
			}
			// 添加进字符串缓存区
			sbf.append(ch);
		}
		// 返回
		return sbf.toString();
	}
	
	/**
	 * 将下划线之后的字母转换成大写，首个字母不会转成大写
	 * @param str
	 * @return
	 */
	public static String upperWithOutFisrtChar(String str) {
		if(StringUtils.isBlank(str)) {
			return StringUtils.EMPTY;
		}
		
		char[] charArray = str.toCharArray();
		int length = charArray.length;
		// A-Z 对应数字65-90 a-z 对应数字97-122
		for (int i = 0; i < length; i++) {
			if (charArray[i] == '_') {
				// 如果下划线之后没有字母了，结束本次循环
				if((i + 1) == length) {
					continue;
				}
				
				// 字符在97-122之间的都是小写字母，在原基础上减32转换成大写
				if (charArray[i + 1] >= 97 && charArray[i + 1] <= 122) {
					int upper = charArray[i + 1] - 32;
					charArray[i + 1] = (char) upper;
				}
			}
		}
		
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			result.append(charArray[i]);
		}
		
		return result.toString().replace("_", "");
	}
}
