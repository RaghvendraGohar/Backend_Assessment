package com.midas.app.services;

import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.CreateAccountWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import java.util.List;
import org.springframework.stereotype.Service;

// Service annotation indicates that this class is a service component managed by Spring
@Service
public class AccountServiceImpl implements AccountService {

  private final WorkflowClient workflowClient;
  private final AccountRepository accountRepository;

  // Constructor injection of WorkflowClient and AccountRepository dependencies
  public AccountServiceImpl(WorkflowClient workflowClient, AccountRepository accountRepository) {
    this.workflowClient = workflowClient;
    this.accountRepository = accountRepository;
  }

  /**
   * Create a new user account using a Temporal workflow.
   *
   * @param details User account details (required)
   * @return Created user account
   */
  @Override
  public Account createAccount(Account details) {
    // Log the initiation of the workflow
    System.out.println("Initiating workflow to create account for email: " + details.getEmail());

    // Configure the workflow options
    WorkflowOptions options =
        WorkflowOptions.newBuilder()
            .setTaskQueue(CreateAccountWorkflow.QUEUE_NAME)
            .setWorkflowId(details.getEmail())
            .build();

    // Create a workflow stub using the injected workflowClient
    CreateAccountWorkflow workflow =
        workflowClient.newWorkflowStub(CreateAccountWorkflow.class, options);

    // Call the workflow method to create the account
    Account account = workflow.createAccount(details);

    // Optionally, you can get the workflowId if needed
    String workflowId = WorkflowStub.fromTyped(workflow).getExecution().getWorkflowId();

    // Return the created account
    return account;
  }

  /**
   * Get a list of all user accounts.
   *
   * @return List of user accounts
   */
  @Override
  public List<Account> getAccounts() {
    // Retrieve all accounts from the injected AccountRepository
    return accountRepository.findAll();
  }
}
