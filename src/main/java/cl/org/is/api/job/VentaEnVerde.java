/**
 *@name Consulta.java
 * 
 *@version 1.0 
 * 
 *@date 30-03-2017
 * 
 *@author EA7129
 * 
 *@copyright Cencosud. All rights reserved.
 */
package cl.org.is.api.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @description 
 */
public class VentaEnVerde {
	
	private static final int DIFF_HOY_FECHA_INI = 0;
	private static final int DIFF_HOY_FECHA_FIN = 0;
	//private static final int FORMATO_FECHA_0 = 0;
	//private static final int FORMATO_FECHA_1 = 1;
	//private static final int FORMATO_FECHA_3 = 3;
	private static final String RUTA_ENVIO = "C:/Share/Inbound/VentaEnVerde";

	private static BufferedWriter bw;
	private static String path;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map <String, String> mapArguments = new HashMap<String, String>();
		String sKeyAux = null;

		for (int i = 0; i < args.length; i++) {

			if (i % 2 == 0) {

				sKeyAux = args[i];
			}
			else {

				mapArguments.put(sKeyAux, args[i]);
			}
		}

		try {
			
			

			File info              = null;
			File miDir             = new File(".");
			path                   =  miDir.getCanonicalPath();
			info                   = new File(path+"/info.txt");
			bw = new BufferedWriter(new FileWriter(info));
			info("El programa se esta ejecutando...");
			crearTxt(mapArguments);
			System.out.println("El programa finalizo.");
			info("El programa finalizo.");
			bw.close();
		}
		catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	private static void crearTxt(Map<String, String> mapArguments) {
		// TODO Auto-generated method stub
		Connection dbconnOracle = crearConexionOracle();
		File file1              = null;
		BufferedWriter bw       = null;
		PreparedStatement pstmt = null;
		StringBuffer sb         = null;
		String sFechaIni        = null;
		String sFechaFin        = null;
		String iFechaIni2           							= null;
		String iFechaFin2          								= null;
		
		Date now2 = new Date();
		SimpleDateFormat ft2 = new SimpleDateFormat ("dd/MM/YY hh:mm:ss");
		String currentDate2 = ft2.format(now2);
		info("Inicio Programa: " + currentDate2 + "\n");
		
		
		

		try {

			try {

				sFechaIni = restarDias(mapArguments.get("-f"), DIFF_HOY_FECHA_INI);
				sFechaFin = restarDias(mapArguments.get("-f"), DIFF_HOY_FECHA_FIN);
				
				iFechaIni2 = restarDias2(mapArguments.get("-f"), 1);
				iFechaFin2 = restarDias2(mapArguments.get("-f"), 0);
				
				//sFechaIni = "29-03-2017";
				//sFechaFin = "29-03-2017";
				info("sFechaIni: " + sFechaIni + "\n");
				info("sFechaFin: " + sFechaFin + "\n");
				
				info("sFechaIni: " + iFechaIni2 + "\n");
				info("sFechaFin: " + iFechaIni2 + "\n");
			}
			catch (Exception e) {

				info("error: " + e);
			}
			//file1                   = new File(path + "/" + sFechaIni + "_" + sFechaFin + ".txt");
			file1                   = new File(RUTA_ENVIO + "/" + sFechaIni + "_" + sFechaFin + ".txt");
			sb = new StringBuffer();
			
			
			sb.append("SELECT SOLICITUDORIGINAL,N_ORDEN_DISTRIBU,FECHA_CREACION_ORDEN,PONUMB,EX14ERROR,DESPA,TIPO_ESTADO,TIPO_RELACION,RELNUM,INUMBR,RELBL5,LOGENCONTRADOENJDA ");
			
			//sb.append(",'NO' as venta_empresa ");
			sb.append("FROM CUADRATURA_VENTA_VERDE ");
			sb.append("WHERE 1 = 1 ");
			sb.append(" AND FECHA_CREACION_ORDEN >= '"+iFechaIni2+" 00:00:00'  and FECHA_CREACION_ORDEN <= '"+iFechaFin2+" 23:59:59' ");
			
			sb.append(" ");
			
			
			info("Query : " + sb + "\n");
			
			pstmt = dbconnOracle.prepareStatement(sb.toString());
			ResultSet rs = pstmt.executeQuery();
			bw = new BufferedWriter(new FileWriter(file1));
			//bw.write("ID;");
			bw.write("SOLICITUDORIGINAL;");
			bw.write("N_ORDEN_DISTRIBU;");
			bw.write("FECHA_CREACION_ORDEN;");
			bw.write("PONUMB;");
			bw.write("EX14ERROR;");
			bw.write("DESPA;");
			bw.write("TIPO_ESTADO;");
			bw.write("TIPO_RELACION;");
			bw.write("RELNUM;");
			bw.write("INUMBR;");
			bw.write("RELBL5;");
			bw.write("LOGENCONTRADOENJDA\n");
			//bw.write("VENTA_EMPRESA\n");
			sb = new StringBuffer();
			
			while (rs.next()){

				//bw.write(rs.getString("ID") + ";");
				bw.write(rs.getString("LOGENCONTRADOENJDA") + ";");
				bw.write(rs.getString("N_ORDEN_DISTRIBU")  + ";");
				bw.write(rs.getString("FECHA_CREACION_ORDEN")  + ";");
				bw.write(rs.getString("PONUMB")  + ";");
				bw.write(rs.getString("EX14ERROR")  + ";");
				bw.write(rs.getString("DESPA")  + ";");
				bw.write(rs.getString("TIPO_ESTADO")  + ";");
				bw.write(rs.getString("TIPO_RELACION")  + ";");
				bw.write(rs.getString("RELNUM")  + ";");
				bw.write(rs.getString("INUMBR")  + ";");
				bw.write(rs.getString("RELBL5")  + ";");
				bw.write(rs.getString("LOGENCONTRADOENJDA") + "\n");
				//bw.write(rs.getString("venta_empresa") + "\n");
			}
			bw.write(sb.toString());
			info("Archivos creados." + "\n");
		}
		catch (Exception e) {

			info("[crearTxt1]Exception:"+e.getMessage());
		}
		finally {

			cerrarTodo(dbconnOracle, pstmt, bw);
		}
		info("Fin Programa: " + currentDate2 + "\n");
	}
	
