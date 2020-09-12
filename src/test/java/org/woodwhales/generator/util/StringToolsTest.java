package org.woodwhales.generator.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToolsTest {
	
	@Test
	public void testUpper() {
		assertEquals("TbName", StringTools.upper("tb_name"));
		assertEquals("TbNameName", StringTools.upper("tb_name_name"));
		assertEquals("TbName", StringTools.upper("tb_name_"));
		assertEquals("Name", StringTools.upper("name_"));
	}
	
	@Test
	public void testUpperWithOutFisrtChar() {
		assertEquals("abcName", StringTools.upperWithOutFirstChar("abc_name_"));
		assertEquals("BcdName", StringTools.upperWithOutFirstChar("_bcd_name_"));

		assertEquals("BCdName", StringTools.upperWithOutFirstChar("_b_cdName"));
		assertEquals("BCdName", StringTools.upperWithOutFirstChar("-b-cdName"));

	}

	@Test
	public void test() {
		assertEquals("aBc", StringUtils.uncapitalize("ABc"));
		assertEquals("_ABc", StringUtils.uncapitalize("_ABc"));

		assertEquals("ABc", StringUtils.capitalize("aBc"));
		assertEquals("_aBc", StringUtils.capitalize("_aBc"));
	}

}

