package is.technologies.entities;

import java.util.Calendar;
import java.util.Date;
import lombok.Getter;


/**
 A class representing a time provider that provides the current date and time.
 */
@Getter
public class TimeProvider {

  /**
   The current time represented by a {@code Calendar} instance.
   */
  private Calendar date;

  /**
   Initializes a new instance of the {@code TimeProvider} class, with the current time set as the initial time.
   */
  public TimeProvider() {
    this.date = Calendar.getInstance();
    this.date.setTime(new Date());
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);
  }

  /**
   Changes the current time to the specified date and time.
   @param dateTime the new date and time to set as the current time.
   */
  public void changeDate(Calendar dateTime) {
    date = dateTime;
  }
}
