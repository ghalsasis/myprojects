<h1 class="code-line" data-line-start=0 data-line-end=1 ><a id="SpringBoot_Rest_API_Task_for_XDesign_0"></a>SpringBoot Rest API Task for XDesign</h1>
<p class="has-line-data" data-line-start="2" data-line-end="3">This project is a springboot rest api implementation for XDesign task to query provided data on Munro in Scotland.</p>
<p class="has-line-data" data-line-start="4" data-line-end="5">Dependencies:</p>
<ul>
<li class="has-line-data" data-line-start="5" data-line-end="6">Java Version 1.8</li>
<li class="has-line-data" data-line-start="6" data-line-end="7">Maven 3.x</li>
<li class="has-line-data" data-line-start="7" data-line-end="8">Spring Boot 2.3.0.M2</li>
<li class="has-line-data" data-line-start="8" data-line-end="10">H2 Database</li>
</ul>
<h1 class="code-line" data-line-start=10 data-line-end=11 ><a id="How_to_run_10"></a>How to run</h1>
<ul>
<li class="has-line-data" data-line-start="11" data-line-end="12">Clone this repository or extract the source code</li>
<li class="has-line-data" data-line-start="12" data-line-end="13">Make sure Java 1.8 and Maven 3.x is installed</li>
<li class="has-line-data" data-line-start="13" data-line-end="14">Build the project using mvn clean package</li>
<li class="has-line-data" data-line-start="14" data-line-end="15">Execute: java -jar target/springbootdemo-0.0.1-SNAPSHOT.jar to start the SpringBoot Application and Embedded Tomcat Server</li>
<li class="has-line-data" data-line-start="15" data-line-end="16">Retrieve the data loaded into H2 Database by use of appropriate API in HTTP Request</li>
<li class="has-line-data" data-line-start="16" data-line-end="18">Check the stdout log to make sure no exceptions are thrown</li>
</ul>
<h1 class="code-line" data-line-start=18 data-line-end=19 ><a id="About_the_application_code_18"></a>About the application code</h1>
<ul>
<li class="has-line-data" data-line-start="19" data-line-end="22">
<p class="has-line-data" data-line-start="19" data-line-end="20">Data Ingestion</p>
<ul>
<li class="has-line-data" data-line-start="20" data-line-end="21">Create a Table with columns for relevant data fields (src/main/resources/schema.sql)</li>
<li class="has-line-data" data-line-start="21" data-line-end="22">Create Insert statements from the given input for relevant data fields (src/main/resources/data.sql)</li>
</ul>
</li>
<li class="has-line-data" data-line-start="22" data-line-end="31">
<p class="has-line-data" data-line-start="22" data-line-end="30">H2 Database Configuration:<br>
Set the following in src/main/resources/application.properties<br>
- spring.datasource.url=jdbc:h2:file:/src/main/resources/data.sql<br>
- spring.datasource.driverClassName=org.h2.Driver<br>
- spring.datasource.username=sa<br>
- spring.datasource.password=sa<br>
- spring.jpa.hibernate.ddl-auto=none<br>
- spring.jpa.database-platform=org.hibernate.dialect.H2Dialect</p>
</li>
<li class="has-line-data" data-line-start="31" data-line-end="49">
<p class="has-line-data" data-line-start="31" data-line-end="48">REST API:<br>
The REST controller provides following api to query data:<br>
- /munro/findall: Provides details of all data i.e. all rows<br>
- /munro/findallnonblankcategory : excludes those with blank in the column POST1997 i.e category<br>
- /munro/findAllSortByNameAsc : find all sort by name ascending<br>
- /munro/findAllSortByNameDesc : find all sort by name descending<br>
- /munro/findAllSortByHeightMAsc : find all sort by height in Meter ascending<br>
- /munro/findAllSortByNameDesc : find all sort by height in Meter descending<br>
- /munro/findallSorted: This api takes 5 parameters, when values for these 5 parameters are not provided, default values are used.<br>
- sortByField : field to be sorted (default = name)<br>
- sortDirection : asc for ascending or desc for descending (default = ascending)<br>
- maxHeight : maximum height in meter for a Munro (Default = Max Double Value)<br>
- minHeight : minimum height in meter for a Munro (Default = 0.0)<br>
- size : number of datarows in the result (Default = 10)<br>
Examples:<br>
- /munro/findallSorted?sortByField=name&amp;sortDirection=desc&amp;maxHeight=1199.0&amp;minHeight=800.0&amp;size=20<br>
- /munro/findallSorted?sortByField=&amp;sortDirection=&amp;maxHeight=&amp;minHeight=&amp;size=</p>
</li>
</ul>
<p class="has-line-data" data-line-start="49" data-line-end="50">Incorrect specification of API leads to IncorrectAPIException and appropriate error message in HTTP Response.</p>
<p class="has-line-data" data-line-start="51" data-line-end="54">UNIT Tests:<br>
testLoadDataForTestClass: Can be executed during maven build, this tests dataload into H2 database.<br>
testGetMunroListSuccess and testGetMunroSortedListSuccess : Can be executed after the application is started and server is listening to port 8080</p>
<p class="has-line-data" data-line-start="55" data-line-end="56">Future Enhancements:</p>
<ul>
<li class="has-line-data" data-line-start="56" data-line-end="57">Improved JUNIT Testing using BDD or RestAssured Framework</li>
<li class="has-line-data" data-line-start="57" data-line-end="59">Sorting of the data by multiple fields instead of one</li>
</ul>
<p class="has-line-data" data-line-start="59" data-line-end="60">Questions and Comments: <a href="mailto:supriya.ghalsasi@outlook.com">supriya.ghalsasi@outlook.com</a></p>