/**
 * 
 */
package it.almaviva.siap.istruttoria.reports;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import it.almaviva.siap.istruttoria.domain.Soggetto;

/**
 * @author vterella
 *
 */
public class CheckListReportMappingPrimaPagina  {
	
	
	
	public Collection<Map<String, ?>> preparaPagina1(Domanda domanda,
													 List<Map<String,Object>> interventi, 
													 ElencoPagamento decreto,
													 String pathImage,
													 Boolean flag250Esclusa,
													 Date dataDomanda) {
		
		Collection<Map<String, ?>> cli1 =  new ArrayList<Map<String, ?>>();			
		Soggetto soggetto = domanda.getSoggetto();	   
		for (Map<String,Object> intervento : interventi) {  
			Map<String,Object> map = new HashMap<String,Object>();	
			Timestamp data = null;	    
			try {
				data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(decreto.getDataDecr())).getTime());  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			map.put("dataDomanda", dataDomanda);		
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
			
			
			map.putAll(intervento);
			Integer flagPagato = (Integer)intervento.get("FLAG_PAGATO");
			map.put("checked", flagPagato>0);
			map.put("pathImage",pathImage);
			cli1.add(map);
		}
		cli1.add(getLastRowControlli(flag250Esclusa,pathImage));
		return cli1;
	}
	
	private Map<String,Object> getLastRowControlli(Boolean flag250Esclusa,String pathImage) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("descInte", "Ammontare pagamento domanda interventi richiesti < 250 Euro");
		map.put("checked", flag250Esclusa);	
		map.put("pathImage",pathImage);
		return map;
	}

}
