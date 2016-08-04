/**
 * 
 */
package it.almaviva.siap.istruttoria.reports;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
public class CheckListReportMappingControlliIstruttori {

	
	
	public Collection<Map<String, ?>> preparaPagina2(Domanda domanda,ElencoPagamento decreto,List<Map<String,Object>>  controlli,String pathImage) {
		Collection<Map<String, ?>> cli2 =  new ArrayList<Map<String, ?>>();			
		Soggetto soggetto = domanda.getSoggetto();	   
		for (Map<String,Object>  controllo : controlli) {  
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
			map.put("cuaa", soggetto.getCuaa());
			map.put("codiBarr", ""+domanda.getIdDomanda());	
			map.put("descDeno", soggetto.getDenominazione());	
			
			// aggiunto flag 
			Integer flag =  (Integer)controllo.get("pres_anom");
			map.put("checked", flag>0);
			map.put("pathImage",pathImage);
			
			
			map.putAll(controllo);
			cli2.add(map);
		}		
		return cli2;	
	}		
}
