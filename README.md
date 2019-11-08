# AzureFunctionJavaSQLQuery
For training purpose on how to develop Azure Function to query Azure SQL using Java - Intellij

# Prerequisites : 
	- https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij#set-up-your-development-environment
	- Creat Azure SQL with sample DB: https://docs.microsoft.com/en-us/azure/sql-database/sql-database-single-database-get-started?tabs=azure-portal
  
# Create a defalut-template Java Function using Intellij
 - https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij#create-a-functions-project
 - create a new project
        select maven and add Archetype:
	
        GroupId: com.microsoft.azure
	
        ArtifactId: azure-functions-archetype
	
        Version: 1.22 or latest: https://mvnrepository.com/artifact/com.microsoft.azure/azure-functions-archetype
        
	
        GroupId: e.g. com.azfun.java.sql
        ArtifactId: e.g. AzFunJavaSQL

# Add the code
- Paste the Fuction.java code in this repository to your Java Funtion project's Function.java
- update the Azure SQL: server, db, user and password
- add below SQL JDBC driver dependency to pom.xml

           <dependency>
	   
               <groupId>com.microsoft.sqlserver</groupId>
	       
               <artifactId>mssql-jdbc</artifactId>
	       
               <version>7.0.0.jre8</version>
	       
           </dependency>
          
# Build and Run
- Maven Projects
    Run Maven build from Lifecycle/package
    Run the function from Plugins/azure-functions:run   [Note: whitelist the client IP on Azure SQL Firewal]
    
# Deploy to Azure
https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij#deploy-the-function-to-azure
  

