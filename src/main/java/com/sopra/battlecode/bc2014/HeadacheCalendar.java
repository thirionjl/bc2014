package com.sopra.battlecode.bc2014;

import java.time.Month;
import java.time.Year;

public class HeadacheCalendar implements Comparable<HeadacheCalendar> {

	private static final int MONTH_COUNT = 12;
	private final int day, month, year;

	public HeadacheCalendar(int year, int month, int day) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int daysStrictlyBetween(HeadacheCalendar other) {
		if (compareTo(other) > 0) {
			throw new IllegalArgumentException("Argument must be in the future");
		}
		HeadacheCalendar start = this;
		int daysBetween = 0;
		while (start.compareTo(other) < 0) {
			start = start.next();
			daysBetween++;
		}
		return Math.max(daysBetween - 1, 0);
	}

	private HeadacheCalendar next() {
		int nextDay = day;
		int nextMonth = month;
		int nextYear = year;
		if (day < getDaysInMonth()) {
			nextDay++;
		} else {
			nextDay = 1;
			if (month < MONTH_COUNT) {
				nextMonth++;
			} else {
				nextMonth = 1;
				nextYear++;
			}
		}
		return new HeadacheCalendar(nextYear, nextMonth, nextDay);
	}

	private int getDaysInMonth() {
		int monthDayCount = Month.of(month).length(Year.of(year).isLeap());
		int oddDaysSuppl = monthDayCount % 2 == 0 ? 0 : 3;
		return monthDayCount - 2 * month + oddDaysSuppl;
	}

	@Override
	public int compareTo(HeadacheCalendar o) {
		int yearComp = this.year - o.year;
		int monthComp = this.month - o.month;
		int dayComp = this.day - o.day;

		if (yearComp != 0)
			return yearComp;
		if (monthComp != 0)
			return monthComp;
		return dayComp;
	}

	@Override
	public String toString() {
		return String.format("%4d-%02d-%02d", year, month, day);
	}

	public static void main(String[] args) {
		HeadacheCalendar start = new HeadacheCalendar(2010, 1, 1);
		HeadacheCalendar stop = new HeadacheCalendar(2014, 9, 1);
		System.out.format("There are %d days strictly between %s and %s", start.daysStrictlyBetween(stop), start, stop);
	}

}
