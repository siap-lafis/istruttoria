package it.almaviva.siap.istruttoria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import oracle.jdbc.OracleTypes;
@Component
public class StoredProcedureDao extends JdbcDaoSupport {
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	@Autowired 
	public StoredProcedureDao(JdbcTemplate jdbcTemplate) {
	    super();
	    setJdbcTemplate(jdbcTemplate);
	}
	
	
	public List<Map<String,Object>> getControlliIstruttoriDU(long idAttoAmmi, int idDecr, int numeCamp) {
			
		RowMapper	 mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object> cli2 = new HashMap<String,Object>();
				cli2.put("progIstr", Integer.valueOf(rs.getInt("prog_istr")));
				cli2.put("clasCont", Integer.valueOf(rs.getInt("clas_cont")));
				cli2.put("descCont", rs.getString("desc_cont"));
				cli2.put("dataCont", rs.getTimestamp("data_cont"));
				return cli2;
			}
		};
		SimpleJdbcCall sjc = new SimpleJdbcCall(getJdbcTemplate());
		sjc.withCatalogName("aduaax001");  // nome del package
		sjc.withFunctionName("getControlliIstruttoriDU");  // nome della funzione
		sjc.withoutProcedureColumnMetaDataAccess();  // importante
		// I parametri vanno dichiarati nell'ordine in cui vengono richiesti dalla funzione
		sjc.declareParameters(new SqlOutParameter("return", OracleTypes.CURSOR, mapper),
							  new SqlParameter("idAttoAmmiIn", Types.NUMERIC),
		                      new SqlParameter("idDecrIn", Types.NUMERIC),
		                      new SqlParameter("numeCampIn", Types.NUMERIC));
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idAttoAmmiIn", idAttoAmmi);
		param.addValue("idDecrIn", idDecr);
		param.addValue("numeCampIn", numeCamp);
		Map<String,Object> result = sjc.execute(param);  // si esegue la funzione passandogli il parametro impostato 
		return  (List<Map<String,Object>>)result.get("return");  // si prende il parametro di output
            
	}
	
	public List<Map<String,Object>> getListaInterventiRichiesti(long idAttoAmmi, int idDecr) {
		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object>cli1 = new HashMap<String,Object>();
				cli1.put("idDecr", Integer.valueOf(rs.getInt("id_decr")));
				cli1.put("idInte", Integer.valueOf(rs.getInt("id_inte")));
				cli1.put("codiInte", rs.getString("codi_inte"));
				cli1.put("descInte", rs.getString("desc_inte"));
				cli1.put("unitMisu", rs.getString("unit_misu"));
				cli1.put("qntaRich", rs.getBigDecimal("qnta_rich"));
				cli1.put("qntaDete", rs.getBigDecimal("qnta_dete"));
				cli1.put("FLAG_PAGATO", rs.getInt("FLAG_PAGATO"));
				return cli1;
			}
		};
		SimpleJdbcCall sjc = new SimpleJdbcCall(getJdbcTemplate());
		sjc.withCatalogName("aduaax001");  // nome del package
		sjc.withFunctionName("getListaInterventiRichiesti");  // nome della funzione
		sjc.withoutProcedureColumnMetaDataAccess();  // importante
		// I parametri vanno dichiarati nell'ordine in cui vengono richiesti dalla funzione
		sjc.declareParameters(new SqlOutParameter("return", OracleTypes.CURSOR, mapper),
							  new SqlParameter("idAttoAmmiIn", Types.NUMERIC),
							  new SqlParameter("idDecrIn", Types.NUMERIC));
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idAttoAmmiIn", idAttoAmmi);
		param.addValue("idDecrIn", idDecr);
		Map<String,Object> result = sjc.execute(param);  // si esegue la funzione passandogli il parametro impostato 
		return  (List<Map<String,Object>>)result.get("return");  // si prende il parametro di output            
	}
	
	public List<Map<String,Object>> getListaInterventiRichiesti1(long idAttoAmmi,long annoCampagna) {
		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object> cli1 = new HashMap<String,Object>();
				cli1.put("idInte", Integer.valueOf(rs.getInt("id_inte")));
				cli1.put("codiInte", rs.getString("codi_inte"));
				cli1.put("descInte", rs.getString("desc_inte"));
				return cli1;
			}
		};
		SimpleJdbcCall sjc = new SimpleJdbcCall(getJdbcTemplate());
		sjc.withCatalogName("aduaax001");  // nome del package
		sjc.withFunctionName("getListaInterventiRichiesti1");  // nome della funzione
		sjc.withoutProcedureColumnMetaDataAccess();  // importante
		// I parametri vanno dichiarati nell'ordine in cui vengono richiesti dalla funzione
		sjc.declareParameters(new SqlOutParameter("return", OracleTypes.CURSOR, mapper),
							  new SqlParameter("idAttoAmmiIn", Types.NUMERIC),
							  new SqlParameter("anno_camp",Types.NUMERIC));
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idAttoAmmiIn", idAttoAmmi);
		param.addValue("anno_camp", annoCampagna);
		Map<String,Object> result = sjc.execute(param);  // si esegue la funzione passandogli il parametro impostato 
		return  (List<Map<String,Object>>)result.get("return");  // si prende il parametro di output
             
	}
	
	public InterventoLiquidato getDettaglioImportoLiquidato(long idAttoAmmi, int idDecr, int idInte) {
		RowMapper mapper1 = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				InterventoLiquidato liquidato = new InterventoLiquidato();
				liquidato.setQntaDich(rs.getBigDecimal("c551"));
				liquidato.setQntaRich(rs.getBigDecimal("c552"));
				liquidato.setImpoRich(rs.getBigDecimal("c554"));
				liquidato.setQntaDete(rs.getBigDecimal("c558").doubleValue());
				liquidato.setImpoDete(rs.getBigDecimal("c559").doubleValue());
				liquidato.setQntaRidu(rs.getBigDecimal("c560"));
				liquidato.setImpoRidu(rs.getBigDecimal("c561"));
				liquidato.setQntaSanz(rs.getBigDecimal("c640_qnta"));
				liquidato.setImpoSanz(rs.getBigDecimal("c640"));
				liquidato.setGiorRita(rs.getInt("nume_gior_rita"));
				liquidato.setRiduRitaDepo(rs.getBigDecimal("ridu_rita_depo"));
				liquidato.setRiduModu(rs.getBigDecimal("ridu_modu"));
				liquidato.setDiscFina(rs.getBigDecimal("disc_fina"));
				liquidato.setSanzCond(rs.getBigDecimal("sanz_cond"));
				liquidato.setQntaErogPrec(rs.getBigDecimal("qnta_erog_prec").doubleValue());
				liquidato.setImpoErogPrec(rs.getBigDecimal("impo_erog_prec").doubleValue());
				liquidato.setQntaLiqu(rs.getBigDecimal("qnta_liqu"));
				liquidato.setImpoLiqu(rs.getBigDecimal("impo_liqu"));
				liquidato.setUnita(rs.getString("unit_misu"));
				return liquidato;
			}
		};
		RowMapper mapper2 = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Riduzione riduzione = new Riduzione();
				riduzione.setTipologia(rs.getString("desc_pena"));
				riduzione.setClasse(rs.getString("classe"));
				riduzione.setEsito(rs.getString("effetto"));
				riduzione.setUnita(rs.getString("unit_misu"));
				riduzione.setQuantita(rs.getBigDecimal("qnta_pena"));
				riduzione.setImporto(rs.getBigDecimal("impo_pena"));
				return riduzione;
			}
		};
		SimpleJdbcCall sjc = new SimpleJdbcCall(getJdbcTemplate());
		sjc.withCatalogName("aduaax001");  // nome del package
		sjc.withProcedureName("getDettaglioImportoLiquidato");  // nome della funzione
		sjc.withoutProcedureColumnMetaDataAccess();  // importante
