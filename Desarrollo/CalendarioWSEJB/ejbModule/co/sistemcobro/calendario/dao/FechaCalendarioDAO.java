package co.sistemcobro.calendario.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import co.sistemcobro.calendario.dto.FechaCalendarioDTO;

public class FechaCalendarioDAO extends BaseDAO {

	public FechaCalendarioDAO(DataSource ds) {
		super(ds);
	}

	private static Logger logger = Logger.getLogger(FechaCalendarioDAO.class);

	public List<FechaCalendarioDTO> listaFechaCalendario( Integer idpais, String fechaFestivo,
			String observacion) throws Exception {

		List<FechaCalendarioDTO> lista = new ArrayList<>();
		FechaCalendarioDTO fechaCalendario = null;
		StringBuffer sql = new StringBuffer();

		try {

			sql.append(" SELECT idpais, fecha_festivo,  observacion ");
			sql.append(" FROM publicar.festivos ");
			sql.append(" WHERE idpais = 2 ");

			con = ds.getConnection();

			ps = con.prepareStatement(sql.toString());
			
	//		ps.setInt(1, idpais);

			rs = ps.executeQuery();

			while (rs.next()) {
				int t = 1;

				fechaCalendario = new FechaCalendarioDTO();

				fechaCalendario.setIdpais(rs.getInt(t++));
				fechaCalendario.setFechaFestivo(rs.getString(t++));
				fechaCalendario.setObservacion(rs.getString(t++));

				lista.add(fechaCalendario);
			}

		} catch (Exception e) {
			logger.error("Error SQL al tratar de leer   **" + sql.toString() + "** ", e);
			throw new Exception("Error SQL al tratar de leer   **" + sql.toString() + "** ", e);
		} finally {
			closeConexion();
			logger.info("finalizo dao listaFechaCalendario !");
		}

		return lista;

	}
}
