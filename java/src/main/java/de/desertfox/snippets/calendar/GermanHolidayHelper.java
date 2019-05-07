package de.desertfox.snippets.calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GermanHolidayHelper {

	public static final String SCHLESWIG_HOLSTEIN = "SH";

	public static final String BADEN_WÜRTEMBERG = "BW";

	public static final String BAYERN = "BY";

	public static final String BERLIN = "BE";

	public static final String BRANDENBURG = "BB";

	public static final String BREMEN = "HB";

	public static final String HAMBURG = "HH";

	public static final String HESSEN = "HE";

	public static final String MECKLENBURG_VORPOMMERN = "MV";

	public static final String NIEDERSACHSEN = "NI";

	public static final String NORDREHEIN_WESTPFALEN = "NW";

	public static final String RHEINLAND_PFALZ = "RP";

	public static final String SAARLAND = "SL";

	public static final String SACHSEN = "SN";

	public static final String SACHSEN_ANHALT = "ST";

	public static final String THUERINGEN = "TH";

	private static final String REQUEST = "https://feiertage-api.de/api/?jahr=%d&nur_land=%s";

	private static Pattern datePattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

	public static List<Integer> byMonthOfYearAndState(int month, int year, String state) throws MalformedURLException, IOException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(String.format(REQUEST, year, state)).openStream()));
		StringBuilder builder = new StringBuilder();
		reader.lines().forEach(l -> builder.append(l));
		String response = builder.toString();
		Matcher matcher = datePattern.matcher(response);
		List<Integer> dates = new ArrayList<>();
		while (matcher.find()) {
			Calendar cal = Calendar.getInstance(Locale.GERMANY);
			cal.setTime(dateFormat.parse(matcher.group(0)));
			if (cal.get(Calendar.MONTH) == month) {
				dates.add(cal.get(Calendar.DAY_OF_MONTH));
			}
		}
		return dates;
	}

	public static List<Calendar> byYearAndState(int year, String state) throws MalformedURLException, IOException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(String.format(REQUEST, year, state)).openStream()));
		StringBuilder builder = new StringBuilder();
		reader.lines().forEach(l -> builder.append(l));
		String response = builder.toString();
		Matcher matcher = datePattern.matcher(response);
		List<Calendar> dates = new ArrayList<>(matcher.groupCount());
		while (matcher.find()) {
			Calendar cal = Calendar.getInstance(Locale.GERMANY);
			cal.setTime(dateFormat.parse(matcher.group(0)));
			dates.add(cal);
		}
		return dates;
	}
}
