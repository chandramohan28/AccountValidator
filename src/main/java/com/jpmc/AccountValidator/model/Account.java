package com.jpmc.AccountValidator.model;

import java.util.List;

public class Account {

    private String accountNumber;
    private List<String> providers;

    public Account(String accountNumber, List<String> providers) {
        this.accountNumber = accountNumber;
        this.providers = providers;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }

}
