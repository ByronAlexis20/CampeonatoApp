package ec.edu.upse.gcf.listas;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.dao.CalendarioDAO;
import ec.edu.upse.gcf.modelo.Calendario;

@SuppressWarnings({"unchecked","rawtypes"})
public class CalendarioLista {

	private CalendarioDAO calendarioDAO = new CalendarioDAO();

	private Calendario calendario;
	private List<Calendario> calendarios;
	private String textoBuscar;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		textoBuscar = "";
	}

	@Command
	@GlobalCommand("CalendarioLista.buscar")
	@NotifyChange({"calendarios", "calendario"})
	public void buscar() {
		/*if(calendarios == null) {
			Clients.showNotification("No hay datos para mostrar.!!");
		}*/
			String patron = textoBuscar; 
			calendarios = calendarioDAO.getCalendarios(patron);	
			calendario = null;
		
	}	

	@Command
	public void nuevo() {
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/calendario/calendarioEdita.zul", null, null);
		ventanaCargar.doModal(); 
	}

	@Command
	public void editar() {	   
		HashMap<String,Object> parametros = new HashMap<String, Object>();
		parametros.put("calendario", calendario);
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/calendario/calendarioDato.zul", null, parametros);
		ventanaCargar.doModal(); 	
	}


	@Command
	public void eliminar() {

		Messagebox.show("¿Está seguro de eliminar el registro?", "Confirmación", 
				Messagebox.YES|Messagebox.NO, Messagebox.QUESTION,new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {

					} catch (Exception e) {
						e.printStackTrace(); 
					}finally {
						BindUtils.postGlobalCommand(null, null, "CalendarioLista.buscar", null);
					}
				}else if (event.getName().equals("onNo")) {
					BindUtils.postGlobalCommand(null, null, "CalendarioLista.buscar", null);
				}

			}
		});



	}


	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public List<Calendario> getCalendarios() {
		return calendarios;
	}

	public void setCalendarios(List<Calendario> calendarios) {
		this.calendarios = calendarios;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}



}
