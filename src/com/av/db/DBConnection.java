package com.av.db;

public class DBConnection {
		
public static void connect(){
	
	  /*String url = "jdbc:mysql://localhost:3306/";
	  String dbName = "anandvihari";
	  String driver = "com.mysql.jdbc.Driver";
	  String userName = "root"; 
	  String password = "j2ee";
	  
	  try 
	  {
	  Class.forName(driver).newInstance();
	  Connection conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
	  
	  String query = " insert into customers " +
		       " values (?, ?, ?, ?, ?, ?, ?, ?) ";
	  PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);

pstmt = (PreparedStatement) conn.prepareStatement(query);
pstmt.setString(1,"Kiran Ganesh Bankar");
pstmt.setString(2,"9686124384" );
pstmt.setString(3,"krkp8051@gmail.com");
pstmt.setString(4,"Address");
pstmt.setString(5,"K3239203");
pstmt.setString(6,"M");
pstmt.setString(7, "DELHI");
pstmt.setString(8,"JAN");
int count = pstmt.executeUpdate();

//4. Process the Results returned by SQL Queries
System.out.println("No. of Rows Affected Count : "+count);

	  //int count=st.executeUpdate("")
	  //ResultSet res = st.executeQuery("select * from customers");
//	  while (res.next()) {
//		  
//		  
//		  //int id = res.getInt("id");
//		  //String msg = res.getString("msg");
//		  System.out.println("OHH my god  \t");
//	  }
	  //int val = st.executeUpdate("INSERT into event VALUES("+1+","+"'Easy'"+")");
	  //if(val==1)
		 System.out.print("Successfully inserted value");
	  conn.close();
	  } catch (Exception e) {
		  System.out.println("Execption got");
	  e.printStackTrace();
	  }
	  */
}
}
