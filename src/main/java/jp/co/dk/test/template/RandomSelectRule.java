package jp.co.dk.test.template;

/**
 * RandomSelectRuleは、一覧から条件にあった要素をランダムに取得する際に使用する条件を定義するインターフェースです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public interface RandomSelectRule {
	
	/**
	 * 一覧から条件にあった要素をランダムに取得する際の条件を定義します。
	 * 
	 * @param element 要素
	 * @return true=一致、false=不一致
	 */
	public <E> boolean match(E element);
}
