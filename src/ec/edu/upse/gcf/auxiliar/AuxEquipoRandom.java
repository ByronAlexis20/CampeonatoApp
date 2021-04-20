package ec.edu.upse.gcf.auxiliar;

import java.util.List;

public class AuxEquipoRandom {
	
	private String descripcion;
	private AuxFechaCampeonatoGenerado auxFechaCampeonatoGenerado;
	private List<AuxEncuentroEquipo> auxEncuentroEquipos;
	
	public AuxEquipoRandom() {
		
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<AuxEncuentroEquipo> getAuxEncuentroEquipos() {
		return auxEncuentroEquipos;
	}

	public void setAuxEncuentroEquipos(List<AuxEncuentroEquipo> auxEncuentroEquipos) {
		this.auxEncuentroEquipos = auxEncuentroEquipos;
	}

	public AuxFechaCampeonatoGenerado getAuxFechaCampeonatoGenerado() {
		return auxFechaCampeonatoGenerado;
	}

	public void setAuxFechaCampeonatoGenerado(AuxFechaCampeonatoGenerado auxFechaCampeonatoGenerado) {
		this.auxFechaCampeonatoGenerado = auxFechaCampeonatoGenerado;
	}

	
}
