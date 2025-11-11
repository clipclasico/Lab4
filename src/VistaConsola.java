/// VistaConsola.java
/// Interfaz de consola para el sistema de gestión de contenidos

import java.util.*;

public class VistaConsola
{
    private ContenidoController contenidoController;
    private UsuarioController usuarioController;
    private ReporteController reporteController;
    private Scanner scanner;
    
    public VistaConsola()
    {
        this.contenidoController = new ContenidoController();
        this.usuarioController = new UsuarioController();
        this.reporteController = new ReporteController(contenidoController.getContenidos());
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciar()
    {
        mostrarBienvenida();

        while (usuarioController.obtenerUsuarioActual() == null)
        {
            if (!login())
            {
                System.out.println("\n¿Desea intentar de nuevo? (s/n): ");
                String resp = scanner.nextLine();
                if (!resp.equalsIgnoreCase("s"))
                {
                    System.out.println("Hasta pronto!");
                    return;
                }
            }
        }

        menuRecursivo();        
        scanner.close();
    }

    private void menuRecursivo()
    {
        mostrarMenuPrincipal();

        if (procesarOpcionMenu())
        {
            menuRecursivo();
        }
    }
    
    private void mostrarBienvenida()
    {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║              ESTUDIO DE GRABACIÓN AUDIOVISUAL                 ║");
        System.out.println("║              Sistema de Gestión de Contenidos                 ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
    }
    
    private boolean login()
    {
        System.out.println("\n═══ Inicio de sesión ═══");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();
        
        return usuarioController.login(email, pass);
    }

    public void mostrarMenuPrincipal()
    {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      Menú principal                           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  " + usuarioController.getInfoUsuarioActual());
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Crear contenido                                           ║");
        System.out.println("║  2. Ver contenidos                                            ║");
        System.out.println("║  3. Editar contenido                                          ║");
        System.out.println("║  4. Eliminar contenido                                        ║");
        System.out.println("║  5. Publicar/Despublicar contenido                            ║");
        System.out.println("║  6. Buscar contenidos                                         ║");
        System.out.println("║  7. Gestionar categorías                                      ║");
        System.out.println("║  8. Ver reportes                                              ║");
        System.out.println("║  9. Gestionar usuarios (solo Admin)                           ║");
        System.out.println("║  0. Cerrar sesión                                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.print("\nSeleccione una opción: ");
    }
    
    private boolean procesarOpcionMenu()
    {
        try
        {
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion)
            {
                case 1:
                    crearContenido();
                    break;
                case 2:
                    mostrarContenidos();
                    break;
                case 3:
                    editarContenido();
                    break;
                case 4:
                    eliminarContenido();
                    break;
                case 5:
                    publicarDespublicar();
                    break;
                case 6:
                    buscarContenidos();
                    break;
                case 7:
                    gestionarCategorias();
                    break;
                case 8:
                    verReportes();
                    break;
                case 9:
                    gestionarUsuarios();
                    break;
                case 0:
                    usuarioController.logout();
                    return false;
                default:
                    System.out.println("Opción no válida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido");
        }
        
        return true;
    }
    
    private void crearContenido() {
        System.out.println("\n═══ CREAR CONTENIDO ═══");
        System.out.println("Tipos disponibles:");
        System.out.println("1. Artículo");
        System.out.println("2. Video");
        System.out.println("3. Imagen");
        System.out.print("\nSeleccione tipo: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        Usuario autor = usuarioController.obtenerUsuarioActual();
        
        switch (tipo) {
            case 1: // Artículo
                System.out.print("Cuerpo del artículo: ");
                String cuerpo = scanner.nextLine();
                contenidoController.crearArticulo(titulo, autor, cuerpo);
                break;
                
            case 2: // Video
                System.out.print("Duración (segundos): ");
                int duracion = Integer.parseInt(scanner.nextLine());
                System.out.print("URL: ");
                String url = scanner.nextLine();
                contenidoController.crearVideo(titulo, autor, duracion, url);
                break;
                
            case 3: // Imagen
                System.out.print("Resolución (ej: 1920x1080): ");
                String resolucion = scanner.nextLine();
                System.out.print("Tamaño en MB: ");
                double tamano = Double.parseDouble(scanner.nextLine());
                contenidoController.crearImagen(titulo, autor, resolucion, tamano);
                break;
                
            default:
                System.out.println("Tipo no válido");
        }
    }
    
    /**
     * Muestra todos los contenidos
     */
    public void mostrarContenidos() {
        System.out.println("\n═══ CONTENIDOS DEL SISTEMA ═══");
        
        List<Contenido> contenidos = contenidoController.getContenidos();
        
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos en el sistema.");
            return;
        }
        
        System.out.println("\n1. Ver todos");
        System.out.println("2. Filtrar por tipo");
        System.out.println("3. Filtrar por categoría");
        System.out.print("\nSeleccione opción: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                listarContenidos(contenidos);
                break;
                
            case 2:
                System.out.println("\nTipos:");
                System.out.println("1. Artículos");
                System.out.println("2. Videos");
                System.out.println("3. Imágenes");
                System.out.print("Seleccione: ");
                int tipoOp = Integer.parseInt(scanner.nextLine());
                String tipoStr = tipoOp == 1 ? "ARTICULO" : tipoOp == 2 ? "VIDEO" : "IMAGEN";
                listarContenidos(contenidoController.filtrarPorTipo(tipoStr));
                break;
                
            case 3:
                System.out.println("\nCategorías disponibles:");
                List<Categoria> cats = contenidoController.getCategorias();
                for (int i = 0; i < cats.size(); i++) {
                    System.out.println((i+1) + ". " + cats.get(i).getNombre());
                }
                System.out.print("Seleccione: ");
                int catIdx = Integer.parseInt(scanner.nextLine()) - 1;
                if (catIdx >= 0 && catIdx < cats.size()) {
                    listarContenidos(contenidoController.filtrarPorCategoria(cats.get(catIdx).getNombre()));
                }
                break;
        }
    }
    
    private void listarContenidos(List<Contenido> contenidos) {
        if (contenidos.isEmpty()) {
            System.out.println("\nNo hay contenidos que mostrar.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(80));
        for (Contenido c : contenidos) {
            System.out.println(c.visualizar());
            System.out.println("═".repeat(80));
        }
    }
    
    private void editarContenido() {
        System.out.println("\n═══ EDITAR CONTENIDO ═══");
        mostrarListaSimple();
        
        System.out.print("\nID del contenido a editar: ");
        String id = scanner.nextLine();
        
        Contenido contenido = contenidoController.buscarPorId(id);
        if (contenido == null) {
            System.out.println("Contenido no encontrado");
            return;
        }
        
        System.out.print("Nuevo título (Enter para mantener): ");
        String titulo = scanner.nextLine();
        
        if (contenido instanceof Articulo) {
            System.out.print("Nuevo cuerpo (Enter para mantener): ");
            String cuerpo = scanner.nextLine();
            contenidoController.editarArticulo(id, titulo, cuerpo);
            
        } else if (contenido instanceof Video) {
            System.out.print("Nueva duración en segundos (0 para mantener): ");
            int dur = 0;
            try {
                dur = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                dur = 0;
            }
            
            System.out.print("Nueva URL (Enter para mantener): ");
            String url = scanner.nextLine();
            contenidoController.editarVideo(id, titulo, dur, url);
            
        } else if (contenido instanceof Imagen) {
            System.out.print("Nueva resolución (Enter para mantener): ");
            String res = scanner.nextLine();
            
            System.out.print("Nuevo tamaño en MB (0 para mantener): ");
            double tam = 0;
            try {
                tam = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                tam = 0;
            }
            contenidoController.editarImagen(id, titulo, res, tam);
        }
    }
    
    private void eliminarContenido() {
        System.out.println("\n═══ ELIMINAR CONTENIDO ═══");
        mostrarListaSimple();
        
        System.out.print("\nID del contenido a eliminar: ");
        String id = scanner.nextLine();
        
        System.out.print("¿Está seguro? (s/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("s")) {
            contenidoController.eliminarContenido(id, usuarioController.obtenerUsuarioActual());
        }
    }
    
    private void publicarDespublicar() {
        System.out.println("\n═══ PUBLICAR/DESPUBLICAR ═══");
        mostrarListaSimple();
        
        System.out.print("\nID del contenido: ");
        String id = scanner.nextLine();
        
        Contenido c = contenidoController.buscarPorId(id);
        if (c == null) {
            System.out.println("Contenido no encontrado");
            return;
        }
        
        if (c.estaPublicado()) {
            c.despublicar();
        } else {
            c.publicar();
        }
    }
    
    private void buscarContenidos() {
        System.out.println("\n═══ BUSCAR CONTENIDOS ═══");
        System.out.print("Término de búsqueda: ");
        String criterio = scanner.nextLine();
        
        List<Contenido> resultados = contenidoController.buscarContenidos(criterio);
        
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados");
        } else {
            System.out.println("\nResultados encontrados: " + resultados.size());
            listarContenidos(resultados);
        }
    }
    
    private void gestionarCategorias() {
        System.out.println("\n═══ GESTIONAR CATEGORÍAS ═══");
        System.out.println("1. Ver categorías");
        System.out.println("2. Agregar categoría a contenido");
        System.out.println("3. Crear nueva categoría");
        System.out.print("\nSeleccione: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                List<Categoria> cats = contenidoController.getCategorias();
                System.out.println("\nCategorías:");
                for (Categoria cat : cats) {
                    System.out.println("- " + cat.toString());
                }
                break;
                
            case 2:
                mostrarListaSimple();
                System.out.print("\nID del contenido: ");
                String id = scanner.nextLine();
                Contenido c = contenidoController.buscarPorId(id);
                
                if (c != null) {
                    System.out.println("\nCategorías disponibles:");
                    List<Categoria> categorias = contenidoController.getCategorias();
                    for (int i = 0; i < categorias.size(); i++) {
                        System.out.println((i+1) + ". " + categorias.get(i).getNombre());
                    }
                    System.out.print("Seleccione categoría: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    
                    if (idx >= 0 && idx < categorias.size()) {
                        c.agregarCategoria(categorias.get(idx));
                        System.out.println("Categoría agregada exitosamente");
                    }
                }
                break;
                
            case 3:
                System.out.print("Nombre de la categoría: ");
                String nombre = scanner.nextLine();
                System.out.print("Descripción: ");
                String desc = scanner.nextLine();
                contenidoController.agregarCategoria(new Categoria(nombre, desc));
                System.out.println("Categoría creada exitosamente");
                break;
        }
    }
    
    private void verReportes() {
        System.out.println("\n═══ REPORTES ═══");
        System.out.println("1. Reporte general");
        System.out.println("2. Reporte por tipo");
        System.out.println("3. Reporte por autor");
        System.out.println("4. Exportar reporte");
        System.out.print("\nSeleccione: ");
        
        // Actualizar el reporteController con los contenidos actuales
        reporteController = new ReporteController(contenidoController.getContenidos());
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                System.out.println(reporteController.generarReporteGeneral());
                break;
                
            case 2:
                System.out.print("Tipo (ARTICULO/VIDEO/IMAGEN): ");
                String tipo = scanner.nextLine();
                System.out.println(reporteController.generarReportePorTipo(tipo));
                break;
                
            case 3:
                Usuario actual = usuarioController.obtenerUsuarioActual();
                System.out.println(reporteController.generarReportePorAutor(actual));
                break;
                
            case 4:
                System.out.print("Formato (TXT/CSV/JSON): ");
                String formato = scanner.nextLine();
                String reporte = reporteController.exportarReporte(formato);
                System.out.println("\n═══ REPORTE EXPORTADO ═══");
                System.out.println(reporte);
                break;
        }
    }
    
    private void gestionarUsuarios() {
        if (!usuarioController.validarPermiso("ELIMINAR")) {
            System.out.println("Solo administradores pueden gestionar usuarios");
            return;
        }
        
        System.out.println("\n═══ GESTIONAR USUARIOS ═══");
        System.out.println("1. Ver usuarios");
        System.out.println("2. Registrar nuevo usuario");
        System.out.print("\nSeleccione: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                usuarioController.listarUsuarios();
                break;
                
            case 2:
                System.out.println("\nTipo de usuario:");
                System.out.println("1. Administrador");
                System.out.println("2. Editor");
                System.out.print("Seleccione: ");
                int tipo = Integer.parseInt(scanner.nextLine());
                String tipoStr = tipo == 1 ? "ADMINISTRADOR" : "EDITOR";
                
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Contraseña: ");
                String pass = scanner.nextLine();
                
                String especialidad = "";
                if (tipo == 2) {
                    System.out.print("Especialidad: ");
                    especialidad = scanner.nextLine();
                }
                
                usuarioController.registrarUsuario(tipoStr, nombre, email, pass, especialidad);
                break;
        }
    }
    
    private void mostrarListaSimple() {
        List<Contenido> contenidos = contenidoController.getContenidos();
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos");
            return;
        }
        
        System.out.println("\nContenidos:");
        for (Contenido c : contenidos) {
            String tipo = c instanceof Articulo ? "📄" : c instanceof Video ? "🎥" : "🖼️";
            String estado = c.estaPublicado() ? "✅" : "📝";
            System.out.printf("%s %s %s - %s %s\n", 
                tipo, c.getId(), c.getTitulo(), estado, 
                c.estaPublicado() ? "Publicado" : "Borrador");
        }
    }
    
    /**
     * Captura datos del usuario
     * Método auxiliar para expansiones futuras
     */
    public String capturarDatos() {
        return "";
    }
}