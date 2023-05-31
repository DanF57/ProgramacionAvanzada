package ec.edu.utpl.computacion.pa.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Table
@Entity
public class Asambleista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String tipo;

    @OneToOne(mappedBy = "asambleista")
    private Votacion votacion;

    public Asambleista(String tipo) {
        this.tipo = tipo;
    }

    public Asambleista(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Asambleista() {
    }

    public String getTipo(){
        return tipo;
    }

    public long getId(){
        return id;
    }
}


