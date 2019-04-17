package co.sistemcobro.calendario.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Administra el cierre de Conexiones
 * 
 * @author Andres Diaz
 * 
 */
public class BaseDAO {

	private Logger logger = Logger.getLogger(BaseDAO.class);

	protected transient Connection con = null;
	protected transient PreparedStatement ps = null;
	protected transient ResultSet rs = null;
	protected transient CallableStatement cs = null;

	protected DataSource ds = null;

	public BaseDAO(DataSource ds2) {
		this.ds=ds2;
	}


	protected void closeConexion() {
		this.closeRS();
		this.closePS();
		this.closeCON();
		this.closeCS();
	}
	

	/**
	 * Cierra la conexión
	 */
	private void closeCON() {
		if (null != con) {
			try {
				con.close();
				// logger.info("Close > Connection: OK");
			} catch (SQLException e) {
				logger.error(e.toString(), e.fillInStackTrace());
			}
		}
	}

	/**
	 * @param stm
	 */
	private void closePS() {
		if (null != ps) {
			try {
				ps.close();
				// logger.info("Close > PreparedStatement: OK");
			} catch (SQLException e) {
				logger.error(e.toString(), e.fillInStackTrace());
			}
		}
	}

	/**
	 * @param rs
	 */
	private void closeRS() {
		if (null != rs) {
			try {
				rs.close();
				// logger.info("Close > ResultSet: OK");
			} catch (SQLException e) {
				logger.error(e.toString(), e.fillInStackTrace());
			}
		}
	}

	/**
	 * @param cs
	 */
	private void closeCS() {
		if (null != cs) {
			try {
				cs.close();
			} catch (SQLException e) {
				logger.error(e.toString(), e.fillInStackTrace());
			}
		}
	}
}