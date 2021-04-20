package ec.edu.upse.gcf.editar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.dao.PartidoDAO;
import ec.edu.upse.gcf.modelo.Detallecalendario;
import ec.edu.upse.gcf.modelo.Partido;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PartidoEditar {	

	public String textoBuscar;
	private PartidoEditar partidoE;	
	private Partido partidoSeleccionado;
	private PartidoDAO partidoDao = new PartidoDAO();
	//private DetallecalendarioDAO detallecalendarioDao = new DetallecalendarioDAO();
	private List<Partido> partido;
	private List<Detallecalendario> detacalendario;
	private Detallecalendario detallecalendarioSeleccionada;	

	@Wire
	Button btnEditar,btnEliminar;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		textoBuscar="";
		partidoE = (PartidoEditar) Executions.getCurrent().getArg().get("Partido");	
		deshabilitarBoton();
	}

	private void deshabilitarBoton() {
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}

	@Command
	public void buscarPartido(){
		System.out.println("buscando ventana");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Detallecalendario", null);
		//Context.getInstance().setGrupoEditar(this);
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/partidos/buscarPartido.zul", null, params);
		ventanaCargar.doModal();
	}

	@GlobalCommand("Partido.buscarPorPatron")
	@Command
	@NotifyChange({"partidos", "partidoSeleccionado"})
	public void buscar(){
		if (partido != null) {
			partido = null; 
		}
		partido = partidoDao.getPartidos(textoBuscar);
		if(partido.size() == 0) {
			Clients.showNotification("No hay datos para mostrar.!!");
		}else {
			partidoSeleccionado = null;
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}		
	}

	@Command
	public void editar(){
		if(partidoSeleccionado == null) {
			Clients.showNotification("Seleccione una opción de la lista.");
			return;
		}
		partidoDao.getEntityManager().refresh(partidoSeleccionado);		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Partido", partidoSeleccionado);
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/partidos/ingresoResultado.zul", null, params);
		ventanaCargar.doModal();
	}

	@Command
	public void eliminar(){

		if (partidoSeleccionado == null) {
			Clients.showNotification("Seleccione una opción de la lista.");
			return; 
		}

		Messagebox.show("Desea eliminar el registro seleccionado?", "Confirmación de Eliminación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						partidoDao.getEntityManager().getTransaction().begin();
						partidoSeleccionado.setEstado("1");
						partidoSeleccionado = partidoDao.getEntityManager().merge(partidoSeleccionado);
						partidoDao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Transaccion ejecutada con exito.");

						// Actualiza la lista
						BindUtils.postGlobalCommand(null, null, "Partido.buscarPorPatron", null);

					} catch (Exception e) {
						e.printStackTrace();
						partidoDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
		});		
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}


	public PartidoEditar getPartido() {
		return partidoE;
	}

	public void setPartido(PartidoEditar partidoE) {
		this.partidoE = partidoE;
	}

	public Partido getPartidoSeleccionado() {
		return partidoSeleccionado;
	}

	public void setPartidoSeleccionado(Partido partidoSeleccionado) {
		this.partidoSeleccionado = partidoSeleccionado;
	}

	public List<Partido> getPartidos() {
		return partido;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partido = partidos;
	}

	public List<Detallecalendario> getDetacalendario() {
		return detacalendario;
	}

	public void setDetacalendario(List<Detallecalendario> detacalendario) {
		this.detacalendario = detacalendario;
	}

	public Detallecalendario getDetallecalendarioSeleccionada() {
		return detallecalendarioSeleccionada;
	}

	public void setDetallecalendarioSeleccionada(Detallecalendario detallecalendarioSeleccionada) {
		this.detallecalendarioSeleccionada = detallecalendarioSeleccionada;
	}


}
