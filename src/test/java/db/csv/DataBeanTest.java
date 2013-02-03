package db.csv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DataBeanTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCompare() {
		DataBean b = new DataBean("8/10/13", 1,2,3);
		DataBean c = new DataBean("9/10/13",10,11,12);
		DataBean a = new DataBean("7/10/13",20,21,22);
		List<DataBean> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		list.add(c);
		Collections.sort(list);
		assertEquals(a,list.get(0));
		assertEquals(b,list.get(1));
		assertEquals(c,list.get(2));
	}

}
