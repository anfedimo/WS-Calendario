package co.sistemcobro.calendario.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import co.sistemcobro.calendario.dto.AsignacionCalendarioDTO;
import co.sistemcobro.calendario.ejb.IAsignacioncalendarioEJBLocal;
import co.sistemcobro.calendario.ejb.IFechaCalendarioEJBLocal;
import co.sistemcobro.calendario.ejb.impl.AsignacionCalendarioEJB;

@Path("/AsignacionFecha")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AsignacionCalendarioWS {

	
	private Logger logger = Logger.getLogger(AsignacionCalendarioWS.class);
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	
	@EJB
	private IAsignacioncalendarioEJBLocal AsignacionCalendarioEJB;
	
	
	
	@GET
	@Path("/InsertarFecha")
	public Response insertarFecha(@QueryParam("idpais") Integer idpais,@QueryParam("fechafestivo") String fechafestivo, 
			@QueryParam("observacion") String observacion) {
		try {

			AsignacionCalendarioDTO asignacionDTO = new AsignacionCalendarioDTO();
			
			asignacionDTO.setIdpais(idpais);
			asignacionDTO.setFechaFestivo(fechafestivo);
			asignacionDTO.setObservacion(observacion);
			
			boolean bandera = AsignacionCalendarioEJB.insertarFecha(asignacionDTO);
			return Response.ok(JSON_MAPPER.writeValueAsString(bandera)).build();

		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}

	
	
	
}
