/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package dominio;

import java.io.Serializable;

public class Vehiculo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String matricula;
    private String marca;
    private String modelo;
    private String estado;
    
    public Vehiculo(String matricula, String marca, String modelo, String estado){
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getMatricula(){
        return matricula; 
    }
    public void setMatricula(String matricula){ 
        this.matricula = matricula; 
    }
    
    public String getMarca(){ 
        return marca; 
    }
    public void setMarca(String marca){ 
        this.marca = marca; 
    }
    
    public String getModelo(){ 
        return modelo; 
    }
    public void setModelo(String modelo){ 
        this.modelo = modelo; 
    }
    
    public String getEstado(){ 
        return estado; 
    }
    public void setEstado(String estado){ 
        this.estado = estado; 
    }
    
    @Override
    public String toString(){
        return matricula + " - " + marca + " " + modelo;
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) obj;
        return matricula.equals(vehiculo.matricula);
    }
}
