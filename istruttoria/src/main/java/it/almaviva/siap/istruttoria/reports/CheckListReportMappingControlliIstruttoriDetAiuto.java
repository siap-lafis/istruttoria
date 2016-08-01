/**
 * 
 */
package it.almaviva.siap.istruttoria.reports;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.almaviva.siap.istruttoria.dao.InterventoLiquidato;
import it.almaviva.siap.istruttoria.dao.Riduzione;
import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/**
 * @author vterella
 *
 */
public class CheckListReportMappingControlliIstruttoriDetAiuto {

	
	
	public Collection<Map<String, ?>> preparaPagina4(Domanda domanda,List<InterventoLiquidato> importi, ElencoPagamento decreto) {
		
		Collection<Map<String, ?>> cli4 = new ArrayList<Map<String, ?>>();			
		for (InterventoLiquidato inte  : importi ) {
			if (inte.getQntaRich() != null) {
				Map<String,Object> map = new HashMap<String,Object>();	
				Timestamp data = null;	    
				try {
					data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(decreto.getDataDecr())).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				map.put("dataDecr", data);
				map.put("protocollo", "");
				map.put("cuaa", domanda.getSoggetto().getCuaa());
				map.put("codiBarr",""+domanda.getIdDomanda());					
				map.put("descDeno", domanda.getSoggetto().getDenominazione());
				map.put("codiInte", inte.getCodiInte());
				map.put("descInte", inte.getDescInte());
				map.put("unita", inte.getUnita());
				map.put("qntaDich", inte.getQntaDich());
				map.put("qntaRich", inte.getQntaRich());
				map.put("impoRich", inte.getImpoRich());
				
				
				// RIDUZIONI
				double totaQntaRidu = 0;
				double totaImpoRidu = 0;
				Collection<Map<String, ?>> cli41 = new ArrayList<Map<String, ?>>();	
				List<Riduzione> riduzioni = inte.getRiduzioni();
				for (int k=0; k<riduzioni.size(); k++) {
					Riduzione ridu = riduzioni.get(k);
					totaQntaRidu += ridu.getQuantita().doubleValue();
					totaImpoRidu += ridu.getImporto().doubleValue();
					Map<String,Object> mr = new HashMap<String,Object>();	  // Si preparano i dati per il datasource delle riduzioni
					mr.put("tipologia", ridu.getTipologia());
					mr.put("classe", ridu.getClasse());
					mr.put("esito", ridu.getEsito());
					mr.put("unita", ridu.getUnita());
					mr.put("quantita", ridu.getQuantita());
					mr.put("importo", ridu.getImporto());
					mr.put("totaQntaRidu", inte.getQntaRidu());
					mr.put("totaImpoRidu", inte.getImpoRidu());
					cli41.add(mr);
				}
				JRMapCollectionDataSource cli41DS = new JRMapCollectionDataSource(cli41);
				map.put("cli41DS", cli41DS);
				
				
				// SANZIONI
				double totaQntaSanz = 0;
				double totaImpoSanz = 0;
				Collection<Map<String, ?>> cli42 =  new ArrayList<Map<String, ?>>();
				List<Riduzione> sanzioni = inte.getSanzioni();
				for (int k=0; k<sanzioni.size(); k++) {
					Riduzione ridu = sanzioni.get(k);
					totaQntaSanz += ridu.getQuantita().doubleValue();
					totaImpoSanz += ridu.getImporto().doubleValue();
					Map<String,Object> ms = new HashMap<String,Object>();  // Si preparano i dati per il datasource delle sanzioni
					ms.put("tipologia", ridu.getTipologia());
					ms.put("classe", ridu.getClasse());
					ms.put("esito", ridu.getEsito());
					ms.put("unita", ridu.getUnita());
					ms.put("quantita", ridu.getQuantita());
					ms.put("importo", ridu.getImporto());
					ms.put("totaQntaSanz", inte.getQntaSanz());
					ms.put("totaImpoSanz", inte.getImpoSanz());
					cli42.add(ms);
				}
				JRMapCollectionDataSource cli42DS = new JRMapCollectionDataSource(cli42);
				map.put("cli42DS", cli42DS);  
				
				// DETERMINATO
				Collection<Map<String, ?>> cli43 = new ArrayList<Map<String, ?>>();
				Map<String,Object> m43 = new HashMap<String,Object>();
				BigDecimal qntaDete = new BigDecimal(inte.getQntaDete());
				BigDecimal impoDete = new BigDecimal(inte.getImpoDete());
//				BigDecimal qntaDete = inte.getQntaRich().add(new BigDecimal(totaQntaRidu));  // quantita' negativa
//				BigDecimal impoDete = inte.getImpoRich().add(new BigDecimal(totaImpoRidu));
				m43.put("unita", inte.getUnita());
				m43.put("qntaDete", qntaDete);
				m43.put("impoDete", impoDete);
				cli43.add(m43);
				JRMapCollectionDataSource cli43DS = new JRMapCollectionDataSource(cli43);
				map.put("cli43DS", cli43DS);  
	
				// AMMESSO
				Collection<Map<String, ?>>  cli44 = new ArrayList<Map<String, ?>>();
				Map<String,Object> m44 = new HashMap<String,Object>();
				BigDecimal qntaAmme = qntaDete.add(new BigDecimal(totaQntaSanz));
				BigDecimal impoAmme = impoDete.add(new BigDecimal(totaImpoSanz));
				m44.put("unita", inte.getUnita());
				m44.put("qntaAmme", qntaAmme);
				m44.put("impoAmme", impoAmme);
				m44.put("giorRita", Integer.valueOf(inte.getGiorRita()));
				m44.put("riduRitaDepo", inte.getRiduRitaDepo());
				BigDecimal impoRidoRita = impoAmme.add(inte.getRiduRitaDepo());
				// TODO: sulla visga pagamento non ho il campo
//				if (descreto.getDescFasePaga().equals("ANTICIPO")) {
//					impoRidoRita = impoRidoRita.divide(new BigDecimal(2));
//				}
				m44.put("impoRidoRita", impoRidoRita);
				m44.put("riduModu", inte.getRiduModu());
				m44.put("discFina", inte.getDiscFina());
				BigDecimal impoRidoAggi = impoRidoRita.add(inte.getRiduModu()).add(inte.getDiscFina());
				m44.put("impoRidoAggi", impoRidoAggi);
				m44.put("qntaErogPrec", new BigDecimal(inte.getQntaErogPrec()));
				m44.put("impoErogPrec", new BigDecimal(inte.getImpoErogPrec()));
				BigDecimal qntaDecr = qntaAmme.subtract(new BigDecimal(inte.getQntaErogPrec()));
				BigDecimal impoDecr = impoRidoAggi.subtract(new BigDecimal(inte.getImpoErogPrec()));
				m44.put("qntaDecr", qntaDecr);
				m44.put("impoDecr", impoDecr);
				m44.put("sanzCond", inte.getSanzCond());
				m44.put("qntaLiqu", inte.getQntaLiqu());
				m44.put("impoLiqu", inte.getImpoLiqu());
				cli44.add(m44);
				JRMapCollectionDataSource cli44DS = new JRMapCollectionDataSource(cli44);
				map.put("cli44DS", cli44DS);  
				
				cli4.add(map);
			}	
		}
		return cli4;
	}
			
}
