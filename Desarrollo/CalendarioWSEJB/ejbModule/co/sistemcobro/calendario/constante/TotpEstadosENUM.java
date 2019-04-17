package co.sistemcobro.calendario.constante;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TotpEstadosENUM {

	ESTADOTOTP_GENERADO(1), ESTADOTOTP_AUTENTICADO(2), ESTADOTOTP_EXPIRADO(3), ESTADOTOTP_ANULADO(4);

	protected static final Map<Integer, TotpEstadosENUM> mapByID = new HashMap<>();
	protected static final Map<String, TotpEstadosENUM> mapByNAME = new HashMap<>();

	static {
		for (TotpEstadosENUM s : EnumSet.allOf(TotpEstadosENUM.class)) {
			mapByID.put(s.getIndex(), s);
			mapByNAME.put(s.name(), s);
		}
	}

	private int index;

	TotpEstadosENUM(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getIndexString() {
		return String.valueOf(index);
	}

	public static TotpEstadosENUM get(int id) {
		return mapByID.get(id);
	}

	public static TotpEstadosENUM get(String name) {
		return mapByNAME.get(name);
	}

}
