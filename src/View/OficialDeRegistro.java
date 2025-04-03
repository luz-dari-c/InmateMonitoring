/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Informacion.ExpedientePreso;
import Informacion.HistorialMedicoPreso;
import Informacion.HistorialVisitasPreso;
import Informacion.InformacionPreso;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenuItem;


public class OficialDeRegistro extends javax.swing.JFrame {

  
    public OficialDeRegistro() {
        initComponents();
        inicializarMenu();
    }

    public void inicializarMenu(){
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
        PanelPresos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PanelPerfil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelAñadirPreso = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        OficialDeRegistroView = new javax.swing.JTabbedPane();
        PanelTablaPresoBase = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPresos = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        SelectorSeccion = new javax.swing.JComboBox<>();
        PanelAñadirPresoBase = new javax.swing.JPanel();
        PanelPerfilBase = new javax.swing.JPanel();
        PanelInfoBasicaODR = new RoundedPanel(30);
        ;
        LabelFotoOficialDeRegistro = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BotonCerrarSesion = new javax.swing.JButton();
        LabelNombreODR = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelPresos.setBackground(new java.awt.Color(29, 35, 51));
        PanelPresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPresosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelPresosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelPresosMouseExited(evt);
            }
        });
        PanelPresos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PRESOS");
        PanelPresos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, -1));

        jPanel5.add(PanelPresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 370, 60));

        PanelPerfil.setBackground(new java.awt.Color(29, 35, 51));
        PanelPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelPerfilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelPerfilMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelPerfilMouseExited(evt);
            }
        });
        PanelPerfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PERFIL");
        PanelPerfil.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jPanel5.add(PanelPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 60));

        PanelAñadirPreso.setBackground(new java.awt.Color(29, 35, 51));
        PanelAñadirPreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelAñadirPresoMouseExited(evt);
            }
        });
        PanelAñadirPreso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑADIR PRESO");
        PanelAñadirPreso.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 140, -1));

        jPanel5.add(PanelAñadirPreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 350, 60));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 60));

        PanelTablaPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelTablaPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Foto", "Id", "Nombre", "Apellido", "Edad", "Identificación", "Nacionalidad", "Sección"
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
            TablaPresos.getColumnModel().getColumn(0).setResizable(false);
        }

        PanelTablaPresoBase.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 121, 900, 380));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        PanelTablaPresoBase.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 570, 40));

        jButton1.setText("Buscar");
        PanelTablaPresoBase.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 90, 40));

        SelectorSeccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelectorSeccion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SelectorSeccionItemStateChanged(evt);
            }
        });
        PanelTablaPresoBase.add(SelectorSeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        OficialDeRegistroView.addTab("Presos", PanelTablaPresoBase);

        PanelAñadirPresoBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelAñadirPresoBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        OficialDeRegistroView.addTab("Añadir Preso", PanelAñadirPresoBase);

        PanelPerfilBase.setBackground(new java.awt.Color(255, 255, 255));
        PanelPerfilBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelInfoBasicaODR.setBackground(new java.awt.Color(180, 180, 195));
        PanelInfoBasicaODR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelInfoBasicaODR.add(LabelFotoOficialDeRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 160, 160));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");
        PanelInfoBasicaODR.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 60, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Oficial De Registro");
        PanelInfoBasicaODR.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        BotonCerrarSesion.setText("Cerrar sesión");
        PanelInfoBasicaODR.add(BotonCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 120, 30));
        PanelInfoBasicaODR.add(LabelNombreODR, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 200, 20));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        PanelInfoBasicaODR.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 220, 20));

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

    private void PanelPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilMouseClicked

        OficialDeRegistroView.setSelectedIndex(2);

        
    }//GEN-LAST:event_PanelPerfilMouseClicked

    private void PanelPresosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosMouseClicked

        OficialDeRegistroView.setSelectedIndex(0);

    }//GEN-LAST:event_PanelPresosMouseClicked

    private void PanelAñadirPresoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoMouseClicked

        OficialDeRegistroView.setSelectedIndex(1);


    }//GEN-LAST:event_PanelAñadirPresoMouseClicked

    private void PanelPerfilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilMouseEntered

PanelPerfil.setBackground(new Color(43,54,84));
    }//GEN-LAST:event_PanelPerfilMouseEntered

    private void PanelPresosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosMouseEntered
PanelPresos.setBackground(new Color(43,54,84));
    }//GEN-LAST:event_PanelPresosMouseEntered

    private void PanelAñadirPresoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoMouseEntered
        PanelAñadirPreso.setBackground(new Color(43, 54, 84));
    }//GEN-LAST:event_PanelAñadirPresoMouseEntered

    private void PanelPerfilMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPerfilMouseExited

        PanelPerfil.setBackground(new Color(29,35,51));


    }//GEN-LAST:event_PanelPerfilMouseExited

    private void PanelPresosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelPresosMouseExited
        PanelPresos.setBackground(new Color(29,35,51));
    }//GEN-LAST:event_PanelPresosMouseExited

    private void PanelAñadirPresoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAñadirPresoMouseExited
        PanelAñadirPreso.setBackground(new Color(29,35,51));
    }//GEN-LAST:event_PanelAñadirPresoMouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void SelectorSeccionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SelectorSeccionItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectorSeccionItemStateChanged

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
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JLabel LabelApellidoIG;
    private javax.swing.JLabel LabelEdadIG;
    private javax.swing.JLabel LabelFotoOficialDeRegistro;
    private javax.swing.JLabel LabelIdentificacionIG;
    private javax.swing.JLabel LabelNacionalidadIG;
    private javax.swing.JLabel LabelNombreODR;
    private javax.swing.JLabel LabelNombreiIG;
    private javax.swing.JLabel LabelSexoIG;
    private javax.swing.JTabbedPane OficialDeRegistroView;
    private javax.swing.JPanel PanelAñadirPreso;
    private javax.swing.JPanel PanelAñadirPresoBase;
    private javax.swing.JPanel PanelInfoBasicaODR;
    private javax.swing.JPanel PanelPerfil;
    private javax.swing.JPanel PanelPerfilBase;
    private javax.swing.JPanel PanelPresos;
    private javax.swing.JPanel PanelTablaPresoBase;
    private javax.swing.JComboBox<String> SelectorSeccion;
    private javax.swing.JTable TablaPresos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPopupMenu ppMenuTablaPresos;
    // End of variables declaration//GEN-END:variables
}
