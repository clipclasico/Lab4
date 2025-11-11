/// Articulo.java
/// Clase que representa un contenido de tipo artículo en el sistema de gestión de contenidos
/// Hereda de la clase Contenido e implementa las interfaces iPublicable e iReportable

public class Articulo extends Contenido
{
    private String cuerpo;
    private int numeroPalabras;
    
    public Articulo(String id, String titulo, Usuario autor, String cuerpo)
    {
        super(id, titulo, autor);
        this.cuerpo = cuerpo;
        this.numeroPalabras = contarPalabras();
    }
    
    @Override
    public void publicar()
    {
        if (numeroPalabras < 100)
        {
            System.out.println("Error: El artículo debe tener al menos 100 palabras");
            return;
        }
        this.publicado = true;
        System.out.println("Artículo publicado: " + titulo);
    }
    
    @Override
    public String visualizar()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ARTÍCULO ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Fecha: ").append(fechaCreacion).append("\n");
        sb.append("Palabras: ").append(numeroPalabras).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        sb.append("\nContenido:\n").append(cuerpo).append("\n");
        return sb.toString();
    }
    
    public int contarPalabras()
    {
        if (cuerpo == null || cuerpo.trim().isEmpty())
        {
            return 0;
        }
        return cuerpo.trim().split("\\s+").length;
    }
    
    @Override
    public String obtenerEstadisticas()
    {
        StringBuilder stats = new StringBuilder(super.obtenerEstadisticas());
        stats.append("Tipo: Artículo\n");
        stats.append("Número de palabras: ").append(numeroPalabras).append("\n");
        return stats.toString();
    }
    
    public String getCuerpo()
    {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo)
    { 
        this.cuerpo = cuerpo;
        this.numeroPalabras = contarPalabras();
    }
    
    public int getNumeroPalabras()
    {
        return numeroPalabras;
    }
}