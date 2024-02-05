package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

/**
 * Implementation of the CreateAccountWorkflow interface.
 */
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {

  // Create an activity stub to interact with AccountActivity
  private final AccountActivity activities =
      Workflow.newActivityStub(
          AccountActivity.class,
          ActivityOptions.newBuilder()
              .setTaskQueue(CreateAccountWorkflow.QUEUE_NAME)
              .setStartToCloseTimeout(Duration.ofSeconds(40))
              .build());

  /**
   * Creates a new user account by saving the account details and creating a payment account.
   *
   * @param account User account details (required)
   * @return Saved user account
   */
  @Override
  public Account createAccount(Account account) {
    // Save the user account using the AccountActivity
    Account savedAccount = activities.saveAccount(account);

    // Create a payment account (currently a placeholder method in AccountActivity)
    activities.createPaymentAccount(savedAccount);

    // Return the saved user account
    return savedAccount;
  }
}
