package db;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.csv.Csv;
import db.csv.DataBean;

public class Db {
	/**
	 * Sorted
	 */
	private List<DataBean> database = null;

	public enum Bounds {
		LOWER, // <
		FLOOR, // <=
		STRICT, // ==
		CEILING, // >=
		HIGHER // >
	}

	public Db(URL inDatabase) {
		// This is a fixed mock. Ideally the DB implementation (ie. CSV)
		// wouldn't be hard-coded and of course, nor would the data object (ie.
		// DataBean)
		Csv csv = null;
		try {
			csv = new Csv(inDatabase);
		} catch (FileNotFoundException e) {
			Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, e);
		}
		database = csv.getDataList();
		printDatabase();
		Collections.sort(database);
		printDatabase();
	}

	public List<DataBean> getDatabase() {
		return database;
	}

	public String printDatabase() {
		StringBuffer sb = new StringBuffer();
		for (DataBean b : database) {
			sb.append(b).append("\n");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param d
	 * @param bound
	 * @return list containing the DataBeans with the given date matched according to the Bounds. Will return an empty
	 *         list if no matches.
	 */
	public List<DataBean> querySingle(Date d, Bounds bound) {
		List<DataBean> list = new ArrayList<DataBean>();
		int searchIndex = Collections.binarySearch(database, new DataBean(d));
		int foundIndex = selectIndexWRTBounds(searchIndex, bound, database);
		
		if (foundIndex >= 0) {
			list.add(database.get(foundIndex));
		}
		return list;
	}

	/**
	 * 
	 * @param d
	 * @return list containing the DataBeans with the given date matched according to the Bounds.STRICT bound - if value
	 *         exists it will be returned else the empty list.
	 */
	public List<DataBean> querySingle(Date d) {
		return querySingle(d, Bounds.CEILING);
	}

	/**
	 * 
	 * @param lower - lower date to search from
	 * @param upper - upper date to search to
	 * @param lbounds - bounds on lower date
	 * @param ubounds - bounds on upper date
	 * @return list of DataBeans between and including the two dates wrt the lbound and ubound bounds. Or empty list if no matches.
	 */
	public List<DataBean> queryRange(Date lower, Date upper, Bounds lbound, Bounds ubound) {
		if (lower.getTime() > upper.getTime()) {
			throw new IllegalArgumentException("First date must be earlier in time than 2nd");
		}
		List<DataBean> list = new ArrayList<DataBean>();
		if (database.size() <= 0) {
			return list; // its empty
		}
		int searchIndexStart = Collections.binarySearch(database, new DataBean(lower));
		int searchIndexEnd = Collections.binarySearch(database, new DataBean(upper));
		int foundIndexStart = selectIndexWRTBounds(searchIndexStart, lbound, database);
		int foundIndexEnd = selectIndexWRTBounds(searchIndexEnd, ubound, database);

		if (outOfRange(foundIndexStart) || outOfRange(foundIndexEnd)) {
			return list;	// its empty
		}
		
		System.out.format("queryRange(Date d1:%s, Date d2:%s) - i1:%d, i2:%s (i1Orig:%d, i2Orig:%d)\n", lower, upper,
				foundIndexStart, foundIndexEnd, searchIndexStart, searchIndexEnd);
		for (int j = foundIndexStart; j <= foundIndexEnd; j++) {
			list.add(database.get(j));
		}
		return list;
	}

	/**
	 * 
	 * @param lower - lower date to search from
	 * @param upper - upper date to search to
	 * @return list of DataBeans between and including the two dates.  The lbound defaults to CEILING and the ubound defaults to FLOOR. Or empty list if no matches.
	 */
	public List<DataBean> queryRange(Date lower, Date upper) {
		return queryRange(lower,upper,Bounds.CEILING, Bounds.FLOOR);
	}
	
	/**
	 * 
	 * @param index
	 * @return if index is outside range of the database list.
	 */
	private boolean outOfRange(int index) {
		return (index < 0 || index >= database.size());
	}

	/**
	 * 
	 * @param index is the index the search calculated. It can either be an exact element (index >= 0) - this will just
	 *            return this value if so. Or it can be a place to insert the new element to keep it sorted (index < 0 -
	 *            insertion point is -(index + 1)). We want to return an actual existing element either HIGHER, LOWER,
	 *            etc.. However if the bounds is STRICT then we don't want to return any index - we'll return index,
	 *            which is negative to indicate this. Similarly if there is no HIGHER or LOWER then this will also
	 *            return index, which again is negative, to indicate this.
	 * 
	 * @param bounds to say what to do when the index is negative.
	 * @param list is the list the index applies to
	 * @return int >= 0 for the index of a list element that was found according to what index that was a params and the
	 *         bounds. Or an int < 0 indicating an element couldn't be found. This negative index is the same one that
	 *         was passed in and can be used if need be, to insert an element that will keep the list sorted.
	 */
	protected <T> int selectIndexWRTBounds(int index, Bounds bounds, List<T> list) {
		int origIndex = index;

		if (index >= 0) {
			return index; // exact match - bounds not required
		}
		if (bounds == Bounds.STRICT) {
			return index; // no match and STRICT says not to 'look' for one higher or lower
		}
		index = -(index + 1);
		if (bounds == Bounds.LOWER || bounds == Bounds.FLOOR) {
			if (index == 0) {
				return origIndex; // can't go any lower so there was nothing to match on
			}
			return index - 1;
		}
		if (bounds == Bounds.HIGHER || bounds == Bounds.CEILING) {
			if (index >= list.size()) {
				return origIndex; // can't go any higher so there was nothing to match on
			}
			return index;
		}
		return 0;
	}
}
