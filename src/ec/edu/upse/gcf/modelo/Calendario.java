package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the calendario database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Calendario.findAll", query="SELECT c FROM Calendario c"),
@NamedQuery(name="Calendario.buscarPorPatron", query="SELECT c FROM Calendario c "
		+ "WHERE LOWER(c.campeonato.nombreC) LIKE LOWER(:patron)")
})
@AdditionalCriteria("this.estado IS NULL")
public class Calendario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_calendario")
	private int idCalendario;

	private String estado;

	@Column(name="cantidad_grupo")
	private Integer cantidadGrupo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	//bi-directional many-to-one association to Campeonato
	@ManyToOne
	@JoinColumn(name="id_campeonato")
	private Campeonato campeonato;

	//bi-directional many-to-one association to Modalidad
	@ManyToOne
	@JoinColumn(name="id_modalidad")
	private Modalidad modalidad;

	//bi-directional many-to-one association to Detallecalendario
	@OneToMany(mappedBy="calendario",cascade = CascadeType.ALL)
	private List<Detallecalendario> detallecalendarios;

	
	public Calendario() {
	}

	public int getIdCalendario() {
		return this.idCalendario;
	}

	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getCantidadGrupo() {
		return this.cantidadGrupo;
	}

	public void setCantidadGrupo(Integer cantidadGrupo) {
		this.cantidadGrupo = cantidadGrupo;
	}

	public Campeonato getCampeonato() {
		return this.campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Modalidad getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Detallecalendario> getDetallecalendarios() {
		return this.detallecalendarios;
	}

	public void setDetallecalendarios(List<Detallecalendario> detallecalendarios) {
		this.detallecalendarios = detallecalendarios;
	}

	public Detallecalendario addDetallecalendario(Detallecalendario detallecalendario) {
		getDetallecalendarios().add(detallecalendario);
		detallecalendario.setCalendario(this);

		return detallecalendario;
	}

	public Detallecalendario removeDetallecalendario(Detallecalendario detallecalendario) {
		getDetallecalendarios().remove(detallecalendario);
		detallecalendario.setCalendario(null);

		return detallecalendario;
	}	

}