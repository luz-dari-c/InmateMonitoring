package View;

import DAO.PresoDAO;
import Informacion.ActualizarInformaciónODR;
import Informacion.ExpedientePreso;
import Informacion.HistorialMedicoPreso;
import Informacion.HistorialVisitasPreso;
import Model.Delito;
import Model.Preso;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
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
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OficialDeRegistro extends javax.swing.JFrame {

    private BufferedImage originalImage;
    private File selectedImageFile;

    public OficialDeRegistro() {
        initComponents();
        inicializarMenu();
        configurarTablaImagenes();

        cargarDatosEnTabla();
       
     
        TablaPresos.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int fila = TablaPresos.getSelectedRow();
        if (fila >= 0) {
            String identificacion = (String) TablaPresos.getValueAt(fila, 5);
            Preso preso = new PresoDAO().buscarPresoPorIdentificacion(identificacion);
            if (preso != null) {
                DescripDelito.setText(preso.getDatosExpedienteBasico());
            }
        }
    }
});
        
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
                && !InputSexoPreso.getSelectedItem().toString().equals("Seleccione")
                && !InputNacionalidadPreso.getText().trim().isEmpty()
                && !InputEstaturaPreso.getText().trim().isEmpty()
                && !InputPesoPreso.getText().trim().isEmpty()
                && !TipoSangreCombobox.getSelectedItem().toString().equals("Seleccione")
                && !Codigo.getText().trim().isEmpty()
                && !ArticuloLey.getText().trim().isEmpty()
                && !Gravedad.getText().trim().isEmpty()
                && !FechaComision.getText().trim().isEmpty()
                && !DescripcionDelito.getText().trim().isEmpty()
                && lblFoto.getIcon() != null;

        AñadirPreso.setEnabled(camposLlenos);
    }

 private void agregarValidacionInstantanea() {
    Runnable actualizarBoton = () -> {
        switch (TabbedAñadirInformacionGeneral.getSelectedIndex()) {
            case 0 -> actualizarEstadoBotonDatosPersonales();
            case 1 -> actualizarEstadoBotonInformacionJudicial();
            case 2 -> actualizarEstadoBotonDelito();
        }
    };

    KeyListener keyListener = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            actualizarBoton.run();
        }
    };

    // Agregamos el mismo KeyListener a todos los JTextField
    InputNombrePreso.addKeyListener(keyListener);
    InputApellidoPreso.addKeyListener(keyListener);
    InputEdadPreso.addKeyListener(keyListener);
    InputIdentificacionPreso.addKeyListener(keyListener);
    InputNacionalidadPreso.addKeyListener(keyListener);
    InputEstaturaPreso.addKeyListener(keyListener);
    InputPesoPreso.addKeyListener(keyListener);
    
    SeccionAsignada.addKeyListener(keyListener);
        NivelSeguridad.addKeyListener(keyListener);
        NivelRiesgo.addKeyListener(keyListener);
        FechaIngreso.addKeyListener(keyListener);
        NumeroExpediente.addKeyListener(keyListener);
        Sentencia.addKeyListener(keyListener);
        FechaSalida.addKeyListener(keyListener);
        Condicion.addKeyListener(keyListener);
        
         NombreDelito.addKeyListener(keyListener);
        Codigo.addKeyListener(keyListener);
        ArticuloLey.addKeyListener(keyListener);
        Gravedad.addKeyListener(keyListener);
        FechaComision.addKeyListener(keyListener);
        DescripcionDelito.addKeyListener(keyListener);

    // Y el mismo ActionListener al JComboBox
    TipoSangreCombobox.addActionListener(e -> actualizarBoton.run());
    InputSexoPreso.addActionListener(e -> actualizarBoton.run());



        

       
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

    private boolean validarDatosPersonales() {
        if (InputNombrePreso.getText().trim().isEmpty()
                || InputApellidoPreso.getText().trim().isEmpty()
                || InputEdadPreso.getText().trim().isEmpty()
                || InputIdentificacionPreso.getText().trim().isEmpty()
                ||InputSexoPreso.getSelectedItem().toString().equals("Seleccione")
                || InputNacionalidadPreso.getText().trim().isEmpty()
                || InputEstaturaPreso.getText().trim().isEmpty()
                || InputPesoPreso.getText().trim().isEmpty()
                ||InputSexoPreso.getSelectedItem().toString().equals("Seleccione")) {
            return false;
        }

        try {
            Integer.parseInt(InputEdadPreso.getText().trim());  
            Float.parseFloat(InputEstaturaPreso.getText().trim());  
            Float.parseFloat(InputPesoPreso.getText().trim());  
            return true;
        } catch (NumberFormatException ex) {
            return false; 
        }
    }

   private boolean validarDatosJudiciales() {
    if (SeccionAsignada.getText().trim().isEmpty() ||
        NivelSeguridad.getText().trim().isEmpty() ||
        NivelRiesgo.getText().trim().isEmpty() ||
        FechaIngreso.getText().trim().isEmpty() ||
        NumeroExpediente.getText().trim().isEmpty() ||
        Sentencia.getText().trim().isEmpty() ||
        FechaSalida.getText().trim().isEmpty() ||
        Condicion.getText().trim().isEmpty()) {
        return false;
    }

    if (!validarFecha(FechaIngreso.getText().trim()) || 
        !validarFecha(FechaSalida.getText().trim())) {
        return false;
    }

    if (!validarNumero(NumeroExpediente.getText().trim(), true) ||
        !validarNumero(Sentencia.getText().trim(), true)) {
        return false;
    }

    return true;
}

    private boolean validarDatosDelito() {
    if (NombreDelito.getText().trim().isEmpty() ||
        Codigo.getText().trim().isEmpty() ||
        ArticuloLey.getText().trim().isEmpty() ||
        Gravedad.getText().trim().isEmpty() ||
        FechaComision.getText().trim().isEmpty() ||
        DescripcionDelito.getText().trim().isEmpty()) {
        return false;
    }

    if (!validarNumero(Codigo.getText().trim(), true)) {
        return false;
    }

    if (!validarFecha(FechaComision.getText().trim())) {
        return false;
    }

    return true;
}

