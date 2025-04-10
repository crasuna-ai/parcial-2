public class Articulo {
    private String nombre;
    private String referencia;
    private double precio;
    private int cantidad;

    public Articulo(String nombre, String referencia, double precio, int cantidad) {
        this.nombre = nombre;
        this.referencia = referencia;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - " + referencia;
    }

    public String mostrarDetalle() {
        return "Nombre: " + nombre +
               ", Referencia: " + referencia +
               ", Precio: $" + precio +
               ", Cantidad: " + cantidad;
    }
}