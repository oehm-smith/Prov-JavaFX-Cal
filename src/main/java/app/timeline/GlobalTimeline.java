package app.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableColumn;
import app.Timeline;

public class GlobalTimeline implements Timeline {

	public GlobalTimeline(Date startDate) {
		System.out.format("GlobalTimeline - %s\n", startDate);
	}

	public List<TableColumn<Integer, String>> getColumns() {
		List<TableColumn<Integer, String>> list = new ArrayList<TableColumn<Integer, String>>();
		for (int i = 0; i < 100; i++) {
			TableColumn<Integer, String> t = new TableColumn<>(Integer.toString(i));
			list.add(t);
		}
		return list;
	}
}
