package ec.edu.upse.gcf.editar;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.dao.CategoriaDAO;
import ec.edu.upse.gcf.modelo.Categoria;

public class CategoriaEditar {

	@Wire private Window winCategoriaEditar;
	@Wire private Textbox nombre;
	@Wire private Textbox descripcion;
	@Wire private Combobox cboTipoC;

	private CategoriaDAO categoriaDao = new CategoriaDAO();
	private Categoria categoria;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		// Recupera el objeto pasado como parametro. 
		categoria = (Categoria) Executions.getCurrent().getArg().get("Categoria");
		if (categoria != null) {
			if(categoria.getTipo().equals("F")) {
				categoria.setTipo("FEMENINO");					
			}else if (categoria.getTipo().equals("M")) {
				categoria.setTipo("MASCULINO");
			}
		}else {
			categoria = new Categoria();
		}
	}

	public List<String> getTipoCategoria() {		
		List<String> tipoCategoria = new ArrayList<String>();	
		tipoCategoria.add("FEMENINO");				
		tipoCategoria.add("MASCULINO");   
		return tipoCategoria;					
	}

	public boolean isValidarDatos() {
		try {
			Boolean retorna = true;
			if(nombre.getText().isEmpty() && descripcion.getText().isEmpty()) {
				Clients.showNotification("No hay datos para guardar!!.");
				return retorna;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void grabar(){
		if(isValidarDatos() == true) {
			return;
		}
		Messagebox.show("Desea guardar el registro?", "Confirmaci??n de Guardar", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {		
					try {						
						categoriaDao.getEntityManager().getTransaction().begin();			
						if (categoria.getIdCategoria() == 0) {
							if(categoria.getTipo().equals("FEMENINO")) {
								categoria.setTipo("F");
							}else if(categoria.getTipo().equals("MASCULINO")) {
								categoria.setTipo("M");
							}
							categoriaDao.getEntityManager().persist(categoria);
						}else{
							categoria = (Categoria) categoriaDao.getEntityManager().merge(categoria);
						}			
						categoriaDao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Proceso Ejecutado con exito.");
						BindUtils.postGlobalCommand(null, null, "Categorias.buscarPorPatron", null);
						salir();						
					} catch (Exception e) {
						e.printStackTrace();
						categoriaDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
		});
	}	

	@Command
	public void salir(){
		winCategoriaEditar.detach();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}