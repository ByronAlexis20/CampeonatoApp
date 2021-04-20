package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the cambios database table.
 * 
 */
@Entity
@Table(name="cambios")
@NamedQuery(name="Cambio.findAll", query="SELECT c FROM Cambio c")
public class Cambio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cambio")
	private int idCambio;

	private String estado;
	
	private String golpt;
	
	private String golst;

	private Time tiempo;

	//bi-directional many-to-one association to Partido
	@ManyToOne
	@JoinColumn(name="id_partido")
	private Partido partido;

	//bi-directional many-to-one association to Jugador
	@ManyToOne
	@JoinColumn(name="id_equipojugadorentra")
	private Equipojugador equipojugador1;

	//bi-directional many-to-one association to Jugador
	@ManyToOne
	@JoinColumn(name="id_equipojugadorsale")
	private Equipojugador equipojugador2;

	public Cambio() {
	}

	public int getIdCambio() {
		return this.idCambio;
	}

	public void setIdCambio(int idCambio) {
		this.idCambio = idCambio;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Time getTiempo() {
		return this.tiempo;
	}

	public void setTiempo(Time tiempo) {
		this.tiempo = tiempo;
	}

	public Partido getPartido() {
		return this.partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Equipojugador getEquipojugador1() {
		return equipojugador1;
	}

	public void setEquipojugador1(Equipojugador equipojugador1) {
		this.equipojugador1 = equipojugador1;
	}

	public Equipojugador getEquipojugador2() {
		return equipojugador2;
	}

	public void setEquipojugador2(Equipojugador equipojugador2) {
		this.equipojugador2 = equipojugador2;
	}

	public String getGolpt() {
		return golpt;
	}

	public void setGolpt(String golpt) {
		this.golpt = golpt;
	}

	public String getGolst() {
		return golst;
	}

	public void setGolst(String golst) {
		this.golst = golst;
	}
}