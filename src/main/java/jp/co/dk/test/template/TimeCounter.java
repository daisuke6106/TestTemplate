package jp.co.dk.test.template;

/**
 * TimeCounterは、プログラムの実行時間を計測するクラスです。<br>
 * 
 * インスタンスを事前に生成しておき、時間計測を行う対象の箇所にてstart()を実行し、計測を開始。finish()を実行し、計測を停止する。
 * 計測完了後、各種類の秒数（ナノ秒、ミリ秒、秒）を取得することができます。
 * 
 * @version 1.0
 * @author D.Kanno
 * 
 */
public class TimeCounter {
	
	private long startTime;
	
	private boolean started = false;
	
	private long finTime;
	
	private boolean finished = false;
	
	private long result;
	
	/**
	 * 時間計測を開始します。<p>
	 * 
	 * @throws Exception すでに計測を開始している場合、例外を発生します。
	 */
	public void start() throws RuntimeException{
		if (this.started) throw new RuntimeException("Timer is already running.");
		this.startTime = System.nanoTime();
		this.started = true;
	}
	
	/**
	 * 時間計測を終了します。<p>
	 * 
	 * @throws Exception まだ計測を開始していない、またはすでに計測を終了している場合、例外を発生します。
	 */
	public void fin() throws RuntimeException{
		if (!this.started) throw new RuntimeException("Timer is not running.");
		if (this.finished) throw new RuntimeException("Timer is already stopped.");
		this.finTime = System.nanoTime();
		this.finished = true;
		this.result = this.finTime - this.startTime;
	}
	
	/**
	 * 計測結果をナノ秒として取得します。<p>
	 * 
	 * @return ナノ秒
	 */
	public long getNanosecond() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Timer is not stopped.");
		return this.result;
	}
	
	/**
	 * 計測結果をミリ秒として取得します。<p>
	 * 
	 * @return ミリ秒
	 */
	public long getMillisecond() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Timer is not stopped.");
		return this.result / 1000000;
	}
	
	/**
	 * 計測結果を秒として取得します。<p>
	 * 
	 * @return 秒
	 */
	public long getSecond() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Timer is not stopped.");
		return this.result / 1000000000;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append("Second      [").append(this.getSecond()).append(']');
		sb.append("Millisecond [").append(this.getMillisecond()).append(']');
		//sb.append("Nanosecond  [").append(this.getNanosecond()).append(']');
		return sb.toString();
	}
}
