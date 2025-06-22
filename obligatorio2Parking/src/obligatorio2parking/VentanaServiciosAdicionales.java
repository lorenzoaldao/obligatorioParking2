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
public class VentanaServiciosAdicionales extends javax.swing.JFrame {
    private Sistema sistema;
    
    public VentanaServiciosAdicionales(Sistema sistema){
        this.sistema = sistema;
        initComponents();
        cargarDatosIniciales(); 
        configurarSpinners();
        cargarComboTiposServicio();
    }
    
    private void cargarDatosIniciales() {
        cargarComboVehiculos();
        cargarComboEmpleados();
        cargarListaServicios();
        configurarTabla();
        cargarDatosTabla();
    }
    
    // CORREGIDO: Agregar placeholder
    private void cargarComboVehiculos() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Seleccione un vehículo --"); // Placeholder
        
        if (sistema.getVehiculos() != null && !sistema.getVehiculos().isEmpty()) {
            for (Vehiculo v : sistema.getVehiculos()) {
                model.addElement(v.toString());
            }
        }
        comboVehiculo.setModel(model);
        System.out.println("Vehículos cargados: " + sistema.getVehiculos().size());
    }
    
    // CORREGIDO: Agregar placeholder
    private void cargarComboEmpleados() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Seleccione un empleado --"); // Placeholder
        
        if (sistema.getEmpleados() != null && !sistema.getEmpleados().isEmpty()) {
            for (Empleado emp : sistema.getEmpleados()) {
                model.addElement(emp.toString());
            }
        }
        comboEmpleado.setModel(model);
        System.out.println("Empleados cargados: " + sistema.getEmpleados().size());
    }
    
    private void cargarComboTiposServicio() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        // Agregar tipos de servicio comunes
        model.addElement("Lavado");
        model.addElement("Cambio de rueda");
        model.addElement("Limpieza de tapizado");
        model.addElement("Cambio de luces");
        model.addElement("Otro");
        comboTipoS.setModel(model);
    }
    
    private void cargarListaServicios() {
        javax.swing.DefaultListModel<String> model = new javax.swing.DefaultListModel<>();

        if (sistema.getServiciosAdicionales() != null && !sistema.getServiciosAdicionales().isEmpty()) {
            for (ServicioAdicional servicio : sistema.getServiciosAdicionales()) {
                String item = servicio.getTipoServicio() + " - " + 
                             servicio.getVehiculo().getMatricula() + " - " +
                             servicio.getFecha();
                model.addElement(item);
            }
        } else {
            model.addElement("No hay servicios registrados");
        }

        listaServicios.setModel(model);
    }

    private void configurarTabla() {
        // Configurar columnas de la tabla
        String[] columnas = {"Tipo Servicio", "Fecha", "Hora", "Vehículo", "Empleado", "Costo"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        tableDatos.setModel(model);

        // Ajustar ancho de columnas
        tableDatos.getColumnModel().getColumn(0).setPreferredWidth(100); // Tipo Servicio
        tableDatos.getColumnModel().getColumn(1).setPreferredWidth(80);  // Fecha
        tableDatos.getColumnModel().getColumn(2).setPreferredWidth(60);  // Hora
        tableDatos.getColumnModel().getColumn(3).setPreferredWidth(80);  // Vehículo
        tableDatos.getColumnModel().getColumn(4).setPreferredWidth(100); // Empleado
        tableDatos.getColumnModel().getColumn(5).setPreferredWidth(70);  // Costo
    }

    // CORREGIDO: Agregar todas las columnas
    private void cargarDatosTabla() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableDatos.getModel();
        model.setRowCount(0); // Limpiar tabla

        if (sistema.getServiciosAdicionales() != null && !sistema.getServiciosAdicionales().isEmpty()) {
            for (ServicioAdicional servicio : sistema.getServiciosAdicionales()) {
                Object[] fila = {
                    servicio.getTipoServicio(),
                    servicio.getFecha(),
                    servicio.getHora(),
                    servicio.getVehiculo().getMatricula(),
                    servicio.getEmpleado().getNombre(), // CORREGIDO: Agregar empleado
                    String.format("%.2f", servicio.getCosto()) // CORREGIDO: Agregar costo formateado
                };
                model.addRow(fila);
            }
        }
    }

    // CORREGIDO: Validación mejorada
    private boolean validarDatos() {
        // Verificar que existan datos en el sistema
        if (sistema.getVehiculos() == null || sistema.getVehiculos().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No hay vehículos registrados en el sistema.\nPrimero debe registrar vehículos.", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (sistema.getEmpleados() == null || sistema.getEmpleados().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No hay empleados registrados en el sistema.\nPrimero debe registrar empleados.", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (comboTipoS.getSelectedIndex() < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Seleccione un tipo de servicio", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (comboVehiculo.getSelectedIndex() <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Seleccione un vehículo válido", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (comboEmpleado.getSelectedIndex() <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Seleccione un empleado válido", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        double costo = (Double) spinCostoS.getValue();
        if (costo <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "El costo debe ser mayor a 0", 
                "Validación", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        comboTipoS.setSelectedIndex(0);
        comboVehiculo.setSelectedIndex(0);
        comboEmpleado.setSelectedIndex(0);
        spinFechaHora.setValue(new java.util.Date());
        spinCostoS.setValue(0.0);
    }

    // Método para refrescar los datos cuando sea necesario
    public void refrescarDatos() {
        cargarComboVehiculos();
        cargarComboEmpleados();
        cargarListaServicios();
        cargarDatosTabla();
    }

    // Configurar spinner de costo en el constructor
    private void configurarSpinners() {
        // Configurar spinner de fecha/hora
        spinFechaHora.setModel(new javax.swing.SpinnerDateModel());
        spinFechaHora.setEditor(new javax.swing.JSpinner.DateEditor(spinFechaHora, "dd/MM/yyyy HH:mm"));

        // Configurar spinner de costo
        spinCostoS.setModel(new javax.swing.SpinnerNumberModel(0.0, 0.0, 999999.0, 1.0));
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
        panelServiciosA = new javax.swing.JPanel();
        lblServiciosRealizados = new javax.swing.JLabel();
        comboTipoS = new javax.swing.JComboBox<>();
        spinFechaHora = new javax.swing.JSpinner();
        lblFechayHora = new javax.swing.JLabel();
        lblVehiculo = new javax.swing.JLabel();
        comboVehiculo = new javax.swing.JComboBox<>();
        lblEmpleado = new javax.swing.JLabel();
        comboEmpleado = new javax.swing.JComboBox<>();
        spinCostoS = new javax.swing.JSpinner();
        lblCostoS = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        lblTipoServ = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaServicios = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDatos = new javax.swing.JTable();
        lblDatos = new javax.swing.JLabel();

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
        setTitle("Servicios Adicionales");
        getContentPane().setLayout(null);

        lblServiciosRealizados.setText("Servicios realizados");

        comboTipoS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTipoS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoSActionPerformed(evt);
            }
        });

        lblFechayHora.setText("Fecha y hora");

        lblVehiculo.setText("Vehiculo");

        comboVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVehiculoActionPerformed(evt);
            }
        });

        lblEmpleado.setText("Empleado");

        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadoActionPerformed(evt);
            }
        });

        lblCostoS.setText("Costo del servicio");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        lblTipoServ.setText("Tipo de servicio");

        listaServicios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaServicios);

        tableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableDatos);

        lblDatos.setText("Datos");

        javax.swing.GroupLayout panelServiciosALayout = new javax.swing.GroupLayout(panelServiciosA);
        panelServiciosA.setLayout(panelServiciosALayout);
        panelServiciosALayout.setHorizontalGroup(
            panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosALayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelServiciosALayout.createSequentialGroup()
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboTipoS, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoServ))
                        .addGap(18, 18, 18)
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechayHora))
                        .addGap(15, 15, 15)
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVehiculo)
                            .addComponent(comboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelServiciosALayout.createSequentialGroup()
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmpleado))
                        .addGap(18, 18, 18)
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCostoS)
                            .addGroup(panelServiciosALayout.createSequentialGroup()
                                .addComponent(spinCostoS, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRegistrar))))
                    .addGroup(panelServiciosALayout.createSequentialGroup()
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblServiciosRealizados)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        panelServiciosALayout.setVerticalGroup(
            panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosALayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechayHora)
                    .addComponent(lblVehiculo)
                    .addComponent(lblTipoServ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTipoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelServiciosALayout.createSequentialGroup()
                        .addComponent(lblEmpleado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelServiciosALayout.createSequentialGroup()
                        .addComponent(lblCostoS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinCostoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRegistrar))))
                .addGap(18, 18, 18)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiciosRealizados)
                    .addComponent(lblDatos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelServiciosALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(panelServiciosA);
        panelServiciosA.setBounds(0, 0, 510, 300);

        setBounds(0, 0, 524, 368);
    }// </editor-fold>//GEN-END:initComponents

    private void comboTipoSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoSActionPerformed

    private void comboVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboVehiculoActionPerformed

    private void comboEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEmpleadoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (validarDatos()) {
            try {
                // Obtener datos del formulario
                String tipoServicio = (String) comboTipoS.getSelectedItem();

                // Obtener fecha y hora
                java.util.Date fechaHora = (java.util.Date) spinFechaHora.getValue();
                java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
                java.text.SimpleDateFormat formatoHora = new java.text.SimpleDateFormat("HH:mm");
                String fecha = formatoFecha.format(fechaHora);
                String hora = formatoHora.format(fechaHora);

                // Obtener vehículo y empleado seleccionados
                int indexVehiculo = comboVehiculo.getSelectedIndex() - 1; // -1 por el placeholder
                int indexEmpleado = comboEmpleado.getSelectedIndex() - 1;

                Vehiculo vehiculoSeleccionado = sistema.getVehiculos().get(indexVehiculo);
                Empleado empleadoSeleccionado = sistema.getEmpleados().get(indexEmpleado);

                // Obtener costo
                double costo = (Double) spinCostoS.getValue();

                // Agregar servicio al sistema
                boolean exitoso = sistema.agregarServicioAdicional(tipoServicio, fecha, hora, 
                                                                 vehiculoSeleccionado, empleadoSeleccionado, costo);

                if (exitoso) {
                    javax.swing.JOptionPane.showMessageDialog(this, 
                        "Servicio registrado exitosamente", 
                        "Éxito", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                    // Actualizar lista y tabla
                    cargarListaServicios();
                    cargarDatosTabla();

                    // Limpiar formulario
                    limpiarFormulario();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, 
                        "Error al registrar el servicio", 
                        "Error", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaServiciosAdicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaServiciosAdicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaServiciosAdicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaServiciosAdicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Sistema sistema = new Sistema();
                new VentanaServiciosAdicionales(sistema).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboTipoS;
    private javax.swing.JComboBox<String> comboVehiculo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCostoS;
    private javax.swing.JLabel lblDatos;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblFechayHora;
    private javax.swing.JLabel lblServiciosRealizados;
    private javax.swing.JLabel lblTipoServ;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JList<String> listaServicios;
    private javax.swing.JPanel panelServiciosA;
    private javax.swing.JSpinner spinCostoS;
    private javax.swing.JSpinner spinFechaHora;
    private javax.swing.JTable tableDatos;
    // End of variables declaration//GEN-END:variables
}
