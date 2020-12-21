/**
 * 
 */
package uk.sky;

import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * @author supriya ghalsasi
 *
 */
public class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
	private static final String[] HEADER = new String[] { "REQUEST_TIMESTAMP", "COUNTRY_CODE", "RESPONSE_TIME" };

	@Override
	public String[] generateHeader() {
		return HEADER;
	}
}