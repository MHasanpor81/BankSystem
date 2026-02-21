package ir.naji.common;

import ir.naji.dto.*;
import ir.naji.entity.account.AccountType;
import ir.naji.entity.user.Customer;
import ir.naji.entity.user.employee.Operator;
import ir.naji.entity.user.employee.Manager;
import ir.naji.repository.OperatorRepo;
import ir.naji.repository.ManagerRepo;
import ir.naji.service.*;
import ir.naji.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Operator Username : operator    Password : op1234
//Manager Username: admin   Password:1234
// You can see both them on Repository

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        OperatorService operatorService = new OperatorService(customerService, accountService);
        OperatorAuthService operatorAuthService = new OperatorAuthService();
        ManagerAuthService managerAuthService = new ManagerAuthService();
        ManagerService managerService = new ManagerService();
        System.out.println("Hello");
        System.out.println("Welcome to C Bank");
        System.out.println("Make your Decision");
        System.out.println("WOW WOrld");
        while (true) {
            System.out.println("\n--- Bank Menu ---");
            System.out.println("1. Operator (login and operate)");
            System.out.println("2. Customer (login and operate)");
            System.out.println("3. Manager (login and manage)");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter operator username: ");
                    String opUser = sc.nextLine();
                    System.out.print("Enter password: ");
                    String opPass = sc.nextLine();
                    OperatorDto loggedOp = operatorAuthService.login(opUser, opPass);
                    if (loggedOp == null) {
                        System.out.println("Invalid operator credentials.");
                        continue;
                    }
                    Operator operatorEntity = OperatorRepo.getInstance().findByUsername(opUser);

                    System.out.println("Welcome Operator " + loggedOp.username());

                    while (true) {
                        System.out.println("\n--- Operator Menu ---");
                        System.out.println("1. Create new customer");
                        System.out.println("2. Open account for existing customer");
                        System.out.println("3. Become a customer (if not yet)");
                        System.out.println("0. Back");
                        System.out.print("Choose: ");
                        String opChoice = sc.nextLine().trim();
                        if (opChoice.equals("0")) break;
                        switch (opChoice) {
                            case "1" -> {
                                System.out.print("Enter First Name: ");
                                String firstName = sc.nextLine();
                                if (!ValidationUtil.isValidName(firstName)) {
                                    System.out.println("Invalid first name.");
                                    continue;
                                }
                                System.out.print("Enter Last Name: ");
                                String lastName = sc.nextLine();
                                if (!ValidationUtil.isValidName(lastName)) {
                                    System.out.println("Invalid last name.");
                                    continue;
                                }
                                String id = IdGenerator.generate("CUST");

                                CustomerDto newCustomer = new CustomerDto(
                                        id,
                                        firstName + "." + lastName + "." + id,
                                        firstName,
                                        lastName,
                                        new ArrayList<>()
                                );
                                operatorService.createCustomer(newCustomer);
                                System.out.println("Customer created with ID: " + id + " (waiting for manager approval).");
                            }

                            case "2" -> {
                                System.out.print("Enter Customer ID: ");
                                String id = sc.nextLine();
                                CustomerDto existing = customerService.findById(id);
                                if (existing == null) {
                                    System.out.println("Customer not found.");
                                    continue;
                                }

                                System.out.print("Enter Account Type (SHORT_TERM, LONG_TERM, CURRENT, QHASAN): ");
                                String at = sc.nextLine();
                                if (!ValidationUtil.isValidAccountType(at)) {
                                    System.out.println("Invalid account type.");
                                    continue;
                                }

                                String accNo = AccountNumberGenerator.generate();
                                System.out.println("Generated Account Number: " + accNo);

                                AccountDto accDto = new AccountDto(accNo, AccountType.fromString(at));
                                operatorService.openAccountForCustomer(accDto, existing);
                                System.out.println("Account opened for " + existing.username());
                            }

                            case "3" -> {
                                if (operatorEntity.isAlsoCustomer()) {
                                    System.out.println("You are already a customer with ID: " + operatorEntity.getLinkedCustomerId());
                                } else {
                                    System.out.print("Enter First Name: ");
                                    String fn = sc.nextLine();
                                    System.out.print("Enter Last Name: ");
                                    String ln = sc.nextLine();
                                    String cid = IdGenerator.generate("CUST");

                                    CustomerDto dto = new CustomerDto(
                                            cid,
                                            fn + "." + ln + "." + cid,
                                            fn,
                                            ln,
                                            new ArrayList<>()
                                    );
                                    customerService.saveCustomer(dto);
                                    operatorService.linkOperatorToCustomer(operatorEntity.getId(), cid);
                                    System.out.println("Operator is now also a customer (waiting for manager approval). ID: " + cid);
                                }
                            }

                            default -> System.out.println("Invalid option.");
                        }
                    }
                }


                case "2" -> {
                    System.out.print("Enter your Customer ID to login: ");
                    String id = sc.nextLine();
                    CustomerDto logged = customerService.findById(id);
                    if (logged == null) {
                        System.out.println("Customer not found.");
                        continue;
                    }

                    Customer realCustomer = customerService.findEntityById(id);
                    if (realCustomer != null && !realCustomer.isApproved()) {
                        System.out.println("Your account is not approved by the manager yet.");
                        continue;
                    }

                    System.out.println("Welcome " + logged.username());

                    while (true) {
                        System.out.println("\n--- Customer Menu ---");
                        System.out.println("1. Show my accounts");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Transfer");
                        System.out.println("5. Show balance");
                        System.out.println("0. Logout");
                        System.out.print("Choose: ");
                        String cmd = sc.nextLine().trim();

                        if (cmd.equals("0")) break;

                        switch (cmd) {
                            case "1" -> {
                                List<AccountDto> myAccounts = ConverterUtil.toAccountDtoList(
                                        accountService.getAccountsByCustomer(realCustomer)
                                );
                                if (myAccounts.isEmpty())
                                    System.out.println("No accounts found.");
                                else
                                    myAccounts.forEach(a ->
                                            System.out.println("Account: " + a.accountNumber() + " | Type: " + a.accountType()));
                            }
                            case "2" -> {
                                System.out.print("Enter account number: ");
                                String accNo = sc.nextLine();
                                System.out.print("Enter amount: ");
                                int amt = sc.nextInt();
                                sc.nextLine();
                                boolean ok = accountService.deposit(logged, accNo, amt);
                                System.out.println(ok ? "Deposited." : " Deposit failed (wrong account or not yours).");
                            }
                            case "3" -> {
                                System.out.print("Enter account number: ");
                                String accNo = sc.nextLine();
                                System.out.print("Enter amount: ");
                                int amt = sc.nextInt();
                                sc.nextLine();
                                boolean ok = accountService.withdraw(logged, accNo, amt);
                                System.out.println(ok ? "Withdraw successful." : " Failed (not your account or insufficient funds).");
                            }
                            case "4" -> {
                                System.out.print("Source account: ");
                                String from = sc.nextLine();
                                System.out.print("Destination account: ");
                                String to = sc.nextLine();
                                System.out.print("Amount: ");
                                int amt = sc.nextInt();
                                sc.nextLine();
                                boolean ok = accountService.transfer(logged, from, to, amt);
                                System.out.println(ok ? "Transfer successful." : "Transfer failed.");
                            }
                            case "5" -> {
                                System.out.print("Enter account number: ");
                                String accNo = sc.nextLine();
                                sc.nextLine();
                                Double bal = accountService.getBalance(logged, accNo);
                                System.out.println(bal != null ? "Balance: " + bal : "Account not found or not yours.");
                            }
                            default -> System.out.println("Invalid option.");
                        }
                    }
                }


                case "3" -> {
                    System.out.print("Enter manager username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();

                    ManagerDto loggedManager = managerAuthService.login(uname, pass);
                    if (loggedManager == null) {
                        System.out.println("Invalid manager credentials.");
                        continue;
                    }

                    Manager managerEntity = ManagerRepo.getInstance().findByUsername(uname);
                    System.out.println("Welcome Manager " + loggedManager.username());

                    while (true) {
                        System.out.println("\n--- Manager Menu ---");
                        System.out.println("1. Show all customers");
                        System.out.println("2. Show total bank balance");
                        System.out.println("3. Approve customer");
                        System.out.println("4. Become a customer (if not yet)");
                        System.out.println("0. Back");
                        System.out.print("Choose: ");
                        String mChoice = sc.nextLine().trim();

                        if (mChoice.equals("0")) break;

                        switch (mChoice) {
                            case "1" -> managerService.showAllCustomers();
                            case "2" -> System.out.println("Total Bank Balance: " + managerService.getTotalBankBalance());
                            case "3" -> {
                                System.out.print("Enter customer ID to approve: ");
                                String approveId = sc.nextLine();
                                managerService.approveCustomer(approveId);
                            }
                            case "4" -> {
                                if (managerEntity.isAlsoCustomer()) {
                                    System.out.println("You are already a customer with ID: " + managerEntity.getLinkedCustomerId());
                                } else {
                                    System.out.print("Enter First Name: ");
                                    String fn = sc.nextLine();
                                    System.out.print("Enter Last Name: ");
                                    String ln = sc.nextLine();
                                    String cid = IdGenerator.generate("CUST");

                                    CustomerDto dto = new CustomerDto(
                                            cid,
                                            fn + "." + ln + "." + cid,
                                            fn,
                                            ln,
                                            new ArrayList<>()
                                    );
                                    customerService.saveCustomer(dto);
                                    managerService.linkManagerToCustomer(managerEntity.getId(), cid);
                                    System.out.println("Manager is now also a customer (waiting for approval). ID: " + cid);
                                }
                            }
                            default -> System.out.println("Invalid option.");
                        }
                    }
                }

                
                default -> System.out.println("Invalid choice.");
            }
        }
        System.out.println("Goodbye.");
    }
}
