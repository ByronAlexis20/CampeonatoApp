package ec.edu.upse.gcf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.gcf.modelo.Detallecalendario;

@SuppressWarnings("unchecked")
public class DetallecalendarioDAO extends ClaseDAO{

	public List<Detallecalendario> getDetalleCalendarios(String value) {
		List<Detallecalendario> resultado; 
		String patron = value;
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("Detallecalendario.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado = (List<Detallecalendario>) query.getResultList();		
		return resultado;
	}		

	public List<Detallecalendario> getListaDetalleCalendarios() {
		List<Detallecalendario> retorno = new ArrayList<Detallecalendario>();
		Query query = getEntityManager().createNamedQuery("Detallecalendario.findAll");		
		retorno = (List<Detallecalendario>) query.getResultList();
		return retorno;
	}

	public List<Detallecalendario> getDetallecalendarioEquipo(String value) {
		List<Detallecalendario> resultado; 
		String patron = value;
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("Detallecalendario.buscarPorEquipo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado = (List<Detallecalendario>) query.getResultList();		
		return resultado;
	}		

	/**++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	/**
	 * retorna la lista de los equipos con los grupos de acuerdo al calendario
	 * @param campeonato
	 * @return
	 */	
	public List<Detallecalendario> getGrupoEquipoCalendario(Integer calendario) {
		List<Detallecalendario> resultado;  	 
		Query query = getEntityManager().createNamedQuery("Detallecalendario.buscarPorIdCalendario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", calendario);
		resultado = (List<Detallecalendario>) query.getResultList();
		return resultado;
	}	

	/**
	 * retorna la lista de los grupos de acuerdo al calendario
	 * @param campeonato
	 * @return
	 */	
	public List<Detallecalendario> getGrupoCalendario(Integer calendario) {
		List<Detallecalendario> resultado;  	 
		Query query = getEntityManager().createNamedQuery("Detallecalendario.buscarGrupoPorIdCalendario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", calendario);
		resultado = (List<Detallecalendario>) query.getResultList();
		return resultado;
	}	

	/**
	 * retorna el detalle del calendario ordenado por fecha de acuerdo al calendario
	 * @param campeonato
	 * @return
	 */	
	public List<Detallecalendario> getAgruparFechaCalendario(Integer calendario) {
		List<Detallecalendario> resultado;  	 
		Query query = getEntityManager().createNamedQuery("Detallecalendario.AgruparFechaCalendarioByIdCalendario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", calendario);
		resultado = (List<Detallecalendario>) query.getResultList();
		return resultado;
	}	
}
