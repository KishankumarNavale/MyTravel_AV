package com.av.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.av.Main;
import com.av.util.Util;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class LoginController {
	@FXML
	private TextField loginUname;
	@FXML
	private PasswordField loginPassword;
	@FXML
	private TextField cpUname;
	@FXML
	private PasswordField cpOldPassword;
	@FXML
	private PasswordField cpNewPassword;
	@FXML
	private Hyperlink chagePasswordLink;

	@FXML
	private Button loginButton;
	@FXML
	private Button cpCancelButton;
	@FXML
	private Button loginCancel;
	@FXML
	private Label cpUnamelabel;
	@FXML
	private Label loginPasswordLabel;
	@FXML
	private Label cpOldPasswordLabel;
	@FXML
	private Label loginUnameLabel;
	@FXML
	private Label cpNewPasswordLabel;
	@FXML
	private Label cpTitle;
	@FXML
	private Label loginTitle;
	@FXML
	private Main main = new Main();

	@FXML
	private Button cpConfirm;

	@FXML
	private void initialize() {

		cpConfirm.setVisible(false);
		cpNewPassword.setVisible(false);
		cpNewPasswordLabel.setVisible(false);
		cpOldPassword.setVisible(false);
		cpOldPasswordLabel.setVisible(false);
		cpTitle.setVisible(false);
		cpUname.setVisible(false);
		cpUnamelabel.setVisible(false);
		cpCancelButton.setVisible(false);

	}

	@FXML
	public void loginAction() {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "select * from users where user_name = ? and password= ?";
			pstmt = con.prepareStatement(retriveCust);
			if ("".equalsIgnoreCase(loginUname.getText().trim())) {
				showValidationAlert("Enter Username!!");
				loginUname.requestFocus();
				return;
			} else if (loginPassword.getText().trim().isEmpty()) {
				showValidationAlert("Enter Password!!");
				loginPassword.requestFocus();
				return;
			}

			pstmt.setString(1, loginUname.getText().trim());
			pstmt.setString(2, loginPassword.getText().trim());
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			String role = null;
			String userName = null;
			while (rs.next()) {
				role = rs.getString("role");
				userName = rs.getString("user_name");
				Users.setRole(role);
				Users.setUserName(userName);
				count++;
			}
			if (count != 1) {
				main.navigateToAdminPage();
				main.backupDataWithOutDatabase();
                                Main.ls.info("User :   " + loginUname.getText().trim() +"    "+"Logged in Successfully");
				loginUname.clear();
				loginPassword.clear();
			} else {
				showValidationAlert("Invalid UserName / Password !!");
                                Main.le.error("User :   " + loginUname.getText().trim() +" entered wrong password  :   " + loginPassword.getText().trim(),null);
			}
		} catch (Exception e) {
                        Main.le.error("Excpetion :   ", e);
			e.printStackTrace();
		}
	}

	@FXML
	public void confirmChangePasswordAction() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtupdate = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String updateQuery = "update users set password = ? where user_name = ? ";
			String retriveCust = "select * from users where user_name = ? and password= ?";
			pstmt = con.prepareStatement(retriveCust);
			pstmtupdate = con.prepareStatement(updateQuery);
			if ("".equalsIgnoreCase(cpUname.getText().trim())) {
				showValidationAlert("Enter Username!!");
				cpUname.requestFocus();
				return;
			} else if (cpOldPassword.getText().trim().isEmpty()) {
				showValidationAlert("Enter Old Password!!");
				cpOldPassword.requestFocus();
				return;
			} else if (cpNewPassword.getText().trim().isEmpty()) {
				showValidationAlert("Enter New password!!");
				cpNewPassword.requestFocus();
				return;
			}

			pstmt.setString(1, cpUname.getText().trim());
			pstmt.setString(2, cpOldPassword.getText().trim());
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			String role = null;
			while (rs.next()) {
				role = rs.getString("role");
				Users.setRole(role);
				count++;
			}
			if (count != 1) {

				pstmtupdate.setString(1, cpNewPassword.getText().trim());
				pstmtupdate.setString(2, cpUname.getText().trim());
				int uCount = pstmtupdate.executeUpdate();

				if (uCount >= 1) {
					showValidationAlert("Password changed Successfully!!");

					cpConfirm.setVisible(false);
					cpNewPassword.setVisible(false);
					cpNewPasswordLabel.setVisible(false);
					cpOldPassword.setVisible(false);
					cpOldPasswordLabel.setVisible(false);
					cpTitle.setVisible(false);
					cpUname.setVisible(false);
					cpUnamelabel.setVisible(false);
					cpCancelButton.setVisible(false);

					cpNewPassword.clear();
					cpOldPassword.clear();
					cpUname.clear();

					loginButton.setVisible(true);
					loginCancel.setVisible(true);
					loginPassword.setVisible(true);
					loginPasswordLabel.setVisible(true);
					loginTitle.setVisible(true);
					loginUname.setVisible(true);
					loginUnameLabel.setVisible(true);
					chagePasswordLink.setVisible(true);
					loginPassword.clear();
					loginUname.clear();
                                        Main.ls.info("Password for " +cpUname.getText().trim() + "  " + "Changed Successfully");

				}
			} else {
				showValidationAlert("Invalid UserName / Password !!");
                                Main.le.error("Password for " +cpUname.getText().trim() + "  " + "wrongly entered");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void cancelLogin() {
		loginPassword.clear();
		loginUname.clear();

	}

	@FXML
	public void cpCancelAction() {
		cpConfirm.setVisible(false);
		cpNewPassword.setVisible(false);
		cpNewPasswordLabel.setVisible(false);
		cpOldPassword.setVisible(false);
		cpOldPasswordLabel.setVisible(false);
		cpTitle.setVisible(false);
		cpUname.setVisible(false);
		cpUnamelabel.setVisible(false);
		cpCancelButton.setVisible(false);

		cpNewPassword.clear();
		cpOldPassword.clear();
		cpUname.clear();

		loginButton.setVisible(true);
		loginCancel.setVisible(true);
		loginPassword.setVisible(true);
		loginPasswordLabel.setVisible(true);
		loginTitle.setVisible(true);
		loginUname.setVisible(true);
		loginUnameLabel.setVisible(true);
		chagePasswordLink.setVisible(true);
		loginPassword.clear();
		loginUname.clear();

	}

	@FXML
	public void enableCPAction() {
		cpConfirm.setVisible(true);
		cpNewPassword.setVisible(true);
		cpNewPasswordLabel.setVisible(true);
		cpOldPassword.setVisible(true);
		cpOldPasswordLabel.setVisible(true);
		cpTitle.setVisible(true);
		cpUname.setVisible(true);
		cpUnamelabel.setVisible(true);
		cpCancelButton.setVisible(true);

		loginButton.setVisible(false);
		loginCancel.setVisible(false);
		loginPassword.setVisible(false);
		loginPasswordLabel.setVisible(false);
		loginTitle.setVisible(false);
		loginUname.setVisible(false);
		loginUnameLabel.setVisible(false);
		chagePasswordLink.setVisible(false);

		cpNewPassword.clear();
		cpOldPassword.clear();
		cpUname.clear();
		loginPassword.clear();
		loginUname.clear();

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
