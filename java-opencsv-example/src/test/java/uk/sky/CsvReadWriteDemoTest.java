/**
 * 
 */
package uk.sky;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * @author supri
 *
 */
class CsvReadWriteDemoTest {

		
		static String limit; 
		static String outputFilesPath; 	  	
		static String inputFilePath;
		
		@BeforeEach
	    public void beforeEachTestMethod() throws IOException {
			loadProperties();
	    }
		
		
		@Test
		public void testFilterLogs() throws IOException {
			
			CsvReadWriteDemo demo = new CsvReadWriteDemo(limit, outputFilesPath, inputFilePath);
			demo.filterLogs();
			
			String fileName = outputFilesPath+"log-DE.csv";
			File f1 = new File(fileName);
			assertTrue(f1.exists());
			assertEquals(2, countLineNumberReader(fileName));
			
			fileName = outputFilesPath+"log-GB.csv";
			File f2 = new File(fileName); 
			assertTrue(f2.exists());
			assertEquals(2, countLineNumberReader(fileName));
			
			fileName = outputFilesPath+"log-US.csv";
			File f3 = new File(fileName);      
			assertTrue(f3.exists());
			assertEquals(4, countLineNumberReader(fileName));
			
			fileName = outputFilesPath+"log-US-responseTimeGreaterThan-600.0.csv";
			File f4 = new File(fileName);      
			assertTrue(f4.exists());
			assertEquals(3, countLineNumberReader(fileName));
			
			fileName = outputFilesPath+"log-ResponseTimeAboveAverage.csv";
			File f5 = new File(fileName);
			assertTrue(f5.exists());
			assertEquals(4, countLineNumberReader(fileName));
			
			
			
			
		}
		
		public static long countLineNumberReader(String fileName) {

		      File file = new File(fileName);
		      long lines = 0;
		      try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
		          while (lnr.readLine() != null) ;
		          lines = lnr.getLineNumber();
		      } catch (IOException e) {
		    	  Logger.getLogger(CsvReadWriteDemoTest.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		      }
		      return lines;

		  }
		
		
		/**
		 * 
		 */
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
			
			limit = prop.getProperty("limit");
			outputFilesPath = prop.getProperty("outputFilesPath");		
			inputFilePath = prop.getProperty("inputFilePath");
			
		}

	}

