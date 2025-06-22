/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package obligatorio2parking;

import dominio.*;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaReportes extends javax.swing.JFrame {
    
    private Sistema sistema;
    
    public VentanaReportes(Sistema sistema) {
        this.sistema = sistema;
        initComponents();
        configurarComponentes();
        cargarVehiculos();
        cargarEstadisticas();
    }
    
    private void configurarComponentes() {
        txtAreaServicios.setEditable(false);
        txtAreaEmpleadosMovimientos.setEditable(false);
        txtAreaClientesContratos.setEditable(false);
        txtAreaEstadia.setEditable(false);
        
        // Configurar tabla
        String[] columnas = {"Tipo", "Fecha", "Hora", "Vehículo", "Empleado", "Detalles"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVehiculosMovimientos.setModel(modelo);
    }
    
    private void cargarVehiculos() {
        comboVehiculoReportes.removeAllItems();
        comboVehiculoReportes.addItem("Seleccione un vehículo");
        
        for (Vehiculo vehiculo : sistema.getVehiculos()) {
            comboVehiculoReportes.addItem(vehiculo.getMatricula() + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        }
    }
    
    private void cargarHistorialVehiculo() {
        if (comboVehiculoReportes.getSelectedIndex() <= 0) {
            limpiarTabla();
            return;
        }
        
        String seleccion = (String) comboVehiculoReportes.getSelectedItem();
        String matricula = seleccion.split(" - ")[0];
        Vehiculo vehiculo = sistema.buscarVehiculoPorMatricula(matricula);
        
        if (vehiculo != null) {
            actualizarTabla("Todos", true);
        }
    }
    
    private void actualizarTabla(String filtro, boolean ordenAscendente) {
        if (comboVehiculoReportes.getSelectedIndex() <= 0) {
            return;
        }
        
        String seleccion = (String) comboVehiculoReportes.getSelectedItem();
        String matricula = seleccion.split(" - ")[0];
        Vehiculo vehiculo = sistema.buscarVehiculoPorMatricula(matricula);
        
        if (vehiculo == null) {
            return;
        }
        
        // Obtener historial directamente del sistema
        ArrayList<Object> historial = sistema.getHistorialVehiculo(vehiculo);
        
        // Aplicar filtro
        ArrayList<Object> historialFiltrado = new ArrayList<>();
        for (Object movimiento : historial) {
            if (filtro.equals("Todos") || 
                (filtro.equals("Entradas") && movimiento instanceof Entrada) ||
                (filtro.equals("Salidas") && movimiento instanceof Salida) ||
                (filtro.equals("Servicios") && movimiento instanceof ServicioAdicional)) {
                historialFiltrado.add(movimiento);
            }
        }
        
        // Aplicar ordenamiento
        Collections.sort(historialFiltrado, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String fecha1 = "", hora1 = "", fecha2 = "", hora2 = "";
                
                if (o1 instanceof Entrada) {
                    Entrada e = (Entrada) o1;
                    fecha1 = e.getFecha();
                    hora1 = e.getHora();
                } else if (o1 instanceof Salida) {
                    Salida s = (Salida) o1;
                    fecha1 = s.getFecha();
                    hora1 = s.getHora();
                } else if (o1 instanceof ServicioAdicional) {
                    ServicioAdicional sa = (ServicioAdicional) o1;
                    fecha1 = sa.getFecha();
                    hora1 = sa.getHora();
                }
                
                if (o2 instanceof Entrada) {
                    Entrada e = (Entrada) o2;
                    fecha2 = e.getFecha();
                    hora2 = e.getHora();
                } else if (o2 instanceof Salida) {
                    Salida s = (Salida) o2;
                    fecha2 = s.getFecha();
                    hora2 = s.getHora();
                } else if (o2 instanceof ServicioAdicional) {
                    ServicioAdicional sa = (ServicioAdicional) o2;
                    fecha2 = sa.getFecha();
                    hora2 = sa.getHora();
                }
                
                // Comparar fecha y hora
                String fechaHora1 = fecha1 + " " + hora1;
                String fechaHora2 = fecha2 + " " + hora2;
                
                int resultado = fechaHora1.compareTo(fechaHora2);
                return ordenAscendente ? resultado : -resultado;
            }
        });
        
        // Actualizar tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculosMovimientos.getModel();
        modelo.setRowCount(0);
        
        for (Object movimiento : historialFiltrado) {
            Object[] fila = new Object[6];
            
            if (movimiento instanceof Entrada) {
                Entrada entrada = (Entrada) movimiento;
                fila[0] = "ENTRADA";
                fila[1] = entrada.getFecha();
                fila[2] = entrada.getHora();
                fila[3] = entrada.getVehiculo().getMatricula();
                fila[4] = entrada.getEmpleado().getNombre();
                fila[5] = entrada.getNotas();
            } else if (movimiento instanceof Salida) {
                Salida salida = (Salida) movimiento;
                fila[0] = "SALIDA";
                fila[1] = salida.getFecha();
                fila[2] = salida.getHora();
                fila[3] = salida.getEntrada().getVehiculo().getMatricula();
                fila[4] = salida.getEmpleado().getNombre();
                fila[5] = salida.getComentario();
            } else if (movimiento instanceof ServicioAdicional) {
                ServicioAdicional servicio = (ServicioAdicional) movimiento;
                fila[0] = "SERVICIO";
                fila[1] = servicio.getFecha();
                fila[2] = servicio.getHora();
                fila[3] = servicio.getVehiculo().getMatricula();
                fila[4] = servicio.getEmpleado().getNombre();
                fila[5] = servicio.getTipoServicio() + " - $" + servicio.getCosto();
            }
            
            modelo.addRow(fila);
        }
    }
    
    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculosMovimientos.getModel();
        modelo.setRowCount(0);
    }
    
    private void exportarHistorial() {
        if (comboVehiculoReportes.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehículo primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String seleccion = (String) comboVehiculoReportes.getSelectedItem();
        String matricula = seleccion.split(" - ")[0];
        Vehiculo vehiculo = sistema.buscarVehiculoPorMatricula(matricula);
        
        if (vehiculo == null) {
            return;
        }
        
        // Obtener datos actuales de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculosMovimientos.getModel();
        
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String nombreArchivo = matricula + ".txt";
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("HISTORIAL DE MOVIMIENTOS - VEHÍCULO: " + matricula + "\n");
            writer.write("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("=" + "=".repeat(80) + "\n\n");
            
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String tipo = (String) modelo.getValueAt(i, 0);
                String fecha = (String) modelo.getValueAt(i, 1);
                String hora = (String) modelo.getValueAt(i, 2);
                String empleado = (String) modelo.getValueAt(i, 4);
                String detalles = (String) modelo.getValueAt(i, 5);
                
                writer.write(String.format("%s - %s %s - Empleado: %s - %s\n",
                    tipo, fecha, hora, empleado, detalles));
            }
            
            JOptionPane.showMessageDialog(this, "Historial exportado exitosamente a: " + nombreArchivo, 
                "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar el archivo: " + e.getMessage(), 
                "Error de Exportación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private class MovListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton cual = ((JButton) e.getSource());
            JOptionPane.showMessageDialog(null, "Info del botón: " + cual.getText());
        }
    }
    
    // SEPARADOR---------------------------------------------------------------
    // VENTANAS ESTADISTICAS GENERALES
    
    
    public void cargarEstadisticas() {
        // 1. Servicios más utilizados
        List<String> serviciosMasUsados = sistema.obtenerServiciosMasUtilizados();
        String textoServicios = "";
        for (String s : serviciosMasUsados) {
            textoServicios += "- " + s + "\n";
        }
        txtAreaServicios.setText(textoServicios);

        // 2. Clientes con más vehículos
        List<String> clientesTop = sistema.obtenerClientesConMasVehiculos(sistema.getClientes());
        String textoClientes = "";
        for (String s : clientesTop) {
            textoClientes += "- " + s + "\n";
        }
        txtAreaClientesContratos.setText(textoClientes);

        // 3. Empleados con menos movimientos
        List<Empleado> empleadosMenos = sistema.obtenerEmpleadosConMenosMovimientos(sistema.getEmpleados());
        String textoEmpleados = "";
        for (Empleado e : empleadosMenos) {
            textoEmpleados += "- " + e.getNombre() + "\n"; 
        }
        txtAreaEmpleadosMovimientos.setText(textoEmpleados);

        // 4. Estadías más largas
        List<Salida> estadiasLargas = sistema.obtenerEstadiasMasLargas();
        String textoEstadias = "";
        for (Salida s : estadiasLargas) {
            textoEstadias += "- " + s.getEntrada().getVehiculo().getMatricula()
                           + " - " + s.calcularTiempoEstadia() + "\n";
        }
        txtAreaEstadia.setText(textoEstadias);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pannelReportes = new javax.swing.JTabbedPane();
        frameHistorial = new javax.swing.JInternalFrame();
        comboVehiculoReportes = new javax.swing.JComboBox<>();
        lblVehiculo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVehiculosMovimientos = new javax.swing.JTable();
        btnOrdenar = new javax.swing.JButton();
        btnFiltrar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        frameMovimientos = new javax.swing.JInternalFrame();
        panelMovimientos = new javax.swing.JPanel();
        btnGrilla = new javax.swing.JButton();
        frameEstadisticas = new javax.swing.JInternalFrame();
        lblServiciosMasUsados = new javax.swing.JLabel();
        lblEmpleadosMovimientos = new javax.swing.JLabel();
        lblClientesContratos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaClientesContratos = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaEmpleadosMovimientos = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaServicios = new javax.swing.JTextArea();
        lblEstadiaLarga = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaEstadia = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reportes");
        setAlwaysOnTop(true);

        frameHistorial.setVisible(true);

        comboVehiculoReportes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboVehiculoReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVehiculoReportesActionPerformed(evt);
            }
        });

        lblVehiculo.setText("Vehículo:");

        tablaVehiculosMovimientos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaVehiculosMovimientos);

        btnOrdenar.setText("Ordenar fecha ascendente/descendente");
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        btnFiltrar.setText("Filtro por tipo de movimiento  ");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar ");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frameHistorialLayout = new javax.swing.GroupLayout(frameHistorial.getContentPane());
        frameHistorial.getContentPane().setLayout(frameHistorialLayout);
        frameHistorialLayout.setHorizontalGroup(
            frameHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameHistorialLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(frameHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVehiculo)
                    .addComponent(comboVehiculoReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOrdenar)
                    .addComponent(btnFiltrar)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );
        frameHistorialLayout.setVerticalGroup(
            frameHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameHistorialLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblVehiculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboVehiculoReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnOrdenar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFiltrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportar)
                .addContainerGap(141, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pannelReportes.addTab("Historial", frameHistorial);

        frameMovimientos.setVisible(true);

        panelMovimientos.setLayout(new java.awt.GridLayout(4, 3));

        btnGrilla.setText("Generar grilla");
        btnGrilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrillaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frameMovimientosLayout = new javax.swing.GroupLayout(frameMovimientos.getContentPane());
        frameMovimientos.getContentPane().setLayout(frameMovimientosLayout);
        frameMovimientosLayout.setHorizontalGroup(
            frameMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameMovimientosLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(panelMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnGrilla)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        frameMovimientosLayout.setVerticalGroup(
            frameMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameMovimientosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(frameMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frameMovimientosLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnGrilla)))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        pannelReportes.addTab("Movimientos", frameMovimientos);

        frameEstadisticas.setVisible(true);

        lblServiciosMasUsados.setText("Servicios más utilizados:");

        lblEmpleadosMovimientos.setText("Empleados con más movimientos:");

        lblClientesContratos.setText("Clientes más contratos:");

        txtAreaClientesContratos.setColumns(20);
        txtAreaClientesContratos.setRows(5);
        jScrollPane2.setViewportView(txtAreaClientesContratos);

        txtAreaEmpleadosMovimientos.setColumns(20);
        txtAreaEmpleadosMovimientos.setRows(5);
        jScrollPane3.setViewportView(txtAreaEmpleadosMovimientos);

        txtAreaServicios.setColumns(20);
        txtAreaServicios.setRows(5);
        jScrollPane4.setViewportView(txtAreaServicios);

        lblEstadiaLarga.setText("Estadía más larga:");

        txtAreaEstadia.setColumns(20);
        txtAreaEstadia.setRows(5);
        jScrollPane5.setViewportView(txtAreaEstadia);

        javax.swing.GroupLayout frameEstadisticasLayout = new javax.swing.GroupLayout(frameEstadisticas.getContentPane());
        frameEstadisticas.getContentPane().setLayout(frameEstadisticasLayout);
        frameEstadisticasLayout.setHorizontalGroup(
            frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameEstadisticasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frameEstadisticasLayout.createSequentialGroup()
                        .addComponent(lblEstadiaLarga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(frameEstadisticasLayout.createSequentialGroup()
                        .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServiciosMasUsados)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmpleadosMovimientos)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClientesContratos))
                        .addGap(50, 50, 50))))
        );
        frameEstadisticasLayout.setVerticalGroup(
            frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameEstadisticasLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiciosMasUsados)
                    .addComponent(lblEmpleadosMovimientos)
                    .addComponent(lblClientesContratos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addGap(47, 47, 47)
                .addGroup(frameEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEstadiaLarga)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pannelReportes.addTab("Estadísticas Generales", frameEstadisticas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(pannelReportes)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(pannelReportes)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGrillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrillaActionPerformed
        panelMovimientos.removeAll();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                JButton nuevo = new JButton(" ");
                nuevo.setMargin(new Insets(-5, -5, -5, -5));
                nuevo.setBackground(Color.BLACK);
                nuevo.setForeground(Color.WHITE);
                nuevo.setText("Día " + j + ", Hora " + i);
                nuevo.addActionListener(new MovListener());
                panelMovimientos.add(nuevo);
            }
        }
        panelMovimientos.revalidate();
        panelMovimientos.repaint();
    }//GEN-LAST:event_btnGrillaActionPerformed

    private void comboVehiculoReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVehiculoReportesActionPerformed
         cargarHistorialVehiculo();
    }//GEN-LAST:event_comboVehiculoReportesActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        if (comboVehiculoReportes.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehículo primero.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] opciones = {"Todos", "Entradas", "Salidas", "Servicios"};
        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el tipo de movimiento a mostrar:",
            "Filtrar Movimientos",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            "Todos"
        );
        
        if (seleccion != null) {
            // Determinar orden actual basado en el texto del botón
            boolean ordenAscendente = btnOrdenar.getText().contains("descendente");
            actualizarTabla(seleccion, ordenAscendente);
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
         exportarHistorial();
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        if (comboVehiculoReportes.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehículo primero.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Alternar orden
        boolean ordenAscendente = btnOrdenar.getText().contains("descendente");
        btnOrdenar.setText(ordenAscendente ? "Ordenar fecha ascendente" : "Ordenar fecha descendente");
        
        // Actualizar tabla con el nuevo orden
        actualizarTabla("Todos", !ordenAscendente);
    }//GEN-LAST:event_btnOrdenarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 Sistema sistema = new Sistema();
                 new VentanaReportes(sistema).setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGrilla;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> comboVehiculoReportes;
    private javax.swing.JInternalFrame frameEstadisticas;
    private javax.swing.JInternalFrame frameHistorial;
    private javax.swing.JInternalFrame frameMovimientos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblClientesContratos;
    private javax.swing.JLabel lblEmpleadosMovimientos;
    private javax.swing.JLabel lblEstadiaLarga;
    private javax.swing.JLabel lblServiciosMasUsados;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JPanel panelMovimientos;
    private javax.swing.JTabbedPane pannelReportes;
    private javax.swing.JTable tablaVehiculosMovimientos;
    private javax.swing.JTextArea txtAreaClientesContratos;
    private javax.swing.JTextArea txtAreaEmpleadosMovimientos;
    private javax.swing.JTextArea txtAreaEstadia;
    private javax.swing.JTextArea txtAreaServicios;
    // End of variables declaration//GEN-END:variables
}
