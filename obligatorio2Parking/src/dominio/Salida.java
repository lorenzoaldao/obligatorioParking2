/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO ()
*/
package dominio;

import java.io.Serializable;

public class Salida implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Entrada entrada;
    private Empleado empleado;
    private String fecha;
    private String hora;
    private String comentario;
    
    public Salida(Entrada unaEntrada, Empleado unEmpleado, String unaFecha, String unaHora, String unComentario) {
        this.entrada = unaEntrada;
        this.empleado = unEmpleado;
        this.fecha = unaFecha;
        this.hora = unaHora;
        this.comentario = unComentario;
    }
    

    public String calcularTiempoEstadia(){
        String resultado = "";
        try{
            String[] horaEntrada = entrada.getHora().split(":");
            String[] horaSalida = this.hora.split(":");

            int minutosEntrada = Integer.parseInt(horaEntrada[0]) * 60 + Integer.parseInt(horaEntrada[1]);
            int minutosSalida = Integer.parseInt(horaSalida[0]) * 60 + Integer.parseInt(horaSalida[1]);

            int diferenciaMinutos = minutosSalida - minutosEntrada;
            if (diferenciaMinutos < 0){
                diferenciaMinutos += 24 * 60; // Asume que pasó al día siguiente
            }

            int horas = diferenciaMinutos / 60;
            int minutos = diferenciaMinutos % 60;

            resultado = horas + " horas y " + minutos + " minutos";
        } catch (Exception e){
            resultado = "Error al calcular el tiempo";
        }
        return resultado;
    }
    
    // Getters y Setters
    public Entrada getEntrada(){ 
        return entrada; 
    }
    public void setEntrada(Entrada unaEntrada){ 
        this.entrada = unaEntrada; 
    }
    
    public Empleado getEmpleado(){ 
        return empleado; 
    }
    public void setEmpleado(Empleado unEmpleado){ 
        this.empleado = unEmpleado; 
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
    
    public String getComentario(){
        return comentario; 
    }
    public void setComentario(String unComentario){ 
        this.comentario = unComentario; 
    }
    
    @Override
    public String toString(){
        return "Salida - " + entrada.getVehiculo().getMatricula() + " - " + fecha + " " + hora;
    }
}
