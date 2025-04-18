package View;

import DAO.CeldaDAO;
import DAO.DelitoDAO;
import DAO.PresoDAO;
import Informacion.HistorialMedicoPreso;
import Model.Celda;
import Model.Delito;
import Model.Preso;
import Model.Sentencia;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OficialDeRegistro extends javax.swing.JFrame {

    private HashMap<String, String[]> datosDelitos;

    CeldaDAO celda = new CeldaDAO();

    private Preso preso;
    private int delitoActual = 1;
    private int totalDelitos = 1;
    private BufferedImage originalImage;
    private File selectedImageFile;
    private Preso presoOriginal;

    private List<Delito> delitosTemporales = new ArrayList<>();

    public OficialDeRegistro() {
        initComponents();
        this.setLocationRelativeTo(null);

        inicializarMenu();
        cargarDelitosConCodigoYArticulo();
        calcularFechaSalida();
        configurarTablaImagenes();
        cargarDatosEnTabla();

        celda.generarCeldas("Sección A", 10, 2);
        celda.generarCeldas("Sección B", 10, 2);
        celda.generarCeldas("Sección C", 10, 2);

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
        guardarNuevoDelito.addActionListener(e -> agregarDelitoTemporalActualizacion());
        guardarDelito.addActionListener(e -> agregarDelitoTemporalAñadir());
        actualizarPreso.addActionListener(e -> actualizarPreso());
        ActualizarFotoBoton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedImageFile.getPath());
                Image img = icon.getImage().getScaledInstance(nuevaFoto.getWidth(), nuevaFoto.getHeight(), Image.SCALE_SMOOTH);
                nuevaFoto.setIcon(new ImageIcon(img));
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
        identificacionB.setText("Buscar preso por identificación");
        identificacionB.setForeground(Color.GRAY);
        identificacionB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (identificacionB.getText().equals("Buscar preso por identificación")) {
                    identificacionB.setText("");
                    identificacionB.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (identificacionB.getText().isEmpty()) {
                    identificacionB.setText("Buscar preso por identificación");
                    identificacionB.setForeground(Color.GRAY);
                }
            }
        });

    }

    public void inicializarMenu() {
        JMenuItem Expediente = new JMenuItem("Expediente");
        JMenuItem historialMedico = new JMenuItem("Historial Medico");
        JMenuItem historialVisita = new JMenuItem("Historial Visitas");
        JMenuItem Informacion = new JMenuItem("Informacion General");
        JMenuItem Eliminar = new JMenuItem("Eliminar");
        JMenuItem Actualizar = new JMenuItem("Actualizar información");

        ppMenuTablaPresos.add(historialMedico);
        ppMenuTablaPresos.add(Expediente);
        ppMenuTablaPresos.add(Informacion);
        ppMenuTablaPresos.add(historialVisita);
        ppMenuTablaPresos.add(Eliminar);
        ppMenuTablaPresos.add(Actualizar);

        TablaPresos.setComponentPopupMenu(ppMenuTablaPresos);

        Actualizar.addActionListener(e -> {
            int fila = TablaPresos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un preso", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = TablaPresos.getValueAt(fila, 5).toString();
            Preso preso = new PresoDAO().buscarPresoPorIdentificacion(id);

            if (preso != null) {
                cargarDatosPresoEnFormularioActualizacion(preso);
                OficialDeRegistroView.setSelectedIndex(1);
                TabbedAñadirInformacionGeneral.setSelectedIndex(3);
            } else {
                JOptionPane.showMessageDialog(this, "Preso no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaPresos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Seleccione un preso primero",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String identificacion = (String) TablaPresos.getValueAt(filaSeleccionada, 5);
                PresoDAO presoDAO = new PresoDAO();
                Preso preso = presoDAO.buscarPresoPorIdentificacion(identificacion);

                if (preso == null) {
                    JOptionPane.showMessageDialog(null,
                            "Preso no encontrado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ValidarFechaDialog dialogo = new ValidarFechaDialog(null, true);
                dialogo.setVisible(true);

                if (!dialogo.isAceptado()) {
                    return;
                }

                Date fechaIngresada = dialogo.getFechaSeleccionada();
                if (fechaIngresada == null) {
                    JOptionPane.showMessageDialog(null,
                            "¡Debe seleccionar una fecha válida!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate fechaActual = fechaIngresada.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalDate fechaSalida = preso.getSentencia().getFechaSalidaCalculada();

                if (fechaActual.isBefore(fechaSalida)) {
                    JOptionPane.showMessageDialog(null,
                            "No se puede eliminar: El preso no ha completado su condena.\n"
                            + "Fecha de liberación: " + fechaSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar al preso con identificación " + identificacion + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean eliminado = presoDAO.eliminarPreso(identificacion);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Preso eliminado correctamente");
                        cargarDatosEnTabla(); 
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Error al eliminar el preso",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        Expediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaPresos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(OficialDeRegistro.this,
                            "¡Selecciona un preso primero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Object idValue = TablaPresos.getValueAt(filaSeleccionada, 5);
                    if (idValue == null) {
                        throw new Exception("Identificación nula");
                    }

                    String identificacion = idValue.toString();
                    Preso preso = new PresoDAO().buscarPresoPorIdentificacion(identificacion);

                    if (preso == null) {
                        JOptionPane.showMessageDialog(OficialDeRegistro.this,
                                "No se encontró el preso", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    RegistroNum.setText(preso.getExpediente().getNumeroRegistro());
                    CodExpe.setText(preso.getExpediente().getCodigoExpediente());
                    FechaAper.setText(preso.getExpediente().getFechaApertura().toString());
                    Estado.setText(preso.getExpediente().getEstado());
                    Juzgado.setText(preso.getExpediente().getJuzgado());
                    nivelRiesgExp.setText(preso.getNivelDeRiesgo());

                    ImageIcon icon = new ImageIcon(preso.getFotoPath());
                    Image img = icon.getImage().getScaledInstance(
                            fotoPresoExpediente.getWidth(),
                            fotoPresoExpediente.getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    fotoPresoExpediente.setIcon(new ImageIcon(img));

                    DefaultTableModel model = (DefaultTableModel) tablaExpediente.getModel();
                    model.setRowCount(0);

                    List<Delito> listaDelitos = preso.getExpediente().getDelitos();
                    if (listaDelitos != null && !listaDelitos.isEmpty()) {
                        for (Delito delito : listaDelitos) {
                            model.addRow(new Object[]{
                                delito.getNombre(),
                                delito.getCodigo(),
                                preso.getSentencia().getFechaIngreso(),
                                preso.getSentenciaFormateada(),
                                delito.getGravedad(),
                                delito.getFechaComision().toString(),
                                preso.getSentencia().getFechaSalidaCalculada()
                            });
                        }

                        if (!listaDelitos.isEmpty()) {
                            DescripDelito.setText(listaDelitos.get(0).getDescripcion());
                        }
                    }

                    OficialDeRegistroView.setSelectedIndex(3);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(OficialDeRegistro.this,
                            "Error al cargar expediente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tablaExpediente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tablaExpediente.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            Object codigoValue = tablaExpediente.getValueAt(selectedRow, 1);
                            if (codigoValue != null) {
                                int codigoDelito = Integer.parseInt(codigoValue.toString());
                                Delito delitoSeleccionado = new DelitoDAO().buscarPorCodigo(codigoDelito);
                                if (delitoSeleccionado != null) {
                                    DescripDelito.setText(delitoSeleccionado.getDescripcion());
                                }
                            }
                        } catch (Exception ex) {
                            System.err.println("Error al obtener delito: " + ex.getMessage());
                        }
                    }
                }
            }
        });

        Informacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaPresos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(OficialDeRegistro.this,
                            "¡Selecciona un preso primero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (TablaPresos.getColumnCount() <= 5) {
                        throw new Exception("La tabla no tiene la estructura esperada");
                    }

                    Object idValue = TablaPresos.getValueAt(filaSeleccionada, 5);
                    if (idValue == null || idValue.toString().trim().isEmpty()) {
                        throw new Exception("La identificación está vacía o no es válida");
                    }

                    String identificacion = idValue.toString();
                    Preso preso = new PresoDAO().buscarPresoPorIdentificacion(identificacion);

                    if (preso == null) {
                        JOptionPane.showMessageDialog(OficialDeRegistro.this,
                                "No se encontró el preso con identificación: " + identificacion,
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    nombre.setText(preso.getNombre());
                    apellido.setText(preso.getApellido());
                    edad.setText(String.valueOf(preso.getEdad()));
                    nacionali.setText(preso.getNacionalidad());
                    sexo.setText(preso.getSexo());
                    identi.setText(preso.getIdentificacion());
                    estatura.setText(String.valueOf(preso.getEstatura()));
                    peso.setText(String.valueOf(preso.getPeso()));
                    sangre.setText(preso.getGrupoSanguineo());

                    ImageIcon icon = new ImageIcon(preso.getFotoPath());
                    Image img = icon.getImage().getScaledInstance(
                            ImagenPresoInformacion.getWidth(),
                            ImagenPresoInformacion.getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    ImagenPresoInformacion.setIcon(new ImageIcon(img));

                    OficialDeRegistroView.setSelectedIndex(4);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(OficialDeRegistro.this,
                            "Error al cargar información general: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
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
         
        });

    }

    private void calcularFechaSalida() {
        try {
            Object valorAños = spinnerAñosSentencia.getValue();
            Object valorMeses = spinnerMesesSentencia.getValue();

            Integer años = valorAños != null ? (Integer) valorAños : 0;
            Integer meses = valorMeses != null ? (Integer) valorMeses : 0;

            Sentencia sentencia = null;

            if (datePickerFechaIngreso.getDate() != null && (años > 0 || meses > 0)) {
                LocalDate fechaIngreso = datePickerFechaIngreso.getDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();

                sentencia = new Sentencia(años, meses, fechaIngreso);
                lblFechaSalidaCalculada.setText(
                        sentencia.getFechaSalidaCalculada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                Siguiente2.setEnabled(true);
            } else {
                lblFechaSalidaCalculada.setText("Seleccione una fecha y duración válida.");
            }
        } catch (Exception ex) {
            lblFechaSalidaCalculada.setText("Fecha salida: Error");
            ex.printStackTrace();
        }
    }

    private void cargarDatosPresoEnFormularioActualizacion(Preso preso) {
        this.presoOriginal = preso;
        this.delitosTemporales = new ArrayList<>();

        nuevoNombreField.setText("");
        nuevoApellidoField.setText("");
        nuevaEdadField.setText("");
        nuevaIdentificacionField.setText(preso.getIdentificacion());
        nuevaEstaturaField.setText("");
        nuevoPesoField.setText("");
        nuevaNacionalidadField.setText("");
        nuevoGrupoSanguineoCombo.setSelectedIndex(0);

        nuevoAño.setValue(0);
        nuevoMes.setValue(0);
        nuevaSeccionCombo.setSelectedIndex(0);
        nuevoNivelSeguridadCombo.setSelectedIndex(0);
        nuevoAislamientoCombo.setSelectedIndex(0);
        nuevoNivelRiesgoCombo.setSelectedIndex(0);

        cod.setText("");
        art.setText("");
        jComboBox1.setSelectedIndex(0);
        textAreaDescripcion.setText("");

        spinnerAñosSentencia.setValue(preso.getSentencia().getAños());
        spinnerMesesSentencia.setValue(preso.getSentencia().getMeses());

        LocalDate fechaIngreso = preso.getSentencia().getFechaIngreso();

        Date fechaConvertida = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());

        datePickerFechaIngreso.setDate(fechaConvertida);
        calcularFechaSalida();

        nuevaFoto.setIcon(null);
        selectedImageFile = null;
    }

    private void cargarDelitosConCodigoYArticulo() {
        datosDelitos = new HashMap<>();

        datosDelitos.put("Injuria", new String[]{"220", "Artículo 220"});
        datosDelitos.put("Calumnia", new String[]{"221", "Artículo 221"});
        datosDelitos.put("Daño en bien ajeno", new String[]{"265", "Artículo 265"});
        datosDelitos.put("Violación de habitación ajena", new String[]{"189", "Artículo 189"});
        datosDelitos.put("Inasistencia alimentaria", new String[]{"233", "Artículo 233"});
        datosDelitos.put("Omisión de socorro", new String[]{"131", "Artículo 131"});
        datosDelitos.put("Lesiones personales leves", new String[]{"111", "Artículo 111"});
        datosDelitos.put("Falsedad en documento privado", new String[]{"289", "Artículo 289"});
        datosDelitos.put("Usurpación de derechos", new String[]{"296", "Artículo 296"});
        datosDelitos.put("Uso de documento falso", new String[]{"291", "Artículo 291"});
        datosDelitos.put("Abuso de confianza", new String[]{"249", "Artículo 249"});
        datosDelitos.put("Hurto simple", new String[]{"239", "Artículo 239"});
        datosDelitos.put("Receptación", new String[]{"447", "Artículo 447"});
        datosDelitos.put("Estafa", new String[]{"246", "Artículo 246 "});
        datosDelitos.put("Violación de cerraduras o sellos", new String[]{"189", "Artículo 189"});
        datosDelitos.put("Fraude", new String[]{"246", "Artículo 246 - Fraude"});
        datosDelitos.put("Violación de medidas sanitarias", new String[]{"368", "Artículo 368"});
        datosDelitos.put("Invasión de tierras o edificaciones", new String[]{"263", "Artículo 263"});
        datosDelitos.put("Suplantación de identidad", new String[]{"296", "Artículo 296"});
        datosDelitos.put("Contrabando", new String[]{"319", "Artículo 319 - Contrabando"});
        datosDelitos.put("Hurto calificado", new String[]{"240", "Artículo 240"});
        datosDelitos.put("Lesiones personales graves", new String[]{"111", "Artículo 111"});
        datosDelitos.put("Extorsión", new String[]{"244", "Artículo 244 - Extorsión"});
        datosDelitos.put("Falsedad en documento público", new String[]{"287", "Artículo 287"});
        datosDelitos.put("Lavado de activos", new String[]{"323", "Artículo 323"});
        datosDelitos.put("Peculado por uso", new String[]{"399", "Artículo 399"});
        datosDelitos.put("Violencia intrafamiliar", new String[]{"229", "Artículo 229"});
        datosDelitos.put("Acoso sexual", new String[]{"210", "Artículo 210"});
        datosDelitos.put("Acceso abusivo a sistema informático", new String[]{"269", "Artículo 269"});
        datosDelitos.put("Suplantación en medios electrónicos", new String[]{"296", "Artículo 296"});
        datosDelitos.put("Daño informático", new String[]{"269", "Artículo 269 - Daño informático"});
        datosDelitos.put("Tráfico de influencias", new String[]{"411", "Artículo 411"});
        datosDelitos.put("Porte ilegal de armas", new String[]{"365", "Artículo 365"});
        datosDelitos.put("Cohecho", new String[]{"405", "Artículo 405"});
        datosDelitos.put("Concusión", new String[]{"404", "Artículo 404"});
        datosDelitos.put("Prevaricato", new String[]{"413", "Artículo 413 - Penal"});
        datosDelitos.put("Abuso de autoridad", new String[]{"416", "Artículo 416 - Penal"});
        datosDelitos.put("Perturbación del orden público", new String[]{"353", "Artículo 353"});
        datosDelitos.put("Enriquecimiento ilícito", new String[]{"412", "Artículo 412"});
        datosDelitos.put("Tráfico de fauna o flora silvestre", new String[]{"328", "Artículo 328"});
        datosDelitos.put("Minería ilegal", new String[]{"338", "Artículo 338"});
        datosDelitos.put("Hurto agravado", new String[]{"240", "Artículo 240"});
        datosDelitos.put("Homicidio culposo", new String[]{"109", "Artículo 109"});
        datosDelitos.put("Acceso carnal abusivo con menor de 14 años", new String[]{"208", "Artículo 208"});
        datosDelitos.put("Actos sexuales con menor de 14 años", new String[]{"209", "Artículo 209"});
        datosDelitos.put("Acceso carnal violento", new String[]{"205", "Artículo 205"});
        datosDelitos.put("Acto sexual violento", new String[]{"206", "Artículo 206"});
        datosDelitos.put("Violación", new String[]{"205", "Artículo 205 - Violación"});
        datosDelitos.put("Secuestro simple", new String[]{"168", "Artículo 168"});
        datosDelitos.put("Tráfico de estupefacientes", new String[]{"376", "Artículo 376"});
        datosDelitos.put("Fabricación o porte de estupefacientes", new String[]{"376", "Artículo 376"});
        datosDelitos.put("Concierto para delinquir", new String[]{"340", "Artículo 340"});
        datosDelitos.put("Homicidio", new String[]{"103", "Artículo 103 - Homicidio"});
        datosDelitos.put("Homicidio agravado", new String[]{"104", "Artículo 104"});
        datosDelitos.put("Tortura", new String[]{"178", "Artículo 178 - Tortura"});
        datosDelitos.put("Desaparición forzada", new String[]{"165", "Artículo 165"});
        datosDelitos.put("Terrorismo", new String[]{"343", "Artículo 343"});
        datosDelitos.put("Rebelión", new String[]{"467", "Artículo 467"});
        datosDelitos.put("Genocidio", new String[]{"101", "Artículo 101"});
        datosDelitos.put("Crímenes de lesa humanidad", new String[]{"7", "Artículo 7"});

        for (String delito : datosDelitos.keySet()) {
            NuevosDelitosNombre.addItem(delito);
            this.delito.addItem(delito);

        }

        NuevosDelitosNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delitoSeleccionado = NuevosDelitosNombre.getSelectedItem().toString();

                if (datosDelitos.containsKey(delitoSeleccionado)) {
                    String[] datos = datosDelitos.get(delitoSeleccionado);
                    cod.setText(datos[0]);
                    art.setText(datos[1]);
                } else {
                    cod.setText("");
                    art.setText("");
                }
            }
        });

        delito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delitoSeleccionado = delito.getSelectedItem().toString();

                if (datosDelitos.containsKey(delitoSeleccionado)) {
                    String[] datos = datosDelitos.get(delitoSeleccionado);
                    Codigo.setText(datos[0]);
                    ArticuloLey.setText(datos[1]);

                } else {
                    Codigo.setText("");
                    ArticuloLey.setText("");
                }
            }
        });

    }

    private void agregarDelitoTemporalActualizacion() {
        try {
            if (cod.getText().trim().isEmpty()
                    || art.getText().trim().isEmpty()
                    || fechaComisionActualizar.getDate() == null) {
                JOptionPane.showMessageDialog(this,
                        "Código, Artículo de Ley y Fecha son campos obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int codigo;
            try {
                codigo = Integer.parseInt(cod.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "El código debe ser un número entero válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaComision = fechaComisionActualizar.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String articulo = ArticuloLey.getText().trim();
            String gravedad = jComboBox1.getSelectedItem().toString();
            String descripcion = textAreaDescripcion.getText().trim();
            String delitonuevo = NuevosDelitosNombre.getSelectedItem().toString();

            Delito delito = new Delito(
                    codigo,
                    delitonuevo,
                    articulo,
                    gravedad,
                    descripcion,
                    fechaComision
            );

            delitosTemporales.add(delito);

            Codigo.setText("");
            ArticuloLey.setText("");
            fechaComisionActualizar.setDate(null);
            jComboBox1.setSelectedIndex(0);
            textAreaDescripcion.setText("");

            JOptionPane.showMessageDialog(this,
                    "Delito agregado (Total: " + delitosTemporales.size() + ")",
                    "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void agregarDelitoTemporalAñadir() {
        try {
            if (Codigo.getText().trim().isEmpty()
                    || ArticuloLey.getText().trim().isEmpty()
                    || FechaComision.getDate() == null) {
                JOptionPane.showMessageDialog(this,
                        "Código, Artículo de Ley y Fecha son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(Codigo.getText().trim());
            LocalDate fecha = FechaComision.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String articulo = ArticuloLey.getText().trim();
            String gravedad = Gravedad.getSelectedItem().toString();
            String descripcion = DescripcionDelito.getText().trim();
            String nombreDelito = delito.getSelectedItem().toString();

            Delito delito = new Delito(codigo, nombreDelito, articulo, gravedad, descripcion, fecha);
            delitosTemporales.add(delito);

            Codigo.setText("");
            ArticuloLey.setText("");
            FechaComision.setDate(null);
            Gravedad.setSelectedIndex(0);
            DescripcionDelito.setText("");

            JOptionPane.showMessageDialog(this,
                    "Delito agregado correctamente (Total: " + delitosTemporales.size() + ")",
                    "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualizarPreso() {
        if (nuevoNombreField.getText().trim().isEmpty()
                && nuevoApellidoField.getText().trim().isEmpty()
                && nuevaEdadField.getText().trim().isEmpty()
                && nuevaEstaturaField.getText().trim().isEmpty()
                && nuevoPesoField.getText().trim().isEmpty()
                && nuevaNacionalidadField.getText().trim().isEmpty()
                && nuevoGrupoSanguineoCombo.getSelectedIndex() == 0
                && (Integer) nuevoAño.getValue() == 0
                && (Integer) nuevoMes.getValue() == 0
                && nuevaSeccionCombo.getSelectedIndex() == 0
                && nuevoNivelSeguridadCombo.getSelectedIndex() == 0
                && nuevoAislamientoCombo.getSelectedIndex() == 0
                && nuevoNivelRiesgoCombo.getSelectedIndex() == 0
                && delitosTemporales.isEmpty()
                && selectedImageFile == null) {

            JOptionPane.showMessageDialog(this, "No hay cambios para guardar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PresoDAO presoDAO = new PresoDAO();

            Sentencia sentencia = null;

            int años = (int) nuevoAño.getValue();
            int meses = (int) nuevoMes.getValue();

            if (años > 0 || meses > 0) {
                LocalDate fechaIngreso = presoOriginal.getSentencia().getFechaIngreso();
                sentencia = new Sentencia(años, meses, fechaIngreso);
            }

            boolean resultado = presoDAO.actualizarPresoConDelitos(
                    presoOriginal.getIdentificacion(),
                    nuevoNombreField.getText().trim().isEmpty() ? null : nuevoNombreField.getText().trim(),
                    nuevoApellidoField.getText().trim().isEmpty() ? null : nuevoApellidoField.getText().trim(),
                    nuevaEdadField.getText().trim().isEmpty() ? null : Integer.parseInt(nuevaEdadField.getText().trim()),
                    presoOriginal.getSexo(),
                    nuevaNacionalidadField.getText().trim().isEmpty() ? null : nuevaNacionalidadField.getText().trim(),
                    nuevaEstaturaField.getText().trim().isEmpty() ? null : Float.parseFloat(nuevaEstaturaField.getText().trim()),
                    nuevoPesoField.getText().trim().isEmpty() ? null : Float.parseFloat(nuevoPesoField.getText().trim()),
                    sentencia,
                    nuevoGrupoSanguineoCombo.getSelectedIndex() == 0 ? null : nuevoGrupoSanguineoCombo.getSelectedItem().toString(),
                    nuevaSeccionCombo.getSelectedIndex() == 0 ? null : nuevaSeccionCombo.getSelectedItem().toString(),
                    nuevoNivelSeguridadCombo.getSelectedIndex() == 0 ? null : nuevoNivelSeguridadCombo.getSelectedItem().toString(),
                    nuevoAislamientoCombo.getSelectedIndex() == 0 ? null : nuevoAislamientoCombo.getSelectedItem().toString().equalsIgnoreCase("Sí"),
                    nuevoNivelRiesgoCombo.getSelectedIndex() == 0 ? null : nuevoNivelRiesgoCombo.getSelectedItem().toString(),
                    selectedImageFile,
                    delitosTemporales.isEmpty() ? null : delitosTemporales
            );

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Preso actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                OficialDeRegistroView.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar preso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en formato numérico", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                && !Gravedad.getSelectedItem().toString().equals("Seleccione")
                && FechaComision.getDate() != null
                && !DescripcionDelito.getText().trim().isEmpty()
                && lblFoto.getIcon() != null;

        AñadirPreso.setEnabled(camposLlenos);
    }

    private void agregarValidacionInstantanea() {
        Runnable actualizarBoton = () -> {
            switch (TabbedAñadirInformacionGeneral.getSelectedIndex()) {
                case 0 ->
                    actualizarEstadoBotonDatosPersonales();
                case 1 ->
                    actualizarEstadoBotonInformacionJudicial();
                case 2 ->
                    actualizarEstadoBotonDelito();
            }
        };
        ;

        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarBoton.run();
            }
        };

        InputNombrePreso.addKeyListener(keyListener);
        InputApellidoPreso.addKeyListener(keyListener);
        InputEdadPreso.addKeyListener(keyListener);
        InputIdentificacionPreso.addKeyListener(keyListener);
        InputNacionalidadPreso.addKeyListener(keyListener);
        InputEstaturaPreso.addKeyListener(keyListener);
        InputPesoPreso.addKeyListener(keyListener);

        spinnerAñosSentencia.addPropertyChangeListener("value", evt -> actualizarBoton.run());
        spinnerMesesSentencia.addPropertyChangeListener("value", evt -> actualizarBoton.run());

        Codigo.addKeyListener(keyListener);
        ArticuloLey.addKeyListener(keyListener);
        Gravedad.addKeyListener(keyListener);
        FechaComision.getDateEditor().addPropertyChangeListener("date", evt -> actualizarBoton.run());
        DescripcionDelito.addKeyListener(keyListener);

        spinnerAñosSentencia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularFechaSalida();
            }
        });

        spinnerAñosSentencia.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calcularFechaSalida();
            }
        });
        datePickerFechaIngreso.addPropertyChangeListener("date", e -> calcularFechaSalida());

        TipoSangreCombobox.addActionListener(e -> actualizarBoton.run());
        InputSexoPreso.addActionListener(e -> actualizarBoton.run());
        riesgo.addActionListener(e -> actualizarBoton.run());
        delito.addActionListener(e -> actualizarBoton.run());
        seguridad.addActionListener(e -> actualizarBoton.run());
        seccion.addActionListener(e -> actualizarBoton.run());
        condicionComb.addActionListener(e -> actualizarBoton.run());
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
                || InputSexoPreso.getSelectedItem().toString().equals("Seleccione")
                || InputNacionalidadPreso.getText().trim().isEmpty()
                || InputEstaturaPreso.getText().trim().isEmpty()
                || InputPesoPreso.getText().trim().isEmpty()
                || TipoSangreCombobox.getSelectedItem().toString().equals("Seleccione")
                || lblFoto.getIcon() == null)
                  {
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
        if (seccion.getSelectedIndex() == 0
                || seguridad.getSelectedIndex() == 0
                || riesgo.getSelectedIndex() == 0
                || condicionComb.getSelectedIndex() == 0) {
            return false;
        }

        if (datePickerFechaIngreso.getDate() == null) {
            return false;
        }

        Object valorAños = spinnerAñosSentencia.getValue();
        Object valorMeses = spinnerMesesSentencia.getValue();

        Integer años = valorAños != null ? (Integer) valorAños : 0;
        Integer meses = valorMeses != null ? (Integer) valorMeses : 0;

        if (años < 0 || meses < 0 || meses > 11) {
            return false;
        }

        if (años == 0 && meses == 0) {
            return false;
        }

        return true;
    }

    private boolean validarDatosDelito() {
        if (delito.getSelectedIndex() == 0
                || Codigo.getText().trim().isEmpty()
                || ArticuloLey.getText().trim().isEmpty()
                || Gravedad.getSelectedIndex() == 0
                || FechaComision.getDate() == null
                || DescripcionDelito.getText().trim().isEmpty()) {
            return false;
        }

        if (!validarNumero(Codigo.getText().trim(), true)) {
            return false;
        }

        if (FechaComision.getDate() == null) {
            return false;
        }

        LocalDate fechaComision = FechaComision.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (!validarFecha(fechaComision.toString())) {
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

    private void configurarTablaImagenes() {
        TablaPresos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        TablaPresos.setRowHeight(65);
        TablaPresos.getColumnModel().getColumn(0).setPreferredWidth(70);
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
        PRINCIPAL = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        PanelPresosTitulo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PanelPerfilTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelAñadirPresoTitulo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        OficialDeRegistroView = new javax.swing.JTabbedPane();
        PanelTablaPresoBase = new javax.swing.JPanel();
        identificacionB = new javax.swing.JTextField();
        btnBuscarIdentificacion = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPresos = new javax.swing.JTable();
        jPanel13 = new RoundedPanel(30);
        ;
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new RoundedPanel(32)
        ;
        jLabel54 = new javax.swing.JLabel();
        SelectorSeccion = new javax.swing.JComboBox<>();
        btnRestaurarTabla = new javax.swing.JButton();
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
        cancelarD3 = new javax.swing.JButton();
        InformacionJudicialPanel = new javax.swing.JPanel();
        jPanel9 = new RoundedPanel(10);
        jPanel10 = new RoundedPanel(10);
        jLabel20 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        jPanel12 = new RoundedPanel(10);
        jLabel36 = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        seccion = new javax.swing.JComboBox<>();
        riesgo = new javax.swing.JComboBox<>();
        seguridad = new javax.swing.JComboBox<>();
        condicionComb = new javax.swing.JComboBox<>();
        Siguiente2 = new javax.swing.JButton();
        spinnerAñosSentencia = new com.toedter.components.JSpinField();
        spinnerMesesSentencia = new com.toedter.components.JSpinField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        datePickerFechaIngreso = new com.toedter.calendar.JDateChooser();
        lblFechaSalidaCalculada = new javax.swing.JLabel();
        cancelarD2 = new javax.swing.JButton();
        PanelIngresarDelito = new javax.swing.JPanel();
        jPanel6 = new RoundedPanel(20);
        ;
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jSeparator36 = new javax.swing.JSeparator();
        jSeparator37 = new javax.swing.JSeparator();
        jLabel49 = new javax.swing.JLabel();
        jSeparator38 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        jSeparator39 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        jSeparator40 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        DescripcionDelito = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        delito = new javax.swing.JComboBox<>();
        cantidadDelitos = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        guardarDelito = new javax.swing.JButton();
        jSeparator42 = new javax.swing.JSeparator();
        lblProgreso = new javax.swing.JLabel();
        AñadirPreso = new javax.swing.JButton();
        FechaComision = new com.toedter.calendar.JDateChooser();
        Gravedad = new javax.swing.JComboBox<>();
        ArticuloLey = new javax.swing.JLabel();
        Codigo = new javax.swing.JLabel();
        cancelarD = new javax.swing.JButton();
        ActualizarInformacionPreso = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator50 = new javax.swing.JSeparator();
        jSeparator51 = new javax.swing.JSeparator();
        jSeparator52 = new javax.swing.JSeparator();
        jSeparator53 = new javax.swing.JSeparator();
        jSeparator54 = new javax.swing.JSeparator();
        jSeparator55 = new javax.swing.JSeparator();
        jSeparator56 = new javax.swing.JSeparator();
        actualizarPreso = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jSeparator57 = new javax.swing.JSeparator();
        jLabel76 = new javax.swing.JLabel();
        jSeparator58 = new javax.swing.JSeparator();
        nuevoGrupoSanguineoCombo = new javax.swing.JComboBox<>();
        nuevaSeccionCombo = new javax.swing.JComboBox<>();
        jLabel77 = new javax.swing.JLabel();
        jSeparator59 = new javax.swing.JSeparator();
        nuevoNivelSeguridadCombo = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        jSeparator60 = new javax.swing.JSeparator();
        nuevoAislamientoCombo = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        nuevaFoto = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        ActualizarFotoBoton = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        NuevosDelitos = new javax.swing.JComboBox<>();
        jSeparator61 = new javax.swing.JSeparator();
        NuevosDelitosNombre = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        jSeparator62 = new javax.swing.JSeparator();
        jLabel83 = new javax.swing.JLabel();
        jSeparator63 = new javax.swing.JSeparator();
        jLabel84 = new javax.swing.JLabel();
        jSeparator64 = new javax.swing.JSeparator();
        jLabel85 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator65 = new javax.swing.JSeparator();
        jLabel86 = new javax.swing.JLabel();
        jSeparator66 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        textAreaDescripcion = new javax.swing.JTextArea();
        jLabel87 = new javax.swing.JLabel();
        guardarNuevoDelito = new javax.swing.JButton();
        nuevoNombreField = new javax.swing.JTextField();
        nuevoApellidoField = new javax.swing.JTextField();
        nuevaEdadField = new javax.swing.JTextField();
        nuevaIdentificacionField = new javax.swing.JTextField();
        nuevaEstaturaField = new javax.swing.JTextField();
        nuevoPesoField = new javax.swing.JTextField();
        nuevaNacionalidadField = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jSeparator67 = new javax.swing.JSeparator();
        nuevoNivelRiesgoCombo = new javax.swing.JComboBox<>();
        jPanel27 = new javax.swing.JPanel();
        fechaComisionActualizar = new com.toedter.calendar.JDateChooser();
        nuevoAño = new com.toedter.components.JSpinField();
        nuevoMes = new com.toedter.components.JSpinField();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        cod = new javax.swing.JLabel();
        art = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        Perfil = new javax.swing.JPanel();
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
        jButton1 = new javax.swing.JButton();
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
        Expediente = new javax.swing.JPanel();
        PanelDatosGenerales = new RoundedPanel(30);
        ;
        LabelDatosgenerales = new javax.swing.JLabel();
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
        jLabel52 = new javax.swing.JLabel();
        jSeparator41 = new javax.swing.JSeparator();
        jLabel46 = new javax.swing.JLabel();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel45 = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        ObservacionesConducta = new javax.swing.JLabel();
        nivelRiesgExp = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaExpediente = new javax.swing.JTable();
        botonRegresar = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        DescripDelito = new javax.swing.JLabel();
        jPanel1 = new RoundedPanel(30);
        fotoPresoExpediente = new javax.swing.JLabel();
        DatosPersonalesPreso = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel58 = new javax.swing.JLabel();
        jSeparator43 = new javax.swing.JSeparator();
        jLabel59 = new javax.swing.JLabel();
        jSeparator44 = new javax.swing.JSeparator();
        jLabel60 = new javax.swing.JLabel();
        jSeparator45 = new javax.swing.JSeparator();
        jLabel61 = new javax.swing.JLabel();
        jSeparator46 = new javax.swing.JSeparator();
        jLabel62 = new javax.swing.JLabel();
        jSeparator47 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        jSeparator48 = new javax.swing.JSeparator();
        jLabel64 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jSeparator49 = new javax.swing.JSeparator();
        jPanel23 = new RoundedPanel(30);
        jPanel22 = new RoundedPanel(30);
        ImagenPresoInformacion = new javax.swing.JLabel();
        sangre = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        apellido = new javax.swing.JLabel();
        sexo = new javax.swing.JLabel();
        edad = new javax.swing.JLabel();
        nacionali = new javax.swing.JLabel();
        identi = new javax.swing.JLabel();
        estatura = new javax.swing.JLabel();
        peso = new javax.swing.JLabel();
        regresar = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jSeparator68 = new javax.swing.JSeparator();
        jSeparator69 = new javax.swing.JSeparator();
        jSeparator70 = new javax.swing.JSeparator();
        jSeparator71 = new javax.swing.JSeparator();
        jSeparator72 = new javax.swing.JSeparator();
        jSeparator73 = new javax.swing.JSeparator();
        jSeparator74 = new javax.swing.JSeparator();
        jSeparator75 = new javax.swing.JSeparator();
        jPanel37 = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        nuevoCorreo = new javax.swing.JTextField();
        nuevaEdad = new javax.swing.JTextField();
        nuevaIdenti = new javax.swing.JTextField();
        nuevaNacio = new javax.swing.JTextField();
        nuevoNombre = new javax.swing.JTextField();
        nuevaContra = new javax.swing.JTextField();
        nuevoApellido = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        LabelFOTO = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        BotonRegresar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        PRINCIPAL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel5.add(PanelPresosTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 370, 60));

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

        jPanel5.add(PanelAñadirPresoTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 360, 60));

        PRINCIPAL.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 60));

        PanelTablaPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelTablaPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        identificacionB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificacionBActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(identificacionB, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 550, 30));

        btnBuscarIdentificacion.setText("Buscar");
        btnBuscarIdentificacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarIdentificacionMouseClicked(evt);
            }
        });
        btnBuscarIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdentificacionActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(btnBuscarIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 80, 30));

        TablaPresos.setBackground(new java.awt.Color(204, 204, 204));
        TablaPresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad", "Seccion", "Celda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPresos.setGridColor(new java.awt.Color(0, 0, 0));
        TablaPresos.setShowGrid(false);
        TablaPresos.setShowHorizontalLines(true);
        TablaPresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaPresosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablaPresos);
        if (TablaPresos.getColumnModel().getColumnCount() > 0) {
            TablaPresos.getColumnModel().getColumn(5).setResizable(false);
        }

        PanelTablaPresoBase.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 930, 360));

        jPanel13.setBackground(new java.awt.Color(180, 180, 195));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, -1, -1));

        jPanel18.setBackground(new java.awt.Color(29, 35, 51));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText(" Sección");
        jPanel18.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 100, 30));

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
        jPanel18.add(SelectorSeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, 30));

        jPanel13.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 70));

        btnRestaurarTabla.setBackground(new java.awt.Color(0, 0, 0));
        btnRestaurarTabla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRestaurarTabla.setForeground(new java.awt.Color(255, 255, 255));
        btnRestaurarTabla.setText("Restaurar tabla");
        btnRestaurarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaurarTablaActionPerformed(evt);
            }
        });
        jPanel13.add(btnRestaurarTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 130, 30));

        PanelTablaPresoBase.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 920, 70));

        OficialDeRegistroView.addTab("Presos", PanelTablaPresoBase);

        PanelAñadirPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelAñadirPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SobrePanelEleccionBase.setBackground(new java.awt.Color(255, 255, 255));
        SobrePanelEleccionBase.setForeground(new java.awt.Color(255, 255, 255));
        SobrePanelEleccionBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelAñadirPresoBase.add(SobrePanelEleccionBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 40));

        InformacionGeneralPaneñ.setBackground(new java.awt.Color(255, 255, 255));
        InformacionGeneralPaneñ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setText("Ingresar foto preso");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        InformacionGeneralPaneñ.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 340, 140, 30));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Vista previa foto del preso");
        InformacionGeneralPaneñ.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, -1, -1));

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
        jPanel8.add(InputNombrePreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 390, 30));

        InputApellidoPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputApellidoPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputApellidoPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputApellidoPreso.setBorder(null);
        InputApellidoPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputApellidoPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputApellidoPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 330, 30));

        InputEdadPreso.setBackground(new java.awt.Color(180, 180, 195));
        InputEdadPreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InputEdadPreso.setForeground(new java.awt.Color(0, 0, 0));
        InputEdadPreso.setBorder(null);
        InputEdadPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputEdadPresoActionPerformed(evt);
            }
        });
        jPanel8.add(InputEdadPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 350, 30));

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
        InformacionGeneralPaneñ.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 240, 340));

        cancelarD3.setBackground(new java.awt.Color(56, 7, 7));
        cancelarD3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cancelarD3.setForeground(new java.awt.Color(255, 255, 255));
        cancelarD3.setText("Cancelar y volver");
        cancelarD3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarD3ActionPerformed(evt);
            }
        });
        InformacionGeneralPaneñ.add(cancelarD3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 140, 30));

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
        jLabel13.setText("Meses");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 60, 20));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Nivel de seguridad:");
        jPanel9.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 140, 20));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Nivel de riesgo:");
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Sección asignada:");
        jPanel9.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 170, -1));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 100, 10));

        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 130, 10));

        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 130, 10));

        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 320, 10));

        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, 310, 10));

        jPanel12.setBackground(new java.awt.Color(29, 35, 51));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jPanel12.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 180, -1));

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 790, 10));
        jPanel9.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, -1, -1));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Fecha de salida:");
        jPanel9.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 120, -1));

        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 120, 10));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 320, 10));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Fecha de ingreso:");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 130, 30));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Condicion:");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 90, -1));

        seccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Sección A", "Sección B", "Sección C" }));
        seccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seccionActionPerformed(evt);
            }
        });
        jPanel9.add(seccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 150, 40));

        riesgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Riesgo bajo", "Riesgo medio", "Riesgo alto" }));
        jPanel9.add(riesgo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 150, 40));

        seguridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Bajo", "Medio", "Alto" }));
        jPanel9.add(seguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 150, 40));

        condicionComb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Condenado", "Condenado en traslado" }));
        jPanel9.add(condicionComb, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 180, 40));

        Siguiente2.setBackground(new java.awt.Color(49, 84, 15));
        Siguiente2.setForeground(new java.awt.Color(255, 255, 255));
        Siguiente2.setText("Siguiente");
        Siguiente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Siguiente2ActionPerformed(evt);
            }
        });
        jPanel9.add(Siguiente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 330, 140, 40));
        jPanel9.add(spinnerAñosSentencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 60, -1));
        jPanel9.add(spinnerMesesSentencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 60, -1));

        jLabel89.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("Sentencia:");
        jPanel9.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 80, 20));

        jLabel90.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Años");
        jPanel9.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 60, 20));
        jPanel9.add(jDayChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 177, -1, 0));
        jPanel9.add(datePickerFechaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 150, 170, 30));

        lblFechaSalidaCalculada.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblFechaSalidaCalculada.setForeground(new java.awt.Color(0, 0, 0));
        jPanel9.add(lblFechaSalidaCalculada, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 190, 30));

        InformacionJudicialPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 780, 390));

        cancelarD2.setBackground(new java.awt.Color(56, 7, 7));
        cancelarD2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cancelarD2.setText("Cancelar y volver");
        cancelarD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarD2ActionPerformed(evt);
            }
        });
        InformacionJudicialPanel.add(cancelarD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 140, 30));

        TabbedAñadirInformacionGeneral.addTab("Añadir  Informacion Judicial", InformacionJudicialPanel);

        PanelIngresarDelito.setBackground(new java.awt.Color(255, 255, 255));
        PanelIngresarDelito.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(180, 180, 195));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Delito:");
        jPanel6.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 50, 20));

        jLabel48.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Codigo:");
        jPanel6.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 60, 20));

        jSeparator36.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator36, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 360, 10));

        jSeparator37.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 360, 10));

        jLabel49.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Articulo Ley:");
        jPanel6.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 120, 20));

        jSeparator38.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 360, 10));

        jLabel50.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Gravedad:");
        jPanel6.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 80, 20));

        jSeparator39.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 360, 10));

        jLabel51.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Fecha comisión:");
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 130, 20));

        jSeparator40.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 360, 20));

        DescripcionDelito.setBackground(new java.awt.Color(255, 255, 255));
        DescripcionDelito.setColumns(20);
        DescripcionDelito.setForeground(new java.awt.Color(0, 0, 0));
        DescripcionDelito.setRows(5);
        DescripcionDelito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(DescripcionDelito);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 170, 210));

        jLabel26.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Descripcion delito:");
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 160, -1));

        jPanel4.setBackground(new java.awt.Color(29, 35, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, -1));

        delito.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Injuria", "Calumnia", "Daño en bien ajeno", "Violación de habitación ajena", "Inasistencia alimentaria", "Omisión de socorro", "Lesiones personales leves", "Falsedad en documento privado", "Usurpación de derechos", "Uso de documento falso", "Abuso de confianza", "Hurto simple", "Receptación", "Estafa", "Violación de cerraduras o sellos", "Fraude", "Violación de medidas sanitarias", "Invasión de tierras o edificaciones", "Suplantación de identidad", "Contrabando", "Hurto calificado", "Lesiones personales graves", "Extorsión", "Falsedad en documento público", "Lavado de activos", "Peculado por uso", "Violencia intrafamiliar", "Acoso sexual", "Acceso abusivo a sistema informático", "Suplantación en medios electrónicos", "Daño informático", "Tráfico de influencias", "Porte ilegal de armas", "Cohecho", "Concusión", "Prevaricato", "Abuso de autoridad", "Perturbación del orden público", "Enriquecimiento ilícito", "Tráfico de fauna o flora silvestre", "Minería ilegal", "Hurto agravado", "Homicidio culposo", "Acceso carnal abusivo con menor de 14 años", "Actos sexuales con menor de 14 años", "Acceso carnal violento", "Acto sexual violento", "Violación", "Secuestro simple", "Tráfico de estupefacientes", "Fabricación o porte de estupefacientes", "Concierto para delinquir", "Homicidio", "Homicidio agravado", "Tortura", "Desaparición forzada", "Terrorismo", "Rebelión", "Genocidio", "Crímenes de lesa humanidad" }));
        jPanel6.add(delito, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 300, 30));

        cantidadDelitos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        cantidadDelitos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadDelitosActionPerformed(evt);
            }
        });
        jPanel6.add(cantidadDelitos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 210, 30));

        jLabel53.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Cantidad de delitos");
        jPanel6.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        guardarDelito.setBackground(new java.awt.Color(51, 51, 51));
        guardarDelito.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        guardarDelito.setForeground(new java.awt.Color(255, 255, 255));
        guardarDelito.setText("Guardar delito");
        guardarDelito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarDelitoActionPerformed(evt);
            }
        });
        jPanel6.add(guardarDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, -1, 30));

        jSeparator42.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(jSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 360, 10));

        lblProgreso.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblProgreso.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(lblProgreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 430, 20));

        AñadirPreso.setBackground(new java.awt.Color(45, 79, 11));
        AñadirPreso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AñadirPreso.setForeground(new java.awt.Color(255, 255, 255));
        AñadirPreso.setText("Finalizar y guardar preso");
        AñadirPreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirPresoActionPerformed(evt);
            }
        });
        jPanel6.add(AñadirPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 190, 40));
        jPanel6.add(FechaComision, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 230, 30));

        Gravedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Baja", "Media", "Alta" }));
        jPanel6.add(Gravedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 260, 30));

        ArticuloLey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ArticuloLey.setForeground(new java.awt.Color(0, 0, 0));
        jPanel6.add(ArticuloLey, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 250, 30));

        Codigo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel6.add(Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 270, 30));

        PanelIngresarDelito.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 760, 420));

        cancelarD.setBackground(new java.awt.Color(56, 7, 7));
        cancelarD.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cancelarD.setText("Cancelar y volver");
        cancelarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarDActionPerformed(evt);
            }
        });
        PanelIngresarDelito.add(cancelarD, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, 140, 30));

        TabbedAñadirInformacionGeneral.addTab("Añadir Delito", PanelIngresarDelito);

        ActualizarInformacionPreso.setBackground(new java.awt.Color(255, 255, 255));
        ActualizarInformacionPreso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(180, 180, 195));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Grupo Sanguineo:");
        jPanel24.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel65.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Identificación:");
        jPanel24.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, 30));

        jPanel25.setBackground(new java.awt.Color(29, 35, 51));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 10));

        jPanel26.setBackground(new java.awt.Color(29, 35, 51));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 500));

        jPanel28.setBackground(new java.awt.Color(29, 35, 51));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 10, 480));

        jLabel68.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("Actualizar datos generales");
        jPanel24.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Nombre:");
        jPanel24.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel70.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Apellido:");
        jPanel24.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel71.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText("Edad: ");
        jPanel24.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel72.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setText("Nivel de seguridad:");
        jPanel24.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, -1, -1));

        jLabel73.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText("Nivel de riesgo");
        jPanel24.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, -1, 20));

        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 130, 10));

        jSeparator50.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator50, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 87, 280, 10));

        jSeparator51.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator51, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 280, 10));

        jSeparator52.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator52, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 280, 10));

        jSeparator53.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator53, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 280, 10));

        jSeparator54.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator54, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 280, 10));

        jSeparator55.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator55, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 120, 10));

        jSeparator56.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator56, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 120, 10));

        actualizarPreso.setBackground(new java.awt.Color(48, 49, 81));
        actualizarPreso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        actualizarPreso.setForeground(new java.awt.Color(255, 255, 255));
        actualizarPreso.setText("Actualizar");
        jPanel24.add(actualizarPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 180, 40));

        jLabel74.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setText("Añadir nuevos delitos");
        jPanel24.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, -1, -1));

        jPanel30.setBackground(new java.awt.Color(29, 35, 51));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 10, 470));

        jLabel67.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("Estatura:");
        jPanel24.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        jLabel75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Peso:");
        jPanel24.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jSeparator57.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator57, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 280, 10));

        jLabel76.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 0, 0));
        jLabel76.setText("Nacionalidad: ");
        jPanel24.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jSeparator58.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator58, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 280, 10));

        nuevoGrupoSanguineoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "A+", "A-", "B+", "B-" }));
        jPanel24.add(nuevoGrupoSanguineoCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 346, 140, 30));

        nuevaSeccionCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Sección A", "Sección B", "Sección C" }));
        nuevaSeccionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaSeccionComboActionPerformed(evt);
            }
        });
        jPanel24.add(nuevaSeccionCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 130, -1));

        jLabel77.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("Fecha de comisión:");
        jPanel24.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 280, 130, -1));

        jSeparator59.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator59, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, 100, 10));

        nuevoNivelSeguridadCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Baja", "Media", "Baja" }));
        jPanel24.add(nuevoNivelSeguridadCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 130, -1));

        jLabel78.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setText("Sección Asiganada:");
        jPanel24.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, -1, -1));

        jSeparator60.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator60, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 260, 80, 10));

        nuevoAislamientoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Sí", "No" }));
        jPanel24.add(nuevoAislamientoCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 130, -1));

        jLabel79.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setText("Vista previa");
        jPanel24.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, -1, -1));

        nuevaFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel24.add(nuevaFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 340, 110, 100));

        jLabel80.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("Actualizar datos judiciales");
        jPanel24.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        ActualizarFotoBoton.setText("Actualizar Foto");
        ActualizarFotoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarFotoBotonActionPerformed(evt);
            }
        });
        jPanel24.add(ActualizarFotoBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, -1, -1));

        jLabel81.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Mes");
        jPanel24.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, -1));

        NuevosDelitos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));
        NuevosDelitos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevosDelitosActionPerformed(evt);
            }
        });
        jPanel24.add(NuevosDelitos, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 120, 30));

        jSeparator61.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator61, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 70, 10));

        NuevosDelitosNombre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Injuria", "Calumnia", "Daño en bien ajeno", "Violación de habitación ajena", "Inasistencia alimentaria", "Omisión de socorro", "Lesiones personales leves", "Falsedad en documento privado", "Usurpación de derechos", "Uso de documento falso", "Abuso de confianza", "Hurto simple", "Receptación", "Estafa", "Violación de cerraduras o sellos", "Fraude", "Violación de medidas sanitarias", "Invasión de tierras o edificaciones", "Suplantación de identidad", "Contrabando", "Hurto calificado", "Lesiones personales graves", "Extorsión", "Falsedad en documento público", "Lavado de activos", "Peculado por uso", "Violencia intrafamiliar", "Acoso sexual", "Acceso abusivo a sistema informático", "Suplantación en medios electrónicos", "Daño informático", "Tráfico de influencias", "Porte ilegal de armas", "Cohecho", "Concusión", "Prevaricato", "Abuso de autoridad", "Perturbación del orden público", "Enriquecimiento ilícito", "Tráfico de fauna o flora silvestre", "Minería ilegal", "Hurto agravado", "Homicidio culposo", "Acceso carnal abusivo con menor de 14 años", "Actos sexuales con menor de 14 años", "Acceso carnal violento", "Acto sexual violento", "Violación", "Secuestro simple", "Tráfico de estupefacientes", "Fabricación o porte de estupefacientes", "Concierto para delinquir", "Homicidio", "Homicidio agravado", "Tortura", "Desaparición forzada", "Terrorismo", "Rebelión", "Genocidio", "Crímenes de lesa humanidad" }));
        NuevosDelitosNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevosDelitosNombreActionPerformed(evt);
            }
        });
        jPanel24.add(NuevosDelitosNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 116, 190, 30));

        jLabel82.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setText("Delito:");
        jPanel24.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 50, -1));

        jSeparator62.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator62, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, 50, 10));

        jLabel83.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setText("Codigo:");
        jPanel24.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 70, -1));

        jSeparator63.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator63, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, 120, 10));

        jLabel84.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 0));
        jLabel84.setText("Cantidad de delitos:");
        jPanel24.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 130, -1));

        jSeparator64.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator64, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 250, 10));

        jLabel85.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Articulo Ley:");
        jPanel24.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 200, 80, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Baja", "Media", "Alta", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel24.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 230, 170, 30));

        jSeparator65.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator65, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, 130, 10));

        jLabel86.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Gravedad:");
        jPanel24.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 240, 80, -1));

        jSeparator66.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator66, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 220, 250, 10));

        textAreaDescripcion.setColumns(20);
        textAreaDescripcion.setRows(5);
        jScrollPane4.setViewportView(textAreaDescripcion);

        jPanel24.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(738, 340, 240, 60));

        jLabel87.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setText("Descripcion del delito:");
        jPanel24.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 320, 160, -1));

        guardarNuevoDelito.setText("Guardar delito");
        guardarNuevoDelito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarNuevoDelitoActionPerformed(evt);
            }
        });
        jPanel24.add(guardarNuevoDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 170, -1));

        nuevoNombreField.setBackground(new java.awt.Color(180, 180, 195));
        nuevoNombreField.setBorder(null);
        nuevoNombreField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoNombreFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevoNombreField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 210, 30));

        nuevoApellidoField.setBackground(new java.awt.Color(180, 180, 195));
        nuevoApellidoField.setBorder(null);
        nuevoApellidoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoApellidoFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevoApellidoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 210, 30));

        nuevaEdadField.setBackground(new java.awt.Color(180, 180, 195));
        nuevaEdadField.setBorder(null);
        nuevaEdadField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaEdadFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevaEdadField, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 220, 30));

        nuevaIdentificacionField.setBackground(new java.awt.Color(180, 180, 195));
        nuevaIdentificacionField.setBorder(null);
        nuevaIdentificacionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaIdentificacionFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevaIdentificacionField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 190, 30));

        nuevaEstaturaField.setBackground(new java.awt.Color(180, 180, 195));
        nuevaEstaturaField.setBorder(null);
        nuevaEstaturaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaEstaturaFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevaEstaturaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 210, 30));

        nuevoPesoField.setBackground(new java.awt.Color(180, 180, 195));
        nuevoPesoField.setBorder(null);
        nuevoPesoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoPesoFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevoPesoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 210, 30));

        nuevaNacionalidadField.setBackground(new java.awt.Color(180, 180, 195));
        nuevaNacionalidadField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaNacionalidadFieldActionPerformed(evt);
            }
        });
        jPanel24.add(nuevaNacionalidadField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 190, 30));

        jLabel88.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("En aislamiento:");
        jPanel24.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, 20));

        jSeparator67.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator67, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 100, 10));

        nuevoNivelRiesgoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Bajo", "Medio", "Alto" }));
        jPanel24.add(nuevoNivelRiesgoCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 130, -1));

        jPanel27.setBackground(new java.awt.Color(29, 35, 51));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, 10, 480));
        jPanel24.add(fechaComisionActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 276, 130, 30));
        jPanel24.add(nuevoAño, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 60, -1));
        jPanel24.add(nuevoMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 60, -1));

        jLabel91.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("Sentencia:");
        jPanel24.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));

        jLabel92.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 0));
        jLabel92.setText("Año");
        jPanel24.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        cod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cod.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(cod, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 180, 30));

        art.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        art.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(art, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 190, 160, 30));

        jPanel31.setBackground(new java.awt.Color(29, 35, 51));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel24.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 1030, 10));

        ActualizarInformacionPreso.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 1030, 480));

        jPanel29.setBackground(new java.awt.Color(29, 35, 51));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ActualizarInformacionPreso.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 1030, 10));

        jLabel44.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(153, 0, 0));
        jLabel44.setText("Los campos que no deseé modificar déjelos en blanco");
        ActualizarInformacionPreso.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, -1, -1));

        TabbedAñadirInformacionGeneral.addTab("Actualizar preso", ActualizarInformacionPreso);

        PanelAñadirPresoBase.add(TabbedAñadirInformacionGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 530));

        OficialDeRegistroView.addTab("Añadir Preso", PanelAñadirPresoBase);

        Perfil.setBackground(new java.awt.Color(255, 255, 255));
        Perfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelInfoBasicaODR.setBackground(new java.awt.Color(180, 180, 195));
        PanelInfoBasicaODR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelFotoOficialDeRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Images/preso_0_1743825377442.jpg"))); // NOI18N
        LabelFotoOficialDeRegistro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
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

        Perfil.add(PanelInfoBasicaODR, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 280, 490));

        jPanel3.setBackground(new java.awt.Color(139, 139, 157));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(35, 34, 34));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 20, 90, -1));

        Perfil.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("INFORMACIÓN PERSONAL");
        Perfil.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 220, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Identificación:");
        Perfil.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 80, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nombre:");
        Perfil.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 50, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Edad:");
        Perfil.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, 40, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Sexo:");
        Perfil.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 40, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nacionalidad:");
        Perfil.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 80, 20));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        Perfil.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 360, 10));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        Perfil.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 280, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        Perfil.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 360, 20));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        Perfil.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 360, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        Perfil.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 360, 10));

        jButton2.setBackground(new java.awt.Color(11, 53, 11));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
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
        Perfil.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 470, 160, 30));

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

        Perfil.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 480, 300));

        OficialDeRegistroView.addTab("Perfil", Perfil);

        Expediente.setBackground(new java.awt.Color(255, 255, 255));
        Expediente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelDatosGenerales.setBackground(new java.awt.Color(180, 180, 195));
        PanelDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelDatosgenerales.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelDatosgenerales.setForeground(new java.awt.Color(0, 0, 0));
        LabelDatosgenerales.setText("DATOS GENERALES");
        PanelDatosGenerales.add(LabelDatosgenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Numero de registro:");
        PanelDatosGenerales.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, 20));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Codigo Expediente:");
        PanelDatosGenerales.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Fecha de Apertura:");
        PanelDatosGenerales.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Estado:");
        PanelDatosGenerales.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, 20));

        jLabel43.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Juzgado:");
        PanelDatosGenerales.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, -1, -1));

        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 290, 10));

        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 290, 10));

        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 290, 10));

        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 290, 10));

        jSeparator33.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 290, 10));
        PanelDatosGenerales.add(RegistroNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 140, 20));
        PanelDatosGenerales.add(CodExpe, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 150, 20));
        PanelDatosGenerales.add(FechaAper, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 150, 20));
        PanelDatosGenerales.add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 220, 20));
        PanelDatosGenerales.add(Juzgado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 210, 20));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Nivel de adaptación: ");
        PanelDatosGenerales.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 150, -1));

        jSeparator41.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 290, 10));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Nivel de riesgo:");
        PanelDatosGenerales.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 110, -1));

        jSeparator35.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 290, 10));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Observaciones de conducta:");
        PanelDatosGenerales.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 200, 20));

        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 290, 10));

        ObservacionesConducta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelDatosGenerales.add(ObservacionesConducta, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 290, 80));

        nivelRiesgExp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nivelRiesgExp.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(nivelRiesgExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 170, 20));

        Expediente.add(PanelDatosGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 800, 260));

        tablaExpediente.setBackground(new java.awt.Color(255, 255, 255));
        tablaExpediente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaExpediente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Delito", "Código", "Fecha Sentencia", "Tiempo de condena", "Gravedad", "Fecha comisión", "Fecha de Salida"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaExpediente.setRowHeight(45);
        jScrollPane3.setViewportView(tablaExpediente);

        Expediente.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 800, 220));

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
        Expediente.add(botonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 90, -1));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("DESCRIPCION DELITO");
        Expediente.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 340, 170, -1));

        DescripDelito.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Expediente.add(DescripDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 370, 150, 160));

        jPanel1.setBackground(new java.awt.Color(42, 42, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(fotoPresoExpediente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 190));

        Expediente.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, 160, 210));

        OficialDeRegistroView.addTab("Expediente", Expediente);

        DatosPersonalesPreso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("DATOS PERSONALES");
        jPanel20.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, -1));

        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 360, 10));

        jLabel56.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Nombre:");
        jPanel20.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 70, 20));

        jLabel57.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Apellido:");
        jPanel20.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 110, 20));

        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 207, 360, -1));

        jLabel58.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Edad:");
        jPanel20.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 110, 20));

        jSeparator43.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator43, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 247, 360, -1));

        jLabel59.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Sexo:");
        jPanel20.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 110, 20));

        jSeparator44.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator44, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 360, -1));

        jLabel60.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Nacionalidad:");
        jPanel20.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 110, 20));

        jSeparator45.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator45, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 360, 10));

        jLabel61.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("Identificación:");
        jPanel20.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 110, 20));

        jSeparator46.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator46, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 360, 20));

        jLabel62.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Estatura:");
        jPanel20.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 110, 20));

        jSeparator47.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator47, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 360, -1));

        jLabel63.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Peso:");
        jPanel20.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 110, 20));

        jSeparator48.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator48, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 360, 10));

        jLabel64.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("Grupo Sanguineo:");
        jPanel20.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 140, 20));

        jLabel66.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel20.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 440, 450));

        jSeparator49.setForeground(new java.awt.Color(0, 0, 0));
        jPanel20.add(jSeparator49, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 360, 10));

        jPanel23.setBackground(new java.awt.Color(35, 46, 57));

        jPanel22.setBackground(new java.awt.Color(180, 180, 195));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ImagenPresoInformacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel22.add(ImagenPresoInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 210));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 320, 440));
        jPanel20.add(sangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 470, 210, 20));
        jPanel20.add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 270, 20));
        jPanel20.add(apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 270, 20));
        jPanel20.add(sexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 270, 20));
        jPanel20.add(edad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 270, 20));
        jPanel20.add(nacionali, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 270, 20));
        jPanel20.add(identi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 260, 20));
        jPanel20.add(estatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 270, 20));
        jPanel20.add(peso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 270, 20));

        regresar.setText("Regresar");
        regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarActionPerformed(evt);
            }
        });
        jPanel20.add(regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 90, 30));

        DatosPersonalesPreso.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1050, 580));

        OficialDeRegistroView.addTab("DatosPersonalesPreso", DatosPersonalesPreso);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel94.setBackground(new java.awt.Color(0, 0, 0));
        jLabel94.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(204, 0, 0));
        jLabel94.setText("Los campos que no desee modificar déjelos en blanco*");
        jPanel19.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 290, -1));

        jPanel32.setBackground(new java.awt.Color(180, 180, 195));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel95.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setText("Nacionalidad: ");
        jPanel32.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel96.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 0));
        jLabel96.setText("Identificación:");
        jPanel32.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 207, -1, 30));

        jLabel97.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(0, 0, 0));
        jLabel97.setText("Sexo:");
        jPanel32.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jPanel33.setBackground(new java.awt.Color(29, 35, 51));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel32.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 10));

        jPanel34.setBackground(new java.awt.Color(29, 35, 51));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel32.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 420));

        jPanel35.setBackground(new java.awt.Color(29, 35, 51));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel32.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 10, 400));

        jPanel36.setBackground(new java.awt.Color(29, 35, 51));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel32.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 10, 420));

        jLabel98.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 0));
        jLabel98.setText("DATOS PERSONALES");
        jPanel32.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        jLabel99.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(0, 0, 0));
        jLabel99.setText("Nombre:");
        jPanel32.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel100.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 0, 0));
        jLabel100.setText("Apellido:");
        jPanel32.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel101.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(0, 0, 0));
        jLabel101.setText("Edad: ");
        jPanel32.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel102.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(0, 0, 0));
        jLabel102.setText("Correo Electronico:");
        jPanel32.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        jLabel103.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(0, 0, 0));
        jLabel103.setText("Contraseña:");
        jPanel32.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, 20));

        jSeparator68.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator68, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 380, 10));

        jSeparator69.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator69, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 87, 310, 10));

        jSeparator70.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator70, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 310, 10));

        jSeparator71.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator71, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 310, 10));

        jSeparator72.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator72, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 310, 10));

        jSeparator73.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator73, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 310, 10));

        jSeparator74.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator74, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 160, 10));

        jSeparator75.setForeground(new java.awt.Color(0, 0, 0));
        jPanel32.add(jSeparator75, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 380, 10));

        jPanel37.setBackground(new java.awt.Color(29, 35, 51));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel32.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 940, 10));

        jLabel104.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(0, 0, 0));
        jLabel104.setText("CUENTA");
        jPanel32.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, -1, -1));
        jPanel32.add(nuevoCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 250, 30));
        jPanel32.add(nuevaEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 240, -1));
        jPanel32.add(nuevaIdenti, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 220, 30));
        jPanel32.add(nuevaNacio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 220, 30));

        nuevoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoNombreActionPerformed(evt);
            }
        });
        jPanel32.add(nuevoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 230, 30));

        nuevaContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaContraActionPerformed(evt);
            }
        });
        jPanel32.add(nuevaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 260, 30));

        nuevoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoApellidoActionPerformed(evt);
            }
        });
        jPanel32.add(nuevoApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 240, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel32.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 100, 30));

        LabelFOTO.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jPanel32.add(LabelFOTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 100, 90));

        jButton5.setText("Cambiar foto de perfil");
        jPanel32.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 160, -1));

        jPanel19.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 940, 420));

        BotonRegresar.setBackground(new java.awt.Color(30, 28, 28));
        BotonRegresar.setForeground(new java.awt.Color(255, 255, 255));
        BotonRegresar.setText("Regresar");
        BotonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegresarActionPerformed(evt);
            }
        });
        jPanel19.add(BotonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, -1, -1));

        jButton4.setBackground(new java.awt.Color(48, 49, 81));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Actualizar");
        jPanel19.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 180, 40));

        jPanel16.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 590));

        OficialDeRegistroView.addTab("ActualizarDatosODR", jPanel16);

        PRINCIPAL.add(OficialDeRegistroView, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, 628, Short.MAX_VALUE)
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
        TabbedAñadirInformacionGeneral.setSelectedIndex(0);


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

    private void identificacionBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificacionBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificacionBActionPerformed

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

    private void btnBuscarIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdentificacionActionPerformed
        btnBuscarIdentificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idBuscado = identificacionB.getText().trim();
                if (idBuscado.isEmpty()) {
                    return;
                }

                String identificacion = identificacionB.getText();
                List<Preso> todos = new PresoDAO().cargarTodos();
                DefaultTableModel model = (DefaultTableModel) TablaPresos.getModel();
                model.setRowCount(0);

                for (Preso preso : todos) {
                    if (preso.getIdentificacion().equalsIgnoreCase(identificacion)) {

                        ImageIcon foto = null;
                        if (preso.getFotoPath() != null && !preso.getFotoPath().isEmpty()) {
                            foto = cargarImagenPreso(preso.getFotoPath());
                        } else {
                            foto = new ImageIcon(getClass().getResource("/images/default_profile.png"));
                        }
                        model.addRow(new Object[]{
                            foto,
                            preso.getId(),
                            preso.getNombre(),
                            preso.getApellido(),
                            preso.getEdad(),
                            preso.getIdentificacion(),
                            preso.getNacionalidad(),
                            preso.getSeccionAsignada(),
                            preso.getCeldaAsignada()

                        });
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "NO se encontro ningun preso con la identificación " + identificacion);
                    }
                }
            }
        });
    }//GEN-LAST:event_btnBuscarIdentificacionActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

     

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            
        OficialDeRegistroView.setSelectedIndex(5);
        
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

    private void AñadirPresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirPresoActionPerformed

        finalizarPresoActionPerformed();

                }//GEN-LAST:event_AñadirPresoActionPerformed

    private Delito crearDelitoDesdeFormulario() {
        String nombreDelito = delito.getSelectedItem().toString();

        if (Codigo.getText().trim().isEmpty() || ArticuloLey.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un delito para obtener el código y el artículo de ley.");
        }

        int codigoDelito;
        try {
            codigoDelito = Integer.parseInt(Codigo.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El código del delito no es un número válido.");
        }

        String articuloLey = ArticuloLey.getText().trim();
        String gravedad = Gravedad.getSelectedItem().toString();

        Date fecha = FechaComision.getDate();
        if (fecha == null) {
            throw new IllegalArgumentException("Debe seleccionar una fecha de comisión del delito.");
        }

        LocalDate fechaComision = fecha.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String descripcionDelito = DescripcionDelito.getText().trim();

        return new Delito(codigoDelito, nombreDelito, articuloLey, gravedad, descripcionDelito, fechaComision);
    }

    private void actualizarProgreso() {
        lblProgreso.setText("Delito " + delitoActual + " de " + totalDelitos);
    }

    private void finalizarPresoActionPerformed() {
        try {
            if (delitosTemporales.size() < totalDelitos) {
                JOptionPane.showMessageDialog(this,
                        "Faltan delitos por ingresar. Debe ingresar " + totalDelitos + " delitos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validarDatosPersonales() || !validarDatosJudiciales()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar correctamente todos los datos personales y judiciales.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lblFoto.getIcon() == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una foto del preso",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Preso preso = crearPresoDesdeFormulario();
            boolean guardado = new PresoDAO().guardarPreso(preso, selectedImageFile);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Preso añadido correctamente con " + delitosTemporales.size() + " delitos.");
                limpiarFormularioCompleto();

                cargarDatosEnTabla();

                OficialDeRegistroView.setSelectedIndex(0);

                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el preso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            manejarError(e);
        }
    }

    private Preso crearPresoDesdeFormulario() {
        try {
            String nombrePreso = InputNombrePreso.getText().trim();
            String apellido = InputApellidoPreso.getText().trim();
            int edad = Integer.parseInt(InputEdadPreso.getText().trim());
            String nacionalidad = InputNacionalidadPreso.getText().trim();
            String sexo = InputSexoPreso.getSelectedItem().toString();
            float estatura = Float.parseFloat(InputEstaturaPreso.getText().trim());
            float peso = Float.parseFloat(InputPesoPreso.getText().trim());
            String tipoSangre = TipoSangreCombobox.getSelectedItem().toString();
            String identificacion = InputIdentificacionPreso.getText().trim();
            String condicion = condicionComb.getSelectedItem().toString();
            String seccionAsignada = seccion.getSelectedItem().toString();
            String nivelDeRiesgo = riesgo.getSelectedItem().toString();
            String nivelDeSeguridad = seguridad.getSelectedItem().toString();

            int años = (Integer) spinnerAñosSentencia.getValue();
            int meses = (Integer) spinnerMesesSentencia.getValue();

            Date fechaSeleccionada = datePickerFechaIngreso.getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha de ingreso");
                return null;
            }

            LocalDate fechaIngreso = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            Sentencia sentencia = new Sentencia(años, meses, fechaIngreso);

            Celda celdaAsignada = new CeldaDAO().asignarCeldaDisponible(seccionAsignada);
            if (celdaAsignada == null) {
                JOptionPane.showMessageDialog(null, "No hay celdas disponibles en la sección " + seccionAsignada);
                return null;
            }

            Preso preso = new Preso(
                    nombrePreso,
                    apellido,
                    edad,
                    0,
                    sexo,
                    nacionalidad,
                    identificacion,
                    estatura,
                    peso,
                    new ArrayList<>(delitosTemporales),
                    sentencia,
                    nivelDeSeguridad,
                    seccionAsignada,
                    condicion,
                    celdaAsignada.getNombreFormateado(),
                    false,
                    nivelDeRiesgo,
                    0,
                    tipoSangre,
                    null
            );

            return preso;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en formato numérico: " + e.getMessage());
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear preso: " + e.getMessage());
            return null;
        }
    }

    private void limpiarCamposDelito() {
        delito.setSelectedIndex(0);
        Codigo.setText("");
        ArticuloLey.setText("");
        Gravedad.setSelectedIndex(0);
        FechaComision.setDate(null);
        DescripcionDelito.setText("");
    }

    private void limpiarCamposPersonales() {
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
    }

    private void limpiarCamposJudiciales() {
        seccion.setSelectedIndex(0);
        seguridad.setSelectedIndex(0);
        riesgo.setSelectedIndex(0);
        condicionComb.setSelectedIndex(0);

        datePickerFechaIngreso.setDate(null);

        lblFechaSalidaCalculada.setText("");

        spinnerAñosSentencia.setValue(0);
        spinnerMesesSentencia.setValue(0);
    }

    private void limpiarFormularioCompleto() {
        limpiarCamposDelito();
        limpiarCamposPersonales();
        limpiarCamposJudiciales();
        delitosTemporales.clear();
        delitoActual = 1;
        totalDelitos = 1;
        lblProgreso.setText("Delito 1 de 1");
    }


    private void Siguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Siguiente1ActionPerformed

        TabbedAñadirInformacionGeneral.setSelectedIndex(1);
    }//GEN-LAST:event_Siguiente1ActionPerformed

    private void SelectorSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectorSeccionActionPerformed

        String seccionSeleccionada = SelectorSeccion.getSelectedItem().toString();
        List<Preso> todosLosPresos = new PresoDAO().cargarTodos();
        DefaultTableModel model = (DefaultTableModel) TablaPresos.getModel();
        model.setRowCount(0);

        for (Preso preso : todosLosPresos) {

            if (preso.getSeccionAsignada().equalsIgnoreCase(seccionSeleccionada)) {

                ImageIcon foto = null;
                if (preso.getFotoPath() != null && !preso.getFotoPath().isEmpty()) {
                    foto = cargarImagenPreso(preso.getFotoPath());
                } else {
                    foto = new ImageIcon(getClass().getResource("/images/default_profile.png"));
                }

                model.addRow(new Object[]{
                    foto,
                    preso.getNombre(),
                    preso.getApellido(),
                    preso.getEdad(),
                    preso.getSexo(),
                    preso.getNacionalidad(),
                    preso.getIdentificacion(),
                    preso.getCeldaAsignada(),
                    preso.getSeccionAsignada()

                });
            }
        }
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

    private void seccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seccionActionPerformed

    private void guardarDelitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarDelitoActionPerformed
        try {
            if (!validarDatosDelito()) {
                mostrarErroresDelito();
                return;
            }

            Delito delito = crearDelitoDesdeFormulario();

            delitosTemporales.add(delito);

            new DelitoDAO().guardarDelito(delito);

            actualizarProgreso();

            if (delitoActual < totalDelitos) {
                delitoActual++;
                limpiarCamposDelito();
                JOptionPane.showMessageDialog(this,
                        "Delito guardado. Por favor ingrese el delito " + delitoActual + " de " + totalDelitos,
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Todos los delitos han sido ingresados. Puede finalizar el proceso.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            manejarError(e);
        }

    }//GEN-LAST:event_guardarDelitoActionPerformed

    private void cantidadDelitosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadDelitosActionPerformed
        cantidadDelitos.addActionListener(e -> {
            totalDelitos = Integer.parseInt(cantidadDelitos.getSelectedItem().toString());
            delitoActual = 1;
            lblProgreso.setText("Delito 1 de " + totalDelitos);
            delitosTemporales.clear();
        });

    }//GEN-LAST:event_cantidadDelitosActionPerformed

    private void btnBuscarIdentificacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarIdentificacionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarIdentificacionMouseClicked

    private void btnRestaurarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarTablaActionPerformed
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
                preso.getSeccionAsignada(),
                preso.getCeldaAsignada()

            });
        }

        TablaPresos.revalidate();
        TablaPresos.repaint();

    }//GEN-LAST:event_btnRestaurarTablaActionPerformed

    private void TablaPresosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPresosMousePressed


    }//GEN-LAST:event_TablaPresosMousePressed

    private void regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarActionPerformed
        OficialDeRegistroView.setSelectedIndex(0);
    }//GEN-LAST:event_regresarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        OficialDeRegistroView.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancelarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarDActionPerformed

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cancelar y borrar los datos?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            limpiarCamposDelito();
            OficialDeRegistroView.setSelectedIndex(0);

        }


    }//GEN-LAST:event_cancelarDActionPerformed

    private void cancelarD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarD2ActionPerformed
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cancelar y borrar los datos?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            limpiarCamposJudiciales();
            OficialDeRegistroView.setSelectedIndex(0);

        }
    }//GEN-LAST:event_cancelarD2ActionPerformed

    private void cancelarD3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarD3ActionPerformed
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cancelar y borrar los datos?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            limpiarCamposPersonales();
            OficialDeRegistroView.setSelectedIndex(0);

        }

    }//GEN-LAST:event_cancelarD3ActionPerformed

    private void ActualizarFotoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarFotoBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActualizarFotoBotonActionPerformed

    private void NuevosDelitosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevosDelitosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevosDelitosActionPerformed

    private void NuevosDelitosNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevosDelitosNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevosDelitosNombreActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void guardarNuevoDelitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarNuevoDelitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarNuevoDelitoActionPerformed

    private void nuevoNombreFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoNombreFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoNombreFieldActionPerformed

    private void nuevoApellidoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoApellidoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoApellidoFieldActionPerformed

    private void nuevaEdadFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaEdadFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaEdadFieldActionPerformed

    private void nuevaIdentificacionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaIdentificacionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaIdentificacionFieldActionPerformed

    private void nuevaEstaturaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaEstaturaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaEstaturaFieldActionPerformed

    private void nuevoPesoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoPesoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoPesoFieldActionPerformed

    private void nuevaNacionalidadFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaNacionalidadFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaNacionalidadFieldActionPerformed

    private void nuevaSeccionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaSeccionComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaSeccionComboActionPerformed

    private void BotonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegresarActionPerformed

        OficialDeRegistroView.setSelectedIndex(2);
        
    }//GEN-LAST:event_BotonRegresarActionPerformed

    private void nuevoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoNombreActionPerformed

    private void nuevaContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaContraActionPerformed

    private void nuevoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoApellidoActionPerformed

    private void mostrarErroresDelito() {
        StringBuilder errorMsg = new StringBuilder("Errores en datos del delito:\n");

        if (delito.getSelectedIndex() == 0) {
            errorMsg.append("- Nombre del delito vacío\n");
        }
        if (Codigo.getText().trim().isEmpty()) {
            errorMsg.append("- Código vacío\n");
        } else if (!validarNumero(Codigo.getText().trim(), true)) {
            errorMsg.append("- Código debe ser numérico\n");
        }
        if (ArticuloLey.getText().trim().isEmpty()) {
            errorMsg.append("- Artículo de ley vacío\n");
        }
        if (Gravedad.getSelectedIndex() == 0) {
            errorMsg.append("- Gravedad vacía\n");
        }
        if (FechaComision.getDate() == null) {
            errorMsg.append("- Fecha vacía\n");
        } else {
            LocalDate fechaComision = FechaComision.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String fechaFormateada = fechaComision.toString();
            if (!validarFecha(fechaFormateada)) {
                errorMsg.append("- Formato de fecha inválido (use AAAA-MM-DD)\n");
            }
        }

        if (DescripcionDelito.getText().trim().isEmpty()) {
            errorMsg.append("- Descripción vacía\n");
        }

        JOptionPane.showMessageDialog(this, errorMsg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void manejarError(Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

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
                preso.getSeccionAsignada(),
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
    private javax.swing.JButton ActualizarFotoBoton;
    private javax.swing.JPanel ActualizarInformacionPreso;
    private javax.swing.JLabel ArticuloLey;
    private javax.swing.JButton AñadirPreso;
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JButton BotonRegresar;
    private javax.swing.JLabel CodExpe;
    private javax.swing.JLabel Codigo;
    private javax.swing.JPanel DatosPersonalesPreso;
    private javax.swing.JLabel DescripDelito;
    private javax.swing.JTextArea DescripcionDelito;
    private javax.swing.JLabel Estado;
    private javax.swing.JPanel Expediente;
    private javax.swing.JLabel FechaAper;
    private com.toedter.calendar.JDateChooser FechaComision;
    private javax.swing.JComboBox<String> Gravedad;
    private javax.swing.JLabel ImagenPresoInformacion;
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
    private javax.swing.JLabel LabelFOTO;
    private javax.swing.JLabel LabelFotoOficialDeRegistro;
    private javax.swing.JLabel LabelIdentificacionIG;
    private javax.swing.JLabel LabelNacionalidadIG;
    private javax.swing.JLabel LabelNombreiIG;
    private javax.swing.JLabel LabelNumeroPlaca;
    private javax.swing.JLabel LabelRango;
    private javax.swing.JLabel LabelSexoIG;
    private javax.swing.JLabel LabelTurno;
    private javax.swing.JComboBox<String> NuevosDelitos;
    private javax.swing.JComboBox<String> NuevosDelitosNombre;
    private javax.swing.JLabel ObservacionesConducta;
    private javax.swing.JTabbedPane OficialDeRegistroView;
    private javax.swing.JPanel PRINCIPAL;
    private javax.swing.JPanel PanelAñadirPresoBase;
    private javax.swing.JPanel PanelAñadirPresoTitulo;
    private javax.swing.JPanel PanelDatosGenerales;
    private javax.swing.JPanel PanelInfoBasicaODR;
    private javax.swing.JPanel PanelIngresarDelito;
    private javax.swing.JPanel PanelPerfilTitulo;
    private javax.swing.JPanel PanelPresosTitulo;
    private javax.swing.JPanel PanelTablaPresoBase;
    private javax.swing.JPanel Perfil;
    private javax.swing.JLabel RegistroNum;
    private javax.swing.JComboBox<String> SelectorSeccion;
    private javax.swing.JButton Siguiente1;
    private javax.swing.JButton Siguiente2;
    private javax.swing.JPanel SobrePanelEleccionBase;
    private javax.swing.JTabbedPane TabbedAñadirInformacionGeneral;
    private javax.swing.JTable TablaPresos;
    private javax.swing.JComboBox<String> TipoSangreCombobox;
    private javax.swing.JButton actualizarPreso;
    private javax.swing.JLabel apellido;
    private javax.swing.JLabel art;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JButton btnBuscarIdentificacion;
    private javax.swing.JButton btnRestaurarTabla;
    private javax.swing.JButton cancelarD;
    private javax.swing.JButton cancelarD2;
    private javax.swing.JButton cancelarD3;
    private javax.swing.JComboBox<String> cantidadDelitos;
    private javax.swing.JLabel cod;
    private javax.swing.JComboBox<String> condicionComb;
    private com.toedter.calendar.JDateChooser datePickerFechaIngreso;
    private javax.swing.JComboBox<String> delito;
    private javax.swing.JLabel edad;
    private javax.swing.JLabel estatura;
    private com.toedter.calendar.JDateChooser fechaComisionActualizar;
    private javax.swing.JLabel fotoPresoExpediente;
    private javax.swing.JButton guardarDelito;
    private javax.swing.JButton guardarNuevoDelito;
    private javax.swing.JLabel identi;
    private javax.swing.JTextField identificacionB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
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
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator42;
    private javax.swing.JSeparator jSeparator43;
    private javax.swing.JSeparator jSeparator44;
    private javax.swing.JSeparator jSeparator45;
    private javax.swing.JSeparator jSeparator46;
    private javax.swing.JSeparator jSeparator47;
    private javax.swing.JSeparator jSeparator48;
    private javax.swing.JSeparator jSeparator49;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator50;
    private javax.swing.JSeparator jSeparator51;
    private javax.swing.JSeparator jSeparator52;
    private javax.swing.JSeparator jSeparator53;
    private javax.swing.JSeparator jSeparator54;
    private javax.swing.JSeparator jSeparator55;
    private javax.swing.JSeparator jSeparator56;
    private javax.swing.JSeparator jSeparator57;
    private javax.swing.JSeparator jSeparator58;
    private javax.swing.JSeparator jSeparator59;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator60;
    private javax.swing.JSeparator jSeparator61;
    private javax.swing.JSeparator jSeparator62;
    private javax.swing.JSeparator jSeparator63;
    private javax.swing.JSeparator jSeparator64;
    private javax.swing.JSeparator jSeparator65;
    private javax.swing.JSeparator jSeparator66;
    private javax.swing.JSeparator jSeparator67;
    private javax.swing.JSeparator jSeparator68;
    private javax.swing.JSeparator jSeparator69;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator70;
    private javax.swing.JSeparator jSeparator71;
    private javax.swing.JSeparator jSeparator72;
    private javax.swing.JSeparator jSeparator73;
    private javax.swing.JSeparator jSeparator74;
    private javax.swing.JSeparator jSeparator75;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblFechaSalidaCalculada;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblProgreso;
    private javax.swing.JLabel nacionali;
    private javax.swing.JLabel nivelRiesgExp;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nuevaContra;
    private javax.swing.JTextField nuevaEdad;
    private javax.swing.JTextField nuevaEdadField;
    private javax.swing.JTextField nuevaEstaturaField;
    private javax.swing.JLabel nuevaFoto;
    private javax.swing.JTextField nuevaIdenti;
    private javax.swing.JTextField nuevaIdentificacionField;
    private javax.swing.JTextField nuevaNacio;
    private javax.swing.JTextField nuevaNacionalidadField;
    private javax.swing.JComboBox<String> nuevaSeccionCombo;
    private javax.swing.JComboBox<String> nuevoAislamientoCombo;
    private javax.swing.JTextField nuevoApellido;
    private javax.swing.JTextField nuevoApellidoField;
    private com.toedter.components.JSpinField nuevoAño;
    private javax.swing.JTextField nuevoCorreo;
    private javax.swing.JComboBox<String> nuevoGrupoSanguineoCombo;
    private com.toedter.components.JSpinField nuevoMes;
    private javax.swing.JComboBox<String> nuevoNivelRiesgoCombo;
    private javax.swing.JComboBox<String> nuevoNivelSeguridadCombo;
    private javax.swing.JTextField nuevoNombre;
    private javax.swing.JTextField nuevoNombreField;
    private javax.swing.JTextField nuevoPesoField;
    private javax.swing.JLabel peso;
    private javax.swing.JPopupMenu ppMenuTablaPresos;
    private javax.swing.JButton regresar;
    private javax.swing.JComboBox<String> riesgo;
    private javax.swing.JLabel sangre;
    private javax.swing.JComboBox<String> seccion;
    private javax.swing.JComboBox<String> seguridad;
    private javax.swing.JLabel sexo;
    private com.toedter.components.JSpinField spinnerAñosSentencia;
    private com.toedter.components.JSpinField spinnerMesesSentencia;
    private javax.swing.JTable tablaExpediente;
    private javax.swing.JTextArea textAreaDescripcion;
    // End of variables declaration//GEN-END:variables
}
