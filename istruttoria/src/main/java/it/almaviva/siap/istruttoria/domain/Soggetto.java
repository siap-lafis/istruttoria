package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Soggetto.
 */
@Entity
@Table(name = "soggetto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "soggetto")
public class Soggetto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cuaa")
    private String cuaa;

    @Column(name = "denominazione")
    private String denominazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuaa() {
        return cuaa;
    }

    public void setCuaa(String cuaa) {
        this.cuaa = cuaa;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Soggetto soggetto = (Soggetto) o;
        if(soggetto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, soggetto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Soggetto{" +
            "id=" + id +
            ", cuaa='" + cuaa + "'" +
            ", denominazione='" + denominazione + "'" +
            '}';
    }
}
