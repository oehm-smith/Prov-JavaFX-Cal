package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ModelImpl;
import util.TestingUtils;
import app.Timeline;
import app.TimelineFactory;

/**
 * Build the view, register to handle events and communicate with the Model.
 * This needs to be the Controller and is created by the JavaFX runtime.
 * 
 * @author bsmith
 */
public class View implements Initializable {
	private ModelImpl model;
	private TimelineFactory timelineFactory;
	@FXML
	private MenuButton dropdownTimeScaleId;
	@FXML
	private TableView<Integer> tableTimeline;

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
		// Add handler on all MenuItems in timescale dropdown
		for (MenuItem m : dropdownTimeScaleId.getItems()) {
			m.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					onTimelineMenuChange(event);
				}
			});
		}
	}

	public void onTimelineMenuChange(ActionEvent event) {
		MenuItem mi = (MenuItem) event.getSource();
		Timeline timeline = timelineFactory.fromType(mi.getText(), TestingUtils.buildDate(2012, 1, 1));
		/// now build model from timeline
		List<TableColumn<Integer, String>> columns = timeline.getColumns();
		double tableWidth = tableTimeline.getWidth();
		double colWidth = (tableWidth / (columns.size()));
		System.out.println("Tablewidth:" + tableWidth + ", col widths:" + colWidth);
		tableTimeline.getColumns().remove(0, tableTimeline.getColumns().size());
		for (TableColumn<Integer, String> a: columns) {
			a.setMinWidth(colWidth);
			a.setMaxWidth(colWidth);
		}
		tableTimeline.getColumns().addAll(columns);
	}

}
