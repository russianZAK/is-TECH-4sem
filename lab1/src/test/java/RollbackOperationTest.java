import is.technologies.models.*;
import is.technologies.entities.*;
import is.technologies.service.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;

class RollbackOperationTest {
  @org.junit.jupiter.api.Test
  void rollbackOperation()
      throws BankException, ClientException, AggregatorException, BankAccountException, BankAccountIdException, TransactionException {
    var timeProver = new TimeProvider();
    var centralBank = new CentralBank(timeProver);
    Bank sber = centralBank.addNewBank("Sber", new BigDecimal(5), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(400), new BigDecimal(10000), new BigDecimal(50000));

    Client newClient = new Client.Builder().setFirstName("Fisenko").setSurname("Nikita").setAddress("Teatralnaya 354").setPassport("1017 3434345").build();
    sber.addNewClient(newClient);
    sber.addNewDebitAccountToClient(newClient);
    BankAccount debitAccount = newClient.getBankAccounts().get(0);
    BigDecimal newBalance = new BigDecimal(4000);
    sber.moneyTopUpTransaction(debitAccount, newBalance);
    BigDecimal startBalance = BigDecimal.ZERO;

    Transaction transaction = sber.getTransactions().get(0);
    transaction.rollback();
    Assertions.assertEquals(debitAccount.getBalance(), startBalance);
  }
}
