package co.sistemcobro.calendario.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class smsInfobip {

	public static String sendSMS(String celular,String mensaje) {
		String output =new String();
		try {
			mensaje=mensaje.replaceAll(" ", "%20");
			URL url = new URL("http://172.16.1.175:81/RestSmsws.svc/jsoneInfo?Telefono="+celular+"&Mensaje="+mensaje+"&usuario=ws_masivos&contrasena=Wsm@s1v0s&remplazar=_");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Fallo servicio de envio SMS");
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return output;

	}

}
