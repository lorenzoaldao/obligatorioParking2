/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package obligatorio2parking;

import dominio.*;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author MSI
 */
public class VentanaSalidas extends javax.swing.JFrame {
    private Sistema sistema;
    
    public VentanaSalidas(Sistema sistema){
        this.sistema = sistema;
        initComponents();
        cargarDatosIniciales();
        configurarSpinners();
    }
    
    private void cargarDatosIniciales() {
        cargarComboEmpleados();
        cargarComboEntradas();
    }
    
    private void cargarComboEmpleados() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Empleado emp : sistema.getEmpleados()) {
            System.out.println(sistema.getEmpleados());
            model.addElement(emp.toString());
        }
        comboEmpleadosS.setModel(model);
    }
    
    private void cargarComboEntradas() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        // Verificar si hay entradas disponibles
        if (sistema.getEntradasSinSalida() != null && !sistema.getEntradasSinSalida().isEmpty()) {
            for (Entrada ent : sistema.getEntradasSinSalida()) {
                model.addElement(ent.toString());
            }
        } else {
            model.addElement("No hay entradas disponibles");
        }

        comboEntradasS.setModel(model);
    }
    
    private void configurarSpinners() {
        // Configurar spinner de fecha/hora
        spinFechaHoraS.setModel(new javax.swing.SpinnerDateModel());
        spinFechaHoraS.setEditor(new javax.swing.JSpinner.DateEditor(spinFechaHoraS, "dd/MM/yyyy HH:mm"));
    }
    
    private String calcularTiempoEnParking(Entrada entrada, java.util.Date fechaHoraSalida) {
    try {
        // Combinar fecha y hora de entrada
        String fechaHoraEntradaStr = entrada.getFecha() + " " + entrada.getHora();
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        java.util.Date fechaHoraEntrada = formato.parse(fechaHoraEntradaStr);
        
        // Calcular diferencia en milisegundos
        long diferenciaMs = fechaHoraSalida.getTime() - fechaHoraEntrada.getTime();
        
        // Convertir a días, horas y minutos
        long dias = diferenciaMs / (24 * 60 * 60 * 1000);
        long horas = (diferenciaMs % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutos = (diferenciaMs % (60 * 60 * 1000)) / (60 * 1000);
        
        // Formatear resultado
        StringBuilder tiempo = new StringBuilder();
        if (dias > 0) {
            tiempo.append(dias).append(" día").append(dias > 1 ? "s" : "").append(", ");
        }
        if (horas > 0) {
            tiempo.append(horas).append(" hora").append(horas > 1 ? "s" : "").append(", ");
        }
        tiempo.append(minutos).append(" minuto").append(minutos != 1 ? "s" : "");
        
        return tiempo.toString();
        
    } catch (Exception e) {
        return "Error al calcular tiempo";
    }
}

    private void actualizarInformacion(Entrada entrada, String tiempo) {
        // Mostrar información del contrato si existe
        Contrato contrato = sistema.buscarContratoPorVehiculo(entrada.getVehiculo());
        if (contrato != null) {
            lblContrato.setText("Contrato: #" + contrato.getNumeroContrato() + 
                               " - $" + contrato.getValorMensual());
        } else {
            lblContrato.setText("Contrato: Sin contrato");
        }

        // Mostrar tiempo calculado
        lblTiempo.setText("Tiempo: " + tiempo);
    }

    private void limpiarFormulario() {
        if (comboEntradasS.getModel().getSize() > 0) {
            comboEntradasS.setSelectedIndex(0);
        }
        if (comboEmpleadosS.getModel().getSize() > 0) {
            comboEmpleadosS.setSelectedIndex(0);
        }
        txtAreaComentario.setText("");
        spinFechaHoraS.setValue(new java.util.Date());
        lblContrato.setText("Contrato: ");
        lblTiempo.setText("Tiempo: ");
    }

    // Método para actualizar información cuando se selecciona una entrada
    private void comboEntradasSActionPerformed(java.awt.event.ActionEvent evt) {
        if (comboEntradasS.getSelectedIndex() >= 0 && 
            sistema.getEntradasSinSalida() != null && 
            comboEntradasS.getSelectedIndex() < sistema.getEntradasSinSalida().size()) {

            Entrada entradaSeleccionada = sistema.getEntradasSinSalida().get(comboEntradasS.getSelectedIndex());

            // Mostrar información del contrato
            Contrato contrato = sistema.buscarContratoPorVehiculo(entradaSeleccionada.getVehiculo());
            if (contrato != null) {
                lblContrato.setText("Contrato: #" + contrato.getNumeroContrato() + 
                                   " - $" + contrato.getValorMensual());
            } else {
                lblContrato.setText("Contrato: Sin contrato");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        comboEntradasS = new javax.swing.JComboBox<>();
        lblEntradas = new javax.swing.JLabel();
        spinFechaHoraS = new javax.swing.JSpinner();
        lblFechaYHora = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaComentario = new javax.swing.JTextArea();
        lblComentario = new javax.swing.JLabel();
        comboEmpleadosS = new javax.swing.JComboBox<>();
        lblEmpleado = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        lblContrato = new javax.swing.JLabel();
        lblTiempo = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salidas");
        getContentPane().setLayout(null);

        comboEntradasS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEntradas.setText("Entradas");

        lblFechaYHora.setText("Fecha y hora");

        txtAreaComentario.setColumns(20);
        txtAreaComentario.setRows(5);
        jScrollPane1.setViewportView(txtAreaComentario);

        lblComentario.setText("Comentario");

        comboEmpleadosS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEmpleado.setText("Empleado ");

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        lblContrato.setText("Contrato: ");

        lblTiempo.setText("Tiempo: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(comboEntradasS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEntradas)
                        .addComponent(spinFechaHoraS, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFechaYHora)
                    .addComponent(lblComentario)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmpleado)
                    .addComponent(lblContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar)
                    .addComponent(comboEmpleadosS, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEntradas)
                    .addComponent(lblEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboEntradasS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboEmpleadosS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFechaYHora)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinFechaHoraS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComentario)
                    .addComponent(lblContrato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTiempo))
                .addContainerGap())
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 460, 300);

        setBounds(0, 0, 475, 308);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
          if (comboEntradasS.getSelectedIndex() >= 0 && comboEmpleadosS.getSelectedIndex() >= 0) {
          try {
              // Obtener la entrada seleccionada
              Entrada entradaSeleccionada = sistema.getEntradasSinSalida().get(comboEntradasS.getSelectedIndex());

              // Obtener el empleado seleccionado
              Empleado empleadoSeleccionado = sistema.getEmpleados().get(comboEmpleadosS.getSelectedIndex());

              // Obtener fecha y hora de salida del spinner
              java.util.Date fechaHoraSalida = (java.util.Date) spinFechaHoraS.getValue();
              java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
              java.text.SimpleDateFormat formatoHora = new java.text.SimpleDateFormat("HH:mm");

              String fechaSalida = formatoFecha.format(fechaHoraSalida);
              String horaSalida = formatoHora.format(fechaHoraSalida);

              // Calcular tiempo en parking
              String tiempoEnParking = calcularTiempoEnParking(entradaSeleccionada, fechaHoraSalida);

              // Obtener comentario
              String comentario = txtAreaComentario.getText().trim();

              // Agregar la salida al sistema
              boolean exitoso = sistema.agregarSalida(entradaSeleccionada, empleadoSeleccionado, 
                                                    fechaSalida, horaSalida, comentario);

              if (exitoso) {
                  // Mostrar información de la salida
                  String mensaje = "Salida registrada exitosamente\n" +
                                 "Vehículo: " + entradaSeleccionada.getVehiculo().getMatricula() + "\n" +
                                 "Tiempo en parking: " + tiempoEnParking;

                  javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Éxito", 
                                                          javax.swing.JOptionPane.INFORMATION_MESSAGE);

                  // Actualizar labels con información
                  actualizarInformacion(entradaSeleccionada, tiempoEnParking);

                  // Refrescar combo de entradas
                  cargarComboEntradas();

                  // Limpiar formulario
                  limpiarFormulario();
              } else {
                  javax.swing.JOptionPane.showMessageDialog(this, "Error al registrar la salida", 
                                                          "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
              }

          } catch (Exception e) {
              javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                                      "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
          }
      } else {
          javax.swing.JOptionPane.showMessageDialog(this, "Seleccione entrada y empleado", 
                                                  "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
      }
    }//GEN-LAST:event_btnConfirmarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaSalidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaSalidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaSalidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaSalidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 Sistema sistema = new Sistema();
                 new VentanaSalidas(sistema).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> comboEmpleadosS;
    private javax.swing.JComboBox<String> comboEntradasS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblComentario;
    private javax.swing.JLabel lblContrato;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblEntradas;
    private javax.swing.JLabel lblFechaYHora;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JSpinner spinFechaHoraS;
    private javax.swing.JTextArea txtAreaComentario;
    // End of variables declaration//GEN-END:variables
}
