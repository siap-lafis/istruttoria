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

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.Pagamento;
import it.almaviva.siap.istruttoria.domain.Soggetto;

/**
 * @author vterella
 *
 */
public class CheckListReportMappingPrimaPagina  {
	
	public Collection<Map<String, ?>> getMapping(Domanda domanda,List<Pagamento> pagamenti) {
		
		Collection<Map<String, ?>> cli1 = new ArrayList<Map<String, ?>>();		   	 
    	for (Pagamento pagamento : pagamenti ) {   
    		 Map<String,Object> map = new HashMap<String,Object>();	
		    Soggetto soggetto = domanda.getSoggetto();	   
		    Timestamp data = null;		    
			try {
				data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(pagamento.getDataElab())).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//map.put("numeDecr", decreto.getNumeDecr());
			map.put("dataDecr", data);
			map.put("protocollo", "");
			map.put("cuaa", soggetto.getCuaa());
			map.put("piva", soggetto.getPartitaIva());
			map.put("codiBarr", ""+domanda.getIdDomanda());
			if (soggetto.getCuaa().length()==16) {  // caso di persona fisica
				map.put("descNome", soggetto.getNome()); // deve essere creato il campo NOME SU SOGGETTO
				map.put("descDeno", soggetto.getCognome()); // deve essere creato il campo COGNOME SU SOGGETO
			}
			else 
				map.put("descDeno", soggetto.getDenominazione());
			
			//map.putAll((Map)interventi.get(i));
			map.put("descInte", pagamento.getCodIntervento());
			map.put("unitMisu", pagamento.getUnitMisu());
			map.put("qntaRich", new BigDecimal(pagamento.getQntaDich())); 
			map.put("qntaDete",  new BigDecimal(pagamento.getQntaAmme())); 
			cli1.add(map);
    	}
		return cli1;
	}
	
	public Collection<Map<String, ?>> preparaPagina1(Domanda domanda,List interventi, Pagamento decreto,String pathImage) {
		
		Collection<Map<String, ?>> cli1 =  new ArrayList<Map<String, ?>>();			
		Soggetto soggetto = domanda.getSoggetto();	   
		for (Object intervento : interventi) {  
			Map<String,Object> map = new HashMap<String,Object>();	
			Timestamp data = null;	    
			try {
				data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(decreto.getDataElab())).getTime());  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
					
			map.put("dataDecr", data);
			//map.put("protocollo", decreto.getProtocollo());
			map.put("cuaa", soggetto.getCuaa());
			map.put("piva", soggetto.getPartitaIva());
			map.put("codiBarr", ""+domanda.getIdDomanda());
			map.put("cuaa", soggetto.getCuaa());
			if (soggetto.getCuaa().length()==16) {  // caso di persona fisica
				map.put("descNome", soggetto.getNome()); // deve essere creato il campo NOME SU SOGGETTO
				map.put("descDeno", soggetto.getCognome()); // deve essere creato il campo COGNOME SU SOGGETO
			}
			else 
				map.put("descDeno", soggetto.getDenominazione());
			map.putAll((Map)intervento);
			map.put("checked", Boolean.FALSE);
			map.put("pathImage",pathImage);
			cli1.add(map);
		}
		cli1.add(getLastRowControlli(pathImage));
		return cli1;
	}
	
	private Map<String,Object> getLastRowControlli(String pathImage) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("descInte", "Ammontare pagamento domanda interventi richiesti < 250 Euro");
		map.put("checked", Boolean.TRUE);	
		map.put("pathImage",pathImage);
		return map;
	}

}
