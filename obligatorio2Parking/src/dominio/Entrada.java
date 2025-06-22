/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.Serializable;

public class Entrada implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Vehiculo vehiculo;
    private String fecha;
    private String hora;
    private String notas;
    private Empleado empleado;
    
    public Entrada(Vehiculo unVehiculo, String unaFecha, String unaHora, String unasNotas, Empleado unEmpleado){
        this.vehiculo = unVehiculo;
        this.fecha = unaFecha;
        this.hora = unaHora;
        this.notas = unasNotas;
        this.empleado = unEmpleado;
    }
    
    // Getters y Setters
    public Vehiculo getVehiculo(){ 
        return vehiculo; 
    }
    public void setVehiculo(Vehiculo unVehiculo){ 
        this.vehiculo = unVehiculo; 
    }
    
    public String getFecha(){ 
        return fecha; 
    }
    public void setFecha(String unaFecha){ 
        this.fecha = unaFecha; 
    }
    
    public String getHora(){ 
        return hora; 
    }
    public void setHora(String unaHora){ 
        this.hora = unaHora; 
    }
    
    public String getNotas(){ 
        return notas; 
    }
    public void setNotas(String unasNotas){ 
        this.notas = unasNotas; 
    }
    
    public Empleado getEmpleado(){ 
        return empleado; 
    }
    public void setEmpleado(Empleado unEmpleado){ 
        this.empleado = unEmpleado; 
    }
    
    @Override
    public String toString(){
        return "Entrada - " + vehiculo.getMatricula() + " - " + fecha + " " + hora;
    }
}
