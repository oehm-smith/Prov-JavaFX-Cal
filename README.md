# Overview

This is a job I was doing for a mate who wanted help with a timeline dropdown that controls a calendar view and the chart that corresponds to those dates.  For his JavaFX application.

# JavaFx cal and chart app 

This JavaFx app has a dropdown with global/month/week/day values, used to select the timeline.  The timeline changes a 2-d calendar and chart that displays data for a chosen start date for the desired time period (all data, month view, week view or day view).  With the calendar display are next and previous buttons.

This uses an abstractDb class to use to retrieve data from the desired datasource.  I implemented one to read from CSV files.  To use for a different datasource just requires implementing the Db interface and injecting it.

## Development

- This is an Eclipse project
- It is unfortunately not a Maven project as that is a little harder for JavaFx projects.  So unfortunately you need to add the following libraries.  I will copy these locally sometime:
 - JavaFX SDK
 - JUnit 4
 - Appache Commons Lang 3 - 3.1
 - OpenCSV - 2.3

_I just found [From Zero to JavaFX in 5 minutes](http://www.zenjava.com/2012/11/24/from-zero-to-javafx-in-5-minutes/) and will be using it in future projects)._

### Unit Tests

This was develeoped using TDD and the unit tests can be found under `src/test/java`.  There is a `providenceFx/AllTest.java` to run all the tests.

### Connection with JavaFx

There is an `interface.fxml` file that sets view.View as the controller.  Each interface object in the fxml has an fx:id attribute.  I use this to keep a reference to the object in the Controller:

```xml
<Button fx:id="buttonNext" text="Next"/>
```
```java
@FXML
Button buttonNext;
```

And I use the reference to set event handlers and the like:

```java
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	addHandlersForButtonNext();
	addHandlerForSliderX();
	...
}

private void addHandlersForTimeScaleButtons() {
	buttonNext.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			onButtonChange(event);
		}
	});
```

The same can be achieved by using the appropriate attributes in the FXML (through the CODE pane in **SceneBuilder**), however I like to separate my concerns and not mix code in the .fxml - well as little as possible anyway.

### Drawing the chart
Initially I thought you could define the chart in the .fxml, reference it as above and update it as the data changes.  This may work to a point, however you can't change the axis that I could see, so if the timescale changes from global to month to week to day etc... then the axias has to be redrawn.

Instead what I do is have a `<HBox` to contain the chart.  I can put any fxml elements in here i like as they will be deleted anyway.  Infact, without content you can't see the `<HBox` in SceneBuilder so I add a dummy button.

Then in the code I:

```java
@FXML
private HBox areaChartBox;

...

private void drawChart() {
	...
	NumberAxis xAxis = new NumberAxis(...);
	NumberAxis yAxis = new NumberAxis(...);
	areaChart = new AreaChart<>(xAxis, yAxis);
	areaChart.getData().addAll(_all_the_XYChart.Series_objects_);
	areaChartBox.getChildren().remove(0, areaChartBox.getChildren().size());	// delete current children
	areaChartBox.getChildren().add(areaChart);
}
```
