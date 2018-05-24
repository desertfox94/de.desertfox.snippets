package de.desertfox.snippets.CalendarTool;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkTimeCalculator {

	public static void main(String[] args) throws Exception {
		Map<Integer, Integer> daysOfWeekToHours = new HashMap<Integer, Integer>();
		daysOfWeekToHours.put(Calendar.THURSDAY, 6);
		daysOfWeekToHours.put(Calendar.FRIDAY, 7);
		for (int i = 4; i < 7; i++) {
			System.out.println(sumHoursOfMonth(i, 2018, daysOfWeekToHours));
		}
	}

	private static int sumHoursOfMonth(int month, int year, Map<Integer, Integer> daysOfWeekToHours) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		List<Integer> holidays = GermanHolidayHelper.byMonthOfYearAndState(month, year, GermanHolidayHelper.SCHLESWIG_HOLSTEIN);
		int sum = 0;
		while (calendar.get(Calendar.MONTH) == month) {
			if (!holidays.contains(calendar.get(Calendar.DAY_OF_MONTH))) {
				Integer workingHours = daysOfWeekToHours.get(calendar.get(Calendar.DAY_OF_WEEK));
				if (workingHours != null) {
					sum += workingHours;
				}
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return sum;
	}

}
