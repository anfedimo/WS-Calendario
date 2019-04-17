package co.sistemcobro.calendario.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;



public class Util {

	public static void main(String[] args) {
		System.out.println(Util.convertSecondToCronometer(86400));
	}
	
	  /**
     * Convierte numero de segundos a un cronometro de HH:MM:ss
     * @param seconds
     * @return
     */
    public static String convertSecondToCronometer(int seconds) {
    	String cronometer = "";
    	int hours = 0;
    	int minutes = 0;
    	// Mayor a 24 horas no se convertira en un cronometro
    	
    	if(86400 > seconds && seconds >= 0) {
    		
    		hours = seconds / (60*60);
    		seconds = seconds - (hours*60*60);
    		
    		
    		if(seconds > 0) {
    			minutes = seconds / (60);
    			seconds = seconds - (minutes*60);
    		}
    	
    		cronometer = (hours > 0 ? String.format("%02d",hours) + ":" : "") + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    		
    	} else if(86400 <= seconds) {
    		cronometer = "1+ dia";
    		
    	} else {
    		cronometer = "00:00:00";
    	}
    	
    	
    	
    	return cronometer;
    	
    	
    }
	
	
	
	
	public enum Alineacion {
		IZQUIERDA, DERECHA
	}

	public static String FORMATO_FECHA_YYYYMMDD = "yyyyMMdd";
	public static String FORMATO_FECHA_DDMMYYYY = "ddMMyyyy";
	private static final Logger logger = Logger.getLogger(Util.class);

	public static int diferenciaEnDias(GregorianCalendar datemin, GregorianCalendar datemax) {
		if (datemin.get(Calendar.YEAR) == datemax.get(Calendar.YEAR)) {
			return (datemax.get(Calendar.DAY_OF_YEAR) - datemin.get(Calendar.DAY_OF_YEAR));
		} else {
			/*
			 * SI ESTAMOS EN DISTINTO ANYO COMPROBAMOS QUE EL ANYO DEL DATEINI NO SEA BISIESTO SI ES BISIESTO SON 366 DIAS EL ANYO SINO SON 365
			 */
			int diasAnyo = datemin.isLeapYear(datemin.get(Calendar.YEAR)) ? 366 : 365;

			/* CALCULAMOS EL RANGO DE ANYOS */
			int rangoAnyos = datemax.get(Calendar.YEAR) - datemin.get(Calendar.YEAR);

			/* CALCULAMOS EL RANGO DE DIAS QUE HAY */
			int rango = (rangoAnyos * diasAnyo) + (datemax.get(Calendar.DAY_OF_YEAR) - datemin.get(Calendar.DAY_OF_YEAR));

			return rango;
		}
	}

	/**
	 * Metodo que genera una cadena con la longitud requerida y los campos de relleno pedido, esta cadena sera adjuntada al archivo que se generara para pasarlo a la clase ...
	 * 
	 * @param cadena
	 *            cadena que llega del archivo del usuario o el valor fijo especificado
	 * @param align
	 *            alineacion de la cadena puede ser a la izquierda (L) o a la derecha (R)
	 * @param character
	 *            caracter para rellenar la cadena
	 * @param length
	 *            longitud de la cadena
	 * @return cadena completa
	 */
	public static String compCadena(String cadena, Alineacion align, char character, int length) {

		StringBuffer sb = new StringBuffer();
		StringBuffer toComplete = new StringBuffer();

		try {
			if (cadena == null)
				throw new NullPointerException();

			if (cadena.length() > length) {
				logger.warn("La cadena: \"" + cadena + "\" fue cortada ya que la longitud maxima es de: " + length);
				cadena = cadena.substring(0, length);
			}

			for (int i = 0; i < length - cadena.length(); i++) {
				toComplete.append(character);
			}
			if (align == Alineacion.DERECHA) {
				sb.append(cadena).append(toComplete);
			} else {
				sb.append(toComplete).append(cadena);
			}

		} catch (NullPointerException e) {
			logger.error("La cadena ingresada es null", e.fillInStackTrace());
		} catch (Exception e) {
			logger.error(e, e.fillInStackTrace());
		}
		return sb.toString();
	}


