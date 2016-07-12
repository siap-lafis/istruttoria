package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ObbligoInverdimento.
 */
@Entity
@Table(name = "obbligo_inverdimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "obbligoinverdimento")
public class ObbligoInverdimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "supe_semi")
    private Integer supeSemi;

    @Column(name = "supe_prat_perm")
    private Integer supePratPerm;

    @Column(name = "supe_fora")
    private Integer supeFora;

    @Column(name = "deco_eson_dive")
    private Integer decoEsonDive;

    @Column(name = "deco_eson_efa")
    private Integer decoEsonEfa;

    @Column(name = "flag_risp_colt")
    private Integer flagRispColt;

    @Column(name = "flag_risp_colt_rima")
    private Integer flagRispColtRima;

    @Column(name = "flag_risp_75_p")
    private Integer flagRisp75P;

    @Column(name = "flag_risp_95_p")
    private Integer flagRisp95P;

    @Column(name = "flag_risp_efa")
    private Integer flagRispEfa;

    @OneToOne
    @JoinColumn(unique = true)
    private SuperficieInverdimento superficiInverdimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupeSemi() {
        return supeSemi;
    }

    public void setSupeSemi(Integer supeSemi) {
        this.supeSemi = supeSemi;
    }

    public Integer getSupePratPerm() {
        return supePratPerm;
    }

    public void setSupePratPerm(Integer supePratPerm) {
        this.supePratPerm = supePratPerm;
    }

    public Integer getSupeFora() {
        return supeFora;
    }

    public void setSupeFora(Integer supeFora) {
        this.supeFora = supeFora;
    }

    public Integer getDecoEsonDive() {
        return decoEsonDive;
    }

    public void setDecoEsonDive(Integer decoEsonDive) {
        this.decoEsonDive = decoEsonDive;
    }

    public Integer getDecoEsonEfa() {
        return decoEsonEfa;
    }

    public void setDecoEsonEfa(Integer decoEsonEfa) {
        this.decoEsonEfa = decoEsonEfa;
    }

    public Integer getFlagRispColt() {
        return flagRispColt;
    }

    public void setFlagRispColt(Integer flagRispColt) {
        this.flagRispColt = flagRispColt;
    }

    public Integer getFlagRispColtRima() {
        return flagRispColtRima;
    }

    public void setFlagRispColtRima(Integer flagRispColtRima) {
        this.flagRispColtRima = flagRispColtRima;
    }

    public Integer getFlagRisp75P() {
        return flagRisp75P;
    }

    public void setFlagRisp75P(Integer flagRisp75P) {
        this.flagRisp75P = flagRisp75P;
    }

    public Integer getFlagRisp95P() {
        return flagRisp95P;
    }

    public void setFlagRisp95P(Integer flagRisp95P) {
        this.flagRisp95P = flagRisp95P;
    }

    public Integer getFlagRispEfa() {
        return flagRispEfa;
    }

    public void setFlagRispEfa(Integer flagRispEfa) {
        this.flagRispEfa = flagRispEfa;
    }

    public SuperficieInverdimento getSuperficiInverdimento() {
        return superficiInverdimento;
    }

    public void setSuperficiInverdimento(SuperficieInverdimento superficieInverdimento) {
        this.superficiInverdimento = superficieInverdimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObbligoInverdimento obbligoInverdimento = (ObbligoInverdimento) o;
        if(obbligoInverdimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, obbligoInverdimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ObbligoInverdimento{" +
            "id=" + id +
            ", supeSemi='" + supeSemi + "'" +
            ", supePratPerm='" + supePratPerm + "'" +
            ", supeFora='" + supeFora + "'" +
            ", decoEsonDive='" + decoEsonDive + "'" +
            ", decoEsonEfa='" + decoEsonEfa + "'" +
            ", flagRispColt='" + flagRispColt + "'" +
            ", flagRispColtRima='" + flagRispColtRima + "'" +
            ", flagRisp75P='" + flagRisp75P + "'" +
            ", flagRisp95P='" + flagRisp95P + "'" +
            ", flagRispEfa='" + flagRispEfa + "'" +
            '}';
    }
}
