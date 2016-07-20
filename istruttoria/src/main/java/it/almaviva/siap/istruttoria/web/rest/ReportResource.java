package it.almaviva.siap.istruttoria.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.reports.CustomJRDataSource;
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
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public _FileName generateReportCheckList() throws JRException, FileNotFoundException {
            log.debug("REST request to save Report");
            /****************************************************/
            String provvisoria ="provvisoria";
            

    	    
    	    String dir = "/src/main/webapp/content/reports/";
    	    File file = new File("");     
    		FileInputStream fileJasper = new FileInputStream(new File(file.getAbsolutePath()+dir+"checkListIstruttoria.jasper"));    
            
    		
    		
    		HashMap subreport = new HashMap();
//    		String cli1 = getClass().getResource(dir+"cli1.jasper").getPath();    		
//    		String cli2 = getClass().getResource(dir+"cli2.jasper").getPath();
//    		String cli3 = getClass().getResource(dir+"cli3.jasper").getPath();
//    		String cli4 = getClass().getResource(dir+"cli4.jasper").getPath();
//    		String cli41 = getClass().getResource(dir+"cli41.jasper").getPath();
//    		String cli42 = getClass().getResource(dir+"cli42.jasper").getPath();
//    		String cli43 = getClass().getResource(dir+"cli43.jasper").getPath();
//    		String cli44 = getClass().getResource(dir+"cli44.jasper").getPath();
    		
    		String cli1 = file.getAbsolutePath()+dir+"cli1.jasper"; 		
    		String cli2 = file.getAbsolutePath()+dir+"cli2.jasper"; 
    		String cli3 = file.getAbsolutePath()+dir+"cli2.jasper"; 
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
            
            
            
            
            Map srMap = new HashMap();
    		ArrayList lista = null;
    		Map map = null;
    		JRDataSource srDS;
    		JRMapCollectionDataSource cli1DS = null;
    		JRMapCollectionDataSource cli2DS = null;
    		JRMapCollectionDataSource cli3DS = null;
    		JRMapCollectionDataSource cli4DS = null;
    /* scheda contiene i cursori (1 per ogni sezione) restituiti dalla procedure plsql
       lista contiene i dati da passare al datasource del report 
       c'e' una lista e quindi un datasource per ogni sezione
       i vari datasource confluiscono in un unico datasource srDS che viene passato al report */

    		lista = new ArrayList();
    		List l1 = new ArrayList(); //(List)scheda.get("cli1");
            preparaSezione(l1,lista);
    		cli1DS = new JRMapCollectionDataSource(lista);
    		srMap.put("cli1DS", cli1DS);

    		lista = new ArrayList();
    		List l2 = new ArrayList(); //(List)scheda.get("cli2");
            preparaSezione(l2,lista);
    		cli2DS = new JRMapCollectionDataSource(lista);
    		srMap.put("cli2DS", cli2DS);
    		
    		lista = new ArrayList();
    		List l3 = new ArrayList(); //(List)scheda.get("cli3");
            preparaSezione(l3,lista);
    		cli3DS = new JRMapCollectionDataSource(lista);
    		srMap.put("cli3DS", cli3DS);
    		
    		lista = new ArrayList();
    		List l4 = new ArrayList(); //(List)scheda.get("cli4");
            preparaSezione(l4,lista);
    		cli4DS = new JRMapCollectionDataSource(lista);
    		srMap.put("cli4DS", cli4DS);
    		
    		List srList = new ArrayList();
    		srList.add(srMap);
    		srDS = new JRMapCollectionDataSource(srList);
    		HashMap parametri = new HashMap();
    		parametri.put("cli1DS", cli1DS);
    		parametri.put("cli2DS", cli2DS);
    		parametri.put("cli3DS", cli3DS);
    		parametri.put("cli4DS", cli4DS);
    		// Va in errore il recupero del file
    		//parametri.put(provvisoria, "SI");
    		parametri.putAll(subreport);
    		Locale.setDefault(Locale.ITALY);
      		JasperPrint jasperPrint = JasperFillManager.fillReport(fileJasper, parametri, srDS);  
      		//pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            /****************************************************/
            
            
            
            
            
            
//    		JasperReport jasperReport = JasperCompileManager
//    				.compileReport("src/main/webapp/content/reports/report.jrxml");
//            List<Soggetto> soggettos = soggettoRepository.findAll();
//    		CustomJRDataSource<Soggetto> dataSource = new CustomJRDataSource<Soggetto>()
//    				.initBy(soggettos);
//    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
//    				dataSource);
      		String fileName = "reportChkLst_"+System.currentTimeMillis();
    		JasperExportManager.exportReportToPdfFile(jasperPrint,dir.substring(1)+fileName+".pdf");
    		_FileName f = new _FileName();
    		f.setName(fileName); 
    		//fileName;
    		return f;
        }
    
    private void preparaSezione(List l, List lista) {
    	for (int i=0; i<l.size(); i++) {  // si scorrono i campi restituiti dal cursore 
			Map map = new HashMap();
    		map.putAll((Map)l.get(i));
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
