/**
 * 
 */
package uk.sky;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
/**
 * @author supri
 *
 */
public class LogRecord {
	
	@CsvBindByName (column = "REQUEST_TIMESTAMP", required = true)
	@CsvBindByPosition(position=0)
	private String  requestTimestamp;
	
	@CsvBindByName (column = "COUNTRY_CODE", required = true)
	@CsvBindByPosition(position=1)
	private String countryCode;
	
	@CsvBindByName (column = "RESPONSE_TIME", required = true)
	@CsvBindByPosition(position=2)
	private long responseTime;
	
	
	public String getrequestTimestamp() {
		return requestTimestamp;
	}
	public void setrequestTimestamp(String requestTs) {
		requestTimestamp = requestTs;
	}
	public String getcountryCode() {
		return countryCode;
	}
	public void setcountryCode(String cc) {
		countryCode = cc;
	}
	public long getresponseTime() {
		return responseTime;
	}
	public void setresponseTime(long resT) {
		responseTime = resT;
	}
	
	@Override
    public String toString()
   {
		var builder = new StringBuilder();
	    builder.append("LogRecord{id=").append(requestTimestamp).append(", countryCode=")
	            .append(countryCode).append(", responseTime=").append(responseTime).append("}");
	    return builder.toString();
   }

}
