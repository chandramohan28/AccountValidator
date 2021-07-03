package com.jpmc.AccountValidator.controller;

import com.jpmc.AccountValidator.model.Account;
import com.jpmc.AccountValidator.model.Result;
import com.jpmc.AccountValidator.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accountValidator")
public class AccountController {


    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/validate")
    public List<Result> validate(@RequestBody Account account) throws Exception {
        return service.validateAccount(account);
    }
}
