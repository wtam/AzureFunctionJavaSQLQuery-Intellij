# AzureFunctionJavaSQLQuery
For training purpose on how to develop Azure Function to query Azure SQL using Java - Intellij

# Prerequisites : 
	- https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij#set-up-your-development-environment
	- Create Azure SQL with sample DB: https://docs.microsoft.com/en-us/azure/sql-database/sql-database-single-database-get-started?tabs=azure-portal
	- (Optional) Create a KeyVault and store the db_username and db_passowrd as secrets :https://docs.microsoft.com/en-us/azure-stack/user/azure-stack-key-vault-manage-portal?view=azs-1908#create-a-secret
	
  
# Create a defalut-template Java Function using Intellij
 - https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij#create-a-functions-project
 - create a new project
        select maven and add Archetype:
	
        GroupId: com.microsoft.azure
        ArtifactId: azure-functions-archetype
        Version: 1.22 or latest:https://mvnrepository.com/artifact/com.microsoft.azure/azure-functions-archetype

        GroupId: e.g. com.azfun.java.sql
        ArtifactId: e.g. AzFunJavaSQL

# Add the code
- Paste the Fuction.java code in this repository to your Java Funtion project's Function.java
- update the Azure SQL: server, db in java code but add the db_user and db_password env var in the local.settings.json
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
modify the resource group etc on if not like the default pom.xml

After deoloy to Azure.  Add the db_user, db_password env variavle in the function configuration's Application Setting.

To use KeyVault, turn on the function's managed identity and grant the access from KeyVault by adding the managed idenitiry(objectID) and point the env var vaule to the keyvault'uri e.g. db_password and vaule = @Microsoft.KeyVault(SecretUri=keyVault's db_password uri)
  

