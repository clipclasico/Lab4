/// ReporteController.java
/// Controlador para la generación de reportes en el sistema de gestión de contenidos

import java.util.ArrayList;
import java.util.List;


public class ReporteController
{
    private List<Contenido> contenidos;
    
    public ReporteController(List<Contenido> contenidos)
    {
        this.contenidos = contenidos;
    }

    public String generarReporteGeneral()
    {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n╔════════════════════════════════════════════════════════════╗\n");
        reporte.append("║            Reporte general de contenido                    ║\n");
        reporte.append("╚════════════════════════════════════════════════════════════╝\n\n");
        
        if (contenidos.isEmpty())
        {
            reporte.append("No hay contenidos en el sistema.\n");
            return reporte.toString();
        }
        
        int totalArticulos = 0;
        int totalVideos = 0;
        int totalImagenes = 0;
        int totalPublicados = 0;
        
        for (Contenido c : contenidos)
        {
            if (c instanceof Articulo) totalArticulos++;
            else if (c instanceof Video) totalVideos++;
            else if (c instanceof Imagen) totalImagenes++;
            
            if (c.estaPublicado()) totalPublicados++;
        }
        
        reporte.append("Estadísticas generales: \n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        reporte.append(String.format("Total de contenidos: %d\n", contenidos.size()));
        reporte.append(String.format("  • Artículos: %d\n", totalArticulos));
        reporte.append(String.format("  • Videos: %d\n", totalVideos));
        reporte.append(String.format("  • Imágenes: %d\n", totalImagenes));
        reporte.append(String.format("Publicados: %d (%.1f%%)\n", 
            totalPublicados, (totalPublicados * 100.0) / contenidos.size()));
        reporte.append(String.format("Borradores: %d\n\n", contenidos.size() - totalPublicados));
        
        reporte.append("Lista de contenidos:\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        for (Contenido c : contenidos)
        {
            reporte.append(c.generarResumen()).append("\n");
        }
        
        return reporte.toString();
    }

    public String generarReportePorTipo(String tipo)
    {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n╔════════════════════════════════════════════════════════════╗\n");
        reporte.append(String.format("║              Reporte de %s                    ║\n", tipo.toUpperCase()));
        reporte.append("╚════════════════════════════════════════════════════════════╝\n\n");
        
        List<Contenido> filtrados = new ArrayList<>();
        
        for (Contenido c : contenidos)
        {
            boolean coincide = false;
            if (tipo.equalsIgnoreCase("ARTICULO") || tipo.equalsIgnoreCase("ARTÍCULOS"))
            {
                coincide = c instanceof Articulo;
            } else if (tipo.equalsIgnoreCase("VIDEO") || tipo.equalsIgnoreCase("VIDEOS"))
            {
                coincide = c instanceof Video;
            } else if (tipo.equalsIgnoreCase("IMAGEN") || tipo.equalsIgnoreCase("IMÁGENES"))
            {
                coincide = c instanceof Imagen;
            }
            
            if (coincide)
            {
                filtrados.add(c);
            }
        }
        
        if (filtrados.isEmpty())
        {
            reporte.append("No hay contenidos de este tipo.\n");
            return reporte.toString();
        }
        
        int publicados = 0;
        for (Contenido c : filtrados)
        {
            if (c.estaPublicado()) publicados++;
        }
        
        reporte.append(String.format("Total: %d | Publicados: %d | Borradores: %d\n\n",
            filtrados.size(), publicados, filtrados.size() - publicados));
        
        for (Contenido c : filtrados)
        {
            reporte.append("─────────────────────────────────────────────────────────────\n");
            reporte.append(c.visualizar()).append("\n");
        }
        
        return reporte.toString();
    }

    public String generarReportePorAutor(Usuario autor)
{
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n╔════════════════════════════════════════════════════════════╗\n");
        reporte.append(String.format("║          REPORTE DE AUTOR: %-28s║\n", autor.getNombre()));
        reporte.append("╚════════════════════════════════════════════════════════════╝\n\n");
        
        List<Contenido> porAutor = new ArrayList<>();
        for (Contenido c : contenidos)
        {
            if (c.getAutor().getId().equals(autor.getId()))
            {
                porAutor.add(c);
            }
        }
        
        if (porAutor.isEmpty())
        {
            reporte.append("Este autor no tiene contenidos.\n");
            return reporte.toString();
        }
        
        int articulos = 0, videos = 0, imagenes = 0, publicados = 0;
        
        for (Contenido c : porAutor)
        {
            if (c instanceof Articulo) articulos++;
            else if (c instanceof Video) videos++;
            else if (c instanceof Imagen) imagenes++;
            
            if (c.estaPublicado()) publicados++;
        }
        
        reporte.append("Estadísticas del autor:\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        reporte.append(String.format("Total de contenidos: %d\n", porAutor.size()));
        reporte.append(String.format("  • Artículos: %d\n", articulos));
        reporte.append(String.format("  • Videos: %d\n", videos));
        reporte.append(String.format("  • Imágenes: %d\n", imagenes));
        reporte.append(String.format("Publicados: %d (%.1f%%)\n\n", 
            publicados, (publicados * 100.0) / porAutor.size()));
        
        reporte.append("Contenidos:\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        for (Contenido c : porAutor)
        {
            reporte.append(c.generarResumen()).append("\n");
        }
        
        return reporte.toString();
    }

    public String obtenerEstadisticas()
    {
        StringBuilder stats = new StringBuilder();
        
        int totalArticulos = 0;
        int totalVideos = 0;
        int totalImagenes = 0;
        int totalPublicados = 0;
        
        for (Contenido c : contenidos)
        {
            if (c instanceof Articulo) totalArticulos++;
            else if (c instanceof Video) totalVideos++;
            else if (c instanceof Imagen) totalImagenes++;
            
            if (c.estaPublicado()) totalPublicados++;
        }
        
        stats.append("Total de contenidos: ").append(contenidos.size()).append("\n");
        stats.append("Total de artículos: ").append(totalArticulos).append("\n");
        stats.append("Total de videos: ").append(totalVideos).append("\n");
        stats.append("Total de imágenes: ").append(totalImagenes).append("\n");
        stats.append("Total publicados: ").append(totalPublicados).append("\n");
        stats.append("Total borradores: ").append(contenidos.size() - totalPublicados).append("\n");
        
        return stats.toString();
    }

    public String exportarReporte(String formato)
    {
        String reporte = generarReporteGeneral();
        
        if (formato.equalsIgnoreCase("TXT"))
        {
            return reporte;
        } else if (formato.equalsIgnoreCase("CSV"))
        {
            StringBuilder csv = new StringBuilder();
            csv.append("ID,Título,Autor,Fecha,Tipo,Publicado\n");
            for (Contenido c : contenidos)
            {
                String tipo = c instanceof Articulo ? "Artículo" : 
                             c instanceof Video ? "Video" : "Imagen";
                csv.append(c.getId()).append(",");
                csv.append(c.getTitulo()).append(",");
                csv.append(c.getAutor().getNombre()).append(",");
                csv.append(c.getFechaCreacion()).append(",");
                csv.append(tipo).append(",");
                csv.append(c.estaPublicado() ? "Sí" : "No").append("\n");
            }
            return csv.toString();
        } else if (formato.equalsIgnoreCase("JSON"))
        {
            StringBuilder json = new StringBuilder();
            json.append("{\n  \"contenidos\": [\n");
            for (int i = 0; i < contenidos.size(); i++) {
                Contenido c = contenidos.get(i);
                json.append("    {\n");
                json.append("      \"id\": \"").append(c.getId()).append("\",\n");
                json.append("      \"titulo\": \"").append(c.getTitulo()).append("\",\n");
                json.append("      \"autor\": \"").append(c.getAutor().getNombre()).append("\",\n");
                json.append("      \"publicado\": ").append(c.estaPublicado()).append("\n");
                json.append("    }");
                if (i < contenidos.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ]\n}");
            return json.toString();
        } else {
            return reporte;
        }
    }
}