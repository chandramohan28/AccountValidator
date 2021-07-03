package com.jpmc.AccountValidator.model;

public class ProviderResponse  {

    public ProviderResponse() {

    }
    public ProviderResponse(String isValid) {
        this.isValid = isValid;
    }

    private String isValid;

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
