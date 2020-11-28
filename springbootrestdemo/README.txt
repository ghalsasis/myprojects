# SpringBoot Rest API Task for XDesign

This project is a springboot rest api implementation for XDesign task to query provided data on Munro in Scotland.

Dependencies:
  - Java Version 1.8
  - Maven 3.x
  - Spring Boot 2.3.0.M2
  - H2 Database

# How to run
  - Clone this repository or extract the source code
  - Make sure Java 1.8 and Maven 3.x is installed
  - Build the project using mvn clean package
  - Execute: java -jar target/springbootdemo-0.0.1-SNAPSHOT.jar to start the SpringBoot Application and Embedded Tomcat Server
  - Retrieve the data loaded into H2 Database by use of appropriate API in HTTP Request
  - Check the stdout log to make sure no exceptions are thrown

# About the application code  
 - Data Ingestion
     - Create a Table with columns for relevant data fields (src/main/resources/schema.sql)
     - Create Insert statements from the given input for relevant data fields (src/main/resources/data.sql)
  - H2 Database Configuration:
    Set the following in src/main/resources/application.properties
        - spring.datasource.url=jdbc:h2:file:/src/main/resources/data.sql
        - spring.datasource.driverClassName=org.h2.Driver
        - spring.datasource.username=sa
        - spring.datasource.password=sa
        - spring.jpa.hibernate.ddl-auto=none
        - spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  
  - REST API:
      The REST controller provides following api to query data:
        - /munro/findall: Provides details of all data i.e. all rows
        - /munro/findallnonblankcategory : excludes those with blank in the column POST1997 i.e category 
        - /munro/findAllSortByNameAsc : find all sort by name ascending
        - /munro/findAllSortByNameDesc : find all sort by name descending
        - /munro/findAllSortByHeightMAsc : find all sort by height in Meter ascending
        - /munro/findAllSortByNameDesc : find all sort by height in Meter descending
        - /munro/findallSorted: This api takes 5 parameters, when values for these 5 parameters are not provided, default values are used.
            - sortByField : field to be sorted (default = name)
            - sortDirection : asc for ascending or desc for descending (default = ascending)
            - maxHeight : maximum height in meter for a Munro (Default = Max Double Value)
            - minHeight : minimum height in meter for a Munro (Default = 0.0)
            - size : number of datarows in the result (Default = 10)
        Examples:
              - /munro/findallSorted?sortByField=name&sortDirection=desc&maxHeight=1199.0&minHeight=800.0&size=20
              - /munro/findallSorted?sortByField=&sortDirection=&maxHeight=&minHeight=&size=

  Incorrect specification of API leads to IncorrectAPIException and appropriate error message in HTTP Response.

  UNIT Tests:
  testLoadDataForTestClass: Can be executed during maven build, this tests dataload into H2 database.
  testGetMunroListSuccess and testGetMunroSortedListSuccess : Can be executed after the application is started and server is listening to port 8080

  Future Enhancements:
   - Improved JUNIT Testing using BDD or RestAssured Framework
   - Sorting of the data by multiple fields instead of one
  
Questions and Comments: supriya.ghalsasi@outlook.com