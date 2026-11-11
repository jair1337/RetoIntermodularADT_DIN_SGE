package controlador;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import modelo.*;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class DaoImplementacion implements Dao {
    
    private static final String uri = "mongodb://localhost:27017";
    
    public DaoImplementacion() {
    }
   
    @Override
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            // Buscamos al usuario por su nombre de usuario
            Document documento = collection.find(eq("nombreUsuario", nombreUsuario)).first();

            if (documento == null) {
                return null;
            }

            // Comprobamos la contraseña
            String contrasenaGuardada = documento.getString("contrasenaUsuario");

            if (contrasenaGuardada == null ||
                !contrasenaGuardada.equals(contrasenaUsuario)) {
                return null;
            }

            // Recuperamos TODOS los datos del usuario
            Usuario usuario = new Usuario();

            usuario.setId(documento.getObjectId("_id").toString());
            usuario.setDni(documento.getString("dni"));
            usuario.setNombreUsuario(documento.getString("nombreUsuario"));
            usuario.setContrasenaUsuario(contrasenaGuardada);
            usuario.setRol(leerRol(documento.getString("rol")));
            usuario.setNombre(documento.getString("nombre"));
            usuario.setApellidos(documento.getString("apellidos"));
            usuario.setEmail(documento.getString("email"));
            usuario.setTelefono(documento.getString("telefono"));
            
            Document direccionDoc = documento.get("direccion", Document.class);

            if (direccionDoc != null) {
                String direccionCompleta = formatearDireccion(direccionDoc);

                usuario.setDireccion(direccionCompleta);
            }
            
            return usuario;
        }
    }
  
    @Override
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            // Comprobamos si el nombre de usuario ya existe
            Document documentoExistente = collection.find(eq("nombreUsuario", nombreUsuario)).first();

            if (documentoExistente != null) {
                return null;
            }
            
            Document direccionDoc = new Document("calle", direccion)
                    .append("numero", "")
                    .append("localidad", "")
                    .append("codigoPostal", "")
                    .append("provincia", "");

            // Creamos el nuevo documento con los datos del usuario
            Document documento = new Document("dni", dni)
                    .append("rol", "Cliente")
                    .append("nombreUsuario", nombreUsuario)
                    .append("contrasenaUsuario", contrasenaUsuario)
                    .append("nombre", nombre)
                    .append("apellidos", apellidos)
                    .append("email", email)
                    .append("telefono", telefono)
                    .append("direccion", direccionDoc);
            /* LUEGO AÑADO, NO TE OLVIDES JAIR
            .append("puntos", 0)
            .append("logrosObtenidos", new ArrayList<>()) */

            // Insertamos el documento en MongoDB
            collection.insertOne(documento);

            // Creamos el objeto Usuario que devolveremos
            Usuario usuario = new Usuario();

            usuario.setDni(dni);
            usuario.setRol(Rol.Cliente);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenaUsuario(contrasenaUsuario);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            String direccionCompleta = direccion + ", , , , ";
            usuario.setDireccion(direccionCompleta);
            
            return usuario;
        }
    }
    
    
    @Override
    public Usuario verPerfil(String id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            
            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document documento = collection.find(eq("_id", new ObjectId(id))).first();
            
            if (documento == null) {
                return null;
            }
            
            Usuario usuario = new Usuario();

            usuario.setDni(documento.getString("dni"));
            usuario.setNombreUsuario(documento.getString("nombreUsuario"));
            usuario.setContrasenaUsuario(documento.getString("contrasenaUsuario"));
            usuario.setNombre(documento.getString("nombre"));
            usuario.setApellidos(documento.getString("apellidos"));
            usuario.setEmail(documento.getString("email"));
            usuario.setTelefono(documento.getString("telefono"));
            usuario.setDireccion(documento.getString("direccion"));

            return usuario;
        }
    }
    
    @Override
    public List<Logro> verLogrosUsuario(String idUsuario) throws Exception {
        List<Logro> lista = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");

            Document usuario = database.getCollection("usuarios").find(eq("_id", new ObjectId(idUsuario))).first();

            if (usuario == null) {
                return lista;
            }

            // El usuario guarda los _id de sus logros en "logrosObtenidos"
            List<ObjectId> idsLogros = usuario.getList("logrosObtenidos", ObjectId.class);

            if (idsLogros == null || idsLogros.isEmpty()) {
                return lista;
            }

            for (Document documento : database.getCollection("logros").find(Filters.in("_id", idsLogros))) {
                Logro logro = new Logro();
                logro.setId(documento.getObjectId("_id").toString());
                logro.setNombre(documento.getString("nombre"));
                logro.setDescripcion(documento.getString("descripcion"));
                logro.setIconoUrl(documento.getString("iconoUrl"));
                lista.add(logro);
            }
        }

        return lista;
    }
    
    @Override
    public List<Logro> verLogros() throws Exception {
        
        List<Logro> listaLogros = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            
            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("logros");
            
            FindIterable<Document> documentos = collection.find();
            
            for (Document documento : documentos) {
                Logro logro = new Logro();
                
                logro.setId(documento.getObjectId("_id").toString());

                logro.setNombre(documento.getString("nombre"));

                logro.setDescripcion(documento.getString("descripcion"));

                logro.setIconoUrl(documento.getString("iconoUrl"));
                
                listaLogros.add(logro);
            }
        }
        
        return listaLogros;
    }

    @Override
    public List<Actividad> obtenerActividadesPorEmpleado(String idEmpleado) throws Exception {
        List<Actividad> lista = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> usuariosCol = database.getCollection("usuarios");
            MongoCollection<Document> actividadesCol = database.getCollection("actividades");

            // Buscamos al empleado en la colección usuarios para leer su array real "actividadesAsignadas"
            Document usuarioDoc = usuariosCol.find(eq("_id", new ObjectId(idEmpleado))).first();

            if (usuarioDoc != null) {
                List<ObjectId> idsActividades = usuarioDoc.getList("actividadesAsignadas", ObjectId.class);

                if (idsActividades != null && !idsActividades.isEmpty()) {
                    for (Document docAct : actividadesCol.find(Filters.in("_id", idsActividades))) {
                        Actividad actividad = new Actividad();
                        actividad.setId(docAct.getObjectId("_id").toString());
                        actividad.setTitulo(docAct.getString("titulo"));
                        actividad.setTipo(docAct.getString("tipo"));
                        actividad.setPistaZona(docAct.getString("pistaZona"));
                        actividad.setFechaInicio(docAct.getDate("fechaInicio"));
                        actividad.setFechaFin(docAct.getDate("fechaFin"));
                        actividad.setPrecio(numero(docAct, "precio"));
                        actividad.setAforoMaximo(docAct.getInteger("aforoMaximo"));
                        actividad.setPuntos(docAct.getInteger("puntos"));
                        actividad.setIdEmpleado(idEmpleado);

                        lista.add(actividad);
                    }
                }
            }
        }
        return lista;
    }

    @Override
    public List<Actividad> obtenerActividades() throws Exception {

        List<Actividad> listaActividades = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("actividades");
            MongoCollection<Document> collectionPolideportivos = database.getCollection("polideportivos");

            FindIterable<Document> documentos = collection.find();

            for (Document documento : documentos) {

                Actividad actividad = new Actividad();

                actividad.setId(documento.getObjectId("_id").toString());

                actividad.setTitulo(documento.getString("titulo"));

                actividad.setTipo(documento.getString("tipo"));

                actividad.setPistaZona(documento.getString("pistaZona"));

                org.bson.types.ObjectId polideportivoId = documento.getObjectId("localizacion");

                if (polideportivoId != null) {
                    
                    Document poliDoc = collectionPolideportivos.find(com.mongodb.client.model.Filters.eq("_id", polideportivoId)).first();

                    if (poliDoc != null) {
                        
                        Document direccionDoc = poliDoc.get("direccion", Document.class);
                        if (direccionDoc != null) {
                            String localizacionCompleta =
                                    direccionDoc.getString("calle") + ", " +
                                    direccionDoc.getString("numero") + ", " +
                                    direccionDoc.getString("localidad") + ", " +
                                    direccionDoc.getString("codigoPostal") + ", " +
                                    direccionDoc.getString("provincia");
                            actividad.setLocalizacion(localizacionCompleta);
                        }
                    }
                }
                
                actividad.setFechaInicio(documento.getDate("fechaInicio"));

                actividad.setFechaFin(documento.getDate("fechaFin"));

                actividad.setPrecio(numero(documento, "precio"));

                actividad.setAforoMaximo(documento.getInteger("aforoMaximo"));

                actividad.setPuntos(documento.getInteger("puntos"));

                ObjectId empId = documento.getObjectId("idEmpleado");
                if (empId != null) {
                    actividad.setIdEmpleado(empId.toString());
                }

                listaActividades.add(actividad);
            }
        }

        return listaActividades;
    }  
    
    @Override
    public List<Pista> obtenerPistas() throws Exception  {
    List<Pista> listaPistas = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("pistas");
            MongoCollection<Document> collectionPolideportivos = database.getCollection("polideportivos");

            FindIterable<Document> documentos = collection.find();

            for (Document documento : documentos) {

                Pista pista = new Pista();

                pista.setId(documento.getObjectId("_id").toString());
                pista.setNombre(documento.getString("nombre"));
                pista.setTipo(documento.getString("tipo"));
                org.bson.types.ObjectId polideportivoId = documento.getObjectId("localizacion");

                if (polideportivoId != null) {
                    
                    Document poliDoc = collectionPolideportivos.find(com.mongodb.client.model.Filters.eq("_id", polideportivoId)).first();

                    if (poliDoc != null) {
                        
                        Document direccionDoc = poliDoc.get("direccion", Document.class);
                        if (direccionDoc != null) {
                            String localizacionCompleta =
                                    direccionDoc.getString("calle") + ", " +
                                    direccionDoc.getString("numero") + ", " +
                                    direccionDoc.getString("localidad") + ", " +
                                    direccionDoc.getString("codigoPostal") + ", " +
                                    direccionDoc.getString("provincia");
                            pista.setLocalizacion(localizacionCompleta);
                        }
                    }
                }
              
                pista.setPrecioHora((documento.getDouble("precioHora")));
                pista.setEstado(EstadoPista.valueOf(documento.getString("estado")));
            
                listaPistas.add(pista);
            }
        }    
        return listaPistas;  
    }
    
    @Override
    public List<Reserva> obtenerReservas() throws Exception {
        List<Reserva> listaReservas = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("reservas");
            MongoCollection<Document> collectionUsuarios = database.getCollection("usuarios");
            MongoCollection<Document> collectionPistas = database.getCollection("pistas");
            
            FindIterable<Document> documentos = collection.find();
            
            for (Document documento : documentos) {
                Reserva reserva = new Reserva();
                
                reserva.setId(documento.getObjectId("_id").toString());
                reserva.setId(documento.getObjectId("idCliente").toString());
                reserva.setId(documento.getObjectId("idPista").toString());
                reserva.setFechaHoraInicio(documento.getDate("fechaHoraInicio"));
                reserva.setFechaHoraFin(documento.getDate("fechaHoraFin"));
                reserva.setImporte((documento.getDouble("importe")));
                reserva.setMetodoPago(Pago.valueOf(documento.getString("metodoPago")));
                reserva.setEstado(EstadoReserva.valueOf(documento.getString("estado")));
                reserva.setFechaReserva(documento.getDate("fechaReserva"));
                
                listaReservas.add(reserva);
            }
        }
        return listaReservas;
    }
    
    @Override
    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> collection = mongoClient.getDatabase("base_datos").getCollection("actividades");

            ObjectId actId = new ObjectId(idActividad);
            ObjectId usrId = new ObjectId(idUsuario);

            Document actividad = collection.find(eq("_id", actId)).first();
            if (actividad == null) {
                throw new Exception("La actividad ya no existe");
            }

            List<ObjectId> participantes = actividad.getList("participantes", ObjectId.class, new ArrayList<>());

            if (participantes.contains(usrId)) {
                throw new Exception("Ya estás registrado en esta actividad");
            }
            if (participantes.size() >= actividad.getInteger("aforoMaximo")) {
                throw new Exception("La actividad está completa");
            }

            collection.updateOne(eq("_id", actId), Updates.addToSet("participantes", usrId));
        }
    }

    @Override
    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin, double importe, Pago metodoPago) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> pistas = database.getCollection("pistas");
            MongoCollection<Document> reservas = database.getCollection("reservas");

            ObjectId pistaId = new ObjectId(idPista);
            ObjectId clienteId = new ObjectId(idUsuario);

            Document pista = pistas.find(eq("_id", pistaId)).first();
            if (pista == null) {
                throw new Exception("La pista ya no existe");
            }

            String estadoPista = pista.getString("estado");
            if (!"Libre".equalsIgnoreCase(estadoPista)) {
                throw new Exception("La pista no está disponible (estado actual: " + estadoPista + ")");
            }

            Document solape = reservas.find(Filters.and(
                    eq("idPista", pistaId),
                    Filters.ne("estado", EstadoReserva.Cancelada.name()),   
                    Filters.lt("fechaHoraInicio", fin),
                    Filters.gt("fechaHoraFin", inicio)
            )).first();

            if (solape != null) {
                throw new Exception("La pista ya está reservada en ese horario");
            }

            Document nuevaReserva = new Document("idPista", pistaId)
                    .append("idCliente", clienteId)
                    .append("fechaHoraInicio", inicio)
                    .append("fechaHoraFin", fin)
                    .append("fechaReserva", new Date())
                    .append("importe", importe)
                    .append("metodoPago", metodoPago.name())
                    .append("estado", EstadoReserva.Confirmada.name());

            reservas.insertOne(nuevaReserva);
        }
    }
    
    
    
    @Override
    public List<ItemReserva> obtenerReservasUsuario(String idUsuario) throws Exception {
        List<ItemReserva> lista = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            ObjectId usrId = new ObjectId(idUsuario);

            MongoCollection<Document> actividades = database.getCollection("actividades");
            for (Document act : actividades.find(eq("participantes", usrId))) {
                lista.add(new ItemReserva(
                        act.getObjectId("_id").toString(),
                        "Actividad",
                        act.getString("titulo"),
                        act.getDate("fechaInicio"),
                        numero(act, "precio"),
                        "Registrada"));
            }

            MongoCollection<Document> pistas = database.getCollection("pistas");
            MongoCollection<Document> reservas = database.getCollection("reservas");
            for (Document res : reservas.find(eq("idCliente", usrId))) {
                String nombrePista = "Pista";
                ObjectId pistaId = res.getObjectId("idPista");

                if (pistaId != null) {
                    Document pista = pistas.find(eq("_id", pistaId)).first();
                    if (pista != null && pista.getString("nombre") != null) {
                        nombrePista = pista.getString("nombre");
                    }
                }

                lista.add(new ItemReserva(
                        res.getObjectId("_id").toString(),
                        "Pista",
                        nombrePista,
                        res.getDate("fechaHoraInicio"),
                        numero(res, "importe"),
                        res.getString("estado")));
            }
        }

        lista.sort(Comparator.comparing(ItemReserva::getFecha, Comparator.nullsLast(Comparator.<Date>naturalOrder())));
        return lista;
    }

    private static double numero(Document d, String campo) {
        Object valor = d.get(campo);
        return (valor instanceof Number) ? ((Number) valor).doubleValue() : 0.0;
    }
    
    @Override
    public void cancelarActividad(String idUsuario, String idActividad) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> actividades = mongoClient.getDatabase("base_datos").getCollection("actividades");

            long modificados = actividades.updateOne(
                    eq("_id", new ObjectId(idActividad)),
                    Updates.pull("participantes", new ObjectId(idUsuario))
            ).getModifiedCount();

            if (modificados == 0) {
                throw new Exception("No estabas registrado en esa actividad");
            }
        }
    }

    @Override
    public void cancelarReserva(String idUsuario, String idReserva) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> reservas = mongoClient.getDatabase("base_datos").getCollection("reservas");

            long modificados = reservas.updateOne(
                    Filters.and(
                            eq("_id", new ObjectId(idReserva)),
                            eq("idCliente", new ObjectId(idUsuario)),
                            Filters.ne("estado", EstadoReserva.Cancelada.name())),
                    Updates.set("estado", EstadoReserva.Cancelada.name())
            ).getModifiedCount();

            if (modificados == 0) {
                throw new Exception("No se ha podido cancelar la reserva");
            }
        }
    }

    @Override
    public void actualizarPerfil(String idUsuario, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> collection = mongoClient.getDatabase("base_datos").getCollection("usuarios");

            ObjectId id = new ObjectId(idUsuario);

            Document repetido = collection.find(Filters.and(
                    eq("nombreUsuario", nombreUsuario),
                    Filters.ne("_id", id))).first();

            if (repetido != null) {
                throw new Exception("Ese nombre de usuario ya está en uso");
            }

            long encontrados = collection.updateOne(eq("_id", id), Updates.combine(
                    Updates.set("nombreUsuario", nombreUsuario),
                    Updates.set("contrasenaUsuario", contrasenaUsuario),
                    Updates.set("nombre", nombre),
                    Updates.set("apellidos", apellidos),
                    Updates.set("email", email),
                    Updates.set("telefono", telefono),
                    Updates.set("direccion", direccionADocumento(direccion))
            )).getMatchedCount();

            if (encontrados == 0) {
                throw new Exception("No se ha encontrado el usuario");
            }
        }
    }

    private static String formatearDireccion(Document d) {
        return texto(d, "calle") + ", "
                + texto(d, "numero") + ", "
                + texto(d, "localidad") + ", "
                + texto(d, "codigoPostal") + ", "
                + texto(d, "provincia");
    }

    private static Document direccionADocumento(String direccion) {
        String[] partes = direccion.split(",", -1);

        if (partes.length == 5) {
            return new Document("calle", partes[0].trim())
                    .append("numero", partes[1].trim())
                    .append("localidad", partes[2].trim())
                    .append("codigoPostal", partes[3].trim())
                    .append("provincia", partes[4].trim());
        }

        return new Document("calle", direccion.replaceAll("[\\s,]+$", "").trim())
                .append("numero", "")
                .append("localidad", "")
                .append("codigoPostal", "")
                .append("provincia", "");
    }

    private static String texto(Document d, String campo) {
        Object valor = d.get(campo);
        return valor == null ? "" : valor.toString();
    }
    
    private static Rol leerRol(String texto) {
        if (texto == null) {
            return null;
        }
        try {
            return Rol.valueOf(texto.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public void insertarPolideportivo(String nombre, String calle, String numero, String localidad, String provincia) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("polideportivos");

            Document direccionDoc = new Document("calle", calle)
                    .append("numero", numero)
                    .append("localidad", localidad)
                    .append("provincia", provincia);

            Document polideportivo = new Document("nombre", nombre)
                    .append("direccion", direccionDoc)
                    .append("actividades", new ArrayList<ObjectId>())
                    .append("valoraciones", new ArrayList<Document>());

            collection.insertOne(polideportivo);
        }
    }
    
    @Override
    public void registrarEmpleado(String dni, String nombreUsuario, String password, String nombre, String apellidos, String email, String telefono, String direccion, String puesto) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document existente = collection.find(eq("nombreUsuario", nombreUsuario)).first();
            if (existente != null) {
                throw new Exception("El nombre de usuario ya está en uso");
            }

            Document direccionDoc = direccionADocumento(direccion);

            Document empleado = new Document("dni", dni)
                    .append("rol", "Empleado")
                    .append("nombreUsuario", nombreUsuario)
                    .append("contrasenaUsuario", password)
                    .append("nombre", nombre)
                    .append("apellidos", apellidos)
                    .append("email", email)
                    .append("telefono", telefono)
                    .append("direccion", direccionDoc)
                    .append("puesto", puesto)
                    .append("actividadesAsignadas", new ArrayList<ObjectId>());

            collection.insertOne(empleado);
        }
        
    }
    
    @Override
    public void eliminarUsuario(String idUsuario) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            long eliminados = collection.deleteOne(eq("_id", new ObjectId(idUsuario))).getDeletedCount();

            if (eliminados == 0) {
                throw new Exception("No se ha encontrado al usuario para eliminar");
            }
        }
    }
    
    @Override
    public void modificarHorarioEmpleado(String idEmpleado, List<ObjectId> nuevasActividadesAsignadas) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            long encontrados = collection.updateOne(
                    Filters.and(
                            eq("_id", new ObjectId(idEmpleado)),
                            eq("rol", "Empleado")
                    ),
                    Updates.set("actividadesAsignadas", nuevasActividadesAsignadas)
            ).getMatchedCount();

            if (encontrados == 0) {
                throw new Exception("No se ha encontrado al empleado o no se pudo actualizar su horario/asignación");
            }
        }
    }
    
    @Override
    public void insertarZona(String nombre, String tipo, String idPolideportivo, double precioHora, String estado) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("pistas");

            Document pista = new Document("nombre", nombre)
                    .append("tipo", tipo)
                    .append("localizacion", new ObjectId(idPolideportivo))
                    .append("precioHora", precioHora)
                    .append("estado", estado);

            collection.insertOne(pista);
        }
    }
    
    @Override
    public void insertarActividad(String idPolideportivo, String titulo, String tipo, String localizacion, Date fechaInicio, Date fechaFin, double precio, int aforoMaximo, int puntos, String idEmpleado) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("actividades");

            Document actividad = new Document("titulo", titulo)
                    .append("tipo", tipo)
                    .append("pistaZona", localizacion)
                    .append("localizacion", new ObjectId(idPolideportivo))
                    .append("fechaInicio", fechaInicio)
                    .append("fechaFin", fechaFin)
                    .append("precio", precio)
                    .append("aforoMaximo", aforoMaximo)
                    .append("puntos", puntos)
                    .append("idEmpleado", idEmpleado != null && !idEmpleado.isEmpty() ? new ObjectId(idEmpleado) : null)
                    .append("participantes", new ArrayList<ObjectId>());

            collection.insertOne(actividad);
        }
    }
    
   @Override
    public List<Polideportivo> obtenerPolideportivos() throws Exception {
        List<Polideportivo> lista = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("polideportivos");

            for (Document doc : collection.find()) {
                Polideportivo poli = new Polideportivo();
                poli.setId(doc.getObjectId("_id").toString());
                poli.setNombre(doc.getString("nombre"));
                
                lista.add(poli);
            }
        }
        return lista;
    }
    @Override
    public List<Usuario> obtenerUsuarios() throws Exception {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            FindIterable<Document> documentos = collection.find();

            for (Document documento : documentos) {
                Usuario usuario = new Usuario();
                usuario.setId(documento.getObjectId("_id").toString());
                usuario.setDni(documento.getString("dni"));
                usuario.setNombreUsuario(documento.getString("nombreUsuario"));
                usuario.setContrasenaUsuario(documento.getString("contrasenaUsuario"));
                usuario.setRol(leerRol(documento.getString("rol")));
                usuario.setNombre(documento.getString("nombre"));
                usuario.setApellidos(documento.getString("apellidos"));
                usuario.setEmail(documento.getString("email"));
                usuario.setTelefono(documento.getString("telefono"));

                Document direccionDoc = documento.get("direccion", Document.class);
                if (direccionDoc != null) {
                    usuario.setDireccion(formatearDireccion(direccionDoc));
                }

                listaUsuarios.add(usuario);
            }
        }
        return listaUsuarios;
    }
}