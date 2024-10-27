package personal_expense_manager._Category;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LoginGUI {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginGUI() {
        initializeUI();
    }

    private void initializeUI() {
        // Set up the main login frame
        frame = new JFrame("Login to Raqtrack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create input fields and labels for username and password
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Create login and clear buttons
        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        // Label to display login status
        statusLabel = new JLabel("Enter your credentials");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set up the panel layout and add components
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add components to the panel
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(clearButton);
        panel.add(statusLabel);

        // Set up frame layout and add panel
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // Add action listeners for the buttons
        loginButton.addActionListener(e -> handleLogin());
        clearButton.addActionListener(e -> clearFields());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        // Authenticate using the Authenticator class
        if (Authenticator.validateLogin(username, password)) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(0, 128, 0)); // Green for success
            openMainMenu();
        } else {
            statusLabel.setText("Invalid username or password.");
            statusLabel.setForeground(Color.RED); // Red for error
        }
    }

    private void openMainMenu() {
        // Hide the login frame
        frame.setVisible(false);

        // Initialize and launch the main expense management application (FinanceBot)
        FinanceBot financeBot = new FinanceBot();
        financeBot.showMenu(); // Opens the main menu of the expense app
    }

    private void clearFields() {
        // Clear input fields and reset status message
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText("Enter your credentials");
        statusLabel.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
