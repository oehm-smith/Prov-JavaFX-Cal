package app.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableColumn;

import app.Timeline;

public class WeekTimeline implements Timeline {

	public WeekTimeline(Date startDate) {
		System.out.format("WeekTimeline - %s\n", startDate);
	}

	public List<TableColumn<Integer, String>> getColumns() {
		List<TableColumn<Integer, String>> list  = new ArrayList<TableColumn<Integer, String>>();
		for (int i = 0; i<7;i++) {
			TableColumn<Integer, String> t = new TableColumn<>(Integer.toString(i));
			list.add(t);
		}
		return list;
	}
}
