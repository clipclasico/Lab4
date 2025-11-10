/// Usuario.java
/// Clase abstracta que representa un usuario en el sistema de gestión de contenidos

public abstract class Usuario
{
    protected String id;
    protected String nombre;
    protected String email;
    protected String contrasena;

    public Usuario(String id, String nombre, String email, String contrasena)
    {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public abstract boolean puedeEliminar();

    public boolean autenticar(String contra)
    {
        return this.contrasena.equals(contra);
    }

    public String getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getEmail()
    {
        return email;
    }

    public String getContrasena()
    {
        return contrasena;
    }

    public void setContrasena(String contrasena)
    {
        this.contrasena = contrasena;
    }
}