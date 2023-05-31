# ProgramacionAvanzada
Cuarto Ciclo de Computacion
## Indice
- [Básicos de JDBC](#semana1) 
- [Prepared Statements JDBC](#preparedstatementsjdbc) 

## Semana 1
### Java Database Connectivity (JDBC)
- API Java para interactuar con BBDD relacionales
- El pom solo tendra la base de datos H2 en este caso
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
## Semana 2
### Prepared Statements JDBC
Uso de Prepared Statements para ingresar datos
- Main
- Person: es un objeto normal con constructor, setters and getters
```java
package ec.edu.utpl.computacion.pa.semana2;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException{
        Connection con =
                DriverManager.getConnection("jdbc:h2:~/dbpag01", "root", "");

        //createTable(con);

         List<Person> people = List.of(
                new Person(0, "Tais", "Valarezo", 18),
                new Person(0, "Kevin", "Regalado", 22),
                new Person(0, "Ronin", "Carrion", 19),
                new Person(0, "Cristian", "Rodriguez", 21),
                new Person(0, "Daniel", "Flores", 20),
                new Person(0, "Jeremy", "Escudero", 21)
        );
        for(Person p : people) {
            insertData(p, con);
        }

    }
    private static void insertData(Person person, Connection con) throws SQLException{
        var insert = """
                INSERT INTO REGISTRARION VALUES (?, ?, ?, ?)
                """;
        try(PreparedStatement pstmt = con.prepareStatement(insert)) {

            int maxId = getId(con);
            pstmt.setInt(1, maxId + 1);
            pstmt.setString(2, person.getName());
            pstmt.setString(3, person.getLast_name());
            pstmt.setInt(4, person.getAge());

            pstmt.executeUpdate();
        }
    }

    private static int getId(Connection con) throws SQLException{
        var select = """
                SELECT MAX(ID) FROM REGISTRARION
                """;
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(select)) {

            rs.next();
            return rs.getInt("MAX(ID)");

        }
    }

    private static void createTable(Connection con) throws SQLException {
        try (Statement stat = con.createStatement()) { //Try con recursos
            var sqlCreateTable = """
                DROP TABLE REGISTRARION IF EXISTS;
                CREATE TABLE REGISTRARION(
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
### Taller Grupal JPA Semana 3
- POM XML
```html
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ec.edu.utpl.computacion.pa</groupId>
    <artifactId>Semana2</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/com.h2database/h2 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>2.1.214</version>
        </dependency>
        <dependency>
            <groupId>org.eclipse.persistence</groupId>
            <artifactId>javax.persistence</artifactId>
            <version>2.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.6.10.Final</version>
        </dependency>

    </dependencies>

</project>
```
- Persistence XML
```html
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
  http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">
    <persistence-unit name="pu-pa">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:~/DBPATG1" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password"
                      value="123" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.dialect"
                      value="org.hibernate.dialect.H2Dialect" />
        </properties>
    </persistence-unit>
</persistence>
```
#### - Registration Controller
```java
package ec.edu.utpl.computacion.pa.controller;

import ec.edu.utpl.computacion.pa.model.VehiculoDTO;
import ec.edu.utpl.computacion.pa.model.Vehiculo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class RegistrationController {
    private static EntityManager em = null;

    public RegistrationController() {
        getEm("pu-pa");
    }

    private void getEm(String puName) {
        if(em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
            em = emf.createEntityManager();
        }
    }

    /*public boolean addVehiculo(VehiculoDTO register) {

        Vehiculo newVehiculo = new Vehiculo(register.id(), register.firstName(), register.lastName(), register.age());
        em.getTransaction().begin();
        em.persist(newVehiculo);
        em.flush();
        em.getTransaction().commit();

        return true;
    }*/

    public List<VehiculoDTO> getAllPeople() {
        TypedQuery<VehiculoDTO> qryAll = em.createQuery("""
                SELECT new ec.edu.utpl.computacion.pa.model.VehiculoDTO(v.id,
                                                                  v.placa,
                                                                  v.anio_fabricacion,
                                                                  v.nro_chasis,
                                                                  v.nro_motor,
                                                                  v.color1,
                                                                  v.color2,
                                                                  v.avaluo,
                                                                  v.prendado)
                FROM Vehiculo v
                """, VehiculoDTO.class);

        return qryAll.getResultList();

    }

    public void close() {
        em.close();
    }
}
```
#### - Vehiculo
```java
package ec.edu.utpl.computacion.pa.model;

import com.sun.istack.NotNull;

import javax.persistence.*;


@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "PLACA", length = 9)
    private String placa;
    @NotNull
    private Integer anio_fabricacion;
    @NotNull
    @Column(name = "NRO_CHASIS", length = 32)
    private String nro_chasis;
    @NotNull
    @Column(name = "NRO_MOTOR", length = 32)
    private String nro_motor;
    @NotNull
    @Column(name = "COLOR1", length = 16)
    private String color1;
    @NotNull
    @Column(name = "COLOR2", length = 16)
    private String color2;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double avaluo;
    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'")
    private boolean prendado;

    public Vehiculo() {
    }

    public Vehiculo(Integer id, String placa, Integer anio_fabricacion, String nro_chasis, String nro_motor,
                    String color1, String color2, Double avaluo, boolean prendado) {
        this.id = id;
        this.placa = placa;
        this.anio_fabricacion = anio_fabricacion;
        this.nro_chasis = nro_chasis;
        this.nro_motor = nro_motor;
        this.color1 = color1;
        this.color2 = color2;
        this.avaluo = avaluo;
        this.prendado = prendado;
    }
}
```
#### - VehiculoDTO
```java
package ec.edu.utpl.computacion.pa.model;

public record VehiculoDTO(Integer id, String placa, Integer anio_fabricacion, String nro_chasis, String nro_motor,
                       String color1, String color2, Double avaluo, boolean prendado) {
}
```
#### - Main
```java
package ec.edu.utpl.computacion.pa.view;

import ec.edu.utpl.computacion.pa.controller.RegistrationController;
import ec.edu.utpl.computacion.pa.model.VehiculoDTO;

public class ViewCMD {
    public static void main(String[] args) {
        RegistrationController database = new RegistrationController();

        //database.addVehiculo(new VehiculoDTO(1002, "Verónica", "Segarra", 39));

        for(var vehiculo : database.getAllPeople()) {
            System.out.printf("%d - %s - %d - %s - %s - %s - %s - %.2f - %b%n",
                    vehiculo.id(),
                    vehiculo.placa(),
                    vehiculo.anio_fabricacion(),
                    vehiculo.nro_chasis(),
                    vehiculo.nro_motor(),
                    vehiculo.color1(),
                    vehiculo.color2(),
                    vehiculo.avaluo(),
                    vehiculo.prendado());
        }

        database.close();
    }
}
```
