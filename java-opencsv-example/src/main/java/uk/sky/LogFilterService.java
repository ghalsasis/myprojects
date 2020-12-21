/**
 * 
 */
package uk.sky;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author supriya ghalsasi
 *
 */
public class LogFilterService implements DataFilterer{

	@Override
	public Map<String, List<LogRecord>> filterByCountry(List<LogRecord> records)  {		
		Map<String, List<LogRecord>> mapLogsByCountry = (Map<String, List<LogRecord>>) records.stream().collect(Collectors.groupingBy(LogRecord::getcountryCode));
		return mapLogsByCountry;
		
	}

	@Override
	public Map<String, List<LogRecord>> filterByCountryWithResponseTimeAboveLimit(List<LogRecord> records, double limit)  {		
		Map<String, List<LogRecord>> mapLogsByCountryAndLimit = (Map<String, List<LogRecord>>) records
																	.stream()
																	.filter(x -> x.getresponseTime() > limit)
																	.collect(Collectors.groupingBy(LogRecord::getcountryCode));
		return mapLogsByCountryAndLimit;

	}


	
	@Override
	public List<LogRecord> filterByResponseTimeAboveAverage(List<LogRecord> records) {
		List<Long> respTimeList = records.stream().map(LogRecord::getresponseTime).collect(Collectors.toList());
		double AverageRespTime = respTimeList.stream().mapToLong(val -> val).average().orElse(0.0);
		
		List<LogRecord> aboveAvgRecords = records.stream().filter(x -> x.getresponseTime() > AverageRespTime).collect(Collectors.toList());
		return aboveAvgRecords;
	}
	
	@SuppressWarnings("unchecked")
	public List<LogRecord> getAllRecords(BufferedReader source)  {
		
		 Map<String,String> mapping = new HashMap<String,String>();
	        mapping.put("REQUEST_TIMESTAMP","requestTimestamp");
	        mapping.put("COUNTRY_CODE","countryCode");
	        mapping.put("RESPONSE_TIME","responseTime");
	        String[] memberFieldsToBindTo = {"requestTimestamp", "countryCode", "responseTime"};
	        
	        ColumnPositionMappingStrategy<LogRecord> strategy = new ColumnPositionMappingStrategy();
	        strategy.setType(LogRecord.class);
	      
	        strategy.setColumnMapping(memberFieldsToBindTo);
	        CsvToBean<LogRecord> csvToBean = new CsvToBeanBuilder<LogRecord>(source)
	        															.withMappingStrategy(strategy)
	        															.withSkipLines(1)
	        															.withIgnoreLeadingWhiteSpace(true)
	        															.build();

        List<LogRecord> logRecords = csvToBean.parse();
        return logRecords;
        
	}
	
	/**
	 * @param mapLogsByCountry
	 * @param outFileName
	 */
	public void WriteFilteredLogs(Map<String, List<LogRecord>> mapLogsByCountry, String outFileName) {

		for (Map.Entry<String, List<LogRecord>> entry : mapLogsByCountry.entrySet()) {
            String key = (String)entry.getKey();
            List<LogRecord> value = entry.getValue();
            String fileName = String.format(outFileName, key);            
            writeRecords(value, fileName);           
            
        }

	}

	/**
	 * @param <BeanToCsv>
	 * @param value
	 * @param fileName
	 */
	public <BeanToCsv> void writeRecords(List<LogRecord> value, String fileName) {
		Path myPath = Paths.get(fileName);
        try (var writer = Files.newBufferedWriter(myPath, StandardCharsets.UTF_8)) {
        	
        	final CustomMappingStrategy<LogRecord> mappingStrategy = new CustomMappingStrategy<>();
        	mappingStrategy.setType(LogRecord.class);

        	final StatefulBeanToCsv<LogRecord> beanToCsv = new StatefulBeanToCsvBuilder<LogRecord>(writer)
        	    .withMappingStrategy(mappingStrategy)
        	    .build();
        	
        	
        	beanToCsv.write(value);
        	writer.close();
        	Logger.getLogger(LogFilterService.class.getName()).log(Level.INFO, "CSV File written successfully.");
            

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex) {
            Logger.getLogger(LogFilterService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
		
	}


}
