package app.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import app.Timeline;
import db.AbstractDb;

public class GlobalTimeline implements Timeline {
	AbstractDb db;
	Date startDate;

	public GlobalTimeline(Date startDate, AbstractDb db) {
		this.startDate = startDate;
		this.db = db;
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

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void prev() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLabelText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<XYChart.Data<Number, Number>>> getChartData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChartDataMaxValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
