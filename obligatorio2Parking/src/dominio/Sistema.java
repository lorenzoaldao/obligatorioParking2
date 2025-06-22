/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Sistema extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Empleado> empleados;
    private ArrayList<Contrato> contratos;
    private ArrayList<Entrada> entradas;
    private ArrayList<Salida> salidas;
    private ArrayList<ServicioAdicional> serviciosAdicionales;
    private int proximoNumeroContrato;
    
    public Sistema(){
        this.clientes = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.contratos = new ArrayList<>();
        this.entradas = new ArrayList<>();
        this.salidas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
        this.proximoNumeroContrato = 1;
    }
    
    // Métodos para Cliente
    public boolean agregarCliente(Cliente cliente){
        boolean resultado = false;
        if (buscarClientePorCedula(cliente.getCedula()) == null){
            resultado = clientes.add(cliente);
            if(resultado){
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public boolean eliminarCliente(String cedula){
        boolean resultado = false;
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null){
            for (int i = contratos.size() - 1; i >= 0; i--) {
                if (contratos.get(i).getCliente().equals(cliente)) {
                    contratos.remove(i);
                }
            }
            resultado = clientes.remove(cliente);
            if(resultado){
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public Cliente buscarClientePorCedula(String cedula){
        Cliente clienteEncontrado = null;
        for (Cliente cliente : clientes){
            if (cliente.getCedula().equals(cedula)){
                clienteEncontrado = cliente;
            }
        }
        return clienteEncontrado;
    }
    
    // Métodos para Vehículo
    public boolean agregarVehiculo(Vehiculo vehiculo) {
        boolean resultado = false;
        if (vehiculo != null && buscarVehiculoPorMatricula(vehiculo.getMatricula()) == null) {
            resultado = vehiculos.add(vehiculo);
            if (resultado) {
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public Vehiculo buscarVehiculoPorMatricula(String unaMatricula){
        Vehiculo vehiculoEncontrado = null;
        for (Vehiculo vehiculo : vehiculos){
            if (vehiculo.getMatricula().equals(unaMatricula)){
                vehiculoEncontrado = vehiculo;
            }
        }
        return vehiculoEncontrado;
    }
    
    // Métodos para Empleado
    public boolean agregarEmpleado(Empleado empleado) {
        boolean resultado = false;
        if (empleado != null 
            && buscarEmpleadoPorCedula(empleado.getCedula()) == null 
            && buscarEmpleadoPorNumero(empleado.getNumeroEmpleado()) == null) {

            resultado = empleados.add(empleado);
            if (resultado) {
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public Empleado buscarEmpleadoPorCedula(String cedulaE){
        Empleado empleadoEncontrado = null;
        for (Empleado empleado : empleados){
            if (empleado.getCedula().equals(cedulaE)){
                empleadoEncontrado = empleado;
            }
        }
        return empleadoEncontrado;
    }
    
    public Empleado buscarEmpleadoPorNumero(int numeroEmpleado){
        Empleado empleadoEncontrado = null;
        for (Empleado empleado : empleados){
            if (empleado.getNumeroEmpleado() == numeroEmpleado){
                empleadoEncontrado = empleado;
            }
        }
        return empleadoEncontrado;
    }
    
    // Métodos para Contrato
    public boolean agregarContrato(Vehiculo unVehiculo, Cliente unClienteC, Empleado unEmpleadoC, double unValorMensual){
        Contrato contrato = new Contrato(proximoNumeroContrato, unVehiculo, unClienteC, unEmpleadoC, unValorMensual);
        proximoNumeroContrato++;
        boolean resultado = contratos.add(contrato);
        if(resultado){
            setChanged();
            notifyObservers();
        }
        return resultado;
    }
    
    public Contrato buscarContratoPorVehiculo(Vehiculo vehiculoC){
        Contrato contratoEncontrado = null;
        for (Contrato contrato : contratos){
            if (contrato.getVehiculo().equals(vehiculoC)){
                contratoEncontrado = contrato;
            }
        }
        return contratoEncontrado;
    }
    
    // Métodos para Entrada
    public boolean agregarEntrada(Vehiculo vehiculoEntrada, String fechaEntrada, String horaEntrada, String notaEntrada, Empleado empleadoDeEntrada){
        boolean resultado = false;
        if (!vehiculoEstaEnParking(vehiculoEntrada)){
            Entrada entrada = new Entrada(vehiculoEntrada, fechaEntrada, horaEntrada, notaEntrada, empleadoDeEntrada);
            resultado = entradas.add(entrada);
            if(resultado){
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public boolean vehiculoEstaEnParking(Vehiculo vehiculoP){
        boolean estaEnParking = false;
        for (Entrada entrada : entradas){
            if (entrada.getVehiculo().equals(vehiculoP) && !tieneSalida(entrada)){
                estaEnParking = true;
            }
        }
        return estaEnParking;
    }
    
    public boolean tieneSalida(Entrada entrada){
        boolean tieneSalida = false;
        for (Salida salida : salidas) {
            if (salida.getEntrada().equals(entrada)){
                tieneSalida = true;
            }
        }
        return tieneSalida;
    }
    
    // Métodos para Salida
    public boolean agregarSalida(Entrada entrada, Empleado empleadoSalida, String fechaSalida, String horaSalida, String comentario){
        boolean resultado = false;
        if (!tieneSalida(entrada)){
            Salida salida = new Salida(entrada, empleadoSalida, fechaSalida, horaSalida, comentario);
            resultado = salidas.add(salida);
            if(resultado){
                setChanged();
                notifyObservers();
            }
        }
        return resultado;
    }
    
    public ArrayList<Entrada> getEntradasSinSalida(){
        ArrayList<Entrada> entradasSinSalida = new ArrayList<>();
        for (Entrada entrada : entradas){
            if (!tieneSalida(entrada)){
                entradasSinSalida.add(entrada);
            }
        }
        return entradasSinSalida;
    }
    
    // Métodos para Servicio Adicional
    public boolean agregarServicioAdicional(String tipoServicio, String fechaServicio, String horaServicio, Vehiculo vehiculoServicio, Empleado empleadoServicio, double costoDelServicio){
        ServicioAdicional servicio = new ServicioAdicional(tipoServicio, fechaServicio, horaServicio, vehiculoServicio, empleadoServicio, costoDelServicio);
        boolean resultado = serviciosAdicionales.add(servicio);
        if(resultado){
            setChanged();
            notifyObservers();
        }
        return resultado;
    }
    
    // Métodos para Reportes
    public ArrayList<Object> getHistorialVehiculo(Vehiculo vehiculo){
        ArrayList<Object> historial = new ArrayList<>();
        
        for (Entrada entrada : entradas){
            if (entrada.getVehiculo().equals(vehiculo)){
                historial.add(entrada);
            }
        }
        
        for (Salida salida : salidas){
            if (salida.getEntrada().getVehiculo().equals(vehiculo)){
                historial.add(salida);
            }
        }
        
        for (ServicioAdicional servicio : serviciosAdicionales){
            if (servicio.getVehiculo().equals(vehiculo)){
                historial.add(servicio);
            }
        }
        
        return historial;
    }
    
    public ArrayList<Object> getMovimientosPorFechaYHora(String fecha, int horaInicio, int horaFin){
        ArrayList<Object> movimientos = new ArrayList<>();
        
        for (Entrada entrada : entradas){
            if (entrada.getFecha().equals(fecha)){
                int hora = Integer.parseInt(entrada.getHora().split(":")[0]);
                if (hora >= horaInicio && hora <= horaFin){
                    movimientos.add(entrada);
                }
            }
        }
        
        for (Salida salida : salidas){
            if (salida.getFecha().equals(fecha)) {
                int hora = Integer.parseInt(salida.getHora().split(":")[0]);
                if (hora >= horaInicio && hora <= horaFin){
                    movimientos.add(salida);
                }
            }
        }
        
        for (ServicioAdicional servicio : serviciosAdicionales){
            if (servicio.getFecha().equals(fecha)) {
                int hora = Integer.parseInt(servicio.getHora().split(":")[0]);
                if (hora >= horaInicio && hora <= horaFin){
                    movimientos.add(servicio);
                }
            }
        }
        
        return movimientos;
    }
    
    public String getServicioMasUtilizado(){
        HashMap<String, Integer> conteoServicios = new HashMap<>();
        
        for (ServicioAdicional servicio : serviciosAdicionales){
            String tipo = servicio.getTipoServicio();
            conteoServicios.put(tipo, conteoServicios.getOrDefault(tipo, 0) + 1);
        }
        
        String servicioMasUtilizado = "";
        int maxUsos = 0;
        
        for (Map.Entry<String, Integer> entry : conteoServicios.entrySet()){
            if (entry.getValue() > maxUsos){
                maxUsos = entry.getValue();
                servicioMasUtilizado = entry.getKey();
            }
        }
        
        return servicioMasUtilizado;
    }
    
    public Cliente getClienteConMasVehiculos(){
        HashMap<Cliente, Integer> conteoVehiculos = new HashMap<>();
        
        for (Contrato contrato : contratos){
            Cliente cliente = contrato.getCliente();
            conteoVehiculos.put(cliente, conteoVehiculos.getOrDefault(cliente, 0) + 1);
        }
        
        Cliente clienteConMas = null;
        int maxVehiculos = 0;
        
        for (Map.Entry<Cliente, Integer> entry : conteoVehiculos.entrySet()){
            if (entry.getValue() > maxVehiculos){
                maxVehiculos = entry.getValue();
                clienteConMas = entry.getKey();
            }
        }
        
        return clienteConMas;
    }
    
    public boolean estaVacio(){
        boolean vacio = true;
        
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i) != null) {
                vacio = false;
            }
        }

        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i) != null) {
                vacio = false;
            }
        }

        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i) != null) {
                vacio = false;
            }
        }

        for (int i = 0; i < contratos.size(); i++) {
            if (contratos.get(i) != null) {
                vacio = false;
            }
        }

        return vacio;
    }
    
    // Métodos de serialización
    public boolean grabarDatos(){
        boolean exitoso = false;
        try{
            FileOutputStream archivoSalida = new FileOutputStream("DATOS.ser");
            ObjectOutputStream archGrabacion = new ObjectOutputStream(archivoSalida);
            archGrabacion.writeObject(this);
            archGrabacion.close();
            archivoSalida.close();
            exitoso = true;
            System.out.println("Datos grabados exitosamente en DATOS.ser");
        } catch (FileNotFoundException excepcion){
            System.err.println("Error: No se pudo crear el archivo DATOS.ser");
            System.err.println("Verifique los permisos de escritura en la carpeta del proyecto");
        } catch (IOException excepcion){
            System.err.println("Error de entrada/salida al grabar los datos: " + excepcion.getMessage());
        } catch (Exception excepcion){
            System.err.println("Error inesperado al grabar los datos: " + excepcion.getMessage());
        }
        return exitoso;
    }

    // Método mejorado para recuperar datos con mejor manejo de excepciones
    public static Sistema recuperarDatos(){
        Sistema sistema = null;
        try {
            FileInputStream archivoEntrada = new FileInputStream("DATOS.ser");
            ObjectInputStream lectura = new ObjectInputStream(archivoEntrada);
            sistema = (Sistema) lectura.readObject();
            lectura.close();
            archivoEntrada.close();
            System.out.println("Datos recuperados exitosamente desde DATOS.ser");
        } catch (FileNotFoundException excepcion){
            System.out.println("Archivo DATOS.ser no encontrado. Creando sistema nuevo...");
            sistema = new Sistema();
        } catch (IOException excepcion){
            System.err.println("Error de entrada/salida al leer DATOS.ser: " + excepcion.getMessage());
            System.out.println("Creando sistema nuevo debido al error...");
            sistema = new Sistema();
        } catch (ClassNotFoundException excepcion){
            System.err.println("Error: Clase no encontrada al deserializar: " + excepcion.getMessage());
            System.out.println("El archivo puede estar corrupto. Creando sistema nuevo...");
            sistema = new Sistema();
        } catch (ClassCastException excepcion){
            System.err.println("Error: El archivo DATOS.ser no contiene un objeto Sistema válido");
            System.out.println("Creando sistema nuevo...");
            sistema = new Sistema();
        } catch (Exception excepcion){
            System.err.println("Error inesperado al recuperar datos: " + excepcion.getMessage());
            System.out.println("Creando sistema nuevo debido al error...");
            sistema = new Sistema();
        }
        return sistema;
    }
    public List<String> obtenerServiciosMasUtilizados() {
        List<String> tiposServicio = new ArrayList<>();
        List<Integer> conteos = new ArrayList<>();

        for (ServicioAdicional servicio : serviciosAdicionales) {
            String tipo = servicio.getTipoServicio();
            if (tiposServicio.contains(tipo)) {
                int index = tiposServicio.indexOf(tipo);
                conteos.set(index, conteos.get(index) + 1);
            } else {
                tiposServicio.add(tipo);
                conteos.add(1);
            }
        }

        int max = 0;
        for (int c : conteos) {
            if (c > max) max = c;
        }

        List<String> masUsados = new ArrayList<>();
        for (int i = 0; i < tiposServicio.size(); i++) {
            if (conteos.get(i) == max) {
                masUsados.add(tiposServicio.get(i) + " (" + max + " veces)");
            }
        }

        return masUsados;
    }
    
    public List<Salida> obtenerEstadiasMasLargas() {
        List<Salida> resultado = new ArrayList<>();
        long mayorDuracion = 0;

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Salida salida : salidas) {
            Entrada entrada = salida.getEntrada();

            try {
                LocalDate fechaEntrada = LocalDate.parse(entrada.getFecha(), formatoFecha);
                LocalTime horaEntrada = LocalTime.parse(entrada.getHora(), formatoHora);
                LocalDate fechaSalida = LocalDate.parse(salida.getFecha(), formatoFecha);
                LocalTime horaSalida = LocalTime.parse(salida.getHora(), formatoHora);

                LocalDateTime fechaHoraEntrada = LocalDateTime.of(fechaEntrada, horaEntrada);
                LocalDateTime fechaHoraSalida = LocalDateTime.of(fechaSalida, horaSalida);

                long minutos = Duration.between(fechaHoraEntrada, fechaHoraSalida).toMinutes();

                if (minutos > mayorDuracion) {
                    mayorDuracion = minutos;
                    resultado.clear();
                    resultado.add(salida);
                } else if (minutos == mayorDuracion) {
                    resultado.add(salida);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }
    public List<Empleado> obtenerEmpleadosConMenosMovimientos(List<Empleado> empleados) {
        List<Empleado> resultado = new ArrayList<>();
        int minMovimientos = Integer.MAX_VALUE;

        for (Empleado e : empleados) {
            int contador = 0;

            for (Entrada entrada : entradas) {
                if (entrada.getEmpleado().equals(e)) {
                    contador++;
                }
            }

            for (Salida salida : salidas) {
                if (salida.getEmpleado().equals(e)) {
                    contador++;
                }
            }

            for (ServicioAdicional servicio : serviciosAdicionales) {
                if (servicio.getEmpleado().equals(e)) {
                    contador++;
                }
            }

            if (contador < minMovimientos) {
                minMovimientos = contador;
                resultado.clear();
                resultado.add(e);
            } else if (contador == minMovimientos) {
                resultado.add(e);
            }
        }

        return resultado;
    }
    
    public List<String> obtenerClientesConMasVehiculos(List<Cliente> clientes) {
        List<String> nombresClientes = new ArrayList<>();
        List<Integer> conteos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            int contador = 0;
            for (Contrato contrato : contratos) {
                if (contrato.getCliente().equals(cliente)) {
                    contador++;
                }
            }
            nombresClientes.add(cliente.getNombre());
            conteos.add(contador);
        }
        int max = 0;
        for (int c : conteos) {
            if (c > max) max = c;
        }
        List<String> clientesMasVehiculos = new ArrayList<>();
        for (int i = 0; i < nombresClientes.size(); i++) {
            if (conteos.get(i) == max) {
                clientesMasVehiculos.add(nombresClientes.get(i) + " (" + max + " vehÃ­culos)");
            }
        }
        return clientesMasVehiculos;
    }
    
    // Getters
    public ArrayList<Cliente> getClientes(){ 
        return clientes; 
    }
    public ArrayList<Vehiculo> getVehiculos(){ 
        return vehiculos; 
    }
    public ArrayList<Empleado> getEmpleados(){ 
        return empleados; 
    }
    public ArrayList<Contrato> getContratos(){ 
        return contratos; 
    }
    public ArrayList<Entrada> getEntradas(){ 
        return entradas; 
    }
    public ArrayList<Salida> getSalidas(){ 
        return salidas; 
    }
    public ArrayList<ServicioAdicional> getServiciosAdicionales(){ 
        return serviciosAdicionales;
    }
    
    public boolean existeDatos() {
        return clientes.size() > 0 || 
               vehiculos.size() > 0 || 
               empleados.size() > 0 || 
               contratos.size() > 0 || 
               entradas.size() > 0 || 
               salidas.size() > 0 || 
               serviciosAdicionales.size() > 0;
    }
}
