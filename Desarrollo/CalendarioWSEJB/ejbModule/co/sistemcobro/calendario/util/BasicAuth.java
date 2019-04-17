package co.sistemcobro.calendario.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;

public class BasicAuth {

	public static boolean isUserAuthenticated(String authString) {

		String decodedAuth = "";
		// Header is in the format "Basic 5tyc0uiDat4"
		// We need to extract data before decoding it back to original string
		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[1];
		// Decode the data back to original string
		byte[] bytes = null;
		try {
			bytes = new BASE64Decoder().decodeBuffer(authInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodedAuth = new String(bytes);
		System.out.println(decodedAuth);
		String[] user = decodedAuth.split(":");
		String usuario = user[0];
		String pass = user[1];
		String usuario2 = "sistemcobro";
		String pass2 = "prueba";

		System.out.println(usuario);
		System.out.println(pass);
		if (usuario.equals(usuario2)) {
			System.out.println("Usuarios iguales");
			if (pass.equals(pass2)) {
				System.out.println("claves iguales");
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

}
