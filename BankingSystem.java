package Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// createing abstract class for protection the details

abstract class Account {
    protected long accountNumber;
    protected String accountHolderName;
    protected double balance;

   
    public Account(long accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(long accountNumber, String accountHolderName, double balance, double interestRate) {
        super(accountNumber, accountHolderName, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Amount withdrawn successfully.");
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }
}

class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(long accountNumber, String accountHolderName, double balance, double overdraftLimit) {
        super(accountNumber, accountHolderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            System.out.println("Amount withdrawn successfully.");
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }
}

class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void createAccount(Account account) {
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    public Account getAccount(long accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public void deleteAccount(long accountNumber) {
        accounts.removeIf(account -> account.getAccountNumber() == accountNumber);
        System.out.println("Account deleted successfully.");
    }

    public void displayAllAccounts() {
        for (Account account : accounts) {
            account.displayAccountDetails();
            System.out.println("------------------------");
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\nBanking System Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Account Details");
            System.out.println("5. Delete Account");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. Savings Account");
                    System.out.println("2. Current Account");
                    System.out.print("Select account type: ");
                    int accountType = scanner.nextInt();

                    System.out.print("Enter account number: ");
                    long accountNumber = scanner.nextLong();
                    System.out.print("Enter account holder name: ");
                    String accountHolderName = scanner.next();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();

                    if (accountType == 1) {
                        System.out.print("Enter interest rate: ");
                        double interestRate = scanner.nextDouble();
                        bank.createAccount(new SavingsAccount(accountNumber, accountHolderName, initialBalance, interestRate));
                    } else if (accountType == 2) {
                        System.out.print("Enter overdraft limit: ");
                        double overdraftLimit = scanner.nextDouble();
                        bank.createAccount(new CurrentAccount(accountNumber, accountHolderName, initialBalance, overdraftLimit));
                    } else {
                        System.out.println("Invalid account type.");
                    }
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLong();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    Account account = bank.getAccount(accountNumber);
                    if (account != null) {
                        account.deposit(depositAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLong();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account = bank.getAccount(accountNumber);
                    if (account != null) {
                        account.withdraw(withdrawAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLong();
                    account = bank.getAccount(accountNumber);
                    if (account != null) {
                        account.displayAccountDetails();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLong();
                    bank.deleteAccount(accountNumber);
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
