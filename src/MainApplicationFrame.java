import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainApplicationFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7952381894013285502L;
	private UsuarioController usuarioController;
    private ContenidoController contenidoController;
    private ReporteController reporteController;
    
    private JTabbedPane tabbedPane;
    private JTable contentTable;
    private DefaultTableModel tableModel;
    private JLabel userInfoLabel;
    
    public MainApplicationFrame(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.contenidoController = new ContenidoController();
        this.reporteController = new ReporteController(contenidoController.getContenidos());
        
        initComponents();
        loadContent();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión de Contenidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JPanel headerPanel = createHeaderPanel();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
        
        tabbedPane.addTab("Contenidos", createContentPanel());
        tabbedPane.addTab("Crear", createCreatePanel());
        tabbedPane.addTab("Categorías", createCategoryPanel());
        tabbedPane.addTab("Reportes", createReportsPanel());
        
        if (usuarioController.obtenerUsuarioActual() instanceof Administrador) {
            tabbedPane.addTab("Usuarios", createUsersPanel());
        }
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        Usuario user = usuarioController.obtenerUsuarioActual();
        String userType = user instanceof Administrador ? "Administrador" : "Editor";
        
        userInfoLabel = new JLabel("Usuario: " + user.getNombre() + " (" + userType + ")");
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userInfoLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setBackground(new Color(220, 50, 50));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(userInfoLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton refreshButton = new JButton("Actualizar");
        JButton viewButton = new JButton("Ver Detalles");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");
        JButton publishButton = new JButton("Publicar/Despublicar");
        
        styleButton(refreshButton);
        styleButton(viewButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(publishButton);
        
        refreshButton.addActionListener(e -> loadContent());
        viewButton.addActionListener(e -> viewContentDetails());
        editButton.addActionListener(e -> editContent());
        deleteButton.addActionListener(e -> deleteContent());
        publishButton.addActionListener(e -> togglePublish());
        
        toolbar.add(refreshButton);
        toolbar.add(viewButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(publishButton);
        
        String[] columns = {"ID", "Tipo", "Título", "Autor", "Fecha", "Estado"};
        tableModel = new DefaultTableModel(columns, 0) {
			
        	private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        contentTable = new JTable(tableModel);
        contentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        contentTable.setRowHeight(25);
        contentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        contentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(contentTable);
        
        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCreatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel typeLabel = new JLabel("Tipo de contenido:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        String[] types = {"Artículo", "Video", "Imagen"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(typeCombo, gbc);
        
        JLabel titleLabel = new JLabel("Título:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField titleField = new JTextField(30);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(titleField, gbc);
        
        JPanel dynamicPanel = new JPanel(new GridBagLayout());
        
        JLabel bodyLabel = new JLabel("Cuerpo:");
        JTextArea bodyArea = new JTextArea(10, 30);
        JScrollPane bodyScroll = new JScrollPane(bodyArea);
        
        JLabel durationLabel = new JLabel("Duración (seg):");
        JTextField durationField = new JTextField(10);
        JLabel urlLabel = new JLabel("URL:");
        JTextField urlField = new JTextField(30);
        
        JLabel resolutionLabel = new JLabel("Resolución:");
        JTextField resolutionField = new JTextField(15);
        JLabel sizeLabel = new JLabel("Tamaño (MB):");
        JTextField sizeField = new JTextField(10);
        
        updateDynamicPanel(dynamicPanel, typeCombo.getSelectedIndex(),
            bodyLabel, bodyScroll, durationLabel, durationField, urlLabel, urlField,
            resolutionLabel, resolutionField, sizeLabel, sizeField);
        
        typeCombo.addActionListener(e -> {
            updateDynamicPanel(dynamicPanel, typeCombo.getSelectedIndex(),
                bodyLabel, bodyScroll, durationLabel, durationField, urlLabel, urlField,
                resolutionLabel, resolutionField, sizeLabel, sizeField);
            panel.revalidate();
            panel.repaint();
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panel.add(dynamicPanel, gbc);
        
        JButton createButton = new JButton("Crear Contenido");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(new Color(76, 175, 80));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        
        createButton.addActionListener(e -> {
            createContent(typeCombo.getSelectedIndex(), titleField.getText(),
                bodyArea.getText(), durationField.getText(), urlField.getText(),
                resolutionField.getText(), sizeField.getText());
        });
        
        gbc.gridy = 3; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(createButton, gbc);
        
        return panel;
    }
    
    private void updateDynamicPanel(JPanel panel, int type, JLabel bodyLabel, JScrollPane bodyScroll,
            JLabel durationLabel, JTextField durationField, JLabel urlLabel, JTextField urlField,
            JLabel resolutionLabel, JTextField resolutionField, JLabel sizeLabel, JTextField sizeField) {
        
        panel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        if (type == 0) {
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(bodyLabel, gbc);
            gbc.gridy = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            panel.add(bodyScroll, gbc);
        } else if (type == 1) {
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(durationLabel, gbc);
            gbc.gridx = 1;
            panel.add(durationField, gbc);
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(urlLabel, gbc);
            gbc.gridx = 1;
            panel.add(urlField, gbc);
        } else {
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(resolutionLabel, gbc);
            gbc.gridx = 1;
            panel.add(resolutionField, gbc);
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(sizeLabel, gbc);
            gbc.gridx = 1;
            panel.add(sizeField, gbc);
        }
    }
    
    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextArea categoryArea = new JTextArea();
        categoryArea.setEditable(false);
        categoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(categoryArea);
        
        JButton refreshButton = new JButton("Actualizar Categorías");
        styleButton(refreshButton);
        
        refreshButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            List<Categoria> cats = contenidoController.getCategorias();
            sb.append("CATEGORÍAS DISPONIBLES\n");
            sb.append("═══════════════════════════════════════\n\n");
            for (Categoria cat : cats) {
                sb.append("📁 ").append(cat.getNombre()).append("\n");
                sb.append("   ").append(cat.getDescripcion()).append("\n");
                sb.append("   Contenidos: ").append(cat.getContenidos().size()).append("\n\n");
            }
            categoryArea.setText(sb.toString());
        });
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton generalButton = new JButton("Reporte General");
        JButton articlesButton = new JButton("Artículos");
        JButton videosButton = new JButton("Videos");
        JButton imagesButton = new JButton("Imágenes");
        JButton authorButton = new JButton("Mi Contenido");
        
        styleButton(generalButton);
        styleButton(articlesButton);
        styleButton(videosButton);
        styleButton(imagesButton);
        styleButton(authorButton);
        
        generalButton.addActionListener(e -> {
            reporteController = new ReporteController(contenidoController.getContenidos());
            reportArea.setText(reporteController.generarReporteGeneral());
        });
        
        articlesButton.addActionListener(e -> {
            reporteController = new ReporteController(contenidoController.getContenidos());
            reportArea.setText(reporteController.generarReportePorTipo("ARTICULO"));
        });
        
        videosButton.addActionListener(e -> {
            reporteController = new ReporteController(contenidoController.getContenidos());
            reportArea.setText(reporteController.generarReportePorTipo("VIDEO"));
        });
        
        imagesButton.addActionListener(e -> {
            reporteController = new ReporteController(contenidoController.getContenidos());
            reportArea.setText(reporteController.generarReportePorTipo("IMAGEN"));
        });
        
        authorButton.addActionListener(e -> {
            reporteController = new ReporteController(contenidoController.getContenidos());
            reportArea.setText(reporteController.generarReportePorAutor(usuarioController.obtenerUsuarioActual()));
        });
        
        buttonPanel.add(generalButton);
        buttonPanel.add(articlesButton);
        buttonPanel.add(videosButton);
        buttonPanel.add(imagesButton);
        buttonPanel.add(authorButton);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextArea usersArea = new JTextArea();
        usersArea.setEditable(false);
        usersArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(usersArea);
        
        JButton refreshButton = new JButton("Ver Usuarios");
        styleButton(refreshButton);
        
        refreshButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            List<Usuario> users = usuarioController.getUsuarios();
            sb.append("USUARIOS DEL SISTEMA\n");
            sb.append("═══════════════════════════════════════\n\n");
            for (Usuario u : users) {
                String tipo = u instanceof Administrador ? "Administrador" : "Editor";
                sb.append(tipo).append("\n");
                sb.append("ID: ").append(u.getId()).append("\n");
                sb.append("Nombre: ").append(u.getNombre()).append("\n");
                sb.append("Email: ").append(u.getEmail()).append("\n\n");
            }
            usersArea.setText(sb.toString());
        });
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void loadContent() {
        tableModel.setRowCount(0);
        List<Contenido> contenidos = contenidoController.getContenidos();
        
        for (Contenido c : contenidos) {
            String tipo = c instanceof Articulo ? "Artículo" : 
                         c instanceof Video ? "Video" : "Imagen";
            String estado = c.estaPublicado() ? "Publicado" : "Borrador";
            
            tableModel.addRow(new Object[]{
                c.getId(),
                tipo,
                c.getTitulo(),
                c.getAutor().getNombre(),
                c.getFechaCreacion().toString(),
                estado
            });
        }
    }
    
    private void viewContentDetails() {
        int row = contentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        Contenido c = contenidoController.buscarPorId(id);
        
        if (c != null) {
            JOptionPane.showMessageDialog(this, c.visualizar(), 
                "Detalles del Contenido", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void editContent() {
        int row = contentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        Contenido c = contenidoController.buscarPorId(id);
        
        if (c != null) {
            String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo título:", c.getTitulo());
            if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
                c.setTitulo(nuevoTitulo);
                JOptionPane.showMessageDialog(this, "Contenido editado exitosamente");
                loadContent();
            }
        }
    }
    
    private void deleteContent() {
        int row = contentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este contenido?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (contenidoController.eliminarContenido(id, usuarioController.obtenerUsuarioActual())) {
                JOptionPane.showMessageDialog(this, "Contenido eliminado");
                loadContent();
            }
        }
    }
    
    private void togglePublish() {
        int row = contentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        Contenido c = contenidoController.buscarPorId(id);
        
        if (c != null) {
            if (c.estaPublicado()) {
                c.despublicar();
            } else {
                c.publicar();
            }
            loadContent();
        }
    }
    
    private void createContent(int type, String titulo, String body, String duration, 
            String url, String resolution, String size) {
        
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio");
            return;
        }
        
        Usuario autor = usuarioController.obtenerUsuarioActual();
        
        try {
            if (type == 0) {
                contenidoController.crearArticulo(titulo, autor, body);
            } else if (type == 1) {
                int dur = Integer.parseInt(duration);
                contenidoController.crearVideo(titulo, autor, dur, url);
            } else {
                double tam = Double.parseDouble(size);
                contenidoController.crearImagen(titulo, autor, resolution, tam);
            }
            
            JOptionPane.showMessageDialog(this, "Contenido creado exitosamente");
            loadContent();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en los datos numéricos");
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            usuarioController.logout();
            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        }
    }
}