package co.sistemcobro.calendario.ejb.impl;

import javax.ejb.Stateless;

import co.sistemcobro.calendario.dao.AsignacionCalendarioDAO;
import co.sistemcobro.calendario.dto.AsignacionCalendarioDTO;
import co.sistemcobro.calendario.ejb.IAsignacioncalendarioEJBLocal;


@Stateless
public class AsignacionCalendarioEJB extends BaseEJB  implements IAsignacioncalendarioEJBLocal{

	@Override
	public  boolean insertarFecha(AsignacionCalendarioDTO asignacion)  throws Exception {
		AsignacionCalendarioDAO asignacionFecha = new AsignacionCalendarioDAO(dg_publicar_fecha);
		return asignacionFecha.insertarFecha(asignacion);
	}
	
}
