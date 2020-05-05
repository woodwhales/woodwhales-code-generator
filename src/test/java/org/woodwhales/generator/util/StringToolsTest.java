package org.woodwhales.generator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class StringToolsTest {
	
	@Test
	public void testUpper() {
		assertEquals("TbName", StringTools.upper("tb_name"));
		assertEquals("TbNameName", StringTools.upper("tb_name_name"));
		assertEquals("TbName", StringTools.upper("tb_name_"));
		assertEquals("Name", StringTools.upper("name_"));
	}
	
	@Test
	public void testSpiltStr() {
		assertEquals("name_", StringTools.substringAfter("tb_name_", "tb_"));
		
		assertEquals("name_", StringTools.substringAfter("t_name_", Arrays.asList("tb_", "t_")));

		assertEquals("name_", StringTools.substringAfter("name_", Arrays.asList("tb_", "t_")));
	}
	
	@Test
	public void testUpperWithOutFisrtChar() {
		assertEquals("abcName", StringTools.upperWithOutFisrtChar("abc_name_"));
		assertEquals("BcdName", StringTools.upperWithOutFisrtChar("_bcd_name_"));
	}
	
	
	
	
}

