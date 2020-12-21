/**
 * 
 */
package uk.sky;

import java.io.BufferedReader;
/**
 * @author supri
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CsvReadWriteDemo {
	private static String limit;
	private static String outputFilesPath;
	private static String inputFilePath;

	/**
	 * @param limit2
	 * @param outputFilesPath2
	 * @param inputFilePath2
	 */
	public CsvReadWriteDemo(String limit2, String outFilesPath, String inFilePath) {
		limit = limit2;
		outputFilesPath = outFilesPath;
		inputFilePath = inFilePath;
	}

	public static void main(String[] args) throws IOException {
		loadProperties();
		CsvReadWriteDemo demo = new CsvReadWriteDemo(limit, outputFilesPath, inputFilePath);
		demo.filterLogs();
	}

	public void filterLogs() throws IOException {

		Path myPath = Paths.get(inputFilePath);
		BufferedReader source = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);

		LogFilterService service = new LogFilterService();
		List<LogRecord> records = service.getAllRecords(source);

		Map<String, List<LogRecord>> mapLogsByCountry = (Map<String, List<LogRecord>>) service.filterByCountry(records);
		service.WriteFilteredLogs(mapLogsByCountry, outputFilesPath + "log-%s.csv");

		Map<String, List<LogRecord>> mapLogsByCountryAndLimit = service
				.filterByCountryWithResponseTimeAboveLimit(records, Double.parseDouble(limit));
		service.WriteFilteredLogs(mapLogsByCountryAndLimit,
				outputFilesPath + "log-%s-responseTimeGreaterThan-" + limit + ".csv");

		String outFileNameAboveAverage = outputFilesPath + "log-ResponseTimeAboveAverage.csv";
		List<LogRecord> listLogsByResponseTime = service.filterByResponseTimeAboveAverage(records);
		service.writeRecords(listLogsByResponseTime, outFileNameAboveAverage);
	}

	private static void loadProperties() throws IOException {
		Properties prop = new Properties();
		String propFilePath = "src/main/resources/config.properties";
		String[] tmpArray = propFilePath.split("/");
		String propFileName = tmpArray[tmpArray.length - 1];
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = loader.getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		}

		limit = prop.getProperty("limit");
		outputFilesPath = prop.getProperty("outputFilesPath");
		inputFilePath = prop.getProperty("inputFilePath");

	}

}
