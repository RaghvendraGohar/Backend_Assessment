package com.midas.app.mappers;

import com.midas.app.models.Account;
import com.midas.generated.model.AccountDto;
import lombok.NonNull;

/**
 * Mapper class for converting between Account and AccountDto objects.
 */
public class Mapper {
  // Prevent instantiation
  private Mapper() {}

  /**
   * toAccountDto maps an account to an account dto.
   *
   * @param account is the account to be mapped
   * @return AccountDto representing the mapped account
   */
  public static AccountDto toAccountDto(@NonNull Account account) {
    // Create a new AccountDto to store the mapped values
    var accountDto = new AccountDto();

    // Map the values from the provided Account to the AccountDto
    accountDto
        .id(account.getId())
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .email(account.getEmail())
        .createdAt(account.getCreatedAt())
        .updatedAt(account.getUpdatedAt());

    // Return the mapped AccountDto
    return accountDto;
  }
}
