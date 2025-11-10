/// Categoria.java
/// Clase que representa una categoría de contenido en el sistema de gestión de contenidos

import java.util.ArrayList;
import java.util.List;

public class Categoria
{
    private String nombre;
    private String descripcion;
    private List<Contenido> contenidos;
    
    public Categoria(String nombre, String descripcion)
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenidos = new ArrayList<>();
    }

    public void agregarContenido(Contenido c)
    {
        if (!contenidos.contains(c))
        {
            contenidos.add(c);
        }
    }

    public void removerContenido(Contenido c)
    {
        contenidos.remove(c);
    }

    public List<Contenido> getContenidos()
    {
        return new ArrayList<>(contenidos);
    }
    
    public List<Contenido> getContenidosPublicados()
    {
        List<Contenido> publicados = new ArrayList<>();
        for (Contenido c : contenidos)
        {
            if (c.estaPublicado())
            {
                publicados.add(c);
            }
        }
        return publicados;
    }
    
    @Override
    public String toString()
    {
        return nombre + " (" + contenidos.size() + " contenidos)";
    }
    
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
}