/// Contenido.java
/// Clase abstracta que representa un contenido en el sistema de gestión de contenidos
/// Implementa las interfaces iPublicable e iReportable

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Contenido implements iPublicable, iReportable
{
    protected String id;
    protected String titulo;
    protected Usuario autor;
    protected LocalDate fechaCreacion;
    protected List<Categoria> categorias;
    protected boolean publicado;
    
    public Contenido(String id, String titulo, Usuario autor)
    {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaCreacion = LocalDate.now();
        this.categorias = new ArrayList<>();
        this.publicado = false;
    }

    public void agregarCategoria(Categoria c)
    {
        if (!categorias.contains(c))
        {
            categorias.add(c);
            c.agregarContenido(this);
        }
    }
    
    @Override
    public void despublicar()
    {
        this.publicado = false;
        System.out.println("Contenido despublicado: " + titulo);
    }
    
    @Override
    public boolean estaPublicado()
    {
        return this.publicado;
    }
    
    @Override
    public abstract void publicar();
    
    public abstract String visualizar();
    
    @Override
    public String generarResumen()
    {
        return String.format("ID: %s | Título: %s | Autor: %s | Fecha: %s | Publicado: %s",
        id, titulo, autor.getNombre(), fechaCreacion, publicado ? "Sí" : "No");
    }
    
    @Override
    public String obtenerEstadisticas()
    {
        StringBuilder stats = new StringBuilder();
        stats.append("ID: ").append(id).append("\n");
        stats.append("Título: ").append(titulo).append("\n");
        stats.append("Autor: ").append(autor.getNombre()).append("\n");
        stats.append("Fecha: ").append(fechaCreacion.toString()).append("\n");
        stats.append("Publicado: ").append(publicado ? "Sí" : "No").append("\n");
        stats.append("Categorías: ").append(categorias.size()).append("\n");
        return stats.toString();
    }
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }
    
    public Usuario getAutor()
    {
        return autor;
    }

    public void setAutor(Usuario autor)
    {
        this.autor = autor;
    }
    
    public LocalDate getFechaCreacion()
    {
        return fechaCreacion;
    }
    
    public List<Categoria> getCategorias()
    {
        return new ArrayList<>(categorias);
    }
    
    public boolean isPublicado()
    {
        return publicado;
    }
}