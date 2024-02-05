package com.midas.app.controllers;

import com.midas.app.mappers.Mapper;
import com.midas.app.models.Account;
import com.midas.app.services.AccountService;
import com.midas.generated.api.AccountsApi;
import com.midas.generated.model.AccountDto;
import com.midas.generated.model.CreateAccountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

// Controller annotation indicates that this class is a Spring MVC controller
@Controller
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

  private final AccountService accountService;
  private final Logger logger = LoggerFactory.getLogger(AccountController.class);

  /**
   * POST /accounts : Create a new user account
   * Creates a new user account with the given details and attaches a supported payment provider such as 'stripe'.
   *
   * @param createAccountDto User account details (required)
   * @return User account created (status code 201)
   */
  @PostMapping("create-account")
  @Override
  public ResponseEntity<AccountDto> createUserAccount(CreateAccountDto createAccountDto) {
    // Log information about the account creation process
    logger.info("Creating account for user with email: {}", createAccountDto.getEmail());

    // Create an account using the AccountService
    var account =
        accountService.createAccount(
            Account.builder()
                .firstName(createAccountDto.getFirstName())
                .lastName(createAccountDto.getLastName())
                .email(createAccountDto.getEmail())
                .build());

    // Return the created account as a ResponseEntity with HTTP status 201 (Created)
    return new ResponseEntity<>(Mapper.toAccountDto(account), HttpStatus.CREATED);
  }

  /**
   * GET /accounts : Get list of user accounts
   * Returns a list of user accounts.
   *
   * @return List of user accounts (status code 200)
   */
  @Override
  public ResponseEntity<List<AccountDto>> getUserAccounts() {
    // Log information about the account retrieval process
    logger.info("Retrieving all accounts");

    // Retrieve all accounts using the AccountService
    var accounts = accountService.getAccounts();

    // Convert the list of accounts to a list of AccountDto using the Mapper
    var accountsDto = accounts.stream().map(Mapper::toAccountDto).toList();

    // Return the list of AccountDto as a ResponseEntity with HTTP status 200 (OK)
    return new ResponseEntity<>(accountsDto, HttpStatus.OK);
  }
}
