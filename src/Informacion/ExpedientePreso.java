package Informacion;

import DAO.PresoDAO;
import Model.Delito;
import Model.ExpedienteJudicial;
import Model.Preso;
import View.OficialDeRegistro;
import View.RoundedPanel;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ExpedientePreso extends javax.swing.JPanel {

    public ExpedientePreso() {
        initComponents();
    }

    public void cargarDatosPreso(Preso preso) {

        if (preso == null || preso.getExpediente() == null) {
            return;
        }

        ExpedienteJudicial expediente = preso.getExpediente();
        Delito delito = preso.getDelito();

        RegistroNum.setText(String.valueOf(expediente.getNumeroRegistro()));
        CodExpe.setText(String.valueOf(expediente.getCodigoExpediente()));
        FechaAper.setText(String.valueOf(expediente.getFechaApertura()));
        Estado.setText(String.valueOf(expediente.getEstado()));
        Juzgado.setText(String.valueOf(expediente.getJuzgado()));
        DescripDelito.setText(delito.getDescripcion());

        NivelAdaptacion.setText(expediente.getNivelAdaptacion());
        NivelRiesgo.setText(expediente.getNivelRiesgo());
    }

    private void cargarDatosExpedienteEnTabla(ExpedienteJudicial expediente, Preso preso) {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        if (expediente == null || expediente.getDelitos() == null) {
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tiempoCondena = preso.getSentencia() + " años";

        for (String delitoStr : expediente.getDelitos()) {
            String[] partes = delitoStr.split("\\|");

            modelo.addRow(new Object[]{
                partes.length > 0 ? partes[0] : "",
                partes.length > 1 ? partes[1] : "",
                expediente.getFechaSentencia() != null
                ? expediente.getFechaSentencia().format(dateFormatter) : "",
                tiempoCondena,
                partes.length > 2 ? partes[2] : "",
                partes.length > 3 ? partes[3] : ""
            });
        }

        jTable1.revalidate();
        jTable1.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonRegresar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        PanelDatosGenerales = new RoundedPanel(30);
        ;
        jLabel9 = new javax.swing.JLabel();
        LabelDatosgenerales = new javax.swing.JLabel();
        DescripDelito = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        RegistroNum = new javax.swing.JLabel();
        CodExpe = new javax.swing.JLabel();
        FechaAper = new javax.swing.JLabel();
        Estado = new javax.swing.JLabel();
        Juzgado = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        ObservacionesConducta = new javax.swing.JLabel();
        NivelAdaptacion = new javax.swing.JLabel();
        NivelRiesgo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 35, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("EXPEDIENTE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

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
        jPanel1.add(botonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, 80, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 50));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("SEGUIMIENTO EN PRISIÓN");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, 200, 20));

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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, 660, 190));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("INFORMACION LEGAL");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, -1, 20));

        PanelDatosGenerales.setBackground(new java.awt.Color(180, 180, 195));
        PanelDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("DESCRIPCION DELITO");
        PanelDatosGenerales.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));

        LabelDatosgenerales.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelDatosgenerales.setForeground(new java.awt.Color(0, 0, 0));
        LabelDatosgenerales.setText("DATOS GENERALES");
        PanelDatosGenerales.add(LabelDatosgenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        DescripDelito.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelDatosGenerales.add(DescripDelito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 280, 160));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Numero de registro:");
        PanelDatosGenerales.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Codigo Expediente:");
        PanelDatosGenerales.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha de Apertura:");
        PanelDatosGenerales.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Estado:");
        PanelDatosGenerales.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 20));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Juzgado:");
        PanelDatosGenerales.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 290, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 290, 10));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 290, 10));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 290, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        PanelDatosGenerales.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 290, 10));
        PanelDatosGenerales.add(RegistroNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 20));
        PanelDatosGenerales.add(CodExpe, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 150, 20));
        PanelDatosGenerales.add(FechaAper, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 150, 20));
        PanelDatosGenerales.add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 220, 20));
        PanelDatosGenerales.add(Juzgado, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 210, 20));

        add(PanelDatosGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 330, 500));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Observaciones de conducta:");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 440, 250, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Nivel de adaptación: ");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, 200, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nivel de riesgo:");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 400, 120, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 190, 10));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 620, 10));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 620, 10));

        ObservacionesConducta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(ObservacionesConducta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 440, 410, 100));
        add(NivelAdaptacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, 400, 20));
        add(NivelRiesgo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 460, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresarMouseClicked


              }//GEN-LAST:event_botonRegresarMouseClicked

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed
        OficialDeRegistro regresar = new OficialDeRegistro();
        regresar.setVisible(true);
        regresar.dispose();
    }//GEN-LAST:event_botonRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CodExpe;
    private javax.swing.JLabel DescripDelito;
    private javax.swing.JLabel Estado;
    private javax.swing.JLabel FechaAper;
    private javax.swing.JLabel Juzgado;
    private javax.swing.JLabel LabelDatosgenerales;
    private javax.swing.JLabel NivelAdaptacion;
    private javax.swing.JLabel NivelRiesgo;
    private javax.swing.JLabel ObservacionesConducta;
    private javax.swing.JPanel PanelDatosGenerales;
    private javax.swing.JLabel RegistroNum;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
