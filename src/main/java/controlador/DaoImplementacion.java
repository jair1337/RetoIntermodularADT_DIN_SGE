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
    /*
    try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }  
    */
    }
   
    /*
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
            usuario.setNombre(documento.getString("nombre"));
            usuario.setApellidos(documento.getString("apellidos"));
            usuario.setEmail(documento.getString("email"));
            usuario.setTelefono(documento.getString("telefono"));
            
            Document direccionDoc = documento.get("direccion", Document.class);

            if (direccionDoc != null) {
                String direccionCompleta =
                        direccionDoc.getString("calle") + ", " +
                        direccionDoc.getString("numero") + ", " +
                        direccionDoc.getString("localidad") + ", " +
                        direccionDoc.getString("codigoPostal") + ", " +
                        direccionDoc.getString("provincia");

                usuario.setDireccion(direccionCompleta);
            }
            
            return usuario;
        }
    }
    */
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
                    .append("nombreUsuario", nombreUsuario)
                    .append("contrasenaUsuario", contrasenaUsuario)
                    .append("nombre", nombre)
                    .append("apellidos", apellidos)
                    .append("email", email)
                    .append("telefono", telefono)
                    .append("direccion", direccionDoc);

            // Insertamos el documento en MongoDB
            collection.insertOne(documento);

            // Creamos el objeto Usuario que devolveremos
            Usuario usuario = new Usuario();

            usuario.setDni(dni);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenaUsuario(contrasenaUsuario);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            String direccionCompleta = direccion + ", , , , ";
            usuario.setDireccion(direccionCompleta);
            //usuario.setDireccion(direccion);
            
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

                // Ojito en como se obtiene el objectId
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

                actividad.setIdEmpleado(documento.getObjectId("idEmpleado").toString());

                
                /*
                List<ObjectId> participantesMongo = documento.getList("participantes", ObjectId.class);

                List<Cliente> participantes = new ArrayList<>();

                for (ObjectId participante : participantesMongo) {
                    participantes.add(participante.toString());
                }
                
                actividad.setParticipantes(participantes);
                */

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

            // $addToSet evita duplicados aunque haya dos peticiones a la vez
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

            // 1. Validar contra "Libre" que es como lo tienes en MongoDB y en la interfaz
            String estadoPista = pista.getString("estado");
            if (!"Libre".equalsIgnoreCase(estadoPista)) {
                throw new Exception("La pista no está disponible (estado actual: " + estadoPista + ")");
            }

            // 2. Comprobación de solapes usando los nombres correctos (fechaHoraInicio / fechaHoraFin)
            Document solape = reservas.find(Filters.and(
                    eq("idPista", pistaId),
                    Filters.ne("estado", EstadoReserva.Cancelada.name()),   // las canceladas no bloquean el horario
                    Filters.lt("fechaHoraInicio", fin),
                    Filters.gt("fechaHoraFin", inicio)
            )).first();

            if (solape != null) {
                throw new Exception("La pista ya está reservada en ese horario");
            }

            // 3. Insertar respetando los nombres de campos de tu colección 'reservas'
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
    
    
    
    // ------------------------------------------------------------------
    // Actividades registradas + pistas reservadas de UN usuario
    // ------------------------------------------------------------------
    @Override
    public List<ItemReserva> obtenerReservasUsuario(String idUsuario) throws Exception {
        List<ItemReserva> lista = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            ObjectId usrId = new ObjectId(idUsuario);

            // Actividades: el usuario aparece en el array "participantes"
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

            // Pistas: reservas cuyo idCliente es el usuario
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

        // Ordenadas por fecha
        lista.sort(Comparator.comparing(ItemReserva::getFecha, Comparator.nullsLast(Comparator.<Date>naturalOrder())));
        return lista;
    }

    // Lee un número aunque en Mongo esté guardado como Int32 o como Double
    private static double numero(Document d, String campo) {
        Object valor = d.get(campo);
        return (valor instanceof Number) ? ((Number) valor).doubleValue() : 0.0;
    }
    
    // ------------------------------------------------------------------
    // Cancelar la participación en una actividad
    // ------------------------------------------------------------------
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

    // ------------------------------------------------------------------
    // Cancelar una reserva de pista (queda guardada con estado "Cancelada")
    // ------------------------------------------------------------------
    @Override
    public void cancelarReserva(String idUsuario, String idReserva) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> reservas = mongoClient.getDatabase("base_datos").getCollection("reservas");

            // Solo se puede cancelar una reserva propia y que no esté ya cancelada
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

    // ------------------------------------------------------------------
    // Actualizar los datos del perfil
    // ------------------------------------------------------------------
    @Override
    public void actualizarPerfil(String idUsuario, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> collection = mongoClient.getDatabase("base_datos").getCollection("usuarios");

            ObjectId id = new ObjectId(idUsuario);

            // El nuevo nombre de usuario no puede ser de otra persona
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

    // "calle, numero, localidad, codigoPostal, provincia" (sin escribir "null")
    private static String formatearDireccion(Document d) {
        return texto(d, "calle") + ", "
                + texto(d, "numero") + ", "
                + texto(d, "localidad") + ", "
                + texto(d, "codigoPostal") + ", "
                + texto(d, "provincia");
    }

    // Inversa de formatearDireccion: si el texto tiene 5 partes separadas por coma
    // se reparte en los 5 campos; si no, todo va a "calle" (como hace el registro)
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
    
    // Convierte el texto guardado en Mongo ("Cliente", "Empleado"...) en el enum Rol.
    // Devuelve null si el campo falta o no es un rol válido.
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
    public void registrarEmpleado(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion, String puesto) throws Exception {
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
                    .append("contrasenaUsuario", contrasenaUsuario)
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

            long modificados = collection.updateOne(
                    Filters.and(
                            eq("_id", new ObjectId(idEmpleado)),
                            eq("rol", "Empleado")
                    ),
                    Updates.set("actividadesAsignadas", nuevasActividadesAsignadas)
            ).getModifiedCount();

            if (modificados == 0) {
                throw new Exception("No se ha encontrado al empleado o no se pudo actualizar su horario/asignación");
            }
        }
    }
    @Override
    public void insertarZona(String nombre, String tipo, String idPolideportivo, double precioHora, String estado) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("base_datos");
            MongoCollection<Document> collection = database.getCollection("pistas");

            // Creamos el documento respetando los campos y convirtiendo el ID del polideportivo a ObjectId
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
                .append("idEmpleado", new ObjectId(idEmpleado))
                .append("participantes", new ArrayList<ObjectId>());

        collection.insertOne(actividad);
    }
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
