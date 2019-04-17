package co.sistemcobro.calendario.constante;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CodigoSegmentoENUM {
	
	Codigo_Linea_Normal(1), Codigo_Familias(2);
	
	protected static final Map<Integer, CodigoSegmentoENUM> mapByID = new HashMap<>();
	protected static final Map<String, CodigoSegmentoENUM> mapByNAME = new HashMap<>();

	static {
		for (CodigoSegmentoENUM s : EnumSet.allOf(CodigoSegmentoENUM.class)) {
			mapByID.put(s.getIndex(), s);
			mapByNAME.put(s.name(), s);
		}
	}

	private int index;

	CodigoSegmentoENUM(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getIndexString() {
		return String.valueOf(index);
	}

	public static CodigoSegmentoENUM get(int id) {
		return mapByID.get(id);
	}

	public static CodigoSegmentoENUM get(String name) {
		return mapByNAME.get(name);
	}
}
