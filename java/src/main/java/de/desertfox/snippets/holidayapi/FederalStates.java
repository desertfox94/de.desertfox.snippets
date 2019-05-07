package de.desertfox.snippets.holidayapi;

public enum FederalStates {
	SCHLESWIG_HOLSTEIN("SH"),

	BADEN_WÜRTEMBERG("BW"),

	BAYERN("BY"),

	BERLIN("BE"),

	BRANDENBURG("BB"),

	BREMEN("HB"),

	HAMBURG("HH"),

	HESSEN("HE"),

	MECKLENBURG_VORPOMMERN("MV"),

	NIEDERSACHSEN("NI"),

	NORDREHEIN_WESTPFALEN("NW"),

	RHEINLAND_PFALZ("RP"),

	SAARLAND("SL"),

	SACHSEN("SN"),

	SACHSEN_ANHALT("ST"),

	THUERINGEN("TH");

	public final String ident;

	private FederalStates(String ident) {
		this.ident = ident;
	}

}
