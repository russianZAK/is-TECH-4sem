package is.technologies.entities;

import java.util.Calendar;

/**
 Represents a watcher interface.
 */
public interface Watcher {
  /**
   Updates the date of this watcher to the specified time.
   @param time The time to update the date to.
   */
  void updateDate(Calendar time);
}
