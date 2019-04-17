package co.sistemcobro.calendario.dao;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import co.sistemcobro.calendario.dto.AsignacionCalendarioDTO;



public class AsignacionCalendarioDAO extends BaseDAO {

	public AsignacionCalendarioDAO(DataSource ds) {
		super(ds);
	}
	
	private static Logger logger = Logger.getLogger(AsignacionCalendarioDAO.class);
	
	
	public boolean insertarFecha(AsignacionCalendarioDTO asignacion)   throws Exception{
		boolean bandera = false;
		try {
			String query = "  insert into publicar.festivos    "
						 + "  (idpais,fecha_festivo,observacion,fecha_crea,idusuario_crea,estado )   "
						 + "  values(?,getDate(),?,getDate(),?,2)    ";
			
				con = ds.getConnection();

				ps = con.prepareStatement(query);
				
				int t = 1;
				
				ps.setInt(t++, asignacion.getIdpais());
				ps.setString(t++, asignacion.getFechaFestivo());
				ps.setString(t++, asignacion.getObservacion());
		
				
				ps.executeUpdate();
				bandera = true;
				
		} catch (Exception e) {
			logger.error("Error SQL al tratar de insertar   ", e);
			throw new Exception("Error SQL al tratar de insertar   ", e);
		}finally {
			closeConexion();
			logger.info("finalizo dao!");
		}
		return bandera;
	}

}
