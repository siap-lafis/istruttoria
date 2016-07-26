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
import it.almaviva.siap.istruttoria.domain.Pagamento;
import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingControlliIstruttori;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingControlliIstruttoriDetAiuto;
import it.almaviva.siap.istruttoria.reports.CheckListReportMappingPrimaPagina;
import it.almaviva.siap.istruttoria.reports.CustomJRDataSource;
import it.almaviva.siap.istruttoria.repository.AduxstceRepository;
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
        
    @Inject
    private SoggettoRepository soggettoRepository;
    @Inject
    private PagamentoRepository pagamentoRepository;
    @Inject
    private AduxstceRepository  aduxstceRepository;
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
    
    
    
    
    // TODO: codice da rivedere per caricare i dati reali
    @RequestMapping(value = "/domanda/detail/report",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public _FileName generateReportCheckList(@RequestBody Domanda domanda) throws JRException, FileNotFoundException {
    	
            log.debug("REST request to save Report"); 
            boolean stored = true;
            /*********************************************************************************************************/
            // REALIZZAZIONE CON CHIAMATE STORED, 
            List controlli = dao.getControlliIstruttoriDU(domanda.getId(), 4, 2015); // decreto e annoCampagna fissi
            List interventi = dao.getListaInterventiRichiesti(domanda.getId(),4);	// decreto fisso
			List interventi1 = dao.getListaInterventiRichiesti1(domanda.getId(),2015);
			//aggiungiInterventiRichiesti(interventi, interventi1); // TODO: per il momento commentato
			List<InterventoLiquidato> importi = new ArrayList<InterventoLiquidato>();
			for (int i=0; i<interventi.size(); i++) {
				Map m = (Map)interventi.get(i);
				int idInte = ((Integer)m.get("idInte")).intValue();
				InterventoLiquidato intervento = dao.getDettaglioImportoLiquidato(domanda.getId(), 4, idInte);
				intervento.setCodiInte((String)m.get("codiInte"));
				intervento.setDescInte((String)m.get("descInte"));
				importi.add(intervento);
			}
            /****************************************************/
            List<Pagamento> pagamenti = pagamentoRepository.findByIdAttoAmmi(domanda.getIdDomanda());            
            //String provvisoria ="provvisoria";             	    
    	    String dir = "/src/main/webapp/content/reports/";
    	    File file = new File("");     
    		FileInputStream fileJasper = new FileInputStream(new File(file.getAbsolutePath()+dir+"checkListIstruttoria.jasper"));    
             		   		
    		Map<String,Object> subreport = new HashMap<String,Object>();

    		
    		String cli1 = file.getAbsolutePath()+dir+"cli1.jasper"; 		
    		String cli2 = file.getAbsolutePath()+dir+"cli2.jasper"; 
    		//String cli3 = file.getAbsolutePath()+dir+"cli2.jasper"; 
    		String cli4 = file.getAbsolutePath()+dir+"cli4.jasper"; 
    		String cli41 =file.getAbsolutePath()+ dir+"cli41.jasper"; 
    		String cli42 = file.getAbsolutePath()+dir+"cli42.jasper"; 
    		String cli43 = file.getAbsolutePath()+dir+"cli43.jasper"; 
    		String cli44 = file.getAbsolutePath()+dir+"cli44.jasper"; 
    		
    		subreport.put("cli1", cli1);
    		subreport.put("cli2", cli2);
//    		subreport.put("cli3", cli3);
    		subreport.put("cli4", cli4);
    		subreport.put("cli41", cli41);
    		subreport.put("cli42", cli42);
    		subreport.put("cli43", cli43);
    		subreport.put("cli44", cli44);
            
                              
            Map<String,Object> srMap = new HashMap<String,Object>();
    		
    		JRDataSource srDS;
    		JRMapCollectionDataSource cli1DS = null;
    		JRMapCollectionDataSource cli2DS = null;
    		JRMapCollectionDataSource cli3DS = null;
    		JRMapCollectionDataSource cli4DS = null;
   
    		Collection<Map<String, ?>> lista;
    		if (!stored)  {
    			Collection<Map<String, ?>> listaPrimaPagina = new CheckListReportMappingPrimaPagina().getMapping(domanda, pagamenti);    		          
    			cli1DS = new JRMapCollectionDataSource(listaPrimaPagina);
    			srMap.put("cli1DS", cli1DS);
    		}
    		
    		else {
	    		Collection<Map<String, ?>> listaPrimaPagina = new CheckListReportMappingPrimaPagina().preparaPagina1(domanda,interventi,pagamenti.get(0));
	    		lista = new ArrayList<Map<String, ?>>();
	            preparaSezione(listaPrimaPagina,lista);
	    		cli1DS = new JRMapCollectionDataSource(listaPrimaPagina);
	    		srMap.put("cli1DS", cli1DS);
    		}

    		
    		if (!stored)  {
    			List<String> _controlli = new ArrayList<String>();
    			_controlli.add("Controllo #1");
    			_controlli.add("Controllo #2");    				
    			Collection<Map<String, ?>> listaPaginaControlliIstruttori = new CheckListReportMappingControlliIstruttori().getMapping(domanda, _controlli);
    			cli2DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttori);
        		srMap.put("cli2DS", cli2DS);
    		}
    		else {
    			Collection<Map<String, ?>> listaPaginaControlliIstruttori = new CheckListReportMappingControlliIstruttori().preparaPagina2(domanda, controlli);  
    			lista = new ArrayList<Map<String, ?>>();  		
    			preparaSezione(listaPaginaControlliIstruttori,lista);
    			cli2DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttori);
        		srMap.put("cli2DS", cli2DS);
    		}
    		
    		
    		
    		if (!stored) {
    			Collection<Map<String, ?>> listaPaginaControlliIstruttoriDetAiuto = new CheckListReportMappingControlliIstruttoriDetAiuto().getMapping(domanda, pagamenti);           
    			cli4DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttoriDetAiuto);
    			srMap.put("cli4DS", cli4DS);
    		}
    		
    		else {
    			Collection<Map<String, ?>> listaPaginaControlliIstruttoriDetAiuto = new CheckListReportMappingControlliIstruttoriDetAiuto().preparaPagina4(domanda,importi,pagamenti.get(0));
    			cli4DS = new JRMapCollectionDataSource(listaPaginaControlliIstruttoriDetAiuto);
    			srMap.put("cli4DS", cli4DS);
    		}
    		
    		
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
    		_FileName f = new _FileName();
    		f.setName(fileName); 
    		return f;
        }
    
    
    private void aggiungiInterventiRichiesti(List interventi, List interventi1) {
		for (int i=0; interventi1 != null && i<interventi1.size(); i++) {
			Map inte1 = (Map)interventi1.get(i);
			boolean trovato = false;
			for (int j=0; interventi != null && j<interventi.size(); j++) {
				Map inte = (Map)interventi.get(j);
				if (inte.get("idInte").equals(inte1.get("idInte"))) {
					trovato = true;
				}
			}
			if (!trovato) {
				Map map = new HashMap();
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
    
    
    class _FileName {
    	
    	private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
    	
    }
    


}
