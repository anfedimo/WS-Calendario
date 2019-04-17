package co.sistemcobro.calendario.ejb.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;

import co.sistemcobro.calendario.dao.FechaCalendarioDAO;
import co.sistemcobro.calendario.dto.FechaCalendarioDTO;
import co.sistemcobro.calendario.ejb.IFechaCalendarioEJBLocal;

@Stateless
public class FechaCalendarioEJB extends BaseEJB implements IFechaCalendarioEJBLocal {
	
	
	public List<FechaCalendarioDTO> listaFechaCalendario(Integer idpais, String fechaFestivo, 
			String observacion) throws Exception {
		FechaCalendarioDAO fechaCalendario = new FechaCalendarioDAO(dc_publicar_fecha);
		
		
		return fechaCalendario.listaFechaCalendario(idpais, fechaFestivo,  observacion  );
	}
	

}
