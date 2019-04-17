package co.sistemcobro.calendario.dto;

import java.sql.Timestamp;

public class FechaCalendarioDTO {

//	private Integer idfestivo;
	private Integer idpais;
	private String fechaFestivo;
	private String observacion;
	

//	public Integer getIdfestivo() {
//		return idfestivo;
//	}
//
//	public void setIdfestivo(Integer idfestivo) {
//		this.idfestivo = idfestivo;
//	}

	public Integer getIdpais() {
		return idpais;
	}

	public void setIdpais(Integer idpais) {
		this.idpais = idpais;
	}

	

	public String getFechaFestivo() {
		return fechaFestivo;
	}

	public void setFechaFestivo(String fechaFestivo) {
		this.fechaFestivo = fechaFestivo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
