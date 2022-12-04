package com.av.view;



public class Packages {
	
	public Packages(String packageName, String tripCode, String noOfDays, 
			String fare, String travelMode, String tourType ,String distance,String cities,String departures,String createdDate,String packageUID){
		this.packageName=packageName;
		this.tripCode=tripCode;
		this.noOfDays=noOfDays;
		this.fare=fare;
		this.travelMode=travelMode;
		this.tourType=tourType;
		this.distance=distance;
		this.cities=cities;
		this.departures=departures;
		this.createdDate=createdDate;
        this.packageUID=packageUID;
		
		
		
	}

    
	  private String packageName = new String();
	  private String tripCode = new String();
	  private String noOfDays = new String();
	  private String fare = new String();
	  private String travelMode = new String();
	  private String tourType = new String();
	  private String distance = new String();
	  private String cities = new String();
	  private String departures = new String();
	  private String createdDate = new String();
          private String packageUID=new String();

    Packages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
        public String getPackageUID() {
         return packageUID;
        }

        public void setPackageUID(String packageUID) {
            this.packageUID = packageUID;
        }

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTripCode() {
		return tripCode;
	}

	public void setTripCode(String tripCode) {
		this.tripCode = tripCode;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

	public String getTourType() {
		return tourType;
	}

	public void setTourType(String tourType) {
		this.tourType = tourType;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public String getDepartures() {
		return departures;
	}

	public void setDepartures(String departures) {
		this.departures = departures;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	  
	  
	

       
}
