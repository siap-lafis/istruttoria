package it.almaviva.siap.istruttoria.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.reports.CustomJRDataSource;
import it.almaviva.siap.istruttoria.repository.SoggettoRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

}
