package fx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;

public class FxController implements Initializable {
	@FXML
	private MenuButton dropdownTimeScaleId;
	@FXML
	private TableView tableTimeline;
	@FXML
	private HBox HBoxForTable;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Add handler on all MenuItems in timescale dropdown
		for (MenuItem m : dropdownTimeScaleId.getItems()) {
			m.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					onChange(event);
				}
			});
		}
	}

	@FXML
	public void onChange(ActionEvent event) {
		int  makemorehappentostopcrash = 7;
		MenuItem mi = (MenuItem) event.getSource();
		System.out.println("CHange me! "+makemorehappentostopcrash+", event source:"+mi.getText());
		int NUM_COLS = 31;
		if (mi.getText().equalsIgnoreCase("global"))
			NUM_COLS = 100;
		else if (mi.getText().equalsIgnoreCase("month"))
			NUM_COLS = 31;
		else if (mi.getText().equalsIgnoreCase("week"))
			NUM_COLS = 7;
		else if (mi.getText().equalsIgnoreCase("day"))
			NUM_COLS = 1;

		// TableColumn<Integer, String> b = new TableColumn<>("b");
		double tableWidth = tableTimeline.getWidth();
		double colWidth = (tableWidth / (NUM_COLS));
		System.out.println("Tablewidth:" + tableWidth + ", col widths:" + colWidth);
		tableTimeline.getColumns().remove(0, tableTimeline.getColumns().size());
		List<TableColumn<Integer, String>> list = new ArrayList<>();
		for (int i = 1; i <= NUM_COLS; i++) {
			TableColumn<Integer, String> a = new TableColumn<>(Integer.toString(i));
			a.setMinWidth(colWidth);
			a.setMaxWidth(colWidth);
			list.add(a);
		}
		tableTimeline.getColumns().addAll(list);
	}

	@FXML
	public void onHandleTimeScaleEvent(Event event) {
		System.out.println("You clicked me!");
	}

	@FXML
	public void dragDetected(DragEvent event) {
		System.out.println("You clicked me!");
	}

	@FXML
	public void buttonAction(ActionEvent event) {
		
	}

	public void onActionTimeScaleEvent(ActionEvent event) {
		System.out.println("timscale item event!");
	}
}
