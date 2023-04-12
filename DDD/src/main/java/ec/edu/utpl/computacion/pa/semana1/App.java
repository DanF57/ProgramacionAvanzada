package ec.edu.utpl.computacion.pa.semana1;

import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception{
        Connection con =
                DriverManager.getConnection("jdbc:h2:~/dbpa", "root", "");
        //1. Crear la talbla
        //createTable(con);

        //2. Insertar los datos
        //insertData(con);

        //3. Ver los datos
        getData(con);
        con.close();
    }

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
