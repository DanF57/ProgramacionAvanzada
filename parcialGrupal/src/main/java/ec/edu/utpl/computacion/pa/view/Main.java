package ec.edu.utpl.computacion.pa.view;

import ec.edu.utpl.computacion.pa.controller.Controller;
import ec.edu.utpl.computacion.pa.controller.InsertAsambleistas;
import ec.edu.utpl.computacion.pa.controller.Simulacion;
import ec.edu.utpl.computacion.pa.model.*;

import java.sql.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        //Creacion Tablas JPA
        Controller c = new Controller();
        int cantidad = 137;

        //Lista de Asambleistas
        ArrayList<Asambleista> lista = createRandomAsambleistas(cantidad);
        //Insercion de Asambleistas con JDBC
        Thread thread1 = new Thread(new InsertAsambleistas(lista));
        thread1.start();
        thread1.join();

        //Lista con todos los asambleistas en la base
        List<Asambleista> asambleistas = c.getAllAsambleista();
        //Lista de Hilos
        List<Thread> hilos = new ArrayList<>();

        Simulacion s = new Simulacion();
        s.setCon(c);
        s.setCantidadAsambleistas(cantidad);

        //Creacion de Objetos Simulacion en base a la cantidad de Asambleistas
        for (Asambleista asambleista: asambleistas) {
            Simulacion sA = new Simulacion();
            sA.setA(asambleista);
            Thread thread = new Thread(sA);
            hilos.add(thread);
        }

        for (Thread hilo: hilos ){
            hilo.start();
        }

        for (Thread thread: hilos) {
            thread.join();
        }

        System.out.println("Terminado la simulacion");
        c.close();
        System.out.println("Presentación de resultados");
        resumenVotos();
        System.out.println("-------------------------");
        System.out.println("---------------------------------------");
        resumenAsambleistaVotos();
        System.out.println("---------------------------------------");
    }

    public static void resumenVotos() {
        // Datos de conexión a la base de datos
        String url = "jdbc:h2:C:/Users/Daniel/dbasambleapa";
        String username = "root";
        String password = "123";

        // Establecer conexión
        try (Connection conexion = DriverManager.getConnection(url, username, password)) {
            // Crear una declaración SQL
            Statement statement = conexion.createStatement();

            // Consulta para obtener información de Asambleista, Votacion y Presente
            String consulta = "SELECT v.voto, COUNT(*) AS cantidad " +
                    "FROM Asambleista a " +
                    "JOIN Votacion v ON a.id = v.asambleista_id " +
                    "GROUP BY v.voto " +
                    "ORDER BY v.voto";

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery(consulta);

            // Imprimir la tabla de resultados
            System.out.println("|    VOTO    |  CANTIDAD  |");
            System.out.println("|------------|------------|");

            // Variables para almacenar el total de votos
            int total = 0;

            // Recorrer los resultados
            while (resultSet.next()) {
                // Obtener los datos de las columnas
                String voto = resultSet.getString("voto");
                int cantidad = resultSet.getInt("cantidad");

                // Imprimir los resultados
                if (voto.equals("NO") || voto.equals("SI")){
                    System.out.println("| " + voto + "         |     " + cantidad + "     |");
                } else {
                    System.out.println("| " + voto + " |     " + cantidad + "     |");
                }
                // Sumar al total de votos
                total += cantidad;
            }

            // Presentar el total de votos
            System.out.println("| TOTAL      |     " + total + "   |");

            // Cerrar el ResultSet, la declaración y la conexión
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resumenAsambleistaVotos() {
        // Datos de conexión a la base de datos
        String url = "jdbc:h2:C:/Users/Daniel/dbasambleapa";
        String username = "root";
        String password = "123";

        // Establecer conexión
        try (Connection conexion = DriverManager.getConnection(url, username, password)) {
            // Crear una declaración SQL
            Statement statement = conexion.createStatement();

            // Consulta para obtener información de Asambleista, Votacion y Presente
            String consulta = "SELECT a.tipo, v.voto, COUNT(*) AS cantidad " +
                    "FROM Asambleista a " +
                    "JOIN Votacion v ON a.id = v.asambleista_id " +
                    "GROUP BY a.tipo, v.voto";

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery(consulta);

            // Variables para almacenar los totales de votos por columna
            int totalSi = 0;
            int totalNo = 0;
            int totalAbstencion = 0;

            // Variables para almacenar los totales de votos por tipo de asambleista
            int totalNacionalesSi = 0;
            int totalNacionalesNo = 0;
            int totalNacionalesAbstencion = 0;

            int totalProvincialesSi = 0;
            int totalProvincialesNo = 0;
            int totalProvincialesAbstencion = 0;

            int totalExtranjeroSi = 0;
            int totalExtranjeroNo = 0;
            int totalExtranjeroAbstencion = 0;

            // Recorrer los resultados
            while (resultSet.next()) {
                // Obtener los datos de las columnas
                String tipo = resultSet.getString("tipo");
                String voto = resultSet.getString("voto");
                int cantidad = resultSet.getInt("cantidad");

                // Actualizar los totales de votos por columna
                switch (voto) {
                    case "SI":
                        totalSi += cantidad;
                        break;
                    case "NO":
                        totalNo += cantidad;
                        break;
                    case "ABSTENCIÓN":
                        totalAbstencion += cantidad;
                        break;
                }

                // Actualizar los totales de votos por tipo de asambleista
                switch (tipo) {
                    case "Nacional":
                        switch (voto) {
                            case "SI":
                                totalNacionalesSi += cantidad;
                                break;
                            case "NO":
                                totalNacionalesNo += cantidad;
                                break;
                            case "ABSTENCIÓN":
                                totalNacionalesAbstencion += cantidad;
                                break;
                        }
                        break;
                    case "Provincial":
                        switch (voto) {
                            case "SI":
                                totalProvincialesSi += cantidad;
                                break;
                            case "NO":
                                totalProvincialesNo += cantidad;
                                break;
                            case "ABSTENCIÓN":
                                totalProvincialesAbstencion += cantidad;
                                break;
                        }
                        break;
                    case "Extranjero":
                        switch (voto) {
                            case "SI":
                                totalExtranjeroSi += cantidad;
                                break;
                            case "NO":
                                totalExtranjeroNo += cantidad;
                                break;
                            case "ABSTENCIÓN":
                                totalExtranjeroAbstencion += cantidad;
                                break;
                        }
                        break;
                }
            }

            // Imprimir la tabla de resultados
            System.out.println("---------------------------------------");
            System.out.println("|              |  SI  |  NO  | ABSTENCION |");
            System.out.println("|--------------|------|------|------------|");
            System.out.println("| NACIONALES   | " + totalNacionalesSi + "   | " + totalNacionalesNo + "   | " + totalNacionalesAbstencion + "         |");
            System.out.println("| PROVINCIALES | " + totalProvincialesSi + "   | " + totalProvincialesNo + "   | " + totalProvincialesAbstencion + "         |");
            System.out.println("| EXTRANJERO   | " + totalExtranjeroSi + "    | " + totalExtranjeroNo + "   | " + totalExtranjeroAbstencion + "         |");
            System.out.println("| TOTAL        | " + totalSi + "   | " + totalNo + "   | " + totalAbstencion + "         |");
            System.out.println("---------------------------------------");

            // Cerrar el ResultSet, la declaración y la conexión
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Asambleista> createRandomAsambleistas (int len){
        String[] tipos = {"Nacional", "Extranjero", "Provincial"};
        ArrayList<Asambleista> asambleistas = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(tipos.length);
            String tipo = tipos[randomIndex];

            Asambleista asambleista = new Asambleista(tipo);
            asambleistas.add(asambleista);
        }
        return asambleistas;
    }


}
