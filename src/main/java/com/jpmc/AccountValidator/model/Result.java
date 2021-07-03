package com.jpmc.AccountValidator.model;

public class Result {

    private String provider;
    private String isValid;

    public Result(String provider, String isValid) {
        this.provider = provider;
        this.isValid = isValid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
