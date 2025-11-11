import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private UsuarioController usuarioController;
    
    public LoginFrame() {
        usuarioController = new UsuarioController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión de Contenidos - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 245));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("ESTUDIO DE GRABACIÓN AUDIOVISUAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión de Contenidos", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 200, 255));
        
        JPanel titleContainer = new JPanel(new GridLayout(2, 1));
        titleContainer.setBackground(new Color(63, 81, 181));
        titleContainer.add(titleLabel);
        titleContainer.add(subtitleLabel);
        
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 245));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(emailLabel, gbc);
        
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 13));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(passwordField, gbc);
        
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(63, 81, 181));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(loginButton, gbc);
        
        JLabel infoLabel = new JLabel("<html><center>Cuentas de prueba:<br>gio@uvg.edu.gt / admin123<br>juan@uvg.edu.gt / juan123</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(100, 100, 100));
        gbc.gridy = 5;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(infoLabel, gbc);
        
        loginButton.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos",
                "Campos vacíos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (usuarioController.login(email, password)) {
            this.dispose();
            MainApplicationFrame mainFrame = new MainApplicationFrame(usuarioController);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Email o contraseña incorrectos",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}