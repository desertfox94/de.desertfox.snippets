package de.desertfox.snippets.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchiver {

	private static final String JAR_SUFFIX = ".jar";

	private static final String ZIP_SUFFIX = ".zip";

	private ZipOutputStream zipStream;

	private Stack<String> relativePaths = new Stack<>();

	private String pathInZip = "";

	public File toJar(File file, String name) throws IOException {
		if (!name.endsWith(JAR_SUFFIX)) {
			name += JAR_SUFFIX;
		}
		return toArchive(file, name);
	}

	public File toZip(File file, String name) throws IOException {
		if (!name.endsWith(ZIP_SUFFIX)) {
			name += ZIP_SUFFIX;
		}
		return toArchive(file, name);
	}

	private File toArchive(File file, String name) throws IOException {
		File zipFile = new File(new File("").getAbsolutePath(), name);
		zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
		if (file.isFile()) {
			createArchive(file.toPath());
		} else {
			walktThroughDirectory(file);
		}
		zipStream.flush();
		zipStream.close();
		return zipFile;
	}

	private void walktThroughDirectory(File file) throws IOException {
		Files.list(file.toPath()).forEach(this::createArchive);
	}

	private void createArchive(Path path) {
		File file = path.toFile();
		try {
			if (file.isFile()) {
				write(file);
			} else {
				enterDirectory(file);
				walktThroughDirectory(file);
				leaveDirectory();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void enterDirectory(File file) {
		relativePaths.push(file.getName());
		relativePathStackToString();
	}

	private void leaveDirectory() {
		relativePaths.pop();
		relativePathStackToString();
	}

	private void relativePathStackToString() {
		StringBuffer buffer = new StringBuffer();
		relativePaths.forEach(s -> buffer.append(s).append(File.separator));
		pathInZip = buffer.toString();
	}

	private void write(File file) throws IOException {
		zipStream.putNextEntry(new ZipEntry(pathInZip + file.getName()));

		byte[] b = new byte[1024];
		int count;
		FileInputStream in = new FileInputStream(file);
		while ((count = in.read(b)) > 0) {
			zipStream.write(b, 0, count);
		}
		zipStream.flush();
		in.close();

	}

	public static File unzip(File zip) throws IOException {
		File destDir = new File(zip.getParentFile(), zip.getName().substring(0, zip.getName().indexOf('.')));
		return unzip(zip, destDir);
	}

	public static File unzip(File zip, File destDir) throws IOException {
		if (destDir.isFile()) {
			throw new RuntimeException("Cannot write Zip content to file. Target must be a directory!");
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = newFile(destDir, zipEntry);
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		return destDir;
	}

	private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}
		return destFile;
	}

}
