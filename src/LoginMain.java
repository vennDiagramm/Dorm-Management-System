import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMain extends JFrame {
    private JPanel Login; // Main panel for login components
    private JLabel User; // Username label
    private JTextField textField1; // Username input field
    private JButton loginButton; // Login button
    private JPasswordField passwordField1; // Password input field
    private JLabel passwordLabel; // Password label

    public LoginMain() {
        createUIComponents(); // Initialize UI components

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = textField1.getText(); // Get username input
                String password = new String(passwordField1.getPassword()); // Get password input

                // Check if username and password match expected values
                if ("admin".equals(user) && "password123".equals(password)) {
                    System.out.println("Login successful, opening MainMenu...");
                    dispose(); // Dispose the current login frame
                    MainMenu mainMenu = new MainMenu(); // Open the main menu
                    mainMenu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Login, "Incorrect Credentials"); // Show error message
                }
            }
        });
    }

    // Method to create UI components
    private void createUIComponents() {
        Login = new JPanel(new GridBagLayout()); // Create main panel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints for layout control

        // Set padding and alignment constraints
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.WEST; // Align to the left

        // Username label and text field
        User = new JLabel("Username:");
        textField1 = new JTextField(20);

        // Password label and password field
        passwordLabel = new JLabel("Password:");
        passwordField1 = new JPasswordField(20);

        // Login button
        loginButton = new JButton("Login");

        // Add components to the panel with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        Login.add(User, gbc); // Add username label

        gbc.gridx = 1;
        Login.add(textField1, gbc); // Add username text field

        gbc.gridx = 0;
        gbc.gridy = 1;
        Login.add(passwordLabel, gbc); // Add password label

        gbc.gridx = 1;
        Login.add(passwordField1, gbc); // Add password text field

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        Login.add(loginButton, gbc); // Add login button centered

        // Customize fonts for components
        User.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        textField1.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField1.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginMain loginMain = new LoginMain(); // Create instance of LoginMain
                loginMain.setContentPane(loginMain.Login); // Set content pane to the login panel
                loginMain.setTitle("Login Screen"); // Set window title
                loginMain.setSize(400, 200); // Set window size
                loginMain.setLocationRelativeTo(null); // Center window on screen
                loginMain.setVisible(true); // Make window visible
                loginMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
            }
        });
    }
}
