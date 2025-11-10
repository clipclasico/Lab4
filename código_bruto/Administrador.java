/// Administrador.java
/// Clase que representa un usuario administrador en el sistema de gestión de contenidos

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario
{
    private List<String> permisos;
    
    public Administrador(String id, String nombre, String email, String contrasena)
    {
        super(id, nombre, email, contrasena);
        this.permisos = new ArrayList<>();
        inicializarPermisos();
    }
    
    private void inicializarPermisos()
    {
        permisos.add("CREAR");
        permisos.add("EDITAR");
        permisos.add("ELIMINAR");
        permisos.add("PUBLICAR");
        permisos.add("DESPUBLICAR");
        permisos.add("VER_REPORTES");
    }
    
    @Override
    public boolean puedeEliminar()
    {
        return true;
    }
    
    public boolean puedePublicar()
    {
        return true;
    }
    
    public List<String> getPermisos()
    {
        return new ArrayList<>(permisos);
    }
}