package org.woodwhales.generator.constant;

/**
 * 全局常量类
 * @author woodwhales
 */
public class MyConstant {

	private MyConstant() { }

	public static final String templateUrlWithoutSchema = "jdbc:mysql://[ip]:[port]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useInformationSchema=true";

	public static final String templateUrl = "jdbc:mysql://[ip]:[port]/[schema]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useInformationSchema=true";
}