	/**
	 * Retorna el primer día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static Calendar getPrimerDia(Calendar fechain) {
		Calendar primerdia = Calendar.getInstance();
		try {
			primerdia.set(fechain.get(Calendar.YEAR), fechain.get(Calendar.MONTH), fechain.getActualMinimum(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primerdia;
	}

	/**
	 * Retorna el primer día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static String getPrimerDia(Calendar fechain, String formatoout) {
		String primerdia = "";
		DateFormat formatOUT = new SimpleDateFormat(formatoout);

		try {
			fechain.set(fechain.get(Calendar.YEAR), fechain.get(Calendar.MONTH), fechain.getActualMinimum(Calendar.DAY_OF_MONTH));

			primerdia = formatOUT.format(fechain.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primerdia;
	}

	/**
	 * Retorna el último día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static Calendar getUltimoDia(Calendar fechain) {
		try {
			fechain.set(fechain.get(Calendar.YEAR), fechain.get(Calendar.MONTH), fechain.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fechain;
	}

	/**
	 * Retorna el último día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static String getUltimoDia(Calendar fechain, String formatoout) {
		String primerdia = "";
		DateFormat formatOUT = new SimpleDateFormat(formatoout);

		try {
			fechain.set(fechain.get(Calendar.YEAR), fechain.get(Calendar.MONTH), fechain.getActualMaximum(Calendar.DAY_OF_MONTH));

			primerdia = formatOUT.format(fechain.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primerdia;
	}

	/**
	 * Retorna el primer día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static String getPrimerDia(String fechain, String formatoin, String formatoout) {
		String primerdia = "";
		DateFormat formatOUT = new SimpleDateFormat(formatoout);
		DateFormat formatIN = new SimpleDateFormat(formatoin);

		try {
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(formatIN.parse(fechain));

			ahora.set(ahora.get(Calendar.YEAR), ahora.get(Calendar.MONTH), ahora.getActualMinimum(Calendar.DAY_OF_MONTH));

			primerdia = formatOUT.format(ahora.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return primerdia;
	}

	/**
	 * Retorna el último día de un mes
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static String getUltimoDia(String fechain, String formatoin, String formatoout) {
		String primerdia = "";
		DateFormat formatOUT = new SimpleDateFormat(formatoout);
		DateFormat formatIN = new SimpleDateFormat(formatoin);

		try {
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(formatIN.parse(fechain));

			ahora.set(ahora.get(Calendar.YEAR), ahora.get(Calendar.MONTH), ahora.getActualMaximum(Calendar.DAY_OF_MONTH));

			primerdia = formatOUT.format(ahora.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return primerdia;
	}

	/**
	 * Agrega un determinado número de dias a una fecha que tiene el siguiente formato yyyyMMdd.
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static Calendar addDays(Calendar date, int days) {
		Calendar newdate = null;
		try {
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(date.getTime());
			ahora.add(Calendar.DAY_OF_YEAR, days);

			newdate = ahora;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newdate;
	}

	/**
	 * Agrega un determinado número de dias a una fecha que tiene el siguiente formato yyyyMMdd.
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static Timestamp addDays(Timestamp date, int days) {
		Timestamp newdate = null;
		try {
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(new Date(date.getTime()));
			ahora.add(Calendar.DAY_OF_YEAR, days);

			newdate = new Timestamp(ahora.getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newdate;
	}

	/**
	 * Agrega un determinado número de dias a una fecha que tiene el siguiente formato yyyyMMdd.
	 * 
	 * @param idperiodo
	 * @param days
	 * @return
	 */
	public static String addDays(String formatoIn, String date, int days) {
		DateFormat formatoFecha1 = new SimpleDateFormat(formatoIn);
		Date formatdate;
		String newdate = "";
		try {
			formatdate = formatoFecha1.parse(date);
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(formatdate);
			ahora.add(Calendar.DAY_OF_YEAR, days);

			newdate = formatoFecha1.format(ahora.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newdate;
	}

	/**
	 * Devuelve la fecha en String dependiendo el formato deseado
	 * 
	 * @author jolleroo
	 * @param sFecha
	 * @param formato
	 * @return
	 */
	public static String stringToString(String sFecha, String formatoIn, String formatoOut) {
		String fecha = "";
		DateFormat formatOUT = new SimpleDateFormat(formatoOut);
		DateFormat formatIN = new SimpleDateFormat(formatoIn);

		try {
			Calendar ahora = Calendar.getInstance();
			ahora.setTime(formatIN.parse(sFecha));
			fecha = formatOUT.format(ahora.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	}

	public static String timestampToString(Timestamp fecha, String formatoout) throws ParseException {
		String fechaout = "";
		if (fecha != null) {
			DateFormat formatOUT = new SimpleDateFormat(formatoout);
			fechaout = formatOUT.format(fecha.getTime());
		}
		return fechaout;
	}
	
	public static String dateToString(Date fecha, String formatoout) throws ParseException {
		String fechaout = "";
		if (fecha != null) {
			DateFormat formatOUT = new SimpleDateFormat(formatoout);
			fechaout = formatOUT.format(fecha.getTime());
		}
		return fechaout;
	}

	public static GregorianCalendar timestampToGregorianCalendar(Timestamp fecha) {
		GregorianCalendar date = null;
		if (fecha != null) {
			date = new GregorianCalendar();
			date.setTimeInMillis(fecha.getTime());
		}
		return date;
	}

	public static String doubleToString(Double num, String pattern, DecimalFormatSymbols simbolos) throws ParseException {
		String numout = "";
		if (num != null) {
			if (!Double.isNaN(num)) {
				NumberFormat formatter = null;
				if (null != simbolos) {
					formatter = new DecimalFormat(pattern, simbolos);
				} else {
					formatter = new DecimalFormat(pattern);
				}
				numout = formatter.format(num);
			}

		}

		return numout;
	}

	public static Timestamp stringToTimestamp(String sFecha, String formato) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Timestamp tsmp = null;
		if (sFecha != null && !sFecha.equals("")){
			tsmp = new Timestamp(sdf.parse(sFecha).getTime());
		}
		

		return tsmp;
	}

	public static Date stringToDate(String sFecha, String formato) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date date = null;
		if (sFecha != null && !sFecha.equals("")) {
			date = sdf.parse(sFecha);
		}
		return date;
	}

	public static java.sql.Date stringToDateSql(String sFecha, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date date = null;
		java.sql.Date datesql = null;
		try {
			if (sFecha != null)
				date = sdf.parse(sFecha);
			datesql = new java.sql.Date(date.getTime());

		} catch (ParseException ex) {
			System.out.println("Error al Pasar de tipo String a tipo Fecha" + ex.getMessage());
		}

		return datesql;
	}

	public static Calendar stringToCalendar(String sFecha, String formato) {

		Calendar fecha = Calendar.getInstance();

		try {
			DateFormat formatter = new SimpleDateFormat(formato);
			Date date = (Date) formatter.parse(sFecha);
			fecha.setTime(date);

		} catch (ParseException e) {
			System.out.println("Exception :" + e);
		}
		return fecha;
	}

	public static String calendarToString(Calendar calendar, String formato, Locale local) {

		String fecha = null;
		try {
			SimpleDateFormat fmto = new SimpleDateFormat(formato, local);
			fecha = fmto.format(calendar.getTime());
		} catch (Exception e) {
			System.out.println("Error al Pasar de Calendar a tipo String" + e.getMessage());
		}

		return fecha;
	}

	public static Timestamp calendarToTimeStamp(Calendar calendar) {
		Timestamp tsmp = null;
		try {
			if (calendar != null)
				tsmp = new Timestamp(calendar.getTime().getTime());

		} catch (Exception ex) {
			System.out.println("Error al convertir de tipo Calendar a tipo TimeStamp" + ex.getMessage());
		}

		return tsmp;
	}

	public static Timestamp dateToTimeStamp(Date date) {
		Timestamp tsmp = null;
		try {
			if (date != null)
				tsmp = new Timestamp(date.getTime());

		} catch (Exception ex) {
			System.out.println("Error al convertir de tipo Date a tipo TimeStamp" + ex.getMessage());
		}

		return tsmp;
	}

	public static String getNombreCompletoCapitaLeter(String nombre, String apellidoPat, String apellidoMat) {
		String capital = null;
		try {
			capital = nombre.trim().concat(" ").concat(apellidoPat.trim()).concat(" ".concat(apellidoMat.trim()));
			String[] words = capital.split("\\s");
			capital = "";
			for (String s : words) {
				capital = capital.concat(capitalize(s));
			}
		} catch (Exception e) {
			capital = "Nombre invalido";
		}

		return capital;
	}

	public static String borrarCerosDeLaIzquierda(String text) {
		while (text.startsWith("0")) {
			text = text.substring(1, text.length());
		}
		return text;
	}

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ";
	}

	/**
	 * The method copyFiles being defined
	 */
	public static boolean copyFiles(String ruta_origen, String ruta_destino) throws IOException {

		File origen = new File(ruta_origen);
		File destino = new File(ruta_destino);

		if (!destino.exists()) {
			logger.info("destino no existe: ES CREADO");
			InputStream in = new FileInputStream(origen);
			OutputStream out = new FileOutputStream(destino);

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) >= 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			in.close();

			return true;
		} else {
			logger.info("destino existe: NO SE CREO");
			return false;
		}
	}

