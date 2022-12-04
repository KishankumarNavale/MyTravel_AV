package com.av.view;

import java.io.IOException;

import com.av.Main;
import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class MainPageController {

	private Main main = new Main();
	@FXML
	private Label headerLabel;

	@FXML
	private Label userLabel;
	@FXML
	private Button registerButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button registerPackageButton;
	@FXML
	private Button searchPackageButton;
	@FXML
	private Label twisters;

	@FXML
	private void getRegisterCustomers() throws IOException {
		SearchController.editFlag = false;
		main.ShowRegisterCustomers();
	}

	@FXML
	private void initialize() {
		SearchController.editFlag = false;
		if (Users.getRole().equalsIgnoreCase("EMPLOYEE")) {
			registerPackageButton.setVisible(false);
			searchPackageButton.setVisible(false);
		} else {
			registerPackageButton.setVisible(true);
			searchPackageButton.setVisible(true);
		}
		userLabel.setText(Users.getUserName());
	}

	@FXML
	private void getSearchPakages() throws IOException {
		main.ShowSearchPakages();
	}

	@FXML
	private void getRegisterPackages() throws IOException {
		main.ShowRegisterPackages();
	}

	@FXML
	private void getCustomerDetails() throws IOException {
		main.ShowCustomersDetails();
	}

	@FXML
	private void logoutAction() throws IOException {
		main.logoutAction();
		showValidationAlert("Logged out Successfully !!");

	}
        
        
        @FXML
	public void enableInternet() {
            
            try {
            Desktop.getDesktop().browse(new URL("http://www.twisters.tech").toURI());
             Main.ls.info("Connected to URL : Twisters.tech");
            
        } 
        catch (MalformedURLException ex) {
            Main.le.error("Could not connect to URL/Internet");
        } 
        catch (URISyntaxException | IOException ex){
            Main.le.error("Could not connect to URL/Internet");
        }
		
	}

	private void showValidationAlert(String msg) {

		Alert alert = new Alert(AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		// showMainPageView();
		alert.setTitle("INFO");
		alert.setHeaderText(msg);
		alert.showAndWait();
	}
}
