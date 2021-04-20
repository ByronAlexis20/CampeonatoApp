package ec.edu.upse.gcf.reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;

import ec.edu.upse.gcf.dao.CampeonatoDAO;
import ec.edu.upse.gcf.dao.ClaseDAO;
import ec.edu.upse.gcf.dao.DetalleCampeonatoDAO;
import ec.edu.upse.gcf.modelo.Campeonato;
import ec.edu.upse.gcf.modelo.Detallecampeonato;
import ec.edu.upse.gcf.modelo.Equipo;
import ec.edu.upse.gcf.modelo.Equipojugador;
import ec.edu.upse.gcf.util.PrintReport;

public class reporteEquipos {
	ClaseDAO claseDAO = new ClaseDAO();
	private CampeonatoDAO campeonatoDao = new CampeonatoDAO();
	private DetalleCampeonatoDAO detallecampeonatoDao = new DetalleCampeonatoDAO();
	private Equipojugador equipojugador = new Equipojugador();
	
	private Campeonato campeonato;
	private Campeonato campeonatoSeleccionado;
	private Campeonato campeonatoseleccionada;
	private Campeonato campSeleccionado;
	private Campeonato campGolesSeleccionado;
	private Campeonato campTablaposicionSeleccionado;
	private Campeonato campCalendarioSeleccionado;
	private Equipo equiposeleccionada;
	
	
	private List<Equipo> listaequipos = new ArrayList<>();
	
	@Wire private Combobox cboCampeonato;

	@Command
	@GlobalCommand("EquipoJugadorEditar.listarequipos")
	@NotifyChange({"listaequipos", "campeonatoseleccionada"})
	public void listarequipos() {
		if(listaequipos != null) {		
			listaequipos.clear();
		}

		if(campeonatoseleccionada != null) {
			List<Detallecampeonato> opcion = detallecampeonatoDao.getCampeonatoEquipo(campeonatoseleccionada.getIdCampeonato());
			if(!opcion.isEmpty()) {
				for(Detallecampeonato detallecampeonato : opcion) {
					listaequipos.add(detallecampeonato.getEquipo());
				}
			}			
		}
	}
	
	@Command
	public void verReporteEquipos() {		
		// Crea un arreglo de parametros.
		Map<String, Object> parametros = new HashMap<String, Object>();
		// Coloca el parametro a pasar al reporte.
		parametros.put("nombreCampeonato",campeonatoSeleccionado.getNombreC());
		// Ejecuta el reporte.
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptInscripcionEquipos.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}	

	@Command
	public void verReporteJugadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nombreCampeonato",campSeleccionado.getNombreC());
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptInscripcionJugadores.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}	

	@Command
	public void verReporteListadoEquipos() {
		PrintReport.ejecutaReporte(campeonatoDao, "/recursos/reportes/rptListadoEquipos.jasper", null, PrintReport.FORMATO_PDF);
	}	
	
	@Command
	public void verReporteTarjetajugadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nombrE",equiposeleccionada.getNombre());
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptTarjetajugador.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}	
	
	@Command
	public void verReporteGolesJugadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nombreCamp",campGolesSeleccionado.getNombreC());
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptTablagoleadores.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}	
	
	@Command
	public void verReporteTablaposiciones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nombreCampeonato",campTablaposicionSeleccionado.getNombreC());
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptTablaposicion.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}
	
	@Command
	public void verReporteCalendario() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nombreCampeonatocal", campCalendarioSeleccionado.getNombreC());
		PrintReport.ejecutaReporte(campeonatoDao, 
				"/recursos/reportes/rptCronograma.jasper", 
				parametros, 
				PrintReport.FORMATO_PDF);
	}

	public List<Campeonato> getCampeonatos() {
		return campeonatoDao.getListaCampeonato();
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Campeonato getCampeonatoSeleccionado() {
		return campeonatoSeleccionado;
	}

	public void setCampeonatoSeleccionado(Campeonato campeonatoSeleccionado) {
		this.campeonatoSeleccionado = campeonatoSeleccionado;
	}

	public Campeonato getCampSeleccionado() {
		return campSeleccionado;
	}

	public void setCampSeleccionado(Campeonato campSeleccionado) {
		this.campSeleccionado = campSeleccionado;
	}

	public List<Equipo> getListaequipos() {
		return listaequipos;
	}

	public void setListaequipos(List<Equipo> listaequipos) {
		this.listaequipos = listaequipos;
	}

	public Campeonato getCampeonatoseleccionada() {
		return campeonatoseleccionada;
	}

	public void setCampeonatoseleccionada(Campeonato campeonatoseleccionada) {
		this.campeonatoseleccionada = campeonatoseleccionada;
	}

	public Equipojugador getEquipojugador() {
		return equipojugador;
	}

	public void setEquipojugador(Equipojugador equipojugador) {
		this.equipojugador = equipojugador;
	}

	public Equipo getEquiposeleccionada() {
		return equiposeleccionada;
	}

	public void setEquiposeleccionada(Equipo equiposeleccionada) {
		this.equiposeleccionada = equiposeleccionada;
	}

	public Campeonato getCampGolesSeleccionado() {
		return campGolesSeleccionado;
	}

	public void setCampGolesSeleccionado(Campeonato campGolesSeleccionado) {
		this.campGolesSeleccionado = campGolesSeleccionado;
	}

	public Campeonato getCampTablaposicionSeleccionado() {
		return campTablaposicionSeleccionado;
	}

	public void setCampTablaposicionSeleccionado(Campeonato campTablaposicionSeleccionado) {
		this.campTablaposicionSeleccionado = campTablaposicionSeleccionado;
	}

	public Campeonato getCampCalendarioSeleccionado() {
		return campCalendarioSeleccionado;
	}

	public void setCampCalendarioSeleccionado(Campeonato campCalendarioSeleccionado) {
		this.campCalendarioSeleccionado = campCalendarioSeleccionado;
	}
}
