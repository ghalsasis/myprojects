/**
* Application to implement ecommerce example as described in pdf in src/main/resources
*
* @author  Supriya Ghalsasi
* @version 1.0
* @since   25-11-2020
*/
package com.example.ecommerce;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


public class CheckoutTest {
	

    @Test
    public void applyPromotionTest1() {
   	
    	Cart c = new Cart();
    	ProductData pd = new ProductData();
    	List<Product> checkoutList1 = new ArrayList<Product>();
    	checkoutList1.add(pd.createProduct("0001"));
    	checkoutList1.add(pd.createProduct("0001"));
    	checkoutList1.add(pd.createProduct("0002"));
    	checkoutList1.add(pd.createProduct("0003"));

    	double cartSumNoDiscount = (double)Math.round(c.CartSumNoDiscount(checkoutList1) * 100d) / 100d;
    	double cartSumWithDiscount = (double)Math.round(c.applyPromotions(checkoutList1) * 100d) / 100d;
    	
    	org.junit.jupiter.api.Assertions.assertEquals(118.89, cartSumNoDiscount, "Incorrect sum");
    	org.junit.jupiter.api.Assertions.assertEquals(103.47, cartSumWithDiscount, "Incorrect promotion calculation");
    }
    
    @Test
    public void applyPromotionTest2() {
   	
    	Cart c = new Cart();
    	ProductData pd = new ProductData();
    	List<Product> checkoutList2 = new ArrayList<Product>();
    	checkoutList2.add(pd.createProduct("0001"));
    	checkoutList2.add(pd.createProduct("0001"));
    	checkoutList2.add(pd.createProduct("0001"));
    	
    	double cartSumNoDiscount = (double)Math.round(c.CartSumNoDiscount(checkoutList2) * 100d) / 100d;
    	double cartSumWithDiscount = (double)Math.round(c.applyPromotions(checkoutList2) * 100d) / 100d;
    	
    	org.junit.jupiter.api.Assertions.assertEquals(74.85, cartSumNoDiscount, "Incorrect sum");
    	org.junit.jupiter.api.Assertions.assertEquals(68.97, cartSumWithDiscount, "Incorrect promotion calculation");
    }
    
    
    @Test
    public void applyPromotionTest3() {

    	Cart c = new Cart();
    	ProductData pd = new ProductData();
    	List<Product> checkoutList3 = new ArrayList<Product>();
    	checkoutList3.add(pd.createProduct("0002"));
    	checkoutList3.add(pd.createProduct("0002"));
    	checkoutList3.add(pd.createProduct("0003"));
    	
    	double cartSumNoDiscount = (double)Math.round(c.CartSumNoDiscount(checkoutList3) * 100d) / 100d;
    	double cartSumWithDiscount = (double)Math.round(c.applyPromotions(checkoutList3) * 100d) / 100d;
    	
    	org.junit.jupiter.api.Assertions.assertEquals(133.99, cartSumNoDiscount, "Incorrect sum");
    	org.junit.jupiter.api.Assertions.assertEquals(120.59, cartSumWithDiscount, "Incorrect promotion calculation");
    }
}
