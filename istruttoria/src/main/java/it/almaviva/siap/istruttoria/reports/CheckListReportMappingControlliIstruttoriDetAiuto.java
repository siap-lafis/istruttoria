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
import it.almaviva.siap.istruttoria.domain.Pagamento;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/**
 * @author vterella
 *
 */
public class CheckListReportMappingControlliIstruttoriDetAiuto {

	public Collection<Map<String, ?>> getMapping(Domanda domanda,List<Pagamento> pagamenti) {
		
		Collection<Map<String, ?>> cli4 = new ArrayList<Map<String, ?>>();			
		for (Pagamento pagamento : pagamenti ) {
			if (pagamento.getQntaDich().floatValue()>0) {
				Map<String,Object> map = new HashMap<String,Object>();	
				Timestamp data = null;	    
				try {
					data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(pagamento.getDataElab())).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				map.put("dataDecr", data);
				map.put("protocollo", "");
				map.put("cuaa", domanda.getSoggetto().getCuaa());
				map.put("codiBarr",""+domanda.getIdDomanda());					
				map.put("descDeno", domanda.getSoggetto().getDenominazione());
				
				
				map.put("codiInte", "codiInte"); // Se codice intervento = '026' mostra il rigo con qntaDich
				map.put("descInte", pagamento.getCodIntervento());
				map.put("unita", "unita");//inte.getUnita());
				map.put("qntaDich", new BigDecimal(10)); // C551
				map.put("qntaRich",  new BigDecimal(11));  // C552
				map.put("impoRich",  new BigDecimal(12.45)); // C554
				
				
				
				List<Object> riduzioni = new ArrayList<Object>();
				Collection<Map<String, ?>> cli41 = getMappingRiduzioni(riduzioni);
				JRMapCollectionDataSource cli41DS = new JRMapCollectionDataSource(cli41);
				map.put("cli41DS", cli41DS);
	
				
				List<Object> sanzioni = new ArrayList<Object>();
				Collection<Map<String, ?>> cli42 = getMappingSanzioni(sanzioni);
				JRMapCollectionDataSource cli42DS = new JRMapCollectionDataSource(cli42);
				map.put("cli42DS", cli42DS);  
	
				Collection<Map<String, ?>> cli43 = getMappingDeterminato();				
				JRMapCollectionDataSource cli43DS = new JRMapCollectionDataSource(cli43);
				map.put("cli43DS", cli43DS);  
	
				Collection<Map<String, ?>> cli44 = getMappingAmmesso();								
				JRMapCollectionDataSource cli44DS = new JRMapCollectionDataSource(cli44);
				map.put("cli44DS", cli44DS);  
	
				cli4.add(map);
			}	
		}
		return cli4;
	}
	
