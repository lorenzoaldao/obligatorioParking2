/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.Serializable;

public class ServicioAdicional implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String tipoServicio;
    private String fecha;
    private String hora;
    private Vehiculo vehiculo;
    private Empleado empleado;
    private double costo;
    
    public ServicioAdicional(String unTipoDeServicio, String unaFecha, String unaHora, Vehiculo unVehiculo, Empleado unEmpleado, double unCosto){
        this.tipoServicio = unTipoDeServicio;
        this.fecha = unaFecha;
        this.hora = unaHora;
        this.vehiculo = unVehiculo;
        this.empleado = unEmpleado;
        this.costo = unCosto;
    }
    
    // Getters y Setters
    public String getTipoServicio(){ 
        return tipoServicio; 
    }
    public void setTipoServicio(String unServicio){ 
        this.tipoServicio = unServicio; 
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
    
    public Vehiculo getVehiculo(){ 
        return vehiculo; 
    }
    public void setVehiculo(Vehiculo unVehiculo){ 
        this.vehiculo = unVehiculo; 
    }
    
    public Empleado getEmpleado(){ 
        return empleado; 
    }
    public void setEmpleado(Empleado unEmpleado){ 
        this.empleado = unEmpleado; 
    }
    
    public double getCosto(){ 
        return costo; 
    }
    public void setCosto(double unCosto){ 
        this.costo = unCosto; 
    }
    
    @Override
    public String toString(){
        return tipoServicio + " - " + vehiculo.getMatricula() + " - $" + costo;
    }
}
