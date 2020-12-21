This project is a java implementation for task to filter given csv logs

This project assumes that this code would be used to process large input csv log file.
 
Dependencies:
  - Java Version 11
  - Maven 3.x
  
How to run:
  - Clone this repository or extract the source code (example c:\projects)
  - Make sure Java 11 and Maven 3.x is installed
  - Build project using - mvn -DskipTests clean package
  - the command mvn clean install will compile source and execute test as well
  
 Configuration:
 	- Update src/main/resources/config.properties to specify the parameters:
 		-- limit=600.0 => threshold for response time. logs having response time more than this threshold should be filtered out.
		-- outputFilesPath=src/main/resources/com/example/output/ => destination location for all filtered output log files
		-- inputFilePath=src/main/resources/com/example/sample-extract => source location for the input csv log file
  - !! Ensure that input file and the output folder exist on the file system at the specified path !!
  
  This project writes the filtered logs to output csv file using FileWriter
  It creates following filtered output files:
  a) Task - 1: filter log extracts by country
  		-- destination-folder/log-<COUNTRY_CODE>.csv		
  b) Task - 2: filter log extracts by country and response time was above a certain limit
  		-- destination-folder/log-<COUNTRY_CODE>-responseTimeGreaterThan-<Limit>.csv			
  c) Task - 3: filter log extracts by response time is above average
 		-- destination-folder/log-ResponseTimeAboveAverage.csv
 		
 Tests:
 	For the given file: sample-extract.csv and responsetime threshold of 600
 	this program should generate following logfiles :
 	- log-DE.csv - filtered output only for country = DE, containing header + 1 row
 	- log-GB.csv - filtered output only for country = GB, containing header + 1 row
 	- log-US.csv - filtered output only for country = US, containing header + 4 rows 	
 	- log-US-responseTimeGreaterThan-600.0.csv - filtered output only for country = US, containing header + 2 rows
 	- log-ResponseTimeAboveAverage.csv - filtered output containing header + 3 rows
 	
Questions and Comments: supriya.ghalsasi@outlook.com