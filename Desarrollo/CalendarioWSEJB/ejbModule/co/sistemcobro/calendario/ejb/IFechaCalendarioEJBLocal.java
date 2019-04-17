package co.sistemcobro.calendario.ejb;


import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;

import co.sistemcobro.calendario.dto.FechaCalendarioDTO;

@Local
public interface IFechaCalendarioEJBLocal {

	public List<FechaCalendarioDTO> listaFechaCalendario( Integer idpais, String fechaFestivo,
			String observacion) throws Exception;

}
