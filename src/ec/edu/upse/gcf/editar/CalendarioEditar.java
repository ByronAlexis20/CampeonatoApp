package ec.edu.upse.gcf.editar;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.auxiliar.AuxCalendario;
import ec.edu.upse.gcf.auxiliar.AuxEncuentroEquipo;
import ec.edu.upse.gcf.auxiliar.AuxEquipoGenerado;
import ec.edu.upse.gcf.auxiliar.AuxEquipoRandom;
import ec.edu.upse.gcf.auxiliar.AuxEquipoSemanaCampeonato;
import ec.edu.upse.gcf.auxiliar.AuxFecha;
import ec.edu.upse.gcf.auxiliar.AuxFechaCampeonatoGenerado;
import ec.edu.upse.gcf.auxiliar.AuxGrupoGeneradoAleatorio;
import ec.edu.upse.gcf.auxiliar.AuxGrupoGeneradoEquipo;
import ec.edu.upse.gcf.auxiliar.AuxPartidoTotalGrupo;
import ec.edu.upse.gcf.auxiliar.AuxTotalEquipoLocal;
import ec.edu.upse.gcf.dao.CalendarioDAO;
import ec.edu.upse.gcf.dao.CampeonatoDAO;
import ec.edu.upse.gcf.dao.ConfiguracionDAO;
import ec.edu.upse.gcf.dao.LugarPartidoDAO;
import ec.edu.upse.gcf.dao.ModalidadDAO;
import ec.edu.upse.gcf.general.ConstanteApp;
import ec.edu.upse.gcf.general.MetodoGeneral;
import ec.edu.upse.gcf.modelo.Calendario;
import ec.edu.upse.gcf.modelo.Campeonato;
import ec.edu.upse.gcf.modelo.Configuracion;
import ec.edu.upse.gcf.modelo.Detallecalendario;
import ec.edu.upse.gcf.modelo.Detallecampeonato;
import ec.edu.upse.gcf.modelo.Equipo;
import ec.edu.upse.gcf.modelo.Lugarpartido;
import ec.edu.upse.gcf.modelo.Modalidad;

