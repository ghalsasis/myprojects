/**
* Application to implement ecommerce example as described in pdf in src/main/resources
*
* @author  Supriya Ghalsasi
* @version 1.0
* @since   25-11-2020
*/

package com.example.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class Promotions {
	
	final double discountedWaterBottlePrice = 22.99;
	final String waterBottleId = "0001";
	final double discountSumThreshold = 75.0;
	final double discountSumPercentage = 10.0;
	final int discountWaterBottleCountThreshold = 2;

    public Map<Integer, Double> itemCountPromotion = new HashMap<Integer, Double>();
    
    public Map<Double, Double> totalDiscount = new HashMap<Double, Double>();
    
    Map<String, Object> itemPromotions =new HashMap<String, Object>();
    
    public Map<String, Object> getItemPromotions(){
    	
    	itemCountPromotion.put(discountWaterBottleCountThreshold, discountedWaterBottlePrice);
    	itemPromotions.put(waterBottleId, itemCountPromotion);
    	return itemPromotions;
    }


    

    
    

}
