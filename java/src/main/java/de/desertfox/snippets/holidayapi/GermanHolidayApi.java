package de.desertfox.snippets.holidayapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GermanHolidayApi {

	private static final String REQUEST = "http://feiertage-api.de/api/?jahr=%d&nur_land=%s";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

	private static Predicate<Holiday> all = holiday -> true;

	private static Function<Integer, Predicate<Holiday>> createMonthFilter = month -> {
		return holiday -> {
			Calendar cal = Calendar.getInstance();
			cal.setTime(holiday.getDate());
			return cal.get(Calendar.MONTH) == month;
		};
	};

	public static List<Date> getDatesByYearAndMonth(int year, int month, FederalStates state) throws MalformedURLException, IOException, ParseException {
		return getDates(year, state, createMonthFilter.apply(month));
	}

	public static List<Date> getDatesByYear(int year, FederalStates state) throws MalformedURLException, IOException, ParseException {
		return getDates(year, state, all);
	}

	public static List<Date> getDates(int year, FederalStates state, Predicate<Holiday> filter) throws MalformedURLException, IOException, ParseException {
		return getFiltered(year, state, filter).stream().map(holiday -> holiday.getDate()).collect(Collectors.toList());
	}

	public static List<Holiday> getByYear(int year, FederalStates state) throws MalformedURLException, IOException, ParseException {
		return get(year, state);
	}

	public static List<Holiday> getByYear(int year, FederalStates state, Predicate<Holiday> filter) throws MalformedURLException, IOException, ParseException {
		return getFiltered(year, state, filter);
	}

	public static List<Holiday> getByYearAndMonth(int year, int month, FederalStates state) throws MalformedURLException, IOException, ParseException {
		return getFiltered(year, state, createMonthFilter.apply(month));
	}

	public static List<Holiday> get(int year, FederalStates state) throws MalformedURLException, IOException, ParseException {
		return requestHolidays(year, state.ident);
	}

	public static List<Holiday> getFiltered(int year, FederalStates state, Predicate<Holiday> filter) throws MalformedURLException, IOException, ParseException {
		return get(year, state).stream().filter(filter).collect(Collectors.toList());
	}

	private static List<Holiday> requestHolidays(int year, String state) throws IOException, MalformedURLException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(String.format(REQUEST, year, state)).openStream()));
		StringBuilder builder = new StringBuilder();
		reader.lines().forEach(l -> builder.append(l));
		Type mapType = new TypeToken<Map<String, Map<String, String>>>() {
		}.getType();
		Map<String, Map<String, String>> response = new Gson().fromJson(builder.toString(), mapType);
		List<Holiday> holidays = new ArrayList<>(response.size());
		for (Entry<String, Map<String, String>> entry : response.entrySet()) {
			Holiday holiday = new Holiday();
			holiday.setName(entry.getKey());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(entry.getValue().get("datum")));
			holiday.setCalendar(cal);
			holiday.setHint(entry.getValue().get("hinweis"));
			holidays.add(holiday);
		}
		return holidays;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("All Holidays of Berlin:");
		GermanHolidayApi.getByYear(2018, FederalStates.BERLIN).forEach(System.out::println);

		System.out.println("\nAll Holidays of Bayern in May:");
		GermanHolidayApi.getByYearAndMonth(2019, Calendar.MAY, FederalStates.BAYERN).forEach(System.out::println);

		System.out.println("\nAll Holidays of Hamburg with custom filter:");
		Predicate<Holiday> mondayAndFridayOnly = holiday -> {
			int day = holiday.getCalendar().get(Calendar.DAY_OF_WEEK);
			return day == Calendar.MONDAY || day == Calendar.FRIDAY;
		};
		GermanHolidayApi.getByYear(2019, FederalStates.HAMBURG, mondayAndFridayOnly).forEach(System.out::println);
	}

}
