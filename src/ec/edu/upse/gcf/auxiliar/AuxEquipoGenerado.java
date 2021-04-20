package ec.edu.upse.gcf.auxiliar;

import java.sql.Time;

import ec.edu.upse.gcf.modelo.Equipo;

public class AuxEquipoGenerado {
	
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private Time hora;
	private String lugar;
	
	public AuxEquipoGenerado() {
		
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

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	

}
