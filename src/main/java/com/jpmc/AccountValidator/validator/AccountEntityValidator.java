package com.jpmc.AccountValidator.validator;

import com.jpmc.AccountValidator.exception.BusinessException;
import com.jpmc.AccountValidator.model.Account;


public class AccountEntityValidator {

    public static boolean validate(Account accountEntity) throws BusinessException {

        if (accountEntity.getProviders() == null || accountEntity.getProviders().isEmpty()) {
            throw new BusinessException(460, "Account Providers cannot be null or empty");
        }
        if (accountEntity.getAccountNumber() == null || accountEntity.getAccountNumber().isEmpty()) {
            throw new BusinessException(461, "Account Number cannot be null or empty");
        }
        return true;
    }
}
