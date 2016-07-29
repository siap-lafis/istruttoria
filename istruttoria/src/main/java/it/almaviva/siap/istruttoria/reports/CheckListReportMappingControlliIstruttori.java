/**
 * 
 */
package it.almaviva.siap.istruttoria.reports;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.Soggetto;

/**
 * @author vterella
 *
 */
public class CheckListReportMappingControlliIstruttori {

	public Collection<Map<String, ?>> getMapping(Domanda domanda,List<String> controlli) {  //  TODO: i controlli sono da definire, per ora sono fissi
		
		Collection<Map<String, ?>> cli2 =  new ArrayList<Map<String, ?>>();			
		Soggetto soggetto = domanda.getSoggetto();	   
		for (String controllo : controlli) {  
			Map<String,Object> map = new HashMap<String,Object>();	
			Timestamp data = null;	    
			try {
				data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(domanda.getDataInse())).getTime());  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			map.put("dataDecr", data);
			map.put("protocollo", "");
			map.put("cuaa", soggetto.getCuaa());
			map.put("codiBarr", ""+domanda.getIdDomanda());	
			map.put("descDeno", soggetto.getDenominazione());
			//map.putAll((Map)controlli.get(i));
			map.put("clasCont",new Integer(1));
			map.put("descCont",controllo);
			map.put("dataCont", Calendar.getInstance().getTime());
			cli2.add(map);
		}
		return cli2;
	}
	
	public Collection<Map<String, ?>> preparaPagina2(Domanda domanda,List<Map<String,Object>>  controlli) {
		Collection<Map<String, ?>> cli2 =  new ArrayList<Map<String, ?>>();			
		Soggetto soggetto = domanda.getSoggetto();	   
		for (Map<String,Object>  controllo : controlli) {  
			Map<String,Object> map = new HashMap<String,Object>();	
			Timestamp data = null;	    
			try {
				data = new Timestamp((new SimpleDateFormat("dd/MM/yyyy").parse(domanda.getDataInse())).getTime());  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			map.put("dataDecr", data);  // TODO: da definire
			map.put("protocollo", "");
			map.put("cuaa", soggetto.getCuaa());
			map.put("codiBarr", ""+domanda.getIdDomanda());	
			map.put("descDeno", soggetto.getDenominazione());
						
			map.putAll(controllo);
			cli2.add(map);
		}		
		return cli2;	
	}		
}
