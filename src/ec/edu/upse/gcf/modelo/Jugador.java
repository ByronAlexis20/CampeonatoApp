package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the jugador database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Jugador.findAll", query="SELECT j FROM Jugador j"),
	@NamedQuery(name="Jugadores.buscarPorPatron", 
	query="SELECT j FROM Jugador j WHERE LOWER(j.nombres) LIKE LOWER(:patron) ORDER BY j.idJugador DESC"),
	@NamedQuery(name="Jugador.validaJugadorExistente", query="SELECT j FROM Jugador j WHERE j.identificacion = (:cedulaJugador)"),
	@NamedQuery(name="Jugador.jugadoresDisponibles", query="SELECT j FROM Jugador j WHERE  j.idJugador NOT IN (SELECT cj.jugador.idJugador FROM Categoriajugador cj WHERE cj.categoria = :categoria) ORDER BY j.nombres"),
	@NamedQuery(name="Jugador.jugadoresDisponiblesEquipoCampeonatos", query="SELECT j  FROM Jugador j WHERE j.idJugador NOT IN(SELECT ej.jugador.idJugador FROM Equipojugador ej WHERE ej.campeonato = :campeonato and ej.equipo = :equipo ) ORDER BY j.nombres ")	

})
@AdditionalCriteria("this.estado IS NULL")
public class Jugador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_jugador")
	private int idJugador;

	private String edad;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nac")
	private Date fechaNac;

	private String foto;

	private String genero;

	private String identificacion;

	private String nombres;

	private int numcamiseta;

	private String posicionjuego;	

	//bi-directional many-to-one association to Equipojugador
	@OneToMany(mappedBy="jugador")
	private List<Equipojugador> equipojugadors;

	//bi-directional many-to-one association to Detallecampeonato
	@OneToMany(mappedBy="jugador")
	private List<Categoriajugador> categoriajugadores;

	public Jugador() {
	}

	public int getIdJugador() {
		return this.idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public int getNumcamiseta() {
		return this.numcamiseta;
	}

	public void setNumcamiseta(int numcamiseta) {
		this.numcamiseta = numcamiseta;
	}

	public String getPosicionjuego() {
		return this.posicionjuego;
	}

	public void setPosicionjuego(String posicionjuego) {
		this.posicionjuego = posicionjuego;
	}

	public List<Categoriajugador> getCategoriajugadores() {
		return categoriajugadores;
	}

	public void setCategoriajugadores(List<Categoriajugador> categoriajugadores) {
		this.categoriajugadores = categoriajugadores;
	}

	public List<Equipojugador> getEquipojugadors() {
		return this.equipojugadors;
	}

	public void setEquipojugadors(List<Equipojugador> equipojugadors) {
		this.equipojugadors = equipojugadors;
	}

	public Equipojugador addEquipojugador(Equipojugador equipojugador) {
		getEquipojugadors().add(equipojugador);
		equipojugador.setJugador(this);

		return equipojugador;
	}

	public Equipojugador removeEquipojugador(Equipojugador equipojugador) {
		getEquipojugadors().remove(equipojugador);
		equipojugador.setJugador(null);

		return equipojugador;
	}

}