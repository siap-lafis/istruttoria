package it.almaviva.siap.istruttoria.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class InterventoLiquidato implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal qntaDich;
	private BigDecimal qntaRich;
	private BigDecimal impoRich;
	private double qntaDete;
	private double impoDete;
	private BigDecimal qntaRidu;
	private BigDecimal impoRidu;		
	private BigDecimal qntaSanz;
	private BigDecimal impoSanz;
	private int giorRita;
	private BigDecimal riduRitaDepo;
	private BigDecimal riduModu;
	private BigDecimal discFina;
	private BigDecimal sanzCond;
	private double qntaErogPrec;
	private double impoErogPrec;
	private BigDecimal qntaLiqu;
	private BigDecimal impoLiqu;
	private String unita;
	private String codiInte;
	private String descInte;
	private List<Riduzione> riduzioni;
	private List<Riduzione> sanzioni;;
	
	
	public BigDecimal getQntaDich() {
		return qntaDich;
	}
	public void setQntaDich(BigDecimal qntaDich) {
		this.qntaDich = qntaDich;
	}
	public BigDecimal getQntaRich() {
		return qntaRich;
	}
	public void setQntaRich(BigDecimal qntaRich) {
		this.qntaRich = qntaRich;
	}
	public BigDecimal getImpoRich() {
		return impoRich;
	}
	public void setImpoRich(BigDecimal impoRich) {
		this.impoRich = impoRich;
	}
	public double getQntaDete() {
		return qntaDete;
	}
	public void setQntaDete(double qntaDete) {
		this.qntaDete = qntaDete;
	}
	public double getImpoDete() {
		return impoDete;
	}
	public void setImpoDete(double impoDete) {
		this.impoDete = impoDete;
	}
	public BigDecimal getQntaRidu() {
		return qntaRidu;
	}
	public void setQntaRidu(BigDecimal qntaRidu) {
		this.qntaRidu = qntaRidu;
	}
	public BigDecimal getImpoRidu() {
		return impoRidu;
	}
	public void setImpoRidu(BigDecimal impoRidu) {
		this.impoRidu = impoRidu;
	}
	public BigDecimal getQntaSanz() {
		return qntaSanz;
	}
	public void setQntaSanz(BigDecimal qntaSanz) {
		this.qntaSanz = qntaSanz;
	}
	public BigDecimal getImpoSanz() {
		return impoSanz;
	}
	public void setImpoSanz(BigDecimal impoSanz) {
		this.impoSanz = impoSanz;
	}
	public int getGiorRita() {
		return giorRita;
	}
	public void setGiorRita(int giorRita) {
		this.giorRita = giorRita;
	}
	public BigDecimal getRiduRitaDepo() {
		return riduRitaDepo;
	}
	public void setRiduRitaDepo(BigDecimal riduRitaDepo) {
		this.riduRitaDepo = riduRitaDepo;
	}
	public BigDecimal getRiduModu() {
		return riduModu;
	}
	public void setRiduModu(BigDecimal riduModu) {
		this.riduModu = riduModu;
	}
	public BigDecimal getDiscFina() {
		return discFina;
	}
	public void setDiscFina(BigDecimal discFina) {
		this.discFina = discFina;
	}
	public BigDecimal getSanzCond() {
		return sanzCond;
	}
	public void setSanzCond(BigDecimal sanzCond) {
		this.sanzCond = sanzCond;
	}
	public double getQntaErogPrec() {
		return qntaErogPrec;
	}
	public void setQntaErogPrec(double qntaErogPrec) {
		this.qntaErogPrec = qntaErogPrec;
	}
	public double getImpoErogPrec() {
		return impoErogPrec;
	}
	public void setImpoErogPrec(double impoErogPrec) {
		this.impoErogPrec = impoErogPrec;
	}
	public BigDecimal getQntaLiqu() {
		return qntaLiqu;
	}
	public void setQntaLiqu(BigDecimal qntaLiqu) {
		this.qntaLiqu = qntaLiqu;
	}
	public BigDecimal getImpoLiqu() {
		return impoLiqu;
	}
	public void setImpoLiqu(BigDecimal impoLiqu) {
		this.impoLiqu = impoLiqu;
	}
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	public List<Riduzione> getRiduzioni() {
		return riduzioni;
	}
	public void setRiduzioni(List<Riduzione> riduzioni) {
		this.riduzioni = riduzioni;
	}
	public List<Riduzione> getSanzioni() {
		return sanzioni;
	}
	public void setSanzioni(List<Riduzione> sanzioni) {
		this.sanzioni = sanzioni;
	}
	public String getCodiInte() {
		return codiInte;
	}
	public void setCodiInte(String codiInte) {
		this.codiInte = codiInte;
	}
	public String getDescInte() {
		return descInte;
	}
	public void setDescInte(String descInte) {
		this.descInte = descInte;
	}
	
	

}
