package org.woodwhales.generator.core.constant;

/**
 * 全局常量类
 * @author woodwhales
 */
public class MyConstant {

	private MyConstant() { }

	public static final String hexTxt = "wood.whales";

	public static final String mysql_Template_Url_Without_Schema = "jdbc:mysql://[ip]:[port]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useInformationSchema=true";

	public static final String mysql_Template_Url = "jdbc:mysql://[ip]:[port]/[schema]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useInformationSchema=true";

	public static final String oracle_Template_Url = "jdbc:oracle:thin:@[ip]:[port]:[sid]";

	public static final String pgsql_Template_Url_Without_Schema = "jdbc:postgresql://[ip]:[port]/postgres";

	public static final String pgsql_Template_Url = "jdbc:postgresql://[ip]:[port]/[schema]";
}
