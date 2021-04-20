package ec.edu.upse.gcf.editar;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.edu.upse.gcf.dao.EquipojugadorDAO;
import ec.edu.upse.gcf.dao.PartidoDAO;
import ec.edu.upse.gcf.dao.TarjetaDAO;
import ec.edu.upse.gcf.modelo.Calendario;
import ec.edu.upse.gcf.modelo.Cambio;
import ec.edu.upse.gcf.modelo.Campeonato;
import ec.edu.upse.gcf.modelo.Detallecalendario;
import ec.edu.upse.gcf.modelo.Detallecampeonato;
import ec.edu.upse.gcf.modelo.Detallepartido;
import ec.edu.upse.gcf.modelo.Equipo;
import ec.edu.upse.gcf.modelo.Equipojugador;
import ec.edu.upse.gcf.modelo.Jugador;
import ec.edu.upse.gcf.modelo.Partido;
import ec.edu.upse.gcf.modelo.Tablaposicione;
import ec.edu.upse.gcf.modelo.Tarjeta;
import ec.edu.upse.gcf.modelo.Tarjetajugador;

public class IngresoPartido {
	@Wire private Window winIngresoPartidoEditar;
	private Detallecalendario detallecalendario;
	private Detallepartido detallepartido;
	private Calendario calendario;

	private List<Cambio> cambioJugadorElocal = new ArrayList<>();
	private List<Cambio> cambioJugadorEVisitante = new ArrayList<>();

	private List<Detallepartido> detGolesEquipoLocal = new ArrayList<>();
	private List<Detallepartido> detGolesEquipoVisitante = new ArrayList<>();

	private List<Tarjetajugador> tarjetaEquipoLocal = new ArrayList<>();
	private List<Tarjetajugador> tarjetaEquipoVisitante = new ArrayList<>();

	private EquipojugadorDAO equipoJugadorDao = new EquipojugadorDAO();
	private TarjetaDAO tarjetaDao = new TarjetaDAO();
	private PartidoDAO partidoDao = new PartidoDAO();
	//private TablaposicionDAO tablaposicionDao = new TablaposicionDAO();

	private Equipo equipo;
	private Campeonato campeonato;
	private Partido partidoC;
	private Jugador jugador;	
	private Equipojugador equipojugador = new Equipojugador();
	
	private String categoria;

	//private Partido partido = new Partido();

