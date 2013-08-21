package jp.co.dk.test.template.property;

import java.io.File;

import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.exception.PropertyException;

public class TestTemplateProperty extends AbstractProperty{
	
	/** コンソール表示用ヘッダー */
	public static TestTemplateProperty CONSOLE_HEADER = new TestTemplateProperty("console.header");
	
	/** コンソール表示用フッター */
	public static TestTemplateProperty CONSOLE_FOTTER = new TestTemplateProperty("console.fotter");
	
	/** テスト用・一時作業ディレクトリ作成 */
	public static TestTemplateProperty CREATE_TEST_TEMP_DIR = new TestTemplateProperty("create.test.tempdir");
	
	/** テスト用・一時作業ディレクトリ削除 */
	public static TestTemplateProperty DELETE_TEST_TEMP_FILE = new TestTemplateProperty("delete.test.tempdir");
	
	/** テスト用・一時作業ディレクトリ */
	public static TestTemplateProperty TEST_TEMP_DIR = new TestTemplateProperty("test.tempdir");
	
	/** テスト用・一時作業ファイル */
	public static TestTemplateProperty TEST_TEMP_FILE = new TestTemplateProperty("test.tempfile");
	
	/** テスト用・存在しないディレクトリ */
	public static TestTemplateProperty TEST_FRAUD_TEMP_DIR = new TestTemplateProperty("test.fraud.tempdir");
	
	/** テスト用・存在しないファイル */
	public static TestTemplateProperty TEST_FRAUD_TEMP_FILE = new TestTemplateProperty("test.fraud.tempfile");
	
	protected TestTemplateProperty(String key) throws PropertyException {
		super(new File("properties/test/TestTemplate.properties"), key);
	}
	
}
