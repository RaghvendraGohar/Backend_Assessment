package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import org.springframework.stereotype.Service;

// Service annotation indicates that this class is a service component managed by Spring
@Service
public class AccountActivityImpl implements AccountActivity {

  private final AccountRepository accountRepository;

  // Constructor injection of AccountRepository dependency
  public AccountActivityImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  // Save an account using the injected AccountRepository
  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  // Placeholder method for creating a payment account; currently returns the input account
  @Override
  public Account createPaymentAccount(Account account) {
    // Actual implementation for creating a payment account should be added here
    return account;
  }
}
