/// ContenidoController.java
/// Controlador para la gestión de contenidos en el sistema de gestión de contenidos

import java.util.ArrayList;
import java.util.List;

public class ContenidoController
{
    private List<Contenido> contenidos;
    private List<Categoria> categorias;
    
    public ContenidoController()
    {
        this.contenidos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        inicializarCategorias();
    }
    
    private void inicializarCategorias()
    {
        categorias.add(new Categoria("Programación", "Contenido sobre desarrollo de software"));
        categorias.add(new Categoria("Diseño", "Contenido sobre diseño gráfico"));
        categorias.add(new Categoria("Marketing", "Contenido sobre marketing digital"));
        categorias.add(new Categoria("Negocios", "Contenido sobre administración y emprendimiento"));
        categorias.add(new Categoria("Ciencia", "Contenido científico y académico"));
    }

    public Contenido crearArticulo(String titulo, Usuario autor, String cuerpo)
    {
        String id = "CNT-" + (contenidos.size() + 1);
        Articulo nuevo = new Articulo(id, titulo, autor, cuerpo);
        contenidos.add(nuevo);
        System.out.println("Artículo creado exitosamente: " + titulo);
        return nuevo;
    }

    public Contenido crearVideo(String titulo, Usuario autor, int duracion, String url) {
        String id = "CNT-" + (contenidos.size() + 1);
        Video nuevo = new Video(id, titulo, autor, duracion, url);
        contenidos.add(nuevo);
        System.out.println("Video creado exitosamente: " + titulo);
        return nuevo;
    }

    public Contenido crearImagen(String titulo, Usuario autor, String resolucion, double tamano)
    {
        String id = "CNT-" + (contenidos.size() + 1);
        Imagen nueva = new Imagen(id, titulo, autor, resolucion, tamano);
        contenidos.add(nueva);
        System.out.println("Imagen creada exitosamente: " + titulo);
        return nueva;
    }

    public void editarArticulo(String id, String nuevoTitulo, String nuevoCuerpo)
    {
        Contenido contenido = buscarPorId(id);
        if (contenido == null) {
            System.out.println("Contenido no encontrado");
            return;
        }
        
        if (!(contenido instanceof Articulo))
        {
            System.out.println("El contenido no es un artículo");
            return;
        }
        
        Articulo articulo = (Articulo) contenido;
        
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty())
        {
            articulo.setTitulo(nuevoTitulo);
        }
        
        if (nuevoCuerpo != null && !nuevoCuerpo.isEmpty())
        {
            articulo.setCuerpo(nuevoCuerpo);
        }
        
        System.out.println("Artículo editado exitosamente");
    }

    public void editarVideo(String id, String nuevoTitulo, int nuevaDuracion, String nuevaUrl)
    {
        Contenido contenido = buscarPorId(id);
        if (contenido == null)
        {
            System.out.println("Contenido no encontrado");
            return;
        }
        
        if (!(contenido instanceof Video))
        {
            System.out.println("El contenido no es un video");
            return;
        }
        
        Video video = (Video) contenido;
        
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty())
        {
            video.setTitulo(nuevoTitulo);
        }
        
        if (nuevaDuracion > 0)
        {
            video.setDuracion(nuevaDuracion);
        }
        
        if (nuevaUrl != null && !nuevaUrl.isEmpty())
        {
            video.setUrl(nuevaUrl);
        }
        
        System.out.println("Video editado exitosamente");
    }

    public void editarImagen(String id, String nuevoTitulo, String nuevaResolucion, double nuevoTamano)
    {
        Contenido contenido = buscarPorId(id);
        if (contenido == null)
        {
            System.out.println("Contenido no encontrado");
            return;
        }
        
        if (!(contenido instanceof Imagen))
        {
            System.out.println("El contenido no es una imagen");
            return;
        }
        
        Imagen imagen = (Imagen) contenido;
        
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty())
        {
            imagen.setTitulo(nuevoTitulo);
        }
        
        if (nuevaResolucion != null && !nuevaResolucion.isEmpty())
        {
            imagen.setResolucion(nuevaResolucion);
        }
        
        if (nuevoTamano > 0)
        {
            imagen.setTamanoArchivo(nuevoTamano);
        }
        
        System.out.println("Imagen editada exitosamente");
    }

    public boolean eliminarContenido(String id, Usuario usuario)
    {
        if (!usuario.puedeEliminar())
        {
            System.out.println("No tienes permisos para eliminar contenido");
            return false;
        }
        
        Contenido contenido = buscarPorId(id);
        if (contenido == null)
        {
            System.out.println("Contenido no encontrado");
            return false;
        }
        for (Categoria c : contenido.getCategorias())
        {
            c.removerContenido(contenido);
        }
        
        contenidos.remove(contenido);
        System.out.println("Contenido eliminado exitosamente");
        return true;
    }

    public List<Contenido> buscarContenidos(String criterio)
    {
        List<Contenido> resultados = new ArrayList<>();
        String criterioLower = criterio.toLowerCase();
        
        for (Contenido c : contenidos)
        {
            if (c.getTitulo().toLowerCase().contains(criterioLower) || c.getAutor().getNombre().toLowerCase().contains(criterioLower))
            {
            resultados.add(c);
            }
        }
        
        return resultados;
    }

    public List<Contenido> filtrarPorCategoria(String categoria)
    {
        Categoria c = buscarCategoria(categoria);
        if (c == null)
        {
            return new ArrayList<>();
        }
        return c.getContenidos();
    }
    

    public List<Contenido> filtrarPorTipo(String tipo)
    {
        List<Contenido> resultados = new ArrayList<>();
        
        for (Contenido c : contenidos)
        {
            if (tipo.equalsIgnoreCase("ARTICULO") && c instanceof Articulo)
            {
                resultados.add(c);
            } else if (tipo.equalsIgnoreCase("VIDEO") && c instanceof Video)
            {
                resultados.add(c);
            } else if (tipo.equalsIgnoreCase("IMAGEN") && c instanceof Imagen)
            {
                resultados.add(c);
            }
        }
        
        return resultados;
    }

    public Contenido buscarPorId(String id)
    {
        for (Contenido c : contenidos)
        {
            if (c.getId().equals(id))
            {
                return c;
            }
        }
        return null;
    }
    
    public Categoria buscarCategoria(String nombre)
    {
        for (Categoria c : categorias)
        {
            if (c.getNombre().equalsIgnoreCase(nombre))
            {
                return c;
            }
        }
        return null;
    }

    public List<Contenido> getContenidos()
    {
        return new ArrayList<>(contenidos);
    }
    
    public List<Categoria> getCategorias()
    {
        return new ArrayList<>(categorias);
    }
    
    public void agregarCategoria(Categoria c)
    {
        if (!categorias.contains(c))
        {
            categorias.add(c);
        }
    }
}