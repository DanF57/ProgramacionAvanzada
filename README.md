# ProgramacionAvanzada
Cuarto Ciclo de Computacion

## Semana 1
### Java Database Connectivity (JDBC)
- API Java para interactuar con BBDD relacionales

Elementos basicos JDBC
- Driver base de datos
- Conexion
- Sentencias --> Consulta(Select) - Actualizacion(insert, delete, update)
- Resultados

```java
Connection con = DriverManager.geConnection(urlDB, "root", "");

Statement stat = con.createStatement();

stat.executeQuery(sql)

stat.executeUpdate(sql)

ResultSet rs = stat.executeQuery(sql)
```
Buscar en maven repository el driver necesario

### Métodos JDBC
```java
package ec.edu.utpl.computacion.pa.semana1;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception{
        Connection con =
                DriverManager.getConnection("jdbc:h2:~/dbpa", "root", "");
        //1. Crear la talbla
        //createTable(con);

        //2. Insertar los datos
        //insertData(con);

        //3. Ver los datos
        //getData(con);

        //4. Calcular promedio edades
        //System.out.println(average(con));

        //5. Nombres con J
        //getSpecificNames(con);

        //6. Buscar por ID
        Scanner lector = new Scanner(System.in);
        System.out.println("Ingrese el ID: ");
        String id2Search = lector.nextLine();
        searchById(id2Search, con);
        con.close();
    }
```
#### 1. Crear Tablas
```java
    private static void createTable(Connection con) throws SQLException {
        try (Statement stat = con.createStatement()) { //Try con recursos
            var sqlCreateTable = """
                CREATE TABLE IF NOT EXISTS REGISTRARION(
                ID INTEGER NOT NULL,
                FIRST_NAME VARCHAR(255),
                LAST_NAME VARCHAR(255),
                AGE INTEGER,
                CONSTRAINT REGISTRATION_pkey PRIMARY KEY (ID)
                );
                """;

            stat.executeUpdate(sqlCreateTable);
            System.out.println("Tabla Creada");
        }

    }
}
```
#### 2. Insertar Datos
```java
    private static void insertData(Connection con) throws SQLException{
        var data = """
                INSERT INTO REGISTRARION VALUES(1, 'DANIEL', 'FLORES', 20);
                INSERT INTO REGISTRARION VALUES(2, 'JJ', 'CIFUENTES', 30);
                INSERT INTO REGISTRARION VALUES(3, 'LIONEL', 'MESSI', 35);
                INSERT INTO REGISTRARION VALUES(4, 'JUAN', 'MATA', 37);
                INSERT INTO REGISTRARION VALUES(5, 'VINICIUS', 'JR', 23);
                INSERT INTO REGISTRARION VALUES(6, 'ENNER', 'VALENCIA', 32);             
                """;
        try(Statement stmt = con.createStatement()){
            int count = stmt.executeUpdate(data);
            System.out.println(count);
        }

    }
```
#### 3. Obtener Data
```java
    private static void getData(Connection con) throws SQLException{
        String select = "SELECT ID, FIRST_NAME, LAST_NAME, AGE FROM REGISTRARION";
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(select)
        ) {
            while (rs.next()) {
                System.out.printf("%d - %s %s (%d)\n",
                        rs.getInt("ID"),
                        rs.getString("LAST_NAME"),
                        rs.getString("FIRST_NAME"),
                        rs.getInt("AGE"));
            }
        }
    }
```
#### 4. Calcular Promedio
```java
    private static double average(Connection con) throws SQLException{

        String select = "SELECT AVG(AGE) FROM REGISTRARION";
        double output = -1;
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(select)
        ){
            rs.next();
            output = rs.getDouble("AVG(AGE)");
        }
        return output;
    }
```
#### 5. Imprimir Nombre Específico
```java
    private static void getSpecificNames(Connection con) throws SQLException{
        String select = """
                  SELECT FIRST_NAME 
                  FROM REGISTRARION 
                  WHERE FIRST_NAME LIKE 'J%'
                        """;
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(select)
        ) {
            while (rs.next()) {
                System.out.printf("%s\n",
                        rs.getString("FIRST_NAME"));
            }
        }
    }
```
#### 6. Buscar por ID
```java
    private static void searchById(String id, Connection con) throws SQLException{
        var select = """
        SELECT ID, FIRST_NAME, LAST_NAME, AGE 
        FROM REGISTRARION
        WHERE ID = ?
        """;
        try(PreparedStatement pStmt = con.prepareStatement(select)){
            pStmt.setString(1, id);
            try(ResultSet rs = pStmt.executeQuery()){
                while (rs.next()) {
                    System.out.printf("%d - %s %s (%d)\n",
                        rs.getInt("ID"),
                        rs.getString("LAST_NAME"),
                        rs.getString("FIRST_NAME"),
                        rs.getInt("AGE"));
                }
            }
        }
    }
```
