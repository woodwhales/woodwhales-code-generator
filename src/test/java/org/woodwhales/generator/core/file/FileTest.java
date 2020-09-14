package org.woodwhales.generator.core.file;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class FileTest {
	@Test
	public void testFile() throws Exception {
//		File file = FileUtils.getFile("D://abc");
//		boolean exists = file.exists();
//		System.out.println(exists);
//		
//		boolean directory = file.isDirectory();
//		System.out.println(directory);
//		
//		FileUtils.forceMkdir(file);
//		
//		boolean exists1 = file.exists();
//		System.out.println(exists1);
//		
//		boolean directory1 = file.isDirectory();
//		System.out.println(directory1);
//		
////		FileUtils.deleteDirectory(file);
//		
//		boolean exists2 = file.exists();
//		System.out.println(exists2);
//		
//		boolean directory2 = file.isDirectory();
//		System.out.println(directory2);
		
		FileUtils.forceMkdir(new File("D://abc/bcc/dd/ss"));
	}
}
