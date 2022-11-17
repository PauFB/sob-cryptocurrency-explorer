<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import = "java.sql.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database SQL Load</title>
    </head>
    <style>
        .error {
            color: red;
        }
        pre {
            color: green;
        }
    </style>
    <body>
        <h2>Database SQL Load</h2>
        <%
            /* How to customize:
             * 1. Update the database name on dbname.
             * 2. Create the list of tables, under tablenames[].
             * 3. Create the list of table definition, under tables[].
             * 4. Create the data into the above table, under data[]. 
             * 
             * If there is any problem, it will exit at the very first error.
             */
            String dbname = "homework1";
            String schema = "ROOT";
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            /* this will generate database if not exist */
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname, "root", "root");
            Statement stmt = con.createStatement();
            
            /* inserting data */
            /* you have to exclude the id autogenerated from the list of columns if you have use it. */
            String data[] = new String[]{
                "INSERT INTO " + schema + ".CREDENTIALS VALUES (NEXT VALUE FOR CREDENTIALS_GEN, 'sob', 'sob')",
                "INSERT INTO " + schema + ".CUSTOMER VALUES (NEXT VALUE FOR CUSTOMER_GEN, 'admin', 'admin', 'admin', '000000000')",
                "INSERT INTO " + schema + ".CUSTOMER VALUES (NEXT VALUE FOR CUSTOMER_GEN, 'address1@nonexistent.com', 'Juan Martin', 'password1234', '726184627')",
                "INSERT INTO " + schema + ".CUSTOMER VALUES (NEXT VALUE FOR CUSTOMER_GEN, 'address2@nonexistent.com', 'Pedro Losano', 'password1234', '817364526')",
                "INSERT INTO " + schema + ".CUSTOMER VALUES (NEXT VALUE FOR CUSTOMER_GEN, 'address3@nonexistent.com', 'Jaime Perez', 'password1234', '847163261')",
                "INSERT INTO " + schema + ".CUSTOMER VALUES (NEXT VALUE FOR CUSTOMER_GEN, 'address4@nonexistent.com', 'Andres Silva', 'password1234', '172846127')",
                "INSERT INTO " + schema + ".COIN VALUES (NEXT VALUE FOR COIN_GEN, 'Generic description.', 'Bitcoin', 500000.0, '2000-1-1-12.00.00.000000')",
                "INSERT INTO " + schema + ".COIN VALUES (NEXT VALUE FOR COIN_GEN, 'Generic description.', 'ethereum', 30000.0, '2000-1-1-12.00.00.000000')",
                "INSERT INTO " + schema + ".COIN VALUES (NEXT VALUE FOR COIN_GEN, 'Generic description.', 'NicoCoin', 400000.0, '2000-1-1-12.00.00.000000')",
                "INSERT INTO " + schema + ".COIN VALUES (NEXT VALUE FOR COIN_GEN, 'Generic description.', 'PauCoin', 600000.0, '2000-1-1-12.00.00.000000')",
            };
            for (String datum : data) {
                if (stmt.executeUpdate(datum)<=0) {
                    out.println("<span class='error'>Error inserting data: " + datum + "</span>");
                    return;
                }
                out.println("<pre> -> " + datum + "<pre>");
            }
        %>
        <button onclick="window.location='<%=request.getSession().getServletContext().getContextPath()%>'">Go home</button>
    </body>
</html>
