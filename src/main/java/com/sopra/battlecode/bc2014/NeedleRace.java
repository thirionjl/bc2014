package com.sopra.battlecode.bc2014;

import static com.codepoetics.protonpack.StreamUtils.takeUntil;

import java.time.LocalTime;
import java.util.stream.Stream;

public class NeedleRace {

	static boolean areNeedleSuperimposed(LocalTime time) {
		return time.getMinute() == time.getHour() % 12 * 5;
	}

	public static void main(String[] args) {
		LocalTime start = LocalTime.of(00, 01);
		LocalTime stop = LocalTime.of(22, 24);

		Stream<LocalTime> timeline = Stream.iterate(start, time -> time.plusMinutes(1));
		long crossings = takeUntil(timeline, time -> time.equals(stop)).filter(NeedleRace::areNeedleSuperimposed).count();

		System.out.format("There are %d crossings between %s and %s", crossings, start, stop);
	}
}
