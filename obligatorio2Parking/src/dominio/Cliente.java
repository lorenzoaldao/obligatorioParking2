/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.Serializable;

public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String cedula;
    private String direccion;
    private String celular;
    private int anioCliente;
    
    public Cliente(String unNombre, String unaCedula, String unaDireccion, String unCelular, int unAnioCliente){
        this.nombre = unNombre;
        this.cedula = unaCedula;
        this.direccion = unaDireccion;
        this.celular = unCelular;
        this.anioCliente = unAnioCliente;
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
    
    public String getCelular(){ 
        return celular; 
    }
    public void setCelular(String unCelular){ 
        this.celular = unCelular; 
    }
    
    public int getAnioCliente(){ 
        return anioCliente; 
    }
    public void setAnioCliente(int unAnioCliente){ 
        this.anioCliente = unAnioCliente; 
    }
    
    @Override
    public String toString(){
        return nombre + " - " + cedula;
    }
    
   @Override
    public boolean equals(Object obj){
        boolean resultado = false;
        if (this == obj) {
            resultado = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            Cliente cliente = (Cliente) obj;
            resultado = cedula.equals(cliente.cedula);
        }

            return resultado;
        }
    }
