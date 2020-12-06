# SpringBoot Rest API Task for Scalable Capital

This project is a springboot rest api implementation for task to provide a foreign exchange rate service

Dependencies:
  - Java Version 1.8
  - Maven 3.x
  - Spring Boot 2.4.0
  - H2 Database

# How to run
  - Clone this repository or extract the source code (example c:\projects)
  - Make sure Java 1.8 and Maven 3.x is installed
  - Build the project using mvn clean package
  - Make sure that /src/main/resources/data.sql is present at path mentioned in src/main/resources/application.properties file
  - Execute: java -jar target/Demo-Exchange-Rate-Service-0.0.1-SNAPSHOT.jar to start the SpringBoot Application and Embedded Tomcat Server
  - Retrieve the data loaded into H2 Database by use of appropriate API in HTTP Request
  - Check the stdout log to make sure no exceptions are thrown

# About the application code  
 - Data Ingestion
     - Application creates a Table as per schema in src/main/resources/schema.sql
     - Application loads data as per script in src/main/resources/data.sql
  - H2 Database Configuration:
    Setup in src/main/resources/application.properties
        - spring.datasource.url=jdbc:h2:mem:testdb:file:C:/projects/Demo-Exchange-Rate-Service/src/main/resources/data.sql
        - spring.datasource.driverClassName=org.h2.Driver
        - spring.datasource.username=sa
        - spring.datasource.password=
        - spring.jpa.hibernate.ddl-auto=none
        - spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  
  - REST API:
      The REST controller provides following api to query data:
        - /ecbrefxchgrate/findall: Provides all data 
        - /ecbrefxchgrate/findallcurrencies : Provides list of all currencies including EUR
        - /ecbrefxchgrate/findallcurrenciesCounter: A counter that specifies how many times the api /ecbrefxchgrate/findallcurrencies was called
        - /ecbrefxchgrate/{currency}: provides exchange rate for the currency considering EUR as base
        - /ecbfxrate/{currency1}-{currency2}: provides exchange rate for given pair of currencies
        - /ecbrefxchgrate/ConvertAmountByCurrency?fromCur={currency1}&fromAmount={amount}&toCur={currency2}: 
										Provides amount converted from source to destination currency
        - /getLinktoFxCharts/{currency1}-{currency2}: provides link to other webpage which shows FX charts for the given currency pair
        Examples:
	      - http://localhost:8080/ecbrefxchgrate/gbp
              - http://localhost:8080/ecbrefxchgrate/ConvertAmountByCurrency?fromCur=EUR&fromAmount=10&toCur=GBP
              - http://localhost:8080/getLinktoFxCharts/usd-aud

  - Error Handling:
     - Incorrect specification of API leads to IncorrectAPIException and appropriate error message in Response.
     - Incorrect format of input amount in the api /ecbrefxchgrate/ConvertAmountByCurrency leads to appropriate error message in Response

   - Health check: The endpoint /actuator/health provides health check i.e. Status:UP 

  UNIT Tests:
  	- testFindAll
	- testFindAllCurrencies
	- testXchgRateForCurrency
	- testFxRate

  USER Testing Screenshots provided in the folder: Demo-Exchange-Rate-Service/documents

  Future Enhancements:
   - Improved JUNIT Testing by direct URI invocation using WebMVC Framework

  
Questions and Comments: supriya.ghalsasi@outlook.com