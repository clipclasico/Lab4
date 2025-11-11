/// Editor.java
/// Clase que representa un usuario editor en el sistema de gestión de contenidos

public class Editor extends Usuario
{
    private String especialidad;
    
    public Editor(String id, String nombre, String email, String contrasena, String especialidad)
    {
        super(id, nombre, email, contrasena);
        this.especialidad = especialidad;
    }
    
    @Override
    public boolean puedeEliminar()
    {
        return false;
    }
    
    public boolean puedeEditar()
    {
        return true;
    }
    
    public String getEspecialidad()
    {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad)
    {
        this.especialidad = especialidad;
    }
}