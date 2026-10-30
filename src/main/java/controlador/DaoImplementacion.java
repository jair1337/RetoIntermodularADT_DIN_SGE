package controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import modelo.Usuario;
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
}
