package is.technologies.entities;

import is.technologies.exceptions.CentralBankException;
import java.util.Calendar;

/**
 Represents a subject interface.
 */
public interface Subject {
  /**
   Attaches the specified watcher to this subject.
   @param watcher The watcher to attach.
   @throws CentralBankException if there is an error with the central bank.
   */
  void attach(Watcher watcher) throws CentralBankException;

  /**
   Detaches the specified watcher from this subject.
   @param watcher The watcher to detach.
   @throws CentralBankException if there is an error with the central bank.
   */
  void detach(Watcher watcher) throws CentralBankException;

  /**
   Changes the date of this subject to the specified time.
   @param time The time to change the date to.
   */
  void changeDate(Calendar time);
}
