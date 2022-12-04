package com.av.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterController {

	ObservableList<String> tripMonthList = FXCollections.observableArrayList("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
			"JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
	ObservableList<String> tourTypeList = FXCollections.observableArrayList("South India Tours",
			"Domestic Tours by Flight", "North India Tours", "Tours Abroad", "Customized Trip");
	ObservableList<String> tripCodeList = FXCollections.observableArrayList();
	@FXML
	private TextField fullName;

	@FXML
	private TextArea address;

	@FXML
	private TextArea descriptionArea;

	@FXML
	private TextField emailId;

	@FXML
	private TextField phone;

	@FXML
	private TextField alternatePhone;

	@FXML
	private TextField tour;

	@FXML
	private TextArea remarks;
	@FXML
	private Label packageLabelArea;

	@FXML
	private DatePicker journeyDate;

	@FXML
	private DatePicker followUpDate;

	@FXML
	private TextField noOfDays;

	@FXML
	private TextField cities;

	@FXML
	private ComboBox tripMonthBox;
	@FXML
	private ComboBox tourTypeBox;
	@FXML
	private ComboBox tripCodeBox;

	@FXML
	private TextField noOfPerson;

	@FXML
	private Label userLabel;

	private Stage dialogStage;
	private Customers customer;
	private boolean okClicked = false;
	public static String UID = "";

	@FXML
	private Main main = new Main();

	@FXML
	private void initialize() {
		tourTypeBox.setValue("Select Tour Type");
		tripCodeBox.setValue("Select Trip Code");
		descriptionArea.setVisible(false);

		tourTypeBox.setItems(tourTypeList);
		tour.setEditable(false);
		userLabel.setText(Users.getUserName());
		packageLabelArea.setVisible(false);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 * @throws Exception
	 */
	public void setCustomer(Customers customer) throws Exception {
		this.customer = customer;

		fullName.setText(customer.getName());
		address.setText(customer.getAddress());
		emailId.setText(customer.getEmail());
		String contactNo = customer.getContactNo().substring(0, 10);
		phone.setText(contactNo);
		noOfDays.setText(customer.getNoOfDays());
		noOfPerson.setText(customer.getNoOfPersons());
		String date = customer.getJourneyDate();
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = (Date) formatter.parse(date);

		journeyDate.setValue(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		tripCodeBox.setValue(customer.getTripCode());
		tourTypeBox.setValue(customer.getTourType());
		remarks.setText(customer.getRemarks());
		tour.setText(customer.getCities());
		// As followUpDate field is not mandatory handled null
		if (customer.getFollowUpDate() == null || customer.getFollowUpDate().isEmpty()) {
			followUpDate.setValue(null);
		} else {
			String followDate = customer.getFollowUpDate();
			Date followupDate = formatter.parse(followDate);
			followUpDate.setValue(followupDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		// As alternatePhone field is not mandatory handled null
		if (customer.getContactNo().length() > 11) {
			String altContactNo = customer.getContactNo().substring(11, 22);
			alternatePhone.setText(altContactNo);
		}
		UID = customer.getCustomer_pk();
		onChangeTripCode();

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

		customer.setName(fullName.getText());
		customer.setAddress(address.getText());
		customer.setEmail(emailId.getText());
		customer.setContactNo(phone.getText());
		customer.setNoOfDays(noOfDays.getText());
		customer.setNoOfPersons(noOfPerson.getText());
		customer.setTourType(tourTypeBox.getValue().toString());
		customer.setTripCode(tourTypeBox.getValue().toString());
		customer.setRemarks(remarks.getText());
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
	private void editRegisterCustomers() throws IOException, ClassNotFoundException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("UID" + UID);
			Util util = new Util();
			con = util.openConnection();
			String query = "update customers set full_name = ?, phone_number=?, email=?, address=?, cities=?, no_of_days=?, "
					+ "journey_date=?, customer_pk=?, trip_code=?, tour_type=?, no_of_person=?,remarks=?,follow_up_date=?  where customer_pk = ? ";

			pstmt = con.prepareStatement(query);
			if ("".equalsIgnoreCase(fullName.getText().trim().trim())) {
				showValidationAlert("Name is required!!");
				fullName.requestFocus();
				return;
			} /*
				 * else if ("Select Gender"
				 * .equalsIgnoreCase(genderBox.getValue().toString())) {
				 * showValidationAlert("Select Gender!!");
				 * genderBox.requestFocus(); return; }
				 */ else if ("".equalsIgnoreCase(address.getText().trim().trim())) {
				showValidationAlert("Provide Address!!");
				address.requestFocus();
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
				 */ else if (phone.getText().trim().trim().isEmpty()) {
				showValidationAlert("Contact number Missing!!");
				phone.requestFocus();
				return;
			} else if (!phone.getText().trim().trim().matches("\\d{10}")) {
				showValidationAlert("Enter Valid Contact Number");
				phone.requestFocus();
				return;

			} else if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select Tour Type!!!");
				tourTypeBox.requestFocus();
				return;
			} else if ("Customized Trip".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (null == tour.getText() || tour.getText().isEmpty())) {
				showValidationAlert("Enter cities to include!!!");
				tour.requestFocus();
				return;
			} else if (null == tripCodeBox.getValue()
					|| "Select Trip Code".equalsIgnoreCase(tripCodeBox.getValue().toString().trim())) {
				showValidationAlert("Select Trip Code!!!");
				tripCodeBox.requestFocus();
				return;
			}

			else if (null == (journeyDate.getValue())) {
				showValidationAlert("Date is required!!");
				journeyDate.requestFocus();
				return;
			} else if (noOfDays.getText().trim().isEmpty()) {
				showValidationAlert("No of Days is Missing!!");
				noOfDays.requestFocus();
				return;
			} else if (noOfPerson.getText().trim().isEmpty()) {
				showValidationAlert("No of Persons is Missing!!");
				noOfPerson.requestFocus();
				return;
			} else {
				pstmt.setString(1, fullName.getText().trim());
				if (alternatePhone.getText().isEmpty()) {
					pstmt.setString(2, phone.getText().trim());
				} else {
					pstmt.setString(2, phone.getText().trim() + "/ " + alternatePhone.getText().trim());
				}
				pstmt.setString(3, emailId.getText().trim());
				pstmt.setString(4, address.getText().trim());
				pstmt.setString(5, tour.getText().trim());
				LocalDate date = journeyDate.getValue();
				pstmt.setString(9, tripCodeBox.getValue().toString().trim());
				pstmt.setString(10, tourTypeBox.getValue().toString());
				pstmt.setString(11, noOfPerson.getText().trim());
				Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
				Date jDate = Date.from(instant);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				String journeyDateString = sdf.format(jDate);

				String arr[] = journeyDateString.split("-");
				pstmt.setString(7, journeyDateString);
				pstmt.setString(6, noOfDays.getText());
				pstmt.setString(8, fullName.getText().trim().trim() + phone.getText().trim());
				pstmt.setString(14, UID);
				pstmt.setString(12, remarks.getText().trim());
				if (followUpDate.getValue() != null) {
					LocalDate followUpdate = followUpDate.getValue();
					Instant instantFollowUp = Instant.from(followUpdate.atStartOfDay(ZoneId.systemDefault()));
					Date fDate = Date.from(instantFollowUp);
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
					String followUpDateString = sdf1.format(fDate);
					pstmt.setString(13, followUpDateString);
				} else {
					pstmt.setString(13, "");
				}

				int count = pstmt.executeUpdate();

				if (0 != count) {
					showValidationAlert("Customer Details Updated Successfully!!");
					phone.clear();
					fullName.clear();
					emailId.clear();
					address.clear();
					tripCodeBox.setValue("Select Trip Code");
					tourTypeBox.setValue("Select Tour Type");
					tour.clear();
					noOfDays.clear();
					journeyDate.setValue(null);
					noOfPerson.clear();
					remarks.clear();
					alternatePhone.clear();
					followUpDate.setValue(null);
                                        Main.ls.info("Customer : " + fullName+"    "+"updated Successfully");
				}
				Main.editCustomerStage.hide();
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
                        Main.le.error("Package : " + fullName + "could not be updated" ,e);
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
	private void saveRegisterCustomers() throws IOException, ClassNotFoundException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Util util = new Util();
			con = util.openConnection();
			String query = " insert into customers  values (?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?) ";

			pstmt = con.prepareStatement(query);
			if ("".equalsIgnoreCase(fullName.getText().trim().trim())) {
				showValidationAlert("Name is required!!");
				fullName.requestFocus();
				return;
			} /*
				 * else if ("Select Gender"
				 * .equalsIgnoreCase(genderBox.getValue().toString())) {
				 * showValidationAlert("Select Gender!!");
				 * genderBox.requestFocus(); return; }
				 */ else if ("".equalsIgnoreCase(address.getText().trim().trim())) {
				showValidationAlert("Provide Address!!");
				address.requestFocus();
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
				 */ else if (phone.getText().trim().trim().isEmpty()) {
				showValidationAlert("Contact number Missing!!");
				phone.requestFocus();
				return;
			} else if (!phone.getText().trim().trim().matches("\\d{10}")) {
				showValidationAlert("Enter Valid Contact Number");
				phone.requestFocus();
				return;

			} else if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select Tour Type!!!");
				tourTypeBox.requestFocus();
				return;
			} else if (null == tripCodeList || tripCodeList.isEmpty())
				return;
			else if (!"Customized Trip".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (null == tripCodeBox.getValue()
							|| "Select Trip Code".equalsIgnoreCase(tripCodeBox.getValue().toString().trim()))) {

				showValidationAlert("Select Trip Code!!!");
				tripCodeBox.requestFocus();
				return;

			} else if ("Customized Trip".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (null == tour.getText() || tour.getText().isEmpty())) {
				showValidationAlert("Enter cities to include!!!");
				tour.requestFocus();
				return;
			}
			/*
			 * else if (!tripCode.getText().trim().trim().matches("\\d{5}")) {
			 * showValidationAlert("Enter  the valid TripCode !!!");
			 * tripCode.requestFocus(); return;
			 * 
			 * }
			 */

			/*
			 * else if ("".equalsIgnoreCase(tour.getText().trim())) {
			 * showValidationAlert("Package code is required!!");
			 * tour.requestFocus(); return;
			 * 
			 * }
			 */

			else if (null == (journeyDate.getValue())) {
				showValidationAlert("Date is required!!");
				journeyDate.requestFocus();
				return;
			} else if (noOfDays.getText().trim().isEmpty()) {
				showValidationAlert("No of Days is Missing!!");
				noOfDays.requestFocus();
				return;
			} else if (noOfPerson.getText().trim().isEmpty()) {
				showValidationAlert("No of Persons is Missing!!");
				noOfPerson.requestFocus();
				return;
			}

			else {
				pstmt.setString(1, fullName.getText().trim());
				if (alternatePhone.getText().isEmpty()) {
					pstmt.setString(2, phone.getText().trim());
				} else {
					pstmt.setString(2, phone.getText().trim() + "/ " + alternatePhone.getText().trim());
				}
				pstmt.setString(3, emailId.getText().trim());
				pstmt.setString(4, address.getText().trim());
				pstmt.setString(5, tour.getText().trim());
				LocalDate date = journeyDate.getValue();
				pstmt.setString(9, tripCodeBox.getValue().toString().trim());
				pstmt.setString(10, tourTypeBox.getValue().toString());
				pstmt.setString(11, noOfPerson.getText().trim());
				Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
				Date jDate = Date.from(instant);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				String journeyDateString = sdf.format(jDate);
				pstmt.setString(7, journeyDateString);
				pstmt.setString(6, noOfDays.getText());
				pstmt.setString(8, fullName.getText().trim() + phone.getText().trim());
				pstmt.setString(12, Users.getUserName() + ":" + remarks.getText().trim());
				if (followUpDate.getValue() != null) {
					LocalDate followUpdate = followUpDate.getValue();
					Instant instantFollowUp = Instant.from(followUpdate.atStartOfDay(ZoneId.systemDefault()));
					Date fDate = Date.from(instantFollowUp);
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
					String followUpDateString = sdf1.format(fDate);
					pstmt.setString(13, followUpDateString);
				} else {
					pstmt.setString(13, "");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				Date today = new Date();
				String createdDate = dateFormat.format(today);
				pstmt.setString(14, createdDate);
				int count = pstmt.executeUpdate();

				if (0 != count) {
					showValidationAlert("Customer Details saved Successfully !!");
					phone.clear();
					// genderBox.setValue("Select Gender");
					fullName.clear();
					emailId.clear();
					address.clear();
					tripCodeBox.setValue("Select Trip Code");
					tourTypeBox.setValue("Select Tour Type");
					tour.clear();
					noOfDays.clear();
					journeyDate.setValue(null);
					noOfPerson.clear();
					remarks.clear();
					alternatePhone.clear();
					followUpDate.setValue(null);
                                        Main.ls.info("Customer : " + fullName+"    "+"created Successfully");
				}

			}

		} catch (SQLException e) {
                        Main.le.error("Customer : " + fullName + "could not be created" ,e);
			showValidationAlert("Duplicate record found!!");
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
	}

	@FXML
	private void getCustomerRegisterBackAction() throws IOException {
		main.customerRegBackAction();
	}

	@FXML
	private void resetRegDetails() throws IOException {

		phone.clear();
		// genderBox.setValue("Select Gender");
		fullName.clear();
		emailId.clear();
		address.clear();
		tripCodeBox.setValue("Select Trip Code");
		tourTypeBox.setValue("Select Tour Type");
		tour.clear();
		noOfDays.clear();
		journeyDate.setValue(null);
		noOfPerson.clear();
		descriptionArea.clear();
		remarks.clear();
		followUpDate.setValue(null);
		alternatePhone.clear();

	}

	public void onChangeTourTypeBox() throws Exception {
		if ("Customized Trip".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
			showValidationAlert("Enter Cities included and No of Days !!");
			if (!SearchController.editFlag) {
				showValidationAlert("Enter Cities included and No of Days !!");
				SearchController.editFlag = true;
				noOfDays.setText("");
				tour.requestFocus();
				tour.setEditable(true);
				descriptionArea.setVisible(false);
				packageLabelArea.setVisible(false);
			}
			tour.requestFocus();
			tour.setEditable(true);
			tripCodeList.clear();
			tripCodeList.add("NA");
			descriptionArea.setVisible(false);
			tripCodeBox.setValue(tripCodeList.get(0));
			tripCodeBox.setVisible(false);
			descriptionArea.setVisible(false);
			packageLabelArea.setVisible(false);
			noOfDays.setEditable(true);
			SearchController.editFlag = false;
			tour.setText("");

			return;
		} else {
			if (!SearchController.editFlag) {
				tripCodeBox.setValue("Select Trip Code");
				SearchController.editFlag = true;
			}
			tripCodeBox.setVisible(true);
			noOfDays.setText("NA");
			noOfDays.setEditable(false);
			tour.setText("NA");
			tour.setEditable(false);
			Connection con = null;
			String retriveCust = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			tripCodeList.clear();
			List<String> tripCodeListquery = new ArrayList<String>();
			try {

				Util util = new Util();
				con = util.openConnection();
				retriveCust = "select * from packages where tour_type like ? ";
				pstmt = con.prepareStatement(retriveCust);
				pstmt.setString(1, tourTypeBox.getValue().toString());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					tripCodeListquery.add(rs.getString("trip_code"));
				}
				tripCodeList.addAll(tripCodeListquery);
				tripCodeBox.setItems(tripCodeList);
				descriptionArea.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tour.setEditable(false);
			tripCodeBox.requestFocus();
			return;
		}
	}

	public void onChangeTripCode() throws Exception {
		Connection con = null;
		String retriveCust = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Util util = new Util();
			con = util.openConnection();
			retriveCust = "select * from packages where trip_code = ? ";
			pstmt = con.prepareStatement(retriveCust);
			if (tripCodeBox.getValue() != null) {
				pstmt.setString(1, tripCodeBox.getValue().toString());
				rs = pstmt.executeQuery();
				packageLabelArea.setVisible(true);
				descriptionArea.setVisible(true);
				descriptionArea.setEditable(false);
				while (rs.next()) {
					descriptionArea.setText(rs.getString("packages_name"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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