	/**
	 * The method copyFiles being defined
	 */
	public static boolean createDirectory(String ruta) {
		boolean resultado = false;
		System.out.println("create:" + ruta + ":");

		File origen = new File(ruta);

		if (!origen.exists()) {
			// if (origen.isDirectory()) {
			resultado = origen.mkdirs();
			System.out.println("creado:" + ruta + ":");

			// } else {
			// System.out.println(":" + ruta + ": no es directorio");
			// }
		} else {
			System.out.println(":" + ruta + ": ya existe el directorio");
		}
		return resultado;
	}

	/**
	 * 
	 * <p>
	 * Obtiene de un ResultSet el valor contenido en la columna recibida como parametro
	 * </p>
	 * 
	 * @author Cristian Nieto - Sistemcobro S.A
	 * @return
	 */
	public static String getValueStringByColumnName(ResultSet rs, String columnName) {
		String valor = "";
		logger.debug(" Obteniendo el valor de la columna " + columnName);

		try {
			valor = rs.getString(columnName);
		} catch (SQLException e) {
			logger.error(" No se encontro una columna con el nombre " + columnName);
			valor = "";
		}
		logger.debug(" Se obtuvo el valor de la columna " + columnName + " : " + valor);
		return valor;
	}

	/**
	 * 
	 * <p>
	 * Obtiene de un ResultSet el valor contenido en la columna recibida como parametro
	 * </p>
	 * 
	 * @author Cristian Nieto - Sistemcobro S.A
	 * @return
	 */
	public static Integer getValueIntegerByColumnName(ResultSet rs, String columnName) {
		Integer valor = 0;
		logger.debug(" Obteniendo el valor de la columna " + columnName);

		try {
			valor = rs.getInt(columnName);
		} catch (SQLException e) {
			logger.debug(" No se encontro una columna con el nombre " + columnName);
			valor = 0;
		}
		logger.debug(" Se obtuvo el valor de la columna " + columnName + " : " + valor);
		return valor;
	}

