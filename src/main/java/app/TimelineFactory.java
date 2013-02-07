package app;

import java.util.Date;

import db.impl.DbCsv;

import app.timeline.DayTimeline;
import app.timeline.GlobalTimeline;
import app.timeline.MonthTimeline;
import app.timeline.WeekTimeline;

public class TimelineFactory {
	DbCsv db;

	public TimelineFactory(DbCsv db) {
		this.db = db;
	}

	public Timeline fromType(String type, Date startDate) {
		if ("global".equalsIgnoreCase(type)) {
			return new GlobalTimeline(startDate, db);
		} else if ("month".equalsIgnoreCase(type)) {
			return new MonthTimeline(startDate, db);
		} else if ("week".equalsIgnoreCase(type)) {
			return new WeekTimeline(startDate, db);
		} else if ("day".equalsIgnoreCase(type)) {
			return new DayTimeline(startDate, db);
		}
		return null;
	}
}
