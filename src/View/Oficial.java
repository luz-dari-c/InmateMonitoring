/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.PresoDAO;
import Informacion.ExpedientePreso;
import Informacion.HistorialDeSanciones;
import Informacion.HistorialMedicoPreso;
import Model.Preso;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import DAO.OficialDAO;

public class Oficial extends javax.swing.JFrame {

    public Oficial() {
        initComponents();
        InicializarMenu();
        configurarTablaImagenes();
        cargarDatosEnTabla();
    }

    public void InicializarMenu() {
        JMenuItem historialVisita = new JMenuItem("Historial de visitas");
        JMenuItem historialMedicoPreso = new JMenuItem("Historial Medico");
        JMenuItem historialDeSanciones = new JMenuItem("Historial de sanciones");
        JMenuItem informacionPreso = new JMenuItem("Informacion preso");
        JMenuItem expediente = new JMenuItem("Expediente");

        ppMenuPreso.add(historialVisita);
        ppMenuPreso.add(historialMedicoPreso);
        ppMenuPreso.add(historialDeSanciones);
        ppMenuPreso.add(informacionPreso);
        ppMenuPreso.add(expediente);

        TablaPresosGuardia.setComponentPopupMenu(ppMenuPreso);

        expediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExpedientePreso exp = new ExpedientePreso();
                exp.setSize(800, 600);
                exp.setVisible(true);

                Oficial.this.setContentPane(exp);
                Oficial.this.revalidate();
                Oficial.this.repaint();

            }

        });

        informacionPreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformacionPreso inf = new InformacionPreso();
                inf.setSize(800, 600);
                inf.setVisible(true);

                Oficial.this.setContentPane(inf);
                Oficial.this.revalidate();
                Oficial.this.repaint();

            }

        });

        historialDeSanciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialDeSanciones hisS = new HistorialDeSanciones();
                hisS.setSize(800, 600);
                hisS.setVisible(true);

                Oficial.this.setContentPane(hisS);
                Oficial.this.revalidate();
                Oficial.this.repaint();

            }

        });

        historialMedicoPreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialMedicoPreso hisM = new HistorialMedicoPreso();
                hisM.setSize(800, 600);
                hisM.setVisible(true);

                Oficial.this.setContentPane(hisM);
                Oficial.this.revalidate();
                Oficial.this.repaint();

            }

        });

        historialVisita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaPresosGuardia.getSelectedRow();

                if (filaSeleccionada != -1) {
                    String identificacionPreso = TablaPresosGuardia.getValueAt(filaSeleccionada, 5).toString();

                    HistorialVisitasPreso hvp = new HistorialVisitasPreso(identificacionPreso);
                    hvp.setSize(800, 600);
                    hvp.setVisible(true);

                    Oficial.this.setContentPane(hvp);
                    Oficial.this.revalidate();
                    Oficial.this.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un preso para ver su historial de visitas.");
                }
            }
        });

    }

    private void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaPresosGuardia.getModel();
        modelo.setRowCount(0);

        PresoDAO presoDAO = new PresoDAO();
        List<Preso> presos = presoDAO.cargarTodos();

        for (Preso preso : presos) {
            ImageIcon foto = null;
            if (preso.getFotoPath() != null && !preso.getFotoPath().isEmpty()) {
                foto = cargarImagenPreso(preso.getFotoPath());
            } else {
                foto = new ImageIcon(getClass().getResource("/images/default_profile.png"));
            }

            modelo.addRow(new Object[]{
                foto,
                preso.getId(),
                preso.getNombre(),
                preso.getApellido(),
                preso.getEdad(),
                preso.getIdentificacion(),
                preso.getNacionalidad(),
                preso.getCeldaAsignada()
            });
        }

        TablaPresosGuardia.revalidate();
        TablaPresosGuardia.repaint();
    }

    private ImageIcon cargarImagenPreso(String path) {
        if (path == null || !new File(path).exists()) {
            return null;
        }

        try {
            Image img = ImageIO.read(new File(path));
            return new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return null;
        }
    }

    private void configurarTablaImagenes() {
        TablaPresosGuardia.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (column == 0 && value instanceof ImageIcon) {
                    ImageIcon originalIcon = (ImageIcon) value;
                    Image img = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    ImageIcon roundedIcon = new ImageIcon(createRoundedImage(img));
                    label.setIcon(roundedIcon);
                    label.setText("");
                } else {
                    label.setIcon(null);
                }
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        TablaPresosGuardia.setRowHeight(65);
        TablaPresosGuardia.getColumnModel().getColumn(0).setPreferredWidth(70);
    }

    private Image createRoundedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        output = g2.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, width, height, 20, 20);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return output;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ppMenuPreso = new javax.swing.JPopupMenu();
        jPanel5 = new javax.swing.JPanel();
        PanelPerfilTab = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        PanelPresosTab = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        PanelCitaTab = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        PanelGuardiasTab = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        OficialTabbed = new javax.swing.JTabbedPane();
        PanelPerfilOficial = new javax.swing.JPanel();
        jPanel2 = new RoundedPanel(30);
        LabelFotoOficial = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        TurnoOficial = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        FechaDeIngresoOficial = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel4 = new RoundedPanel(30);
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        IdentificacionOficial = new javax.swing.JLabel();
        NombreOficial = new javax.swing.JLabel();
        ApellidoOficial = new javax.swing.JLabel();
        EdadOficial = new javax.swing.JLabel();
        PlacaOficial = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        SexoOficial = new javax.swing.JLabel();
        NacionalidadOficial = new javax.swing.JLabel();
        BotonParaLlevarPanelActualizar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        PanelListaDePresosGuardia = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPresosGuardia = new javax.swing.JTable();
        BarraDeBusquedaPreso = new javax.swing.JTextField();
        BuscarPresoPorId = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        PanelAgendarCita = new javax.swing.JPanel();
        FechaCita = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        IdentificacionDelPreso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        IdentificacionDelGuardia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MotivoCita = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        IdentificacionDelPreso2 = new javax.swing.JTextField();
        PanelListaDeGuardias = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaGuardias = new javax.swing.JTable();
        PanelActualizarInformacion = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
        jSeparator29 = new javax.swing.JSeparator();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jSeparator33 = new javax.swing.JSeparator();
        BotonActualizarInformacion = new javax.swing.JButton();
        NuevoCorreoGuardia = new javax.swing.JTextField();
        ContraseñaGuardia = new javax.swing.JTextField();
        NuevoNombreGuardia = new javax.swing.JTextField();
        NuevoApellidoGuardia = new javax.swing.JTextField();
        NuevaIdentificacionGuardia = new javax.swing.JTextField();
        NuevaEdadGuardia = new javax.swing.JTextField();
        NuevaNacionalidadGuardia = new javax.swing.JTextField();
        jSeparator34 = new javax.swing.JSeparator();
        NuevaContraseñaGuardia = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        VistaPreviaFotoPerfilGuardia = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        SubirNuevaFotoPerfil = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(29, 35, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelPerfilTab.setBackground(new java.awt.Color(29, 35, 51));
        PanelPerfilTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPerfilTabMouseClicked(evt);
            }
        });
        PanelPerfilTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("PERFIL");
        PanelPerfilTab.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jPanel5.add(PanelPerfilTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 60));

        PanelPresosTab.setBackground(new java.awt.Color(29, 35, 51));
        PanelPresosTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPresosTabMouseClicked(evt);
            }
        });
        PanelPresosTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("PRESOS");
        PanelPresosTab.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jPanel5.add(PanelPresosTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 280, 60));

        PanelCitaTab.setBackground(new java.awt.Color(29, 35, 51));
        PanelCitaTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelCitaTabMouseClicked(evt);
            }
        });
        PanelCitaTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("AGENDAR CITA");
        PanelCitaTab.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel5.add(PanelCitaTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 250, 60));

        PanelGuardiasTab.setBackground(new java.awt.Color(29, 35, 51));
        PanelGuardiasTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelGuardiasTabMouseClicked(evt);
            }
        });
        PanelGuardiasTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("GUARDIAS");
        PanelGuardiasTab.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jPanel5.add(PanelGuardiasTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, 270, 60));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        PanelPerfilOficial.setBackground(new java.awt.Color(255, 255, 255));
        PanelPerfilOficial.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(180, 180, 195));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(LabelFotoOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, 180));

        jLabel4.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Oficial");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 50, 20));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Fecha Ingreso");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, -1, -1));

        jButton1.setText("Cerrar sesión");
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 110, 30));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 380, 10));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 210, 10));
        jPanel2.add(TurnoOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 210, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Cargo");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, -1, -1));

        jLabel32.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Turno");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, -1, -1));

        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 210, 10));
        jPanel2.add(FechaDeIngresoOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 210, 30));

        PanelPerfilOficial.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 290, 490));

        jPanel3.setBackground(new java.awt.Color(139, 139, 157));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("INFORMACIÓN PERSONAL");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, 30));

        PanelPerfilOficial.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 370, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 370, 10));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 370, 10));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 370, 10));

        jPanel4.setBackground(new java.awt.Color(180, 180, 195));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nombre:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Apellido:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Identificación:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Edad:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nacionalidad:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 370, 10));
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 310, -1));
        jPanel4.add(IdentificacionOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 260, 30));
        jPanel4.add(NombreOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 16, 260, 30));
        jPanel4.add(ApellidoOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 260, 30));
        jPanel4.add(EdadOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 260, 30));
        jPanel4.add(PlacaOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 260, 30));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 370, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Sexo:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Placa:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 370, 10));
        jPanel4.add(SexoOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 260, 30));
        jPanel4.add(NacionalidadOficial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 260, 30));

        BotonParaLlevarPanelActualizar.setText("Actualizar información");
        BotonParaLlevarPanelActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonParaLlevarPanelActualizarMouseClicked(evt);
            }
        });
        BotonParaLlevarPanelActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonParaLlevarPanelActualizarActionPerformed(evt);
            }
        });
        jPanel4.add(BotonParaLlevarPanelActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 170, 30));

        PanelPerfilOficial.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 470, 360));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, 370, 10));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilOficial.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 370, 10));

        OficialTabbed.addTab("PERFIL", PanelPerfilOficial);

        PanelListaDePresosGuardia.setBackground(new java.awt.Color(255, 255, 255));
        PanelListaDePresosGuardia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPresosGuardia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPresosGuardia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPresosGuardiaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaPresosGuardia);

        PanelListaDePresosGuardia.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 87, 940, 400));
        PanelListaDePresosGuardia.add(BarraDeBusquedaPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 620, 30));

        BuscarPresoPorId.setText("Buscar");
        BuscarPresoPorId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPresoPorIdActionPerformed(evt);
            }
        });
        PanelListaDePresosGuardia.add(BuscarPresoPorId, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, -1, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seccion A", "Seccion B", "Seccion C" }));
        PanelListaDePresosGuardia.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 90, 30));

        OficialTabbed.addTab("LISTA DE PRESOS", PanelListaDePresosGuardia);

        PanelAgendarCita.setBackground(new java.awt.Color(255, 255, 255));
        PanelAgendarCita.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FechaCita.setBackground(new java.awt.Color(180, 180, 195));
        FechaCita.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Motivo");
        FechaCita.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, 20));
        FechaCita.add(IdentificacionDelPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 390, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Identificacion guardia asignado:");
        FechaCita.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));
        FechaCita.add(IdentificacionDelGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 390, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Fecha de la cita:");
        FechaCita.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        MotivoCita.setColumns(20);
        MotivoCita.setRows(5);
        jScrollPane1.setViewportView(MotivoCita);

        FechaCita.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 390, 100));

        jButton2.setText("Solicitar cita");
        FechaCita.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, -1, -1));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Identificación del preso");
        FechaCita.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));
        FechaCita.add(IdentificacionDelPreso2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 390, 30));

        PanelAgendarCita.add(FechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 490, 430));

        OficialTabbed.addTab("SOLICITAR CITA", PanelAgendarCita);

        PanelListaDeGuardias.setBackground(new java.awt.Color(255, 255, 255));
        PanelListaDeGuardias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaGuardias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaGuardias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaGuardiasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaGuardias);

        PanelListaDeGuardias.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 940, 420));

        OficialTabbed.addTab("LISTA DE GUARDIAS", PanelListaDeGuardias);

        PanelActualizarInformacion.setBackground(new java.awt.Color(255, 255, 255));
        PanelActualizarInformacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(180, 180, 195));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Nueva contraseña:");
        jPanel7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Apellido:");
        jPanel7.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Identificación:");
        jPanel7.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Edad:");
        jPanel7.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Sexo:");
        jPanel7.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 390, 10));

        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 390, 10));

        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 390, 10));

        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 390, 10));

        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 390, 10));

        jLabel39.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Nombre:");
        jPanel7.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 390, 10));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Correo electronico:");
        jPanel7.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 390, 10));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Nacionalidad:");
        jPanel7.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Contraseña*:");
        jPanel7.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jSeparator33.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 390, 10));

        BotonActualizarInformacion.setText("Actualizar infotmación");
        BotonActualizarInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonActualizarInformacionActionPerformed(evt);
            }
        });
        jPanel7.add(BotonActualizarInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, 170, 30));

        NuevoCorreoGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevoCorreoGuardia.setBorder(null);
        NuevoCorreoGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoCorreoGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(NuevoCorreoGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 250, 30));

        ContraseñaGuardia.setBackground(new java.awt.Color(180, 180, 195));
        ContraseñaGuardia.setBorder(null);
        ContraseñaGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContraseñaGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(ContraseñaGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 290, 30));

        NuevoNombreGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevoNombreGuardia.setBorder(null);
        jPanel7.add(NuevoNombreGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 320, 30));

        NuevoApellidoGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevoApellidoGuardia.setBorder(null);
        NuevoApellidoGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoApellidoGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(NuevoApellidoGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 320, 30));

        NuevaIdentificacionGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevaIdentificacionGuardia.setBorder(null);
        jPanel7.add(NuevaIdentificacionGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 280, 30));

        NuevaEdadGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevaEdadGuardia.setBorder(null);
        NuevaEdadGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaEdadGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaEdadGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 340, 30));

        NuevaNacionalidadGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevaNacionalidadGuardia.setBorder(null);
        NuevaNacionalidadGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaNacionalidadGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaNacionalidadGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 290, 30));

        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 390, 10));

        NuevaContraseñaGuardia.setBackground(new java.awt.Color(180, 180, 195));
        NuevaContraseñaGuardia.setBorder(null);
        NuevaContraseñaGuardia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaContraseñaGuardiaActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaContraseñaGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 250, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "Femenino", "Masculino" }));
        jPanel7.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 352, 330, 30));

        PanelActualizarInformacion.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 520, 450));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(VistaPreviaFotoPerfilGuardia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 250));

        PanelActualizarInformacion.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 220, 270));

        jLabel44.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Vista previa foto de perfil");
        PanelActualizarInformacion.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, -1, -1));

        SubirNuevaFotoPerfil.setText("Subir foto de perfil");
        SubirNuevaFotoPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubirNuevaFotoPerfilActionPerformed(evt);
            }
        });
        PanelActualizarInformacion.add(SubirNuevaFotoPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 450, 140, 30));

        jPanel10.setBackground(new java.awt.Color(139, 139, 157));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelActualizarInformacion.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 90));

        OficialTabbed.addTab("ACTUALIZAR INFORMACION", PanelActualizarInformacion);

        getContentPane().add(OficialTabbed, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1050, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonParaLlevarPanelActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonParaLlevarPanelActualizarActionPerformed

    }//GEN-LAST:event_BotonParaLlevarPanelActualizarActionPerformed

    private void TablaPresosGuardiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPresosGuardiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaPresosGuardiaMouseClicked

    private void BuscarPresoPorIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPresoPorIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPresoPorIdActionPerformed

    private void PanelPerfilTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilTabMouseClicked
        OficialTabbed.setSelectedIndex(0);
    }//GEN-LAST:event_PanelPerfilTabMouseClicked

    private void PanelPresosTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosTabMouseClicked
        OficialTabbed.setSelectedIndex(1);
    }//GEN-LAST:event_PanelPresosTabMouseClicked

    private void PanelCitaTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelCitaTabMouseClicked
        OficialTabbed.setSelectedIndex(2);
    }//GEN-LAST:event_PanelCitaTabMouseClicked

    private void PanelGuardiasTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardiasTabMouseClicked
        OficialTabbed.setSelectedIndex(3);
    }//GEN-LAST:event_PanelGuardiasTabMouseClicked

    private void TablaGuardiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaGuardiasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaGuardiasMouseClicked

    private void BotonActualizarInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonActualizarInformacionActionPerformed

    }//GEN-LAST:event_BotonActualizarInformacionActionPerformed

    private void NuevoCorreoGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoCorreoGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoCorreoGuardiaActionPerformed

    private void ContraseñaGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContraseñaGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ContraseñaGuardiaActionPerformed

    private void NuevoApellidoGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoApellidoGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoApellidoGuardiaActionPerformed

    private void NuevaEdadGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaEdadGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaEdadGuardiaActionPerformed

    private void NuevaNacionalidadGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaNacionalidadGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaNacionalidadGuardiaActionPerformed

    private void NuevaContraseñaGuardiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaContraseñaGuardiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaContraseñaGuardiaActionPerformed

    private void SubirNuevaFotoPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubirNuevaFotoPerfilActionPerformed
        SubirNuevaFotoPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);

                int resultado = fileChooser.showOpenDialog(null);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File selectedImageFile = fileChooser.getSelectedFile();

                    try {
                        BufferedImage originalImage = ImageIO.read(selectedImageFile);

                        ImageIcon icon = new ImageIcon(originalImage);
                        Image img = icon.getImage();
                        Image imgEscalada = img.getScaledInstance(
                                VistaPreviaFotoPerfilGuardia.getWidth(),
                                VistaPreviaFotoPerfilGuardia.getHeight(),
                                Image.SCALE_SMOOTH);

                        VistaPreviaFotoPerfilGuardia.setIcon(new ImageIcon(imgEscalada));

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Error al cargar la imagen: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }//GEN-LAST:event_SubirNuevaFotoPerfilActionPerformed

    private void BotonParaLlevarPanelActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonParaLlevarPanelActualizarMouseClicked
        OficialTabbed.setSelectedIndex(4);
    }//GEN-LAST:event_BotonParaLlevarPanelActualizarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Oficial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Oficial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Oficial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Oficial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Oficial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ApellidoOficial;
    private javax.swing.JTextField BarraDeBusquedaPreso;
    private javax.swing.JButton BotonActualizarInformacion;
    private javax.swing.JButton BotonParaLlevarPanelActualizar;
    private javax.swing.JButton BuscarPresoPorId;
    private javax.swing.JTextField ContraseñaGuardia;
    private javax.swing.JLabel EdadOficial;
    private javax.swing.JPanel FechaCita;
    private javax.swing.JLabel FechaDeIngresoOficial;
    private javax.swing.JTextField IdentificacionDelGuardia;
    private javax.swing.JTextField IdentificacionDelPreso;
    private javax.swing.JTextField IdentificacionDelPreso2;
    private javax.swing.JLabel IdentificacionOficial;
    private javax.swing.JLabel LabelFotoOficial;
    private javax.swing.JTextArea MotivoCita;
    private javax.swing.JLabel NacionalidadOficial;
    private javax.swing.JLabel NombreOficial;
    private javax.swing.JTextField NuevaContraseñaGuardia;
    private javax.swing.JTextField NuevaEdadGuardia;
    private javax.swing.JTextField NuevaIdentificacionGuardia;
    private javax.swing.JTextField NuevaNacionalidadGuardia;
    private javax.swing.JTextField NuevoApellidoGuardia;
    private javax.swing.JTextField NuevoCorreoGuardia;
    private javax.swing.JTextField NuevoNombreGuardia;
    private javax.swing.JTabbedPane OficialTabbed;
    private javax.swing.JPanel PanelActualizarInformacion;
    private javax.swing.JPanel PanelAgendarCita;
    private javax.swing.JPanel PanelCitaTab;
    private javax.swing.JPanel PanelGuardiasTab;
    private javax.swing.JPanel PanelListaDeGuardias;
    private javax.swing.JPanel PanelListaDePresosGuardia;
    private javax.swing.JPanel PanelPerfilOficial;
    private javax.swing.JPanel PanelPerfilTab;
    private javax.swing.JPanel PanelPresosTab;
    private javax.swing.JLabel PlacaOficial;
    private javax.swing.JLabel SexoOficial;
    private javax.swing.JButton SubirNuevaFotoPerfil;
    private javax.swing.JTable TablaGuardias;
    private javax.swing.JTable TablaPresosGuardia;
    private javax.swing.JLabel TurnoOficial;
    private javax.swing.JLabel VistaPreviaFotoPerfilGuardia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPopupMenu ppMenuPreso;
    // End of variables declaration//GEN-END:variables
}
