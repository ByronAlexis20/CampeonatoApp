package ec.edu.upse.gcf.editar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vlayout;

import ec.edu.upse.gcf.auxiliar.AuxCalendario;
import ec.edu.upse.gcf.auxiliar.AuxEquipoGenerado;
import ec.edu.upse.gcf.auxiliar.AuxGrupoGeneradoEquipo;
import ec.edu.upse.gcf.dao.DetallecalendarioDAO;
import ec.edu.upse.gcf.general.ConstanteApp;
import ec.edu.upse.gcf.general.MetodoGeneral;
import ec.edu.upse.gcf.modelo.Calendario;
import ec.edu.upse.gcf.modelo.Detallecalendario;
import ec.edu.upse.gcf.modelo.Detallecampeonato;
import ec.edu.upse.gcf.modelo.Equipo;

public class CalendarioDato {
	
	private DetallecalendarioDAO detalleCalendarioDAO = new DetallecalendarioDAO();
	private Calendario calendario;
	private String categoria; 
	private String tipoCampeonato;
	
	@Wire 
	Tree equipo;
	
	@Wire
	Div divlistaGrupo;
	
	@Wire
	Vlayout gridCalendar;
	
	@Init
	public void init(@ExecutionArgParam("calendario") Calendario calendario) {
		    
		if (calendario == null) {
			this.calendario = new Calendario();
		}else{
			this.calendario = calendario; 
			if(calendario.getCampeonato().getTipoCampeonato().equals(ConstanteApp.MASCULINO)) {
				tipoCampeonato = ConstanteApp.M;
			}else {
				tipoCampeonato = ConstanteApp.F;
			}
			int contador = 0;
			for(Detallecampeonato detallecampeonato:calendario.getCampeonato().getDetallecampeonatos()) { 
				if(contador == 0) {
					categoria = detallecampeonato.getCategoria().getNombre();  
					contador = contador + 1;
				}  
			}
			
		}
		 			
	}
 
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException {
	    Selectors.wireComponents(view, this, false);
	    equipo.appendChild(getTreechildren(calendario.getCampeonato().getDetallecampeonatos())); 
	    loadGrupoEquipoCalendario();
	    loadCalendario();
	}
	
	private void loadGrupoEquipoCalendario() {
		List<Detallecalendario> listaGrupoEquipo = detalleCalendarioDAO.getGrupoEquipoCalendario(calendario.getIdCalendario());
		List<Detallecalendario> listaGrupo = detalleCalendarioDAO.getGrupoCalendario(calendario.getIdCalendario());
		List<AuxGrupoGeneradoEquipo> listaEquipoGrupo = new ArrayList<>();
		
		for (Detallecalendario objeto : listaGrupo) {
			AuxGrupoGeneradoEquipo auxGrupoGeneradoEquipo = new AuxGrupoGeneradoEquipo();
			auxGrupoGeneradoEquipo.setDescripcion(objeto.getGrupo());
			List<Equipo> equipos = new ArrayList<>();
			for (Detallecalendario equipo : listaGrupoEquipo) {
				if(objeto.getGrupo().equals(equipo.getGrupo())) {
					equipos.add(equipo.getEquipoLocal());
				}
			} 
			auxGrupoGeneradoEquipo.setEquipos(equipos);
			listaEquipoGrupo.add(auxGrupoGeneradoEquipo);
		} 
		
		//mostramos los grupos con los equipos en listbox
		divlistaGrupo.appendChild(getHbo(listaEquipoGrupo));
	}
	
	private void loadCalendario() {
		List<Detallecalendario> calendarioFecha = detalleCalendarioDAO.getAgruparFechaCalendario(calendario.getIdCalendario());
		
		for (Detallecalendario detallecalendario : calendarioFecha) {
			System.out.println(detallecalendario.getFecha());
		}
	
		Integer columna = 7;
		List<AuxCalendario> listaCalendario = new ArrayList<AuxCalendario>();
		gridCalendar.appendChild(getGrid(columna, listaCalendario));
	}
	
	private Treechildren getTreechildren(List<Detallecampeonato> lista) throws IOException {
		try {
			Treechildren retorno =  new Treechildren();   
			Treechildren retornoEquipos = new Treechildren(); 
			Treeitem ti = new Treeitem(); 
			//Treeitem te = new Treeitem();
			int contador = 0;
			for(Detallecampeonato detallecampeonato:lista) { 
				if(contador == 0) {
					ti.setLabel("Equipos"); 
					retorno.appendChild(ti); 
					contador = contador + 1;
				}  
				
				Treeitem e = getTreeitemEquipo(detallecampeonato.getEquipo());
				retornoEquipos.appendChild(e); 
				ti.appendChild(retornoEquipos);
				ti.setOpen(false);
			 
			}
			
			return retorno;	
		} catch (Exception e) {
			return null; 
		}	
		
	}
	
	private Treeitem getTreeitemEquipo(Equipo equipo) throws IOException {
		Treeitem retorno = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tc = new Treecell(equipo.getNombre());		 
		tr.appendChild(tc);
		retorno.appendChild(tr);
		retorno.setOpen(false);
		return retorno;
	}
	
