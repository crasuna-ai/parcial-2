import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class SistemaVentas {
    private Queue<Articulo> cola = new LinkedList<>();

    public void iniciar() {
        while (true) {
            String opcion = JOptionPane.showInputDialog("""
                    1. Ingresar artículo
                    2. Vender artículo
                    3. Modificar artículo
                    4. Eliminar artículo
                    5. Mostrar artículos
                    6. Salir""");

            if (opcion == null) break;

            switch (opcion) {
                case "1" -> ingresarArticulo();
                case "2" -> venderArticulo();
                case "3" -> modificarArticulo();
                case "4" -> eliminarArticulo();
                case "5" -> mostrarArticulos();
                case "6" -> System.exit(0);
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }

    private void ingresarArticulo() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del artículo:");
            if (nombre == null || nombre.trim().isEmpty())
                throw new Exception("Nombre inválido");

            String referencia = JOptionPane.showInputDialog("Referencia del artículo:");
            if (referencia == null || referencia.trim().isEmpty())
                throw new Exception("Referencia inválida");

            double precio = pedirDouble("Precio:");
            int cantidad = pedirEntero("cantidad");


            cola.add(new Articulo(nombre.trim(), referencia.trim(), precio, cantidad));
            JOptionPane.showMessageDialog(null, "Artículo agregado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar artículo: " + e.getMessage());
        }
    }

    private void venderArticulo() {
        if (cola.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay artículos para vender.");
            return;
        }

        try {
            double ventaPromedio = pedirDouble("Ingrese la venta promedio:");

            Articulo seleccionado = seleccionarArticulo("Seleccione el artículo a vender:");
            if (seleccionado == null) return;

            if (seleccionado.getCantidad() <= 0) {
                JOptionPane.showMessageDialog(null, "Este artículo no tiene unidades disponibles.");
                return;
            }

            String nombreCliente = JOptionPane.showInputDialog("Nombre del cliente:");
            if (nombreCliente == null || nombreCliente.trim().isEmpty())
                throw new Exception("Nombre del cliente inválido");

            String cedula = JOptionPane.showInputDialog("Cédula del cliente:");
            if (cedula == null || cedula.trim().isEmpty())
                throw new Exception("Cédula inválida");

            double precioFinal = seleccionado.getPrecio();
            boolean tieneDescuento = false;

            if (precioFinal > ventaPromedio) {
                precioFinal *= 0.9;
                tieneDescuento = true;
            }

            seleccionado.setCantidad(seleccionado.getCantidad() - 1);

            JOptionPane.showMessageDialog(null,
                    "Venta realizada\nCliente: " + nombreCliente +
                    "\nCédula: " + cedula +
                    "\nArtículo: " + seleccionado.getNombre() +
                    "\nReferencia: " + seleccionado.getReferencia() +
                    "\nPrecio final: $" + precioFinal +
                    (tieneDescuento ? "\n(Descuento del 10% aplicado)" : ""));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void modificarArticulo() {
        if (cola.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay artículos para modificar.");
            return;
        }

        try {
            double ventaPromedio = pedirDouble("Ingrese la venta promedio:");

            Articulo seleccionado = seleccionarArticulo("Seleccione el artículo a modificar:");
            if (seleccionado == null) return;

            double nuevoPrecio = pedirDouble("Nuevo precio:");
            if (nuevoPrecio > ventaPromedio) {
                nuevoPrecio *= 0.9;
            }

            int nuevaCantidad = pedirEntero("Nueva cantidad:");

            seleccionado.setPrecio(nuevoPrecio);
            seleccionado.setCantidad(nuevaCantidad);

            JOptionPane.showMessageDialog(null, "Artículo modificado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void eliminarArticulo() {
        if (cola.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay artículos para eliminar.");
            return;
        }

        Articulo seleccionado = seleccionarArticulo("Seleccione el artículo a eliminar:");
        if (seleccionado == null) return;

        Queue<Articulo> nuevaCola = new LinkedList<>();
        boolean eliminado = false;

        while (!cola.isEmpty()) {
            Articulo actual = cola.poll();
            if (actual != seleccionado) {
                nuevaCola.add(actual);
            } else {
                eliminado = true;
            }
        }

        cola = nuevaCola;

        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Artículo eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Artículo no encontrado.");
        }
    }

    private void mostrarArticulos() {
        if (cola.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay artículos en el inventario.");
            return;
        }

        StringBuilder sb = new StringBuilder("Artículos en inventario:\n");
        for (Articulo art : cola) {
            sb.append(art.mostrarDetalle()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private Articulo seleccionarArticulo(String mensaje) {
        Articulo[] articulosArray = cola.toArray(new Articulo[0]);
        return (Articulo) JOptionPane.showInputDialog(
                null,
                mensaje,
                "Seleccionar Artículo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                articulosArray,
                articulosArray[0]
        );
    }

    private double pedirDouble(String mensaje) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(mensaje);
                if (input == null) throw new Exception("Operación cancelada");
                double valor = Double.parseDouble(input);
                if (valor <= 0) throw new NumberFormatException();
                return valor;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido mayor a 0.");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private int pedirEntero(String mensaje) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(mensaje);
                if (input == null) throw new Exception("Operación cancelada");
                int valor = Integer.parseInt(input);
                if (valor <= 0) throw new NumberFormatException();
                return valor;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número entero válido mayor a 0.");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}