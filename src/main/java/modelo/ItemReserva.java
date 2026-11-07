package modelo;
 
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
 
// Fila de la tabla de ClienteCancelar
public class ItemReserva {
 
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
 
    private final String id;
    private final String tipo;      
    private final String nombre;    
    private final Date fecha;
    private final double importe;
    private final String estado;
 
    public ItemReserva(String id, String tipo, String nombre, Date fecha, double importe, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.importe = importe;
        this.estado = estado;
    }
 
    public String getId() {
        return id;
    }
 
    public String getTipo() {
        return tipo;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public Date getFecha() {
        return fecha;
    }
 
    // Para mostrar la fecha ya formateada en la tabla
    public String getFechaTexto() {
        if (fecha == null) {
            return "";
        }
        return FORMATO.format(fecha.toInstant().atZone(ZoneId.systemDefault()));
    }
 
    public double getImporte() {
        return importe;
    }
 
    public String getEstado() {
        return estado;
    }
}