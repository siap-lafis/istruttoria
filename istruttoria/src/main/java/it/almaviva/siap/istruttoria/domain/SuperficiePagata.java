package it.almaviva.siap.istruttoria.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Dettaglio superfici per parcella o particella</p>                        
 * 
 */
@ApiModel(description = ""
    + "<p>Dettaglio superfici per parcella o particella</p>                   "
    + "")
@Entity
@Table(name = "superficie_pagata")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "superficiepagata")
public class SuperficiePagata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "supe_dich")
    private Integer supeDich;

    @Column(name = "supe_ammi")
    private Integer supeAmmi;

    @Column(name = "supe_refr")
    private Integer supeRefr;

    @Column(name = "supe_dete")
    private Integer supeDete;

    @Column(name = "supe_nsan")
    private Integer supeNsan;

    @Column(name = "supe_acce")
    private Integer supeAcce;

    @Column(name = "num_tito_dich")
    private Integer numTitoDich;

    @Column(name = "num_tito_dete")
    private Integer numTitoDete;

    @OneToOne
    @JoinColumn(unique = true)
    private Pagamento pagamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupeDich() {
        return supeDich;
    }

    public void setSupeDich(Integer supeDich) {
        this.supeDich = supeDich;
    }

    public Integer getSupeAmmi() {
        return supeAmmi;
    }

    public void setSupeAmmi(Integer supeAmmi) {
        this.supeAmmi = supeAmmi;
    }

    public Integer getSupeRefr() {
        return supeRefr;
    }

    public void setSupeRefr(Integer supeRefr) {
        this.supeRefr = supeRefr;
    }

    public Integer getSupeDete() {
        return supeDete;
    }

    public void setSupeDete(Integer supeDete) {
        this.supeDete = supeDete;
    }

    public Integer getSupeNsan() {
        return supeNsan;
    }

    public void setSupeNsan(Integer supeNsan) {
        this.supeNsan = supeNsan;
    }

    public Integer getSupeAcce() {
        return supeAcce;
    }

    public void setSupeAcce(Integer supeAcce) {
        this.supeAcce = supeAcce;
    }

    public Integer getNumTitoDich() {
        return numTitoDich;
    }

    public void setNumTitoDich(Integer numTitoDich) {
        this.numTitoDich = numTitoDich;
    }

    public Integer getNumTitoDete() {
        return numTitoDete;
    }

    public void setNumTitoDete(Integer numTitoDete) {
        this.numTitoDete = numTitoDete;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuperficiePagata superficiePagata = (SuperficiePagata) o;
        if(superficiePagata.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, superficiePagata.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SuperficiePagata{" +
            "id=" + id +
            ", supeDich='" + supeDich + "'" +
            ", supeAmmi='" + supeAmmi + "'" +
            ", supeRefr='" + supeRefr + "'" +
            ", supeDete='" + supeDete + "'" +
            ", supeNsan='" + supeNsan + "'" +
            ", supeAcce='" + supeAcce + "'" +
            ", numTitoDich='" + numTitoDich + "'" +
            ", numTitoDete='" + numTitoDete + "'" +
            '}';
    }
}
