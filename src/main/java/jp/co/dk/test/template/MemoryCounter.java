package jp.co.dk.test.template;

/**
 * MemoryCounterは、プログラムのメモリ状態を計測するクラスです。<br>
 * 
 * インスタンスを事前に生成しておき、時間計測を行う対象の箇所にてstart()を実行し、その時点のメモリ使用量を計測を開始。finish()を実行し、計測を停止する。
 * 計測完了後、計測開始、終了の間にて増加したメモリ使用量（バイト、キロバイト、メガバイト）を取得することができます。
 * 
 * @version 1.0
 * @author D.Kanno
 * 
 */
public class MemoryCounter {
	
	private long startFreeMem;
	
	private long startTotalMem;
	
	private long startMaxMem;
	
	private long startUsedMem;
	
	private boolean started = false;
	
	private long finFreeMem;
	
	private long finTotalMem;
	
	private long finMaxMem;
	
	private long finUsedMem;
	
	private boolean finished = false;
	
	private long resultFreeMem;
	
	private long resultTotalMem;
	
	private long resultMaxMem;
	
	private long resultUsedMem;
	
	/**
	 * 時間計測を開始します。<p>
	 * 
	 * @throws Exception すでに計測を開始している場合、例外を発生します。
	 */
	public void start() throws RuntimeException{
		if (this.started) throw new RuntimeException("Counter is already running.");
		this.startFreeMem  = Runtime.getRuntime().freeMemory();
		this.startTotalMem = Runtime.getRuntime().totalMemory();
		this.startMaxMem   = Runtime.getRuntime().maxMemory();
		this.startUsedMem  = this.startTotalMem - this.startFreeMem;
		this.started = true;
	}
	
	/**
	 * 時間計測を終了します。<p>
	 * 
	 * @throws Exception まだ計測を開始していない、またはすでに計測を終了している場合、例外を発生します。
	 */
	public void fin() throws RuntimeException{
		if (!this.started) throw new RuntimeException("Counter is not running.");
		if (this.finished) throw new RuntimeException("Counter is already stopped.");
		this.finFreeMem  = Runtime.getRuntime().freeMemory();
		this.finTotalMem = Runtime.getRuntime().totalMemory();
		this.finMaxMem   = Runtime.getRuntime().maxMemory();
		this.finUsedMem  = this.finTotalMem - this.finFreeMem;
		this.finished = true;
		this.resultFreeMem  = this.finFreeMem  - this.startFreeMem;
		this.resultTotalMem = this.finTotalMem - this.startTotalMem;
		this.resultMaxMem   = this.finMaxMem   - this.startMaxMem;
		this.resultUsedMem  = this.finUsedMem  - this.startUsedMem;
	}
	
	/**
	 * メモリ使用率をバイトとして取得します。<p>
	 * 
	 * @return メモリ使用率
	 */
	public long getUsedByte() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Counter is not stopped.");
		return this.resultUsedMem;
	}
	
	/**
	 * メモリ使用率をキロバイトとして取得します。<p>
	 * 
	 * @return メモリ使用率
	 */
	public long getUsedKiroByte() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Counter is not stopped.");
		return this.resultUsedMem / 1024;
	}
	
	/**
	 * メモリ使用率をメガバイトとして取得します。<p>
	 * 
	 * @return メモリ使用率
	 */
	public long getUsedMegaByte() throws RuntimeException{
		if (!this.started || !this.finished) throw new RuntimeException("Counter is not stopped.");
		return this.resultUsedMem / 1024 / 1024;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// sb.append("UseMegaByte [").append(this.getUsedMegaByte()).append(']').append(" Mbyte");
		sb.append("UseKiroByte [").append(this.getUsedKiroByte()).append(']').append(" Kbyte");
		// sb.append("UseByte     [").append(this.getUsedByte()).append(']').append(" byte");
		return sb.toString();
	}
}
