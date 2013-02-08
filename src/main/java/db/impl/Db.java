package db.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.AbstractDb;
import db.csv.Csv;
import db.csv.DataBean;

/**
 * This provides a java.util.List implementation generally and a CSV implementation specifically.
 * 
 * @author bsmith
 * 
 */
public class Db extends AbstractDb {
	/**
	 * Sorted
	 */
	private List<DataBean> database = null;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.impl.Db#getDatabase()
	 */
	@Override
	public List<DataBean> getDatabase() {
		return database;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.impl.Db#printDatabase()
	 */
	@Override
	public String printDatabase() {
		StringBuffer sb = new StringBuffer();
		for (DataBean b : database) {
			sb.append(b).append("\n");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.impl.Db#querySingle(java.util.Date, db.Db.Bounds)
	 */
	@Override
	public List<DataBean> querySingle(Date d, Bounds bound) {
		List<DataBean> list = new ArrayList<DataBean>();
		int searchIndex = Collections.binarySearch(database, new DataBean(d));
		int foundIndex = selectIndexWRTBounds(searchIndex, bound, database);

		if (foundIndex >= 0) {
			list.add(database.get(foundIndex));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.impl.Db#queryRange(java.util.Date, java.util.Date, db.Db.Bounds, db.Db.Bounds)
	 */
	@Override
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
			return list; // its empty
		}

		System.out.format("queryRange(Date d1:%s, Date d2:%s) - i1:%d, i2:%s (i1Orig:%d, i2Orig:%d)\n", lower, upper,
				foundIndexStart, foundIndexEnd, searchIndexStart, searchIndexEnd);
		for (int j = foundIndexStart; j <= foundIndexEnd; j++) {
			list.add(database.get(j));
		}
		return list;
	}

	/**
	 * Helper function for implemenations.
	 * 
	 * @param index
	 * @return if index is outside range of the database list.
	 */
	private boolean outOfRange(int index) {
		return (index < 0 || index >= database.size());
	}

	/**
	 * 
	 * @param index is the index the search calculated. Its as per Collections.BinarySearch(). If (index >= 0) then this
	 *            a the search found the requested object. If (index < 0) then, as per Collections.BinarySearch() this
	 *            index is an indication of where the object can be inserted to keep the Collection sorted. The actual
	 *            index where it needs to be inserted is -(index + 1).
	 * @param bounds for what to return - LOWER -> return object less than this, FLOOR -> return the object equal to or
	 *            lower than this, CEILING -> return the object equal to or greater than this, HIGHER -> return the
	 *            object greater than this. STRICT -> exact match required. 'this' being what's at the index (taking
	 *            into account if the index is >= 0 or < 0).
	 * @param list is the list the index applies to
	 * @return index for an element that was matched according to the Bounds. The ways to return a negative index are
	 *         this. 1) Bounds.STRICT when no match is made, 2) Bounds.LOWER/FLOOR when no match is made and nothing is
	 *         lower than the value that was searched for, 3) Bounds.HIGHER/CEILING when no match is made and nothing is
	 *         higher than the value that was searched for. Otherwise a positive index is returned for a selected index.
	 * 
	 *         This method is protected so that it can be unit tested.
	 */
	protected <T> int selectIndexWRTBounds(int index, Bounds bounds, List<T> list) {
		int origIndex = index;

		if (index >= 0) {
			switch (bounds) {
			case LOWER:
				if (index == 0) {
					return -1; // indicating no match but insert at 0 (-(-1+1))
				} else {
					return index-1;
				}
			case HIGHER:
				if (index == list.size()) {
					return -(list.size() + 1); // indicating no match but insert at list.size()
				} else {
					return index+1;
				}
			default:
				// exact match
				return index;
			}
		}
		// index is negative
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
