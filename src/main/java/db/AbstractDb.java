package db;

import java.util.Date;
import java.util.List;

import db.AbstractDb.Bounds;
import db.csv.DataBean;

public abstract class AbstractDb {
	public enum Bounds {
		LOWER, // <
		FLOOR, // <=
		STRICT, // ==
		CEILING, // >=
		HIGHER // >
	}

	/**
	 * 
	 * @return the entire database in a list of DataBeans. Not practical for extremely large databases.
	 */
	public abstract List<DataBean> getDatabase();

	/**
	 * Helper function for debugging to return a string representation of the data. Not practical for extremely large
	 * databases.
	 * 
	 * @return
	 */
	public abstract String printDatabase();

	/**
	 * Run a query on the datasource and return a list of DataBeans for the given date and bounds.
	 * 
	 * @param d is the date to be queried.
	 * @param bound - Higher, Lower, Ceiling (inclusive) or Floor (inclusive)
	 * @return list containing the DataBeans with the given date matched according to the Bounds. Will return an empty
	 *         list if no matches.
	 */
	public abstract List<DataBean> querySingle(Date d, Bounds bound);

	/**
	 * Run a query on the datasource and return a list of DataBeans. The bound has some defaults - by default is
	 * Bounds.CEILING which will return records less-than and equal to the given date d.
	 * 
	 * @param d
	 * @return list containing the DataBeans with the given date matched according to the Bounds.STRICT bound - if value
	 *         exists it will be returned else the empty list.
	 * 
	 * @see querySingle(java.util.Date, db.Db.Bounds)
	 */
	public List<DataBean> querySingle(Date d) {
		return querySingle(d, Bounds.CEILING);
	}

	/**
	 * Run a query on the datasource and return a list of DataBeans for between the given dates wrt the bounds.
	 * 
	 * @param lower - lower date to search from
	 * @param upper - upper date to search to
	 * @param lbounds - bounds on lower date
	 * @param ubounds - bounds on upper date
	 * @return list of DataBeans between and including the two dates wrt the lbound and ubound bounds. Or empty list if no matches.
	 */
	public abstract List<DataBean> queryRange(Date lower, Date upper, Bounds lbound, Bounds ubound);

	/**
	 * Run a query on the datasource and return a list of DataBeans for between the given dates wrt a default bounds
	 * which is by default Bounds.CEILING (Greater-than equal) for the lower date and Bounds.FLOOR (Lower-than equal)
	 * for the upper date.
	 * 
	 * @param lower - lower date to search from
	 * @param upper - upper date to search to
	 * @return list of DataBeans between and including the two dates.  The lbound defaults to CEILING and the ubound defaults to FLOOR. Or empty list if no matches.
	 */
	public List<DataBean> queryRange(Date lower, Date upper) {
		return queryRange(lower, upper, Bounds.CEILING, Bounds.FLOOR);
	}
}
