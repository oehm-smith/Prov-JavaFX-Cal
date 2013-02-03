package db.csv;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import static util.TestingUtils.namedDate;

public class CsvTest {
	private Csv csv;

	@Before
	public void setUp() throws Exception {
		csv = new Csv(CsvTest.class.getResource("/db/data1.csv"));
	}

	
	@Test(expected = FileNotFoundException.class)
	public void testLoadFailure() throws FileNotFoundException {
		csv = new Csv(CsvTest.class.getResource("/db/dataXDoesntExist.csv"));
	}

	@Test
	public void testFileHasAllExpectedRows() throws FileNotFoundException {
		assertEquals(29, csv.getDataList().size());
	}

	@Test
	public void testFileHasRow1() throws FileNotFoundException {
		DataBean bean = csv.getDataList().get(0);
		assertRow1(bean);
	}
	private void assertRow1(DataBean db) {
		assertEquals("01/01/12", db.getDate());
		assertEquals(0, db.getFaceBook());
		assertEquals(0, db.getTwitter());
		assertEquals(0, db.getYouTube());
	}

	@Test
	public void testFileHasRow29() throws FileNotFoundException {
		DataBean bean = csv.getDataList().get(28);

		assertRow29(bean);
	}
	
	private void assertRow29(DataBean db) {
		assertEquals("01/08/13", db.getDate());
		assertEquals(40, db.getFaceBook());
		assertEquals(20, db.getTwitter());
		assertEquals(30, db.getYouTube());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.MONTH, Calendar.AUGUST);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date expected = DateUtils.truncate(c.getTime(), Calendar.DAY_OF_MONTH);
		Date actual = db.dateValueAtMidnight();
		assertEquals(namedDate("Expected date of "+expected, expected), 
				namedDate("Actual date of "+actual,actual));
	}
	
	@Test
	public void testSorted() {
		// the file is already in order so sorting should have no affect
		Collections.sort(csv.getDataList());
		DataBean bean = csv.getDataList().get(0);
		assertRow1(csv.getDataList().get(0));
		assertRow29(csv.getDataList().get(28));

//		int i = 1;
//		for (DataBean db : csv.getDataList()) {
//			System.out.format("%d : %s\n",i++,db);
//		}
	}
}
