/// Imagen.java
/// Clase que representa un contenido de tipo imagen en el sistema de gestión de contenidos
/// Hereda de la clase Contenido e implementa las interfaces iPublicable e iReportable

public class Imagen extends Contenido
{
    private String resolucion;
    private double tamanoArchivo;
    
    public Imagen(String id, String titulo, Usuario autor, String resolucion, double tamanoArchivo)
    {
        super(id, titulo, autor);
        this.resolucion = resolucion;
        this.tamanoArchivo = tamanoArchivo;
    }
    
    @Override
    public void publicar()
    {
        if (resolucion == null || resolucion.trim().isEmpty())
        {
            System.out.println("Error: La imagen debe tener una resolución válida");
            return;
        }
        
        if (!resolucion.matches("\\d+x\\d+"))
        {
            System.out.println("Error: La resolución debe tener el formato (ejemplo: 1920x1080)");
            return;
        }
        
        this.publicado = true;
        System.out.println("Imagen publicada: " + titulo);
    }
    
    @Override
    public String visualizar()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=== IMAGEN ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Fecha: ").append(fechaCreacion).append("\n");
        sb.append("Resolución: ").append(resolucion).append("\n");
        sb.append("Tamaño: ").append(obtenerTamano()).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        return sb.toString();
    }
    
    public String obtenerTamano()
    {
        if (tamanoArchivo < 1)
        {
            return String.format("%.2f KB", tamanoArchivo * 1024);
        } else {
            return String.format("%.2f MB", tamanoArchivo);
        }
    }
    
    @Override
    public String obtenerEstadisticas()
    {
        StringBuilder stats = new StringBuilder(super.obtenerEstadisticas());
        stats.append("Tipo: Imagen\n");
        stats.append("Resolución: ").append(resolucion).append("\n");
        stats.append("Tamaño (MB): ").append(tamanoArchivo).append("\n");
        return stats.toString();
    }
    
    public String getResolucion()
    {
        return resolucion;
    }

    public void setResolucion(String resolucion)
    {
        this.resolucion = resolucion;
    }
    
    public double getTamanoArchivo()
    {
        return tamanoArchivo;
    }

    public void setTamanoArchivo(double tamanoArchivo)
    {
        this.tamanoArchivo = tamanoArchivo;
    }
}