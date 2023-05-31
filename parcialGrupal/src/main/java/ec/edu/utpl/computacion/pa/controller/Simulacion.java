package ec.edu.utpl.computacion.pa.controller;
import ec.edu.utpl.computacion.pa.model.Asambleista;
import ec.edu.utpl.computacion.pa.model.Votacion;
import java.util.Random;

public class Simulacion implements Runnable {
    //The variable is associated with the class itself, rather than with individual instances of the class.
    //This implies that all instances of the Simulacion class share the same con variable.
    private static Controller con;
    private static int cantidadAsambleistas;

    private Asambleista a;

    @Override
    public void run() {
        //Creacion de Votos random
        Votacion voto = new Votacion(); //Voto Vacío
        voto.setAsambleista(a);         //Set al Voto del Asambleista enviado desde el main
        voto.setVoto(votoAleatorio());  //Voto Aleatorio de las opciones

        con.addVotacion(voto, cantidadAsambleistas);
        System.out.println(Thread.currentThread().getName() + " Termino su trabajo ");
    }

    public String votoAleatorio () {
        //Obtencion del Voto
        Random random = new Random();
        // Generar un número random entre 1 y 100
        int randomNumber = random.nextInt(100) + 1;
        // Asignar opcion voto basado en el numero random
        String voteOption;
        if (randomNumber <= 24) {
            voteOption = "SI";
        } else if (randomNumber <= 49) {
            voteOption = "NO";
        } else {
            voteOption = "ABSTENCIÓN";
        }
        return voteOption;
    }

    public void setCon(Controller con) {
        this.con = con;
    }

    public void setCantidadAsambleistas(Integer cantidadAsambleistas){
        this.cantidadAsambleistas = cantidadAsambleistas;
    }

    public void setA(Asambleista a) {
        this.a = a;
    }
}
