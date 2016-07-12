package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ElencoPagamento.
 */
@Entity
@Table(name = "elenco_pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "elencopagamento")
public class ElencoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_decr")
    private Integer idDecr;

    @Column(name = "data_decr")
    private String dataDecr;

    @OneToOne
    @JoinColumn(unique = true)
    private Domanda domanda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDecr() {
        return idDecr;
    }

    public void setIdDecr(Integer idDecr) {
        this.idDecr = idDecr;
    }

    public String getDataDecr() {
        return dataDecr;
    }

    public void setDataDecr(String dataDecr) {
        this.dataDecr = dataDecr;
    }

    public Domanda getDomanda() {
        return domanda;
    }

    public void setDomanda(Domanda domanda) {
        this.domanda = domanda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ElencoPagamento elencoPagamento = (ElencoPagamento) o;
        if(elencoPagamento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, elencoPagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ElencoPagamento{" +
            "id=" + id +
            ", idDecr='" + idDecr + "'" +
            ", dataDecr='" + dataDecr + "'" +
            '}';
    }
}
