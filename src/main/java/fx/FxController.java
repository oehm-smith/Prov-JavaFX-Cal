package fx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;

public class FxController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void onChange(ActionEvent event) {
        System.out.println("CHange me!");
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
        System.out.println("You clicked me!");
    }
	
	public void onActionTimeScaleEvent(ActionEvent event) {
        System.out.println("timscale item event!");
    }
}
