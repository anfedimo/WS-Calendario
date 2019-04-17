package co.sistemcobro.calendario.dto;

import java.sql.Timestamp;

public class AsignacionCalendarioDTO {

	private Integer idfestivo;
	private Integer idpais;
	private String fechaFestivo;
	private String observacion;
	private Timestamp fechacrea;
	private Integer idusuariocrea;
	private Integer idusuariomod;
	private Timestamp fechamod;
	private Integer estado;

	public Integer getIdfestivo() {
		return idfestivo;
	}

	public void setIdfestivo(Integer idfestivo) {
		this.idfestivo = idfestivo;
	}

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

	public Timestamp getFechacrea() {
		return fechacrea;
	}

	public void setFechacrea(Timestamp fechacrea) {
		this.fechacrea = fechacrea;
	}

	public Integer getIdusuariocrea() {
		return idusuariocrea;
	}

	public void setIdusuariocrea(Integer idusuariocrea) {
		this.idusuariocrea = idusuariocrea;
	}

	public Integer getIdusuariomod() {
		return idusuariomod;
	}

	public void setIdusuariomod(Integer idusuariomod) {
		this.idusuariomod = idusuariomod;
	}

	public Timestamp getFechamod() {
		return fechamod;
	}

	public void setFechamod(Timestamp fechamod) {
		this.fechamod = fechamod;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
