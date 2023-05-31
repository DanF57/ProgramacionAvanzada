package ec.edu.utpl.computacion.pa.model;

import javax.persistence.*;

@Table
@Entity
public class Votacion {
    @Id
    private Long id;
    @Column(length = 15, nullable = false)
    private String voto;
    @OneToOne
    @MapsId
    private Asambleista asambleista;

    public Votacion(String voto){
        this.voto = voto;
    }
    public Votacion(){
    }

    public Votacion(Long id, String voto){
        this.id = id;
        this.voto = voto;
    }

    public Votacion(Asambleista asambleista){
        setAsambleista(asambleista);
    }
    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public Asambleista getAsambleista() {
        return asambleista;
    }

    public void setAsambleista(Asambleista asambleista) {
        this.asambleista = asambleista;
    }
}