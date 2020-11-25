/**
* Application to implement ecommerce example as described in pdf in src/main/resources
*
* @author  Supriya Ghalsasi
* @version 1.0
* @since   25-11-2020
*/

package com.example.ecommerce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
	

	protected List<Product> items;

    public void AddItem( Product newItem ) {  }
    
    public Map<String, Object> itemPromotions = new HashMap<String, Object>();
    public Map<Double, Double> totalDiscount = new HashMap<Double, Double>();
    
    @SuppressWarnings("unchecked")
	public double applyPromotions(List<Product> items) {
    	
    	Promotions p = new Promotions();
    	
    	itemPromotions = p.getItemPromotions();
    	
    	
		itemPromotions.entrySet().forEach(entry -> {
    		String itemId = entry.getKey();
    		
			Map<Integer, Double> itemDiscountParameters = (Map<Integer, Double>) entry.getValue();
    		
    		itemDiscountParameters.forEach( (key, value) -> {
    			int itemCount = key.intValue();
    			double itemDiscountedPrice = value.doubleValue();
    			
    			long count = items.stream().filter(item -> itemId.equals(item.getId())).count();
            	if(count >= itemCount)
            		items.forEach(e -> {if(e.getId().equalsIgnoreCase(itemId))
            								e.setPrice(itemDiscountedPrice);
            							});
    			
    		});
    	});
    		
    	double discountedSum = items.stream().map(e -> e.getPrice()).reduce(0.0, Double::sum);

    	if(discountedSum > p.discountSumThreshold)
    		discountedSum = discountedSum * (1 -(p.discountSumPercentage/100));
        return discountedSum;
        
     }
    
    
    public double CartSumNoDiscount(List<Product> items) {
    	return items.stream().map(e -> e.getPrice()).reduce(0.0, Double::sum);
    }
    
   
       
}




