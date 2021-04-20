package ec.edu.upse.gcf.dao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

import ec.edu.upse.gcf.general.ConstanteApp;
import ec.edu.upse.gcf.modelo.Calendario;
import ec.edu.upse.gcf.modelo.Campeonato;

@SuppressWarnings("unchecked")
public class CampeonatoDAO extends ClaseDAO {
	public List<Campeonato> getCampeonatos(String value) {
		List<Campeonato> resultado; 
		String patron = value;
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("Campeonatos.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado = (List<Campeonato>) query.getResultList();		
		return resultado;
	}

	public List<Campeonato> getListaCampeonato() {
		List<Campeonato> resultado; 
		Query query = getEntityManager().createNamedQuery("Campeonato.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Campeonato>) query.getResultList();		
		return resultado;
	}

	public Campeonato getCampeonato(String nombreCampeonato) {
		Campeonato campeonato; 
		Query consulta;		
		consulta = getEntityManager().createNamedQuery("Campeonato.buscaCampeonato");
		consulta.setHint("javax.persistence.cache.storeMode", "REFRESH");
		consulta.setParameter("nombreCampeonato", nombreCampeonato);		
		campeonato = (Campeonato) consulta.getSingleResult();		
		return campeonato;
	}

	/**
	 * retorna la lista de campeonato disponible comparando con el calendario
	 * para la generacion del mismo
	 * @return
	 */
	public List<Campeonato> getListaCampeonatoCalendarioDisponible() {
		List<Campeonato> resultado = new ArrayList<>(); 
		//Listamos todos los campeonatos
		Query query = getEntityManager().createNamedQuery("Campeonato.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");		
		//retorna la lista de los campeonatos
		List<Campeonato> campeonatos = (List<Campeonato>) query.getResultList();

		//Listamos todos los calendarios
		Query queryCalendario = getEntityManager().createNamedQuery("Calendario.findAll");
		queryCalendario.setHint("javax.persistence.cache.storeMode", "REFRESH");
		//retorna la lista de calendarios generado
		List<Calendario> calendarios = (List<Calendario>) queryCalendario.getResultList();

		//recorremos la lista de campeonato
		for (Campeonato campeonato : campeonatos) {
			//verificamos los campeonatos disponible
			if(campeonato.getEstadocamp().equals(ConstanteApp.DISPONIBLE)) {
				Integer contadorCalendario = 0;
				//recorremos la lista de calendario
				for (Calendario calendario : calendarios) {
					if(calendario.getCampeonato().getIdCampeonato() == campeonato.getIdCampeonato()) {
						contadorCalendario++;
					}
				}
				if(contadorCalendario == 0) {
					resultado.add(campeonato);
				}else {
					contadorCalendario = 0;
				}
			}
		}
		return resultado;
	}
}