	/**
	 * 
	 * <p>
	 * Obtiene de un ResultSet el valor contenido en la columna recibida como parametro
	 * </p>
	 * 
	 * @author Cristian Nieto - Sistemcobro S.A
	 * @return
	 */
	public static Long getValueLongByColumnName(ResultSet rs, String columnName) {
		Long valor = new Long(0);
		logger.debug(" Obteniendo el valor de la columna " + columnName);

		try {
			valor = rs.getLong(columnName);
		} catch (SQLException e) {
			logger.error(" No se encontro una columna con el nombre " + columnName);
			valor = new Long(0);
		}
		logger.debug(" Se obtuvo el valor de la columna " + columnName + " : " + valor);
		return valor;
	}

	/**
	 * 
	 * <p>
	 * Obtiene de un ResultSet el valor contenido en la columna recibida como parametro
	 * </p>
	 * 
	 * @author Cristian Nieto - Sistemcobro S.A
	 * @return
	 */
	public static Timestamp getValueTimestampByColumnName(ResultSet rs, String columnName) {
		Timestamp valor = null;
		logger.debug(" Obteniendo el valor de la columna " + columnName);

		try {
			valor = rs.getTimestamp(columnName);
		} catch (SQLException e) {
			logger.error(" No se encontro una columna con el nombre " + columnName);
			valor = null;
		}
		logger.debug(" Se obtuvo el valor de la columna " + columnName + " : " + valor);
		return valor;
	}

	/**
	 * 
	 * <p>
	 * Obtiene de un ResultSet el valor contenido en la columna recibida como parametro
	 * </p>
	 * 
	 * @author Cristian Nieto - Sistemcobro S.A
	 * @return
	 */
	public static Double getValueDoubleByColumnName(ResultSet rs, String columnName) {
		Double valor = new Double(0);
		logger.debug(" Obteniendo el valor de la columna " + columnName);

		try {
			valor = rs.getDouble(columnName);
		} catch (SQLException e) {
			logger.error(" No se encontro una columna con el nombre " + columnName);
			valor = new Double(0);
		}
		logger.debug(" Se obtuvo el valor de la columna " + columnName + " : " + valor);
		return valor;
	}

