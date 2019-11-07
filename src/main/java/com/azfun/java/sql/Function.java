package com.azfun.java.sql;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.util.List;
import java.util.ArrayList;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke function deployed to Azure.
     * More details: https://aka.ms/functions_authorization_keys
     */
    @FunctionName("HttpTrigger-Java-SQL")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("SQL");
        String userInput = request.getBody().orElse(query);
        String sqlStatement = "SELECT TOP 20 pc.Name as CategoryName, p.name as ProductName "
                + "FROM [SalesLT].[ProductCategory] pc "
                + "JOIN [SalesLT].[Product] p ON pc.productcategoryid = p.productcategoryid";
        //String[] queryResult = null;
        List<String> queryResults = new ArrayList<String>();

        if (userInput == null) {
            //return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
            //use default sql statement
        } else {
            //return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
            //e.g. http://localhost:7071/api/HttpTrigger-Java-SQL?SQL=select * from SalesLT.ProductCategory
            sqlStatement = userInput;
        }

        // Connect to database
        String hostName = "SQL Server name"; // update me
        String dbName = "DB name"; // update me
        String user = "user"; // update me
        String password = "password"; // update me
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
                + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();
            context.getLogger().info("Successful connection - Schema: " + schema);
            context.getLogger().info("=========================================");

            // Execute SQL statement.
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlStatement)) {

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                final int columnCount = resultSetMetaData.getColumnCount();

                while (resultSet.next())
                {
                    //context.getLogger().info(resultSet.getString(1) + " " + resultSet.getString(2));
                    Object[] values = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        values[i - 1] = resultSet.getString(i);
                    }
                    context.getLogger().info("Each row" + Arrays.toString(values));
                    queryResults.add(Arrays.toString(values));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return request.createResponseBuilder(HttpStatus.OK).body("Result: " + queryResults).build();
    }
}
