package telran.application.dates;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DateTimeWithZoneAppl {

	static List<ZonedDateTime> timeZones = new ArrayList<>();
	
	public static void main(String[] args) {
	//args[0] - optional substring of time Zone (default - local time zone)
	//TODO display out the time in the appropriate ZoneId
		if (args.length > 0 && args[0].contains("-help")) {
			printHelp();
			return;
		}
		try {
			getZones(args);
			printResults();
		} catch (RuntimeException ex) {
			ex.printStackTrace();				//functionality error
		} catch(Exception ex) {
			System.out.println(ex.toString());  //wrong input data 
		}
	}

	private static void printHelp() {
		System.out.println("Select one on following zones. Default value is Local Zone");
		for (String zone : ZoneId.getAvailableZoneIds()) {
			System.out.println(zone);
		}
	}

	private static void printResults() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss VV");
		Iterator<ZonedDateTime> itr = timeZones.iterator();
		while(itr.hasNext()) {
			System.out.printf("The time for selected Zone is: %s  \n", itr.next().format(format));
		}
	}

	private static void getZones(String[] args) throws Exception {
		if(args.length>0) {
			for (String zone : ZoneId.getAvailableZoneIds()) {
				if (zone.toLowerCase().contains(args[0].toLowerCase())) { 
					timeZones.add(ZonedDateTime.now(ZoneId.of(zone)));
				}
			}
			if(timeZones.size()==0) {
				throw new Exception("zone="+args[0]+" isn't found");
			}
		} else {
			timeZones.add(ZonedDateTime.now());
		}
	}
}
