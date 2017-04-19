package view;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


public class OverviewSelectionPane extends GridPane {
	private TextArea selection;
	private Button btnSave;
	public OverviewSelectionPane(){
		selection = new TextArea("Overview will appear here\n");


		selection.setPrefRowCount(20);
		selection.setPrefColumnCount(50);
		selection.setWrapText(true);
		selection.setPrefWidth(500);

		selection.setEditable(false);
		selection.autosize();
		btnSave = new Button("Save");


		HBox HBoxbtn = new HBox();
		HBoxbtn.setPadding(new Insets(10, 0, 0, 0));
		HBoxbtn.setAlignment(Pos.TOP_RIGHT);
		HBoxbtn.getChildren().add(btnSave);

		
		this.add(selection, 0, 0);
		this.add(HBoxbtn, 0, 1);
		this.setPadding(new Insets(20, 20, 20, 10));
		
		
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(50);
		RowConstraints row0 = new RowConstraints();
		row0.setVgrow(Priority.ALWAYS);
		this.getColumnConstraints().add(column0);
		this.getRowConstraints().add(row0);
		this.setAlignment(Pos.TOP_CENTER);


	}

	public TextArea getSelection() {
		return selection;
	}

	public void setSelection(String overview){
		selection.setText(overview);

	}
	public void appendSelection(String overview){
		selection.appendText(overview);

	}
	public void setColor(String colour) {
		this.setStyle("-fx-background-color: " + colour + ";");
	}

	public void addSaveButtonModuleHandler(EventHandler<ActionEvent> handler) {
		btnSave.setOnAction(handler);
	}

}
