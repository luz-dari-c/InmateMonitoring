/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import DAO.VisitaDAO;
import DAO.VisitanteDAO;
import Informacion.ExpedientePreso;
import Model.Visita;
import Model.Visitante;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class HistorialVisitasPreso extends javax.swing.JPanel {

    private String identificacionPreso;

    public HistorialVisitasPreso(String identificacionPreso) {
        this.identificacionPreso = identificacionPreso;
        initComponents();
        configurarTablaImagenes();
        InicializarMenu();
        cargarDatosEnTablaVisita(identificacionPreso); 
    }

    public void InicializarMenu() {
        JMenuItem HistorialVisitantes = new JMenuItem("Historial visitantes");
        ppMenuVisitas.add(HistorialVisitantes);

        TablaHistorialVisitasPDC.setComponentPopupMenu(ppMenuVisitas);

        HistorialVisitantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PanelHistorialVisitantes.setVisible(true);

                HistorialVisitasPreso.this.revalidate();
                HistorialVisitasPreso.this.repaint();
            }
        });
    }

    private void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaHistorialVisitantes.getModel();
        modelo.setRowCount(0);

        VisitanteDAO visitanteDAO = new VisitanteDAO();
        List<Visitante> visitantes = visitanteDAO.cargarTodos();

        for (Visitante visitante : visitantes) {
            ImageIcon foto = null;
            if (visitante.getFotoPath() != null && !visitante.getFotoPath().isEmpty()) {
                foto = cargarImagenVisitante(visitante.getFotoPath());
            } else {
                foto = new ImageIcon(getClass().getResource("/images/default_profile.png"));
            }

            modelo.addRow(new Object[]{
                foto,
                visitante.getId(),
                visitante.getNombre(),
                visitante.getApellido(),
                visitante.getEdad(),
                visitante.getIdentificacion(),
                visitante.getSexo(),
                visitante.getNacionalidad(),
                visitante.getRelacionConPreso(),});
        }

        TablaHistorialVisitantes.revalidate();
        TablaHistorialVisitantes.repaint();
    }

    private ImageIcon cargarImagenVisitante(String path) {
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
        TablaHistorialVisitantes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        TablaHistorialVisitantes.setRowHeight(65);
        TablaHistorialVisitantes.getColumnModel().getColumn(0).setPreferredWidth(70);
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

    private void cargarDatosEnTablaVisita(String identificacionPreso) {
        DefaultTableModel modelo = (DefaultTableModel) TablaHistorialVisitasPDC.getModel();
        modelo.setRowCount(0);

        VisitaDAO visitaDAO = new VisitaDAO();
        List<Visita> visitas = visitaDAO.cargarPorIdentificacionPreso(identificacionPreso);

        for (Visita visita : visitas) {
            for (Visitante visitante : visita.getVisitantes()) {
                modelo.addRow(new Object[]{
                    visita.getId(),
                    visitante.getIdentificacion(),
                    visita.getFechaVisita(),
                    visita.getHoraVisita(),
                    visita.getDuracionVisita(),
                    visita.getTipoVisita(),
                    visita.getLugarVisita(),
                    identificacionPreso
                });
            }
        }

        TablaHistorialVisitasPDC.revalidate();
        TablaHistorialVisitasPDC.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ppMenuVisitas = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        BotonRegresar = new javax.swing.JButton();
        TabbedHistorialVisitas = new javax.swing.JTabbedPane();
        PanelHistorialVisitas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaHistorialVisitasPDC = new javax.swing.JTable();
        BarraDeBusquedaCodigoVisitas = new javax.swing.JTextField();
        BuscarVisitaPorCodigoBoton = new javax.swing.JButton();
        PanelHistorialVisitantes = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaHistorialVisitantes = new javax.swing.JTable();
        BarraDeBusquedaPorIdentificacionVisitantes = new javax.swing.JTextField();
        BuscarVisitantePorIdentificacionPDC = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 35, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("HISTORIAL DE VISITAS");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, 30));

        BotonRegresar.setText("Regresar");
        BotonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(BotonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 70));

        PanelHistorialVisitas.setBackground(new java.awt.Color(255, 255, 255));
        PanelHistorialVisitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaHistorialVisitasPDC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Identificación visitante", "Fecha", "Hora", "Duracion", "Tipo de visita", "Lugar", "Visitado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaHistorialVisitasPDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaHistorialVisitasPDCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaHistorialVisitasPDC);
        if (TablaHistorialVisitasPDC.getColumnModel().getColumnCount() > 0) {
            TablaHistorialVisitasPDC.getColumnModel().getColumn(0).setResizable(false);
            TablaHistorialVisitasPDC.getColumnModel().getColumn(2).setResizable(false);
            TablaHistorialVisitasPDC.getColumnModel().getColumn(3).setResizable(false);
            TablaHistorialVisitasPDC.getColumnModel().getColumn(4).setResizable(false);
            TablaHistorialVisitasPDC.getColumnModel().getColumn(5).setResizable(false);
            TablaHistorialVisitasPDC.getColumnModel().getColumn(6).setResizable(false);
        }

        PanelHistorialVisitas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 1020, 390));

        BarraDeBusquedaCodigoVisitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarraDeBusquedaCodigoVisitasActionPerformed(evt);
            }
        });
        PanelHistorialVisitas.add(BarraDeBusquedaCodigoVisitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 580, 30));

        BuscarVisitaPorCodigoBoton.setText("Buscar");
        PanelHistorialVisitas.add(BuscarVisitaPorCodigoBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, 80, 30));

        TabbedHistorialVisitas.addTab("Historial de visitas", PanelHistorialVisitas);

        PanelHistorialVisitantes.setBackground(new java.awt.Color(255, 255, 255));
        PanelHistorialVisitantes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaHistorialVisitantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificacion", "Sexo", "Nacionalidad", "Relación", "N° Visitas"
            }
        ));
        jScrollPane2.setViewportView(TablaHistorialVisitantes);

        PanelHistorialVisitantes.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 990, 410));
        PanelHistorialVisitantes.add(BarraDeBusquedaPorIdentificacionVisitantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 600, 30));

        BuscarVisitantePorIdentificacionPDC.setText("Buscar");
        PanelHistorialVisitantes.add(BuscarVisitantePorIdentificacionPDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, 90, 30));

        TabbedHistorialVisitas.addTab("Historial de visitante", PanelHistorialVisitantes);

        add(TabbedHistorialVisitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1050, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void BarraDeBusquedaCodigoVisitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarraDeBusquedaCodigoVisitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BarraDeBusquedaCodigoVisitasActionPerformed

    private void BotonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegresarActionPerformed
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();

            new PersonalDeControl().setVisible(true);
        }    }//GEN-LAST:event_BotonRegresarActionPerformed

    private void TablaHistorialVisitasPDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaHistorialVisitasPDCMouseClicked

     }//GEN-LAST:event_TablaHistorialVisitasPDCMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BarraDeBusquedaCodigoVisitas;
    private javax.swing.JTextField BarraDeBusquedaPorIdentificacionVisitantes;
    private javax.swing.JButton BotonRegresar;
    private javax.swing.JButton BuscarVisitaPorCodigoBoton;
    private javax.swing.JButton BuscarVisitantePorIdentificacionPDC;
    private javax.swing.JPanel PanelHistorialVisitantes;
    private javax.swing.JPanel PanelHistorialVisitas;
    private javax.swing.JTabbedPane TabbedHistorialVisitas;
    private javax.swing.JTable TablaHistorialVisitantes;
    private javax.swing.JTable TablaHistorialVisitasPDC;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu ppMenuVisitas;
    // End of variables declaration//GEN-END:variables
}
