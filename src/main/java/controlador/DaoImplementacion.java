package controlador;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;

import modelo.*;
import org.bson.Document;

public class DaoImplementacion implements Dao {
   
    private static final String uri = "mongodb://localhost:27017";
    
    public DaoImplementacion() {
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
            
            /*
            Document direccionDoc = documento.get("direccion", Document.class);
            
                String calle = direccionDoc.getString("calle");
                String numero = direccionDoc.getString("numero");
                String localidad = direccionDoc.getString("localidad");
                String codigoPostal = direccionDoc.getString("codigoPostal");
                String provincia = direccionDoc.getString("provincia");
            */

            //usuario.setDireccion(documento.getString("direccion"));

            return usuario;
        }
    }
    /*
    @Override
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document documento = collection.find(eq("nombreUsuario", nombreUsuario)).first();

            if (documento == null) {
                return null;
            }

            String contrasenaGuardada = documento.getString("contrasenaUsuario");

            if (contrasenaGuardada == null || !contrasenaGuardada.equals(contrasenaUsuario)) {
                return null;
            }

            Usuario usuario = new Usuario();

            usuario.setNombreUsuario(documento.getString("nombreUsuario"));

            usuario.setContrasenaUsuario(contrasenaGuardada);

            return usuario;
        }
    }
    */
    
    @Override
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document documento = collection.find(eq("nombreUsuario", nombreUsuario)).first();
            
            if (documento != null) {
                return null;
            }
            
            FindIterable<Document> documentos = collection.find();
            
            collection.insertOne(documento);
            
            Usuario usuario = new Usuario();

            usuario.setDni(dni);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenaUsuario(contrasenaUsuario);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);

            return usuario;
        }
        
    }
    
    @Override
    public Usuario verPerfil(String id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            
            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document documento = collection.find(eq("_id", id)).first();
            
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

                actividad.setPrecio((documento.getDouble("precio")));

                actividad.setAforoMaximo(documento.getInteger("aforoMaximo"));

                actividad.setPuntos(documento.getInteger("puntos"));

                actividad.setIdEmpleado(documento.getObjectId("idEmpleado").toString()
                );

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
                            pista.setLocalizacion(localizacionCompleta);
                        }
                    }
                }
              
                pista.setPrecioHora((documento.getDouble("precioHora")));

//                pista.setEstado((documento.getString("estado")));
                pista.setEstado(Estado.valueOf(documento.getString("estado")));

                
                listaPistas.add(pista);
            }
        }    
        return listaPistas;  
    }
}
