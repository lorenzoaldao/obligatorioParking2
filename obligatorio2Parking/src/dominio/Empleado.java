/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.Serializable;

public class Empleado implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String cedula;
    private String direccion;
    private int numeroEmpleado;
    
    public Empleado(String unNombre, String unaCedula, String unaDireccion, int unNumeroEmpleado){
        this.nombre = unNombre;
        this.cedula = unaCedula;
        this.direccion = unaDireccion;
        this.numeroEmpleado = unNumeroEmpleado;
    }
    
    // Getters y Setters
    public String getNombre(){ 
        return nombre; 
    }
    public void setNombre(String unNombre){ 
        this.nombre = unNombre; 
    }
    
    public String getCedula(){ 
        return cedula; 
    }
    public void setCedula(String unaCedula){ 
        this.cedula = unaCedula; 
    }
    
    public String getDireccion(){ 
        return direccion; 
    }
    public void setDireccion(String unaDireccion){ 
        this.direccion = unaDireccion; 
    }
    
    public int getNumeroEmpleado(){ 
        return numeroEmpleado; 
    }
    public void setNumeroEmpleado(int unNumeroEmpleado){ 
        this.numeroEmpleado = unNumeroEmpleado; 
    }
    
    @Override
    public String toString(){
        return nombre + " - " + numeroEmpleado;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean resultadoE  = false;
        if (this == obj){
            resultadoE = true;
        } else if (obj == null || getClass() != obj.getClass()){
            Empleado empleado = (Empleado) obj;
            resultadoE = cedula.equals(empleado.cedula);
        }
            return resultadoE;
        }
    }
