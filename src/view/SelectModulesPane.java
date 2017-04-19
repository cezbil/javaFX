package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.Module;

public class SelectModulesPane extends GridPane {

	private ListView<Module> l1;
	private ListView<Module> l2;
	private Button btnReset, btnAdd, btnSubmit, btnRemove;
	private Label lblCredits = new Label("0");
	private Label lblCreditsTitle = new Label("Current Credits: ");
private Label lblUnselected, lblSelected;


	public SelectModulesPane() {
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		btnReset = new Button("Reset");
		btnAdd = new Button("Add");
		btnSubmit = new Button("Submit");
		btnRemove = new Button("Remove");
		lblUnselected = new Label("Unselected Modules: ");
		lblSelected = new Label("Selected Modules: ");
		l1 = new ListView<Module>();
		l2 = new ListView<Module>();        
		HBox hboxBtn1 = new HBox();
		hboxBtn1.setAlignment(Pos.BASELINE_RIGHT);
		hboxBtn1.setSpacing(20);
		hboxBtn1.getChildren().addAll(btnReset,btnRemove);

		HBox hboxBtn2 = new HBox();
		hboxBtn2.setAlignment(Pos.BASELINE_LEFT);
		hboxBtn2.setSpacing(20);
		hboxBtn2.getChildren().addAll(btnAdd, btnSubmit);

		HBox hbox = new HBox(3);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(lblCreditsTitle,lblCredits);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(50);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		RowConstraints row0 = new RowConstraints();
		row0.setVgrow(Priority.ALWAYS);

		this.getColumnConstraints().addAll(column0,column1);
		this.getRowConstraints().add(row0);

		this.add(lblUnselected, 0, 0);
		this.add(lblSelected, 1, 0);
		this.add(l1, 0, 1);
		this.add(l2, 1, 1);
		this.add(hboxBtn1, 0, 2);
		this.add(hboxBtn2, 1, 2);
		this.add(hbox, 0, 6);
	}

	public void populateListView(ObservableList<Module> mod) {
		l1.getItems().addAll(mod);
		l1.getSelectionModel().select(0);
	}

	public ListView<Module> getChoosableListView() {
		return l1;
	}

	public ListView<Module> getChosenListView() {
		return l2;
	}

	public void addAddButtonHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	public void addRemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}

	public void addResetButtonHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	public void addSubmitButtonModuleHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

	public Label getCreditsLabel()
	{
		return lblCredits;
	}

	public Button getButtonAdd() {
		return btnAdd;
	}
}
