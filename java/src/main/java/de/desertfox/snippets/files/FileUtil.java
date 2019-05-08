package de.desertfox.snippets.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

	public static final File ROOT_DIR = new File(new File("").getAbsolutePath());

	public static String read(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()));
	}

	public static boolean delete(List<File> files) {
		boolean success = true;
		for (File file : files) {
			success &= delete(file);
		}
		return success;
	}

	public static boolean delete(File file) {
		if (file.exists()) {
			try {
				deleteRecursive(file);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private static void deleteRecursive(File file) throws IOException {
		if (file.isDirectory() && file.listFiles() != null) {
			for (File child : file.listFiles()) {
				deleteRecursive(child);
			}
		}
		file.delete();
	}

	public static boolean delete(File[] files) {
		return delete(Arrays.asList(files));
	}

	public static void move(File srcDir, File destDir) {
		String srcDirPath = srcDir.getAbsolutePath();
		String destDirPath = destDir.getAbsolutePath();
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		for (File file : srcDir.listFiles()) {
			file.renameTo(new File(file.getAbsolutePath().replace(srcDirPath, destDirPath)));
		}

	}

	public static File buildFile(String... paths) {
		return new File(build(paths));
	}

	private static String build(String... paths) {
		StringBuilder builder = new StringBuilder(paths[0]);
		for (int i = 1; i < paths.length; i++) {
			builder.append(File.separator).append(paths[i]);
		}
		return builder.toString();
	}

	public static File buildFile(File dir, String... paths) {
		return new File(dir, build(paths));
	}

}
