package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Penalita.
 */
@Entity
@Table(name = "penalita")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "penalita")
public class Penalita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "desc_decu")
    private String decoTipoPena;

    @Column(name = "qnta_pena")
    private Float qntaPena;

    @Column(name = "impo_pena")
    private Float impoPena;

    @Column(name = "unit_misu")
    private String unitMisu;

    @OneToOne
    @JoinColumn(unique = true)
    private Pagamento pagamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecoTipoPena() {
        return decoTipoPena;
    }

    public void setDecoTipoPena(String decoTipoPena) {
        this.decoTipoPena = decoTipoPena;
    }

    public Float getQntaPena() {
        return qntaPena;
    }

    public void setQntaPena(Float qntaPena) {
        this.qntaPena = qntaPena;
    }

    public Float getImpoPena() {
        return impoPena;
    }

    public void setImpoPena(Float impoPena) {
        this.impoPena = impoPena;
    }

    public String getUnitMisu() {
        return unitMisu;
    }

    public void setUnitMisu(String unitMisu) {
        this.unitMisu = unitMisu;
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
        Penalita penalita = (Penalita) o;
        if(penalita.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, penalita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Penalita{" +
            "id=" + id +
            ", decoTipoPena='" + decoTipoPena + "'" +
            ", qntaPena='" + qntaPena + "'" +
            ", impoPena='" + impoPena + "'" +
            ", unitMisu='" + unitMisu + "'" +
            '}';
    }
}
