package app;

import java.util.Date;

import app.timeline.DayTimeline;
import app.timeline.GlobalTimeline;
import app.timeline.MonthTimeline;
import app.timeline.WeekTimeline;

public class TimelineFactory {
	public Timeline fromType(String type, Date startDate) {
		if ("global".equalsIgnoreCase(type)) {
			return new GlobalTimeline(startDate);
		} else if ("month".equalsIgnoreCase(type)) {
			return new MonthTimeline(startDate);
		} else if ("week".equalsIgnoreCase(type)) {
			return new WeekTimeline(startDate);
		} else if ("day".equalsIgnoreCase(type)) {
			return new DayTimeline(startDate);
		}
		return null;
	}
}
