package app.timeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import app.Timeline;
import db.AbstractDb.Bounds;
import db.csv.DataBean;
import db.impl.Db;

public class MonthTimeline implements Timeline {
	private Db db;
	private Calendar startDateCal = null;
	private int maxValue = 0;
	
	final static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");// dd/M/yyyy");

	public MonthTimeline(Date startDate, Db db) {
		this.db=db;
		startDateCal = Calendar.getInstance();
		startDateCal.setTime(startDate);
		System.out.format("MonthTimeline - %s\n", startDate);
	}

	public List<TableColumn<Integer, String>> getColumns() {
		List<TableColumn<Integer, String>> list = new ArrayList<TableColumn<Integer, String>>();
//		String calMonth = Integer.toString(cal.get(Calendar.MONTH)+1);
		for (int i = 1; i <= startDateCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			TableColumn<Integer, String> t = new TableColumn<>(Integer.toString(i));//calMonth+":"+
			list.add(t);
		}
		return list;
	}

	@Override
	public void next() {
		startDateCal.add(Calendar.MONTH, 1);
		System.out.format("MonthTimeline next - %s\n", startDateCal.getTime());
	}

	@Override
	public void prev() {
		startDateCal.add(Calendar.MONTH, -1);
		System.out.format("MonthTimeline prev - %s\n", startDateCal.getTime());
	}

	@Override
	public String getLabelText() {
		return dateFormat.format(startDateCal.getTime());
	}

	@Override
	public List<List<XYChart.Data<Number, Number>>> getChartData() {
		Calendar endDateCal = Calendar.getInstance();
		endDateCal.setTime(startDateCal.getTime());
		endDateCal.add(Calendar.MONTH, 1);
		
		List<XYChart.Data<Number, Number>> facebooklist = new ArrayList<>();
		List<XYChart.Data<Number, Number>> twitterlist = new ArrayList<>();
		List<XYChart.Data<Number, Number>> youtubelist = new ArrayList<>();
		List<List<XYChart.Data<Number, Number>>> chartData = new ArrayList<>();
		chartData.add(facebooklist);
		chartData.add(twitterlist);
		chartData.add(youtubelist);
		
		List<DataBean> list = db.queryRange(startDateCal.getTime(), endDateCal.getTime(), Bounds.CEILING, Bounds.LOWER);
		System.out.println("-> getChartData - print from query for datarange "+startDateCal.getTime()+" -> "+endDateCal.getTime());
		for (DataBean b : list) {
			System.out.println("  "+b);
			Calendar cal = Calendar.getInstance();
			cal.setTime(b.getDateAtMidnight());
			XYChart.Data<Number, Number> facebook = new XYChart.Data<Number, Number>(cal.get(Calendar.DAY_OF_MONTH), b.getFaceBook());
			XYChart.Data<Number, Number> twitter = new XYChart.Data<Number, Number>(cal.get(Calendar.DAY_OF_MONTH), b.getTwitter());
			XYChart.Data<Number, Number> youtube = new XYChart.Data<Number, Number>(cal.get(Calendar.DAY_OF_MONTH), b.getYouTube());
			maxValue = b.getFaceBook() > maxValue ?  b.getFaceBook() : maxValue;
			maxValue = b.getTwitter() > maxValue ?  b.getTwitter() : maxValue;
			maxValue = b.getYouTube() > maxValue ?  b.getYouTube() : maxValue;
			
			facebooklist.add(facebook);
			twitterlist.add(twitter);
			youtubelist.add(youtube);
		}
		return chartData;
	}

	@Override
	public int getChartDataMaxValue() {
		return maxValue;
	}
}
