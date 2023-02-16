package be.abis.exercise.model;

public class Address {
	
	private String street;
	private String town;
	private String zipcode;
	private String nr;

	public Address(){}

	public Address(String street,  String nr, String zipcode, String town) {
		this.street = street;
		this.town = town;
		this.zipcode = zipcode;
		this.nr = nr;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}

	

}