/**
 * Control para el formulario : calendario.zul
 * @author Dayana Tigrero
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused"})
public class CalendarioEditar {

	private List<AuxFechaCampeonatoGenerado> listaFechaCampeonato = new ArrayList<>();
	private List<AuxCalendario> listaCalendario = new ArrayList<AuxCalendario>();
	private LugarPartidoDAO lugarEquipoDAO = new LugarPartidoDAO();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO(); 
	private ModalidadDAO modalidadDAO = new ModalidadDAO(); 
	private ConfiguracionDAO configuracionDAO = new ConfiguracionDAO(); 
	private CalendarioDAO calendarioDAO = new CalendarioDAO();

	private Calendario calendario = new Calendario(); 	

	private AuxEquipoSemanaCampeonato auxEquipoSemanaCampeonato;	
	private Campeonato campeonatoSeleccionado; 
	private Lugarpartido lugarSeleccionado;
	private Modalidad modalidadSeleccionada;

	@Wire
	Button btnGenerar,btnGuardar;

	@Wire 
	Tree equipo;

	@Wire
	Tab tabCalendar,tabGrupo;

	@Wire
	Label lblNumero,lblFechaFin;

	@Wire
	Intbox txtNumero;

	@Wire
	Div divlistaGrupo;

	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException{
		Selectors.wireComponents(view, this, false);
		deshabilitarBoton();
	}

	private void deshabilitarBoton() { 
		btnGuardar.setDisabled(true); 
		btnGenerar.setDisabled(true);
		tabCalendar.setDisabled(true);
		tabGrupo.setDisabled(true);		
		txtNumero.setDisabled(true);
		tabGrupo.setVisible(false);
		txtNumero.setVisible(false);
		lblNumero.setVisible(false);
	}

	@Command
	@NotifyChange({"equipo","campeonatoSeleccionado","lblNumero","txtNumero","btnGenerar","modalidadSeleccionada"})
	public void equipoCampeonato() throws IOException {
		List<Detallecampeonato> detallecampeonatos = new ArrayList<>();
		if(modalidadSeleccionada.getDescripcion().toLowerCase().trim().equals(ConstanteApp.GRUPO)) {
			if(campeonatoSeleccionado.getDetallecampeonatos().size() > 0) {
				Integer cantidad = campeonatoSeleccionado.getDetallecampeonatos().size();
				if(cantidad >= 8) {
					if((cantidad%2) == 0) {
						detallecampeonatos.addAll(campeonatoSeleccionado.getDetallecampeonatos());
						txtNumero.setVisible(true);
						txtNumero.setDisabled(false);
						lblNumero.setVisible(true);
					} 
				}				
			}			
		}else {
			txtNumero.setDisabled(true); 
			txtNumero.setVisible(false);
			lblNumero.setVisible(false);
		}

		if(modalidadSeleccionada.getDescripcion().toLowerCase().trim().equals(ConstanteApp.TODOS_CONTRA_TODO)) {
			Integer cantidad = campeonatoSeleccionado.getDetallecampeonatos().size();
			if(cantidad >= 6) {
				if((cantidad%2) != 0) {
					btnGenerar.setDisabled(false);
					detallecampeonatos.addAll(campeonatoSeleccionado.getDetallecampeonatos()); 
				}				
			}	
		}

		if(equipo != null) {
			equipo.getChildren().clear();
		}	

		equipo.appendChild(getTreechildren(detallecampeonatos)); 
	}

	@Command
	@NotifyChange({"btnGenerar","campeonatoSeleccionado","txtNumero"})
	public void habilitar() {
		Integer resultado = campeonatoSeleccionado.getDetallecampeonatos().size() % txtNumero.getValue();
		if(resultado == 0) {
			Integer numeroEquipoGrupo = (campeonatoSeleccionado.getDetallecampeonatos().size() / txtNumero.getValue());
			if(numeroEquipoGrupo >= 4) {
				btnGenerar.setDisabled(false);
			}else{
				btnGenerar.setDisabled(true);
				Messagebox.show("El número ingresado para la división de los grupos es incorrecta", "Sistema", Messagebox.OK, Messagebox.ERROR);
			}			
		}else {
			btnGenerar.setDisabled(true);
			Messagebox.show("El número ingresado para la división de los grupos es incorrecta", "Sistema", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private Treechildren getTreechildren(List<Detallecampeonato> lista) throws IOException {
		try {
			Treechildren retorno =  new Treechildren();
			Treechildren categoria = new Treechildren();
			Treechildren retornoEquipos = new Treechildren(); 
			Treeitem ti = new Treeitem();
			Treeitem te = new Treeitem();
			int contador = 0;
			for(Detallecampeonato detallecampeonato:lista) { 
				if(contador == 0) {
					ti.setLabel("Categoria"); 
					retorno.appendChild(ti); 

					//Contenido de la categoria 
					Treeitem c = getTreeitemCategoria(detallecampeonato); 
					categoria.appendChild(c);
					ti.appendChild(categoria);
					ti.setOpen(false);

					te.setLabel("Equipos"); 
					categoria.appendChild(te); 
					contador = contador + 1;
				}  

				Treeitem e = getTreeitemEquipo(detallecampeonato.getEquipo());
				retornoEquipos.appendChild(e); 
				te.appendChild(retornoEquipos);
				te.setOpen(false);			 
			}			
			return retorno;	
		} catch (Exception e) {
			e.getMessage();
			return null; 
		}		
	}

	private Treeitem getTreeitemCategoria(Detallecampeonato objeto) throws IOException {
		Treeitem retorno = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tc = new Treecell(objeto.getCategoria().getNombre());			
		tr.appendChild(tc);
		retorno.appendChild(tr);
		retorno.setOpen(false);
		return retorno;
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

	@Command
	public void generar(@ContextParam(ContextType.VIEW) Component view) {

		tabCalendar.setDisabled(false);
		tabGrupo.setVisible(true);
		tabGrupo.setDisabled(false);

		Time horainicio;
		Time horaFin;
		Time duracionEncuentro;
		Integer numerPartidoPorDia = 0;
		String diaInicio = "";
		String diaFin = "";

		/**
		 * Obtener la configuracion del usuario
		 * para generar el calendario
		 */
		List<Configuracion> config = configuracionDAO.getListaConfiguracion();
		for (Configuracion c : config) {
			horainicio = c.getHoraInicio();
			horaFin = c.getHoraFin();
			numerPartidoPorDia = c.getPartidoPorDia();
			diaInicio = c.getDiasJuegoInicio();
			diaFin = c.getDiasJuegoFin();
			duracionEncuentro = c.getTiempoDuracion();
		}

		//division de grupo campeonato por modalidad
		Integer numeroEquipoGrupo = 0;		
		Integer cantidadGrupo = 0;
		if(modalidadSeleccionada.getDescripcion().toLowerCase().trim().equals(ConstanteApp.GRUPO)) {
			numeroEquipoGrupo = campeonatoSeleccionado.getDetallecampeonatos().size() / txtNumero.getValue();	
			cantidadGrupo = txtNumero.getValue();
		}else {
			if(modalidadSeleccionada.getDescripcion().toLowerCase().trim().equals(ConstanteApp.TODOS_CONTRA_TODO)) {
				numeroEquipoGrupo = campeonatoSeleccionado.getDetallecampeonatos().size() / 1 ;
				cantidadGrupo = 1;
			}
		}

		Integer numeroPartidoPorEquipo = numeroEquipoGrupo - 1;
		Integer numeroPartidoTotalGrupo = numeroEquipoGrupo * numeroPartidoPorEquipo;
		Integer totalPartidoCampeonato = campeonatoSeleccionado.getDetallecampeonatos().size() * numeroPartidoPorEquipo;

		//separacion de fecha del campeonato inicial
		Integer dia = MetodoGeneral.getNumeroDia(campeonatoSeleccionado.getFechaInicio());
		Integer mes = MetodoGeneral.getNumeroMes(campeonatoSeleccionado.getFechaInicio());
		Integer año = MetodoGeneral.getNumeroAño(campeonatoSeleccionado.getFechaInicio());

		//Obtenemos el dia de la semana
		Calendar c = Calendar.getInstance();
		c.set(año, mes-1, dia);	    
		String semana = MetodoGeneral.getFechaDiaString(c.get(Calendar.DAY_OF_WEEK) - 1); 

		System.out.println("fecha campeonato : Semana : "+semana +"  "+ dia +"/"+mes+"/"+año);
		System.out.println("total de partido por equipo : "+numeroPartidoPorEquipo);
		System.out.println("total de partido por grupo : "+numeroPartidoTotalGrupo);
		System.out.println("total de partido : "+totalPartidoCampeonato);

		/**
		 * generamos la lista de grupo de acuerdo a la cantidad de grupo 
		 * ingresada por el usuario
		 * 
		 */
		List<AuxGrupoGeneradoAleatorio> lista = new ArrayList<AuxGrupoGeneradoAleatorio>();
		int contadorGrupo = 0;
		for(int i=0; i<cantidadGrupo; i++) {
			if(contadorGrupo == 0) {
				AuxGrupoGeneradoAleatorio objeto = new AuxGrupoGeneradoAleatorio();
				objeto.setDescripcion("Grupo "+(i+1));
				List<Integer> resultado = getListaNumeroEquipoAleatorio(numeroEquipoGrupo);
				objeto.setGrupoGeneradoAleatorio(resultado);
				lista.add(objeto);	
				contadorGrupo++;
			}else {
				AuxGrupoGeneradoAleatorio objeto = new AuxGrupoGeneradoAleatorio();
				objeto.setDescripcion("Grupo "+(i+1));
				List<Integer> resultado = getGrupoNumeroEquipoAleatorio(numeroEquipoGrupo,lista);
				objeto.setGrupoGeneradoAleatorio(resultado);
				lista.add(objeto);	
			}

		}

		List<AuxGrupoGeneradoEquipo> listaEquipoGrupo = new ArrayList<>();
		//Asignamos los equipos en cada grupo generado
		for (AuxGrupoGeneradoAleatorio auxGrupoGeneradoAleatorio : lista) {
			System.out.println(" *** "+auxGrupoGeneradoAleatorio.getDescripcion());
			AuxGrupoGeneradoEquipo auxGrupoGeneradoEquipo = new AuxGrupoGeneradoEquipo();
			auxGrupoGeneradoEquipo.setDescripcion(auxGrupoGeneradoAleatorio.getDescripcion());
			List<Equipo> equipos = new ArrayList<>();
			for (Integer numero : auxGrupoGeneradoAleatorio.getGrupoGeneradoAleatorio()) {	
				System.out.println(" *** equipo : "+numero);
				for (int i=0; i < campeonatoSeleccionado.getDetallecampeonatos().size(); i++) {
					if((i+1) == numero) {
						equipos.add(campeonatoSeleccionado.getDetallecampeonatos().get(i).getEquipo());
						break;
					}
				}
			}
			auxGrupoGeneradoEquipo.setEquipos(equipos);
			listaEquipoGrupo.add(auxGrupoGeneradoEquipo);
		} 

		//mostramos los nombres de los equipo en cada grupo
		for (AuxGrupoGeneradoEquipo auxGrupoGeneradoEquipo : listaEquipoGrupo) {
			System.out.println(" *** "+auxGrupoGeneradoEquipo.getDescripcion());
			for (Equipo e : auxGrupoGeneradoEquipo.getEquipos()) {
				System.out.println(" *** equipo : "+e.getNombre());
			}
		}

		//mostramos los grupos con los equipos en listbox
		divlistaGrupo.appendChild(getHbo(listaEquipoGrupo));

		/**
		 * generamos los equipos de enfrentamiento por fecha
		 * 
		 */
		List<AuxEquipoRandom> equipoRandom = new ArrayList<AuxEquipoRandom>();
		List<AuxPartidoTotalGrupo> totalGrupo = new ArrayList<AuxPartidoTotalGrupo>();

		Integer diaAsignado = 0;
		Integer mesAsignado = 0;
		Integer anioAsignado = 0;
		Integer posicionSemana = 0; 
		String semanaAsignada = "";
		Integer contadorDia = 0;
		Integer contadorPartido = 0;

		do {
			if(contadorDia == 0) {
				diaAsignado = dia;
				mesAsignado = mes;
				anioAsignado = año;
				semanaAsignada = semana;	
				posicionSemana = MetodoGeneral.getFechaDiaInteger(semana);
			}

			for (AuxGrupoGeneradoAleatorio a : lista) {
				AuxEquipoRandom auxEquipoRandom = new AuxEquipoRandom();
				List<AuxEncuentroEquipo> resultado  = new ArrayList<AuxEncuentroEquipo>();

				boolean retorna = false; 

				//validamos que la fecha a generar sea correcta
				do {
					AuxFecha auxFecha = MetodoGeneral.validaFechaCampeonato(diaAsignado, mesAsignado);
					if (auxFecha.getAuxDia() == ConstanteApp.ESTADO_FECHA_DIA_CORRECTO &&
							auxFecha.getAuxMes() == ConstanteApp.ESTADO_FECHA_MES_CORRECTO) {					
						retorna = true;
						break;
					}else { 
						if (auxFecha.getAuxMes() == ConstanteApp.ESTADO_FECHA_MES_CORRECTO &&
								auxFecha.getAuxDia() == ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO){
							diaAsignado = 1;
							mesAsignado = mesAsignado + 1;
							retorna = false;
							break;
						}
					}				
				}while(retorna != false);		

				if(posicionSemana > 0 && posicionSemana < 6) {					
					//validamos si se inicia por primera vez los equipos de enfrentamiento para cada grupo
					if(contadorDia < cantidadGrupo) {
						resultado = getListEncuentroEquipo(a.getGrupoGeneradoAleatorio(),2);							
						auxEquipoRandom.setDescripcion(a.getDescripcion());
						auxEquipoRandom.setAuxEncuentroEquipos(resultado); 
						auxEquipoRandom.setAuxFechaCampeonatoGenerado(getFechaGenerada(diaAsignado, mesAsignado, anioAsignado, posicionSemana));
						for (AuxEncuentroEquipo e : auxEquipoRandom.getAuxEncuentroEquipos()) {
							auxEquipoSemanaCampeonato = new AuxEquipoSemanaCampeonato();
							auxEquipoSemanaCampeonato.setGrupo(a.getDescripcion());
							auxEquipoSemanaCampeonato.setEquipo(e.getPosicionEquipoLocal()); 
						}								

						System.out.println(" ***************************************************");
						System.out.println(" 			equipos generados");
						System.out.println(" ***************************************************");
						for (AuxEncuentroEquipo aee : resultado) {
							System.out.println(" equipo local :  "+aee.getPosicionEquipoLocal()+ " equipo visitante : "+aee.getPosicionEquipoVisitante());  
						}

						//guardamos el grupo y el total del partido
						AuxPartidoTotalGrupo partidoTotalGrupo = new AuxPartidoTotalGrupo();
						partidoTotalGrupo.setGrupo(a.getDescripcion());
						partidoTotalGrupo.setTotalEquipo(2);
						totalGrupo.add(partidoTotalGrupo);
						contadorDia++;
						contadorPartido = contadorPartido + 2;						 
					}else { 
						Integer totalGrupoEquipo = 0;
						//obtenemos el numero total de equipo por grupo
						for (AuxPartidoTotalGrupo p: totalGrupo) {
							if(p.getGrupo().equals(a.getDescripcion())) {									
								totalGrupoEquipo = totalGrupoEquipo + p.getTotalEquipo();
							}
						}
						System.out.println("grupo : "+a.getDescripcion());
						System.out.println("total partido del grupo : "+totalGrupoEquipo);

						if(totalGrupoEquipo < numeroPartidoTotalGrupo) {								
							//validamos y verificamos los equipos a enfrentarse con los partidos completo por grupo
							Integer nPartido = 2;
							resultado = getListaEquipoEncuentroFases(a.getGrupoGeneradoAleatorio(),
									nPartido,
									equipoRandom,
									a.getDescripcion(),
									numeroPartidoPorEquipo);
							System.out.println(" ***************************************************");
							System.out.println(" 			equipos generados");
							System.out.println(" ***************************************************");
							for (AuxEncuentroEquipo aee : resultado) {
								System.out.println(" equipo local :  "+aee.getPosicionEquipoLocal()+ " equipo visitante : "+aee.getPosicionEquipoVisitante());  
							}

							int contadorEquipoFantasma = 0;
							for (AuxEncuentroEquipo aee : resultado) {
								if(aee.getPosicionEquipoLocal() == 0 && aee.getPosicionEquipoVisitante() == 0) {
									contadorEquipoFantasma = contadorEquipoFantasma + 1;
								} 
							}

							if(contadorEquipoFantasma > 0) {
								nPartido = 1;
								System.out.println(" **  partidos fantasma encontrado :  "+contadorEquipoFantasma);
								contadorEquipoFantasma = 0;								 
							}else {
								contadorEquipoFantasma = 0;
							}

							auxEquipoRandom.setAuxEncuentroEquipos(resultado); 
							auxEquipoRandom.setDescripcion(a.getDescripcion());
							auxEquipoRandom.setAuxFechaCampeonatoGenerado(getFechaGenerada(diaAsignado, mesAsignado, anioAsignado, posicionSemana));
							for (AuxEncuentroEquipo e : auxEquipoRandom.getAuxEncuentroEquipos()) {
								auxEquipoSemanaCampeonato = new AuxEquipoSemanaCampeonato();
								auxEquipoSemanaCampeonato.setGrupo(a.getDescripcion());
								auxEquipoSemanaCampeonato.setEquipo(e.getPosicionEquipoLocal());

							}

							//guardamos el grupo y el total del partido
							AuxPartidoTotalGrupo partidoTotalGrupo = new AuxPartidoTotalGrupo();
							partidoTotalGrupo.setGrupo(a.getDescripcion());
							partidoTotalGrupo.setTotalEquipo(nPartido);
							totalGrupo.add(partidoTotalGrupo);
							contadorPartido = contadorPartido + nPartido;									 
						} 					
					}
				}else {
					if(posicionSemana < 7) {
						auxEquipoRandom.setDescripcion("");
						auxEquipoRandom.setAuxEncuentroEquipos(new ArrayList<AuxEncuentroEquipo>()); 
						auxEquipoRandom.setAuxFechaCampeonatoGenerado(getFechaGenerada(diaAsignado, mesAsignado, anioAsignado, posicionSemana));
					}else {
						posicionSemana = 0;
						auxEquipoRandom.setDescripcion("");
						auxEquipoRandom.setAuxEncuentroEquipos(new ArrayList<AuxEncuentroEquipo>()); 
						auxEquipoRandom.setAuxFechaCampeonatoGenerado(getFechaGenerada(diaAsignado, mesAsignado, anioAsignado, posicionSemana));
					}
				}					
				equipoRandom.add(auxEquipoRandom);
				diaAsignado++; 
				posicionSemana++;
				System.out.println("total del total : "+contadorPartido);
			} 			
		}while(contadorPartido < totalPartidoCampeonato);

		//presentamos los equipos de encuentro
		/*for (AuxEquipoRandom auxEquipoRandom : equipoRandom) {
			if(auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDescripcion() != null) {
				System.out.println("*********************************************************");
				System.out.println(" 			"+auxEquipoRandom.getDescripcion()+" 		 ");
				System.out.println("*********************************************************");
				System.out.println("Dia : "+auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDescripcion());
				String fecha = auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDia()+"/"+auxEquipoRandom.getAuxFechaCampeonatoGenerado().getMes()+"/"+auxEquipoRandom.getAuxFechaCampeonatoGenerado().getAño();
				System.out.println("Fecha : "+fecha);
				System.out.println("*********************************************************");
				for (AuxEncuentroEquipo e : auxEquipoRandom.getAuxEncuentroEquipos()) {
					System.out.println("equipo local : "+e.getPosicionEquipoLocal() + " equipo visitante : "+e.getPosicionEquipoVisitante());
				}
			}			
		}*/

		//asignamos los equipos de enfrentamiento de cada grupo en el calendario

		for (AuxEquipoRandom auxEquipoRandom : equipoRandom) {
			if(auxEquipoRandom != null) {
				Boolean retorna = false;
				String fechaCamp = "";
				try {
					fechaCamp = auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDescripcion();
					retorna = true;
				} catch (Exception e) {
					e.getMessage();
					retorna = false;
				}
				if(retorna) {
					AuxCalendario auxCalendario = new AuxCalendario();
					auxCalendario.setDescripcion(auxEquipoRandom.getDescripcion());
					auxCalendario.setDia(auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDescripcion());
					String fecha = auxEquipoRandom.getAuxFechaCampeonatoGenerado().getDia()+"/"+auxEquipoRandom.getAuxFechaCampeonatoGenerado().getMes()+"/"+auxEquipoRandom.getAuxFechaCampeonatoGenerado().getAño();
					auxCalendario.setFecha(fecha);

					List<AuxEquipoGenerado> equiposGenerado = new ArrayList<AuxEquipoGenerado>();				
					for(AuxEncuentroEquipo e : auxEquipoRandom.getAuxEncuentroEquipos()) {					
						AuxEquipoGenerado equipoGenerado = new AuxEquipoGenerado();
						//mostramos los nombres de los equipo en cada grupo
						for (AuxGrupoGeneradoEquipo auxGrupoGeneradoEquipo : listaEquipoGrupo) {						
							if(auxEquipoRandom.getDescripcion().equals(auxGrupoGeneradoEquipo.getDescripcion())) {
								for(int i=0; i<auxGrupoGeneradoEquipo.getEquipos().size(); i++) {
									if((i+1) == e.getPosicionEquipoLocal()) {
										equipoGenerado.setEquipoLocal(auxGrupoGeneradoEquipo.getEquipos().get(i));
										break;
									}
								}

								for(int i=0; i<auxGrupoGeneradoEquipo.getEquipos().size(); i++) {
									if((i+1) == e.getPosicionEquipoVisitante()) {
										equipoGenerado.setEquipoVisitante(auxGrupoGeneradoEquipo.getEquipos().get(i));
										break;
									}							
								}
							}
						}
						equiposGenerado.add(equipoGenerado);
					}		
					auxCalendario.setAuxEquipoGenerados(equiposGenerado);
					listaCalendario.add(auxCalendario);
				}
			}	
		}

		//habilitamos para guardar el calendario
		btnGuardar.setDisabled(false);
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show("¿Está seguro de guadar el calendario generado?", "Confirmación", 
						Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION,new EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							try {

								calendarioDAO.getEntityManager().getTransaction().begin();	 
								calendario.setCampeonato(campeonatoSeleccionado);
								if(txtNumero.getValue() != null) {
									calendario.setCantidadGrupo(txtNumero.getValue()); 
								}
								Date fechaActual = new Date();
								calendario.setFechaRegistro(fechaActual);
								calendario.setModalidad(modalidadSeleccionada);
								List<Detallecalendario> detalle = new ArrayList<>();
								for (AuxCalendario calendarioGenerado : listaCalendario) {											
									for (AuxEquipoGenerado auxEquipoGenerado : calendarioGenerado.getAuxEquipoGenerados()) {

										if(auxEquipoGenerado.getEquipoLocal() != null && auxEquipoGenerado.getEquipoVisitante() != null) {
											Detallecalendario detallecalendario = new Detallecalendario();
											detallecalendario.setCalendario(calendario);
											detallecalendario.setGrupo(calendarioGenerado.getDescripcion());
											detallecalendario.setDia(calendarioGenerado.getDia());
											detallecalendario.setEquipoLocal(auxEquipoGenerado.getEquipoLocal());
											detallecalendario.setEquipoVisitante(auxEquipoGenerado.getEquipoVisitante());
											detallecalendario.setFecha(MetodoGeneral.getStringToDate(calendarioGenerado.getFecha()));
											detallecalendario.setLugarpartido(lugarSeleccionado);
											detallecalendario.setHora(auxEquipoGenerado.getHora());	
											detalle.add(detallecalendario);
										}


									}
								}
								calendario.setDetallecalendarios(detalle);						        		
								calendarioDAO.getEntityManager().persist(calendario);
								calendarioDAO.getEntityManager().getTransaction().commit();
								Clients.showNotification("Proceso Ejecutado con exito.");	
								salir(view);
							} catch (Exception e) {
								e.printStackTrace();
								calendarioDAO.getEntityManager().getTransaction().rollback();
							}
						}								
					}
				} );				
			}
		});

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

	/**
	 * metodo que retorna la fecha para el encuentro de los equipos de cada grupo por dia
	 * @param diaAsignado
	 * @param mesAsignado
	 * @param anioAsignado
	 * @param posicionSemana
	 * @return
	 */
	private AuxFechaCampeonatoGenerado getFechaGenerada(Integer diaAsignado, Integer mesAsignado, Integer anioAsignado,Integer posicionSemana) {
		String semanaAsignada = "";  
		semanaAsignada = MetodoGeneral.getFechaDiaString(posicionSemana); 		
		AuxFechaCampeonatoGenerado objeto = new AuxFechaCampeonatoGenerado();
		objeto.setDescripcion(semanaAsignada);
		objeto.setDia(diaAsignado);
		objeto.setMes(mesAsignado);
		objeto.setAño(anioAsignado);		
		return objeto;
	}


	/**
	 * retorna los equipos locales y visitante por primera vez
	 * @param equipos
	 * @return
	 */
	private List<AuxEncuentroEquipo> getListEncuentroEquipo(List<Integer> equipos,Integer numeroPartido){
		List<AuxEncuentroEquipo> resultado = new ArrayList<AuxEncuentroEquipo>();
		Integer totalEquipo = equipos.size();
		Integer equipoLocal = 0;
		Integer contador = 0;		
		Boolean retorna = true;

		do {
			retorna = true;
			equipoLocal = MetodoGeneral.generarEquipoAleatorio(totalEquipo);
			if(resultado.size() > 0) {
				for (AuxEncuentroEquipo equipo : resultado) {
					if(equipo.getPosicionEquipoLocal() == equipoLocal ) {
						retorna = false;
						break;
					}
					if(equipo.getPosicionEquipoVisitante() == equipoLocal) {
						retorna = false;
						break;
					}
				}

				if(retorna){
					Boolean retornar = true;
					AuxEncuentroEquipo auxEncuentroEquipo = new AuxEncuentroEquipo();
					auxEncuentroEquipo.setPosicionEquipoLocal(equipoLocal);
					Integer equipoVisitante;
					do {
						retornar = true;
						equipoVisitante = MetodoGeneral.generarEquipoAleatorio(totalEquipo);
						for (AuxEncuentroEquipo a : resultado) {
							if(a.getPosicionEquipoLocal() == equipoVisitante ) {
								retornar = false;
								break;
							}
							if(a.getPosicionEquipoVisitante() == equipoVisitante) {
								retornar = false;
								break;
							}
						}	 
					} while (!retornar);

					if(equipoVisitante == equipoLocal) {
						retornar = false;
					}else {
						auxEncuentroEquipo.setPosicionEquipoVisitante(equipoVisitante);				 
						resultado.add(auxEncuentroEquipo);
						contador++;
					} 										
				}				
			}else {
				Boolean equipo = true;
				AuxEncuentroEquipo auxEncuentroEquipo = new AuxEncuentroEquipo();
				auxEncuentroEquipo.setPosicionEquipoLocal(equipoLocal);
				Integer equipoVisitante;
				do {
					equipo = true;
					equipoVisitante = MetodoGeneral.generarEquipoAleatorio(totalEquipo);
					if(equipoVisitante == equipoLocal) {
						equipo = false;
					}					
				} while (!equipo);
				auxEncuentroEquipo.setPosicionEquipoVisitante(equipoVisitante);				 
				resultado.add(auxEncuentroEquipo);
				contador++;
			}						
		} while (contador < numeroPartido);
		return resultado;
	}

	/**
	 * retorna los equipos locales y visitante 
	 * @param equipos
	 * @return
	 */
	private List<AuxEncuentroEquipo> getListaEquipoEncuentroFases(		  List<Integer> equipos,
			Integer numeroPartido, 
			List<AuxEquipoRandom> listaEncuentro, 
			String grupo,
			Integer totalPartidoEncuentro){
		List<AuxEncuentroEquipo> resultado = new ArrayList<AuxEncuentroEquipo>(); 

		Integer totalEquipo = equipos.size();
		Integer equipoLocal = 0;
		Integer contador = 0;
		Integer contadorEquipoLocal = 0; 
		Integer auxContadorEquipoLocal = 0; 
		Integer contadorEquipoVisitante = 0;
		Integer equipoVisitante = 0;		 
		Integer contadorPartidoLocalTotal = 0;

		Boolean retorna = false;	
		Boolean retornaTotalEquipoLocal = false;
		Boolean retornaTotalEquipoVisitante = false;
		Boolean retornaEquipoTotal = false;
		Boolean retornaAuxEquipoFantasma = false;

		do { 
			do {				
				retorna = true;
				retornaEquipoTotal = false;
				retornaAuxEquipoFantasma = false;

				List<AuxTotalEquipoLocal> auxTotalEquipo = new ArrayList<AuxTotalEquipoLocal>();
				Integer pl = totalEquipo;

				/**
				 * recorremos la lista de los equipos generado y contamos el total de enfretamiento 				
				 */
				for (int i = 0; i < pl; i++) {
					int j = i + 1;
					for (AuxEquipoRandom local : listaEncuentro) {
						if(grupo != null) {
							if(local.getDescripcion() != null) {
								if(local.getDescripcion().equals(grupo)) {
									for (AuxEncuentroEquipo auxEquipo : local.getAuxEncuentroEquipos()) {
										if(auxEquipo.getPosicionEquipoLocal() == j) {
											contadorPartidoLocalTotal = contadorPartidoLocalTotal + 1;									
										}							
									}  
								}
							}							
						}

					}

					if(resultado.size() > 0) {
						for (AuxEncuentroEquipo equipo : resultado) {							 
							if(equipo.getPosicionEquipoLocal() == j) {
								contadorPartidoLocalTotal = contadorPartidoLocalTotal + 1;	;
							} 
						}
					}	

					/**
					 * agregamos el total de partido de equipos locales para despues comparar
					 */
					if(contadorPartidoLocalTotal > 0) {
						if(grupo != null) {
							AuxTotalEquipoLocal auxTotalEquipoLocal = new AuxTotalEquipoLocal();
							auxTotalEquipoLocal.setGrupo(grupo);
							auxTotalEquipoLocal.setPosicionEquipoLocal(j);
							auxTotalEquipoLocal.setTotalEncuentro(contadorPartidoLocalTotal);
							auxTotalEquipo.add(auxTotalEquipoLocal);
							contadorPartidoLocalTotal = 0;
						}						
					}
				}

				Integer totalPartidoCompleto = 0;	
				Integer totalPartidoIncompleto = 0;
				if(auxTotalEquipo.size() > 0) {
					/**
					 * recorremos la lista de auxTotalEquipo equipos para verificar si todo los equipos tiene sus partidos completos
					 */
					for (AuxTotalEquipoLocal auxTotalEquipoLocal : auxTotalEquipo) {
						if(auxTotalEquipoLocal.getTotalEncuentro() == totalPartidoEncuentro) {
							totalPartidoCompleto = totalPartidoCompleto + 1;
						}
					}
					/**
					 * recorremos y verificamos si solo falta un equipo por generar su ultimo partido de encuentro
					 * para generar el equipo fantasma
					 */
					for (AuxTotalEquipoLocal auxTotalEquipoLocal : auxTotalEquipo) {
						Integer penultimoEncuentro = totalPartidoEncuentro - 1;
						if(auxTotalEquipoLocal.getTotalEncuentro() == penultimoEncuentro) {
							totalPartidoIncompleto = totalPartidoIncompleto + 1;
						}
					}
				}

				/**
				 * retornamos si los equipo tienen sus partidos completo
				 */
				if(totalPartidoCompleto == totalEquipo) {
					retornaEquipoTotal = true;
				}else {

					Integer totalPenultimoEncuentro = totalEquipo - 1;
					if(resultado.size() > 0) {
						/**
						 * esta condicion solo se cumple si lo demas equipos tienes sus partidos completo y ademas
						 * si solo falta aun equipo por generar su ultimo partido de encuentro,
						 * se procede a generar el equipo fantasma
						 */
						if(totalPartidoCompleto == totalPenultimoEncuentro && totalPartidoIncompleto == 1) {
							retornaEquipoTotal = true;
						}
					}else {
						retornaEquipoTotal = false;
					}					
				}

				if(!retornaEquipoTotal) {							 

					do {
						retornaTotalEquipoLocal = true;
						equipoLocal = MetodoGeneral.generarEquipoAleatorio(totalEquipo);	
						/**
						 * verificamos la cantidad total de encuentro del equipo generado de forma aleatoria de acuerdo al grupo pasado como parametro	
						 */
						for (AuxEquipoRandom local : listaEncuentro) {
							if(grupo != null) {
								if(local.getDescripcion() != null) {
									if(local.getDescripcion().equals(grupo)) {
										for (AuxEncuentroEquipo auxEquipo : local.getAuxEncuentroEquipos()) {
											if(auxEquipo.getPosicionEquipoLocal() == equipoLocal) {
												contadorEquipoLocal = contadorEquipoLocal + 1;										
											}							
										}
									}
								}
							}								
						}

						if(resultado.size() > 0) {
							for (AuxEncuentroEquipo equipo : resultado) {							 
								if(equipo.getPosicionEquipoLocal() == equipoLocal) {
									contadorEquipoLocal = contadorEquipoLocal + 1;	
								} 
							}
						}	

						if(contadorEquipoLocal == 0) {
							retornaTotalEquipoLocal = true;
							contadorEquipoLocal = 0; 
						}else {
							if(contadorEquipoLocal < totalPartidoEncuentro) {
								retornaTotalEquipoLocal = true;
								auxContadorEquipoLocal = contadorEquipoLocal;
								contadorEquipoLocal = 0; 
							} else {
								retornaTotalEquipoLocal = false;
								contadorEquipoLocal = 0; 
							}				
						}
					}while (!retornaTotalEquipoLocal);	

					if(resultado.size() > 0) { 
						/**
						 * verificamos que no se repita el numero del equipo generado anteriormente
						 */
						for (AuxEncuentroEquipo equipo : resultado) {
							if(equipo.getPosicionEquipoLocal() == equipoLocal ) {
								retorna = false;
								break;
							}
							if(equipo.getPosicionEquipoVisitante() == equipoLocal ) {
								retorna = false;
								break;
							}
						}
					}

					if(retorna) {
						retorna = true;						
						do {						
							equipoVisitante = MetodoGeneral.generarEquipoAleatorio(totalEquipo);
							/**
							 * verificamos si el equipo local se ha enfrentado con el equipoVisitante del grupo			
							 */
							for (AuxEquipoRandom visitante : listaEncuentro) {
								if(grupo != null) {
									if(visitante.getDescripcion() != null) {
										if(visitante.getDescripcion().equals(grupo)) {
											for (AuxEncuentroEquipo auxEquipoVisitante : visitante.getAuxEncuentroEquipos()) {															
												if(auxEquipoVisitante.getPosicionEquipoLocal() == equipoLocal &&  
														auxEquipoVisitante.getPosicionEquipoVisitante() == equipoVisitante) {
													contadorEquipoVisitante++;
												}							
											}
										}
									}
								}								
							}

							if(contadorEquipoVisitante == 0) {
								retornaTotalEquipoVisitante = true;
								contadorEquipoVisitante = 0; 
							}else {
								retornaTotalEquipoVisitante = false;
								contadorEquipoVisitante = 0; 									
							}

						} while (!retornaTotalEquipoVisitante);

						/**
						 * verificamos que el equipo visitante no se repita 
						 */
						if(resultado.size() > 0) { 
							for (AuxEncuentroEquipo a : resultado) {
								if(a.getPosicionEquipoLocal() == equipoVisitante ) {
									retorna = false;
									break;
								}
								if(a.getPosicionEquipoVisitante() == equipoVisitante) {
									retorna = false;
									break;
								}
							}	 
						}

						if(retorna) {
							if(equipoVisitante == equipoLocal) {
								retorna = false;
							}else {
								retornaAuxEquipoFantasma = false;
							}
						}else {
							Integer max = totalPartidoEncuentro;  
							Integer min = totalPartidoEncuentro -1;		
							if(auxContadorEquipoLocal >= min && auxContadorEquipoLocal <= max) {
								retornaAuxEquipoFantasma = true;
								retorna = true;
							}
						}
					}

				}else {
					retorna = true;
				}
			}while(!retorna);	

			if(retorna) {
				AuxEncuentroEquipo auxEncuentroEquipo = new AuxEncuentroEquipo();
				/**
				 * Esta condición solo se ejecuta cuando todos los equipos tienen sus partidos completos
				 */
				if(retornaEquipoTotal) {
					auxEncuentroEquipo.setPosicionEquipoLocal(0);			 
					auxEncuentroEquipo.setPosicionEquipoVisitante(0); 
					retornaEquipoTotal = false;
				}else {
					/** Esta condición solo se ejecuta cuando:
					 *  1) El equipo local tenga el mismo equipo visitante generado anteriormente al otro equipo Local y visitante
					 *  2) El equipo local se generó anteriormente y sea los ultimos partidos de enfrentamiento
					 */
					if(retornaAuxEquipoFantasma) {
						auxEncuentroEquipo.setPosicionEquipoLocal(0);			 
						auxEncuentroEquipo.setPosicionEquipoVisitante(0); 
						retornaAuxEquipoFantasma = false;
					}else {
						auxEncuentroEquipo.setPosicionEquipoLocal(equipoLocal);			 
						auxEncuentroEquipo.setPosicionEquipoVisitante(equipoVisitante);	
					}								 
				}
				resultado.add(auxEncuentroEquipo);
			} 
			contador++; 								
		} while (contador < numeroPartido);
		return resultado;
	}


	/**
	 * retorna los numeros de los equipo de forma aleatoria
	 * @param cantidad
	 * @return
	 */
	private List<Integer> getListaNumeroEquipoAleatorio(Integer cantidad){
		List<Integer> resultado = new ArrayList<Integer>();
		for(int j=0; j<cantidad ; j++) {
			if(resultado.size() > 0) {
				Boolean retorna = true;
				int numero;
				do {
					retorna = true;
					numero =  MetodoGeneral.generarGrupoAleatorio(campeonatoSeleccionado.getDetallecampeonatos().size());
					for (Integer integer : resultado) {
						if(integer == numero) {
							retorna = false;
							break;
						}
					}
				} while (!retorna);
				resultado.add(numero);					
			}else {
				int numero =  MetodoGeneral.generarGrupoAleatorio(campeonatoSeleccionado.getDetallecampeonatos().size());
				resultado.add(numero);
			}			
		}
		return resultado;
	}  


	/**
	 * retorna los numeros de los equipo de forma aleatoria
	 * @param cantidad
	 * @return
	 */
	private List<Integer> getGrupoNumeroEquipoAleatorio(Integer cantidad,List<AuxGrupoGeneradoAleatorio> grupo){
		List<Integer> resultado = new ArrayList<Integer>();
		Boolean retornaNumero = true;
		Boolean retornaGrupo = true;
		Boolean retornaResultado = true;
		Integer valor = 0;
		for(int j=0; j<cantidad ; j++) {
			if(resultado.size() > 0) {				
				int numero;
				do {
					retornaNumero = true;
					retornaGrupo = true;
					retornaResultado = true;
					valor = 0;
					numero =  MetodoGeneral.generarGrupoAleatorio(campeonatoSeleccionado.getDetallecampeonatos().size());
					for (Integer integer : resultado) {
						if(integer == numero) {
							retornaNumero = false;
							break;
						}
					}

					for (AuxGrupoGeneradoAleatorio objeto : grupo) {
						for (Integer integer : objeto.getGrupoGeneradoAleatorio()) {
							if(integer == numero) {
								retornaGrupo = false;
								valor = 1;
								break;
							}
						} 
						if(valor == 1) {
							break;
						}
					}

					if(retornaGrupo && retornaNumero) {
						retornaResultado = true;
					}else {
						retornaResultado = false;
					}	
				} while (!retornaResultado);
				resultado.add(numero);					
			}else {
				Integer numero;
				do {
					retornaGrupo = true; 
					valor = 0;
					numero =  MetodoGeneral.generarGrupoAleatorio(campeonatoSeleccionado.getDetallecampeonatos().size());									
					for (AuxGrupoGeneradoAleatorio objeto : grupo) {
						for (Integer integer : objeto.getGrupoGeneradoAleatorio()) {
							if(integer == numero) {
								retornaGrupo = false;
								valor = 1;
								break;
							}
						} 
						if(valor == 1) {
							break;
						}
					}					
				} while (!retornaGrupo);
				resultado.add(numero);
			}			
		}
		return resultado;
	} 

	@Command
	public void showCalendar() {
		HashMap<String,Object> parametros = new HashMap<String, Object>();
		parametros.put("calendarios", listaCalendario);
		Window ventanaCargar = (Window) Executions.createComponents("/Mantenimiento/calendario/calendario.zul", null, parametros);
		ventanaCargar.doModal();
	}

	@Command
	public void salir(@ContextParam(ContextType.VIEW) Component view) { 
		view.detach();
	}

	public List<AuxFechaCampeonatoGenerado> getListaFechaCampeonato() {
		return listaFechaCampeonato;
	}

	public void setListaFechaCampeonato(List<AuxFechaCampeonatoGenerado> listaFechaCampeonato) {
		this.listaFechaCampeonato = listaFechaCampeonato;
	}

	public List<Modalidad> getListaModalidad() {
		return modalidadDAO.getListaModalidad();
	}

	public List<Campeonato> getListaCampeonato(){
		return campeonatoDAO.getListaCampeonatoCalendarioDisponible();
	} 

	public List<Lugarpartido> getLugarEquipo() {
		return lugarEquipoDAO.getLugarEquipo();
	}

	public Campeonato getCampeonatoSeleccionado() {
		return campeonatoSeleccionado;
	}

	public void setCampeonatoSeleccionado(Campeonato campeonatoSeleccionado) {
		this.campeonatoSeleccionado = campeonatoSeleccionado;
	}

	public Lugarpartido getLugarSeleccionado() {
		return lugarSeleccionado;
	}

	public void setLugarSeleccionado(Lugarpartido lugarSeleccionado) {
		this.lugarSeleccionado = lugarSeleccionado;
	}

	public Modalidad getModalidadSeleccionada() {
		return modalidadSeleccionada;
	}

	public void setModalidadSeleccionada(Modalidad modalidadSeleccionada) {
		this.modalidadSeleccionada = modalidadSeleccionada;
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public List<AuxCalendario> getListaCalendario() {
		return listaCalendario;
	}

	public void setListaCalendario(List<AuxCalendario> listaCalendario) {
		this.listaCalendario = listaCalendario;
	} 	
}
