package View;

import Informacion.ActualizarInformaciónODR;
import Informacion.ExpedientePreso;
import Informacion.HistorialMedicoPreso;
import Informacion.HistorialVisitasPreso;
import Informacion.InformacionPreso;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OficialDeRegistro extends javax.swing.JFrame {

    private BufferedImage originalImage;
    private File selectedImageFile;

    public OficialDeRegistro() {
        initComponents();
        inicializarMenu();

        agregarValidacionInstantanea();
        Siguiente1.setEnabled(false);
        Siguiente2.setEnabled(false);
        AñadirPreso.setEnabled(false);

        actualizarEstadoBotonDatosPersonales();
        actualizarEstadoBotonInformacionJudicial();
        actualizarEstadoBotonDelito();

        DescripcionDelito.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                actualizarEstadoBoton();
            }
        });

    }

    private void actualizarEstadoBoton() {
        boolean camposLlenos = !InputNombrePreso.getText().trim().isEmpty()
                && !InputApellidoPreso.getText().trim().isEmpty()
                && !InputEdadPreso.getText().trim().isEmpty()
                && !InputIdentificacionPreso.getText().trim().isEmpty()
                && !InputSexoPreso.getText().trim().isEmpty()
                && !InputNacionalidadPreso.getText().trim().isEmpty()
                && !InputEstaturaPreso.getText().trim().isEmpty()
                && !InputPesoPreso.getText().trim().isEmpty()
                && !InputGrupoSanguineoPreso.getText().trim().isEmpty()
                && !Codigo.getText().trim().isEmpty()
                && !ArticuloLey.getText().trim().isEmpty()
                && !Gravedad.getText().trim().isEmpty()
                && !FechaComision.getText().trim().isEmpty()
                && !DescripcionDelito.getText().trim().isEmpty()
                && lblFoto.getIcon() != null;

        AñadirPreso.setEnabled(camposLlenos);
    }

    private void agregarValidacionInstantanea() {
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int tabActual = TabbedAñadirInformacionGeneral.getSelectedIndex();

                switch (tabActual) {
                    case 0:
                        actualizarEstadoBotonDatosPersonales();
                        break;
                    case 1:
                        actualizarEstadoBotonInformacionJudicial();
                        break;
                    case 2:
                        actualizarEstadoBotonDelito();
                        break;
                }
            }
        };

        InputNombrePreso.addKeyListener(listener);
        InputApellidoPreso.addKeyListener(listener);
        InputEdadPreso.addKeyListener(listener);
        InputIdentificacionPreso.addKeyListener(listener);
        InputSexoPreso.addKeyListener(listener);
        InputNacionalidadPreso.addKeyListener(listener);
        InputEstaturaPreso.addKeyListener(listener);
        InputPesoPreso.addKeyListener(listener);
        InputGrupoSanguineoPreso.addKeyListener(listener);

        SeccionAsignada.addKeyListener(listener);
        NivelSeguridad.addKeyListener(listener);
        NivelRiesgo.addKeyListener(listener);
        FechaIngreso.addKeyListener(listener);
        NumeroExpediente.addKeyListener(listener);
        Sentencia.addKeyListener(listener);
        FechaSalida.addKeyListener(listener);
        Condicion.addKeyListener(listener);

        NombreDelito.addKeyListener(listener);
        Codigo.addKeyListener(listener);
        ArticuloLey.addKeyListener(listener);
        Gravedad.addKeyListener(listener);
        FechaComision.addKeyListener(listener);
        DescripcionDelito.addKeyListener(listener);
    }

    private void actualizarEstadoBotonDatosPersonales() {
        Siguiente1.setEnabled(validarDatosPersonales());
    }

    private void actualizarEstadoBotonInformacionJudicial() {
        Siguiente2.setEnabled(validarDatosJudiciales());
    }

    private void actualizarEstadoBotonDelito() {
        AñadirPreso.setEnabled(validarDatosDelito());
    }

    // Método para validar Panel 1 (Datos Personales)
    private boolean validarDatosPersonales() {
        // Verificar que los campos obligatorios no estén vacíos
        if (InputNombrePreso.getText().trim().isEmpty()
                || InputApellidoPreso.getText().trim().isEmpty()
                || InputEdadPreso.getText().trim().isEmpty()
                || InputIdentificacionPreso.getText().trim().isEmpty()
                || InputSexoPreso.getText().trim().isEmpty()
                || InputNacionalidadPreso.getText().trim().isEmpty()
                || InputEstaturaPreso.getText().trim().isEmpty()
                || InputPesoPreso.getText().trim().isEmpty()
                || InputGrupoSanguineoPreso.getText().trim().isEmpty()) {
            return false;
        }

        try {
            // Validar que los campos numéricos sean correctos
            Integer.parseInt(InputEdadPreso.getText().trim());  // Edad como entero
            Float.parseFloat(InputEstaturaPreso.getText().trim());  // Estatura como decimal
            Float.parseFloat(InputPesoPreso.getText().trim());  // Peso como decimal
            return true;
        } catch (NumberFormatException ex) {
            return false; // Si hay error en la conversión
        }
    }

    private boolean validarDatosJudiciales() {
        try {

            return !SeccionAsignada.getText().trim().isEmpty()
                    && !NivelSeguridad.getText().trim().isEmpty()
                    && !NivelRiesgo.getText().trim().isEmpty()
                    && !FechaIngreso.getText().trim().isEmpty()
                    && !NumeroExpediente.getText().trim().isEmpty()
                    && !Sentencia.getText().trim().isEmpty()
                    && !FechaSalida.getText().trim().isEmpty()
                    && !Condicion.getText().trim().isEmpty();

        } catch (NumberFormatException ex) {
            return false; // Si hay error en el número de expediente
        }
    }

    private boolean validarDatosDelito() {
        try {
            // Validar fecha (formato básico)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(FechaComision.getText().trim());

            return !NombreDelito.getText().trim().isEmpty()
                    && !Codigo.getText().trim().isEmpty()
                    && !ArticuloLey.getText().trim().isEmpty()
                    && !Gravedad.getText().trim().isEmpty()
                    && !FechaComision.getText().trim().isEmpty()
                    && !DescripcionDelito.getText().trim().isEmpty();

        } catch (Exception ex) {
            return false; // Si hay error en la fecha
        }
    }

    public void inicializarMenu() {
        JMenuItem Expediente = new JMenuItem("Expediente");
        JMenuItem historialMedico = new JMenuItem("Historial Medico");
        JMenuItem historialVisita = new JMenuItem("Historial Visitas");
        JMenuItem Informacion = new JMenuItem("Informacion General");

        ppMenuTablaPresos.add(historialMedico);
        ppMenuTablaPresos.add(Expediente);
        ppMenuTablaPresos.add(Informacion);
        ppMenuTablaPresos.add(historialVisita);

        TablaPresos.setComponentPopupMenu(ppMenuTablaPresos);

        Expediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExpedientePreso ep = new ExpedientePreso();
                ep.setSize(800, 600);
                ep.setVisible(true);

                OficialDeRegistro.this.setContentPane(ep);
                OficialDeRegistro.this.revalidate();
                OficialDeRegistro.this.repaint();
            }
        });

        Informacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformacionPreso info = new InformacionPreso();
                info.setSize(800, 600);
                info.setVisible(true);

                OficialDeRegistro.this.setContentPane(info);
                OficialDeRegistro.this.revalidate();
                OficialDeRegistro.this.repaint();
            }
        });

        historialMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialMedicoPreso imp = new HistorialMedicoPreso();
                imp.setSize(800, 600);
                imp.setVisible(true);

                OficialDeRegistro.this.setContentPane(imp);
                OficialDeRegistro.this.revalidate();
                OficialDeRegistro.this.repaint();
            }
        });

        historialVisita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialVisitasPreso hvp = new HistorialVisitasPreso();
                hvp.setSize(800, 600);
                hvp.setVisible(true);

                OficialDeRegistro.this.setContentPane(hvp);
                OficialDeRegistro.this.revalidate();
                OficialDeRegistro.this.repaint();
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ppMenuTablaPresos = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        PanelPresosTitulo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PanelPerfilTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelAñadirPresoTitulo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        OficialDeRegistroView = new javax.swing.JTabbedPane();
        PanelTablaPresoBase = new javax.swing.JPanel();
        BarraDeBusquedaPreso = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        SelectorSeccion = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPresos = new javax.swing.JTable();
        PanelAñadirPresoBase = new javax.swing.JPanel();
        SobrePanelEleccionBase = new javax.swing.JPanel();
        TabbedAñadirInformacionGeneral = new javax.swing.JTabbedPane();
        InformacionGeneralPaneñ = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(30);
        lblFoto = new javax.swing.JLabel();
        jPanel8 = new RoundedPanel(30);
        jLabel14 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        InputNombrePreso = new javax.swing.JTextField();
        InputApellidoPreso = new javax.swing.JTextField();
        InputEdadPreso = new javax.swing.JTextField();
        InputIdentificacionPreso = new javax.swing.JTextField();
        InputSexoPreso = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        InputNacionalidadPreso = new javax.swing.JTextField();
        InputEstaturaPreso = new javax.swing.JTextField();
        InputPesoPreso = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        InputGrupoSanguineoPreso = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        Siguiente1 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        InformacionJudicialPanel = new javax.swing.JPanel();
        jPanel9 = new RoundedPanel(10);
        jPanel10 = new RoundedPanel(10);
        jLabel20 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        jPanel12 = new RoundedPanel(10);
        jLabel36 = new javax.swing.JLabel();
        Condicion = new javax.swing.JTextField();
        SeccionAsignada = new javax.swing.JTextField();
        NivelSeguridad = new javax.swing.JTextField();
        NivelRiesgo = new javax.swing.JTextField();
        Sentencia = new javax.swing.JTextField();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        FechaIngreso = new javax.swing.JTextField();
        FechaSalida = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        NumeroExpediente = new javax.swing.JTextField();
        Siguiente2 = new javax.swing.JButton();
        PanelIngresarDelito = new javax.swing.JPanel();
        jPanel6 = new RoundedPanel(20);
        ;
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        Codigo = new javax.swing.JTextField();
        jSeparator36 = new javax.swing.JSeparator();
        jSeparator37 = new javax.swing.JSeparator();
        NombreDelito = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        ArticuloLey = new javax.swing.JTextField();
        jSeparator38 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        Gravedad = new javax.swing.JTextField();
        jSeparator39 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        FechaComision = new javax.swing.JTextField();
        jSeparator40 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        DescripcionDelito = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        AñadirPreso = new javax.swing.JButton();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        PanelPerfilBase = new javax.swing.JPanel();
        PanelInfoBasicaODR = new RoundedPanel(30);
        ;
        LabelFotoOficialDeRegistro = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BotonCerrarSesion = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        jSeparator29 = new javax.swing.JSeparator();
        LabelRango = new javax.swing.JLabel();
        LabelNumeroPlaca = new javax.swing.JLabel();
        LabelTurno = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new RoundedPanel(30);
        ;
        jLabel9 = new javax.swing.JLabel();
        LabelNacionalidadIG = new javax.swing.JLabel();
        LabelNombreiIG = new javax.swing.JLabel();
        LabelApellidoIG = new javax.swing.JLabel();
        LabelIdentificacionIG = new javax.swing.JLabel();
        LabelEdadIG = new javax.swing.JLabel();
        LabelSexoIG = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelPresosTitulo.setBackground(new java.awt.Color(29, 35, 51));
        PanelPresosTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPresosTituloMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelPresosTituloMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelPresosTituloMouseExited(evt);
            }
        });
        PanelPresosTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PRESOS");
        PanelPresosTitulo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, -1));

        jPanel5.add(PanelPresosTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 370, 70));

        PanelPerfilTitulo.setBackground(new java.awt.Color(29, 35, 51));
        PanelPerfilTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPerfilTituloMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelPerfilTituloMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelPerfilTituloMouseExited(evt);
            }
        });
        PanelPerfilTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PERFIL");
        PanelPerfilTitulo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jPanel5.add(PanelPerfilTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 60));

        PanelAñadirPresoTitulo.setBackground(new java.awt.Color(29, 35, 51));
        PanelAñadirPresoTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoTituloMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoTituloMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoTituloMouseExited(evt);
            }
        });
        PanelAñadirPresoTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑADIR PRESO");
        PanelAñadirPresoTitulo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 140, -1));

        jPanel5.add(PanelAñadirPresoTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 350, 70));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        PanelTablaPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelTablaPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraDeBusquedaPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarraDeBusquedaPresoActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(BarraDeBusquedaPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 570, 40));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, 90, 40));

        SelectorSeccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sección A", "Sección B", "Sección C" }));
        SelectorSeccion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SelectorSeccionItemStateChanged(evt);
            }
        });
        PanelTablaPresoBase.add(SelectorSeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad", "Sección", "Celda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaPresos);
        if (TablaPresos.getColumnModel().getColumnCount() > 0) {
            TablaPresos.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel13.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 380));

        PanelTablaPresoBase.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 860, 380));

        OficialDeRegistroView.addTab("Presos", PanelTablaPresoBase);

        PanelAñadirPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelAñadirPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SobrePanelEleccionBase.setBackground(new java.awt.Color(180, 180, 195));
        SobrePanelEleccionBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelAñadirPresoBase.add(SobrePanelEleccionBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 40));

        InformacionGeneralPaneñ.setBackground(new java.awt.Color(255, 255, 255));
        InformacionGeneralPaneñ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setText("Ingresar foto preso");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        InformacionGeneralPaneñ.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 140, 30));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Vista previa foto del preso");
        InformacionGeneralPaneñ.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 150, -1, -1));

        jPanel7.setBackground(new java.awt.Color(180, 180, 195));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(lblFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 170));

        InformacionGeneralPaneñ.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 180, 160, 190));

        jPanel8.setBackground(new java.awt.Color(180, 180, 195));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 100, -1));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 460, 10));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Nombre:");
        jPanel8.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 100, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Apellido:");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 100, -1));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Edad:");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 100, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Identificación:");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 100, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Sexo:");
        jPanel8.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 100, -1));

        InputNombrePreso.setBackground(new java.awt.Color(180, 180, 195));
        InputNombrePreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputNombrePreso.setForeground(new java.awt.Color(0, 0, 0));
        InputNombrePreso.setBorder(null);
        InputNombrePreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNombrePresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputNombrePreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 330, 20));

        InputApellidoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputApellidoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputApellidoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputApellidoPreso.setBorder(null);
        InputApellidoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputApellidoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputApellidoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 330, 20));

        InputEdadPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputEdadPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputEdadPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputEdadPreso.setBorder(null);
        InputEdadPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputEdadPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputEdadPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 350, 20));

        InputIdentificacionPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputIdentificacionPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputIdentificacionPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputIdentificacionPreso.setBorder(null);
        InputIdentificacionPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputIdentificacionPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputIdentificacionPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 330, 20));

        InputSexoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputSexoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputSexoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputSexoPreso.setBorder(null);
        InputSexoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputSexoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputSexoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 370, 20));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Nacionalidad:");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 100, -1));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Estatura:");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 100, -1));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Peso:");
        jPanel8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 100, -1));

        InputNacionalidadPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputNacionalidadPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputNacionalidadPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputNacionalidadPreso.setBorder(null);
        InputNacionalidadPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNacionalidadPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputNacionalidadPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 340, 20));

        InputEstaturaPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputEstaturaPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputEstaturaPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputEstaturaPreso.setBorder(null);
        InputEstaturaPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputEstaturaPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputEstaturaPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 350, 20));

        InputPesoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputPesoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputPesoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputPesoPreso.setBorder(null);
        InputPesoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputPesoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputPesoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 380, 20));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 460, 10));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 460, 10));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 460, 10));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 460, 10));

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 460, 10));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 460, 10));

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 460, 10));

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 460, 10));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Tipo Sangre:");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 100, -1));

        InputGrupoSanguineoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputGrupoSanguineoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputGrupoSanguineoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputGrupoSanguineoPreso.setBorder(null);
        InputGrupoSanguineoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputGrupoSanguineoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputGrupoSanguineoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 360, 20));

        jPanel11.setBackground(new java.awt.Color(29, 35, 51));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 650, 30));

        InformacionGeneralPaneñ.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 650, 420));

        Siguiente1.setBackground(new java.awt.Color(34, 80, 11));
        Siguiente1.setForeground(new java.awt.Color(255, 255, 255));
        Siguiente1.setText("Siguiente");
        Siguiente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Siguiente1ActionPerformed(evt);
            }
        });
        InformacionGeneralPaneñ.add(Siguiente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 140, 30));

        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        InformacionGeneralPaneñ.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 260, 340));

        TabbedAñadirInformacionGeneral.addTab("Añadir Informacion General", InformacionGeneralPaneñ);

        InformacionJudicialPanel.setBackground(new java.awt.Color(255, 255, 255));
        InformacionJudicialPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(180, 180, 195));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(29, 35, 51));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("INFORMACIÓN JUDICIAL");
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 7, 180, 20));

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Sentencia:");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 80, 20));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Nivel de seguridad:");
        jPanel9.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 140, 20));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Nivel de riesgo:");
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Sección asignada:");
        jPanel9.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 170, -1));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Numero de expediente: ");
        jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 170, -1));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 320, 10));

        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 310, 10));

        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 310, 10));

        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 320, 10));

        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 310, 10));

        jPanel12.setBackground(new java.awt.Color(29, 35, 51));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jPanel12.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 180, -1));

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 790, 10));

        Condicion.setBackground(new java.awt.Color(180, 180, 195));
        Condicion.setBorder(null);
        jPanel9.add(Condicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, 220, 20));

        SeccionAsignada.setBackground(new java.awt.Color(180, 180, 195));
        SeccionAsignada.setBorder(null);
        SeccionAsignada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeccionAsignadaActionPerformed(evt);
            }
        });
        jPanel9.add(SeccionAsignada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 180, 20));

        NivelSeguridad.setBackground(new java.awt.Color(180, 180, 195));
        NivelSeguridad.setBorder(null);
        NivelSeguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NivelSeguridadActionPerformed(evt);
            }
        });
        jPanel9.add(NivelSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 160, 20));

        NivelRiesgo.setBackground(new java.awt.Color(180, 180, 195));
        NivelRiesgo.setBorder(null);
        NivelRiesgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NivelRiesgoActionPerformed(evt);
            }
        });
        jPanel9.add(NivelRiesgo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 190, 20));

        Sentencia.setBackground(new java.awt.Color(180, 180, 195));
        Sentencia.setBorder(null);
        Sentencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SentenciaActionPerformed(evt);
            }
        });
        jPanel9.add(Sentencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 230, 20));
        jPanel9.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, -1, -1));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Fecha de salida:");
        jPanel9.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 120, -1));

        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 320, 10));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 320, 10));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Fecha de ingreso:");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 130, -1));

        FechaIngreso.setBackground(new java.awt.Color(180, 180, 195));
        FechaIngreso.setBorder(null);
        FechaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaIngresoActionPerformed(evt);
            }
        });
        jPanel9.add(FechaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 180, 20));

        FechaSalida.setBackground(new java.awt.Color(180, 180, 195));
        FechaSalida.setBorder(null);
        FechaSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaSalidaActionPerformed(evt);
            }
        });
        jPanel9.add(FechaSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 220, 180, 20));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Condicion:");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 90, -1));

        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 320, 10));

        NumeroExpediente.setBackground(new java.awt.Color(180, 180, 195));
        NumeroExpediente.setBorder(null);
        NumeroExpediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumeroExpedienteActionPerformed(evt);
            }
        });
        jPanel9.add(NumeroExpediente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 150, 20));

        InformacionJudicialPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 780, 390));

        Siguiente2.setBackground(new java.awt.Color(49, 84, 15));
        Siguiente2.setForeground(new java.awt.Color(255, 255, 255));
        Siguiente2.setText("Siguiente");
        Siguiente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Siguiente2ActionPerformed(evt);
            }
        });
        InformacionJudicialPanel.add(Siguiente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 220, 140, 30));

        TabbedAñadirInformacionGeneral.addTab("Añadir  Informacion Judicial", InformacionJudicialPanel);

        PanelIngresarDelito.setBackground(new java.awt.Color(255, 255, 255));
        PanelIngresarDelito.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(180, 180, 195));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Nombre:");
        jPanel6.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 70, 20));

        jLabel48.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Codigo:");
        jPanel6.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 60, 20));

        Codigo.setBackground(new java.awt.Color(180, 180, 195));
        Codigo.setBorder(null);
        Codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CodigoActionPerformed(evt);
            }
        });
        jPanel6.add(Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 230, 30));

        jSeparator36.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator36, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 310, 10));

        jSeparator37.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 310, 10));

        NombreDelito.setBackground(new java.awt.Color(180, 180, 195));
        NombreDelito.setBorder(null);
        NombreDelito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreDelitoActionPerformed(evt);
            }
        });
        jPanel6.add(NombreDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 250, 30));

        jLabel49.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Articulo Ley:");
        jPanel6.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 120, 20));

        ArticuloLey.setBackground(new java.awt.Color(180, 180, 195));
        ArticuloLey.setBorder(null);
        ArticuloLey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArticuloLeyActionPerformed(evt);
            }
        });
        jPanel6.add(ArticuloLey, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 200, 30));

        jSeparator38.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 310, 20));

        jLabel50.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Gravedad:");
        jPanel6.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 80, 20));

        Gravedad.setBackground(new java.awt.Color(180, 180, 195));
        Gravedad.setBorder(null);
        Gravedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GravedadActionPerformed(evt);
            }
        });
        jPanel6.add(Gravedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 210, 30));

        jSeparator39.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 310, 10));

        jLabel51.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Fecha comisión:");
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 130, 20));

        FechaComision.setBackground(new java.awt.Color(180, 180, 195));
        FechaComision.setBorder(null);
        FechaComision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaComisionActionPerformed(evt);
            }
        });
        jPanel6.add(FechaComision, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 190, 30));

        jSeparator40.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 310, 20));

        DescripcionDelito.setBackground(new java.awt.Color(255, 255, 255));
        DescripcionDelito.setColumns(20);
        DescripcionDelito.setForeground(new java.awt.Color(0, 0, 0));
        DescripcionDelito.setRows(5);
        DescripcionDelito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(DescripcionDelito);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 170, 210));

        jLabel26.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Descripcion delito:");
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 160, -1));

        jPanel4.setBackground(new java.awt.Color(29, 35, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, -1));

        PanelIngresarDelito.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 690, 380));

        AñadirPreso.setBackground(new java.awt.Color(45, 79, 11));
        AñadirPreso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AñadirPreso.setForeground(new java.awt.Color(255, 255, 255));
        AñadirPreso.setText("Añadir preso");
        AñadirPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirPresoActionPerformed(evt);
            }
        });
        PanelIngresarDelito.add(AñadirPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 440, -1, 40));
        PanelIngresarDelito.add(jCalendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, -1, 30));

        TabbedAñadirInformacionGeneral.addTab("Añadir Delito", PanelIngresarDelito);

        PanelAñadirPresoBase.add(TabbedAñadirInformacionGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 530));

        OficialDeRegistroView.addTab("Añadir Preso", PanelAñadirPresoBase);

        PanelPerfilBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelPerfilBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelInfoBasicaODR.setBackground(new java.awt.Color(180, 180, 195));
        PanelInfoBasicaODR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelInfoBasicaODR.add(LabelFotoOficialDeRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 160, 160));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Turno");
        PanelInfoBasicaODR.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Oficial De Registro");
        PanelInfoBasicaODR.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        BotonCerrarSesion.setText("Cerrar sesión");
        PanelInfoBasicaODR.add(BotonCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, 120, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        PanelInfoBasicaODR.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 200, 10));

        jLabel39.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Rango");
        PanelInfoBasicaODR.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 50, -1));

        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));
        PanelInfoBasicaODR.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 200, 10));

        jLabel40.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Numero de placa");
        PanelInfoBasicaODR.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 120, -1));

        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));
        PanelInfoBasicaODR.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 200, 10));
        PanelInfoBasicaODR.add(LabelRango, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 180, 20));
        PanelInfoBasicaODR.add(LabelNumeroPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 170, 20));
        PanelInfoBasicaODR.add(LabelTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 180, 20));

        PanelPerfilBase.add(PanelInfoBasicaODR, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 280, 500));

        jPanel3.setBackground(new java.awt.Color(139, 139, 157));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelPerfilBase.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("INFORMACIÓN PERSONAL");
        PanelPerfilBase.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 220, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Identificación:");
        PanelPerfilBase.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 80, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nombre:");
        PanelPerfilBase.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 50, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Edad:");
        PanelPerfilBase.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, 40, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Sexo:");
        PanelPerfilBase.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 40, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nacionalidad:");
        PanelPerfilBase.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 80, 20));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilBase.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 360, 10));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilBase.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 280, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilBase.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 360, 20));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilBase.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 360, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        PanelPerfilBase.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 360, 10));

        jButton2.setText("Actualizar información");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        PanelPerfilBase.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 450, 160, 30));

        jPanel2.setBackground(new java.awt.Color(180, 180, 195));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Apellido:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 60, 20));
        jPanel2.add(LabelNacionalidadIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 270, 30));
        jPanel2.add(LabelNombreiIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 260, 30));
        jPanel2.add(LabelApellidoIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 260, 30));
        jPanel2.add(LabelIdentificacionIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 270, 30));
        jPanel2.add(LabelEdadIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 270, 30));
        jPanel2.add(LabelSexoIG, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 270, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 360, 20));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 360, 20));

        jPanel14.setBackground(new java.awt.Color(29, 35, 51));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 480, 10));

        jPanel15.setBackground(new java.awt.Color(29, 35, 51));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 10));

        PanelPerfilBase.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 480, 300));

        OficialDeRegistroView.addTab("Perfil", PanelPerfilBase);

        jPanel1.add(OficialDeRegistroView, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1050, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PanelPerfilTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilTituloMouseClicked

        OficialDeRegistroView.setSelectedIndex(2);


    }//GEN-LAST:event_PanelPerfilTituloMouseClicked

    private void PanelPresosTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosTituloMouseClicked

        OficialDeRegistroView.setSelectedIndex(0);

    }//GEN-LAST:event_PanelPresosTituloMouseClicked

    private void PanelAñadirPresoTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoTituloMouseClicked

        OficialDeRegistroView.setSelectedIndex(1);


    }//GEN-LAST:event_PanelAñadirPresoTituloMouseClicked

    private void PanelPerfilTituloMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilTituloMouseEntered

        PanelPerfilTitulo.setBackground(new Color(43, 54, 84));
    }//GEN-LAST:event_PanelPerfilTituloMouseEntered

    private void PanelPresosTituloMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosTituloMouseEntered
        PanelPresosTitulo.setBackground(new Color(43, 54, 84));
    }//GEN-LAST:event_PanelPresosTituloMouseEntered

    private void PanelAñadirPresoTituloMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoTituloMouseEntered
        PanelAñadirPresoTitulo.setBackground(new Color(43, 54, 84));
    }//GEN-LAST:event_PanelAñadirPresoTituloMouseEntered

    private void PanelPerfilTituloMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilTituloMouseExited

        PanelPerfilTitulo.setBackground(new Color(29, 35, 51));


    }//GEN-LAST:event_PanelPerfilTituloMouseExited

    private void PanelPresosTituloMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosTituloMouseExited
        PanelPresosTitulo.setBackground(new Color(29, 35, 51));
    }//GEN-LAST:event_PanelPresosTituloMouseExited

    private void PanelAñadirPresoTituloMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoTituloMouseExited
        PanelAñadirPresoTitulo.setBackground(new Color(29, 35, 51));
    }//GEN-LAST:event_PanelAñadirPresoTituloMouseExited

    private void BarraDeBusquedaPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarraDeBusquedaPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BarraDeBusquedaPresoActionPerformed

    private void SelectorSeccionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SelectorSeccionItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectorSeccionItemStateChanged

    private void InputNombrePresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNombrePresoActionPerformed

    }//GEN-LAST:event_InputNombrePresoActionPerformed

    private void InputApellidoPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputApellidoPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputApellidoPresoActionPerformed

    private void InputEdadPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputEdadPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputEdadPresoActionPerformed

    private void InputIdentificacionPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputIdentificacionPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputIdentificacionPresoActionPerformed

    private void InputNacionalidadPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNacionalidadPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputNacionalidadPresoActionPerformed

    private void InputEstaturaPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputEstaturaPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputEstaturaPresoActionPerformed

    private void InputPesoPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputPesoPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputPesoPresoActionPerformed

    private void InputSexoPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputSexoPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputSexoPresoActionPerformed

    private void InputGrupoSanguineoPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputGrupoSanguineoPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputGrupoSanguineoPresoActionPerformed

    private void Siguiente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Siguiente2ActionPerformed

        TabbedAñadirInformacionGeneral.setSelectedIndex(2);


    }//GEN-LAST:event_Siguiente2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

        ActualizarInformaciónODR ai = new ActualizarInformaciónODR();
        ai.setSize(800, 600);
        ai.setVisible(true);

        OficialDeRegistro.this.setContentPane(ai);
        OficialDeRegistro.this.revalidate();
        OficialDeRegistro.this.repaint();

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();

            try {
                originalImage = ImageIO.read(selectedImageFile);

                ImageIcon icon = new ImageIcon(originalImage);
                Image img = icon.getImage();
                Image imgEscalada = img.getScaledInstance(
                        lblFoto.getWidth(),
                        lblFoto.getHeight(),
                        Image.SCALE_SMOOTH);

                // Mostrar la imagen en el JLabel
                lblFoto.setIcon(new ImageIcon(imgEscalada));

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la imagen: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }    }//GEN-LAST:event_jButton3ActionPerformed

    private void SeccionAsignadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeccionAsignadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SeccionAsignadaActionPerformed

    private void CodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CodigoActionPerformed

    private void NombreDelitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreDelitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreDelitoActionPerformed

    private void NivelSeguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NivelSeguridadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NivelSeguridadActionPerformed

    private void SentenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SentenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SentenciaActionPerformed

    private void ArticuloLeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArticuloLeyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ArticuloLeyActionPerformed

    private void FechaComisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaComisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaComisionActionPerformed

    private void GravedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GravedadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GravedadActionPerformed

    private void AñadirPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirPresoActionPerformed
        String nombrePreso = InputNombrePreso.getText();
        String apellido = InputApellidoPreso.getText();
        int edad = Integer.parseInt(InputEdadPreso.getText());
        String nacionalidad = InputNacionalidadPreso.getText();
        String sexo = InputSexoPreso.getText();
        float estatura = Float.parseFloat(InputEstaturaPreso.getText()); //
        float peso = Float.parseFloat(InputPesoPreso.getText());  //
        String tipoSangre = InputGrupoSanguineoPreso.getText();
        String identificacion = InputIdentificacionPreso.getText();

        String fechaIngreso = FechaIngreso.getText();
        String fechaSalida = FechaSalida.getText();

        String condicion = Condicion.getText();
        String seccionAsignada = SeccionAsignada.getText();
        String nivelDeRiesgo = NivelRiesgo.getText();
        String nivelDeSeguridad = NivelSeguridad.getText();//
        String sentencia = Sentencia.getText();
        byte numeroDeExpediente = Byte.parseByte(NumeroExpediente.getText());
        //
        String nombreDelito = NombreDelito.getText();
        String codigoDelito = Codigo.getText();
        String articuloLey = ArticuloLey.getText();
        String gravedad = Gravedad.getText();
        String fechaComision = FechaComision.getText();
        String descripcionDelito = DescripcionDelito.getText();


                }//GEN-LAST:event_AñadirPresoActionPerformed

    private void Siguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Siguiente1ActionPerformed

        TabbedAñadirInformacionGeneral.setSelectedIndex(1);
    }//GEN-LAST:event_Siguiente1ActionPerformed

    private void NivelRiesgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NivelRiesgoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NivelRiesgoActionPerformed

    private void FechaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaIngresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaIngresoActionPerformed

    private void FechaSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaSalidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaSalidaActionPerformed

    private void NumeroExpedienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumeroExpedienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NumeroExpedienteActionPerformed

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
            java.util.logging.Logger.getLogger(OficialDeRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OficialDeRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OficialDeRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OficialDeRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OficialDeRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ArticuloLey;
    private javax.swing.JButton AñadirPreso;
    private javax.swing.JTextField BarraDeBusquedaPreso;
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JTextField Codigo;
    private javax.swing.JTextField Condicion;
    private javax.swing.JTextArea DescripcionDelito;
    private javax.swing.JTextField FechaComision;
    private javax.swing.JTextField FechaIngreso;
    private javax.swing.JTextField FechaSalida;
    private javax.swing.JTextField Gravedad;
    private javax.swing.JPanel InformacionGeneralPaneñ;
    private javax.swing.JPanel InformacionJudicialPanel;
    private javax.swing.JTextField InputApellidoPreso;
    private javax.swing.JTextField InputEdadPreso;
    private javax.swing.JTextField InputEstaturaPreso;
    private javax.swing.JTextField InputGrupoSanguineoPreso;
    private javax.swing.JTextField InputIdentificacionPreso;
    private javax.swing.JTextField InputNacionalidadPreso;
    private javax.swing.JTextField InputNombrePreso;
    private javax.swing.JTextField InputPesoPreso;
    private javax.swing.JTextField InputSexoPreso;
    private javax.swing.JLabel LabelApellidoIG;
    private javax.swing.JLabel LabelEdadIG;
    private javax.swing.JLabel LabelFotoOficialDeRegistro;
    private javax.swing.JLabel LabelIdentificacionIG;
    private javax.swing.JLabel LabelNacionalidadIG;
    private javax.swing.JLabel LabelNombreiIG;
    private javax.swing.JLabel LabelNumeroPlaca;
    private javax.swing.JLabel LabelRango;
    private javax.swing.JLabel LabelSexoIG;
    private javax.swing.JLabel LabelTurno;
    private javax.swing.JTextField NivelRiesgo;
    private javax.swing.JTextField NivelSeguridad;
    private javax.swing.JTextField NombreDelito;
    private javax.swing.JTextField NumeroExpediente;
    private javax.swing.JTabbedPane OficialDeRegistroView;
    private javax.swing.JPanel PanelAñadirPresoBase;
    private javax.swing.JPanel PanelAñadirPresoTitulo;
    private javax.swing.JPanel PanelInfoBasicaODR;
    private javax.swing.JPanel PanelIngresarDelito;
    private javax.swing.JPanel PanelPerfilBase;
    private javax.swing.JPanel PanelPerfilTitulo;
    private javax.swing.JPanel PanelPresosTitulo;
    private javax.swing.JPanel PanelTablaPresoBase;
    private javax.swing.JTextField SeccionAsignada;
    private javax.swing.JComboBox<String> SelectorSeccion;
    private javax.swing.JTextField Sentencia;
    private javax.swing.JButton Siguiente1;
    private javax.swing.JButton Siguiente2;
    private javax.swing.JPanel SobrePanelEleccionBase;
    private javax.swing.JTabbedPane TabbedAñadirInformacionGeneral;
    private javax.swing.JTable TablaPresos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JCalendar jCalendar1;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JPopupMenu ppMenuTablaPresos;
    // End of variables declaration//GEN-END:variables
}
