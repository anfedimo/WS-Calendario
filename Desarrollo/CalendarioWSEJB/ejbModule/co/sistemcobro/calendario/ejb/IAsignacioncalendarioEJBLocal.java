package co.sistemcobro.calendario.ejb;

import javax.ejb.Local;

import co.sistemcobro.calendario.dto.AsignacionCalendarioDTO;

@Local
public interface IAsignacioncalendarioEJBLocal {

	public boolean insertarFecha(AsignacionCalendarioDTO asignacion) throws Exception;

}
