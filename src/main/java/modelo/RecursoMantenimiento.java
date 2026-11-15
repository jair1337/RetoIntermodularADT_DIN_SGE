package modelo;

public final class RecursoMantenimiento {

    private final String id;
    private final String nombre;
    private final boolean actividad;

    public RecursoMantenimiento(String id, String nombre, boolean actividad) {
        this.id = id;
        this.nombre = nombre;
        this.actividad = actividad;
    }

    public String id() {
        return id;
    }

    public boolean actividad() {
        return actividad;
    }

    @Override
    public String toString() {
        return (actividad ? "Actividad: " : "Pista o aula: ") + nombre;
    }
}