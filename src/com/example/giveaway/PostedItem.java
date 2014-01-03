package com.example.giveaway;

import java.util.Date;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class PostedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;	
	//private double dLatitude;
	//private double dLongitude;
	private String strEmail;
	private String strPhone;
	private String strImageUrl;
	private Date postingDate;
	private GeoPt location;
	
	
	/*
	public PostedItem (double dLat, double dLong, String strE, String strP, String strImgUrl)
	{
		dLatitude = dLat;
		dLongitude=dLong;
		strEmail=strE;
		strPhone=strP;
		strImageUrl=strImgUrl;
		postingDate = new Date();
	}
	*/
	
    public Key getKey() {
        return key;
    }	
	
    /*
	public double getLatitude(){
		return dLatitude;
	}
	
	public void setLatitude(double dLat){
		this.dLatitude = dLat;
	}
	
	public double getLongitude(){
		return dLongitude;
	}
	
	public void setLongitude(double dLong){
		this.dLongitude=dLong;
	}
	*/
	public String getEmail(){
		return strEmail;
	}
	
	public void setEmail (String strE){
		this.strEmail=strE;
	}
	
	public String getPhone(){
		return strPhone;
	}
	
	public void setPhone(String strP){
		this.strPhone = strP;
	}
	
	public String getImageUrl(){
		return strImageUrl;
	}
	
	public void setImageUrl(String strI){
		this.strImageUrl = strI;
	}
	
	public Date getPostingDate(){
		return postingDate;
	}
	
	public void setPostingDate(Date dateDate){
		this.postingDate = dateDate;
	}
	
	public GeoPt getLocation() {
		return location;
	}
	  
	public void setLocation(GeoPt location) {
		this.location = location;
	}	
/*
	public void setLocation(float Lat, float Long) {
		this.location = new GeoPt(Lat, Long);
	}*/	
}