package com.av.view;



public class Customers {
   
	
	public Customers(String name, String email, String contactNo, 
			String cities, String address, String journeyDate ,String customer_pk,String noOfDays,String tripCode,String tourType,String noOfPersons,String remarks,String followUpDate,String createdDate){
		this.name=new String(name);
		this.email=new String(email);
		this.contactNo=new String(contactNo);
		this.address=new String(address);
		this.cities=new String(cities);
		this.journeyDate=new String(journeyDate);
		this.customer_pk=new String(customer_pk);
		this.noOfDays=new String(noOfDays);
		this.tripCode=new String(tripCode);
		this.tourType=new String(tourType);
		this.noOfPersons=new String(noOfPersons);
		this.remarks= new String(remarks);
		this.followUpDate= new String(followUpDate);
		this.createdDate= new String (createdDate);
		
	}

  

 
	  private String name = new String();
	  private String email = new String();
	  private String contactNo = new String();
	  private String address = new String();
	  private String cities = new String();
	  private String journeyDate = new String();
	  private String customer_pk = new String();
	  private String noOfDays = new String();
	  private String tripCode = new String();
	  private String tourType = new String();
	  private String noOfPersons = new String();
	  private String remarks = new String();
	  private String followUpDate = new String();
	  private String createdDate = new String();
    Customers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	  
    
    public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}




	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}




	public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

 

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public void setCustomer_pk(String customer_pk) {
        this.customer_pk = customer_pk;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getCustomer_pk() {
        return customer_pk;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public String getCities() {
		return cities;
	}




	public void setCities(String cities) {
		this.cities = cities;
	}




	public String getTripCode() {
		return tripCode;
	}




	public void setTripCode(String tripCode) {
		this.tripCode = tripCode;
	}




	public String getTourType() {
		return tourType;
	}




	public void setTourType(String tourType) {
		this.tourType = tourType;
	}




	public String getNoOfPersons() {
		return noOfPersons;
	}




	public void setNoOfPersons(String noOfPersons) {
		this.noOfPersons = noOfPersons;
	}

	

    
}