	public static String generarNombreDeArchivo(String nombreProducto) {

		logger.info(" Nombre del producto " + nombreProducto);

		Calendar calendarArchivo = new GregorianCalendar();
		calendarArchivo.setTime(new Date());
		String mesString = "";
		int mesInt = calendarArchivo.get(Calendar.MONTH);

		switch (mesInt) {
		case 0:
			mesString = "en";

			break;
		case 1:
			mesString = "fb";

			break;
		case 2:
			mesString = "mr";

			break;
		case 3:
			mesString = "ab";

			break;
		case 4:
			mesString = "my";

			break;
		case 5:
			mesString = "jn";

			break;
		case 6:
			mesString = "jl";

			break;
		case 7:
			mesString = "ag";

			break;
		case 8:
			mesString = "sp";

			break;
		case 9:
			mesString = "oc";

			break;
		case 10:
			mesString = "nv";

			break;
		case 11:
			mesString = "de";

			break;

		default:
			mesString = "";
			break;
		}

		int diaInt = calendarArchivo.get(Calendar.DAY_OF_MONTH);

		String diaString = "";

		if (diaInt < 10) {
			diaString = "0" + diaInt;
		} else {
			diaString = "" + diaInt;
		}

		String firstPart = "Gestion_SISTEMCOBRO_";

		nombreProducto = nombreProducto.toUpperCase().replaceAll(" ", "_");

		String filename = firstPart + nombreProducto + "_" + diaString + mesString + ".xlsx";

		logger.info("File name " + filename);
		return filename;
	}

	public static Integer contarCaracteresNumero(String cadena) {
		Integer cantidad = 0;

		if (null != cadena) {
			for (int a = 0; a < cadena.length(); a++) {
				if (cadena.substring(a, a + 1).matches("[0-9]*")) {
					cantidad++;
				}
			}
		}
		return cantidad;
	}

	public static Integer contarCaracteresAlfabeticosMayuscula(String cadena) {
		Integer cantidad = 0;

		if (null != cadena) {
			for (int a = 0; a < cadena.length(); a++) {
				if (cadena.substring(a, a + 1).matches("[A-Z]*")) {
					cantidad++;
				}
			}
		}
		return cantidad;
	}

	public static Integer contarCaracteresAlfabeticosMinuscula(String cadena) {
		Integer cantidad = 0;

		if (null != cadena) {
			for (int a = 0; a < cadena.length(); a++) {
				if (cadena.substring(a, a + 1).matches("[a-z]*")) {
					cantidad++;
				}
			}
		}
		return cantidad;
	}

	public static Integer contarCaracteresEspeciales(String cadena) {
		Integer cantidad = 0;

		if (null != cadena) {
			for (int a = 0; a < cadena.length(); a++) {
				if (!cadena.substring(a, a + 1).matches("[A-Za-z0-9]*")) {
					cantidad++;
				}
			}
		}
		return cantidad;
	}
	