	public Collection<Map<String, ?>> preparaPagina4(Domanda domanda,List<InterventoLiquidato> importi, Pagamento decreto) {
		
		Collection<Map<String, ?>> cli4 = new ArrayList<Map<String, ?>>();			
		for (InterventoLiquidato inte  : importi ) {
			if (inte.getQntaRich() != null) {
				Map<String,Object> map = new HashMap<String,Object>();	
				Timestamp data = null;	    
				try {
					data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(decreto.getDataElab())).getTime());
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
	
	private Collection<Map<String, ?>> getMappingRiduzioni(List<Object> riduzioni) {  // TODO: definire cosa sono le riduzioni
		
		Collection<Map<String, ?>> cli41 = new ArrayList<Map<String, ?>>();	
		Map<String,Object> mr = new HashMap<String,Object>();	
		double totaQntaRidu = 0;
		double totaImpoRidu = 0;	
		for (Object riduzione : riduzioni ) {							
			//Riduzione ridu = riduzioni.get(k);
			//totaQntaRidu += ridu.getQuantita().doubleValue();
			//totaImpoRidu += ridu.getImporto().doubleValue();			
			 mr.put("tipologia", "");//ridu.getTipologia());
			 mr.put("classe", "");//ridu.getClasse());
			 mr.put("esito", "");//ridu.getEsito());
			 mr.put("unita", "");//ridu.getUnita());
			 mr.put("quantita",null);//ridu.getQuantita());
			 mr.put("importo", null);//ridu.getImporto());
			 mr.put("totaQntaRidu", totaQntaRidu); // C560
			 mr.put("totaImpoRidu", totaImpoRidu); // C561
			 cli41.add(mr);
		}
		return cli41;
	}
	
	private Collection<Map<String, ?>>  getMappingSanzioni(List<Object> sanzioni) {  // TODO: definire cosa sono le sanzioni
		
		Collection<Map<String, ?>> cli42 = new ArrayList<Map<String, ?>>();	
		Map<String,Object> ms = new HashMap<String,Object>();	
		double totaQntaSanz = 0;
		double totaImpoSanz = 0;
		for (Object sanzione : sanzioni ) {		
			//Riduzione ridu = sanzioni.get(k);
			//totaQntaSanz += ridu.getQuantita().doubleValue();
			//totaImpoSanz += ridu.getImporto().doubleValue();			
			ms.put("tipologia", "");//ridu.getTipologia());
			ms.put("classe", "");//ridu.getClasse());
			ms.put("esito", "");//ridu.getEsito());
			ms.put("unita", "");//ridu.getUnita());
			ms.put("quantita",null);//ridu.getQuantita());
			ms.put("importo",null);//ridu.getImporto());
			ms.put("totaQntaSanz", totaQntaSanz); // C640 ?????
			ms.put("totaImpoSanz",totaImpoSanz); // C640
			cli42.add(ms);
		}
		return cli42;
	}
		
	
	private Collection<Map<String, ?>>  getMappingDeterminato() {  // TODO: definire cosa passare
		
		Collection<Map<String, ?>> cli43 = new ArrayList<Map<String, ?>>();	
		Map<String,Object> m43 = new HashMap<String,Object>();				
//		BigDecimal qntaDete = new BigDecimal(inte.getQntaDete());
//		BigDecimal impoDete = new BigDecimal(inte.getImpoDete());

		m43.put("unita", "unita43");//inte.getUnita());
		m43.put("qntaDete", new BigDecimal(14.5));// C558
		m43.put("impoDete", new BigDecimal(15.6));// C559
		cli43.add(m43);
		return cli43;
	}
	
	private Collection<Map<String, ?>>  getMappingAmmesso() {  // TODO: definire cosa passare
		
		Collection<Map<String, ?>> cli44 = new ArrayList<Map<String, ?>>();	
		Map<String,Object> m44 = new HashMap<String,Object>();				
//		BigDecimal qntaAmme = qntaDete.add(new BigDecimal(totaQntaSanz));
//		BigDecimal impoAmme = impoDete.add(new BigDecimal(totaImpoSanz));
		m44.put("unita", "unitaCli44");
		m44.put("qntaAmme", new BigDecimal(16.5));//qntaAmme); // CALCOLATO
		m44.put("impoAmme", new BigDecimal(17.5));//impoAmme);  // CALCOLATO
		m44.put("giorRita", new Integer(2));//Integer.valueOf(inte.getGiorRita()));  ??
		m44.put("riduRitaDepo", new BigDecimal(18.5));//inte.getRiduRitaDepo());	 ??
		//BigDecimal impoRidoRita = impoAmme.add(inte.getRiduRitaDepo());
//		if (decreto.getDescFasePaga().equals("ANTICIPO")) {
//			impoRidoRita = impoRidoRita.divide(new BigDecimal(2));
//		}
		m44.put("impoRidoRita", new BigDecimal(19.5)); //??
		m44.put("riduModu",   new BigDecimal(20.5)); //??
		m44.put("discFina", new BigDecimal(21.5)); //??
		
//		BigDecimal impoRidoAggi = impoRidoRita.add(inte.getRiduModu()).add(inte.getDiscFina());
		m44.put("impoRidoAggi",  new BigDecimal(21.5)); //??
		
		
		m44.put("qntaErogPrec", new BigDecimal(22));  // ?? 
		m44.put("impoErogPrec", new BigDecimal(23.5)); // ??
		
		
//		BigDecimal qntaDecr = qntaAmme.subtract(new BigDecimal(inte.getQntaErogPrec()));
//		BigDecimal impoDecr = impoRidoAggi.subtract(new BigDecimal(inte.getImpoErogPrec()));
		m44.put("qntaDecr",new BigDecimal(24));  // ?? 
		m44.put("impoDecr", new BigDecimal(25.6));  // ?? 
		
		
		m44.put("sanzCond", new BigDecimal(26.6));  // ?? 
//		m44.put("qntaLiqu", inte.getQntaLiqu());
//		m44.put("impoLiqu", inte.getImpoLiqu());
		cli44.add(m44);
		return cli44;
	}
		
	
}
