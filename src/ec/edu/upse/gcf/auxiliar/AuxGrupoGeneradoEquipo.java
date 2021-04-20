package ec.edu.upse.gcf.auxiliar;

import java.util.List;

import ec.edu.upse.gcf.modelo.Equipo;

public class AuxGrupoGeneradoEquipo {

	private String descripcion;
	private List<Equipo> equipos;
	
	public AuxGrupoGeneradoEquipo() {
		
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	
	
}
