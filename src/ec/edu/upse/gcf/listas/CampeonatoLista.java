package ec.edu.upse.gcf.listas;

import java.io.IOException;
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

import ec.edu.upse.gcf.dao.CampeonatoDAO;
import ec.edu.upse.gcf.modelo.Campeonato;

//import ec.edu.upse.gcf.util.PrintReport;

@SuppressWarnings({ "unchecked" })
public class CampeonatoLista{
	@Wire private String textoBuscar;
	@Wire private Button btnEditar, btnEliminar;

	private CampeonatoDAO campeonatoDao = new CampeonatoDAO();
	private List<Campeonato> campeonatos;
	private Campeonato campeonatoSeleccionado;
	private Campeonato campeonato;
		
	
	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException{
		Selectors.wireComponents(view, this, false);
		textoBuscar="";
		deshabilitarBoton();
	}
	
	private void deshabilitarBoton() {
		btnEditar.setDisabled(true);
		btnEliminar.setDisabled(true);
	}
	
	@GlobalCommand("Campeonatos.buscarPorPatron")
	@Command
	@NotifyChange({"campeonatos", "campeonatoSeleccionado"})
	public void buscar(){
		if (campeonatos != null) {
			campeonatos = null; 
		}	
		campeonatos = campeonatoDao.getCampeonatos(textoBuscar);
		if(campeonatos.size() == 0) {
			Clients.showNotification("No hay datos para mostrar.!!");
		}else {
			for(int i=0; i<campeonatos.size(); i++) {
				if(campeonatos.get(i).getTipoCampeonato().equals("F")) {
					campeonatos.get(i).setTipoCampeonato("FEMENINO");					
				}else if (campeonatos.get(i).getTipoCampeonato().equals("M")) {
					campeonatos.get(i).setTipoCampeonato("MASCULINO");
				}
			}
			
			for(int i=0; i<campeonatos.size(); i++) {
				if(campeonatos.get(i).getEstadocamp().equals("F")) {
					campeonatos.get(i).setEstadocamp("FINALIZADO");					
				}else if (campeonatos.get(i).getEstadocamp().equals("D")) {
					campeonatos.get(i).setEstadocamp("DISPONIBLE");
				}
			}
			
			campeonatoSeleccionado = null;
			btnEditar.setDisabled(false);
			btnEliminar.setDisabled(false);
		}
	}	
	
	@Command
	public void nuevo(){
		//Map<String, Object> params = new HashMap<String, Object>();
		//params.put("Campeonato", new Campeonato());
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/campeonatos/campeonatoEditar.zul", null, null);
		ventanaCargar.doModal();
	}
	
	@Command
	public void editar(){
		if (campeonatoSeleccionado == null) {
			Clients.showNotification("Debe seleccionar un campeonato de la lista.");
			return; 
		}
		campeonatoDao.getEntityManager().refresh(campeonatoSeleccionado);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Campeonato", campeonatoSeleccionado);
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/campeonatos/campeonatoEditar.zul", null, params);
		ventanaCargar.doModal();
	}
	
	/**@Command
	public void verReporte(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nombreCampeonato", "Campeonato 2018");
		PrintReport pr = new PrintReport();
		pr.crearReporte("/Reportes/prueba.jasper", campeonatoDao, param);
		pr.showReport("Reporte de caja resumido");
	}*/	

	@SuppressWarnings("rawtypes")
	@Command
	public void eliminar(){
		if (campeonatoSeleccionado == null) {
			Clients.showNotification("Debe seleccionar un campeonato de la lista.");
			return; 
		}
		Messagebox.show("Desea eliminar el registro seleccionado?", "Confirmaci??n de Eliminaci??n", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						campeonatoDao.getEntityManager().getTransaction().begin();
						campeonatoSeleccionado.setEstado("1");
						campeonatoSeleccionado = campeonatoDao.getEntityManager().merge(campeonatoSeleccionado);
						campeonatoDao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Transaccion ejecutada con exito.");
						BindUtils.postGlobalCommand(null, null, "Campeonatos.buscarPorPatron", null);
					} catch (Exception e) {
						e.printStackTrace();
						campeonatoDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
		});

	}

	//Getter and Setter
	
	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public List<Campeonato> getCampeonatos() {
		return campeonatos;
	}

	public void setCampeonatos(List<Campeonato> campeonatos) {
		this.campeonatos = campeonatos;
	}
	public Campeonato getCampeonatoSeleccionado() {
		return campeonatoSeleccionado;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public void setCampeonatoSeleccionado(Campeonato campeonatoSeleccionado) {
		this.campeonatoSeleccionado = campeonatoSeleccionado;
	}
}
