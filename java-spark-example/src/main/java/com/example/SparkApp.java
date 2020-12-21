package com.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SparkApp {
	
	private static final Logger LOG = Logger.getLogger(SparkApp.class.getName());
	private static String regex = "\\[|\\]";

	
	static String responseTimeThreshold; 
	static String outputFilesPath; 	  
	static String averageResponseTime; 
	static String inputFilePath;
	
	public static void main(String[] args) throws IOException {
		loadProperties();
		SparkApp sparkApp = new SparkApp(responseTimeThreshold, outputFilesPath, inputFilePath);
		sparkApp.filterLogs();
	}
	 
	SparkApp(String responseTime, String destination, String srcPath ){  
		responseTimeThreshold = responseTime;  
		outputFilesPath = destination;  		
		inputFilePath = srcPath;
	}  

	public void filterLogs() throws IOException {	
		
		SparkSession spark =   SparkSession.builder()
				   .master("local")
				   .appName("App Name")
				   .config("spark.some.config.option", "some-value")
				   .getOrCreate();
		
		StructType schema = new StructType()
					.add("REQUEST_TIMESTAMP", "string")
					.add("COUNTRY_CODE", "string")
					.add("RESPONSE_TIME", "long");
		
		Dataset<Row> csvDataset = spark.read()
					   .format("csv")
					   .option("header", "true")
					   .schema(schema)
					   .load(inputFilePath);
		
		
		
		csvDataset.createOrReplaceTempView("record");
		
		List<Row> countries = spark.sql("select distinct(COUNTRY_CODE) from record").collectAsList();
		
		Row avgValue = spark.sql("select avg(RESPONSE_TIME) from record where RESPONSE_TIME > 0").collectAsList().get(0);
		averageResponseTime = avgValue.toString().replaceAll(regex, "");
		
		Dataset<Row> groupedCSVDataset = spark.sql("select * from record ").groupBy("COUNTRY_CODE").count().orderBy("COUNTRY_CODE");
		Dataset<Row> joinedDS = groupedCSVDataset.join(csvDataset, "COUNTRY_CODE").drop("count");
		String filter = "COUNTRY_CODE LIKE '%s'";		
		String fileName =  outputFilesPath+ "log-%s.csv";
		
		writeOutputLogFiles(joinedDS, countries, filter, fileName);
		
		filter = "COUNTRY_CODE LIKE '%s' and RESPONSE_TIME > " + responseTimeThreshold;	
		fileName =  outputFilesPath+"log-%s-responseTimeGreaterThan-" +responseTimeThreshold+".csv"; 
		
		writeOutputLogFiles(joinedDS, countries, filter, fileName);
		
		filter = "RESPONSE_TIME > " + averageResponseTime;	
		fileName =  outputFilesPath+"log-responseTimeGreaterThan-averageResponseTime.csv"; 
		
		writeOutputLogFiles(joinedDS, filter, fileName);
		
	}





	/**
	 * @param joinedDS
	 * @param filter
	 * @param fileName
	 */
	private void writeOutputLogFiles(Dataset<Row> joinedDS, String filter, String fileName) {
		FileWriter writer;
		Dataset<Row> countryDS = joinedDS.filter(filter);
		List<Row> result = countryDS.collectAsList(); 
		try {
			writer = new FileWriter(fileName);			
			writer.append("REQUEST_TIMESTAMP,COUNTRY_CODE,RESPONSE_TIME");
			writer.append("\n");
			for (Row s : result) { 
    		    writer.append(s.toString().replaceAll(regex, ""));
			    writer.append("\n");
			} writer.close();
		} catch (IOException e) {			
			LOG.log(Level.SEVERE, "Error occur in writing file.", e.getMessage());
		}
		
	}

	/**
	 * @param joinedDS
	 * @param countries
	 * @param filter
	 */
	private static void writeOutputLogFiles(Dataset<Row> joinedDS, List<Row> countries, String filter, String fileName) {
		
		for (Row c : countries) {
			
			String country = c.getString(0);
			String filterExp = String.format(filter, country);
			
			Dataset<Row> countryDS = joinedDS.filter(filterExp);
    		List<Row> result = countryDS.collectAsList(); 
    		
    		if(result.size()<=0)
				  continue;
    		
    		FileWriter writer;
			try {
				String newFileName;
				if(fileName.contains("%s")) {
					newFileName = String.format(fileName, country);
					writer = new FileWriter(newFileName);
				}else
					writer = new FileWriter(fileName);
				writer.append("REQUEST_TIMESTAMP,COUNTRY_CODE,RESPONSE_TIME");
				writer.append("\n");
				for (Row s : result) { 
	    		    writer.append(s.toString().replaceAll(regex, ""));
				    writer.append("\n");
				} writer.close();
			} catch (IOException e) {
				
				LOG.log(Level.SEVERE, "Error occur in writing file.", e.getMessage());
			}
    		
		
		}
		
	}

	private static void loadProperties() throws IOException {
		Properties prop = new Properties();
		String propFilePath = "src/main/resources/config.properties";
	     String[] tmpArray = propFilePath.split("/");
	     String propFileName=tmpArray[tmpArray.length - 1];
	     ClassLoader loader = Thread.currentThread().getContextClassLoader();
	     InputStream inputStream = loader.getResourceAsStream(propFileName);
		
		  if (inputStream != null)
          {
			  prop.load(inputStream);
          } 
		
		responseTimeThreshold = prop.getProperty("responseTimeThreshold");
		outputFilesPath = prop.getProperty("outputFilesPath");		
		inputFilePath = prop.getProperty("inputFilePath");
		
	}

}


