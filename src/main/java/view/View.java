package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.ModelImpl;
import util.DateUtil;
import app.Timeline;
import app.TimelineFactory;

/**
 * Build the view, register to handle events and communicate with the Model. This needs to be the Controller and is
 * created by the JavaFX runtime.
 * 
 * @author bsmith
 */
public class View implements Initializable, ViewContract {
	private ModelImpl model;
	private TimelineFactory timelineFactory;
	private Timeline timeline = null;

	@FXML
	private MenuButton dropdownTimeScaleId;
	@FXML
	private TableView<Integer> tableTimeline;
	@FXML
	private Button buttonPrevious;
	@FXML
	private Button buttonNext;
	@FXML
	private Label labelTimeLine;
	@FXML
	private Pane areaChartBox;

	public View() {
		System.out.println("View()");
	}

	public void setModel(ModelImpl model) {
		System.out.println("setmodel()");
		this.model = model;
	}

	public void setTimeline(TimelineFactory timelineFactory) {
		System.out.println("setTimeline()");
		this.timelineFactory = timelineFactory;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addHandlersForTimeScaleDropdown();
		addHandlersForTimeScaleButtons();
	}

	public void go() {
		// TODO - get this from preferences
		newTimeLine("month");
	}

	private void addHandlersForTimeScaleDropdown() {
		for (MenuItem m : dropdownTimeScaleId.getItems()) {
			m.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					onTimelineMenuChange(event);
				}
			});
		}
	}

	private void addHandlersForTimeScaleButtons() {
		buttonPrevious.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				onButtonChange("PREVIOUS", event);
			}
		});
		buttonNext.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				onButtonChange("NEXT", event);
			}
		});
	}

	private void onTimelineMenuChange(ActionEvent event) {
		MenuItem mi = (MenuItem) event.getSource();
		newTimeLine(mi.getText());
	}

	private void newTimeLine(String timelineType) {
		timeline = timelineFactory.fromType(timelineType, DateUtil.buildDate(2012, 1, 1));
		drawTimeline();
	}

	private void drawTimeline() {
		drawKeyDatesTimeline();
		drawChart();
	}

	private void drawKeyDatesTimeline() {
		// / now build model from timeline
		List<TableColumn<Integer, String>> columns = timeline.getColumns();
		double tableWidth = tableTimeline.getWidth();
		double colWidth = ((tableWidth) / (columns.size()));
		System.out.format("Tablewidth:%f, cols:%d, col widths:%f\n", tableWidth, columns.size(), colWidth);
		tableTimeline.getColumns().remove(0, tableTimeline.getColumns().size());
		for (TableColumn<Integer, String> a : columns) {
			a.setMinWidth(colWidth);
			a.setMaxWidth(colWidth);
		}
		labelTimeLine.setText(timeline.getLabelText());
		tableTimeline.getColumns().addAll(columns);
	}

	private void drawChart() {
		List<List<XYChart.Data<Number, Number>>> data = timeline.getChartData();
		if (data == null)
			return;

		XYChart.Series<Number, Number> facebookSeries = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> twitterSeries = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> youtubeSeries = new XYChart.Series<Number, Number>();
		facebookSeries.getData().addAll(data.get(0));
		facebookSeries.setName("Facebook");
		System.out.println("Print facebook data:");
		for (XYChart.Data<Number,Number> d :data.get(0)  ){
			System.out.format("  %d, %d\n",d.getXValue(),d.getYValue());
		}
		twitterSeries.getData().addAll(data.get(1));
		twitterSeries.setName("Twitter");
		youtubeSeries.getData().addAll(data.get(2));
		youtubeSeries.setName("YouTube");
		final NumberAxis xAxis = new NumberAxis(1, timeline.getColumns().size(), 1);
		final NumberAxis yAxis = new NumberAxis(0, timeline.getChartDataMaxValue(), 10);
		AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);
		areaChart.setId("achart");
		areaChart.setMinSize(AreaChart.USE_COMPUTED_SIZE,AreaChart.USE_COMPUTED_SIZE);
		areaChart.setPrefSize(AreaChart.USE_COMPUTED_SIZE,AreaChart.USE_COMPUTED_SIZE);
		areaChart.setMaxSize(AreaChart.USE_COMPUTED_SIZE,AreaChart.USE_COMPUTED_SIZE);

		System.out.format("Area chart:%s, xaxis:%s\n", areaChart, null);
		// areaChart.getXAxis().setAutoRanging(false);
		// areaChart.getYAxis().setAutoRanging(false);
		areaChart.getData().addAll(facebookSeries, twitterSeries, youtubeSeries);
		// areaChart.layout();
		areaChartBox.getChildren().clear();
		areaChartBox.getChildren().add(areaChart);
//		areaChartBox.getChildren().;
	}

	private void onButtonChange(String button, ActionEvent event) {
		if ("previous".equalsIgnoreCase(button)) {
			if (timeline != null) {
				timeline.prev();
			}
		} else if ("next".equalsIgnoreCase(button)) {
			if (timeline != null) {
				timeline.next();
			}
		}
		if (timeline != null) {
			drawTimeline();
		}
	}

	/*********************/
	private void drawChartY() {
		final NumberAxis xAxis = new NumberAxis(1, 31, 1);
		final NumberAxis yAxis = new NumberAxis();
		final AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);
		ac.setTitle("Temperature Monitoring (in Degrees C)");

		XYChart.Series<Number, Number> seriesApril = new XYChart.Series<>();
		seriesApril.setName("April");
		seriesApril.getData().add(new XYChart.Data<Number, Number>(1, 4));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(3, 10));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(6, 15));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(9, 8));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(12, 5));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(15, 18));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(18, 15));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(21, 13));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(24, 19));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(27, 21));
		seriesApril.getData().add(new XYChart.Data<Number, Number>(30, 21));

		XYChart.Series<Number, Number> seriesMay = new XYChart.Series<>();
		seriesMay.setName("May");
		seriesMay.getData().add(new XYChart.Data<Number, Number>(1, 20));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(3, 15));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(6, 13));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(9, 12));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(12, 14));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(15, 18));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(18, 25));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(21, 25));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(24, 23));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(27, 26));
		seriesMay.getData().add(new XYChart.Data<Number, Number>(31, 26));

		ac.getData().addAll(seriesApril, seriesMay);
		areaChartBox.getChildren().remove(0, areaChartBox.getChildren().size());
		areaChartBox.getChildren().add(ac);
	}

}
