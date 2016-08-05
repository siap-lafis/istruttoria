package it.almaviva.siap.istruttoria.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import it.almaviva.siap.istruttoria.dao.InterventoLiquidato;
import it.almaviva.siap.istruttoria.dao.StoredProcedureDao;
import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingControlliIstruttori;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingControlliIstruttoriDetAiuto;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingPrimaPagina;
import it.almaviva.siap.istruttoria.reports.CustomJRDataSource;
import it.almaviva.siap.istruttoria.repository.ElencoPagamentoRepository;
import it.almaviva.siap.istruttoria.repository.PagamentoRepository;
import it.almaviva.siap.istruttoria.repository.SoggettoRepository;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);
    
    // TODO: deve diventare un parametro
    private final int annoCampagna = 2015;
    //private final int idDecreto = 1;
        
    @Inject
    private SoggettoRepository soggettoRepository;
    @Inject
    private PagamentoRepository pagamentoRepository;  
    @Inject
    private ElencoPagamentoRepository elencoPagamentoRepository;
    @Inject
    private StoredProcedureDao dao;
    
    /**
     * POST  /report : Create a new report.
     *
     */
    @RequestMapping(value = "/report",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String generateReport() throws JRException {
        log.debug("REST request to save Report");
		JasperReport jasperReport = JasperCompileManager
				.compileReport("src/main/webapp/content/reports/report.jrxml");
        List<Soggetto> soggettos = soggettoRepository.findAll();
		CustomJRDataSource<Soggetto> dataSource = new CustomJRDataSource<Soggetto>()
				.initBy(soggettos);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
				dataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint, 
				"src/main/webapp/content/reports/report.pdf");
        return "OK";
    }
    
    
    
    
   
    @RequestMapping(value = "/domanda/detail/report",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public _FileName generateReportCheckList(@RequestBody Domanda domanda) throws JRException, FileNotFoundException {
    	  		
    		
    			
            log.debug("REST request to save Report"); 
            // recupero il decreto massimo a partire dalla domanda         
            List<ElencoPagamento> elencoPagamenti = elencoPagamentoRepository.findByDomandaId(domanda.getId());
            ElencoPagamento elencoPagamentoMaxDecr =  getElencoPagamentoMaxDecr(elencoPagamenti);
            int idDecreto = elencoPagamentoMaxDecr.getIdDecr();
            
            
            List<Map<String,Object>> controlli = dao.getControlliIstruttoriDU(domanda.getId(), idDecreto, annoCampagna); // decreto e annoCampagna fissi
            List<Map<String,Object>> interventi = dao.getListaInterventiRichiesti(domanda.getId(),idDecreto);	// TODO: decreto fisso
            Boolean flagEsclusa250 = dao.getEsclusa250Euro(domanda.getId(),idDecreto);
			//List interventi1 = dao.getListaInterventiRichiesti1(domanda.getId(),annoCampagna); TODO: commentata perchè estrae dati non significativi
			//aggiungiInterventiRichiesti(interventi, interventi1); // TODO: per il momento commentato
            
           
            
			List<InterventoLiquidato> importi = new ArrayList<InterventoLiquidato>();
			for (int i=0; i<interventi.size(); i++) {
				Map<String,Object> m = interventi.get(i);
				int idInte = ((Integer)m.get("idInte")).intValue();
				InterventoLiquidato intervento = dao.getDettaglioImportoLiquidato(domanda.getId(), idDecreto, idInte);
				intervento.setCodiInte((String)m.get("codiInte"));
				intervento.setDescInte((String)m.get("descInte"));
				
				// se è un intervento di greening 
				if ("008".equals(intervento.getCodiInte())) {
					Map<String,Object> checkGreening = dao.getFlagGreening(domanda.getId(), idDecreto, annoCampagna);
					intervento.setFlagGreening(checkGreening);
				}
				
				
				// se è un intervento di bsp o giovane agricoltore
				if ("026".equals(intervento.getCodiInte()) ||
					"300".equals(intervento.getCodiInte())	) {
					Map<String,Object> titoliBSP = dao.getTitoli(domanda.getId(), idDecreto, annoCampagna);
					intervento.setTitoli(titoliBSP);
				}
				
				
				importi.add(intervento);
											
			}                  			
		//	List<Pagamento> pagamenti = pagamentoRepository.findByIdAttoAmmi(domanda.getIdDomanda());    
			
			// Controllo sui dati 
			_FileName fileReturned = new _FileName();
			if (controlli.size()==0 || interventi.size()==0 || importi.size()==0) {
				fileReturned.setMessage("stampa.non.disponiblie");
				return fileReturned;
			}
			
            //String provvisoria ="provvisoria";             	    
    	    String dir = "/src/main/webapp/content/reports/";
    	    String dirImages = "/src/main/webapp/content/images/";
    	    File file = new File("");     
    		FileInputStream fileJasper = new FileInputStream(new File(file.getAbsolutePath()+dir+"checkListIstruttoria.jasper"));    
             		   		
    		Map<String,Object> subreport = new HashMap<String,Object>();

    		
    		String cli1 = file.getAbsolutePath()+dir+"cli1.jasper"; 		
    		String cli2 = file.getAbsolutePath()+dir+"cli2.jasper"; 
    		
    		
    		// TODO: il file cli4.jasper contiene il dettaglio degli aiuti senza le aggiunte che sono state richieste in un secondo momento,
    		//       mentre il file cli4Prova.jasper è la versione modificata. Per generare il template 'prima maniera' è sufficente caricare
    		//		 il file cli4.jasper
    		//String cli4 = file.getAbsolutePath()+dir+"cli4.jasper";    		
    		String cli4 = file.getAbsolutePath()+dir+"cli4Prova.jasper"; 
    		
    		String cli41 =file.getAbsolutePath()+ dir+"cli41.jasper"; 
    		String cli42 = file.getAbsolutePath()+dir+"cli42.jasper"; 
    		String cli43 = file.getAbsolutePath()+dir+"cli43.jasper"; 
    		String cli44 = file.getAbsolutePath()+dir+"cli44.jasper"; 
    		String cli45 = file.getAbsolutePath()+dir+"cli4BSP_Giovane.jasper";
    		String cli46 = file.getAbsolutePath()+dir+"cli4Greening.jasper";
    		
    		String pathImage = file.getAbsolutePath()+dirImages;
    		
    		subreport.put("cli1", cli1);
    		subreport.put("cli2", cli2);
//    		subreport.put("cli3", cli3);
    		subreport.put("cli4", cli4);
    		subreport.put("cli41", cli41);
    		subreport.put("cli42", cli42);
    		subreport.put("cli43", cli43);
    		subreport.put("cli44", cli44);
    		
    		subreport.put("cli45", cli45);
    		subreport.put("cli46", cli46);
                              
            Map<String,Object> srMap = new HashMap<String,Object>();
    		
    		JRDataSource srDS;
    		JRMapCollectionDataSource cli1DS = null;
    		JRMapCollectionDataSource cli2DS = null;
    		JRMapCollectionDataSource cli3DS = null;
    		JRMapCollectionDataSource cli4DS = null;
   
    		// preparazione prima pagina
    		Collection<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();;    		
	    	Collection<Map<String, ?>> listaPrimaPagina = new CheckListReportMappingPrimaPagina().preparaPagina1(domanda,interventi,elencoPagamentoMaxDecr,pathImage,flagEsclusa250);	    		
	        preparaSezione(listaPrimaPagina,lista);
	    	cli1DS = new JRMapCollectionDataSource(listaPrimaPagina);
	    	srMap.put("cli1DS", cli1DS);
    	
    	   		
    		// preparazione seconda pagina
    		Collection<Map<String, ?>> listaPaginaControlliIstruttori = new CheckListReportMappingControlliIstruttori().preparaPagina2(domanda,elencoPagamentoMaxDecr,controlli,pathImage);  
    		lista = new ArrayList<Map<String, ?>>();  		
    		preparaSezione(listaPaginaControlliIstruttori,lista);
    		cli2DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttori);
        	srMap.put("cli2DS", cli2DS);
    	
    		
    		
    		
    		// preparazione ultima pagina
    		Collection<Map<String, ?>> listaPaginaControlliIstruttoriDetAiuto = new CheckListReportMappingControlliIstruttoriDetAiuto().preparaPagina4(domanda,importi,elencoPagamentoMaxDecr,pathImage);
    		cli4DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttoriDetAiuto);
    		srMap.put("cli4DS", cli4DS);
    		
    		
    		
    		Collection<Map<String, ?>> srList = new ArrayList<Map<String, ?>>();	
    		srList.add(srMap);   		  		
    		srDS = new JRMapCollectionDataSource(srList);
    		Map<String,Object> parametri = new HashMap<String,Object>();
    		parametri.put("cli1DS", cli1DS);
    		parametri.put("cli2DS", cli2DS);
    		parametri.put("cli3DS", cli3DS);
    		parametri.put("cli4DS", cli4DS);
    		// Va in errore il recupero del file
    		//parametri.put(provvisoria, "SI");
    		parametri.putAll(subreport);
    		Locale.setDefault(Locale.ITALY);
      		JasperPrint jasperPrint = JasperFillManager.fillReport(fileJasper, parametri, srDS);       		
      		String fileName = "reportChkLst_"+System.currentTimeMillis();
    		JasperExportManager.exportReportToPdfFile(jasperPrint,dir.substring(1)+fileName+".pdf"); 
    		fileReturned.setName(fileName); 
    		return fileReturned;
        }
    
    
    private void aggiungiInterventiRichiesti(List<Map<String,Object>> interventi, List<Map<String,Object>> interventi1) {
		for (int i=0; interventi1 != null && i<interventi1.size(); i++) {
			Map<String,Object> inte1 = interventi1.get(i);
			boolean trovato = false;
			for (int j=0; interventi != null && j<interventi.size(); j++) {
				Map<String,Object> inte = interventi.get(j);
				if (inte.get("idInte").equals(inte1.get("idInte"))) {
					trovato = true;
				}
			}
			if (!trovato) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("idInte",inte1.get("idInte"));
				map.put("codiInte",inte1.get("codiInte"));
				map.put("descInte",inte1.get("descInte"));
				interventi.add(map);
			}
		}
	}
    
          
    private void preparaSezione(Collection<Map<String, ?>> l, Collection<Map<String, ?>> lista) {
    	for (Map<String,?>  map : l){  // si scorrono i campi restituiti dal cursore 
			lista.add(map);
		}
    }
    

    
    private ElencoPagamento getElencoPagamentoMaxDecr(List<ElencoPagamento> elencoPagamenti) {
    	ElencoPagamento elencoPagamentoMax = new ElencoPagamento();
    	elencoPagamentoMax.setIdDecr(0);
    	for (ElencoPagamento elencoPagamento : elencoPagamenti) {
    		if (elencoPagamento.getIdDecr()>elencoPagamentoMax.getIdDecr())
    			elencoPagamentoMax = elencoPagamento;
    	}
    	return elencoPagamentoMax;
    }
    
    
    class _FileName {
    	
    	private String name;
    	private String message;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
    	
    }
    
 
}
