package ec.edu.utpl.computacion.pa.controller;

import ec.edu.utpl.computacion.pa.model.Asambleista;

import java.sql.*;
import java.util.ArrayList;

public class InsertAsambleistas implements Runnable{

    private ArrayList<Asambleista> asambleistas;

    public InsertAsambleistas(ArrayList<Asambleista> a){
        this.asambleistas = a;
    }

    @Override
    public void run() {
        String url = "jdbc:h2:C:/Users/Daniel/dbasambleapa";
        String username = "root";
        String password = "123";

        String sql = "INSERT INTO Asambleista (TIPO) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement stmtAsambleista = connection.prepareStatement(sql)) {

            for (Asambleista asambleista : asambleistas) {
                // Insert into Asambleista
                stmtAsambleista.setString(1, asambleista.getTipo());
                stmtAsambleista.executeUpdate();
                System.out.println("Se inserto: " + asambleista.getTipo());

                stmtAsambleista.clearParameters(); // Limpiar los parametros
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Termino " + Thread.currentThread().getName());
    }

}
