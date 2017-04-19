package view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Course;
public class SetupModulePicker extends GridPane{

	private ComboBox<Course> cboxCourse;
	private TextField txtPnum, txtSurname, txtFirstName, txtEmail;
	private Button btnProfile;
	private DatePicker datePicker;

	public SetupModulePicker(){
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		//create labels
		Label lblTitle = new Label("Select course: ");
		Label lblPnum = new Label("Input P number: ");
		Label lblFirstName = new Label("Input First name: ");
		Label lblSurname = new Label("Input Surname: ");
		Label lblEmail = new Label("Input Email: ");
		Label lbldate = new Label("Input Date: ");

		cboxCourse = new ComboBox<Course>();

		txtPnum = new TextField();
		txtSurname = new TextField();
		txtFirstName = new TextField();
		txtEmail = new TextField();

		btnProfile = new Button("Create Profile");
		datePicker = new DatePicker();

		this.add(lblTitle, 0, 0);
		this.add(cboxCourse, 1, 0);


		this.add(lblPnum, 0, 1);
		this.add(txtPnum, 1, 1);


		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lbldate, 0, 5);
		this.add(datePicker, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnProfile, 1, 6);
	}

	public void populateComboBox(Course[] courses) {
		cboxCourse.getItems().addAll(courses);
		cboxCourse.getSelectionModel().select(0);
	}

	public void populateComboBox(List<Course> courses) {
		cboxCourse.getItems().addAll(courses);
		cboxCourse.getSelectionModel().select(0);
	}

	public Course getSelectedCourse(){
		return cboxCourse.getSelectionModel().getSelectedItem();
	}
	public void setCboxCourse(ComboBox<Course> cboxCourse) {
		this.cboxCourse = cboxCourse;
	}

	public void setTxtPnum(String txtPnum) {
		this.txtPnum.setText(txtPnum);
	}

	public void setTxtSurname(String txtSurname) {
		this.txtSurname.setText(txtSurname);
	}

	public void setTxtFirstName(String txtFirstName) {
		this.txtFirstName.setText(txtFirstName);
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail.setText(txtEmail);
	}

	public void setDatePicker(LocalDate datePicker) {
		this.datePicker.setValue(datePicker);
	}

	public String getPnumInput(){
		return txtPnum.getText();
	}

	public String getFNameInput() {
		return txtFirstName.getText();
	}

	public String getLNameInput() {
		return txtSurname.getText();
	}
	public String getEmail(){
		return txtEmail.getText();
	}
	public LocalDate getDate(){
		return datePicker.getValue();
	}
	public void addCourseListener(EventHandler<ActionEvent> handler){
		btnProfile.setOnAction(handler);
	}
}
