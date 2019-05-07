package de.desertfox.snippets.holidayapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Holiday {

	private Calendar cal;

	private String name;

	private String hint;

	public Date getDate() {
		return cal.getTime();
	}

	public Calendar getCalendar() {
		return cal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(getDate()) + " " + name + (hint != null && !hint.isEmpty() ? " (" + hint + ")" : "");
	}

	public void setCalendar(Calendar cal) {
		this.cal = cal;
	}

}
