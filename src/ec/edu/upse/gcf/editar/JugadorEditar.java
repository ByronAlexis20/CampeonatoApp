
package ec.edu.upse.gcf.editar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.dao.JugadorDAO;
import ec.edu.upse.gcf.modelo.Jugador;
import ec.edu.upse.gcf.util.FileUtil;

public class JugadorEditar {

	@Wire private Window winJugadorEditar;
	@Wire private Textbox cedula;	
	@Wire private Textbox nombres;
	@Wire private Textbox edad;
	@Wire private Intbox numcamisa;
	@Wire private Datebox fechaNac;
	@Wire private Combobox posicionJuego;
	@Wire private Combobox cboGenero;
	@Wire private Checkbox validar;
	@Wire private Button btnGrabar;

	private JugadorDAO jugadorDao = new JugadorDAO(); 
	private Jugador jugador;

	private List<Jugador> edadJugador = new ArrayList<>();

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		jugador = (Jugador) Executions.getCurrent().getArg().get("Jugador");
		if(jugador != null) {
			if(jugador.getGenero().equals("F")) {
				jugador.setGenero("FEMENINO");					
			}else if (jugador.getGenero().equals("M")) {
				jugador.setGenero("MASCULINO");
			}
		}else {
			jugador = new Jugador();
		}
	}

	public List<String> getGenero() {		
		List<String> genero = new ArrayList<String>();	
		genero.add("FEMENINO");				
		genero.add("MASCULINO");   
		return genero;					
	}

	public List<String> getPosicionJuego() {		
		List<String> posicion = new ArrayList<String>();	
		posicion.add("ARQUERO");	
		posicion.add("DEFENSA");
		posicion.add("DELANTERO");
		posicion.add("MEDIO CAMPISTA");		
		return posicion;					
	}

	/** Validar Datos */
	public boolean isValidarV() {
		try {
			Boolean retorna = true;
			if(cedula.getText().isEmpty() 
					&& nombres.getText().isEmpty()
					&& edad.getText().isEmpty() 
					&& cboGenero.getValue().isEmpty() 
					&& fechaNac.getText().isEmpty()
					&& numcamisa.getText().isEmpty()
					&& posicionJuego.getValue().isEmpty()
					) {
				Clients.showNotification("No hay datos para guardar!!.");
				return retorna;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/** Validar si el usuario existe a través de la cedula o pasaporte */
	boolean validarJugadorExistente() {
		try {
			Jugador jugador  = jugadorDao.getValidarJugadorExistente(cedula.getText());
			if(jugador != null)
				return true;
			else
				return false;
		}catch(Exception ex) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Command
	@NotifyChange({"fechaNac"})
	public void calcularEdad() {
		Date fechaActual = new Date();
		if(jugador.getFechaNac()  != null) {		
			int edadJ = (fechaActual.getYear()- fechaNac.getValue().getYear());
			String edadjugador = String.valueOf(edadJ);
			edad.setValue(edadjugador);			
		}			
	}

	/** M�todo para validar cédula*/
	public boolean validarCedula(String cedula) {
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					// Coeficientes de validaci�n c�dula
					// El decimo digito se lo considera d�gito verificador
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9,10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}
					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					}
					else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			System.out.println("Una excepcion ocurrio en el proceso de validacion");
			cedulaCorrecta = false;
		}
		if (!cedulaCorrecta) {
			System.out.println("La Cédula ingresada es Incorrecta");
		}
		return cedulaCorrecta;
	}

	@Command
	public void isValidarDatos() {
		try {
			if (validar.isChecked()) {

			}else {
				if(validarCedula(cedula.getText())== false) {
					Clients.showNotification("La cédula ingresada no es válida!!");
					cedula.focus();				
				}
			}
			if(validarJugadorExistente() == true) {
				Clients.showNotification("El jugador ingresado ya existe!!");
				cedula.focus();	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void grabar(){	
		if(isValidarV() == true) {
			return;
		}
		Messagebox.show("Desea guardar el registro?", "Confirmación de Guardar", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						jugadorDao.getEntityManager().getTransaction().begin();
						if (jugador.getIdJugador() == 0) {							
							if(jugador.getGenero().equals("FEMENINO")) {
								jugador.setGenero("F");
							}else if(jugador.getGenero().equals("MASCULINO")) {
								jugador.setGenero("M");
							}
							jugadorDao.getEntityManager().persist(jugador);
							jugador.setEdad(edad.getText());
						}else{
							jugador = (Jugador) jugadorDao.getEntityManager().merge(jugador);
							jugador.setEdad(edad.getText());
						}
						jugadorDao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Proceso Ejecutado con exito.");			
						BindUtils.postGlobalCommand(null, null, "Jugadores.buscarPorPatron", null);
						salir();							
					} catch (Exception e) {
						e.printStackTrace();
						jugadorDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
		});
	}

	@Command
	@NotifyChange("imagen")
	public void subir(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent evento){
		String imagen = FileUtil.loadFile(evento.getMedia());
		if (imagen != null) {
			jugador.setFoto(imagen);
		}
	}

	public AImage getImagen() {
		AImage retorno = null;
		if (jugador.getFoto() != null) {
			try {
				retorno = FileUtil.getImagenTamanoFijo(new AImage(jugador.getFoto()), 163, 163);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retorno; 
	}

	@Command
	public void salir(){
		winJugadorEditar.detach();
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public List<Jugador> getEdadJugador() {
		return edadJugador;
	}

	public void setEdadJugador(List<Jugador> edadJugador) {
		this.edadJugador = edadJugador;
	}	

}
