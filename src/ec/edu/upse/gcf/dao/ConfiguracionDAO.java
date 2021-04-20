package ec.edu.upse.gcf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.gcf.modelo.Configuracion;

@SuppressWarnings("unchecked")
public class ConfiguracionDAO extends ClaseDAO{
	
	public List<Configuracion> getListaConfiguracion() {
		List<Configuracion> retorno = new ArrayList<Configuracion>();
		Query query = getEntityManager().createNamedQuery("Configuracion.findAll");
		retorno = (List<Configuracion>) query.getResultList();
		return retorno;
	}

}
