/// Video.java
/// Clase que representa un contenido de tipo video en el sistema de gestión de contenidos

public class Video extends Contenido
{
    private int duracion;
    private String url;
    
    public Video(String id, String titulo, Usuario autor, int duracion, String url)
    {
        super(id, titulo, autor);
        this.duracion = duracion;
        this.url = url;
    }
    
    @Override
    public void publicar()
    {
        if (url == null || url.trim().isEmpty())
        {
            System.out.println("Error: El video debe tener una URL válida");
            return;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://"))
        {
            System.out.println("Error: La URL debe comenzar con http:// o https://");
            return;
        }
        this.publicado = true;
        System.out.println("Video publicado: " + titulo);
    }
    
    @Override
    public String visualizar()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VIDEO ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Fecha: ").append(fechaCreacion).append("\n");
        sb.append("Duración: ").append(obtenerDuracion()).append("\n");
        sb.append("URL: ").append(url).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        return sb.toString();
    }
    
    public String obtenerDuracion()
    {
        int horas = duracion / 3600;
        int minutos = (duracion % 3600) / 60;
        int segundos = duracion % 60;
        
        if (horas > 0)
        {
            return String.format("%dh %dm %ds", horas, minutos, segundos);
        } else if (minutos > 0)
        {
            return String.format("%dm %ds", minutos, segundos);
        } else {
            return String.format("%ds", segundos);
        }
    }
    
    @Override
    public String obtenerEstadisticas()
    {
        StringBuilder stats = new StringBuilder(super.obtenerEstadisticas());
        stats.append("Tipo: Video\n");
        stats.append("Duración: ").append(obtenerDuracion()).append("\n");
        stats.append("Duración (segundos): ").append(duracion).append("\n");
        return stats.toString();
    }

    public int getDuracion()
    {
        return duracion;
    }

    public void setDuracion(int duracion)
    {
        this.duracion = duracion;
    }
    
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}