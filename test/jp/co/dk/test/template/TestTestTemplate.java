package jp.co.dk.test.template;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestTestTemplate extends TestCaseTemplate {
	
	@Test
	public void getAllPatternList() {
		List<String> list1 = null;
		List<List<String>> resultList1 = super.getAllPatternList(list1);
		assertThat(resultList1.size(), is (0));
		
		List<String> list2 = new ArrayList<String>();
		list2.add("1");
		List<List<String>> resultList2 = super.getAllPatternList(list2);
		assertThat(resultList2.size() , is (1));
		assertThat(resultList2.get(0), is(list2));
		
		List<List<String>> allPatternList3 = new ArrayList<List<String>>();
		List<String> list3_1 = new ArrayList<String>();
		list3_1.add("1");
		list3_1.add("2");
		list3_1.add("3");
		allPatternList3.add(list3_1);
		
		List<String> list3_2 = new ArrayList<String>();
		list3_2.add("1");
		list3_2.add("3");
		list3_2.add("2");
		allPatternList3.add(list3_2);
		
		List<String> list3_3 = new ArrayList<String>();
		list3_3.add("2");
		list3_3.add("1");
		list3_3.add("3");
		allPatternList3.add(list3_3);
		
		List<String> list3_4 = new ArrayList<String>();
		list3_4.add("2");
		list3_4.add("1");
		list3_4.add("3");
		allPatternList3.add(list3_4);
		
		List<String> list3_5 = new ArrayList<String>();
		list3_5.add("3");
		list3_5.add("1");
		list3_5.add("2");
		allPatternList3.add(list3_5);
		
		List<String> list3_6 = new ArrayList<String>();
		list3_6.add("3");
		list3_6.add("2");
		list3_6.add("1");
		allPatternList3.add(list3_6);
		
		List<List<String>> resultList3 = super.getAllPatternList(list3_1);
		assertThat(resultList3.size(), is (6));
		for (List<String> list : allPatternList3) {
			if(!hasList(resultList3, list)) fail();
		}
	}
	
	@Test
	public void reversalList() {
		assertThat(reversalList(null), nullValue());
		
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("2");
		list2.add("1");
		
		List<String> reversalList1 = reversalList(list1);
		assertThat(asList(reversalList1, list2), is (true));
	}
	
	@Test
	public void hasList() {
		assertThat(hasList(null, null), is (false));
		assertThat(hasList(new ArrayList<List<String>>(), null), is (false));
		
		List<List<String>> lists = new ArrayList<List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("3");
		list2.add("4");
		
		lists.add(list1);
		lists.add(list2);
		lists.add(null);
		
		List<String> list3 = new ArrayList<String>();
		list3.add("1");
		list3.add("2");
		
		assertThat(hasList(lists, list3), is (true));
		assertThat(hasList(lists, null), is (true));
		
		List<String> list4 = new ArrayList<String>();
		list4.add("5");
		list4.add("6");
		
		assertThat(hasList(lists, list4), is (false));
	}
	
	@Test
	public void getEqualsListIndex() {
		assertThat(getEqualsListIndex(null, null).size(), is (0));
		assertThat(getEqualsListIndex(new ArrayList<List<String>>(), null).size(), is (0));
		
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("3");
		list2.add("4");
		
		List<String> list3 = new ArrayList<String>();
		list3.add("3");
		list3.add("4");
		
		List<List<String>> lists = new ArrayList<List<String>>();
		lists.add(list1);
		lists.add(list2);
		lists.add(list3);
		lists.add(null);
		
		List<String> searchList1 = new ArrayList<String>();
		searchList1.add("1");
		searchList1.add("2");
		
		List<String> searchList2 = new ArrayList<String>();
		searchList2.add("3");
		searchList2.add("4");
		
		List<Integer> result1 = getEqualsListIndex(lists, searchList1);
		List<Integer> result2 = getEqualsListIndex(lists, searchList2);
		List<Integer> result3 = getEqualsListIndex(lists, null);
		
		assertThat(result1.size(), is (1));
		assertThat(result1.get(0).intValue(), is (0));
		
		assertThat(result2.size(), is (2));
		assertThat(result2.get(0).intValue(), is (1));
		assertThat(result2.get(1).intValue(), is (2));
		
		assertThat(result3.size(), is (1));
		assertThat(result3.get(0).intValue(), is (3));
	}
	
	@Test
	public void asList() {
		assertThat(super.asList(null, null), is (true));
		assertThat(super.asList(new ArrayList<String>(), null), is (false));
		assertThat(super.asList(null, new ArrayList<String>()), is (false));
		
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("4");
		assertThat(super.asList(list1, list2), is (false));
		
		List<String> list3 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		
		List<String> list4 = new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		assertThat(super.asList(list3, list4), is (true));
		
	}
}
