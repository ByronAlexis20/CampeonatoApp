package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.AdditionalCriteria;


/**
 * The persistent class for the partido database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Partido.findAll", query="SELECT p FROM Partido p"),
	@NamedQuery(name="Partido.buscarPorPatron", 
	query="SELECT p FROM Partido p, Detallecalendario d WHERE p.detallecalendario.idDetallecalendario = d.idDetallecalendario and LOWER(d.equipoLocal.nombre) LIKE LOWER(:patron)"),
})
@AdditionalCriteria("this.estado IS NULL")
public class Partido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_partido")
	private int idPartido;

	private String arbitro;

	private String estado;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha")
	private Date fecha;

	private Time hora;

	private String novedadarbitrolocal;
	
	private String novedadarbitrovisitante;
	
	private String novedadvocal;

	private String vocal;

	private int resultadoequipoL;

	private int resultadoequipoV;

	//bi-directional many-to-one association to Cambio
	@OneToMany(mappedBy="partido", cascade = CascadeType.ALL)
	private List<Cambio> cambios;

	//bi-directional many-to-one association to Cambio
	@OneToMany(mappedBy="partido", cascade = CascadeType.ALL)
	private List<Cambio> cambiosv;

	//bi-directional many-to-one association to Detallepartido
	@OneToMany(mappedBy="partido", cascade = CascadeType.ALL)
	private List<Detallepartido> detallepartidos;

	//bi-directional many-to-one association to Tablaposicione
	@OneToMany(mappedBy="partido", cascade = CascadeType.ALL)
	private List<Tablaposicione> tablaposiciones;

	//bi-directional many-to-one association to Calendario
	@ManyToOne
	@JoinColumn(name="id_detallecalendario")
	private Detallecalendario detallecalendario;

	//bi-directional many-to-one association to Tarjetajugador
	@OneToMany(mappedBy="partido", cascade = CascadeType.ALL)
	private List<Tarjetajugador> tarjetajugadors;

	public Partido() {
	}

	public int getIdPartido() {
		return this.idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public String getArbitro() {
		return this.arbitro;
	}

	public void setArbitro(String arbitro) {
		this.arbitro = arbitro;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getNovedadarbitrolocal() {
		return novedadarbitrolocal;
	}

	public void setNovedadarbitrolocal(String novedadarbitrolocal) {
		this.novedadarbitrolocal = novedadarbitrolocal;
	}

	public String getNovedadarbitrovisitante() {
		return novedadarbitrovisitante;
	}

	public void setNovedadarbitrovisitante(String novedadarbitrovisitante) {
		this.novedadarbitrovisitante = novedadarbitrovisitante;
	}

	public String getNovedadvocal() {
		return this.novedadvocal;
	}

	public void setNovedadvocal(String novedadvocal) {
		this.novedadvocal = novedadvocal;
	}

	public String getVocal() {
		return this.vocal;
	}

	public void setVocal(String vocal) {
		this.vocal = vocal;
	}

	public List<Cambio> getCambios() {
		return this.cambios;
	}

	public void setCambios(List<Cambio> cambios) {
		this.cambios = cambios;
	}

	public Cambio addCambio(Cambio cambio) {
		getCambios().add(cambio);
		cambio.setPartido(this);

		return cambio;
	}

	public Cambio removeCambio(Cambio cambio) {
		getCambios().remove(cambio);
		cambio.setPartido(null);

		return cambio;
	}

	public List<Detallepartido> getDetallepartidos() {
		return this.detallepartidos;
	}

	public void setDetallepartidos(List<Detallepartido> detallepartidos) {
		this.detallepartidos = detallepartidos;
	}

	public Detallepartido addDetallepartido(Detallepartido detallepartido) {
		getDetallepartidos().add(detallepartido);
		detallepartido.setPartido(this);

		return detallepartido;
	}

	public Detallepartido removeDetallepartido(Detallepartido detallepartido) {
		getDetallepartidos().remove(detallepartido);
		detallepartido.setPartido(null);

		return detallepartido;
	}


	public Detallecalendario getDetallecalendario() {
		return detallecalendario;
	}

	public void setDetallecalendario(Detallecalendario detallecalendario) {
		this.detallecalendario = detallecalendario;
	}

	public List<Tarjetajugador> getTarjetajugadors() {
		return this.tarjetajugadors;
	}

	public void setTarjetajugadors(List<Tarjetajugador> tarjetajugadors) {
		this.tarjetajugadors = tarjetajugadors;
	}

	public Tarjetajugador addTarjetajugador(Tarjetajugador tarjetajugador) {
		getTarjetajugadors().add(tarjetajugador);
		tarjetajugador.setPartido(this);

		return tarjetajugador;
	}

	public Tarjetajugador removeTarjetajugador(Tarjetajugador tarjetajugador) {
		getTarjetajugadors().remove(tarjetajugador);
		tarjetajugador.setPartido(null);

		return tarjetajugador;
	}

	public List<Tablaposicione> getTablaposiciones() {
		return tablaposiciones;
	}

	public void setTablaposiciones(List<Tablaposicione> tablaposiciones) {
		this.tablaposiciones = tablaposiciones;
	}

	public int getResultadoequipoL() {
		return resultadoequipoL;
	}

	public void setResultadoequipoL(int resultadoequipoL) {
		this.resultadoequipoL = resultadoequipoL;
	}

	public int getResultadoequipoV() {
		return resultadoequipoV;
	}

	public void setResultadoequipoV(int resultadoequipoV) {
		this.resultadoequipoV = resultadoequipoV;
	}

	public List<Cambio> getCambiosv() {
		return cambiosv;
	}

	public void setCambiosv(List<Cambio> cambiosv) {
		this.cambiosv = cambiosv;
	}

}