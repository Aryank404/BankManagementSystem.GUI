import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BankManagementSystemGUI extends JFrame {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private JTextField accountNumberField, amountField, senderAccountField, recipientAccountField;
    private JTextArea logArea;

    public BankManagementSystemGUI() {
        setTitle("Bank Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Bank Management System");
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField(10);
        centerPanel.add(accountNumberField);
        centerPanel.add(new JLabel("Amount:"));
        amountField = new JTextField(10);
        centerPanel.add(amountField);
        centerPanel.add(new JLabel("Sender Account:"));
        senderAccountField = new JTextField(10);
        centerPanel.add(senderAccountField);
        centerPanel.add(new JLabel("Recipient Account:"));
        recipientAccountField = new JTextField(10);
        centerPanel.add(recipientAccountField);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createBtn = new JButton("Create Account");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        buttonPanel.add(createBtn);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        buttonPanel.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        buttonPanel.add(withdrawBtn);

        JButton checkBalanceBtn = new JButton("Check Balance");
        checkBalanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        buttonPanel.add(checkBalanceBtn);

        JButton transferBtn = new JButton("Transfer");
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        buttonPanel.add(transferBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.EAST);

        setVisible(true);
    }

    private void createAccount() {
        String accountNumber = accountNumberField.getText();
        double initialBalance = Double.parseDouble(amountField.getText());
        BankAccount account = new BankAccount(accountNumber, initialBalance);
        accounts.put(accountNumber, account);
        log("Account created successfully: " + accountNumber);
    }

    private void deposit() {
        String accountNumber = accountNumberField.getText();
        double amount = Double.parseDouble(amountField.getText());
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).deposit(amount);
            log("Deposit of ₹" + amount + " successful for account: " + accountNumber);
        } else {
            log("Account not found");
        }
    }

    private void withdraw() {
        String accountNumber = accountNumberField.getText();
        double amount = Double.parseDouble(amountField.getText());
        if (accounts.containsKey(accountNumber)) {
            if (accounts.get(accountNumber).withdraw(amount)) {
                log("Withdrawal of ₹" + amount + " successful for account: " + accountNumber);
            } else {
                log("Insufficient funds or invalid amount for withdrawal.");
            }
        } else {
            log("Account not found");
        }
    }

    private void checkBalance() {
        String accountNumber = accountNumberField.getText();
        if (accounts.containsKey(accountNumber)) {
            log("Balance of account " + accountNumber + ": ₹" + accounts.get(accountNumber).getBalance());
        } else {
            log("Account not found");
        }
    }

    private void transfer() {
        String senderAccountNumber = senderAccountField.getText();
        String recipientAccountNumber = recipientAccountField.getText();
        if (accounts.containsKey(senderAccountNumber) && accounts.containsKey(recipientAccountNumber)) {
            double amount = Double.parseDouble(amountField.getText());
            if (accounts.get(senderAccountNumber).transfer(accounts.get(recipientAccountNumber), amount)) {
                log("Transfer of ₹" + amount + " from account " + senderAccountNumber + " to account " + recipientAccountNumber + " successful");
            } else {
                log("Transfer failed.");
            }
        } else {
            log("One or both accounts not found");
        }
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankManagementSystemGUI();
            }
        });
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public boolean withdraw(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            return true;
        } else {
            return false;
        }
    }
}
