package ec.edu.utpl.computacion.pa.controller;

import ec.edu.utpl.computacion.pa.model.Asambleista;
import ec.edu.utpl.computacion.pa.model.Votacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Controller {

    private static EntityManager em = null;
    private int contador;
    public Controller() {
        setEm("pu-pa");
    }
    private void setEm(String puName) {
        if(em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
            em = emf.createEntityManager();
        }
    }

    public synchronized void addVotacion(Votacion voto, int cantidadAsambleistas) {
        //Simulacion
        try {
            Thread.currentThread().sleep(100);
            System.out.println("Desperto hilo " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (contador < cantidadAsambleistas) {
            em.getTransaction().begin();
            Asambleista managedAsambleista = em.merge(voto.getAsambleista());
            voto.setAsambleista(managedAsambleista);
            em.persist(voto);
            contador++;
            em.flush();
            em.getTransaction().commit();
            System.out.println("El contador es: " + contador);
        }
    }

    public List<Asambleista> getAllAsambleista() {
        TypedQuery<Asambleista> qryAll = em.createQuery("""  
                SELECT new ec.edu.utpl.computacion.pa.model.Asambleista(a.id, a.tipo)
                FROM Asambleista a
                """, Asambleista.class);
        return qryAll.getResultList();
    }

    public void close() {
        em.close();
    }
}
