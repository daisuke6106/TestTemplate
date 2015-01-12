package jp.co.dk.test.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jp.co.dk.message.AbstractMessage;
import jp.co.dk.message.exception.AbstractMessageException;
import jp.co.dk.test.template.property.TestTemplateProperty;

import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import static org.hamcrest.core.AnyOf.anyOf;

/**
 * TestCaseTemplateは、JUnitを使用して試験を行う際に使用するテストケースクラスの基底クラスです。<br/>
 * <br/>
 * 本クラスを使用することでJUnitがテストケースを実行する開始する直前、終了した直後に<br/>
 * <br/>
 * ・実行にかかった時間(秒・ミリ秒・ナノ秒)<br/>
 * ・メモリ使用量(MByte,KByte,Byte)<br/>
 * <br/>
 * を取得し、結果を表示します。<br/>
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class TestCaseTemplate {
	
	@Rule
	public TestName testName = new TestName();
	
	private TimeCounter timeCounter;
	
	private MemoryCounter memoryCounter;
	
	private static TestCaseTemplate caseTemplate = new TestCaseTemplate();
	
	// テスト用処理 ====================================================================================================
	
	/**
	 * 本テストクラスを呼び出す前に実行され、<br/>
	 * <br/>
	 * ・標準出力へのヘッダー出力
	 * <br/>
	 * を実行します。
	 * 
	 * @since 1.0
	 */
	@BeforeClass 
	public static void beforeClass() {
		TestCaseTemplate.caseTemplate.print(TestTemplateProperty.CONSOLE_HEADER.getString());
		TestCaseTemplate.caseTemplate.print(TestCaseTemplate.caseTemplate.getClassName());
		if (TestTemplateProperty.CREATE_TEST_TEMP_DIR.getBoolean()) {
			TestCaseTemplate.caseTemplate.getTestTmpDir();
			TestCaseTemplate.caseTemplate.getTestTmpFile();
			TestCaseTemplate.caseTemplate.getTestTmpDirReadOnly();
			TestCaseTemplate.caseTemplate.getTestTmpFileReadOnly();
			TestCaseTemplate.caseTemplate.getTestTmpDirWriteOnly();
			TestCaseTemplate.caseTemplate.getTestTmpFileWriteOnly();
		}
	}

	/**
	 * 本テストクラスを呼び出した後に実行され、<br/>
	 * <br/>
	 * ・標準出力へのフッター出力
	 * <br/>
	 * を実行します。
	 * 
	 * @since 1.0
	 */
	@AfterClass 
	public static void afterClass() {
		TestCaseTemplate.caseTemplate.print(TestTemplateProperty.CONSOLE_FOTTER.getString());
		if (TestTemplateProperty.DELETE_TEST_TEMP_FILE.getBoolean()) {
			TestCaseTemplate.caseTemplate.deleteTestTmpFile();
			TestCaseTemplate.caseTemplate.deleteTestTmpDir();
			TestCaseTemplate.caseTemplate.deleteTestTmpFileReadOnly();
			TestCaseTemplate.caseTemplate.deleteTestTmpDirReadOnly();
			TestCaseTemplate.caseTemplate.deleteTestTmpFileWriteOnly();
			TestCaseTemplate.caseTemplate.deleteTestTmpDirWriteOnly();
		}
	}
	
	/**
	 * テストケースが実行される前に実行され、<br/>
	 * <br/>
	 * ・実行時間の計測開始<br/>
	 * ・メモリの使用状況の計測開始<br/>
	 * を実行します。<br/>
	 * 
	 * @since 1.0
	 */
	@Before
	public void before() {
		System.out.println("==============================[Method Name]" + testName.getMethodName()+"==============================");
		this.timeCounter = new TimeCounter();
		this.memoryCounter = new MemoryCounter();
		this.timeCounter.start();
		this.memoryCounter.start();
	}
	
	/**
	 * テストケースが実行された後に実行され、<br/>
	 * <br/>
	 * ・実行時間の計測の完了<br/>
	 * ・メモリの使用状況の完了<br/>
	 * を実行します。<br/>
	 * 
	 * @since 1.0
	 */
	@After
	public void after() {
		this.timeCounter.fin();
		this.memoryCounter.fin();
		this.print(this.timeCounter.toString());
		this.print(this.memoryCounter.toString());
	}
	
	// 比較関連     ====================================================================================================
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(boolean value1, boolean value2) {
		org.junit.Assert.assertEquals(new Boolean(value1), new Boolean(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(byte value1, byte value2) {
		org.junit.Assert.assertEquals(new Byte(value1), new Byte(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(short value1, short value2) {
		org.junit.Assert.assertEquals(new Short(value1), new Short(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(int value1, int value2) {
		org.junit.Assert.assertEquals(new Integer(value1), new Integer(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(long value1, long value2) {
		org.junit.Assert.assertEquals(new Long(value1), new Long(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(float value1, float value2) {
		org.junit.Assert.assertEquals(new Float(value1), new Float(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(char value1, char value2) {
		org.junit.Assert.assertEquals(new Character(value1), new Character(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param value1 期待値
	 * @param value2 実際値
	 */
	protected void assertEquals(double value1, double value2) {
		org.junit.Assert.assertEquals(new Double(value1), new Double(value2));
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param object1 期待値
	 * @param object2 実際値
	 */
	protected void assertEquals(Object object1, Object object2) {
		org.junit.Assert.assertEquals(object1, object2);
	}
	
	/**
	 * 指定の発生した例外とメッセージオブジェクトが一致する場合、テスト成功とする。
	 * 
	 * @param messageException 発生した例外
	 * @param message          メッセージオブジェクト
	 */
	protected void assertEquals(AbstractMessageException messageException, AbstractMessage message) {
		org.junit.Assert.assertEquals(messageException.getMessageObj(), message);
	}
	
	/**
	 * 指定の発生した例外とメッセージオブジェクトが一致する場合、テスト成功とする。
	 * 
	 * @param messageException 発生した例外
	 * @param message          メッセージオブジェクト
	 * @param replaceStr       置換文字列
	 */
	protected void assertEquals(AbstractMessageException messageException, AbstractMessage message, String... replaceStr) {
		org.junit.Assert.assertEquals(messageException.getMessageObj(), message);
		List<String> embeddedStrList = messageException.getEmbeddedStrList();
		if (embeddedStrList == null || embeddedStrList.size() == 0) fail();
		if (replaceStr      == null || replaceStr.length      == 0) fail();
		if (embeddedStrList.size() != replaceStr.length) fail();
		for (int i=0; i<embeddedStrList.size(); i++) {
			assertEquals(embeddedStrList.get(i), replaceStr[i]);
		}
	}
	
	// ================================================================================================================
	
	// 配列比較関連 ====================================================================================================
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(boolean[] array1, boolean[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Boolean(array1[i]), new Boolean(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(byte[] array1, byte[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Byte(array1[i]), new Byte(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(short[] array1, short[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Short(array1[i]), new Short(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(int[] array1, int[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Integer(array1[i]), new Integer(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(long[] array1, long[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Long(array1[i]), new Long(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(float[] array1, float[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Float(array1[i]), new Float(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(char[] array1, char[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Character(array1[i]), new Character(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(double[] array1, double[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(new Double(array1[i]), new Double(array2[i])); 
		}
	}
	
	/**
	 * 指定の期待値と実際値が一致する場合、テスト成功とする。
	 * 
	 * @param array1 期待値
	 * @param array2 実際値
	 */
	protected void assertEquals(Object[] array1, Object[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i=0; i<array1.length; i++) {
			org.junit.Assert.assertEquals(array1[i], array2[i]); 
		}
	}
	
	// ================================================================================================================
	
	// ファイル・データ比較関連 =========================================================================================
	/**
	 * 指定の実際値ファイルと期待値ファイルの内容が一致する場合、テスト成功とする。<p/>
	 * 
	 * それ以外は試験失敗とする。<br/>
	 * ・実際値ファイル、期待値ファイルがnullの場合<br/>
	 * ・実際値ファイル、期待値ファイルが存在しない場合<br/>
	 * ・実際値ファイル、期待値ファイルがディレクトリの場合<br/>
	 * ・実際値ファイル、期待値ファイルのファイルサイズが異なる場合<br/>
	 * ・実際値ファイル、期待値ファイルの内容が異なる場合<br/>
	 * 
	 * @param file1 実際値ファイルオブジェクト
	 * @param file2 期待値ファイルオブジェクト
	 */
	protected void assertFileEquals(File file1, File file2) {
		if (file1 == null || file2 == null) org.junit.Assert.fail();
		if (!file1.exists()) org.junit.Assert.fail();
		if (!file2.exists()) org.junit.Assert.fail();
		if (file1.isDirectory() || file2.isDirectory()) org.junit.Assert.fail();
		if (file1.length() != file2.length()) org.junit.Assert.fail();
		
		try {
			FileInputStream fi1 = new FileInputStream(file1);
			FileInputStream fi2 = new FileInputStream(file2);
			assertStreamEquals(fi1, fi2);
			try {
				fi1.close();
			} catch (IOException e) {
				fail(e);
			}
			try {
				fi2.close();
			} catch (IOException e) {
				fail(e);
			}
		} catch (IOException e) {
			fail(e);
		}
	}
	
	/**
	 * 指定の実際値ストリームと期待値ストリームの内容が一致する場合、テスト成功とする。<p/>
	 * 
	 * それ以外は試験失敗とする。<br/>
	 * ・実際値ストリーム、期待値ストリームがnullの場合<br/>
	 * ・実際値ストリーム、期待値ストリームの内容が異なる場合<br/>
	 * 
	 * @param stream1 実際値ストリームオブジェクト
	 * @param stream2 期待値ストリームオブジェクト
	 */
	protected void assertStreamEquals(InputStream stream1, InputStream stream2) {
		if (stream1 == null || stream2 == null) org.junit.Assert.fail();
		int byte1;
		int byte2;
		try {
			while (((byte1 = stream1.read()) != -1) && ((byte2 = stream2.read()) != -1)) {
				if (byte1 != byte2) org.junit.Assert.fail();
			}
		} catch (FileNotFoundException e) {
			fail(e);
		} catch (IOException e) {
			fail(e);
		}
	}
	
	// ================================================================================================================
	
	// AssertThat関連 ====================================================================================================
	protected <T> void assertThat(T actual, Matcher<T> matcher) {
		org.junit.Assert.assertThat(actual, matcher);
	}
	
	protected void assertThat(boolean actual, Matcher<Boolean> matcher) {
		org.junit.Assert.assertThat(new Boolean(actual), matcher);
	}

	protected void assertThat(byte actual, Matcher<Byte> matcher) {
		org.junit.Assert.assertThat(new Byte(actual), matcher);
	}

	protected void assertThat(double actual, Matcher<Double> matcher) {
		org.junit.Assert.assertThat(new Double(actual), matcher);
	}

	protected void assertThat(float actual, Matcher<Float> matcher) {
		org.junit.Assert.assertThat(new Float(actual), matcher);
	}

	protected void assertThat(int actual, Matcher<Integer> matcher) {
		org.junit.Assert.assertThat(new Integer(actual), matcher);
	}

	protected void assertThat(long actual, Matcher<Long> matcher) {
		org.junit.Assert.assertThat(new Long(actual), matcher);
	}

	protected void assertThat(short actual, Matcher<Short> matcher) {
		org.junit.Assert.assertThat(new Short(actual), matcher);
	}

	protected void assertThat(char actual, Matcher<Character> matcher) {
		org.junit.Assert.assertThat(new Character(actual), matcher);
	}
	
	protected <T> Matcher<T> is (T matcher) {
		return org.hamcrest.core.Is.is(matcher);
	}
	
	protected Matcher<Boolean> is (boolean matcher) {
		return org.hamcrest.core.Is.is(new Boolean(matcher));
	}
	
	protected Matcher<Byte> is (byte matcher) {
		return org.hamcrest.core.Is.is(new Byte(matcher));
	}
	
	protected Matcher<Double> is (double matcher) {
		return org.hamcrest.core.Is.is(new Double(matcher));
	}
	
	protected Matcher<Float> is (float matcher) {
		return org.hamcrest.core.Is.is(new Float(matcher));
	}
	
	protected Matcher<Integer> is (int matcher) {
		return org.hamcrest.core.Is.is(new Integer(matcher));
	}
	
	protected Matcher<Long> is (long matcher) {
		return org.hamcrest.core.Is.is(new Long(matcher));
	}
	
	protected Matcher<Short> is (short matcher) {
		return org.hamcrest.core.Is.is(new Short(matcher));
	}
	
	protected Matcher<Character> is (char matcher) {
		return org.hamcrest.core.Is.is(new Character(matcher));
	}
	
	protected <T> Matcher<T> equalTo (T operand) {
		return org.hamcrest.core.IsEqual.equalTo(operand);
	}
	
	protected <T> Matcher<T> not (T matchers) {
		return org.hamcrest.core.IsNot.not(matchers);
	}
	
	protected Matcher<Boolean> not (boolean matcher) {
		return org.hamcrest.core.IsNot.not(new Boolean(matcher));
	}
	
	protected Matcher<Byte> not (byte matcher) {
		return org.hamcrest.core.IsNot.not(new Byte(matcher));
	}
	
	protected Matcher<Double> not (double matcher) {
		return org.hamcrest.core.IsNot.not(new Double(matcher));
	}
	
	protected Matcher<Float> not (float matcher) {
		return org.hamcrest.core.IsNot.not(new Float(matcher));
	}
	
	protected Matcher<Integer> not (int matcher) {
		return org.hamcrest.core.IsNot.not(new Integer(matcher));
	}
	
	protected Matcher<Long> not (long matcher) {
		return org.hamcrest.core.IsNot.not(new Long(matcher));
	}
	
	protected Matcher<Short> not (short matcher) {
		return org.hamcrest.core.IsNot.not(new Short(matcher));
	}
	
	protected Matcher<Character> not (char matcher) {
		return org.hamcrest.core.IsNot.not(new Character(matcher));
	}
	
	protected Matcher nullValue () {
		return org.hamcrest.core.IsNull.nullValue();
	}
	
	protected Matcher notNullValue () {
		return org.hamcrest.core.IsNull.notNullValue();
	}
	
	protected <T> Matcher<T> nullValue (@SuppressWarnings("unused") Class<T> type) {
		return org.hamcrest.core.IsNull.nullValue(type);
	}
	
	protected <T> Matcher<T> notNullValue (@SuppressWarnings("unused") Class<T> type) {
		return org.hamcrest.core.IsNull.notNullValue(type);
	}
	
	// ===================================================================================================================
	
	// その他比較関連 =====================================================================================================
	/**
	 * 指定の実際値に文字列が含まれている場合、テスト成功とする。
	 * 
	 * @param baseString 実際値
	 * @param str 検出対象文字列
	 */
	protected void assertHasString(String baseString, String str) {
		int index = baseString.indexOf(str);
		if (index == -1) fail(); 
	}
	
	/**
	 * Object1とObject2が同じオブジェクトを参照している場合、テスト成功とする。<p/>
	 * 同じオブジェクトでなかった場合はFailure（失敗）になります。
	 * 
	 * @param object1 期待値
	 * @param object2 実際値
	 */
	protected void assertSame(Object object1, Object object2) {
		org.junit.Assert.assertSame(object1, object2);
	}
	
	/**
	 * 指定のobjectが NULL でない場合、テスト成功とする。
	 * 
	 * @param object 検証値
	 */
	protected void assertNotNull(Object object) {
		org.junit.Assert.assertNotNull(object);
	}
	
	/**
	 * 指定のobjectが NULL の場合、テスト成功とする。
	 * 
	 * @param object 検証値
	 */
	protected void assertNull(Object object) {
		org.junit.Assert.assertNull(object);
	}
	// ===================================================================================================================
	
	/**
	 * privateメソッドを実行します<p/>
	 * テスト対象のターゲットクラスのインスタンス、実行対象のprivateメソッド名称、メソッドの引数を元に<br/>
	 * privateメソッドを実行し、戻り値をオブジェクト型として返却する。<br/>
	 * 
	 * privateメソッドで例外が発生しthrowされた場合、または呼び出しに失敗した場合、例外が発生する。<br/>
	 * 
	 * @param targetInstance テスト対象のターゲットクラスのインスタンス
	 * @param methodName 実行対象のprivateメソッド名称
	 * @param objects メソッドの引数
	 * @return privateメソッドの戻り値
	 * @throws InvocationTargetException privateメソッド実行で発生した例外
	 */
	protected Object executePrivateMethod(Object object, String methodName, Object... parameters) throws Throwable{
		Class classObject = object.getClass();
		Method method = null;
		try {
			if (parameters == null || parameters.length == 0) {
				method = classObject.getDeclaredMethod(methodName);
			} else {
				Class[] classList = new Class[parameters.length];
				for (int i = 0 ; i < parameters.length; i++) {
					classList[i] = (parameters[i].getClass());
				}
				method = classObject.getDeclaredMethod(methodName, classList);
			}
			method.setAccessible( true );
		} catch (NoSuchMethodException e) {
			fail(e);
		} catch (SecurityException e) {
			fail(e);
		}
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			fail(e);
		} catch (IllegalArgumentException e) {
			fail(e);
		} catch (InvocationTargetException e) {
			throw e;
		}
		return null;
	}
	
	
	protected void success(Throwable e) {
		
	}
	
	/**
	 * テスト失敗。<p/>
	 * 
	 * このメソッドが実行された場合、試験失敗とする。
	 * 
	 */
	protected void fail() {
		org.junit.Assert.fail();
	}
	
	/**
	 * テスト失敗。<p/>
	 * 
	 * このメソッドが実行された場合、試験失敗とする。
	 * 
	 */
	protected void fail(String message) {
		org.junit.Assert.fail(message);
	}
	
	/**
	 * テスト失敗。<p/>
	 * 
	 * このメソッドが実行された場合、試験失敗とする。
	 * 
	 */
	protected void fail(Throwable e) {
		e.printStackTrace();
		org.junit.Assert.fail(e.getMessage());
	}
	
	/**
	 * テスト失敗。<p/>
	 * 
	 * このメソッドが実行された場合、試験失敗とする。
	 * 
	 */
	protected void fail(AbstractMessageException e) {
		e.printStackTrace();
		org.junit.Assert.fail(e.getMessage());
	}
	
	/**
	 * リスト内に設定されている要素の全パターンを生成し返却します。</p>
	 * 指定の要素がnullまたは空の場合、空のリストを返却します。
	 * 
	 * 例：
	 * 引数の要素："1","2","3"の場合<br/>
	 * ["1","2","3"]<br/>
	 * ["1","3","2"]<br/>
	 * ["2","1","3"]<br/>
	 * ["2","3","1"]<br/>
	 * ["3","1","2"]<br/>
	 * ["3","2","1"]<br/>
	 * を保持したリストを返却します。
	 * 
	 * @param elementList 生成対象オブジェクトのリスト
	 * @return 要素の全パターンを保持したリスト
	 */
	public <E> List<List<E>> getAllPatternList(List<E> elementList) {
		List<List<E>> returnList = new ArrayList<List<E>>();
		if (elementList == null || elementList.size() == 0) {
			return returnList;
		} else if (elementList.size() == 1) {
			returnList.add(elementList);
			return returnList;
		} else if(elementList.size() == 2) {
			returnList.add(elementList);
			returnList.add(this.reversalList(elementList));
		} else {
			for(int i=0; i<elementList.size(); i++) {
				List<E> copyElementList = new ArrayList<E>(elementList);
				E element = copyElementList.remove(i);
				List<List<E>> patternList = this.getAllPatternList(copyElementList);
				for (List<E> list : patternList) {
					List<E> newList = new ArrayList<E>();
					newList.add(element);
					newList.addAll(list);
					returnList.add(newList);
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 引数に指定されたリストの要素を逆順にした新しいリストを返却します。
	 * 
	 * @param targetList 逆転対象リスト
	 * @return 逆順に並べ直したリスト
	 */
	protected <E> List<E> reversalList(List<E> targetList) {
		if (targetList == null) return null;
		List<E> reversalList = new ArrayList<E>();
		for (int i = targetList.size() -1; 0<=i ; i--) {
			reversalList.add(targetList.get(i));
		}
		return reversalList;
	}
	
	/**
	 * 指定のオブジェクトの一覧がオブジェクトの一覧のリスト内に含まれるか判定する。<p/>
	 * 判定は、順序、オブジェクトがすべて一致するリストが存在した場合のみtrueとなる。<br/>
	 * 
	 * @param lists オブジェクトの一覧のリスト
	 * @param list 判定対象のオブジェクトの一覧
	 * @return 判定結果（true=一致するリストが存在する。false=一致するリストが存在しない。）
	 */
	protected <E> boolean hasList(List<List<E>> lists, List<E> list) {
		List<Integer> indexList = getEqualsListIndex(lists, list);
		if (indexList == null || indexList.size() == 0) return false;
		return true;
	}
	
	/**
	 * 指定のリストから指定の要素を検索し、要素の存在有無を返却する。<p/>
	 * 要素の検索判定はequalsにて判定を行う。
	 * 
	 * @param list 要素の検索対象のリスト
	 * @param element 検索する要素
	 * @return 検索結果（true:存在する、false:存在しない）
	 */
	protected <E> boolean hasList(List<E> list, E element) {
		if (list == null) return false;
		for (E e : list) {
			if (e == null && element == null) return true;
			if (e == null) continue;
			if (e.equals(element)) return true;
		}
		return false;
	}
	
	/**
	 * オブジェクトの一覧のリスト(Listを保持するList)内に対して指定のオブジェクトの一覧(List)と一致するものが存在するか判定し、
	 * 一致したインデックス番号の一覧を返却します。<p/>
	 * 判定は、順序、オブジェクトがすべて一致するリストが存在した場合のみtrueとなる。<br/>
	 * 一致するリストが存在しなかった場合、空のリストを返却します。<br/>
	 * 
	 * @param lists オブジェクトの一覧のリスト 
	 * @param list オブジェクトの一覧
	 * @return インデックス番号の一覧
	 */
	protected <E> List<Integer> getEqualsListIndex(List<List<E>> lists, List<E> list) {
		List<Integer> indexList = new ArrayList<Integer>(); 
		if (lists == null) return indexList;
		for (int index = 0; index<lists.size() ;index++) {
			if (asList(lists.get(index), list)) indexList.add(new Integer(index)); 
		}
		return indexList;
	}
	
	/**
	 * リスト同士を比較し、リスト内に保持する要素がすべて同じものか判定します。<p/>
	 * 判定は、順序、オブジェクトがすべて一致してtrueとなる。<br/>
	 * 引数の両リストがnullの場合、trueを返却する。<br/>
	 * 
	 * @param list1 比較対象リスト1
	 * @param list2 比較対象リスト2
	 * @return 判定結果（true=リスト内容がすべて一致、false=一致しない）
	 */
	protected <E> boolean asList(List<E> list1, List<E> list2) {
		if (list1 == null && list2 == null) return true;
		if (list1 == null || list2 == null) return false;
		if (list1.size() != list2.size()) return false;
		for (int i = 0; i<list1.size(); i++) {
			if (!list1.get(i).equals(list2.get(i))) return false; 
		}
		return true;
	}
	
	private void print(String str) {
		
	}
	
	private String getClassName() {
		StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
		return stackTraceElement[1].getClassName();
	}
	
	// 日付関連 ====================================================================================================
	
	/**
	 * 指定の日付インスタンスをYYYYMMDD形式の文字列に変換し、返却します。
	 * @param date 日付インスタンス
	 * @return YYYMMDD形式の文字列
	 */
	protected String getStringByDate_YYYYMMDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).toString();
	}
	
	/**
	 * 指定の日付インスタンスをYYYYMMDDHH24MMDD形式の文字列に変換し、返却します。
	 * @param date 日付インスタンス
	 * @return YYYYMMDDHH24MMDD形式の文字列
	 */
	protected String getStringByDate_YYYYMMDDHH24MMDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date).toString();
	}
	
	/**
	 * YYYYMMDDHH24MMSS形式の日付を表す文字列から日付インスタンスを生成し、返却します。
	 * @param yyyymmddhh24mmss YYYYMMDDHH24MMSS形式の日付を表す文字列
	 * @return 日付インスタンス
	 * @throws ParseException 変換失敗した場合
	 */
	protected Date createDateByString(String yyyymmddhh24mmss) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.parse(yyyymmddhh24mmss);
	}
	
	/**
	 * 本日日付から指定の日数を引いた日付インスタンスを返却する。
	 * 
	 * @param dayCount 日数
	 * @return 計算結果日付インスタンス
	 */
	protected Date getBeforeDate(int dayCount) {
		return this.getBeforeDate(new Date(), dayCount);
	}
	
	/**
	 * 指定の日付インスタンスから指定の日数を引いた日付インスタンスを返却する。
	 * 
	 * @param date 計算対象日付インスタンス
	 * @param dayCount 日数
	 * @return 計算結果日付インスタンス
	 */
	protected Date getBeforeDate(Date date, int dayCount) {
		long mday = (1000L * 60L * 60L * 24L) * (long)dayCount;
		long time = date.getTime();
		return new Date(time - mday);
	}
	
	/**
	 * 本日日付から指定の日数を引いた日付インスタンスを返却する。
	 * 
	 * @param dayCount 日数
	 * @return 計算結果日付インスタンス
	 */
	protected Date getAfterDate(int dayCount) {
		return this.getAfterDate(new Date(), dayCount);
	}
	
	/**
	 * 指定の日付インスタンスから指定の日数を足した日付インスタンスを返却する。
	 * 
	 * @param date 計算対象日付インスタンス
	 * @param dayCount 日数
	 * @return 計算結果日付インスタンス
	 */
	protected Date getAfterDate(Date date, int dayCount) {
		long mday = (1000L * 60L * 60L * 24L) * (long)dayCount;
		long time = date.getTime();
		return new Date(time + mday);
	}
	
	// ファイル・ディレクトリ関連 ====================================================================================================
	
	/**
	 * テスト用一時作業ディレクトリを作成する。
	 * 
	 * @return テスト用一時作業ディレクトリ
	 */
	protected java.io.File getTestTmpDir() {
		java.io.File dir = this.createDir(this.getTestTmpDirInstance());
		dir.setReadable(true, true);
		dir.setWritable(true, true);
		dir.setExecutable(true, true);
		return dir;
	}
	
	/**
	 * テスト用一時作業ファイルを作成する。
	 * 
	 * @return テスト用一時作業ファイル
	 */
	protected java.io.File getTestTmpFile() {
		java.io.File file = this.createFile(this.getTestTmpFileInstance());
		file.setReadable(true, true);
		file.setWritable(true, true);
		file.setExecutable(true, true);
		return file;
	}
	
	/**
	 * テスト用一時作業ディレクトリ（読み込み専用）を作成する。
	 * 
	 * @return テスト用一時作業ディレクトリ
	 */
	protected java.io.File getTestTmpDirReadOnly() {
		java.io.File dir = this.createDir(this.getTestTmpDirReadOnlyInstance());
		dir.setReadable(true, true);
		dir.setWritable(false, false);
		dir.setExecutable(true, true);
		return dir;
	}
	
	/**
	 * テスト用一時作業ファイルを作成する。
	 * 
	 * @return テスト用一時作業ファイル
	 */
	protected java.io.File getTestTmpFileReadOnly() {
		java.io.File file = this.createFile(this.getTestTmpFileReadOnlyInstance());
		file.setReadable(true, true);
		file.setWritable(false, false);
		file.setExecutable(true, true);
		return file;
	}
	
	/**
	 * テスト用一時作業ディレクトリ（読み込み専用）を作成する。
	 * 
	 * @return テスト用一時作業ディレクトリ
	 */
	protected java.io.File getTestTmpDirWriteOnly() {
		java.io.File dir = this.createDir(this.getTestTmpDirWriteOnlyInstance());
		dir.setReadable(false, false);
		dir.setWritable(true, true);
		dir.setExecutable(false, false);
		return dir;
	}
	
	/**
	 * テスト用一時作業ファイルを作成する。
	 * 
	 * @return テスト用一時作業ファイル
	 */
	protected java.io.File getTestTmpFileWriteOnly() {
		java.io.File file = this.createFile(this.getTestTmpFileWriteOnlyInstance());
		file.setReadable(false, false);
		file.setWritable(true, true);
		file.setExecutable(false, false);
		return file;
	}
	
	/**
	 * テスト用一時作業ディレクトリを削除する。
	 */
	protected void deleteTestTmpDir() {
		this.delete(this.getTestTmpDirInstance());
	}
	
	/**
	 * テスト用一時作業ファイルを削除する。
	 */
	protected void deleteTestTmpFile() {
		this.delete(this.getTestTmpFileInstance());
	}
	
	/**
	 * テスト用一時作業ディレクトリ（読み取り専用）を削除する。
	 */
	protected void deleteTestTmpDirReadOnly() {
		this.delete(this.getTestTmpDirReadOnlyInstance());
	}
	
	/**
	 * テスト用一時作業ファイル（読み取り専用）を削除する。
	 */
	protected void deleteTestTmpFileReadOnly() {
		this.delete(this.getTestTmpFileReadOnlyInstance());
	}
	
	/**
	 * テスト用一時作業ディレクトリ（書き込み専用）を削除する。
	 */
	protected void deleteTestTmpDirWriteOnly() {
		this.delete(this.getTestTmpDirWriteOnlyInstance());
	}
	
	/**
	 * テスト用一時作業ファイル（書き込み専用）を削除する。
	 */
	protected void deleteTestTmpFileWriteOnly() {
		this.delete(this.getTestTmpFileWriteOnlyInstance());
	}
	
	/**
	 * テスト用の存在しないディレクトリのオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	protected java.io.File getFraudTestTmpDir() {
		return new File(TestTemplateProperty.TEST_FRAUD_TEMP_DIR.getString());
	}
	
	/**
	 * クラスと同じディレクトリにあるファイルのファイルオブジェクトを取得する。
	 * 
	 * @param name ファイル名
	 * @return ファイルオブジェクト
	 */
	protected File  getFileByOwnClass(String name) {
		String fullPath = this.getClass().getResource(name).getPath();
		return new File(fullPath);
	}
	
	/**
	 * クラスと同じディレクトリにあるファイルの入力ストリームを取得する。
	 * 
	 * @param name ファイル名
	 * @return 入力ストリーム
	 */
	protected InputStream getInputStreamByOwnClass(String name) {
		try {
			return new FileInputStream (this.getFileByOwnClass(name));
		} catch (FileNotFoundException e) {
			this.fail(e);
		}
		return null;
	}
	
	/**
	 * パッケージ階層が始まる基点からリソースを探し、指定のファイルの入力ストリームを取得する。
	 * 
	 * 例：jp/co/test/TestFile.txt読み込む場合<br/>
	 * 引き数に"jp/co/test/TestFile.txt"を設定することで読み込めます。
	 * 
	 * @param name ファイル名
	 * @return 入力ストリーム
	 */
	protected InputStream getInputStreamBySystemResource(String name) {
		return ClassLoader.getSystemResourceAsStream(name);
	}
	
	
	/**
	 * テスト用一時作業ディレクトリに指定のファイル名でファイルオブジェクトのインスタンスを生成、返却します。<p/>
	 * 参照プロパティ＝test.tempdir（テスト用一時作業ディレクトリ）
	 * 
	 * @param fileName テスト用一時ディレクトリに作成するファイル名
	 * @return ファイルオブジェクトのインスタンス
	 */
	protected java.io.File getTestFileInstance(String fileName) {
		String tempDir = TestTemplateProperty.TEST_TEMP_DIR.getString();
		StringBuilder sb = new StringBuilder(tempDir);
		if (tempDir.endsWith("/") || tempDir.endsWith("¥¥")) {
			sb.append(fileName);
		} else {
			sb.append('/').append(fileName);
		}
		return new File(sb.toString());
	}
	
	/**
	 * 指定のパスにディレクトリを作成する。<br/>
	 * すでに存在する場合、何も行わない。
	 * 
	 * @param file ディレクトリ作成先パス
	 * @return 作成されたディレクトリオブジェクト
	 */
	private java.io.File createDir(File file) {
		if (file == null || file.exists()) {
			return file;
		}
		if (!file.mkdirs()) {
			fail(new StringBuilder("failed to create directory. dir=[").append(file.toString()).append(']').toString());
		}
		return file;
	}
	
	/**
	 * 指定のパスに空のファイルを作成する。<br/>
	 * すでに存在する場合、何も行わない。
	 * 
	 * @param file 空ファイル作成先パス
	 * @return 作成されたディレクトリオブジェクト
	 */
	private java.io.File createFile(File file) {
		if (file == null || file.exists()) {
			return file;
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			fail(new StringBuilder(e.getMessage()).append(" file=[").append(file.toString()).append(']').toString());
		}
		return file;
	}
	
	/**
	 * 指定パスに存在するファイルを削除する。<br/>
	 * 指定パスがディレクトリの場合、そのディレクトリの属するファイルもすべて削除する。
	 * 
	 * @param file 削除対象のファイル、ディレクトリへのパス
	 */
	private void delete(File file) {
		if (file == null || !file.exists()) {
			return ;
		}
		if (file.isFile()) {
			if (!file.delete()) {
				fail(new StringBuilder("failed to delete directory or file. file=[").append(file.toString()).append(']').toString());
			}
			return;
		}
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			if (files != null) {
				for (File deleteFile : files) {
					this.delete(deleteFile);
				}
			}
			if (!file.delete()) {
				fail(new StringBuilder("failed to delete directory or file. file=[").append(file.toString()).append(']').toString());
			}
		}
	}
	
	/**
	 * テスト用一時作業ディレクトリのオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpDirInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_DIR.getString());
	}
	
	/**
	 * テスト用一時作業ファイルのオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpFileInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_FILE.getString());
	}
	
	/**
	 * テスト用一時作業ディレクトリ（読み取り専用）のオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpDirReadOnlyInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_DIR_READONLY.getString());
	}
	
	/**
	 * テスト用一時作業ファイル（読み取り専用）のオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpFileReadOnlyInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_FILE_READONLY_FILE.getString());
	}
	
	/**
	 * テスト用一時作業ディレクトリ（書き込み専用）のオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpDirWriteOnlyInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_DIR_WRITE_ONLY.getString());
	}
	
	/**
	 * テスト用一時作業ファイル（書き込み専用）のオブジェクトを取得する。
	 * 
	 * @return ファイルオブジェクト
	 */
	private java.io.File getTestTmpFileWriteOnlyInstance() {
		return new File(TestTemplateProperty.TEST_TEMP_FILE_WRITE_ONLY.getString());
	}
	
	/**
	 * テスト用一時作業ディレクトリへ指定のファイル名でファイルを作成する。
	 * 
	 * @param fileName ファイル名
	 * @return ファイルオブジェクト
	 */
	protected java.io.File createFileToTmpDir(String fileName) {
		return this.createFileToDir(this.getTestTmpDir(), fileName);
	}
	
	/**
	 * 指定のディレクトリへ指定のファイル名でファイルを作成する。
	 * 
	 * @param dir ディレクトリ
	 * @param fileName ファイル名
	 * @return ファイルオブジェクト
	 */
	protected java.io.File createFileToDir(java.io.File dir, String fileName) {
		StringBuilder sb = new StringBuilder(dir.getAbsolutePath()).append('/').append(fileName);
		return this.createFile(new java.io.File(sb.toString()));
	}
	
	/**
	 * 指定のファイルオブジェクトの更新日付を指定の日付オブジェクトへ変換します。
	 * 
	 * @param dir ディレクトリ
	 * @param fileName ファイル名
	 * @return ファイルオブジェクト
	 */
	protected java.io.File setUpdateTimeToFile(java.io.File file, Date date) {
		if (!file.setLastModified(date.getTime())) {;
			fail(new StringBuilder("failed to update lastmodified. file=[").append(file.toString()).append(']').toString());
		}
		return file;
	}
	
	/**
	 * equalsメソッドのテストテンプレート<p/>
	 * 
	 * このメソッドへequals比較を行った結果、trueとなるオブジェクトを３つ、それとは別にequals比較を行った際にfalseとなるオブジェクトの一覧を引数に実行します。<br/>
	 * この指定された引数を元にequalsの一般契約への試験を実施します。<br/>
	 * 尚、一般契約の定義は以下とします。<br/>
	 * <br/>
	 * ・反射性 (reflexive): null 以外の参照値 x について、x.equals(x) は true を返す<br/>
	 * ・対称性 (symmetric): null 以外の参照値 x と y について、x.equals(y) は、y.equals(x) が true を返す場合だけ true を返す<br/>
	 * ・推移性 (transitive): null 以外の参照値 x、y、z について、x.equals(y) が true を返し、かつ y.equals(z) が true を返す場合に、x.equals(z) は true を返す<br/>
	 * ・整合性 (consistent): null 以外の参照値 x および y について、x.equals(y) を複数呼び出すと常に true を返すか、常に false を返す。これは、オブジェクトに対する equals による比較で使われた情報が変更されていないことが条件である<br/>
	 * ・hashCodeメソッドとの整合性: equals(Object) メソッドで 2 つのオブジェクトが等価とされた場合、どちらのオブジェクトで hashCode メソッドを呼び出しても結果は同じ整数値にならなければならない<br/>
	 * ・toStringの整合性<br/>
	 * 
	 * 使用方法：<br/>
	 * public void testEquals() {<br/>
	 *     XXXXX object1 = ....<br/>
	 *     XXXXX object2 = ....<br/>
	 *     XXXXX object3 = ....<br/>
	 *     <br/>
	 *     List<XXXXX> diffList = new ArrayList<XXXXX>();<br/>
	 *     diffList.add(new XXXXX(...));<br/>
	 *     diffList.add(new XXXXX(...));<br/>
	 *     diffList.add(new XXXXX(...));<br/>
	 *     super(object1, object2, object3, difList);<br/>
	 * }
	 * 
	 * @param object1 同一オブジェクト1
	 * @param object2 同一オブジェクト2
	 * @param object3 同一オブジェクト3
	 * @param differentObjectList 異なるオブジェクト一覧
	 */
	protected void testEquals(Object object1, Object object2, Object object3, List<Object> differentObjectList) {
		// 反射性 (reflexive): null 以外の参照値 x について、x.equals(x) は true を返す
		assertThat(new Boolean(object1.equals(object1)), is (new Boolean(true)));
		assertThat(new Boolean(object2.equals(object2)), is (new Boolean(true)));
		assertThat(new Boolean(object3.equals(object3)), is (new Boolean(true)));
		
		// 対称性 (symmetric): null 以外の参照値 x と y について、x.equals(y) は、y.equals(x) が true を返す場合だけ true を返す
		assertThat(new Boolean(object1.equals(object2) && object2.equals(object1)), is (new Boolean(true)));
		
		// 推移性 (transitive): null 以外の参照値 x、y、z について、x.equals(y) が true を返し、かつ y.equals(z) が true を返す場合に、x.equals(z) は true を返す
		assertThat(new Boolean(object1.equals(object2) && object2.equals(object3) && object1.equals(object3)), is (new Boolean(true)));
		
		// null 以外の参照値 x について、x.equals(null) は false を返す
		assertThat(new Boolean(object1.equals(null)), is (new Boolean(false)));
		assertThat(new Boolean(object2.equals(null)), is (new Boolean(false)));
		assertThat(new Boolean(object3.equals(null)), is (new Boolean(false)));
		
		// hashCodeの一貫性
		assertThat(new Boolean(object1.hashCode() == object2.hashCode()), is (new Boolean(true)));
		assertThat(new Boolean(object2.hashCode() == object3.hashCode()), is (new Boolean(true)));
		assertThat(new Boolean(object1.hashCode() == object3.hashCode()), is (new Boolean(true)));
		
		// toStringの一貫性
		assertThat(object1.toString() , is (object2.toString()));
		assertThat(object2.toString() , is (object3.toString()));
		assertThat(object1.toString() , is (object3.toString()));
		
		// 異なるオブジェクト一覧に設定されているオブジェクトはすべてfalseとなること
		for (Object diffObject : differentObjectList) {
			assertThat(new Boolean(object1.equals(diffObject)), is (new Boolean(false)));
		}
		
		// 整合性 (consistent): null 以外の参照値 x および y について、x.equals(y) を複数呼び出すと常に true を返すか、常に false を返す。これは、オブジェクトに対する equals による比較で使われた情報が変更されていないことが条件である
		Object diffObject = differentObjectList.get(0);
		for (int i = 0; i<100; i++) {
			assertThat(new Boolean(object1.equals(object2)), is (new Boolean(true)));
			assertThat(new Boolean(object1.equals(diffObject)), is (new Boolean(false)));
		}
	}
	
	/**
	 * 指定の開始数値から終了数値までの間でランダムな数値を生成し、返却する。
	 * 
	 * @param start 開始数
	 * @param fin   終了数
	 * @return 開始数から終了数までのランダムな数値
	 */
	public int getRandomInteger(int start, int fin) {
		if (start == fin) return start;
		if (!(start <= 0 && fin <= 0)) { 
			if (start < fin) return new Random().nextInt(fin+1) + start;
			else return new Random().nextInt(start+1) + fin;
		} else {
			start = -start;
			fin   = -fin;
			int result = 0;
			if (start < fin) result = new Random().nextInt(fin+1) + start;
			else result = new Random().nextInt(start+1) + fin;
			return -result;
		}
	}
	
	/**
	 * 指定のリストからランダムな要素を取得する。
	 * 
	 * @param list 取得対象のリスト
	 * @return ランダムな要素
	 */
	public <E> E getRandomElement(List<E> list) {
		if(list == null || list.size() == 0) return null;
		return list.get(this.getRandomInteger(0, list.size()-1));
	}
	
	/**
	 * 指定のリストから指定の条件に合致するランダムな要素を取得する。<p/>
	 * 合致するものがない場合、または、引数に渡された値がnullまたは空の場合、nullが返却されます。
	 * 
	 * @param list 取得対象のリスト
	 * @param rule 取得条件
	 * @return ランダムな要素
	 */
	public <E> E getRandomElement(List<E> list, RandomSelectRule rule) {
		if (rule == null) return null;
		if (list == null) return null;
		List<E> copyList = new ArrayList<E>(list); 
		while(!(copyList.isEmpty())) {
			E element = copyList.remove(this.getRandomInteger(0, copyList.size()-1));
			if (rule.match(element)) return element;
		}
		return null;
	}
	
	// シリアライズ関連 ====================================================================================================
	
	public void testSerialize(java.io.Serializable target) {
		try {
			File testTmpDir = getTestTmpDirInstance();
			StringBuilder testTmpFile = new StringBuilder();
			long fileName = new Date().getTime(); 
			testTmpFile.append(testTmpDir.getAbsolutePath()).append('/').append("testserialize_").append(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(testTmpFile.toString())));
			oos.writeObject(target);
			oos.close();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(testTmpFile.toString())));
			Object object = ois.readObject();
			ois.close();
			assertThat(target.equals(object), is(true));
		} catch (FileNotFoundException e) {
			fail(e);
		} catch (IOException e) {
			fail(e);
		} catch (ClassNotFoundException e) {
			fail(e);
		}
	}
}
