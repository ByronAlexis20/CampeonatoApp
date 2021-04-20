package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the detallecalendario database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Detallecalendario.findAll", query="SELECT d FROM Detallecalendario d"),
	@NamedQuery(name="Detallecalendario.buscarPorPatron", 
	query="SELECT d FROM Detallecalendario d WHERE LOWER(d.calendario.campeonato.nombreC) LIKE LOWER(:patron) ORDER BY d.calendario.campeonato.nombreC"),
	@NamedQuery(name="Detallecalendario.buscarPorEquipo", 
	query="SELECT d FROM Detallecalendario d, Equipo e WHERE d.equipoLocal.idEquipo = e.idEquipo and LOWER(e.nombre) LIKE LOWER(:patron) ORDER BY e.nombre"),
	@NamedQuery(name="Detallecalendario.buscarPorIdCalendario", query="SELECT d FROM Detallecalendario d WHERE d.calendario.idCalendario = :patron GROUP BY d.equipoLocal.idEquipo"),
	@NamedQuery(name="Detallecalendario.buscarGrupoPorIdCalendario", query="SELECT d FROM Detallecalendario d WHERE d.calendario.idCalendario = :patron GROUP BY d.grupo"),
	@NamedQuery(name="Detallecalendario.AgruparFechaCalendarioByIdCalendario", query="SELECT d FROM Detallecalendario d WHERE d.calendario.idCalendario = :patron ORDER BY d.fecha")

})
@AdditionalCriteria("this.estado IS NULL")
public class Detallecalendario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detallecalendario")
	private int idDetallecalendario;

	private String grupo;

	private String dia;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Time hora;

	//bi-directional many-to-one association to Calendario
	@ManyToOne
	@JoinColumn(name="id_calendario")
	private Calendario calendario;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="id_equipolocal")
	private Equipo equipoLocal;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="id_equipovisitante")
	private Equipo equipoVisitante;

	//bi-directional many-to-one association to Lugarpartido
	@ManyToOne
	@JoinColumn(name="id_lugarP")
	private Lugarpartido lugarpartido;

	//bi-directional many-to-one association to Partido
	@OneToMany(mappedBy="detallecalendario")
	private List<Partido> partidos;
	
	//bi-directional many-to-one association to Tablaposicione
	@OneToMany(mappedBy="detallecalendario")
	private List<Tablaposicione> tablaposiciones;

	public Detallecalendario() {
	}

	public int getIdDetallecalendario() {
		return this.idDetallecalendario;
	}

	public void setIdDetallecalendario(int idDetallecalendario) {
		this.idDetallecalendario = idDetallecalendario;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Calendario getCalendario() {
		return this.calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}	

	public Lugarpartido getLugarpartido() {
		return this.lugarpartido;
	}

	public void setLugarpartido(Lugarpartido lugarpartido) {
		this.lugarpartido = lugarpartido;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public List<Tablaposicione> getTablaposiciones() {
		return tablaposiciones;
	}

	public void setTablaposiciones(List<Tablaposicione> tablaposiciones) {
		this.tablaposiciones = tablaposiciones;
	}

}