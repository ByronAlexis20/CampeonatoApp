package ec.edu.upse.gcf.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import java.util.List;


/**
 * The persistent class for the opcion database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Opcion.findAll", query="SELECT o FROM Opcion o"),

@NamedQuery(name="Opcion.BuscaOpcionMenu", query="SELECT o FROM Opcion o WHERE o.idOpcionPadre IS NULL ORDER BY o.posicion "),

@NamedQuery(name="Opcion.opcionPorPerfilPadre", query="SELECT o  FROM Opcion o WHERE o.idOpcionPadre IS NULL  "
		+ "AND o.idOpcion IN (SELECT a.opcion.idOpcion FROM Opcionperfil a WHERE a.perfil.idPerfil = :perfil) ORDER BY o.posicion"),

@NamedQuery(name="Opcion.opcionPorPerfilHijo", query="SELECT o  FROM Opcion o WHERE o.idOpcionPadre IS "
		+ "NOT NULL  AND o.idOpcionPadre = :opcionPadre AND o.idOpcion IN (SELECT a.opcion.idOpcion FROM "
		+ "Opcionperfil a WHERE a.perfil.idPerfil = :perfil) ORDER BY o.posicion"),


@NamedQuery(name="Opcion.opcionPadreHijo", query="SELECT o  FROM Opcion o WHERE o.idOpcionPadre IS NOT NULL  AND o.idOpcionPadre = :idOpcionPadre ORDER BY o.posicion"),


@NamedQuery(name="Opcion.opcionPadre", query="SELECT o  FROM Opcion o WHERE o.idOpcionPadre IS NULL  ORDER BY o.posicion"),

@NamedQuery(name="Opcion.opcionesDisponibles", query="SELECT o  FROM Opcion o WHERE o.url <> NULL and "
		+ "o.idOpcion NOT IN (SELECT a.opcion.idOpcion FROM Opcionperfil a WHERE a.perfil = :perfil) ORDER BY o.posicion"),

@NamedQuery(name="Opcion.opcDisp", query="SELECT o  FROM Opcion o WHERE LOWER(o.titulo) LIKE LOWER(:patron)"),

@NamedQuery(name="Opciones.buscarPorPatron", query="SELECT o FROM Opcion o WHERE LOWER(o.titulo) LIKE LOWER(:patron)"),

@NamedQuery(name="Opcion.opcionMenu", query="SELECT o  FROM Opcion o WHERE o.idOpcion = :idOpcion ORDER BY o.posicion "),

@NamedQuery(name="Opcion.menuHijoPadre", query="SELECT o  FROM Opcion o WHERE o.idOpcionPadre = :idPadre ORDER BY o.posicion"),

@NamedQuery(name="Opcion.BuscarSubmenu", 
query="SELECT o FROM Opcion o WHERE o.idOpcionPadre = :opcionmenu ORDER BY o.posicion")

})

@AdditionalCriteria("this.estado IS NULL ")
public class Opcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_opcion")
	private int idOpcion;

	private String estado;

	@Column(name="id_opcion_padre")
	private Integer idOpcionPadre;
	
	@Column(name="titulo_mostrar")
	private String tituloMostrar;
	
	private int posicion;

	private String imagen;

	private String titulo;

	private String url;

	//bi-directional many-to-one association to Opcionperfil
	@OneToMany(mappedBy="opcion")
	private List<Opcionperfil> opcionperfils;

	public Opcion() {
	}

	public int getIdOpcion() {
		return this.idOpcion;
	}

	public void setIdOpcion(int idOpcion) {
		this.idOpcion = idOpcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdOpcionPadre() {
		return this.idOpcionPadre;
	}

	public void setIdOpcionPadre(Integer idOpcionPadre) {
		this.idOpcionPadre = idOpcionPadre;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Opcionperfil> getOpcionperfils() {
		return this.opcionperfils;
	}

	public void setOpcionperfils(List<Opcionperfil> opcionperfils) {
		this.opcionperfils = opcionperfils;
	}

	public Opcionperfil addOpcionperfil(Opcionperfil opcionperfil) {
		getOpcionperfils().add(opcionperfil);
		opcionperfil.setOpcion(this);

		return opcionperfil;
	}

	public Opcionperfil removeOpcionperfil(Opcionperfil opcionperfil) {
		getOpcionperfils().remove(opcionperfil);
		opcionperfil.setOpcion(null);

		return opcionperfil;
	}
	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getTituloMostrar() {
		return tituloMostrar;
	}

	public void setTituloMostrar(String tituloMostrar) {
		this.tituloMostrar = tituloMostrar;
	}

	@Override
	public String toString() {
		return this.titulo;
	}

}