// I parametri vanno dichiarati nell'ordine in cui vengono richiesti dalla funzione
		sjc.declareParameters(new SqlParameter("idAttoAmmiIn", Types.NUMERIC),
							  new SqlParameter("idDecrIn", Types.NUMERIC),
							  new SqlParameter("idInteIn", Types.NUMERIC),
							  new SqlOutParameter("dettaglio_cursor", OracleTypes.CURSOR, mapper1),
							  new SqlOutParameter("nonriscontrato_cursor", OracleTypes.CURSOR, mapper2),
							  new SqlOutParameter("sanzioni_cursor", OracleTypes.CURSOR, mapper2));
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idAttoAmmiIn", idAttoAmmi);
		param.addValue("idDecrIn", idDecr);
		param.addValue("idInteIn", idInte);
		Map result = sjc.execute(param);  // si esegue la funzione passandogli il parametro impostato 
        List<InterventoLiquidato> l = (List)result.get("dettaglio_cursor");  // si prende il parametro di output
		InterventoLiquidato il = null;
		if (l == null || l.size() == 0) {
			il = new InterventoLiquidato();
		}	
		else {
			il = l.get(0);
		}
        List<Riduzione> ridu = (List)result.get("nonriscontrato_cursor");  // si prende il parametro di output
        List<Riduzione> sanz = (List)result.get("sanzioni_cursor");  // si prende il parametro di output
        il.setRiduzioni(ridu);
        il.setSanzioni(sanz);
        return il;
	}
	
	public Boolean  getEsclusa250Euro (long idAttoAmmi, int idDecr) {
		
		
		SimpleJdbcCall sjc = new SimpleJdbcCall(getJdbcTemplate());
		sjc.withCatalogName("aduaax001");  // nome del package
		sjc.withFunctionName("GetEsclusa250Euro");  // nome della funzione
		sjc.withoutProcedureColumnMetaDataAccess();  // importante
		// I parametri vanno dichiarati nell'ordine in cui vengono richiesti dalla funzione
		sjc.declareParameters(new SqlOutParameter("return", Types.INTEGER), 
							  new SqlParameter("idAttoAmmiIn", Types.NUMERIC),
							  new SqlParameter("idDecrIn", Types.NUMERIC));
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idAttoAmmiIn", idAttoAmmi);
		param.addValue("idDecrIn", idDecr);
		Map result = sjc.execute(param);  // si esegue la funzione passandogli il parametro impostato 
        int r = ((Integer)result.get("return")).intValue();  // si prende il parametro di output
        return (r==1);       
		
            
	}

	
}
