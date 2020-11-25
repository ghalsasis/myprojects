/**
* Application to implement ecommerce example as described in pdf in src/main/resources
*
* @author  Supriya Ghalsasi
* @version 1.0
* @since   25-11-2020
*/

package com.example.ecommerce;

public class Product {
	
	public String id;
	public String name;
	public double price;
	public Currency currency;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency( ) {
		this.currency = Currency.GBP ;
	}

	

}
