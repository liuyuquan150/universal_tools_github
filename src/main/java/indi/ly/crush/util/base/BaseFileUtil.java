package indi.ly.crush.util.base;

import indi.ly.crush.enumeration.SymbolEnum;
import lombok.SneakyThrows;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">文件工具</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseFileUtil {
	//---------------------------------------------------------------------
	// 判断两个文件的内容是否相同的一般便利方法
	//---------------------------------------------------------------------

	public static Boolean isSameContent(String before, String after) {
		return isSameContent(new File(before), new File(after) );
	}

	public static Boolean isNotSameContent(String before, String after) {
		return !isSameContent(new File(before), new File(after) );
	}

	@SneakyThrows(value = IOException.class)
	public static Boolean isSameContent(File before, File after) {
		return isSameContent(new FileInputStream(before), new FileInputStream(after));
	}

	public static Boolean isNotSameContent(File before, File after) {
		return !isSameContent(before, after);
	}
	
	//---------------------------------------------------------------------
	// 判断 File 对象的一般便利方法
	//---------------------------------------------------------------------
	
	public static Boolean notExists(File file) {
		return !Objects.requireNonNull(file).exists();
	}
	
	public static Boolean isNotDirectory(File file) {
		return !Objects.requireNonNull(file).isDirectory();
	}
	
	public static File notExistsThrowException(File file) throws Throwable {
		return BaseObjectUtil.isThrowException(file, BaseFileUtil :: notExists, () -> new RuntimeException("File Object 所表示的目录或文件不存在"));
	}
	
	public static File notDirectoryThrowException(File file) throws Throwable {
		return BaseObjectUtil.isThrowException(file, BaseFileUtil :: isNotDirectory, () -> new RuntimeException("File Object 不是一个文件夹"));
	}

	public static File notExistsThrowException(File file, Supplier<? extends Throwable> es)
			throws Throwable {
		return BaseObjectUtil.isThrowException(file, BaseFileUtil :: notExists, es);
	}
	
	public static File notDirectoryThrowException(File file, Supplier<? extends Throwable> es)
			throws Throwable {
		return BaseObjectUtil.isThrowException(file, BaseFileUtil :: isNotDirectory, es);
	}
	
	public static String getFileExtension(String originalName) {
		return BaseStringUtil.lastIndexOfSubstring(originalName, SymbolEnum.DOT_SYMBOL.getValue());
	}
	
	private static Boolean isSameContent(InputStream before, InputStream after)
			throws IOException {
		var result = BaseObjectUtil.isNotEmpty(before) && BaseObjectUtil.isNotEmpty(after);
		Assert.state(result, () -> "被比较内容是否相同的两个文件流对象其中至少有一个流对象为 null");
		return Objects.equals(DigestUtils.md5DigestAsHex(before), DigestUtils.md5DigestAsHex(after));
	}
	

	public static String createExportFilePath(String originalName) {
		return createExportFilePath(null, originalName);
	}
	
	public static String createExportFilePath(String exportDirectoryPath, String originalName) {
		BaseObjectUtil.isNull(originalName);
		
		var fileExtension = BaseFileUtil.getFileExtension(originalName);
		if (Objects.equals(originalName, fileExtension)) {
			throw new IllegalArgumentException(originalName + " 不是一个有效的文件名");
		}
		
		var fileName = BaseStringUtil.delete(originalName, fileExtension);
		var nowString = BaseLocalDateTimeUtil.format();
		var randomUuId = BaseUuIdUtil.randomUuId();
		var excelFileName = String.join(SymbolEnum.HYPHEN.getValue(), fileName, nowString, randomUuId, fileExtension);
		
		if (exportDirectoryPath == null) {
			return System.getProperty("java.io.tmpdir") + excelFileName;
		}
		
		var file = new File(exportDirectoryPath);
		if (notExists(file) && isNotDirectory(file)) {
			throw new IllegalArgumentException(exportDirectoryPath + " 不是一个有效的目录");
		}
		return exportDirectoryPath + excelFileName;
	}
}