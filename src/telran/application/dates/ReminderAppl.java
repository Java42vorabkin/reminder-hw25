package telran.application.dates;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ReminderAppl {

	private static Instant instantStart;
	private static Instant instantFinish;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//args[0] - mandatory interval of beeps 
		//args[1] - mandatory ChronoUnit (according to time units of ChronoUnit)
		//args[2] - optional in ChronoUnit values when to end up beeps (default) 1 hour
		//args[3] - optional in ChronoUnit values when to start beeping (default immediately )
		//beep - System.out.println("\007")
		try {
			int beepInterval = getBeepInterval(args);
			ChronoUnit chronoUnit = getChronoUnit(args);
			System.out.println("interval="+beepInterval+"  unit="+chronoUnit.toString());
			int values[] = getOptionalParams(args);
			System.out.println("end="+values[0]+"   start="+values[1]);
			updateInstants(values, chronoUnit);
			beeper(beepInterval, chronoUnit);
		} catch (RuntimeException e) {
			e.printStackTrace();                  //functionality error
		} catch(Exception ex) {
			System.out.println(ex.getMessage());  //wrong input data 
		}

	}
	private static void updateInstants(int values[], ChronoUnit chronoUnit) {
		Instant instantCurrent = Instant.now();
		instantStart = instantCurrent.plus(values[1], chronoUnit);
		if(values[0] < 0) {
			instantFinish = instantStart.plus(1, ChronoUnit.HOURS); 
		} else {
			instantFinish = instantStart.plus(values[0], chronoUnit);
		}
	}
	private static int[] getOptionalParams(String[] args) throws Exception {
		int res[] = new int[2];
		if(args.length<3) {
			res[0] = -1;
			res[1] = 0;
			return res;
		}
		try {
			res[0] = Integer.parseInt(args[2]);
			if(res[0] <= 0) {
				throw new Exception("value to end up beeps has to be positive. Not "+args[2]);
			}
		} catch(NumberFormatException ex) {
			throw new Exception("value to end up beeps has to be number. Not "+args[2]);
		}
		if(args.length == 3) {
			res[1] = 0;
			return res;
		}
		try {
			res[1] = Integer.parseInt(args[3]);
			if(res[1] <= 0) {
				throw new Exception("value to start beeping has to be positive. Not "+args[3]);
			}
		} catch(NumberFormatException ex) {
			throw new Exception("value to start beeping has to be number. Not "+args[3]);
		}
		return res;
	}
	private static ChronoUnit getChronoUnit(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length<2) {
			throw new Exception("ChronoUnit is mandatory");
		}
		try {
			return ChronoUnit.valueOf(args[1].toUpperCase());
		} catch(IllegalArgumentException ex) {
			throw new Exception("incorrect the name of ChronoUnit: "+ args[1]);
		}
	}
	private static int getBeepInterval(String[] args) throws Exception {
		if(args.length<1) {
			throw new Exception("interval of beep is mandatory");
		}
		try {
			int result = Integer.parseInt(args[0].toUpperCase());
			if(result <= 0) {
				throw new Exception("Beep Interval should be positive, not " + args[0]);
			}
			return result;
		} catch(NumberFormatException ex) {
			throw new Exception("Beep Interval should be a number, not " + args[0]);
		}
	}
	/**
	 * waiting for the given time period in milliseconds
	 * @param periodInMillis
	 */
	static void waitingFor(long periodInMillis) {
		//TODO
		//do/while cycle with using Instant objects and method ChronoUnit between
		long amount = 0;
		System.out.println("Before" + Instant.now());
		Instant instant = Instant.now();
		do {
			amount = ChronoUnit.MILLIS.between(instant, Instant.now());
		} while(amount < periodInMillis);
		System.out.println("After" + Instant.now());
		System.out.println("\007");
	}
	private static void beeper(int beepInterval, ChronoUnit unit) {
		System.out.println("BEGIN     " + Instant.now().toString());
		// waiting of the start
		while(instantStart.isAfter(Instant.now())) {
			
		}
		System.out.println("START     " + Instant.now().toString());
		// The start of the process and waiting of the finish
		while(instantFinish.isAfter(Instant.now())) {
			Instant instanceBeeper = Instant.now().plus(beepInterval, unit);
			// waiting of the beep
			while(Instant.now().isBefore(instanceBeeper)) {
				
			}
			System.out.println("BE-E-E-EP " + Instant.now().toString());
			System.out.println("\007");
		}
		// The finish of whole process
		System.out.println("FINISH    " + Instant.now().toString());
	}

}