	@Init
	public void init(@ExecutionArgParam("detallecalendario") Detallecalendario detallecalendario) {
		 if (partidoC == null) {
			this.partidoC = new Partido();
		}else{		
			int contador = 0;
			for(Detallecampeonato detallecampeonato:detallecalendario.getCalendario().getCampeonato().getDetallecampeonatos()) { 
				if(contador == 0) {
					categoria = detallecampeonato.getCategoria().getNombre();  
					contador = contador + 1;
				}  
			}
			
		}
		 			
	}	
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		detallecalendario = (Detallecalendario) Executions.getCurrent().getArg().get("Detallecalendario");
		Partido partido = (Partido) Executions.getCurrent().getArg().get("Partido");
		if(partido == null) {
			partidoC = new Partido();
		}else {
			partidoC = partido;
			detallecalendario = partidoC.getDetallecalendario();
			detGolesEquipoLocal.addAll(partidoC.getDetallepartidos());
			detGolesEquipoVisitante.addAll(partidoC.getDetallepartidos());
			cambioJugadorElocal.addAll(partidoC.getCambios());
			cambioJugadorEVisitante.addAll(partidoC.getCambios());
			tarjetaEquipoLocal.addAll(partidoC.getTarjetajugadors());
			tarjetaEquipoVisitante.addAll(partidoC.getTarjetajugadors());		
		}
		init(detallecalendario);
	}

	/**++++++++++++++++++++++++++AGREGAR GOLES EQUIPO UNO+++++++++++++++++++++++++++++++*/
	@Command
	@NotifyChange({"detGolesEquipoLocal"})
	public void agregarGolesEquipouno() {
		Detallepartido detGolEquipoLocal = new Detallepartido();
		detGolesEquipoLocal.add(detGolEquipoLocal);		
	}	

	@Command
	@NotifyChange({"detGolesEquipoLocal"})
	public void quitarGolesEquipoUno(@BindingParam("detGolLocal") Detallepartido det ) {	
		detGolesEquipoLocal.remove(det);				
	}

	/**++++++++++++++++++++++++++AGREGAR GOLES EQUIPO DOS+++++++++++++++++++++++++++++++*/
	@Command
	@NotifyChange({"detGolesEquipoVisitante"})
	public void agregarGolesEquipodos() {
		Detallepartido detGolEquipoVisitante = new Detallepartido();
		detGolesEquipoVisitante.add(detGolEquipoVisitante);		
	}	

	@Command
	@NotifyChange({"detGolesEquipoVisitante"})
	public void quitarGolesEquipodos(@BindingParam("detGolVisitante") Detallepartido detv ) {	
		detGolesEquipoVisitante.remove(detv);				
	}

	/**++++++++++++++++++++++++++TARJETA EQUIPO LOCAL+++++++++++++++++++++++++++++++*/	
	@Command
	@NotifyChange({"tarjetaEquipoLocal"})
	public void agregarTarjetaEuno() {
		Tarjetajugador tarjetaLocal = new Tarjetajugador();
		tarjetaEquipoLocal.add(tarjetaLocal);		
	}	

	@Command
	@NotifyChange({"tarjetaEquipoLocal"})
	public void quitarTarjetaEuno(@BindingParam("tarjetaEl") Tarjetajugador tarjetaElocal ) {	
		tarjetaEquipoLocal.remove(tarjetaElocal);				
	}	

	/**++++++++++++++++++++++++++TARJETA EQUIPO VISITANTE+++++++++++++++++++++++++++++++*/	
	@Command
	@NotifyChange({"tarjetaEquipoVisitante"})
	public void agregarTarjetaEdos() {
		Tarjetajugador tarjetaVisitante = new Tarjetajugador();
		tarjetaEquipoVisitante.add(tarjetaVisitante);		
	}

	@Command
	@NotifyChange({"tarjetaEquipoVisitante"})
	public void quitarTarjetaEdos(@BindingParam("tarjetaEv") Tarjetajugador tarjetaEvisitante ) {	
		tarjetaEquipoVisitante.remove(tarjetaEvisitante);				
	}	

	/**++++++++++++++++++++++++++CAMBIOSO LOCAL+++++++++++++++++++++++++++++++*/	
	@Command
	@NotifyChange({"cambioJugadorElocal","equipojugadores"})
	public void agregarEuno() {
		Cambio cambioJugador = new Cambio();
		cambioJugadorElocal.add(cambioJugador);		
	}	

	@Command
	@NotifyChange({"cambioJugadorElocal"})
	public void quitarEuno(@BindingParam("cambioEuno") Cambio cambioJugador ) {	
		cambioJugadorElocal.remove(cambioJugador);				
	}	

	/**++++++++++++++++++++++++++CAMBIOSO LOCAL+++++++++++++++++++++++++++++++*/	
	@Command
	@NotifyChange({"cambioJugadorEVisitante"})
	public void agregarEdos() {
		Cambio cambioJugador = new Cambio();
		cambioJugadorEVisitante.add(cambioJugador);			
	}

	@Command
	@NotifyChange({"cambioJugadorEVisitante"})
	public void quitarEdos(@BindingParam("cambioEdos") Cambio cambioJugador ) {		
		cambioJugadorEVisitante.remove(cambioJugador);				
	}	

	/**+++++++++++++++++++++++++++++++++++++++++GRABAR+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void grabar(){

		Messagebox.show("Desea guardar el registro?", "Confirmación de Guardar", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						partidoDao.getEntityManager().getTransaction().begin();
						if(partidoC.getIdPartido() == 0) {
							/** ----------------------GUARDADO DE PARTIDO-------------------------*/						
							partidoC.setDetallecalendario(detallecalendario);
							partidoC.setFecha(detallecalendario.getFecha());
							partidoC.setHora(detallecalendario.getHora());
							partidoC.setArbitro(partidoC.getArbitro().trim());				
							partidoC.setNovedadarbitrolocal(partidoC.getNovedadarbitrolocal().trim());
							partidoC.setNovedadarbitrovisitante(partidoC.getNovedadarbitrolocal().trim());
							partidoC.setVocal(partidoC.getVocal().trim());
							partidoC.setNovedadvocal(partidoC.getNovedadvocal());
							partidoC.setResultadoequipoL(partidoC.getResultadoequipoL());
							partidoC.setResultadoequipoV(partidoC.getResultadoequipoV());

							/** ----------------------GUARDADO DE DETALLEPARTIDO-------------------------*/
							List<Detallepartido> listadetallep = new ArrayList<Detallepartido>();
							if(!detGolesEquipoLocal.isEmpty()) {
								for(Detallepartido dl : detGolesEquipoLocal ) {
									Detallepartido graba = new Detallepartido();
									graba.setPartido(partidoC);
									graba.setEquipojugador(dl.getEquipojugador());
									graba.setGolpt(dl.getGolpt());
									graba.setGolst(dl.getGolst());
									graba.setEstadojugador("TITULAR");
									listadetallep.add(graba);
								}
							}
							if(!detGolesEquipoVisitante.isEmpty()) {
								for(Detallepartido dv : detGolesEquipoVisitante ) {
									Detallepartido graba = new Detallepartido();
									graba.setPartido(partidoC);
									graba.setEquipojugador(dv.getEquipojugador());
									graba.setGolpt(dv.getGolpt());
									graba.setGolst(dv.getGolst());
									graba.setEstadojugador("TITULAR");
									listadetallep.add(graba);									
								}
							}
							
							/** ----------------------GUARDADO DE CAMBIOS JUGADORES-------------------------*/
							List<Cambio> listacambios = new ArrayList<Cambio>();
							if(!cambioJugadorElocal.isEmpty()) {
								for(Cambio cl : cambioJugadorElocal) {
									Cambio grabaCambioL = new Cambio();
									grabaCambioL.setPartido(partidoC);
									grabaCambioL.setEquipojugador1(cl.getEquipojugador1());
									grabaCambioL.setEquipojugador2(cl.getEquipojugador2());
									grabaCambioL.setTiempo(cl.getTiempo());
									listacambios.add(grabaCambioL);
								}
							}						
							
							if(!cambioJugadorEVisitante.isEmpty()) {
								for(Cambio cv : cambioJugadorEVisitante) {
									Cambio grabaCambioV = new Cambio();
									grabaCambioV.setPartido(partidoC);
									grabaCambioV.setEquipojugador1(cv.getEquipojugador1());
									grabaCambioV.setEquipojugador2(cv.getEquipojugador2());
									grabaCambioV.setTiempo(cv.getTiempo());
									listacambios.add(grabaCambioV);									
									
								}
							}							
							
							/** ----------------------GUARDADO DE TARJETAS JUGADORES-------------------------*/
							List<Tarjetajugador> listaTarjetajugador = new ArrayList<Tarjetajugador>();
							if(!tarjetaEquipoLocal.isEmpty()) {
								for(Tarjetajugador tl : tarjetaEquipoLocal) {
									Tarjetajugador tarjetaL = new Tarjetajugador();
									tarjetaL.setPartido(partidoC);
									tarjetaL.setTarjeta(tl.getTarjeta());
									tarjetaL.setEquipojugador(tl.getEquipojugador());
									tarjetaL.setTiempo(tl.getTiempo());
									tarjetaL.setCantidad(tl.getCantidad());
									tarjetaL.setMotivo(tl.getMotivo());
									listaTarjetajugador.add(tarjetaL);
								}
							}						
							
							if(!tarjetaEquipoVisitante.isEmpty()) {
								for(Tarjetajugador tv : tarjetaEquipoVisitante) {
									Tarjetajugador tarjetaV = new Tarjetajugador();
									tarjetaV.setPartido(partidoC);
									tarjetaV.setTarjeta(tv.getTarjeta());
									tarjetaV.setEquipojugador(tv.getEquipojugador());
									tarjetaV.setTiempo(tv.getTiempo());
									tarjetaV.setCantidad(tv.getCantidad());
									tarjetaV.setMotivo(tv.getMotivo());
									listaTarjetajugador.add(tarjetaV);
								}
							}							

							/** ----------------------GUARDADO DE TABLA DE POSICIONES-------------------------*/
							List<Tablaposicione> listaTablaposiciones = new ArrayList<Tablaposicione>();
							//VALIDAMOS EL EQUIPO GANADOR DEL PARTIDO PARA LA ASIGNACIÓN DE 3 PUNTOS
							// EN LA TABLAPOSICIONES 
							/**++++++++++++++++++++++++++VARIABLES LOCAL++++++++++++++++++++++++*/
							Integer ptosL = 0;

							Integer partidosGanadosL = 0;
							Integer partidosPerdidosL = 0;
							Integer partidosEmpatadosL = 0;

							Integer golesFavorL = 0;
							Integer golesContraL = 0;
							Integer golesDiferenciaL = 0;

							/**++++++++++++++++++++++++++VARIABLES VISITANTE++++++++++++++++++++++++*/						
							Integer ptosV = 0;

							Integer partidosGanadosV = 0;
							Integer partidosPerdidosV = 0;
							Integer partidosEmpatadosV = 0;

							Integer golesFavorV = 0;
							Integer golesContraV = 0;
							Integer golesDiferenciaV = 0;

							/**+++++++++++++++++++++++++++++LOCAL++++++++++++++++++++++++++++++++++++++*/				
							//validamos si el equipo local gano al equipo visitante
							if(partidoC.getResultadoequipoL() > partidoC.getResultadoequipoV()) {
								ptosL = 3;
								partidosGanadosL = 1;

							}else {
								if(partidoC.getResultadoequipoL() == partidoC.getResultadoequipoV()) {
									ptosL = 1;
									partidosEmpatadosL = 1;
								}else {
									if(partidoC.getResultadoequipoL() < partidoC.getResultadoequipoV()) {
										ptosL = 0;
										partidosPerdidosL = 1;
									}
								}
							}
							golesFavorL = partidoC.getResultadoequipoL();
							golesContraL = partidoC.getResultadoequipoV();
							golesDiferenciaL = golesFavorL - golesContraL;

							//instanciamos el objeto tablaposiciones para la el registro de la tabla de posiciones
							Tablaposicione tablaposicionesL = new Tablaposicione();
							tablaposicionesL.setPartido(partidoC);
							tablaposicionesL.setDetallecalendario(detallecalendario);
							tablaposicionesL.setEquipo(detallecalendario.getEquipoLocal());
							tablaposicionesL.setPj(1);
							tablaposicionesL.setPg(partidosGanadosL);
							tablaposicionesL.setPe(partidosEmpatadosL);
							tablaposicionesL.setPp(partidosPerdidosL);
							tablaposicionesL.setGf(golesFavorL);
							tablaposicionesL.setGc(golesContraL);
							tablaposicionesL.setGd(golesDiferenciaL);
							tablaposicionesL.setPtos(ptosL);
							listaTablaposiciones.add(tablaposicionesL);

							/**+++++++++++++++++++++++++++++LOCAL++++++++++++++++++++++++++++++++++++++*/			
							//validamos si el equipo visitante gano al equipo local
							if(partidoC.getResultadoequipoV() > partidoC.getResultadoequipoL()) {
								ptosV = 3;
								partidosGanadosV = 1;
							}else {
								if(partidoC.getResultadoequipoV() == partidoC.getResultadoequipoL()) {
									ptosV = 1;
									partidosEmpatadosV = 1;
								}else {
									if(partidoC.getResultadoequipoV() < partidoC.getResultadoequipoL()) {
										ptosV = 0;
										partidosPerdidosV = 1;
									}
								}
							}
							golesFavorV = partidoC.getResultadoequipoV();
							golesContraV = partidoC.getResultadoequipoL();
							golesDiferenciaV = golesFavorV - golesContraV;

							//instanciamos el objeto tablaposiciones para la el registro de la tabla de posiciones
							Tablaposicione tablaposicionesV = new Tablaposicione();
							tablaposicionesV.setPartido(partidoC);
							tablaposicionesV.setDetallecalendario(detallecalendario);
							tablaposicionesV.setEquipo(detallecalendario.getEquipoVisitante());
							tablaposicionesV.setPj(1);
							tablaposicionesV.setPg(partidosGanadosV);
							tablaposicionesV.setPe(partidosEmpatadosV);
							tablaposicionesV.setPp(partidosPerdidosV);
							tablaposicionesV.setGf(golesFavorV);
							tablaposicionesV.setGc(golesContraV);
							tablaposicionesV.setGd(golesDiferenciaV);
							tablaposicionesV.setPtos(ptosV);
							listaTablaposiciones.add(tablaposicionesV);


							partidoC.setDetallepartidos(listadetallep);	
							partidoC.setCambios(listacambios);
							partidoC.setTarjetajugadors(listaTarjetajugador);
							partidoC.setTablaposiciones(listaTablaposiciones);							
							
							partidoDao.getEntityManager().persist(partidoC);	
						}else {
							/**+++++++++++++++++++++++++MODIFICA GOLES EQUIPO LOCAL++++++++++++++++++++++++++++*/							
							List<Detallepartido> listadetallep = new ArrayList<Detallepartido>();
							Integer contador = 0;
							if(!detGolesEquipoLocal.isEmpty()) {
								for(Detallepartido dpl : detGolesEquipoLocal ) {
									for(Detallepartido local : partidoC.getDetallepartidos()) {
										if(dpl.getEquipojugador() == local.getEquipojugador()) {
											contador++;
										}
									}
									if(contador == 0) {
										Detallepartido grabaNuevoLocal = new Detallepartido();
										grabaNuevoLocal.setPartido(partidoC);
										grabaNuevoLocal.setEquipojugador(dpl.getEquipojugador());
										grabaNuevoLocal.setGolpt(dpl.getGolpt());
										grabaNuevoLocal.setGolst(dpl.getGolst());
										listadetallep.add(grabaNuevoLocal);										
									}
									contador = 0;
								}
							}
							/**+++++++++++++++++++++++++MODIFICA GOLES EQUIPO VISITANTE++++++++++++++++++++++++++++*/							
							Integer cont = 0;
							if(!detGolesEquipoVisitante.isEmpty()) {
								for(Detallepartido dpv : detGolesEquipoVisitante ) {
									for(Detallepartido visitante : partidoC.getDetallepartidos()) {
										if(dpv.getEquipojugador() == visitante.getEquipojugador()) {
											cont++;
										}
									}
									if(cont == 0) {
										Detallepartido grabaNuevoVisitante = new Detallepartido();
										grabaNuevoVisitante.setPartido(partidoC);
										grabaNuevoVisitante.setEquipojugador(dpv.getEquipojugador());
										grabaNuevoVisitante.setGolpt(dpv.getGolpt());
										grabaNuevoVisitante.setGolst(dpv.getGolst());
										listadetallep.add(grabaNuevoVisitante);										
									}
									contador = 0;
								}
							}
							/**+++++++++++++++++++++++++MODIFICA CAMBIOS EQUIPO LOCAL++++++++++++++++++++++++++++*/							
							List<Cambio> listacambios = new ArrayList<Cambio>();
							Integer contL = 0;
							if(!cambioJugadorElocal.isEmpty()) {
								for(Cambio cl : cambioJugadorElocal ) {
									for(Cambio local : partidoC.getCambios()) {
										if(cl.getEquipojugador1() == local.getEquipojugador1()
												&& cl.getEquipojugador2() == local.getEquipojugador2()) {
											contL++;
										}
									}
									if(contL == 0) {
										Cambio grabaNCambioLocal = new Cambio();
										grabaNCambioLocal.setPartido(partidoC);
										grabaNCambioLocal.setEquipojugador1(cl.getEquipojugador1());
										grabaNCambioLocal.setEquipojugador2(cl.getEquipojugador2());
										grabaNCambioLocal.setTiempo(cl.getTiempo());										
										listacambios.add(grabaNCambioLocal);										
									}
									contL = 0;
								}
							}
							/**+++++++++++++++++++++++++MODIFICA CAMBIOS EQUIPO VISITANTE++++++++++++++++++++++++++++*/							
							Integer contV = 0;
							if(!cambioJugadorEVisitante.isEmpty()) {
								for(Cambio cv : cambioJugadorEVisitante ) {
									for(Cambio visitante : partidoC.getCambios()) {
										if(cv.getEquipojugador1() == visitante.getEquipojugador1()
												&& cv.getEquipojugador2() == visitante.getEquipojugador2()) {
											contL++;
										}
									}
									if(contV == 0) {
										Cambio grabaNCambioVisitante = new Cambio();
										grabaNCambioVisitante.setPartido(partidoC);
										grabaNCambioVisitante.setEquipojugador1(cv.getEquipojugador1());
										grabaNCambioVisitante.setEquipojugador2(cv.getEquipojugador2());
										grabaNCambioVisitante.setTiempo(cv.getTiempo());										
										listacambios.add(grabaNCambioVisitante);										
									}
									contV = 0;
								}
							}
							/**+++++++++++++++++++++++++MODIFICA TARJETAJUGADOR EQUIPO LOCAL++++++++++++++++++++++++++++*/							
							List<Tarjetajugador> listaTarjetajugador = new ArrayList<Tarjetajugador>();
							Integer conTL = 0;
							if(!tarjetaEquipoLocal.isEmpty()) {
								for(Tarjetajugador tl : tarjetaEquipoLocal ) {
									for(Tarjetajugador local : partidoC.getTarjetajugadors()) {
										if(tl.getEquipojugador() == local.getEquipojugador()) {
											conTL++;
										}
									}
									if(conTL == 0) {
										Tarjetajugador grabaNTarjetaLocal = new Tarjetajugador();
										grabaNTarjetaLocal.setPartido(partidoC);
										grabaNTarjetaLocal.setEquipojugador(tl.getEquipojugador());
										grabaNTarjetaLocal.setTarjeta(tl.getTarjeta());
										grabaNTarjetaLocal.setTiempo(tl.getTiempo());
										grabaNTarjetaLocal.setCantidad(tl.getCantidad());
										grabaNTarjetaLocal.setMotivo(tl.getMotivo());
										listaTarjetajugador.add(grabaNTarjetaLocal);										
									}
									conTL = 0;
								}
							}
							/**+++++++++++++++++++++++++MODIFICA TARJETAJUGADOR EQUIPO VISITANTE++++++++++++++++++++++++++++*/							
							Integer conTV = 0;
							if(!tarjetaEquipoVisitante.isEmpty()) {
								for(Tarjetajugador tv : tarjetaEquipoVisitante ) {
									for(Tarjetajugador visitante : partidoC.getTarjetajugadors()) {
										if(tv.getEquipojugador() == visitante.getEquipojugador()) {
											conTV++;
										}
									}
									if(conTV == 0) {
										Tarjetajugador grabaNTarjetaVisitante = new Tarjetajugador();
										grabaNTarjetaVisitante.setPartido(partidoC);
										grabaNTarjetaVisitante.setEquipojugador(tv.getEquipojugador());
										grabaNTarjetaVisitante.setTarjeta(tv.getTarjeta());
										grabaNTarjetaVisitante.setTiempo(tv.getTiempo());
										grabaNTarjetaVisitante.setCantidad(tv.getCantidad());
										grabaNTarjetaVisitante.setMotivo(tv.getMotivo());
										listaTarjetajugador.add(grabaNTarjetaVisitante);										
									}
									conTV = 0;
								}
							}
							
							partidoC.setDetallepartidos(listadetallep);
							partidoC.setCambios(listacambios);
							partidoC.setTarjetajugadors(listaTarjetajugador);
							//partidoC.setTablaposiciones(listaTablaposiciones);
							partidoDao.getEntityManager().merge(partidoC);
						}
						partidoDao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Proceso Ejecutado con exito.");
						BindUtils.postGlobalCommand(null, null, "Partido.buscarPorPatron", null);
						salir();
					} catch (Exception e) {
						e.printStackTrace();						
						partidoDao.getEntityManager().getTransaction().rollback();						
					}		
				}
			}
		});	
	}

	@Command
	public void salir(){
		winIngresoPartidoEditar.detach();
	} 

	/**+++++++++++++++++++++++++++++++++++++++++LISTAS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/	
	public List<Equipojugador> getGolesEquipoLocal() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getGolesEquipoVisitante() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadores() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadoresdos() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadoresCambiouno() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadoresCambiodos() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadoresTarjetaEuno() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Equipojugador> getEquipojugadoresTarjetaEdos() {
		return equipoJugadorDao.getListaEquipojugadores();
	}

	public List<Tarjeta> getTarjetajugadoresEuno() {
		return tarjetaDao.getTarjetas();
	}

	public List<Tarjeta> getTarjetajugadoresEdos() {
		return tarjetaDao.getTarjetas();
	}

	/**+++++++++++++++++++++++++++++++++++++++++GETTER AND SETTER+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/	
	public Detallecalendario getDetallecalendario() {
		return detallecalendario;
	}

	public void setDetallecalendario(Detallecalendario detallecalendario) {
		this.detallecalendario = detallecalendario;
	}

	public EquipojugadorDAO getEquipoJugadorDao() {
		return equipoJugadorDao;
	}

	public void setEquipoJugadorDao(EquipojugadorDAO equipoJugadorDao) {
		this.equipoJugadorDao = equipoJugadorDao;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Equipojugador getEquipojugador() {
		return equipojugador;
	}

	public void setEquipojugador(Equipojugador equipojugador) {
		this.equipojugador = equipojugador;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public void setCambioJugadorElocal(List<Cambio> cambioJugadorElocal) {
		this.cambioJugadorElocal = cambioJugadorElocal;
	}

	public List<Cambio> getCambioJugadorEVisitante() {
		return cambioJugadorEVisitante;
	}

	public void setCambioJugadorEVisitante(List<Cambio> cambioJugadorEVisitante) {
		this.cambioJugadorEVisitante = cambioJugadorEVisitante;
	}

	public Partido getPartidoC() {
		return partidoC;
	}

	public void setPartidoC(Partido partidoC) {
		this.partidoC = partidoC;
	}

	public List<Cambio> getCambioJugadorElocal() {
		return cambioJugadorElocal;
	}

	public Detallepartido getDetallepartido() {
		return detallepartido;
	}

	public void setDetallepartido(Detallepartido detallepartido) {
		this.detallepartido = detallepartido;
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public List<Detallepartido> getDetGolesEquipoLocal() {
		return detGolesEquipoLocal;
	}

	public void setDetGolesEquipoLocal(List<Detallepartido> detGolesEquipoLocal) {
		this.detGolesEquipoLocal = detGolesEquipoLocal;
	}

	public List<Detallepartido> getDetGolesEquipoVisitante() {
		return detGolesEquipoVisitante;
	}

	public void setDetGolesEquipoVisitante(List<Detallepartido> detGolesEquipoVisitante) {
		this.detGolesEquipoVisitante = detGolesEquipoVisitante;
	}

	public List<Tarjetajugador> getTarjetaEquipoLocal() {
		return tarjetaEquipoLocal;
	}

	public void setTarjetaEquipoLocal(List<Tarjetajugador> tarjetaEquipoLocal) {
		this.tarjetaEquipoLocal = tarjetaEquipoLocal;
	}

	public List<Tarjetajugador> getTarjetaEquipoVisitante() {
		return tarjetaEquipoVisitante;
	}

	public void setTarjetaEquipoVisitante(List<Tarjetajugador> tarjetaEquipoVisitante) {
		this.tarjetaEquipoVisitante = tarjetaEquipoVisitante;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}	
}
