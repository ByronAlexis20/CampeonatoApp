package ec.edu.upse.gcf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.gcf.modelo.Cambio;

@SuppressWarnings("unchecked")
public class CambioDAO extends ClaseDAO {	
	public List<Cambio> getListaCambios() {
		List<Cambio> retorno = new ArrayList<Cambio>();

		Query query = getEntityManager().createNamedQuery("Cambio.findAll");
		retorno = (List<Cambio>) query.getResultList();

		return retorno;
	}		
}
