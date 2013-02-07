package util;

import java.util.Date;

public class TestingUtils {
	public static Date namedDate(final String name, final Date date) {
		return new Date(date.getTime()){
			@Override
			public String toString() {
				return name;
			}
		};
	}
}
