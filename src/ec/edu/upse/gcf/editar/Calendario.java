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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Vlayout;

import ec.edu.upse.gcf.auxiliar.AuxCalendario;
import ec.edu.upse.gcf.auxiliar.AuxEquipoGenerado;
import ec.edu.upse.gcf.general.MetodoGeneral;

public class Calendario {

	@Wire
	Vlayout gridCalendar;

	List<AuxCalendario> calendarios = new ArrayList<AuxCalendario>();
	
	@Init
	public void init(@ExecutionArgParam("calendarios") List<AuxCalendario> calendarios) {
		    
		if (calendarios != null) {
			this.calendarios = calendarios;			
		}			
	}
	
	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException{
		Selectors.wireComponents(view, this, false); 
		Integer columna = 7;
		gridCalendar.appendChild(getGrid(columna, calendarios));
	} 
	
	private Grid getGrid(Integer columna,List<AuxCalendario> lista) {
		Grid retorna = new Grid();
		retorna.setVflex("1");
		retorna.setHflex("1");
		
		/**
		 * agregamos los dias del campeonato
		 */
		Columns columns = new Columns();
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
		
		/**
		 * agregamos los partidos en cada fila
		 */
		Rows rows = new Rows();
		rows.setStyle("background:#122BC7;");
		Integer contadorRows = 0;
		Integer contadorInicio = 0;
		Row row = new Row(); 
		Integer semanaInicio = 0;
		
		for (AuxCalendario auxCalendario : lista) {
			if(contadorInicio == 0) {
				/**
				 * obtenemos el dia de la semana al iniciar el calendario
				 */
				semanaInicio = MetodoGeneral.getFechaDiaInteger(auxCalendario.getDia()); 	
				System.out.println("dia de la semana : "+semanaInicio);	
				if(semanaInicio <= 4) {
					Integer llenarCasilla = semanaInicio - 1;
					for (int i = 0; i < llenarCasilla; i++) {
						AuxCalendario objeto = new AuxCalendario();
						objeto.setAuxEquipoGenerados(new ArrayList<AuxEquipoGenerado>());
						Vlayout vlayout = getContainerRow(objeto);	
						row.appendChild(vlayout);
						contadorRows++;
					}
				}
				contadorInicio++;
			}
			
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
					hboxPartido.setDraggable("true");
					hboxPartido.setDroppable("true");
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
	public void salir(@ContextParam(ContextType.VIEW) Component view) { 
		view.detach();
	}

	public List<AuxCalendario> getCalendarios() {
		return calendarios;
	}

	public void setCalendarios(List<AuxCalendario> calendarios) {
		this.calendarios = calendarios;
	}
	
	
	
}