	/**
	 * Metodo para listar los grupos con los equipos
	 * @param lista
	 * @return
	 */ 
	@NotifyChange({"divlistaGrupo"})
	private Hbox getHbo(List<AuxGrupoGeneradoEquipo> lista) {
		Hbox retorna = new Hbox();
		retorna.setHflex("1");
		retorna.setVflex("1");
		for (AuxGrupoGeneradoEquipo auxGrupoGeneradoEquipo : lista) {
			Listbox listbox = new Listbox();
			listbox.setHflex("1");
			listbox.setVflex("1");
			
			Listhead head = new Listhead();
			head.setStyle("background:#122BC7;");
			head.setSizable(true);
			
			Listheader header = new Listheader();
			header.setStyle("background:#122BC7;");
			header.setLabel(auxGrupoGeneradoEquipo.getDescripcion());
			header.setHflex("1");
			head.appendChild(header);
			
			for (Equipo equipo : auxGrupoGeneradoEquipo.getEquipos()) {
				Listitem item = new Listitem();
				Listcell cell = new Listcell();
				cell.setLabel(equipo.getNombre());
				item.appendChild(cell);
				listbox.appendChild(item);
			}			
			listbox.appendChild(head);
			retorna.appendChild(listbox);
		}
		
		return retorna;
	}
	
	private Grid getGrid(Integer columna,List<AuxCalendario> lista) {
		Grid retorna = new Grid();
		retorna.setVflex("1");
		retorna.setHflex("1");
		
		//agregamos los dias del campeonato
		Columns columns = new Columns();
		columns.setStyle("background:#122BC7;");
		for(int i=0 ; i<columna; i++) {
			Column column = new Column();
			column.setStyle("background:#122BC7;");
			if((i+1) < columna) {
				column.setLabel(MetodoGeneral.getFechaDiaString((i+1)));
				column.setHflex("min");
				column.setAlign("center");
				columns.appendChild(column);
			}else {
				column.setLabel(MetodoGeneral.getFechaDiaString(0));
				column.setHflex("min");
				column.setAlign("center");
				columns.appendChild(column);
			}			
		}
		
		//agregamos los partidos en cada fila
		Rows rows = new Rows();
		rows.setStyle("background:#122BC7;");
		Integer contadorRows = 0;
		Row row = new Row(); 
		for (AuxCalendario auxCalendario : lista) {
			 
			if(contadorRows < columna) {
			  Vlayout vlayout = getContainerRow(auxCalendario);	
			  row.appendChild(vlayout);
			}else {
			  row = new Row();
			  Vlayout vlayout = getContainerRow(auxCalendario);	
			  row.appendChild(vlayout);
			  contadorRows = 0;
			}
					
			rows.appendChild(row);
			contadorRows++;
		}	
		
		retorna.appendChild(rows);
		retorna.appendChild(columns);
		return retorna;
	}
	
	/**
	 * retorna el contenido del calendario en el windows
	 * @param object
	 * @return
	 */
	private Vlayout getContainerRow(AuxCalendario calendario) {
		
		//agrupamos los partidos de encuentro
		Vlayout retorna = new Vlayout();
	 
		Label labelFecha = new Label();
		labelFecha.setStyle("cursor:pointer; font-weight: bold;");
		labelFecha.setValue(calendario.getFecha());
		labelFecha.setHflex("1");
		retorna.appendChild(labelFecha);
		if(calendario.getAuxEquipoGenerados().size() > 0) {
		 
			for (AuxEquipoGenerado auxEquipoGenerado : calendario.getAuxEquipoGenerados()) {
	
				if(auxEquipoGenerado.getEquipoLocal() != null && auxEquipoGenerado.getEquipoVisitante() != null) {
			 	
					//partidos de enfrentamiento
					Hbox hboxPartido = new Hbox();
					hboxPartido.setAlign("center");
					hboxPartido.setHflex("1");
					hboxPartido.setStyle("border: 1px solid #C6C6C6; border-radius: 15px; padding:5px;");
					Label labelEquipoLocal = new Label();
					labelEquipoLocal.setValue("");
					
					//equipos
					Vlayout vlayoutPartido = new Vlayout();
					vlayoutPartido.setStyle("text-align: center;");
						
					Label labelPartidoLocal = new Label();
					labelPartidoLocal.setValue(auxEquipoGenerado.getEquipoLocal().getNombre());
					labelPartidoLocal.setStyle("cursor:pointer; padding:10px; text-align: center;");
					
					Label labelVersus = new Label();
					labelVersus.setValue("vs");
					labelVersus.setStyle("cursor:pointer; text-align: center; font-weight: bold;");
					
					Label labelPartidoVisitante = new Label();
					labelPartidoVisitante.setValue(auxEquipoGenerado.getEquipoVisitante().getNombre());
				    labelPartidoVisitante.setStyle("cursor:pointer; text-align: center; padding:10px;");
				   
				    vlayoutPartido.appendChild(labelPartidoVisitante);
				    vlayoutPartido.appendChild(labelVersus);
				    vlayoutPartido.appendChild(labelPartidoLocal);
				    
				    //detalle del partido
				    Vlayout vlayoutDetalle = new Vlayout();
				    vlayoutDetalle.setStyle("text-align: center; padding:5px;");
				    Label labelHora = new Label();
				    labelHora.setValue("Hora");
				    labelHora.setHflex("1");
				    labelHora.setStyle("cursor:pointer; font-weight: bold;");
				    
				    Timebox timebox = new Timebox();
				    timebox.setWidth("123px");
				    
				    vlayoutDetalle.appendChild(labelHora);
				    vlayoutDetalle.appendChild(timebox);
				    				    				   
				    hboxPartido.appendChild(vlayoutPartido);
				    hboxPartido.appendChild(vlayoutDetalle);
				    retorna.appendChild(hboxPartido);
				}   
			}
		} 
		return retorna;	
	}
	
	@Command
	public void grabar(@ContextParam(ContextType.VIEW) Component view) { 
		view.detach();
	}
	
	@Command
	public void salir(@ContextParam(ContextType.VIEW) Component view) { 
		view.detach();
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTipoCampeonato() {
		return tipoCampeonato;
	}

	public void setTipoCampeonato(String tipoCampeonato) {
		this.tipoCampeonato = tipoCampeonato;
	}

}
