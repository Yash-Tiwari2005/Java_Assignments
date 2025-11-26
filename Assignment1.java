import java.util.*;

class Account {

    private int Accountnumber;
    private String accountholderName;
    private double Balance;
    private String Email;
    private String Contact;

    // Constructor
    public Account(int Accountnumber, String accountholderName, double Balance, String Email, String Contact) {
        this.Accountnumber = Accountnumber;
        this.accountholderName = accountholderName;
        this.Balance = Balance;
        this.Email = Email;
        this.Contact = Contact;
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            Balance += amount;
            System.out.println("Deposit successful! New Balance: " + Balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= Balance) {
            Balance -= amount;
            System.out.println("Withdrawal successful! Remaining Balance: " + Balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance!");
        }
    }

    // Display account details
    public void displayaccountdetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account number: " + Accountnumber);
        System.out.println("Account Holder Name: " + accountholderName);
        System.out.println("Balance: " + Balance);
        System.out.println("Email: " + Email);
        System.out.println("Contact: " + Contact);
    }

    // Update contact details
    public void updateContactDetails(String email, String Contact) {
        this.Email = email;
        this.Contact = Contact;
        System.out.println("Contact details updated successfully!");
    }

    // Getter for account number
    public int getAccountNumber() {
        return Accountnumber;
    }
}

class UserInterface {
    private static Scanner sc = new Scanner(System.in);
    private static Account[] accounts = new Account[100]; // store max 100 accounts
    private static int accountCount = 0;
    private static int accountNumberSeed = 1000; // auto-generate account numbers

    // Create new account
    public static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();
        System.out.print("Enter initial deposit amount: ");
        double Balance = sc.nextDouble();
        sc.nextLine(); // consume newline
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        int accountNumber = ++accountNumberSeed; // auto-generate
        accounts[accountCount++] = new Account(accountNumber, name, Balance, email, phone);

        System.out.println(" Account created successfully with Account Number: " + accountNumber);
    }

    // Deposit
    public static void performDeposit() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();

        Account acc = findAccount(accNo);
        if (acc != null) {
            acc.deposit(amount);
        } else {
            System.out.println(" Account not found!");
        }
    }

    // Withdraw
    public static void performWithdrawal() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter withdrawal amount: ");
        double amount = sc.nextDouble();

        Account acc = findAccount(accNo);
        if (acc != null) {
            acc.withdraw(amount);
        } else {
            System.out.println(" Account not found!");
        }
    }

    // Show details
    public static void showAccountDetails() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();

        Account acc = findAccount(accNo);
        if (acc != null) {
            acc.displayaccountdetails();
        } else {
            System.out.println(" Account not found!");
        }
    }

    // Update contact
    public static void updateContact() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        System.out.print("Enter new phone number: ");
        String phone = sc.nextLine();

        Account acc = findAccount(accNo);
        if (acc != null) {
            acc.updateContactDetails(email, phone);
        } else {
            System.out.println(" Account not found!");
        }
    }

    // Find account by account number
    private static Account findAccount(int accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                return accounts[i];
            }
        }
        return null;
    }

    // Main menu
    public static void mainMenu() {
        int choice;
        do {
            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Show Account Details");
            System.out.println("5. Update Contact Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                case 6: System.out.println("Exiting... Thank you!"); break;
                default: System.out.println(" Invalid choice!");
            }
        } while (choice != 6);
    }
}

public class Assignment1{
    public static void main(String[] args) {
        UserInterface.mainMenu();
    }
}

