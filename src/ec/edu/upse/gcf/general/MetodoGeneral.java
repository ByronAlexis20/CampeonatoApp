package ec.edu.upse.gcf.general;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import ec.edu.upse.gcf.auxiliar.AuxFecha;

/**
 * Clase para los metodos generales de la aplicacion
 * @author Dayana Tigrero
 *
 */
public class MetodoGeneral {
	
	
	/**
	 * Retorna el dia de la fecha
	 * @param fecha
	 * @return
	 */
	public static Integer getNumeroDia(Date fecha) {
		String formato="dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return Integer.parseInt(dateFormat.format(fecha));
	}
	
	
	/**
	 * Retorna el numero del mes de la fecha
	 * @param fecha
	 * @return
	 */
	public static Integer getNumeroMes(Date fecha) {
		String formato="MM";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return Integer.parseInt(dateFormat.format(fecha));
	}
	
	/**
	 * Retorna el año de la fecha
	 * @param fecha
	 * @return
	 */
	public static Integer getNumeroAño(Date fecha) {
		String formato="yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return Integer.parseInt(dateFormat.format(fecha));
	}
	

	/**
	 * Retorna el objeto 
	 * @param dia
	 * @param mes
	 * @return
	 */
	public static AuxFecha validaFechaCampeonato(Integer dia,Integer mes) {
		AuxFecha auxFecha = new AuxFecha();
		switch (mes) {
		//Enero
		case 1:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Febrero	
		case 2:
			if(dia >= 1  && dia <= 28) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Marzo	
		case 3:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Abril
		case 4:
			if(dia >= 1  && dia <= 30) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Mayo
		case 5:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Junio
		case 6:
			if(dia >= 1  && dia <= 30) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;			
		//Julio	
		case 7:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Agosto
		case 8:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Septiembre
		case 9:
			if(dia >= 1  && dia <= 30) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Octubre	
		case 10:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Noviembre	
		case 11:
			if(dia >= 1  && dia <= 30) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
		//Diciembre	
		case 12:
			if(dia >= 1  && dia <= 31) {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_CORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);
			}else {
				auxFecha.setAuxDia(ConstanteApp.ESTADO_FECHA_DIA_INCORRECTO);
				auxFecha.setAuxMes(ConstanteApp.ESTADO_FECHA_MES_CORRECTO);	
			}
			break;
			
		default:
			break;
		}
		return auxFecha;
	}
	
	/**
	 * Retorna la semana 
	 * @param i
	 * @return
	 */
    public static String getFechaDiaString(Integer i){
        String resultado = "";
        switch (i){
            case 0:
                resultado = ConstanteApp.Domingo;
                return resultado;
            case 1:
                resultado = ConstanteApp.Lunes;
                return resultado;
            case 2:
                resultado = ConstanteApp.Martes;
                return resultado;
            case 3:
                resultado = ConstanteApp.Miercoles;
                return resultado;
            case 4:
                resultado = ConstanteApp.Jueves;
                return resultado;
            case 5:
                resultado = ConstanteApp.Viernes;
                return resultado;
            case 6:
                resultado = ConstanteApp.Sabado;
                return resultado;
        }
        return resultado;
    }
    
    /**
	 * Retorna la posicion de la semana 
	 * @param semana
	 * @return
	 */
    public static Integer getFechaDiaInteger(String semana){
        Integer resultado = 0;
        switch (semana){
            case ConstanteApp.Domingo:
                resultado = 0;
                return resultado;
            case ConstanteApp.Lunes:
                resultado = 1;
                return resultado;
            case ConstanteApp.Martes:
                resultado = 2;
                return resultado;
            case ConstanteApp.Miercoles:
                resultado = 3;
                return resultado;
            case ConstanteApp.Jueves:
                resultado = 4;
                return resultado;
            case ConstanteApp.Viernes:
                resultado = 5;
                return resultado;
            case ConstanteApp.Sabado:
                resultado = 6;
                return resultado;
        }
        return resultado;
    }
	
    /**
     * Retorna un numero aleatorio del 0 a n equipo
     * @return
     */
    public static Integer generarNumeroPartidoAleatorio(int numeroTotal){
		try {
			Random r = new Random(System.currentTimeMillis());
			Integer resultado = r.nextInt(numeroTotal)+1;
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		} 
		
	}
    
    
    /**
     * Retorna un numero aleatorio del 0 al 6
     * @return
     */
    public static Integer generarNumeroAleatorio(){
		try {
			Random r = new Random(System.currentTimeMillis());
			Integer resultado = r.nextInt(6)+1;
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		} 
		
	}
    
    /**
     * Retorna un numero aleatorio del 0 a n equipo
     * @return
     */
    public static Integer generarGrupoAleatorio(int numeroTotal){
		try {
			Random r = new Random(System.currentTimeMillis());
			Integer resultado = r.nextInt(numeroTotal)+1;
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		} 
		
	}
    
    /**
     * Retorna un numero aleatorio del 0 a n equipo
     * @return
     */
    public static Integer generarEquipoAleatorio(int numeroTotal){
		try {
			Random r = new Random(System.currentTimeMillis());
			Integer resultado = r.nextInt(numeroTotal)+1;
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		} 
		
	}
    
    /**
     * retorna la fecha de un tipo String a tipo Date
     */
	public static Date getStringToDate(String fecha) {
		Date retorna =null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			retorna = simpleDateFormat.parse(fecha);
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
		return retorna;
	}
}
