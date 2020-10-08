package org.golde.hauntedforest.countdowntimer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JComboBox;

import org.golde.hauntedforest.countdowntimer.JTimeSelectBox.HalfHourMarker;

public class JTimeSelectBox extends JComboBox<HalfHourMarker>{

	private static final long serialVersionUID = 7575802071200554940L;

	public JTimeSelectBox() {
		for(int i = 0; i < 24; i++) {
			addItem(new HalfHourMarker(i, false));
			addItem(new HalfHourMarker(i, true));
		}
	}
	
	
	static class HalfHourMarker {

		private final long mills;
		private final String niceString;
		private static final SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm a");
		
		private HalfHourMarker(int hour, boolean halfHour) {
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, halfHour ? 30 : 0);
			this.mills = calendar.getTimeInMillis();

			this.niceString = dateformat.format(calendar.getTime());
			
		}
		
		@Override
		public String toString() {
			return niceString;
		}
		
	}


	public long getMills() {
		return ((HalfHourMarker)getSelectedItem()).mills;
	}
	
}
