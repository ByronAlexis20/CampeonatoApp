package ec.edu.upse.gcf.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the detallepartido database table.
 * 
 */
@Entity
@NamedQuery(name="Detallepartido.findAll", query="SELECT d FROM Detallepartido d")
public class Detallepartido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_detallePartido;

	private String estado;
	
	private String estadojugador;

	private int golpt;

	private int golst;

	//bi-directional many-to-one association to Partido
	@ManyToOne
	@JoinColumn(name="id_partido")
	private Partido partido;

	//bi-directional many-to-one association to Equipojugador
	@ManyToOne
	@JoinColumn(name="id_equipojugador")
	private Equipojugador equipojugador;

	public Detallepartido() {
	}

	public int getId_detallePartido() {
		return this.id_detallePartido;
	}

	public void setId_detallePartido(int id_detallePartido) {
		this.id_detallePartido = id_detallePartido;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getGolpt() {
		return this.golpt;
	}

	public void setGolpt(int golpt) {
		this.golpt = golpt;
	}

	public int getGolst() {
		return this.golst;
	}

	public void setGolst(int golst) {
		this.golst = golst;
	}

	public Partido getPartido() {
		return this.partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Equipojugador getEquipojugador() {
		return this.equipojugador;
	}

	public void setEquipojugador(Equipojugador equipojugador) {
		this.equipojugador = equipojugador;
	}

	public String getEstadojugador() {
		return estadojugador;
	}

	public void setEstadojugador(String estadojugador) {
		this.estadojugador = estadojugador;
	}

}