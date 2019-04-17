package co.sistemcobro.calendario.ejb.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Conexiones
 * 
 * @author David Esguerra
 * 
 */
public class BaseEJB {
	

	
	@Resource(mappedName = "dc_publicar_fecha")
	protected DataSource dc_publicar_fecha;
	
	@Resource(mappedName = "dg_publicar_fecha")
	protected DataSource dg_publicar_fecha;
	
	
	
	
}
