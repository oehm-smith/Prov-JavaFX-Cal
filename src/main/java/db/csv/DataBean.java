package db.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;

import app.Main;

public class DataBean implements Comparable<DataBean> {
	private String dateString;
	private Date date;
	private Date dateAtMidnight;
	private int faceBook;
	private int twitter;
	private int youTube;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d/M/y");
	
	public DataBean() {
	}

	// Constructor mainly for testing
	public DataBean(String date) {
		// I have to do this in the constructor for it to be set on the object - calling setDate(x) won't work until this constructor reaches the end.
		this.dateString = date;
		setDate(dateString);
	}
	public DataBean(String date, int facebook, int twitter, int youTube) {
		this(date);
		setFaceBook(facebook);
		setTwitter(twitter);
		setYouTube(youTube);
	}
	public DataBean(Date d) {
		this(DATE_FORMAT.format(d));
	}

	public String getDate() {
		return dateString;
	}
	public Date getDateValue() {
		return date;
	}
	public Date getDateAtMidnight() {
		return dateAtMidnight;
	}
	public void setDate(String dateString) {
//		System.out.println("DataBean - dateString:"+dateString);
		this.dateString = dateString;
		this.date = dateValue();
		this.dateAtMidnight = dateValueAtMidnight();
	}
	public int getFaceBook() {
		return faceBook;
	}
	public void setFaceBook(int faceBook) {
//		System.out.format("setFacebook:%d\n",faceBook);
		this.faceBook = faceBook;
	}
	public int getTwitter() {
		return twitter;
	}
	public void setTwitter(int twitter) {
		this.twitter = twitter;
	}
	public int getYouTube() {
		return youTube;
	}
	public void setYouTube(int youTube) {
		this.youTube = youTube;
	}
	public Date dateValue() {
		Date newDate = null;
		try {
			newDate = DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
            Logger.getLogger(DataBean.class.getName()).log(Level.SEVERE, null, e);
		}
		return newDate;
	}
	public Date dateValueAtMidnight() {
		return DateUtils.truncate(dateValue(), Calendar.DAY_OF_MONTH);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof DataBean)) {
			return false;
		}
		DataBean dbobj = (DataBean) obj;
		// to use just getDate() (ie. has time) requires that this class deals with time - the default will be the time the Date create happens.
		return (dbobj.getDateAtMidnight().equals(getDateAtMidnight()));
	}
	
	public int hashCode() {
		// to use just getDate() (ie. has time) requires that this class deals with time - the default will be the time the Date create happens.
		return getDateAtMidnight().hashCode();
	}
	
	@Override
	public int compareTo(DataBean o) {
		// I had to do the compare this way as I couldn't get the longs to reliably cast to int as it seems to truncate the right and not the left
		Long l1 = this.getDateAtMidnight().getTime();
		Long l2 = o.getDateAtMidnight().getTime();
		
		if (l1 < l2) {
			return -1;
		} else if (l1 > l2) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public String toString() {
		return String.format("Databean - %s, facebook: %d, twitter: %d, youtube: %d",getDateAtMidnight(),getFaceBook(),getTwitter(), getYouTube());
	}
}
