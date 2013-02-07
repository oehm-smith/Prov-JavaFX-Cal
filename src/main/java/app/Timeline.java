package app;

import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;

public interface Timeline {

	/************************************************************************
	 * First part: Timeline calendar display
	 */
	/**
	 * 
	 * @return list of tableColumns to be displayed in the table.
	 */
	List<TableColumn<Integer, String>> getColumns();

	/**
	 * When user clicks next() button update the model to return new Columns.
	 */
	void next();

	/**
	 * When user clicks prev() button update the model to return new Columns.
	 */
	void prev();

	/**
	 * 
	 * @return the label text to display, such as the month, week range etc...
	 */
	String getLabelText();

	/************************************************************************
	 * Second part: Chart display (also affected by the above)
	 */
	/**
	 * 
	 * @return a list of a list of chart data. There should be no more than getColumns().size() items. Each inner-list
	 *         is the set of data to be displayed and each outer-list is the different data to overlay on the chart.
	 */
	List<List<XYChart.Data<Number, Number>>> getChartData();

	/**
	 * As build the data for the chart keep track of the maximum value to help build the chart itself.
	 * @return the maximum value for the data to be displayed on the graph.
	 */
	int getChartDataMaxValue();
}
