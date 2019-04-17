package co.sistemcobro.calendario.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import co.sistemcobro.calendario.dto.FechaCalendarioDTO;
import co.sistemcobro.calendario.ejb.IFechaCalendarioEJBLocal;

@Path("/CalendarioAsignado")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FechaCalendarioWS {

	private Logger logger = Logger.getLogger(FechaCalendarioWS.class);
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	@EJB
	private IFechaCalendarioEJBLocal FechaCalendarioEJB;

	@GET
	@Path("/ListaFechaCalendario")
	public Response listaFechaCalendario(@QueryParam("idpais") Integer idpais,
			@QueryParam("fechaFestivo") String fechaFestivo,
			@QueryParam("observacion") String observacion) {
		try {

			List<FechaCalendarioDTO> lista = new ArrayList<>();
			lista = FechaCalendarioEJB.listaFechaCalendario( idpais, fechaFestivo, observacion);

			return Response.ok(JSON_MAPPER.writeValueAsString(lista)).build();

		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
