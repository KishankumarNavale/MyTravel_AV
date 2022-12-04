package com.av.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.av.Main;
import com.av.util.Util;
import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PackageController {

	ObservableList<String> tourTypeList = FXCollections.observableArrayList("South India Tours",
			"Domestic Tours by Flight", "North India Tours", "Tours Abroad");
	ObservableList<String> tourTypeListEdit = FXCollections.observableArrayList("South India Tours",
			"Domestic Tours by Flight", "North India Tours", "Tours Abroad");

	@FXML
	private TextField packageName;

	@FXML
	private TextArea cities;

	@FXML
	private TextField distance;

	@FXML
	private TextField travelMode;

	@FXML
	private TextField fare;

	@FXML
	private TextField noOfDays;

	@FXML
	private ComboBox tourTypeBox;
	@FXML
	private TextField tripCode;
	@FXML
	private TextArea departureDates;
	@FXML
	private Label userLabel;

	private Stage dialogStage;
	private Packages packages;
	private boolean okClicked = false;
	public static String packageUID = "";

	@FXML
	private void initialize() {
		tourTypeBox.setValue("Select Tour Type");
		tourTypeBox.setItems(tourTypeList);
		userLabel.setText(Users.getUserName());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the package to be edited in the dialog.
	 * 
	 * @param person
	 * @throws Exception
	 */
	public void setPackage(Packages packages) throws Exception {
		this.packages = packages;

		packageName.setText(packages.getPackageName());
		tourTypeBox.setItems(tourTypeListEdit);
		tourTypeBox.setValue(packages.getTourType());
		tripCode.setText(packages.getTripCode());
		noOfDays.setText(packages.getNoOfDays());
		fare.setText(packages.getFare());
		travelMode.setText(packages.getTravelMode());
		distance.setText(packages.getDistance());
		cities.setText(packages.getCities());
		departureDates.setText(packages.getDepartures());
		packageUID = packages.getPackageUID();

	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {

		packages.setPackageName(packageName.getText());
		packages.setTripCode(tripCode.getText());
		packages.setTourType((String) tourTypeBox.getValue());
		packages.setNoOfDays(noOfDays.getText());
		packages.setFare(fare.getText());
		packages.setTravelMode(travelMode.getText());
		packages.setDistance(distance.getText());
		packages.setCities(cities.getText());
		packages.setDepartures(departureDates.getText());
		okClicked = true;

	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private Main main = new Main();

	public void onChangeTourTypeBoxForPackage() throws Exception {

		Connection con = null;
		String retriveCust = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// tripCodeList.clear();
		String tripCodeListquery = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			retriveCust = "select * from packages where tour_type like ? ";
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, tourTypeBox.getValue().toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("QUERies :" + rs.getString("trip_code"));
				tripCodeListquery = rs.getString("trip_code");
			}
			tripCode.setText(tripCodeListquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@FXML
	private void editPackage() throws IOException, ClassNotFoundException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("Package UID" + packageUID);
			Util util = new Util();
			con = util.openConnection();
			String query = "update packages set packages_name = ?, trip_code=?, no_of_days=?, fare=?, travel_mode=?, tour_type=?, "
					+ "distance=?, cities=?, departures=?  where packages_pk = ? ";

			pstmt = con.prepareStatement(query);
			if ("".equalsIgnoreCase(packageName.getText().trim().trim())) {
				showValidationAlert("PackageName is required!!");
				packageName.requestFocus();
				return;
			} /*
				 * else if ("Select Gender"
				 * .equalsIgnoreCase(genderBox.getValue().toString())) {
				 * showValidationAlert("Select Gender!!");
				 * genderBox.requestFocus(); return; }
				 */ else if ("".equalsIgnoreCase(tripCode.getText().trim().trim())) {
				showValidationAlert("Provide Trip Code!!");
				tripCode.requestFocus();
				return;
			} /*
				 * else if (emailId.getText().trim().trim().isEmpty()) {
				 * showValidationAlert("Email Id is required!!");
				 * emailId.requestFocus(); return; } else if (!new
				 * Util().emailValidator(emailId.getText().trim().trim())) {
				 * showValidationAlert("Enter Valid Email Id");
				 * emailId.requestFocus(); return;
				 * 
				 * }
				 */ else if (noOfDays.getText().trim().trim().isEmpty()) {
				showValidationAlert("No Of Days Missing!!");
				noOfDays.requestFocus();
				return;
			}

			else if (fare.getText().trim().isEmpty()) {
				showValidationAlert("Fare is Missing!!");
				fare.requestFocus();
				return;
			}

			else if (travelMode.getText().trim().isEmpty()) {
				showValidationAlert("TRavel Mode Missing!!");
				travelMode.requestFocus();
				return;
			}

			else if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select Tour Type!!!");
				tourTypeBox.requestFocus();
				return;

			} /*
				 * else if (distance.getText().trim().isEmpty()) {
				 * showValidationAlert("Distance is Missing!!");
				 * distance.requestFocus(); return; }
				 */

			else if (cities.getText().trim().isEmpty()) {
				showValidationAlert("Cities are Missing!!");
				cities.requestFocus();
				return;
			}

			else if (departureDates.getText().trim().isEmpty()) {
				showValidationAlert("Departure Dates are Missing!!");
				departureDates.requestFocus();
				return;
			}

			else {
				pstmt.setString(1, packageName.getText().trim());
				pstmt.setString(2, tripCode.getText().trim());
				pstmt.setString(3, noOfDays.getText().trim());
				pstmt.setString(4, fare.getText().trim());
				pstmt.setString(5, travelMode.getText().trim());
				pstmt.setString(9, departureDates.getText().trim());
				pstmt.setString(7, distance.getText().trim());
				pstmt.setString(6, tourTypeBox.getValue().toString());
				pstmt.setString(8, cities.getText().trim().trim());
				pstmt.setString(10, packageUID);

				int count = pstmt.executeUpdate();

				if (0 != count) {
					showValidationAlert("Package Details Updated Successfully!!");
					packageName.clear();
					tripCode.clear();
					noOfDays.clear();
					fare.clear();
					travelMode.clear();
					tourTypeBox.setValue("Select Tour Type");
					distance.clear();
					cities.clear();
					departureDates.clear();
                                        Main.ls.info("Package : " + packageName +"    "+"Updated Successfully");

				}
				Main.editPackageStage.hide();
				Main.registerCustomerStage.show();
				final Stage dialog = new Stage();
				dialog.initModality(Modality.NONE);
				dialog.initOwner(Main.registerCustomerStage);
				dialog.setTitle("IMPORTANT");
				VBox dialogVbox = new VBox(20);
				dialogVbox.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
				dialogVbox.getChildren().add(new Text("Click on Search Button to get Updated Records!!"));
				Scene dialogScene = new Scene(dialogVbox, 400, 40);
				dialog.setScene(dialogScene);
				dialog.showAndWait();

			}

		} catch (SQLException e) {
                        Main.le.error("Package : " + packageName + "could not be updated" ,e);
			e.printStackTrace();
		} finally {
			// 5. Close ALL JDBC Objects
			System.out.println("In Finally block");
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // End of try-catch block
	}// End of main

	@FXML
	private void savePackages() throws IOException, ClassNotFoundException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Util util = new Util();
			con = util.openConnection();
			String query = " insert into packages " + " values (?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?) ";

			pstmt = con.prepareStatement(query);
			if ("".equalsIgnoreCase(packageName.getText().trim())) {
				showValidationAlert("Package Name is required!!");
				packageName.requestFocus();
				return;
			} else if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select Tour Type!!!");
				tourTypeBox.requestFocus();
				return;
			}

			else if ("".equalsIgnoreCase(tripCode.getText().trim())) {
				showValidationAlert("Enter TripCode !!!");
				tripCode.requestFocus();
				return;
			} else if (noOfDays.getText().trim().isEmpty()) {
				showValidationAlert("No of Days is Missing!!");
				noOfDays.requestFocus();
				return;
			} else if (fare.getText().trim().trim().isEmpty()) {
				showValidationAlert("Fare is Missing!!");
				fare.requestFocus();
				return;
			} else if (travelMode.getText().trim().trim().isEmpty()) {
				showValidationAlert("Mode of travel is Missing!!");
				travelMode.requestFocus();
				return;
			}
			/*
			 * else if (distance.getText().trim().trim().isEmpty()) {
			 * showValidationAlert("Distance of travel is Missing!!");
			 * distance.requestFocus(); return; }
			 */
			else if ("".equalsIgnoreCase(cities.getText().trim().trim())) {
				showValidationAlert("Provide Cities Covered!!");
				cities.requestFocus();
				return;
			} else if ("".equalsIgnoreCase(departureDates.getText().trim().trim())) {
				showValidationAlert("Provide Departure Dates!!");
				departureDates.requestFocus();
				return;
			} else {
				pstmt.setString(1, packageName.getText().trim());
				pstmt.setString(6, tourTypeBox.getValue().toString());
				pstmt.setString(2, tripCode.getText().trim());
				pstmt.setString(3, noOfDays.getText());
				pstmt.setString(4, fare.getText().trim());
				pstmt.setString(5, travelMode.getText().trim());
				pstmt.setString(8, cities.getText().trim());
				pstmt.setString(9, departureDates.getText().trim());
				pstmt.setString(7, distance.getText().trim());
				pstmt.setString(11, packageName.getText().trim() + tripCode.getText().trim());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String file_date = dateFormat.format(date);
				pstmt.setString(10, file_date);

				int count = pstmt.executeUpdate();

				if (0 != count) {
					showValidationAlert("Package Saved Successfully!!");
					packageName.clear();
					fare.clear();
					travelMode.clear();
					cities.clear();
					tripCode.clear();
					tourTypeBox.setValue("Select Tour Type");
					departureDates.clear();
					noOfDays.clear();
					distance.clear();
                                        Main.ls.info("Package : " + packageName+"    "+"created Successfully");

				}

			}
			// 4. Process the Results returned by SQL Queries
			// System.out.println("No. of Rows Affected Count : "+count);

		} catch (SQLException e) {
                        Main.le.error("Package : " + packageName + "could not be created" ,e);
			e.printStackTrace();
		} finally {
			// 5. Close ALL JDBC Objects
			System.out.println("In Finally block");
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // End of try-catch block
	}// End of main

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

	// main.ShowRegisterCustomers();
	@FXML
	private void onPhoneDataType() throws IOException {

		main.showErrorMessage();
		// Alert alert = new Alert(AlertType.CONFIRMATION);
		// alert.setTitle("Success Message");
		// alert.setHeaderText("Registration Compleated");
		// //alert.setContentText("You are existing from Application!");
	}

	@FXML
	private void getEnterPackagesBackAction() throws IOException {
		main.customerRegBackAction();
	}

	@FXML
	private void resetPackages() throws IOException {

		packageName.clear();
		fare.clear();
		travelMode.clear();
		cities.clear();
		tripCode.clear();
		tourTypeBox.setValue("Select Tour Type");
		departureDates.clear();
		noOfDays.clear();
		distance.clear();

	}

	public void onChangeTourTypeBox() throws Exception {
		tripCode.requestFocus();
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

}
