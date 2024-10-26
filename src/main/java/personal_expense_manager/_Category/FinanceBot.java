package personal_expense_manager._Category;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinanceBot {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel statusLabel;

	public FinanceBot() {
		// Set up the main login frame
		frame = new JFrame("Login to Raqtrack");

		// Create input fields and labels for username and password
		usernameField = new JTextField();
		passwordField = new JPasswordField();

		// Create a login button
		JButton loginButton = new JButton("Login");

		// Label to display login status
		statusLabel = new JLabel("Enter your credentials");

		// Set up the login panel layout
		JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// Add components to the panel
		panel.add(new JLabel("Username:"));
		panel.add(usernameField);
		panel.add(new JLabel("Password:"));
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(statusLabel);

		// Add panel to frame
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center on screen
		frame.setVisible(true);

		// Add action listener to handle login button click
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});
	}

	private void handleLogin() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());

		// Authenticate using the Authenticator class
		if (Authenticator.validateLogin(username, password)) {
			statusLabel.setText("Login successful!");
			openMainMenu(); // Open the main Chatbot menu
		} else {
			statusLabel.setText("Invalid username or password.");
		}
	}

	private void openMainMenu() {
		// Hide the login frame
		frame.setVisible(false);

		// Initialize and launch the main expense management application (Chatbot)
		Chatbot chatbot = new Chatbot();
		chatbot.showMenu(); // This opens the main menu of the expense app
	}

	public static void main(String[] args) {
		new FinanceBot();
	}
}