	/**
	 * Metodo que resta dias 
	 * 
	 * @param String, dia que se resta
	 * @param String, cantidad para restar dias
	 * @return String retorna los dias a restar
	 * 
	 */
	private static String restarDias2(String sDia, int iCantDias) {

		String sFormatoIn = "yyyyMMdd";
		String sFormatoOut = "yyyy-MM-dd";
		Calendar diaAux = null;
		String sDiaAux = null;
		SimpleDateFormat df = null;

		try {

			diaAux = Calendar.getInstance();
			df = new SimpleDateFormat(sFormatoIn);
			diaAux.setTime(df.parse(sDia));
			diaAux.add(Calendar.DAY_OF_MONTH, -iCantDias);
			df.applyPattern(sFormatoOut);
			sDiaAux = df.format(diaAux.getTime());
		}
		catch (Exception e) {

			info("[restarDias]error: " + e);
		}
		return sDiaAux;
	}

	/**
	 * Metodo de conexion para MEOMCLP 
	 * 
	 * @return void,  no tiene valor de retorno
	 */
	private static Connection crearConexionOracle() {

		Connection dbconnection = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Shareplex
			//dbconnection = DriverManager.getConnection("jdbc:oracle:thin:@g500603svcr9.cencosud.corp:1521:MEOMCLP","REPORTER","RptCyber2015");
			//El servidor g500603sv0zt corresponde a ProducciÃ³n. por el momento
			//dbconnection = DriverManager.getConnection("jdbc:oracle:thin:@g500603sv0zt.cencosud.corp:1521:MEOMCLP","ca14","Manhattan1234");
			dbconnection = DriverManager.getConnection("jdbc:oracle:thin:@172.18.163.15:1521/XE", "kpiweb", "kpiweb");
		}
		catch (Exception e) {

			info("[crearConexionOracle]error: " + e);
		}
		return dbconnection;
	}



	/**
	 * Metodo que cierra la conexion, Procedimintos,  BufferedWriter
	 * 
	 * @param Connection,  Objeto que representa una conexion a la base de datos
	 * @param PreparedStatement, Objeto que representa una instrucción SQL precompilada. 
	 * @return retorna
	 * 
	 */
	private static void cerrarTodo(Connection cnn, PreparedStatement pstmt, BufferedWriter bw){

		try {

			if (cnn != null) {

				cnn.close();
				cnn = null;
			}
		}
		catch (Exception e) {

			info("[cerrarTodo]Exception:"+e.getMessage());
		}
		try {

			if (pstmt != null) {

				pstmt.close();
				pstmt = null;
			}
		}
		catch (Exception e) {

			info("[cerrarTodo]Exception:"+e.getMessage());
		}
		try {

			if (bw != null) {

				bw.flush();
				bw.close();
				bw = null;
			}
		}
		catch (Exception e) {

			info("[cerrarTodo]Exception:"+e.getMessage());
		}
	}


	/**
	 * Metodo que muestra informacion 
	 * 
	 * @param String, texto a mostra
	 * @param String, cantidad para restar dias
	 * @return String retorna los dias a restar
	 * 
	 */
	private static void info(String texto){

		try {

			bw.write(texto+"\n");
			bw.flush();
		}
		catch (Exception e) {

			System.out.println("Exception:" + e.getMessage());
		}
	}


	/**
	 * Metodo que resta dias 
	 * 
	 * @param String, dia que se resta
	 * @param String, cantidad para restar dias
	 * @return String retorna los dias a restar
	 * 
	 */
	private static String restarDias(String sDia, int iCantDias) {

		String sFormatoIn = "yyyyMMdd";
		String sFormatoOut = "yyyyMMdd";
		Calendar diaAux = null;
		String sDiaAux = null;
		SimpleDateFormat df = null;

		try {

			diaAux = Calendar.getInstance();
			df = new SimpleDateFormat(sFormatoIn);
			diaAux.setTime(df.parse(sDia));
			diaAux.add(Calendar.DAY_OF_MONTH, -iCantDias);
			df.applyPattern(sFormatoOut);
			sDiaAux = df.format(diaAux.getTime());
		}
		catch (Exception e) {

			info("[restarDias]error: " + e);
		}
		return sDiaAux;
	}
	
	/**
	 * Metodo que formatea una fecha 
	 * 
	 * @param String, fecha a formatear
	 * @param String, formato de fecha
	 * @return String retorna el formato de fecha a un String
	 * 
	 */
	/*
	private static String formatDate(Date fecha, int iOptFormat) {

		String sFormatedDate = null;
		String sFormat = null;

		try {

			SimpleDateFormat df = null;

			switch (iOptFormat) {

			case 0:
				sFormat = "dd/MM/yy HH:mm:ss,SSS";
				break;
			case 1:
				sFormat = "dd/MM/yy";
				break;
			case 2:
				sFormat = "dd/MM/yy HH:mm:ss";
				break;
			case 3:
				sFormat = "yyyy-MM-dd HH:mm:ss,SSS";
				break;
			}
			df = new SimpleDateFormat(sFormat);
			sFormatedDate = df.format(fecha != null ? fecha:new Date(0));

			if (iOptFormat == 0 && sFormatedDate != null) {

				sFormatedDate = sFormatedDate + "000000";
			}
		}
		catch (Exception e) {

			info("[formatDate]Exception:"+e.getMessage());
		}
		return sFormatedDate;
	}
	*/

}
