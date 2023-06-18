package is.technologies;

import static java.lang.System.exit;

import is.technologies.service.*;
import is.technologies.exceptions.*;
import is.technologies.entities.*;
import is.technologies.models.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

  public static final CentralBank centralBank;

  static {
    try {
      centralBank = generateBanks();
    } catch (BankException e) {
      throw new RuntimeException(e);
    }
  }

  public static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    mainMenu();
  }

  public static CentralBank generateBanks() throws BankException {

    var timeProvider = new TimeProvider();
    CentralBank centralBank = new CentralBank(timeProvider);

    centralBank.addNewBank("Sber", new BigDecimal(5), new BigDecimal(3),
        new BigDecimal(4), new BigDecimal(5), new BigDecimal(400), new BigDecimal(10000),
        new BigDecimal(50000));
    centralBank.addNewBank("Tinkoff", new BigDecimal(6), new BigDecimal(3), new BigDecimal(4),
        new BigDecimal(5), new BigDecimal(200), new BigDecimal(20000), new BigDecimal(500000));
    centralBank.addNewBank("Open", new BigDecimal(3), new BigDecimal(1), new BigDecimal(2),
        new BigDecimal(3), new BigDecimal(275), new BigDecimal(15000), new BigDecimal(200000));

    return centralBank;
  }

  public static void mainMenu() {
    System.out.println("Main menu:");
    String[] options = {"1 - Choose the bank",
        "2 - Add new bank",
        "3 - Changing time",
        "4 - Exit",
    };
    for (String str : options) {
      System.out.println(str);
    }

    try {
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          chooseBank();
          break;
        case 2:
          addNewBank();
          break;
        case 3:
          changeTime();
          break;
        case 4:
          exit(0);
      }
    } catch (Exception ex) {
      System.out.println("Please enter an integer value between 1 and " + options.length);
    }
  }

  public static void changeTime() {

    System.out.println("Enter months: ");
    int months = scanner.nextInt();

    var timeProverForChangingDate = new TimeProvider();

    Calendar newDate = timeProverForChangingDate.getDate();
    newDate.add(Calendar.MONTH, months);

    timeProverForChangingDate.changeDate(newDate);
    centralBank.changeDate(timeProverForChangingDate.getDate());

    mainMenu();
  }


  public static void addNewBank() throws BankException {

    scanner.nextLine();

    String name = null;
    BigDecimal percentForDebitAccounts, percentLessFiftyThousand, percentLessOneHundredThousand, percentMoreOneHundredThousand,
        commissionForCreditAccount, restrictionForNotVerifiedCustomers, creditLimit;

    System.out.println("Enter name: ");
    try {
      name = scanner.nextLine().trim();
    } catch (Exception ex) {
      System.out.println("Please enter string value");
    }

    System.out.println("Enter percent for debit accounts:");
    percentForDebitAccounts = checkBigDecimalValue();

    System.out.println("Enter percent less fifty thousand:");
    percentLessFiftyThousand = checkBigDecimalValue();

    System.out.println("Enter percent less one hundred thousand:");
    percentLessOneHundredThousand = checkBigDecimalValue();

    System.out.println("Enter percent more one hundred thousand");
    percentMoreOneHundredThousand = checkBigDecimalValue();

    System.out.println("Enter commission for credit account");
    commissionForCreditAccount = checkBigDecimalValue();

    System.out.println("Enter restriction for not verified customers");
    restrictionForNotVerifiedCustomers = checkBigDecimalValue();

    System.out.println("Enter credit limit");
    creditLimit = checkBigDecimalValue();

    centralBank.addNewBank(name, percentForDebitAccounts, percentLessFiftyThousand,
        percentLessOneHundredThousand, percentMoreOneHundredThousand, commissionForCreditAccount,
        restrictionForNotVerifiedCustomers, creditLimit);

    mainMenu();
  }

  public static void chooseBank() {
    int countOfBank = 1;
    for (Bank bank : centralBank.getBanks()) {
      System.out.println(countOfBank + " - " + bank.getName());
      countOfBank++;
    }

    int option = scanner.nextInt();

    while (option > centralBank.getBanks().size()) {
      System.out.println(
          "Please enter an integer value between 1 and " + centralBank.getBanks().size());
      option = scanner.nextInt();
    }

    bankMenu(centralBank.getBanks().get(option - 1));
  }

  public static void bankMenu(Bank bank) {
    System.out.println("Bank menu:");
    String[] options = {"1 - Register",
        "2 - Choose account",
        "3 - Transactions",
        "4 - Back",
    };

    for (String str : options) {
      System.out.println(str);
    }

    try {
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          register(bank);
          break;
        case 2:
          chooseAccount(bank);
          break;
        case 3:
          transactions(bank);
          break;
        case 4:
          mainMenu();
          break;
      }
    } catch (Exception ex) {
      System.out.println("Please enter an integer value between 1 and " + options.length);
      scanner.next();
    }
  }

  public static void transactions(Bank bank) throws BankAccountException, TransactionException {
    int countOfTransactions = 1;

    for (Transaction transaction : bank.getTransactions()) {
      System.out.println(
          countOfTransactions + " - " + transaction.getAccountFrom().getOwner().getName() + " "
              + transaction.getAccountFrom().getOwner().getSurname()
              + "-" + transaction.getAccountTo().getOwner().getName() + " "
              + transaction.getAccountTo().getOwner().getSurname() + "-" + transaction.getMoney()
              + "$");
      countOfTransactions++;
    }

    int option = scanner.nextInt();

    while (option > bank.getTransactions().size()) {
      System.out.println(
          "Please enter an integer value between 1 and " + bank.getTransactions().size());
      option = scanner.nextInt();
    }

    Transaction chosentransaction = bank.getTransactions().get(option - 1);

    System.out.println("Do you want to roll it back? 1-yes/2-no");

    int nextOption = scanner.nextInt();

    while (nextOption > 2) {
      System.out.println(
          "Please enter an integer value between 1 and 2");
      nextOption = scanner.nextInt();
    }

    switch (nextOption) {
      case 1:
        chosentransaction.rollback();
        centralBank.rolledBackTransaction(chosentransaction);
        bankMenu(bank);
        break;
      case 2:
        bankMenu(bank);
        break;
    }
  }

  public static void chooseAccount(Bank bank) throws InterruptedException {
    int countOfClient = 1;

    if (bank.getClients().size() == 0) {
      System.out.println("There is no clients");
      Thread.sleep(2000);
      bankMenu(bank);
    }

    for (Client client1 : bank.getClients()) {
      System.out.println(countOfClient + " - " + client1.getName() + " "
          + client1.getSurname());
      countOfClient++;
    }
    System.out.println(countOfClient + " - Back");

    int option = scanner.nextInt();

    while (option > bank.getClients().size() + 1) {
      System.out.println("Please enter an integer value between 1 and " + bank.getClients().size());
      option = scanner.nextInt();
    }

      if (option == bank.getClients().size() + 1) {
          bankMenu(bank);
      }

    Client client = bank.getClients().get(option - 1);
    clientAccountMenu(client);
  }

  public static void clientAccountMenu(Client client) {
    System.out.println("Client menu:");
    String[] options = {"1 - Open a new bank account",
        "2 - Bank accounts",
        "3 - Back",
    };

    for (String str : options) {
      System.out.println(str);
    }

    try {
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          openBankAccount(client);
          break;
        case 2:
          bankAccounts(client);
          break;
        case 3:
          chooseAccount(client.getBank());
          break;
      }
    } catch (Exception ex) {
      System.out.println("Please enter an integer value between 1 and " + options.length);
      scanner.next();
    }

  }

  public static void bankAccountMenu(BankAccount bankAccount) {
    System.out.println("Bank account menu:" + " " + bankAccount.getBalance() + "$");
    String[] options = {"1 - Top up your account",
        "2 - Withdraw from the account",
        "3 - Transfer to another bank account",
        "4 - Back",
    };

    for (String str : options) {
      System.out.println(str);
    }

    try {
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          topUpBankAccount(bankAccount);
          break;
        case 2:
          withdrawFromAccount(bankAccount);
          break;
        case 3:
          transferToAnotherBankAccount(bankAccount);
          break;
        case 4:
          bankAccounts(bankAccount.getOwner());
          break;
      }
    } catch (Exception ex) {
      System.out.println("Please enter an integer value between 1 and " + options.length);
      scanner.next();
    }

  }

  public static void transferToAnotherBankAccount(BankAccount bankAccount)
      throws BankAccountException, BankException, TransactionException {
    System.out.println("Choose the bank of owner's account");

    int countOfBank = 1;
    for (Bank bank : centralBank.getBanks()) {
      System.out.println(countOfBank + " - " + bank.getName());
      countOfBank++;
    }

    int option = scanner.nextInt();

    while (option > centralBank.getBanks().size()) {
      System.out.println(
          "Please enter an integer value between 1 and " + centralBank.getBanks().size());
      option = scanner.nextInt();
    }

    System.out.println("Choose the client to transfer");

    int countOfClient = 1;
    for (Client client1 : centralBank.getBanks().get(option - 1).getClients()) {
      System.out.println(countOfClient + " - " + client1.getName() + " "
          + client1.getSurname());
      countOfClient++;
    }

    int option2 = scanner.nextInt();

    while (option2 > centralBank.getBanks().get(option - 1).getClients().size() ||
        centralBank.getBanks().get(option - 1).getClients().get(option2 - 1).getBankAccounts()
            .size() == 0) {
      System.out.println(
          "Please enter an integer value between 1 and " + centralBank.getBanks().get(option - 1)
              .getClients().size());
      option2 = scanner.nextInt();
    }

    Client client = centralBank.getBanks().get(option - 1).getClients().get(option2 - 1);

    System.out.println("Choose the account to transfer");

    int countOfAccounts = 1;

    for (BankAccount bankaccountToTransfer : client.getBankAccounts()) {
      System.out.println(countOfAccounts + " - " + bankaccountToTransfer.getBalance() + "$");
      countOfAccounts++;
    }

    int option3 = scanner.nextInt();

    while (option3 > client.getBankAccounts().size()) {
      System.out.println(
          "Please enter an integer value between 1 and " + client.getBankAccounts().size());
      option3 = scanner.nextInt();
    }

    BankAccount bankAccountToTransfer = client.getBankAccounts().get(option3 - 1);

    System.out.println("Enter the amount of money");
    BigDecimal amountOfMoney = scanner.nextBigDecimal();

    var transaction = centralBank.moneyTransferTransaction(bankAccount, bankAccountToTransfer,
        amountOfMoney);

    bankAccountMenu(bankAccount);
  }

  public static void withdrawFromAccount(BankAccount bankAccount)
      throws BankAccountException, BankException, TransactionException {

    System.out.println("Enter the amount of money");
    BigDecimal amountOfMoney = scanner.nextBigDecimal();
    bankAccount.getBank().moneyWithdrawTransaction(bankAccount, amountOfMoney);

    bankAccountMenu(bankAccount);
  }

  public static void topUpBankAccount(BankAccount bankAccount)
      throws BankAccountException, BankException, TransactionException {

    System.out.println("Enter the amount of money");
    BigDecimal amountOfMoney = scanner.nextBigDecimal();
    bankAccount.getBank().moneyTopUpTransaction(bankAccount, amountOfMoney);

    bankAccountMenu(bankAccount);
  }

  public static void bankAccounts(Client client) throws InterruptedException {
    int countOfAccounts = 1;

    if (client.getBankAccounts().size() == 0) {
      System.out.println("There is no bank accounts");
      Thread.sleep(2000);
      clientAccountMenu(client);

    }
    for (BankAccount bankaccount : client.getBankAccounts()) {
      System.out.println(countOfAccounts + " - " + bankaccount.getClass().getSimpleName() + " "
          + bankaccount.getBalance() + "$");
      countOfAccounts++;
    }

    System.out.println(countOfAccounts + " - Back");

    int option = scanner.nextInt();

    while (option > client.getBankAccounts().size() + 1) {
      System.out.println(
          "Please enter an integer value between 1 and " + client.getBankAccounts().size() + 1);
      option = scanner.nextInt();
    }
      if (option == client.getBankAccounts().size() + 1) {
          clientAccountMenu(client);
      }

    BankAccount bankAccount = client.getBankAccounts().get(option - 1);
    bankAccountMenu(bankAccount);
  }

  public static void openBankAccount(Client client) {
    System.out.println("Choose type of bank account:");
    String[] options = {"1 - Debit account",
        "2 - Credit account",
        "3 - Deposit account",
        "4 - Back",
    };

    for (String str : options) {
      System.out.println(str);
    }
    try {
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          client.getBank().addNewDebitAccountToClient(client);
          clientAccountMenu(client);
          break;
        case 2:
          client.getBank().addNewCreditAccountToClient(client);
          clientAccountMenu(client);
          break;
        case 3:
          scanner.nextLine();
          System.out.println("Enter the amount of deposit");
          String amount = scanner.nextLine().trim();
          System.out.println("Enter count of years of deposit account");
          String yearsOfDepositAccount = scanner.nextLine().trim();
          Calendar calendar = Calendar.getInstance();
          calendar.add(Calendar.YEAR, Integer.parseInt(yearsOfDepositAccount));
          client.getBank()
              .addNewDepositAccountToClient(client, BigDecimal.valueOf(Integer.parseInt(amount)),
                  calendar);
          clientAccountMenu(client);
          break;
        case 4:
          clientAccountMenu(client);
          break;
      }
    } catch (Exception ex) {
      System.out.println("Please enter an integer value between 1 and " + options.length);
      scanner.next();
    }

  }

  public static void register(Bank bank)
      throws ClientException, MediatorException, BankException, AggregatorException {
    String firstname = null;
    String secondname = null;
    scanner.nextLine();
    System.out.println("Enter your firstname");
    firstname = scanner.nextLine().trim();

    System.out.println("Enter your secondname");
    secondname = scanner.nextLine().trim();

    System.out.println("Enter your address");
    System.out.println("or skip, press enter");
    String address = scanner.nextLine().trim();

    System.out.println("Enter your passport");
    System.out.println("or skip, press enter");
    String passport = scanner.nextLine().trim();

    if (passport != null && address != null) {
      Client newClient = new Client.Builder().setFirstName(firstname).setSurname(secondname)
          .setAddress(address).setPassport(passport).build();
      var mediator = new ClientMediator(newClient);
      bank.addNewClient(newClient);
    } else if (passport != null) {
      Client newClient = new Client.Builder().setFirstName(firstname).setSurname(secondname)
          .setPassport(passport).build();
      var mediator = new ClientMediator(newClient);
      bank.addNewClient(newClient);
    } else if (address != null) {
      Client newClient = new Client.Builder().setFirstName(firstname).setSurname(secondname)
          .setAddress(address).build();
      var mediator = new ClientMediator(newClient);
      bank.addNewClient(newClient);
    }

    System.out.println("Registration is completed");

    bankMenu(bank);
  }

  private static BigDecimal checkBigDecimalValue() {
    BigDecimal value = null;
    try {
      value = scanner.nextBigDecimal();
    } catch (Exception ex) {
      System.out.println("Please enter decimal value");
      scanner.next();
    }

    return value;
  }


}