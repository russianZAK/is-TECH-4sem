import is.technologies.models.*;
import is.technologies.entities.*;
import is.technologies.service.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Assertions;

class DateChangeTest {
  @org.junit.jupiter.api.Test
  void dateChange()
      throws BankException, ClientException, AggregatorException, BankAccountException, BankAccountIdException, TransactionException {
    var timeProver = new TimeProvider();
    Calendar tempCalendar = Calendar.getInstance();
    tempCalendar.setTime(new Date(122, Calendar.NOVEMBER, 26));
    timeProver.changeDate(tempCalendar);
    var centralBank = new CentralBank(timeProver);
    BigDecimal percentForDebitAccounts = new BigDecimal(5);
    Bank sber = centralBank.addNewBank("Sber", percentForDebitAccounts, new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(400), new BigDecimal(10000), new BigDecimal(50000));

    Client newClient = new Client.Builder()
        .setFirstName("Fisenko")
        .setSurname("Nikita")
        .setAddress("Teatralnaya 354")
        .setPassport("1017 3434345")
        .build();

    sber.addNewClient(newClient);
    sber.addNewDebitAccountToClient(newClient);
    BankAccount debitAccount = newClient.getBankAccounts().get(0);
    BigDecimal newBalance = new BigDecimal(4000);
    sber.moneyTopUpTransaction(debitAccount, newBalance);

    var timeProverForChangingDate = new TimeProvider();

    Calendar newDate = Calendar.getInstance();
    newDate.setTime(new Date(122, Calendar.NOVEMBER, 26));
    newDate.add(Calendar.MONTH, 1);

    timeProverForChangingDate.changeDate(newDate);
    centralBank.changeDate(timeProverForChangingDate.getDate());

    BigDecimal dayPercent = percentForDebitAccounts.divide(new BigDecimal(365), 10,  RoundingMode.HALF_UP) ;
    BigDecimal newAmountOfMoney = new BigDecimal(0);

    for (int i = 0; i < 5; i++)
    {
      newAmountOfMoney = newAmountOfMoney.add(newBalance.multiply(dayPercent).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
    }

    Assertions.assertEquals(debitAccount.getBalance(), newAmountOfMoney.add(newBalance));

  }
}