private boolean validarFecha(String fechaStr) {
    try {
        LocalDate fecha = LocalDate.parse(fechaStr);
        
        if (fecha.getMonthValue() > 12 || fecha.getDayOfMonth() > 31) {
            return false;
        }        
        return true;
    } catch (Exception e) {
        return false;
    }
}

private boolean validarNumero(String numeroStr, boolean esEntero) {
    try {
        if (esEntero) {
            Integer.parseInt(numeroStr);
        } else {
            Float.parseFloat(numeroStr);
        }
        return true;
    } catch (NumberFormatException e) {
        return false;
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
        int filaSeleccionada = TablaPresos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(OficialDeRegistro.this, 
                "¡Selecciona un preso primero!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String identificacion = (String) TablaPresos.getValueAt(filaSeleccionada, 5); 
        Preso preso = new PresoDAO().buscarPresoPorIdentificacion(identificacion);
        
        if (preso != null) {

          //  RegistroNum.setText(String.valueOf(preso.getNumeroExpediente())); 
            CodExpe.setText(preso.getIdentificacion()); 
            FechaAper.setText(preso.getFechaIngreso().toString());
            Estado.setText("Activo"); 
            Juzgado.setText("Juzgado Penal");
            DescripDelito.setText(preso.getDelito().getDescripcion());
            
            
            if (preso.getDelito() != null) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); 
                model.addRow(new Object[]{
                    preso.getDelito().getNombre(),
                    preso.getDelito().getCodigo(),
                    preso.getFechaIngreso().plusYears(1).toString(), 
                    preso.getSentencia() + " años",
                    preso.getDelito().getGravedad(),
                    preso.getDelito().getFechaComision().toString()
                });
            }
            
            OficialDeRegistroView.setSelectedIndex(3); 
        }
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
    

    
    private void configurarTablaImagenes() {
    TablaPresos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, 
                isSelected, hasFocus, row, column);
            
            if(column == 0 && value instanceof ImageIcon) {
                ImageIcon originalIcon = (ImageIcon)value;
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
    
    TablaPresos.setRowHeight(65); // Un poco más grande que la imagen para espacio
    TablaPresos.getColumnModel().getColumn(0).setPreferredWidth(70); // Ancho columna imagen
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
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        TipoSangreCombobox = new javax.swing.JComboBox<>();
        InputSexoPreso = new javax.swing.JComboBox<>();
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
        jPanel16 = new javax.swing.JPanel();
        PanelDatosGenerales = new RoundedPanel(30);
        ;
        jLabel31 = new javax.swing.JLabel();
        LabelDatosgenerales = new javax.swing.JLabel();
        DescripDelito = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        jSeparator33 = new javax.swing.JSeparator();
        RegistroNum = new javax.swing.JLabel();
        CodExpe = new javax.swing.JLabel();
        FechaAper = new javax.swing.JLabel();
        Estado = new javax.swing.JLabel();
        Juzgado = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        ObservacionesConducta = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        jLabel46 = new javax.swing.JLabel();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel52 = new javax.swing.JLabel();
        jSeparator41 = new javax.swing.JSeparator();
        botonRegresar = new javax.swing.JButton();

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
        SelectorSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectorSeccionActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(SelectorSeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad", "Celda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaPresos);
        if (TablaPresos.getColumnModel().getColumnCount() > 0) {
            TablaPresos.getColumnModel().getColumn(5).setResizable(false);
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
        InformacionGeneralPaneñ.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 340, 140, 30));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Vista previa foto del preso");
        InformacionGeneralPaneñ.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, -1, -1));

        jPanel7.setBackground(new java.awt.Color(180, 180, 195));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(lblFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 170));

        InformacionGeneralPaneñ.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 140, 160, 190));

        jPanel8.setBackground(new java.awt.Color(180, 180, 195));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 100, -1));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 460, 10));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Nombre:");
        jPanel8.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 100, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Apellido:");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 80, -1));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Edad:");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 100, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Identificación:");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 100, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Sexo:");
        jPanel8.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, 50, 20));

        InputNombrePreso.setBackground(new java.awt.Color(180, 180, 195));
        InputNombrePreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputNombrePreso.setForeground(new java.awt.Color(0, 0, 0));
        InputNombrePreso.setBorder(null);
        InputNombrePreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNombrePresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputNombrePreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 390, 20));

        InputApellidoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputApellidoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputApellidoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputApellidoPreso.setBorder(null);
        InputApellidoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputApellidoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputApellidoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 330, 20));

        InputEdadPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputEdadPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputEdadPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputEdadPreso.setBorder(null);
        InputEdadPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputEdadPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputEdadPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 350, 20));

        InputIdentificacionPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputIdentificacionPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputIdentificacionPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputIdentificacionPreso.setBorder(null);
        InputIdentificacionPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputIdentificacionPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputIdentificacionPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 330, 20));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Nacionalidad:");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 100, -1));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Estatura (cm):");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 100, -1));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Peso(kg):");
        jPanel8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 100, -1));

        InputNacionalidadPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputNacionalidadPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputNacionalidadPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputNacionalidadPreso.setBorder(null);
        InputNacionalidadPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNacionalidadPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputNacionalidadPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 340, 20));

        InputEstaturaPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputEstaturaPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputEstaturaPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputEstaturaPreso.setBorder(null);
        InputEstaturaPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputEstaturaPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputEstaturaPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 350, 20));

        InputPesoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputPesoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputPesoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputPesoPreso.setBorder(null);
        InputPesoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputPesoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputPesoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 380, 20));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 460, 10));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 460, 10));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 460, 10));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 460, 10));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 460, 10));

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jPanel8.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 460, 10));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Tipo Sangre:");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 100, 20));

        jPanel11.setBackground(new java.awt.Color(29, 35, 51));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 650, 30));

        TipoSangreCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "A+", "A-", "O+", "O-", "B+", "B-", "AB+", "AB-" }));
        TipoSangreCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoSangreComboboxActionPerformed(evt);
            }
        });
        jPanel8.add(TipoSangreCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        InputSexoPreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "F", "M" }));
        InputSexoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputSexoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputSexoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, -1, -1));

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
        BotonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionActionPerformed(evt);
            }
        });
        PanelInfoBasicaODR.add(BotonCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 120, 30));

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

        PanelPerfilBase.add(PanelInfoBasicaODR, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 280, 490));

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
            .addGap(0, 10, Short.MAX_VALUE)
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
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 10));

        PanelPerfilBase.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 480, 300));

        OficialDeRegistroView.addTab("Perfil", PanelPerfilBase);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelDatosGenerales.setBackground(new java.awt.Color(180, 180, 195));
        PanelDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("DESCRIPCION DELITO");
        PanelDatosGenerales.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, -1, -1));

        LabelDatosgenerales.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelDatosgenerales.setForeground(new java.awt.Color(0, 0, 0));
        LabelDatosgenerales.setText("DATOS GENERALES");
        PanelDatosGenerales.add(LabelDatosgenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        DescripDelito.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelDatosGenerales.add(DescripDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 280, 160));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Numero de registro:");
        PanelDatosGenerales.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Codigo Expediente:");
        PanelDatosGenerales.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Fecha de Apertura:");
        PanelDatosGenerales.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Estado:");
        PanelDatosGenerales.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 20));

        jLabel43.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Juzgado:");
        PanelDatosGenerales.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 290, 10));

        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 290, 10));

        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 290, 10));

        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 290, 10));

        jSeparator33.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 290, 10));
        PanelDatosGenerales.add(RegistroNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 20));
        PanelDatosGenerales.add(CodExpe, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 150, 20));
        PanelDatosGenerales.add(FechaAper, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 150, 20));
        PanelDatosGenerales.add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 220, 20));
        PanelDatosGenerales.add(Juzgado, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 210, 20));

        jPanel16.add(PanelDatosGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 330, 500));

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Delito", "Código", "Fecha Sentencia", "Tiempo de condena", "Gravedad", "Fecha comisión"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jPanel16.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 660, 190));

        jLabel44.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("INFORMACION LEGAL");
        jPanel16.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, -1, 50));

        ObservacionesConducta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.add(ObservacionesConducta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 410, 100));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Observaciones de conducta:");
        jPanel16.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, 250, -1));

        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));
        jPanel16.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 390, 190, 10));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Nivel de riesgo:");
        jPanel16.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 120, -1));

        jSeparator35.setForeground(new java.awt.Color(0, 0, 0));
        jPanel16.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 620, 10));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Nivel de adaptación: ");
        jPanel16.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 200, -1));

        jSeparator41.setForeground(new java.awt.Color(0, 0, 0));
        jPanel16.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, 620, 10));

        botonRegresar.setText("Regresar");
        botonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresarMouseClicked(evt);
            }
        });
        botonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarActionPerformed(evt);
            }
        });
        jPanel16.add(botonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 80, -1));

        OficialDeRegistroView.addTab("tab4", jPanel16);

        jPanel1.add(OficialDeRegistroView, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1050, 530));

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
        
        try {
    if (!validarDatosDelito()) {
        StringBuilder errorMsg = new StringBuilder("Errores en datos del delito:\n");
        
        if (NombreDelito.getText().trim().isEmpty()) errorMsg.append("- Nombre del delito vacío\n");
        if (Codigo.getText().trim().isEmpty()) errorMsg.append("- Código vacío\n");
        else if (!validarNumero(Codigo.getText().trim(), true)) errorMsg.append("- Código debe ser numérico\n");
        if (ArticuloLey.getText().trim().isEmpty()) errorMsg.append("- Artículo de ley vacío\n");
        if (Gravedad.getText().trim().isEmpty()) errorMsg.append("- Gravedad vacía\n");
        if (FechaComision.getText().trim().isEmpty()) errorMsg.append("- Fecha vacía\n");
        else if (!validarFecha(FechaComision.getText().trim())) errorMsg.append("- Formato de fecha inválido (use AAAA-MM-DD)\n");
        if (DescripcionDelito.getText().trim().isEmpty()) errorMsg.append("- Descripción vacía\n");
        
        JOptionPane.showMessageDialog(this, errorMsg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (lblFoto.getIcon() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una foto del preso", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    String nombrePreso = InputNombrePreso.getText().trim();
    String apellido = InputApellidoPreso.getText().trim();
    int edad = Integer.parseInt(InputEdadPreso.getText().trim());
    String nacionalidad = InputNacionalidadPreso.getText().trim();
    String sexo = InputSexoPreso.getSelectedItem().toString();
    float estatura = Float.parseFloat(InputEstaturaPreso.getText().trim());
    float peso = Float.parseFloat(InputPesoPreso.getText().trim());
    
    String tipoSangre = TipoSangreCombobox.getSelectedItem().toString();
    String identificacion = InputIdentificacionPreso.getText().trim();

    LocalDate fechaIngreso = LocalDate.parse(FechaIngreso.getText().trim());
    LocalDate fechaSalida = LocalDate.parse(FechaSalida.getText().trim());

    String condicion = Condicion.getText().trim();
    String seccionAsignada = SeccionAsignada.getText().trim();
    String nivelDeRiesgo = NivelRiesgo.getText().trim();
    String nivelDeSeguridad = NivelSeguridad.getText().trim();
    byte sentencia = Byte.parseByte(Sentencia.getText().trim());
    byte numeroDeExpediente = Byte.parseByte(NumeroExpediente.getText().trim());

    String nombreDelito = NombreDelito.getText().trim();
    int codigoDelito = Integer.parseInt(Codigo.getText().trim());
    String articuloLey = ArticuloLey.getText().trim();
    String gravedad = Gravedad.getText().trim();
    LocalDate fechaComision = LocalDate.parse(FechaComision.getText().trim());
    String descripcionDelito = DescripcionDelito.getText().trim();

    Delito delito = new Delito(codigoDelito, nombreDelito, articuloLey, gravedad, descripcionDelito, fechaComision);
    

    Preso preso = new Preso(
        nombrePreso, apellido, edad, 0, sexo, nacionalidad, identificacion,
        estatura, peso, delito, numeroDeExpediente,
        fechaIngreso, fechaSalida,
        nivelDeSeguridad, condicion, sentencia,
        seccionAsignada, false, nivelDeRiesgo, 0, tipoSangre, null
    );

    PresoDAO presoDAO = new PresoDAO();
     
    boolean guardado = presoDAO.guardarPreso(preso, selectedImageFile);
    
    
    
    if (guardado) {
        limpiarFormulario();
        
        cargarDatosEnTabla();
        
        JOptionPane.showMessageDialog(this, "Preso añadido correctamente.");
        OficialDeRegistroView.setSelectedIndex(0);
    } else {
        JOptionPane.showMessageDialog(this, "Error al guardar el preso.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Error en los formatos numéricos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} catch (DateTimeParseException e) {
    JOptionPane.showMessageDialog(this, "Error en el formato de fecha: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}
                }//GEN-LAST:event_AñadirPresoActionPerformed

    private void limpiarFormulario() {
    InputNombrePreso.setText("");
    InputApellidoPreso.setText("");
    InputEdadPreso.setText("");
    InputIdentificacionPreso.setText("");
    InputSexoPreso.setSelectedItem(0);
    InputNacionalidadPreso.setText("");
    InputEstaturaPreso.setText("");
    InputPesoPreso.setText("");
    TipoSangreCombobox.setSelectedItem(0);
    lblFoto.setIcon(null);
    
    SeccionAsignada.setText("");
    NivelSeguridad.setText("");
    NivelRiesgo.setText("");
    FechaIngreso.setText("");
    NumeroExpediente.setText("");
    Sentencia.setText("");
    FechaSalida.setText("");
    Condicion.setText("");
    
    NombreDelito.setText("");
    Codigo.setText("");
    ArticuloLey.setText("");
    Gravedad.setText("");
    FechaComision.setText("");
    DescripcionDelito.setText("");
    
    TabbedAñadirInformacionGeneral.setSelectedIndex(0);
}
    
    
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

    private void SelectorSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectorSeccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectorSeccionActionPerformed

    private void botonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresarMouseClicked

    }//GEN-LAST:event_botonRegresarMouseClicked

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed
        
        OficialDeRegistroView.setSelectedIndex(0);

    }//GEN-LAST:event_botonRegresarActionPerformed

    private void BotonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonCerrarSesionActionPerformed

    private void TipoSangreComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoSangreComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipoSangreComboboxActionPerformed

    private void InputSexoPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputSexoPresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputSexoPresoActionPerformed

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

 private void cargarDatosEnTabla() {
    DefaultTableModel modelo = (DefaultTableModel) TablaPresos.getModel();
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
    
    TablaPresos.revalidate();
    TablaPresos.repaint();
}

private ImageIcon cargarImagenPreso(String path) {
    try {
        Image img = ImageIO.read(new File(path));
        return new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    } catch (Exception e) {
        return new ImageIcon(getClass().getResource("/images/default_profile.png"));
    }
}   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ArticuloLey;
    private javax.swing.JButton AñadirPreso;
    private javax.swing.JTextField BarraDeBusquedaPreso;
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JLabel CodExpe;
    private javax.swing.JTextField Codigo;
    private javax.swing.JTextField Condicion;
    private javax.swing.JLabel DescripDelito;
    private javax.swing.JTextArea DescripcionDelito;
    private javax.swing.JLabel Estado;
    private javax.swing.JLabel FechaAper;
    private javax.swing.JTextField FechaComision;
    private javax.swing.JTextField FechaIngreso;
    private javax.swing.JTextField FechaSalida;
    private javax.swing.JTextField Gravedad;
    private javax.swing.JPanel InformacionGeneralPaneñ;
    private javax.swing.JPanel InformacionJudicialPanel;
    private javax.swing.JTextField InputApellidoPreso;
    private javax.swing.JTextField InputEdadPreso;
    private javax.swing.JTextField InputEstaturaPreso;
    private javax.swing.JTextField InputIdentificacionPreso;
    private javax.swing.JTextField InputNacionalidadPreso;
    private javax.swing.JTextField InputNombrePreso;
    private javax.swing.JTextField InputPesoPreso;
    private javax.swing.JComboBox<String> InputSexoPreso;
    private javax.swing.JLabel Juzgado;
    private javax.swing.JLabel LabelApellidoIG;
    private javax.swing.JLabel LabelDatosgenerales;
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
    private javax.swing.JLabel ObservacionesConducta;
    private javax.swing.JTabbedPane OficialDeRegistroView;
    private javax.swing.JPanel PanelAñadirPresoBase;
    private javax.swing.JPanel PanelAñadirPresoTitulo;
    private javax.swing.JPanel PanelDatosGenerales;
    private javax.swing.JPanel PanelInfoBasicaODR;
    private javax.swing.JPanel PanelIngresarDelito;
    private javax.swing.JPanel PanelPerfilBase;
    private javax.swing.JPanel PanelPerfilTitulo;
    private javax.swing.JPanel PanelPresosTitulo;
    private javax.swing.JPanel PanelTablaPresoBase;
    private javax.swing.JLabel RegistroNum;
    private javax.swing.JTextField SeccionAsignada;
    private javax.swing.JComboBox<String> SelectorSeccion;
    private javax.swing.JTextField Sentencia;
    private javax.swing.JButton Siguiente1;
    private javax.swing.JButton Siguiente2;
    private javax.swing.JPanel SobrePanelEleccionBase;
    private javax.swing.JTabbedPane TabbedAñadirInformacionGeneral;
    private javax.swing.JTable TablaPresos;
    private javax.swing.JComboBox<String> TipoSangreCombobox;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
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
    private javax.swing.JPanel jPanel16;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
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
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JPopupMenu ppMenuTablaPresos;
    // End of variables declaration//GEN-END:variables
}