	@SuppressWarnings({ "unused", "deprecation" })
	public static List<HashMap<String, Object>> diferenciaEnDiasconFrecuencia(GregorianCalendar datemin, GregorianCalendar datemax,List<Integer> diadelasemana,Integer frecuencia ) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
		GregorianCalendar datedelanio=new GregorianCalendar();
		GregorianCalendar fecha=new GregorianCalendar();
		GregorianCalendar fechafin=new GregorianCalendar();
		if (datemin.get(Calendar.YEAR) == datemax.get(Calendar.YEAR)) {
			int rango = (datemax.get(Calendar.DAY_OF_YEAR) - datemin.get(Calendar.DAY_OF_YEAR));
			
			int diasemanamin = datemin.get(Calendar.DAY_OF_WEEK);
			int diasemanamax = datemax.get(Calendar.DAY_OF_WEEK);
			int numerodias = 0;
			int diadelanio= datemin.get(Calendar.DAY_OF_YEAR);
			datedelanio.setTime(datemin.getTime());
			
			
			for(int i=0;i<=rango;i++){
				int numerodia = datedelanio.get(Calendar.DAY_OF_WEEK);
			 
				for(Integer d:diadelasemana){
					if(numerodia==d){
						fecha=new GregorianCalendar();
						fechafin=new GregorianCalendar();
						data = new HashMap<String, Object>();
						numerodias=numerodias+1;
						
						 Date fechatemp = datedelanio.getTime();
						 fecha.setTime(fechatemp);
						 data.put("dia", numerodias);
						 data.put("fecha", fecha);
						
						 Date fechaeventosalida;
						if( frecuencia>=11){//si es nocturno
								
								long m = datemax.getTime().getTime();
								
								Time m1 = new Time(m);
								
								Date temp = fecha.getTime();
								temp.setDate(fecha.getTime().getDate()+1);
								temp.setHours(m1.getHours());
								temp.setMinutes(m1.getMinutes());
								temp.setSeconds(m1.getSeconds());
								
								 fechaeventosalida =temp;
							
							}else{
								 fechaeventosalida =fecha.getTime();
								 fechaeventosalida.setHours(datemax.getTime().getHours());
								 fechaeventosalida.setMinutes( datemax.getTime().getMinutes());
								 fechaeventosalida.setSeconds( datemax.getTime().getSeconds());
								   
							}
						fechafin.setTime(fechaeventosalida);
						 data.put("fechafin", fechafin);
						 datas.add(data);
					}
				
				}
				
				 datedelanio.add(Calendar.DAY_OF_YEAR, 1);
				
			}
			
			return datas;
		} else {
			/*
			 * SI ESTAMOS EN DISTINTO ANYO COMPROBAMOS QUE EL ANYO DEL DATEINI NO SEA BISIESTO SI ES BISIESTO SON 366 DIAS EL ANYO SINO SON 365
			 */
			int diasAnyo = datemin.isLeapYear(datemin.get(Calendar.YEAR)) ? 366 : 365;

			/* CALCULAMOS EL RANGO DE ANYOS */
			int rangoAnyos = datemax.get(Calendar.YEAR) - datemin.get(Calendar.YEAR);

			/* CALCULAMOS EL RANGO DE DIAS QUE HAY */
			int rango = (rangoAnyos * diasAnyo) + (datemax.get(Calendar.DAY_OF_YEAR) - datemin.get(Calendar.DAY_OF_YEAR));

			int diasemanamin = datemin.get(Calendar.DAY_OF_WEEK);
			int diasemanamax = datemax.get(Calendar.DAY_OF_WEEK);
			int numerodias = 0;
			int diadelanio= datemin.get(Calendar.DAY_OF_YEAR);
			datedelanio.setTime(datemin.getTime());
			
			
			for(int i=0;i<=rango;i++){
				int numerodia = datedelanio.get(Calendar.DAY_OF_WEEK);
			 
				for(Integer d:diadelasemana){
					if(numerodia==d){
						fecha=new GregorianCalendar();
						fechafin=new GregorianCalendar();
						data = new HashMap<String, Object>();
						numerodias=numerodias+1;
						
						 Date fechatemp = datedelanio.getTime();
						 fecha.setTime(fechatemp);
						 data.put("dia", numerodias);
						 data.put("fecha", fecha);
						
						 Date fechaeventosalida;
						if( frecuencia>=11){//si es nocturno
								
								long m = datemax.getTime().getTime();
								
								Time m1 = new Time(m);
								
								Date temp = fecha.getTime();
								temp.setDate(fecha.getTime().getDate()+1);
								temp.setHours(m1.getHours());
								temp.setMinutes(m1.getMinutes());
								temp.setSeconds(m1.getSeconds());
								
								 fechaeventosalida =temp;
							
							}else{
								 fechaeventosalida =fecha.getTime();
								 fechaeventosalida.setHours(datemax.getTime().getHours());
								 fechaeventosalida.setMinutes( datemax.getTime().getMinutes());
								 fechaeventosalida.setSeconds( datemax.getTime().getSeconds());
								   
							}
						fechafin.setTime(fechaeventosalida);
						 data.put("fechafin", fechafin);
						 datas.add(data);
					}
				
				}
				
				 datedelanio.add(Calendar.DAY_OF_YEAR, 1);
				
			}
			
			
			
			return datas;
		}
	}
	
	public static String MayusculasPrimerasletras(String givenString) {
	    String[] arr = givenString.split(" ");
	    StringBuffer sb = new StringBuffer();
	   // String a="jenny rocio   rodriguez rojas";
	   // String[] ar = a.split(" ");
	    for (int i = 0; i < arr.length; i++) {
	    	if(arr[i].equals("") || arr[i].equals(" ")){
	    		
	    	}else{
	    		 sb.append(Character.toUpperCase(arr[i].charAt(0)))
		            .append(arr[i].substring(1)).append(" ");
	    	}
	       
	    }          
	    return sb.toString().trim();
	}  
	
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    /**
     * Validate given email with regular expression.
     * 
     * @param email
     *            email for validation
     * @return true valid email, otherwise false
     */
    public static boolean validateEmail(String email) {
 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
    
    
  
}
