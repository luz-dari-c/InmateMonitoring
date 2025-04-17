/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.PresoDAO;
import DAO.VisitaDAO;
import DAO.VisitanteDAO;
import Informacion.ExpedientePreso;
import Informacion.HistorialDeSanciones;
import Informacion.HistorialMedicoPreso;
import Model.Preso;

import Model.Visita;
import Model.Visitante;
import java.awt.AlphaComposite;

import java.awt.Color;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

/**
 *
 * @author ASUS
 */
public class PersonalDeControl extends javax.swing.JFrame {

    private List<Visitante> visitantesTemporales = new ArrayList<>();
    private File imagenVisitanteSeleccionada;
    private List<File> imagenesTemporales = new ArrayList<>();
    int cantidadTotal;

    /**
     * Creates new form PersonalDeControl
     */
    public PersonalDeControl() {
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

        ppMenuTablaPresosPDC.add(historialVisita);
        ppMenuTablaPresosPDC.add(historialMedicoPreso);
        ppMenuTablaPresosPDC.add(historialDeSanciones);
        ppMenuTablaPresosPDC.add(informacionPreso);
        ppMenuTablaPresosPDC.add(expediente);

        TablaPresosPDC.setComponentPopupMenu(ppMenuTablaPresosPDC);

        expediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExpedientePreso exp = new ExpedientePreso();
                exp.setSize(800, 600);
                exp.setVisible(true);

                PersonalDeControl.this.setContentPane(exp);
                PersonalDeControl.this.revalidate();
                PersonalDeControl.this.repaint();

            }

        });

        informacionPreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformacionPreso inf = new InformacionPreso();
                inf.setSize(800, 600);
                inf.setVisible(true);

                PersonalDeControl.this.setContentPane(inf);
                PersonalDeControl.this.revalidate();
                PersonalDeControl.this.repaint();

            }

        });

        historialDeSanciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialDeSanciones hisS = new HistorialDeSanciones();
                hisS.setSize(800, 600);
                hisS.setVisible(true);

                PersonalDeControl.this.setContentPane(hisS);
                PersonalDeControl.this.revalidate();
                PersonalDeControl.this.repaint();

            }

        });

        historialMedicoPreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialMedicoPreso hisM = new HistorialMedicoPreso();
                hisM.setSize(800, 600);
                hisM.setVisible(true);

                PersonalDeControl.this.setContentPane(hisM);
                PersonalDeControl.this.revalidate();
                PersonalDeControl.this.repaint();

            }

        });

        historialVisita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaPresosPDC.getSelectedRow();

                if (filaSeleccionada != -1) {
                    String identificacionPreso = TablaPresosPDC.getValueAt(filaSeleccionada, 5).toString();

                    HistorialVisitasPreso hvp = new HistorialVisitasPreso(identificacionPreso);
                    hvp.setSize(800, 600);
                    hvp.setVisible(true);

                    PersonalDeControl.this.setContentPane(hvp);
                    PersonalDeControl.this.revalidate();
                    PersonalDeControl.this.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un preso para ver su historial de visitas.");
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ppMenuTablaPresosPDC = new javax.swing.JPopupMenu();
        PanelBotones = new javax.swing.JPanel();
        PresosPDC = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PerfilPDC = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        VisitasPDC = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        PersonalDeControlView = new javax.swing.JTabbedPane();
        PanelPerfilPDC = new javax.swing.JPanel();
        jPanel2 = new RoundedPanel(30);
        LabelFotoPDC = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        TurnoPDC = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        FechaDeIngresoPDC = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel4 = new RoundedPanel(30);
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        IdentificacionPDC = new javax.swing.JLabel();
        Nombre2PDC1 = new javax.swing.JLabel();
        NacionalidadPDC = new javax.swing.JLabel();
        ApellidoPDC2 = new javax.swing.JLabel();
        EdadPDC = new javax.swing.JLabel();
        SexoPDC1 = new javax.swing.JLabel();
        BotonParaLlevarPanelActualizar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
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
        NuevoCorreoPDC = new javax.swing.JTextField();
        ContraseñaPDC = new javax.swing.JTextField();
        NuevoNombrePDC = new javax.swing.JTextField();
        NuevoApellidoPDC = new javax.swing.JTextField();
        NuevaIdentificacionPDC = new javax.swing.JTextField();
        NuevaEdadPDC = new javax.swing.JTextField();
        NuevaNacionalidadPDC = new javax.swing.JTextField();
        jSeparator34 = new javax.swing.JSeparator();
        NuevaContraseñaPDC = new javax.swing.JTextField();
        SubirNuevaFotoPerfil = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        VistaPreviaFotoPerfilPDC = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        PanelListaPresosPDC = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPresosPDC = new javax.swing.JTable();
        BarraDeBusquedaPresoPDC = new javax.swing.JTextField();
        BuscarPresoPorId = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        PanelAñadirVisitaPDC = new javax.swing.JPanel();
        PanelAñadirVisitante = new RoundedPanel(30);
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        NombreVisitante = new javax.swing.JTextField();
        EdadVisitante = new javax.swing.JTextField();
        ApellidoVisitante = new javax.swing.JTextField();
        IdentificacionVisitante = new javax.swing.JTextField();
        NacionalidadVisitante = new javax.swing.JTextField();
        BotonAñadirVisitante = new javax.swing.JButton();
        SexoVisitante = new javax.swing.JComboBox<>();
        RelacionConPreso = new javax.swing.JComboBox<>();
        RequiereSupervision = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(30);
        jLabel27 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        IdentificacionPresoVisita = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        FechaVisita = new javax.swing.JTextField();
        HoraVisita = new javax.swing.JTextField();
        DuracionVisita = new javax.swing.JTextField();
        CantidadDeVisitantesCombo = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        BotonAñadirVisita = new javax.swing.JButton();
        AgregarImagenVisitante = new javax.swing.JButton();
        TipoVisita = new javax.swing.JComboBox<>();
        LugarVisita = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        VistaPreviaVisitante = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelBotones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PresosPDC.setBackground(new java.awt.Color(29, 35, 51));
        PresosPDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PresosPDCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PresosPDCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PresosPDCMouseExited(evt);
            }
        });
        PresosPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PRESOS");
        PresosPDC.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        PanelBotones.add(PresosPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 340, 60));

        PerfilPDC.setBackground(new java.awt.Color(29, 35, 51));
        PerfilPDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PerfilPDCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PerfilPDCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PerfilPDCMouseExited(evt);
            }
        });
        PerfilPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PERFIL");
        PerfilPDC.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        PanelBotones.add(PerfilPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 60));

        VisitasPDC.setBackground(new java.awt.Color(29, 35, 51));
        VisitasPDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VisitasPDCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                VisitasPDCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                VisitasPDCMouseExited(evt);
            }
        });
        VisitasPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑADIR VISITA");
        VisitasPDC.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        PanelBotones.add(VisitasPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 360, 60));

        getContentPane().add(PanelBotones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        PanelPerfilPDC.setBackground(new java.awt.Color(255, 255, 255));
        PanelPerfilPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(180, 180, 195));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(LabelFotoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, 180));

        jLabel4.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Personal de control");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 140, 20));

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
        jPanel2.add(TurnoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 210, 30));

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
        jPanel2.add(FechaDeIngresoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 210, 30));

        PanelPerfilPDC.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 290, 490));

        jPanel3.setBackground(new java.awt.Color(139, 139, 157));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelPerfilPDC.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 70));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("INFORMACIÓN PERSONAL");
        PanelPerfilPDC.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, -1, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 370, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, 370, 10));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 280, 370, 10));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 370, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 370, 10));

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

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Sexo:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nacionalidad:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 370, 10));
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 310, -1));
        jPanel4.add(IdentificacionPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 260, 30));
        jPanel4.add(Nombre2PDC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 16, 260, 30));
        jPanel4.add(NacionalidadPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 260, 30));
        jPanel4.add(ApellidoPDC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 260, 30));
        jPanel4.add(EdadPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 260, 30));
        jPanel4.add(SexoPDC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 260, 30));

        PanelPerfilPDC.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 470, 290));

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
        PanelPerfilPDC.add(BotonParaLlevarPanelActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, 170, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilPDC.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, 370, 10));

        PersonalDeControlView.addTab("Perfil", PanelPerfilPDC);

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

        BotonActualizarInformacion.setText("Actualizar información");
        BotonActualizarInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonActualizarInformacionActionPerformed(evt);
            }
        });
        jPanel7.add(BotonActualizarInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, 170, 30));

        NuevoCorreoPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevoCorreoPDC.setBorder(null);
        NuevoCorreoPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoCorreoPDCActionPerformed(evt);
            }
        });
        jPanel7.add(NuevoCorreoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 250, 30));

        ContraseñaPDC.setBackground(new java.awt.Color(180, 180, 195));
        ContraseñaPDC.setBorder(null);
        ContraseñaPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContraseñaPDCActionPerformed(evt);
            }
        });
        jPanel7.add(ContraseñaPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 290, 30));

        NuevoNombrePDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevoNombrePDC.setBorder(null);
        jPanel7.add(NuevoNombrePDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 320, 30));

        NuevoApellidoPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevoApellidoPDC.setBorder(null);
        NuevoApellidoPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoApellidoPDCActionPerformed(evt);
            }
        });
        jPanel7.add(NuevoApellidoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 320, 30));

        NuevaIdentificacionPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevaIdentificacionPDC.setBorder(null);
        jPanel7.add(NuevaIdentificacionPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 280, 30));

        NuevaEdadPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevaEdadPDC.setBorder(null);
        NuevaEdadPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaEdadPDCActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaEdadPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 340, 30));

        NuevaNacionalidadPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevaNacionalidadPDC.setBorder(null);
        NuevaNacionalidadPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaNacionalidadPDCActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaNacionalidadPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 290, 30));

        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 390, 10));

        NuevaContraseñaPDC.setBackground(new java.awt.Color(180, 180, 195));
        NuevaContraseñaPDC.setBorder(null);
        NuevaContraseñaPDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaContraseñaPDCActionPerformed(evt);
            }
        });
        jPanel7.add(NuevaContraseñaPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 250, 30));

        PanelActualizarInformacion.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 520, 450));

        SubirNuevaFotoPerfil.setText("Subir foto de perfil");
        SubirNuevaFotoPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubirNuevaFotoPerfilActionPerformed(evt);
            }
        });
        PanelActualizarInformacion.add(SubirNuevaFotoPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 450, 140, 30));

        jLabel43.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Vista previa foto de perfil");
        PanelActualizarInformacion.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(VistaPreviaFotoPerfilPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 250));

        PanelActualizarInformacion.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 220, 270));

        jPanel9.setBackground(new java.awt.Color(139, 139, 157));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelActualizarInformacion.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 90));

        PersonalDeControlView.addTab("tab4", PanelActualizarInformacion);

        PanelListaPresosPDC.setBackground(new java.awt.Color(255, 255, 255));
        PanelListaPresosPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPresosPDC.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaPresosPDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPresosPDCMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaPresosPDC);
        if (TablaPresosPDC.getColumnModel().getColumnCount() > 0) {
            TablaPresosPDC.getColumnModel().getColumn(0).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(1).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(2).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(3).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(4).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(5).setResizable(false);
            TablaPresosPDC.getColumnModel().getColumn(6).setResizable(false);
        }

        PanelListaPresosPDC.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 87, 940, 400));
        PanelListaPresosPDC.add(BarraDeBusquedaPresoPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 620, 30));

        BuscarPresoPorId.setText("Buscar");
        BuscarPresoPorId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPresoPorIdActionPerformed(evt);
            }
        });
        PanelListaPresosPDC.add(BuscarPresoPorId, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, -1, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seccion A", "Seccion B", "Seccion C" }));
        PanelListaPresosPDC.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 90, 30));

        PersonalDeControlView.addTab("Presos", PanelListaPresosPDC);

        PanelAñadirVisitaPDC.setBackground(new java.awt.Color(255, 255, 255));
        PanelAñadirVisitaPDC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelAñadirVisitante.setBackground(new java.awt.Color(180, 180, 195));
        PanelAñadirVisitante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Nombre:");
        PanelAñadirVisitante.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Apellido:");
        PanelAñadirVisitante.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Identificación:");
        PanelAñadirVisitante.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Edad:");
        PanelAñadirVisitante.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Sexo:");
        PanelAñadirVisitante.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Requiere supervision:");
        PanelAñadirVisitante.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Nacionalidad:");
        PanelAñadirVisitante.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Relación con el preso:");
        PanelAñadirVisitante.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Información del visitante");
        PanelAñadirVisitante.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));
        PanelAñadirVisitante.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 290, 10));
        PanelAñadirVisitante.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 380, 10));
        PanelAñadirVisitante.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 380, 10));
        PanelAñadirVisitante.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 380, 10));
        PanelAñadirVisitante.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 380, 10));
        PanelAñadirVisitante.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 380, 10));
        PanelAñadirVisitante.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 380, 10));
        PanelAñadirVisitante.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 380, 10));
        PanelAñadirVisitante.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 380, 10));

        NombreVisitante.setBackground(new java.awt.Color(180, 180, 195));
        NombreVisitante.setBorder(null);
        NombreVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreVisitanteActionPerformed(evt);
            }
        });
        PanelAñadirVisitante.add(NombreVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 310, 30));

        EdadVisitante.setBackground(new java.awt.Color(180, 180, 195));
        EdadVisitante.setBorder(null);
        PanelAñadirVisitante.add(EdadVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 330, 30));

        ApellidoVisitante.setBackground(new java.awt.Color(180, 180, 195));
        ApellidoVisitante.setBorder(null);
        PanelAñadirVisitante.add(ApellidoVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 310, 30));

        IdentificacionVisitante.setBackground(new java.awt.Color(180, 180, 195));
        IdentificacionVisitante.setBorder(null);
        PanelAñadirVisitante.add(IdentificacionVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 280, 30));

        NacionalidadVisitante.setBackground(new java.awt.Color(180, 180, 195));
        NacionalidadVisitante.setBorder(null);
        PanelAñadirVisitante.add(NacionalidadVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 280, 30));

        BotonAñadirVisitante.setText("Añadir visitante");
        BotonAñadirVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAñadirVisitanteActionPerformed(evt);
            }
        });
        PanelAñadirVisitante.add(BotonAñadirVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 420, -1, -1));

        SexoVisitante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "Femenino", "Masculino" }));
        PanelAñadirVisitante.add(SexoVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 330, 30));

        RelacionConPreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "Esposa/Esposo", "Padre/Madre", "Hijo/Hija", "Hermano/Hermana", "Abuelo/Abuela", "Nieto/Nieta", "Tío/Tía", "Sobrino/Sobrina", "Primo/Prima", "Suegro/Suegra", "Yerno/Nuera", "Cuñado/Cuñada", "Amigo/Amiga", "Compañero de Trabajo", "Vecino/Vecina", "Conocido/Conocida", "Abogado/Abogada", "Asistente Social", "Representante Legal", "Sacerdote/Pastor", "Novio/Novia", "Tutor Legal", "Ex-Esposo/Ex-Esposa", "Familiar Político ", " " }));
        PanelAñadirVisitante.add(RelacionConPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 332, 220, 30));

        RequiereSupervision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "Si", "No" }));
        PanelAñadirVisitante.add(RequiereSupervision, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 372, 220, 30));

        PanelAñadirVisitaPDC.add(PanelAñadirVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 480, 460));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Vista previa de la imagen:");
        PanelAñadirVisitaPDC.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 410, -1, -1));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Tipo de visita:");
        PanelAñadirVisitaPDC.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, -1, -1));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Duracion de la visita:");
        PanelAñadirVisitaPDC.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 250, -1, -1));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Hora de la visita:");
        PanelAñadirVisitaPDC.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, -1, -1));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Lugar de la visita:");
        PanelAñadirVisitaPDC.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, -1, -1));

        jPanel6.setBackground(new java.awt.Color(180, 180, 195));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Indentificación del preso:");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));
        jPanel6.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 400, 10));

        IdentificacionPresoVisita.setBackground(new java.awt.Color(180, 180, 195));
        IdentificacionPresoVisita.setBorder(null);
        IdentificacionPresoVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdentificacionPresoVisitaActionPerformed(evt);
            }
        });
        jPanel6.add(IdentificacionPresoVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 220, 30));

        PanelAñadirVisitaPDC.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 430, 80));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Fecha de la visita:");
        PanelAñadirVisitaPDC.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, -1, -1));
        PanelAñadirVisitaPDC.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 400, 10));
        PanelAñadirVisitaPDC.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 400, 10));
        PanelAñadirVisitaPDC.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 400, 10));
        PanelAñadirVisitaPDC.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 270, 400, 10));
        PanelAñadirVisitaPDC.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 310, 400, 10));

        FechaVisita.setBackground(new java.awt.Color(255, 255, 255));
        FechaVisita.setBorder(null);
        FechaVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaVisitaActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(FechaVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 260, 30));

        HoraVisita.setBackground(new java.awt.Color(255, 255, 255));
        HoraVisita.setBorder(null);
        HoraVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HoraVisitaActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(HoraVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 200, 280, 30));

        DuracionVisita.setBackground(new java.awt.Color(255, 255, 255));
        DuracionVisita.setBorder(null);
        DuracionVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DuracionVisitaActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(DuracionVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 240, 250, 30));

        CantidadDeVisitantesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "1", "2" }));
        CantidadDeVisitantesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CantidadDeVisitantesComboActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(CantidadDeVisitantesCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Cantidad de visitantes");
        PanelAñadirVisitaPDC.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        BotonAñadirVisita.setText("Añadir visita");
        BotonAñadirVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAñadirVisitaActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(BotonAñadirVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 360, -1, -1));

        AgregarImagenVisitante.setText("Agregar imagen*");
        AgregarImagenVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarImagenVisitanteActionPerformed(evt);
            }
        });
        PanelAñadirVisitaPDC.add(AgregarImagenVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 150, -1));

        TipoVisita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Seleccionar >", "Familiar", "Intimas o Conyugales", "Legal", "Religiosas" }));
        PanelAñadirVisitaPDC.add(TipoVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 280, 290, 30));

        LugarVisita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Sala de visitas", "Cabinas de visitas conyugales", "Salas de visitas legales", " " }));
        PanelAñadirVisitaPDC.add(LugarVisita, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 320, 270, 30));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Información de la visita");
        PanelAñadirVisitaPDC.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, -1, -1));
        PanelAñadirVisitaPDC.add(VistaPreviaVisitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 400, 110, 120));

        PersonalDeControlView.addTab("Añadir visita", PanelAñadirVisitaPDC);

        getContentPane().add(PersonalDeControlView, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 17, 1050, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PerfilPDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PerfilPDCMouseClicked
        PersonalDeControlView.setSelectedIndex(0);
    }//GEN-LAST:event_PerfilPDCMouseClicked

    private void PresosPDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PresosPDCMouseClicked
        PersonalDeControlView.setSelectedIndex(2);
    }//GEN-LAST:event_PresosPDCMouseClicked

    private void VisitasPDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisitasPDCMouseClicked
        PersonalDeControlView.setSelectedIndex(3);
    }//GEN-LAST:event_VisitasPDCMouseClicked

    private void PerfilPDCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PerfilPDCMouseEntered
        PerfilPDC.setBackground(new Color(43, 54, 84));    }//GEN-LAST:event_PerfilPDCMouseEntered

    private void PerfilPDCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PerfilPDCMouseExited
        PerfilPDC.setBackground(new Color(29, 35, 51));
    }//GEN-LAST:event_PerfilPDCMouseExited

    private void PresosPDCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PresosPDCMouseEntered
        PresosPDC.setBackground(new Color(43, 54, 84));     }//GEN-LAST:event_PresosPDCMouseEntered

    private void PresosPDCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PresosPDCMouseExited
        PresosPDC.setBackground(new Color(29, 35, 51));
    }//GEN-LAST:event_PresosPDCMouseExited

    private void VisitasPDCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisitasPDCMouseEntered
        VisitasPDC.setBackground(new Color(43, 54, 84));
    }//GEN-LAST:event_VisitasPDCMouseEntered

    private void VisitasPDCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisitasPDCMouseExited
        VisitasPDC.setBackground(new Color(29, 35, 51));
    }//GEN-LAST:event_VisitasPDCMouseExited

    private void NombreVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreVisitanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreVisitanteActionPerformed

    private void IdentificacionPresoVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdentificacionPresoVisitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdentificacionPresoVisitaActionPerformed

    private void FechaVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaVisitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaVisitaActionPerformed

    private void HoraVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HoraVisitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HoraVisitaActionPerformed

    private void DuracionVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DuracionVisitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DuracionVisitaActionPerformed

    private void BuscarPresoPorIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPresoPorIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPresoPorIdActionPerformed

    private void TablaPresosPDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPresosPDCMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaPresosPDCMouseClicked

    private void CantidadDeVisitantesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CantidadDeVisitantesComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CantidadDeVisitantesComboActionPerformed

    private void BotonParaLlevarPanelActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonParaLlevarPanelActualizarActionPerformed

    }//GEN-LAST:event_BotonParaLlevarPanelActualizarActionPerformed

    private boolean validarCamposVisitante() {
        if (CantidadDeVisitantesCombo.getSelectedIndex() == 0) {
            mostrarError("Primero seleccione la cantidad de visitantes");
            limpiarCamposVisitante();
            return false;
        }
        if (NombreVisitante.getText().trim().isEmpty()
                || ApellidoVisitante.getText().trim().isEmpty()
                || EdadVisitante.getText().trim().isEmpty()
                || IdentificacionVisitante.getText().trim().isEmpty()
                || NacionalidadVisitante.getText().trim().isEmpty()) {
            mostrarError("Por favor complete todos los campos del formulario");
            return false;
        }

        if (SexoVisitante.getSelectedIndex() == 0
                || RelacionConPreso.getSelectedIndex() == 0
                || RequiereSupervision.getSelectedIndex() == 0) {
            mostrarError("Por favor seleccione una opción en todos los campos desplegables");
            return false;
        }

        try {
            int edad = Integer.parseInt(EdadVisitante.getText().trim());
            if (edad < 0 || edad > 120) {
                mostrarError("La edad debe estar entre 0 y 120 años");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número válido");
            return false;
        }

        if (imagenVisitanteSeleccionada == null) {
            mostrarError("Por favor seleccione una imagen del visitante");
            return false;
        }

        return true;
    }

    private boolean validarCamposVisita() {
        if (visitantesTemporales.isEmpty()) {
            mostrarError("Primero debes añadir los visitantes");
            return false;
        }

        if (IdentificacionPresoVisita.getText().trim().isEmpty()
                || FechaVisita.getText().trim().isEmpty()
                || HoraVisita.getText().trim().isEmpty()
                || DuracionVisita.getText().trim().isEmpty()) {
            mostrarError("Por favor complete todos los campos obligatorios");
            return false;
        }

        if (TipoVisita.getSelectedIndex() == 0 || LugarVisita.getSelectedIndex() == 0) {
            mostrarError("Por favor seleccione una opción en todos los campos desplegables");
            return false;
        }

        if (visitantesTemporales.size() < cantidadTotal) {
            mostrarError("Debe terminar de ingresar todos los visitantes antes de continuar");
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void limpiarCamposVisitante() {
        NombreVisitante.setText("");
        ApellidoVisitante.setText("");
        EdadVisitante.setText("");
        IdentificacionVisitante.setText("");
        NacionalidadVisitante.setText("");
        SexoVisitante.setSelectedItem(0);
        RelacionConPreso.setSelectedItem(0);
        RequiereSupervision.setSelectedItem(0);
        VistaPreviaVisitante.setIcon(null);
    }

    private void limpiarCamposVisita() {
        IdentificacionPresoVisita.setText("");
        FechaVisita.setText("");
        HoraVisita.setText("");
        DuracionVisita.setText("");
        TipoVisita.setSelectedItem(0);
        LugarVisita.setSelectedItem(0);
    }
    private void BotonAñadirVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAñadirVisitanteActionPerformed
        if (!validarCamposVisitante()) {
            return;
        }

        String nombre = NombreVisitante.getText().trim();
        String apellido = ApellidoVisitante.getText().trim();
        int edad = Integer.parseInt(EdadVisitante.getText().trim());
        String identificacion = IdentificacionVisitante.getText().trim();
        String sexo = SexoVisitante.getSelectedItem().toString();
        String relacion = RelacionConPreso.getSelectedItem().toString();
        String nacionalidad = NacionalidadVisitante.getText().trim();
        boolean supervision = RequiereSupervision.getSelectedItem().toString().equalsIgnoreCase("sí");

        int cantidadTotal;
        try {
            cantidadTotal = Integer.parseInt(CantidadDeVisitantesCombo.getSelectedItem().toString());
            if (cantidadTotal <= 0) {
                JOptionPane.showMessageDialog(this,
                        "La cantidad de visitantes debe ser mayor a cero",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Cantidad de visitantes no válida",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Visitante visitante = new Visitante(relacion, supervision, null, nombre, apellido, edad, 0, sexo, nacionalidad, identificacion);
        visitantesTemporales.add(visitante);
        imagenesTemporales.add(imagenVisitanteSeleccionada);

        limpiarCamposVisitante();
        imagenVisitanteSeleccionada = null;

        if (visitantesTemporales.size() >= cantidadTotal) {
            JOptionPane.showMessageDialog(this,
                    "Ya se añadieron todos los visitantes (" + visitantesTemporales.size() + ").\n"
                    + "Ahora complete los datos de la visita.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Visitante añadido (" + visitantesTemporales.size() + " de " + cantidadTotal + ").\n"
                    + "Por favor ingrese el siguiente visitante.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_BotonAñadirVisitanteActionPerformed

    private void BotonAñadirVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAñadirVisitaActionPerformed
        try {
            if (!validarCamposVisita()) {
                return;
            }

            String identificacionPreso = IdentificacionPresoVisita.getText().trim();
            LocalDate fecha = parsearFecha(FechaVisita.getText().trim());
            LocalTime hora = parsearHora(HoraVisita.getText().trim());
            String duracion = DuracionVisita.getText().trim();
            String tipo = TipoVisita.getSelectedItem().toString();
            String lugar = LugarVisita.getSelectedItem().toString();

            Preso preso = new PresoDAO().buscarPresoPorIdentificacion(identificacionPreso);
            if (preso == null) {
                mostrarError("No se encontró ningún preso con esa identificación");
                return;
            }

            Visita nuevaVisita = new Visita(0, fecha, hora, duracion, tipo, lugar, preso, null);
            nuevaVisita.getVisitantes().addAll(visitantesTemporales);
            new VisitaDAO().guardarVisita(nuevaVisita);

            VisitanteDAO visitanteDAO = new VisitanteDAO();
            for (int i = 0; i < nuevaVisita.getVisitantes().size(); i++) {
                Visitante visitante = nuevaVisita.getVisitantes().get(i);
                File imagen = imagenesTemporales.get(i);
                visitanteDAO.guardarVisitante(visitante, imagen);
            }

            visitantesTemporales.clear();
            imagenesTemporales.clear();
            limpiarCamposVisita();

            JOptionPane.showMessageDialog(this,
                    "¡Visita registrada exitosamente!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            mostrarError("Error al registrar visita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private LocalDate parsearFecha(String fechaTexto) {
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy");
        }
    }

    private LocalTime parsearHora(String horaTexto) {
        if (horaTexto == null || horaTexto.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalTime.parse(horaTexto, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm");
        }

     }//GEN-LAST:event_BotonAñadirVisitaActionPerformed

    private void AgregarImagenVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarImagenVisitanteActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagenVisitanteSeleccionada = fileChooser.getSelectedFile();

            try {
                BufferedImage originalImage = ImageIO.read(imagenVisitanteSeleccionada);

                ImageIcon icon = new ImageIcon(originalImage);
                Image img = icon.getImage();
                Image imgEscalada = img.getScaledInstance(
                        VistaPreviaVisitante.getWidth(),
                        VistaPreviaVisitante.getHeight(),
                        Image.SCALE_SMOOTH);

                VistaPreviaVisitante.setIcon(new ImageIcon(imgEscalada));

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la imagen: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
     }//GEN-LAST:event_AgregarImagenVisitanteActionPerformed

    private void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaPresosPDC.getModel();
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

        TablaPresosPDC.revalidate();
        TablaPresosPDC.repaint();
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
        TablaPresosPDC.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        TablaPresosPDC.setRowHeight(65);
        TablaPresosPDC.getColumnModel().getColumn(0).setPreferredWidth(70);
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


    private void BotonActualizarInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonActualizarInformacionActionPerformed
       
    }//GEN-LAST:event_BotonActualizarInformacionActionPerformed

    private void NuevoCorreoPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoCorreoPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoCorreoPDCActionPerformed

    private void ContraseñaPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContraseñaPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ContraseñaPDCActionPerformed

    private void NuevoApellidoPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoApellidoPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoApellidoPDCActionPerformed

    private void NuevaEdadPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaEdadPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaEdadPDCActionPerformed

    private void NuevaNacionalidadPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaNacionalidadPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaNacionalidadPDCActionPerformed

    private void NuevaContraseñaPDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaContraseñaPDCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaContraseñaPDCActionPerformed

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
                                VistaPreviaFotoPerfilPDC.getWidth(),
                                VistaPreviaFotoPerfilPDC.getHeight(),
                                Image.SCALE_SMOOTH);

                        VistaPreviaFotoPerfilPDC.setIcon(new ImageIcon(imgEscalada));

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
        PersonalDeControlView.setSelectedIndex(1);
    }//GEN-LAST:event_BotonParaLlevarPanelActualizarMouseClicked

    /*
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
            java.util.logging.Logger.getLogger(PersonalDeControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PersonalDeControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PersonalDeControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonalDeControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PersonalDeControl().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarImagenVisitante;
    private javax.swing.JLabel ApellidoPDC2;
    private javax.swing.JTextField ApellidoVisitante;
    private javax.swing.JTextField BarraDeBusquedaPresoPDC;
    private javax.swing.JButton BotonActualizarInformacion;
    private javax.swing.JButton BotonAñadirVisita;
    private javax.swing.JButton BotonAñadirVisitante;
    private javax.swing.JButton BotonParaLlevarPanelActualizar;
    private javax.swing.JButton BuscarPresoPorId;
    private javax.swing.JComboBox<String> CantidadDeVisitantesCombo;
    private javax.swing.JTextField ContraseñaPDC;
    private javax.swing.JTextField DuracionVisita;
    private javax.swing.JLabel EdadPDC;
    private javax.swing.JTextField EdadVisitante;
    private javax.swing.JLabel FechaDeIngresoPDC;
    private javax.swing.JTextField FechaVisita;
    private javax.swing.JTextField HoraVisita;
    private javax.swing.JLabel IdentificacionPDC;
    private javax.swing.JTextField IdentificacionPresoVisita;
    private javax.swing.JTextField IdentificacionVisitante;
    private javax.swing.JLabel LabelFotoPDC;
    private javax.swing.JComboBox<String> LugarVisita;
    private javax.swing.JLabel NacionalidadPDC;
    private javax.swing.JTextField NacionalidadVisitante;
    private javax.swing.JLabel Nombre2PDC1;
    private javax.swing.JTextField NombreVisitante;
    private javax.swing.JTextField NuevaContraseñaPDC;
    private javax.swing.JTextField NuevaEdadPDC;
    private javax.swing.JTextField NuevaIdentificacionPDC;
    private javax.swing.JTextField NuevaNacionalidadPDC;
    private javax.swing.JTextField NuevoApellidoPDC;
    private javax.swing.JTextField NuevoCorreoPDC;
    private javax.swing.JTextField NuevoNombrePDC;
    private javax.swing.JPanel PanelActualizarInformacion;
    private javax.swing.JPanel PanelAñadirVisitaPDC;
    private javax.swing.JPanel PanelAñadirVisitante;
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelListaPresosPDC;
    private javax.swing.JPanel PanelPerfilPDC;
    private javax.swing.JPanel PerfilPDC;
    private javax.swing.JTabbedPane PersonalDeControlView;
    private javax.swing.JPanel PresosPDC;
    private javax.swing.JComboBox<String> RelacionConPreso;
    private javax.swing.JComboBox<String> RequiereSupervision;
    private javax.swing.JLabel SexoPDC1;
    private javax.swing.JComboBox<String> SexoVisitante;
    private javax.swing.JButton SubirNuevaFotoPerfil;
    private javax.swing.JTable TablaPresosPDC;
    private javax.swing.JComboBox<String> TipoVisita;
    private javax.swing.JLabel TurnoPDC;
    private javax.swing.JPanel VisitasPDC;
    private javax.swing.JLabel VistaPreviaFotoPerfilPDC;
    private javax.swing.JLabel VistaPreviaVisitante;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
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
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPopupMenu ppMenuTablaPresosPDC;
    // End of variables declaration//GEN-END:variables
}
