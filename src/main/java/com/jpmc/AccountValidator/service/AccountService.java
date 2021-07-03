package com.jpmc.AccountValidator.service;

import com.jpmc.AccountValidator.config.Providers;
import com.jpmc.AccountValidator.exception.TechnicalException;
import com.jpmc.AccountValidator.model.Account;
import com.jpmc.AccountValidator.model.AccountNumber;
import com.jpmc.AccountValidator.model.ProviderResponse;
import com.jpmc.AccountValidator.model.Result;
import com.jpmc.AccountValidator.validator.AccountEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {


    private Providers providersConfig;


    private RestTemplate restTemplate;

    @Autowired
    public AccountService(Providers providersConfig, RestTemplate restTemplate) {
        this.providersConfig = providersConfig;
        this.restTemplate = restTemplate;
    }

    public List<Result> validateAccount(Account account) throws Exception {

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        List<Result> resultList = new ArrayList<>();
        try {
            if (AccountEntityValidator.validate(account)) {
                for (String accountProvider : account.getProviders()) {
                    providersConfig.getProviders().forEach((configProvider) -> {
                        if (configProvider.getName().equals(accountProvider)) {
                            ProviderResponse providerResponse = restTemplate.postForObject(configProvider.getUrl(), new AccountNumber(account.getAccountNumber()), ProviderResponse.class);
                            resultList.add(new Result(accountProvider, providerResponse.getIsValid()));
                        }
                    });

                }
            }
        } catch (HttpClientErrorException ex) {
            throw new TechnicalException(599, "Provider Service not available");
        }
        return resultList;
    }
}
