
package View;

import View.OficialDeRegistro;
import View.RoundedPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class ActualizarInformaciónODR extends javax.swing.JPanel {

   
    public ActualizarInformaciónODR() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BotonRegresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        nuevoCorreo = new javax.swing.JTextField();
        nuevaEdad = new javax.swing.JTextField();
        nuevaIdenti = new javax.swing.JTextField();
        nuevaNacio = new javax.swing.JTextField();
        nuevoNombre = new javax.swing.JTextField();
        nuevaContra = new javax.swing.JTextField();
        nuevoApellido = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        LabelFOTO = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(29, 35, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ACTUALIZAR INFORMACIÓN");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 210, -1));

        BotonRegresar.setText("Regresar");
        BotonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegresarActionPerformed(evt);
            }
        });
        jPanel2.add(BotonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 40));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setText("Los campos que no desee modificar déjelos en blanco*");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 290, -1));

        jPanel4.setBackground(new java.awt.Color(180, 180, 195));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Nacionalidad: ");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Identificación:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 207, -1, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Sexo:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jPanel3.setBackground(new java.awt.Color(29, 35, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 10));

        jPanel7.setBackground(new java.awt.Color(29, 35, 51));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 420));

        jPanel6.setBackground(new java.awt.Color(29, 35, 51));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 10, 400));

        jPanel5.setBackground(new java.awt.Color(29, 35, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 10, 370));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("DATOS PERSONALES");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Nombre:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Apellido:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Edad: ");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Correo Electronico:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contraseña:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, 20));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 380, 10));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 87, 310, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 310, 10));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 310, 10));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 310, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 310, 10));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 160, 10));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 380, 10));

        jPanel8.setBackground(new java.awt.Color(29, 35, 51));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 940, 10));

        jButton1.setBackground(new java.awt.Color(48, 49, 81));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Actualizar");
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, 180, 40));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("CUENTA");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));
        jPanel4.add(nuevoCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 250, 30));
        jPanel4.add(nuevaEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 240, -1));
        jPanel4.add(nuevaIdenti, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 220, 30));
        jPanel4.add(nuevaNacio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 220, 30));

        nuevoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoNombreActionPerformed(evt);
            }
        });
        jPanel4.add(nuevoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 230, 30));

        nuevaContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaContraActionPerformed(evt);
            }
        });
        jPanel4.add(nuevaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 260, 30));

        nuevoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoApellidoActionPerformed(evt);
            }
        });
        jPanel4.add(nuevoApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 240, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 100, 30));

        LabelFOTO.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jPanel4.add(LabelFOTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 270, 100, 90));

        jButton2.setText("Cambiar foto de perfil");
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 220, 160, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 940, 420));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 590));
    }// </editor-fold>//GEN-END:initComponents

    private void BotonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegresarActionPerformed

     
    }//GEN-LAST:event_BotonRegresarActionPerformed

    private void nuevoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoApellidoActionPerformed

    private void nuevoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoNombreActionPerformed

    private void nuevaContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaContraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonRegresar;
    private javax.swing.JLabel LabelFOTO;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField nuevaContra;
    private javax.swing.JTextField nuevaEdad;
    private javax.swing.JTextField nuevaIdenti;
    private javax.swing.JTextField nuevaNacio;
    private javax.swing.JTextField nuevoApellido;
    private javax.swing.JTextField nuevoCorreo;
    private javax.swing.JTextField nuevoNombre;
    // End of variables declaration//GEN-END:variables
}
