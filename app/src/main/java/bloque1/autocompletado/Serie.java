/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bloque1.autocompletado;

/**
 *
 * @author Molina
 */
public class Serie {
    private String Nombre;
    private Integer Anyo;

    public Serie(String Nombre, Integer Id) {
        this.Nombre = Nombre;
        this.Anyo = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Integer getAnyo() {
        return Anyo;
    }

    public void setAnyo(Integer Id) {
        this.Anyo = Id;
    }
    
    
}
