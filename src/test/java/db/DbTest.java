package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static util.TestingUtils.namedDate;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import util.DateUtil;
import db.csv.DataBean;
import db.impl.DbCsv;
import db.AbstractDb.Bounds;

public class DbTest {
	DbCsv db = null;
	@Rule public TestName testName = new TestName();
	@Before
	public void setUp() throws Exception {
		db = new DbCsv(DbTest.class.getResource("/db/data1.csv"));
		// System.out.println("DB:\n" + db.printDatabase());
		System.out.println("-> " + testName.getMethodName());
	}

	@Test
	public void testLoad() {
		assertEquals(db.getDatabase().size(), 29);
	}

	// I don't really need to do these types of tests as the CvsTest already
	// does it
	@Test
	public void testFileHasRow1() throws FileNotFoundException {
		DataBean bean = db.getDatabase().get(0);
		assertEquals("01/01/12", bean.getDate());
		assertEquals(0, bean.getFaceBook());
		assertEquals(0, bean.getTwitter());
		assertEquals(0, bean.getYouTube());
	}

	@Test
	public void testFileHasRow29() throws FileNotFoundException {
		DataBean bean = db.getDatabase().get(28);

		assertEquals("01/08/13", bean.getDate());
		assertEquals(40, bean.getFaceBook());
		assertEquals(20, bean.getTwitter());
		assertEquals(30, bean.getYouTube());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.MONTH, Calendar.AUGUST);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date expected = DateUtils.truncate(c.getTime(), Calendar.DAY_OF_MONTH);
		Date actual = bean.dateValueAtMidnight();
		assertEquals(namedDate("Expected date of " + expected, expected), namedDate("Actual date of " + actual, actual));
	}

	// Now test the querying (date querying only)
	@Test
	public void testSearchSingle() {
		Date d = DateUtil.buildDate(2012, Calendar.JANUARY, 1);
		List<DataBean> list = db.querySingle(d);
		assertNotNull(list);
		assertEquals(1, list.size());
	}

	@Test
	public void testSearchSingleDoesntExist() {
		Date d = DateUtil.buildDate(2011, Calendar.JANUARY, 1);
		List<DataBean> list = db.querySingle(d, AbstractDb.Bounds.STRICT);
		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void testSearchRangeBothExist() {
		Date d1 = DateUtil.buildDate(2012, Calendar.JANUARY, 1);
		Date d2 = DateUtil.buildDate(2012, Calendar.MARCH, 1);
		List<DataBean> list = db.queryRange(d1, d2);
		assertNotNull(list);
		assertEquals(12, list.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSearchRangeDatesWrongOrder() {
		Date d2 = DateUtil.buildDate(2012, Calendar.JANUARY, 1);
		Date d1 = DateUtil.buildDate(2012, Calendar.MARCH, 1);
		List<DataBean> list = db.queryRange(d1, d2);
		assertNotNull(list);
		assertEquals(12, list.size());
	}
	@Test
	public void testSearchRangeLowerExists() {
		Date d1 = DateUtil.buildDate(2012, Calendar.JANUARY, 1);
		Date d2 = DateUtil.buildDate(2012, Calendar.MARCH, 2);
		List<DataBean> list = db.queryRange(d1, d2);
		assertNotNull(list);
		assertEquals(12, list.size());
	}
	
	@Test
	public void testSearchRangeUpperExists() {
		Date d1 = DateUtil.buildDate(2012, Calendar.JANUARY, 11);
		Date d2 = DateUtil.buildDate(2012, Calendar.MARCH, 1);
		List<DataBean> list = db.queryRange(d1, d2);
		assertNotNull(list);
		assertEquals(2, list.size());
	}
	
	@Test
	public void testSearchSingleCeiling() {
		// find next one above given date (that doesn't exist in db)
		Date d = DateUtil.buildDate(2012, Calendar.JANUARY, 20);
		Date ex = DateUtil.buildDate(2012, Calendar.FEBRUARY, 1);
		List<DataBean> list = db.querySingle(d, Bounds.CEILING);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(ex,list.get(0).getDateAtMidnight());
	}
	
	@Test
	public void testSearchSingleFloor() {
		// find next one above given date (that doesn't exist in db)
		Date d = DateUtil.buildDate(2012, Calendar.JANUARY, 20);
		Date ex = DateUtil.buildDate(2012, Calendar.JANUARY, 10);
		List<DataBean> list = db.querySingle(d, Bounds.FLOOR);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(ex,list.get(0).getDateAtMidnight());
	}
}
