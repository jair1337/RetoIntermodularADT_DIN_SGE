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
import org.bson.types.ObjectId;

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
    
    @Override
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("base_datos");

            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document documento = collection.find(eq("nombreUsuario", nombreUsuario)).first();
            
            if (documento == null) {
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

            FindIterable<Document> documentos = collection.find();

            for (Document documento : documentos) {

                Actividad actividad = new Actividad();

                actividad.setId(documento.getObjectId("_id").toString());

                actividad.setTitulo(documento.getString("titulo"));

                actividad.setTipo(documento.getString("tipo"));

                actividad.setPistaZona(documento.getString("pistaZona"));

                actividad.setLocalizacion(documento.getObjectId("localizacion").toString());

                actividad.setFechaInicio(documento.getDate("fechaInicio"));

                actividad.setFechaFin(documento.getDate("fechaFin"));

                //actividad.setPrecio(documento.getDouble("precio")); provisional, a ver si funciona
                actividad.setPrecio(((Number) documento.get("precio")).doubleValue());

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
}
