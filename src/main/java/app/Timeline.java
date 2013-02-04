package app;

import java.util.List;

import javafx.scene.control.TableColumn;

public interface Timeline {

	/**
	 * 
	 * @return list of tableColumns to be displayed in the table.
	 */
	List<TableColumn<Integer, String>> getColumns();

}
