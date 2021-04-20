package ec.edu.upse.gcf.auxiliar;

import java.util.List;

public class AuxCalendario {

	private String descripcion;
	private String dia;
	private String fecha;
	private List<AuxEquipoGenerado> auxEquipoGenerados;
	
	public AuxCalendario() {
		
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<AuxEquipoGenerado> getAuxEquipoGenerados() {
		return auxEquipoGenerados;
	}

	public void setAuxEquipoGenerados(List<AuxEquipoGenerado> auxEquipoGenerados) {
		this.auxEquipoGenerados = auxEquipoGenerados;
	}
	
	
}
