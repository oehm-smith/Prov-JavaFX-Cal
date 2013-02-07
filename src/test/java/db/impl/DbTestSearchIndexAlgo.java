package db.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import db.AbstractDb;
import db.DbTest;
import db.impl.Db;

public class DbTestSearchIndexAlgo {
	List<Integer> list = null;
	Db db = null;

	@Rule
	public TestName testName = new TestName();

	@Before
	public void setUp() throws Exception {
		System.out.println("-> " + testName.getMethodName());
		db = new Db(DbTest.class.getResource("/db/data1.csv")); // don't actually need the data

		list = new ArrayList<>();
		// 1,5,9,13,17,21
		for (int i = 1; i <= 21; i+=4) {
			list.add(i);
		}
	}

	@Test
	public void testSelectIndexWRTBounds01() {
		Integer elToFind = 1;
		int expectedElIndex = 0;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.STRICT, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds011() {
		Integer elToFind = 1;
		int expectedElIndex = 0;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.FLOOR, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds012() {
		Integer elToFind = 1;
		int expectedElIndex = 0;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.CEILING, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds02() {
		Integer elToFind = 5;
		int expectedElIndex = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.STRICT, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds021() {
		Integer elToFind = 5;
		int expectedElIndex = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.FLOOR, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds022() {
		Integer elToFind = 5;
		int expectedElIndex = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.CEILING, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds03() {
		Integer elToFind = 9;
		int expectedElIndex = 2;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.STRICT, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds031() {
		Integer elToFind = 9;
		int expectedElIndex = 2;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.FLOOR, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds032() {
		Integer elToFind = 9;
		int expectedElIndex = 2;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.CEILING, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds04() {
		Integer elToFind = 2;
		int expectedElIndex = -2;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.STRICT, list);
		assertEquals(expectedElIndex, actualElIndex);
	}
	
	@Test
	public void testSelectIndexWRTBounds20() {
		Integer elToFind = 2;
		int expectedElIndex = 0;
		Integer expectedElement = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.FLOOR, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds21() {
		Integer elToFind = 2;
		int expectedElIndex = 1;
		Integer expectedElement = 5;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.HIGHER, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds22() {
		Integer elToFind = 20;
		int expectedElIndex = 4;
		Integer expectedElement = 17;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.FLOOR, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds23() {
		Integer elToFind = 20;
		int expectedElIndex = 5;
		Integer expectedElement = 21;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.HIGHER, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds24() {
		Integer elToFind = 22;
		int expectedElIndex = 5;
		Integer expectedElement = 21;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.LOWER, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds25() {
		Integer elToFind = 22;
		int expectedElIndex = -7;
//		Integer expectedElement = 21;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.HIGHER, list);
		assertEquals(expectedElIndex, actualElIndex);
		//assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds26() {
		Integer elToFind = 111;
		int expectedElIndex = -7;
//		Integer expectedElement = 21;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.HIGHER, list);
		assertEquals(expectedElIndex, actualElIndex);
		//assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds27() {
		Integer elToFind = 0;
		int expectedElIndex = 0;
		Integer expectedElement = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.HIGHER, list);
		assertEquals(expectedElIndex, actualElIndex);
		assertEquals(expectedElement, list.get(actualElIndex));
	}
	
	@Test
	public void testSelectIndexWRTBounds28() {
		Integer elToFind = 0;
		int expectedElIndex = -1;
//		Integer expectedElement = 1;
		int searchIndex = Collections.binarySearch(list, elToFind);
		int actualElIndex = db.selectIndexWRTBounds(searchIndex, AbstractDb.Bounds.LOWER, list);
		assertEquals(expectedElIndex, actualElIndex);
//		assertEquals(expectedElement, list.get(actualElIndex));
	}
}
