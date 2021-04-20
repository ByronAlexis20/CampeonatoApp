package ec.edu.upse.gcf.dao;
import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.gcf.modelo.Tablaposicione;

@SuppressWarnings("unchecked")
public class TablaposicionDAO extends ClaseDAO {

	public List<Tablaposicione> getListaTablaposiciones() {
		List<Tablaposicione> resultado; 
		Query query = getEntityManager().createNamedQuery("Tablaposicione.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Tablaposicione>) query.getResultList();		
		return resultado;
	}

}
