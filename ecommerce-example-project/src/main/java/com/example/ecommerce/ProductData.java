/**
* Application to implement ecommerce example as described in pdf in src/main/resources
*
* @author  Supriya Ghalsasi
* @version 1.0
* @since   25-11-2020
*/

package com.example.ecommerce;

public class ProductData {
	
	String id;
	
	public Product createProduct(String id) {

		Product product = new Product();
		if(id.equalsIgnoreCase("0001")) {
			product.setId(id);			
			product.setName("Water Bottle");
			product.setPrice(24.95);
		}
		
		if(id.equalsIgnoreCase("0002")) {
			product.setId(id);
			product.setName("Hoodie");
			product.setPrice(65.00);
		}
		
		if(id.equalsIgnoreCase("0003")) {
			product.setId(id);
			product.setName("Sticker Set");
			product.setPrice(3.99);
		}
		
		return product;
	}

}
