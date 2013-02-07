package util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	/**
	 * Quick method to build dates.
	 * @param year
	 * @param month - 0 to 11 - easiest to use Calendar.JANUARY .. Calendar.DECEMBER
	 * @param day
	 * @return
	 */
	public static Date buildDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		Calendar ct = DateUtils.truncate(c, Calendar.DATE);
		System.out.format("-> buildDate(%d,%d, %d) -> %s\n",year,month,day,ct.getTime());
		return ct.getTime();
	}

}
