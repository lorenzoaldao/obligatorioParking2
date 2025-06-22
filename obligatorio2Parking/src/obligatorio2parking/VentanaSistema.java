/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package obligatorio2parking;

import dominio.Sistema;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;

public class VentanaSistema extends javax.swing.JFrame {
    private Sistema sistema;
    // Variable para rastrear el estado del tema
    private boolean temaOscuro = false;
        
    public VentanaSistema() {
        initComponents();
        // Agregar ActionListener al botón
        btnClaroOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClaroOscuroActionPerformed(evt);
            }
        });
    }
    public VentanaSistema(Sistema sistema) {
        this.sistema = sistema;
        initComponents();
        //Precargar va aqui.
    }   
    public void precargarSistema(){
        int resp = JOptionPane.showConfirmDialog(this, "Desea pre-cargar datos?");
        if( resp == 0){
            sistema.recuperarDatos();
        } 
    }
    // Método para aplicar el tema
    private void cambiarTema() {
        try {
            if (temaOscuro) {
                // Aplicar tema oscuro
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                
                // Personalizar colores para tema oscuro
                UIManager.put("Panel.background", new Color(45, 45, 45));
                UIManager.put("Button.background", new Color(60, 60, 60));
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("Label.foreground", Color.WHITE);
                UIManager.put("MenuBar.background", new Color(35, 35, 35));
                UIManager.put("Menu.background", new Color(35, 35, 35));
                UIManager.put("Menu.foreground", Color.WHITE);
                UIManager.put("MenuItem.background", new Color(45, 45, 45));
                UIManager.put("MenuItem.foreground", Color.WHITE);
                UIManager.put("MenuItem.selectionBackground", new Color(45, 45, 45));
                UIManager.put("Frame.background", new Color(45, 45, 45));
                
                // Cambiar color de fondo de la ventana
                getContentPane().setBackground(new Color(45, 45, 45));
                
            } else {
                // Aplicar tema claro (Nimbus)
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
               
                // Restaurar colores por defecto
                UIManager.put("Panel.background", Color.WHITE);
                UIManager.put("Button.background", Color.WHITE);
                UIManager.put("Button.foreground", Color.BLACK);
                UIManager.put("Label.foreground", Color.BLACK);
                UIManager.put("MenuBar.background", Color.WHITE);
                UIManager.put("Menu.background", Color.WHITE);
                UIManager.put("Menu.foreground", Color.BLACK);
                UIManager.put("MenuItem.background", Color.WHITE);
                UIManager.put("MenuItem.foreground", Color.WHITE);
                UIManager.put("MenuItem.selectionBackground", Color.WHITE);
                UIManager.put("Frame.background", Color.WHITE);
                
                getContentPane().setBackground(null);
            }
            
            // Actualizar la interfaz
            SwingUtilities.updateComponentTreeUI(this);
            this.repaint();
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
                window.repaint();

                // Ajustar color de fondo de contenido
                if (window instanceof JFrame) {
                    Container contentPane = ((JFrame) window).getContentPane();
                    if (temaOscuro) {
                        contentPane.setBackground(new Color(45, 45, 45));
                    } else {
                        contentPane.setBackground(null);
                    }
                } 

                // Corregir manualmente el color de texto de JMenuItem en modo claro
                if (!temaOscuro) {
                    actualizarMenuItems(window);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cambiar el tema: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarMenuItems(Window window) {
        if (window instanceof JFrame) {
            JMenuBar menuBar = ((JFrame) window).getJMenuBar();
            if (menuBar != null) {
                for (MenuElement menuElement : menuBar.getSubElements()) {
                    if (menuElement instanceof JMenu) {
                        actualizarMenuItemsRecursivo((JMenu) menuElement);
                    }
                }
            }
        }
    }

    private void actualizarMenuItemsRecursivo(JMenu menu) {
        menu.setForeground(Color.BLACK);
        for (Component comp : menu.getMenuComponents()) {
            if (comp instanceof JMenuItem) {
                comp.setForeground(Color.BLACK);
            } else if (comp instanceof JMenu) {
                actualizarMenuItemsRecursivo((JMenu) comp);
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

        btnClaroOscuro = new javax.swing.JButton();
        jMenuPrincipal = new javax.swing.JMenuBar();
        menuGestion = new javax.swing.JMenu();
        itemGestionClientes = new javax.swing.JMenuItem();
        itemVehiculos = new javax.swing.JMenuItem();
        itemEmpleados = new javax.swing.JMenuItem();
        itemContratos = new javax.swing.JMenuItem();
        menuMovimientos = new javax.swing.JMenu();
        itemEntradas = new javax.swing.JMenuItem();
        itemSalidas = new javax.swing.JMenuItem();
        itemServicios = new javax.swing.JMenuItem();
        menuVarios = new javax.swing.JMenu();
        itemReportes = new javax.swing.JMenuItem();
        itemRecupDatos = new javax.swing.JMenuItem();
        itemGrabDatos = new javax.swing.JMenuItem();
        itemMiniJuego = new javax.swing.JMenuItem();
        itemInfoAutores = new javax.swing.JMenuItem();
        menuTerminar = new javax.swing.JMenu();
        itemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Obligatorio Prog 2 - Autores: Estudiantes: Jhonatan Adalid (320368) y Lorenzo Aldao (307239)");

        btnClaroOscuro.setText("Claro/Oscuro");
        btnClaroOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClaroOscuroActionPerformed(evt);
            }
        });

        menuGestion.setText("Gestión");

        itemGestionClientes.setText("Gestion de Clientes");
        itemGestionClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionClientesActionPerformed(evt);
            }
        });
        menuGestion.add(itemGestionClientes);

        itemVehiculos.setText("Gestion de Vehículos");
        itemVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVehiculosActionPerformed(evt);
            }
        });
        menuGestion.add(itemVehiculos);

        itemEmpleados.setText("Gestion de Empleados");
        itemEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEmpleadosActionPerformed(evt);
            }
        });
        menuGestion.add(itemEmpleados);

        itemContratos.setText("Gestion de Contratos");
        itemContratos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemContratosActionPerformed(evt);
            }
        });
        menuGestion.add(itemContratos);

        jMenuPrincipal.add(menuGestion);

        menuMovimientos.setText("Movimientos");

        itemEntradas.setText("Entradas");
        itemEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEntradasActionPerformed(evt);
            }
        });
        menuMovimientos.add(itemEntradas);

        itemSalidas.setText("Salidas");
        itemSalidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalidasActionPerformed(evt);
            }
        });
        menuMovimientos.add(itemSalidas);

        itemServicios.setText("Servicios Adicionales");
        itemServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemServiciosActionPerformed(evt);
            }
        });
        menuMovimientos.add(itemServicios);

        jMenuPrincipal.add(menuMovimientos);

        menuVarios.setText("Varios");

        itemReportes.setText("Reportes");
        itemReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReportesActionPerformed(evt);
            }
        });
        menuVarios.add(itemReportes);

        itemRecupDatos.setText("Recuperación de datos");
        itemRecupDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRecupDatosActionPerformed(evt);
            }
        });
        menuVarios.add(itemRecupDatos);

        itemGrabDatos.setText("Grabación de datos");
        itemGrabDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGrabDatosActionPerformed(evt);
            }
        });
        menuVarios.add(itemGrabDatos);

        itemMiniJuego.setText("MiniJuego");
        itemMiniJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMiniJuegoActionPerformed(evt);
            }
        });
        menuVarios.add(itemMiniJuego);

        itemInfoAutores.setText("Información de Autores");
        itemInfoAutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemInfoAutoresActionPerformed(evt);
            }
        });
        menuVarios.add(itemInfoAutores);

        jMenuPrincipal.add(menuVarios);

        menuTerminar.setText("Terminar");
        menuTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTerminarActionPerformed(evt);
            }
        });

        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuTerminar.add(itemSalir);

        jMenuPrincipal.add(menuTerminar);

        setJMenuBar(jMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnClaroOscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addComponent(btnClaroOscuro)
                .addGap(67, 67, 67))
        );

        setBounds(0, 0, 576, 324);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClaroOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClaroOscuroActionPerformed
        temaOscuro = !temaOscuro;
        cambiarTema();
    }//GEN-LAST:event_btnClaroOscuroActionPerformed

    // Resto de los métodos ActionPerformed existentes...
    private void itemGestionClientesActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaClientes ventana = new VentanaClientes(sistema);
        ventana.setVisible(true);
    }

    private void itemVehiculosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaVehiculos vv = new VentanaVehiculos(sistema);
        vv.setVisible(true);
    }

    private void itemSalidasActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaSalidas vs = new VentanaSalidas(sistema);
        vs.setVisible(true);
    }

    private void itemMiniJuegoActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaMiniJuego vm = new VentanaMiniJuego();
        vm.setVisible(true);
    }

    private void menuTerminarActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro que querés salir del sistema?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Un método para antes de la salida, guardar datos en caso que se creasen
            if(sistema.existeDatos()){ 
                sistema.grabarDatos();
            }
            System.exit(0);
        }
    }

    private void itemEntradasActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaEntradas ve = new VentanaEntradas(sistema);
        ve.setVisible(true);
    }

    private void itemEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaEmpleados ve = new VentanaEmpleados(sistema);
        ve.setVisible(true);
    }

    private void itemContratosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaContratos vc = new VentanaContratos(sistema);
        vc.setVisible(true);
    }

    private void itemServiciosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaServiciosAdicionales vsa = new VentanaServiciosAdicionales(sistema);
        vsa.setVisible(true);
    }

    private void itemReportesActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaReportes vr = new VentanaReportes(sistema);
        vr.setVisible(true);
    }

    private void itemRecupDatosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaRecuperarDatos vrd = new VentanaRecuperarDatos();
        vrd.setVisible(true);
    }

    private void itemGrabDatosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaGrabarDatos vgd = new VentanaGrabarDatos(sistema);
        vgd.setVisible(true);
    }

    private void itemInfoAutoresActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaAutores via = new VentanaAutores();
        via.setVisible(true);
    }

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
            java.util.logging.Logger.getLogger(VentanaSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaSistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClaroOscuro;
    private javax.swing.JMenuItem itemContratos;
    private javax.swing.JMenuItem itemEmpleados;
    private javax.swing.JMenuItem itemEntradas;
    private javax.swing.JMenuItem itemGestionClientes;
    private javax.swing.JMenuItem itemGrabDatos;
    private javax.swing.JMenuItem itemInfoAutores;
    private javax.swing.JMenuItem itemMiniJuego;
    private javax.swing.JMenuItem itemRecupDatos;
    private javax.swing.JMenuItem itemReportes;
    private javax.swing.JMenuItem itemSalidas;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemServicios;
    private javax.swing.JMenuItem itemVehiculos;
    private javax.swing.JMenuBar jMenuPrincipal;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuMovimientos;
    private javax.swing.JMenu menuTerminar;
    private javax.swing.JMenu menuVarios;
    // End of variables declaration//GEN-END:variables
}