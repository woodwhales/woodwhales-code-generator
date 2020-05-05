package org.woodwhales.generator.constant;

public class MyConstant {

	public static final String templateUrlWithoutSchema = "jdbc:mysql://[ip]:[port]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&useInformationSchema=true";

	public static final String templateUrl = "jdbc:mysql://[ip]:[port]/[schema]?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&useInformationSchema=true";
}
