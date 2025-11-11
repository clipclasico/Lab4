/// UsuarioController.java
/// Controlador para la gestión de usuarios en el sistema de gestión de contenidos

import java.util.ArrayList;
import java.util.List;

public class UsuarioController
{
    private List<Usuario> usuarios;
    private Usuario usuarioActual;
    
    public UsuarioController()
    {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;
        inicializarUsuarios();
    }
    
    private void inicializarUsuarios()
    {
        usuarios.add(new Administrador("AD1", "Giovanni Orozco", "gio@uvg.edu.gt", "admin123"));
        usuarios.add(new Administrador("AD2", "Nathalie Valle", "rut@uvg.edu.gt", "admin123"));

        usuarios.add(new Editor("ED1", "Juan", "juan@uvg.edu.gt", "juan123", "Computación"));
        usuarios.add(new Editor("ED2", "Ana", "ana@uvg.edu.gt", "ana123", "Marketing"));
        usuarios.add(new Editor("ED3", "Carlos", "carlos@uvg.edu.gt", "carlos123", "Arquitectura"));
    }

    public boolean login(String email, String contra)
    {
        for (Usuario usuario : usuarios)
        {
            if (usuario.getEmail().equals(email) && usuario.autenticar(contra))
            {
                usuarioActual = usuario;
                System.out.println("Bienvenido, " + usuario.getNombre());
                return true;
            }
        }
        System.out.println("Credenciales inválidas");
        return false;
    }
    
    public void logout()
    {
        if (usuarioActual != null)
        {
            System.out.println("Hasta luego, " + usuarioActual.getNombre());
            usuarioActual = null;
        }
    }

    public boolean validarPermiso(String accion)
    {
        if (usuarioActual == null)
        {
            System.out.println("Debes iniciar sesión primero");
            return false;
        }
        
        switch (accion.toUpperCase())
        {
            case "ELIMINAR":
                return usuarioActual.puedeEliminar();
                
            case "PUBLICAR":
                if (usuarioActual instanceof Administrador)
                {
                    return ((Administrador) usuarioActual).puedePublicar();
                }
                return false;
                
            case "EDITAR":
                if (usuarioActual instanceof Editor)
                {
                    return ((Editor) usuarioActual).puedeEditar();
                }
                return usuarioActual instanceof Administrador;
                
            default:
                return false;
        }
    }

    public Usuario obtenerUsuarioActual()
    {
        return usuarioActual;
    }

    public boolean registrarUsuario(String tipo, String nombre, String email, String contrasena, String especialidad)
    {
        if (usuarioActual == null || !usuarioActual.puedeEliminar())
        {
            System.out.println("Solo administradores pueden registrar usuarios");
            return false;
        }
        
        for (Usuario u : usuarios)
        {
            if (u.getEmail().equals(email))
            {
                System.out.println("El email ya está registrado");
                return false;
            }
        }
        
        String id;
        Usuario nuevoUsuario;
        
        switch (tipo.toUpperCase())
        {
            case "ADMINISTRADOR":
                id = "AD-" + (usuarios.size() + 1);
                nuevoUsuario = new Administrador(id, nombre, email, contrasena);
                break;
                
            case "EDITOR":
                id = "ED-" + (usuarios.size() + 1);
                nuevoUsuario = new Editor(id, nombre, email, contrasena, especialidad);
                break;
                
            default:
                System.out.println("Tipo de usuario no válido");
                return false;
        }
        
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado exitosamente");
        return true;
    }

    public void listarUsuarios()
    {
        if (usuarios.isEmpty())
        {
            System.out.println("No hay usuarios registrados");
            return;
        }
        
        System.out.println("\n=== USUARIOS REGISTRADOS ===");
        for (Usuario u : usuarios)
        {
            String tipo = u instanceof Administrador ? "Administrador" : "Editor";
            System.out.printf("- %s | %s | %s | Tipo: %s\n", 
                u.getId(), u.getNombre(), u.getEmail(), tipo);
        }
    }

    public String getInfoUsuarioActual()
    {
        if (usuarioActual == null)
        {
            return "No hay sesión activa";
        }
        
        String tipo = usuarioActual instanceof Administrador ? "Administrador" : "Editor";
        String info = String.format("Usuario: %s | Email: %s | Tipo: %s", 
        usuarioActual.getNombre(), usuarioActual.getEmail(), tipo);
        
        if (usuarioActual instanceof Editor)
        {
            info += " | Especialidad: " + ((Editor) usuarioActual).getEspecialidad();
        }
        
        return info;
    }
    
    public List<Usuario> getUsuarios()
    {
        return new ArrayList<>(usuarios);
    }
}