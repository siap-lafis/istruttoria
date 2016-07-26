package it.almaviva.siap.istruttoria.dao;

import java.math.BigDecimal;

public class Riduzione {

	private String tipologia;
	private String esito;
	private String classe;
	private String effetto;
	private String unita;
	private BigDecimal quantita;
	private BigDecimal importo;
	
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getEffetto() {
		return effetto;
	}
	public void setEffetto(String effetto) {
		this.effetto = effetto;
	}
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getQuantita() {
		return quantita;
	}
	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	
	
}
