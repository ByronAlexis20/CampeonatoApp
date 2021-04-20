package ec.edu.upse.gcf.dao;

import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.gcf.modelo.Calendario; 

@SuppressWarnings("unchecked")
public class CalendarioDAO extends ClaseDAO{

	public List<Calendario> getListaCalendario() {
		List<Calendario> resultado; 
		Query query = getEntityManager().createNamedQuery("Calendario.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Calendario>) query.getResultList();
		return resultado;
	}
	
	public List<Calendario> getCalendarios(String value) {
		List<Calendario> resultado; 
		String patron = value;

		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}
		Query query = getEntityManager().createNamedQuery("Calendario.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado = (List<Calendario>) query.getResultList();
		return resultado;
	}
	
}
