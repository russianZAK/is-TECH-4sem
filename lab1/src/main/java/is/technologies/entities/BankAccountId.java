package is.technologies.entities;

import is.technologies.exceptions.*;
import java.util.UUID;
import lombok.*;

/**
 The BankAccountId class represents a unique identifier for a bank account.
 It consists of a bank ID and an account ID.
 */
@Getter
public class BankAccountId {
    /**
     The unique ID of the bank where the account is located.
     */
    private final int bankId;

    /**
     The unique ID of the account.
     */
    private final UUID accountId;

    /**
     Creates a new BankAccount instance with the specified bank ID and account ID.
     @param bankId The unique ID of the bank where the account is located.
     @param accountId The unique ID of the account.
     */
    public BankAccountId(int bankId, UUID accountId) throws BankAccountIdException {
        if (bankId < 0)
            throw BankAccountIdException.invalidId(bankId);

        this.bankId = bankId;
        this.accountId = accountId;
    }
}
