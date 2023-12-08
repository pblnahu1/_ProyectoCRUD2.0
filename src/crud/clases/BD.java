package crud.clases;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;

public class BD {

    private static Connection Conection;
    private static Statement Sentencia;
    private static ResultSet Resultado;
    private final String bd;
    private final String user;
    private final String password;

    // Constructor
    public BD(String bd, String user, String password) {
        Conection = null;
        Sentencia = null;
        Resultado = null;
        this.bd = bd;
        this.user = user;
        this.password = password;
    }

    // Método para conectarse a la base de datos
    public boolean conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Conection = DriverManager.getConnection("jdbc:mysql://localhost/" + bd, user, password);
            return Conection != null;
        } catch (Exception ex) {
            return false;
        }
    }

    // Metodo para desconectar la conexion a la base de datos
    public void desconectar() {
        try {
            this.Conection.close();
        } catch (Exception ex) {
        }
    }

    // Metodo para agregar los productos a la base de datos ejecutando la consulta INSERT INTO
    public boolean agregarProducto(Producto mProducto) {
        Statement consulta;
        try {
            consulta = Conection.createStatement(); // Crear consulta
            //JOptionPane.showMessageDialog(null, "INSERT INTO productos (nombre, precio, tipo, cantidad, clave) VALUES('"+ mProducto.getNombre() +"', '"+ mProducto.getPrecio()
            //        +"','"+ mProducto.getTipo() +"','"+ mProducto.getCantidad() +"', '"+ mProducto.getClave() +"')");
            consulta.execute("INSERT INTO productos (nombre, precio, tipo, cantidad, clave) VALUES('" + mProducto.getNombre() + "', '" + mProducto.getPrecio()
                    + "','" + mProducto.getTipo() + "','" + mProducto.getCantidad() + "', '" + mProducto.getClave() + "')");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // Metodo para tomar los productos existentes en mi BD y lo almaceno en un ArrayList para despues mostrarlos en mi Tabla de la interfaz
    public ArrayList GetProductos() {
        // Creo un arraylist para almacenar los objetos Producto recuperados de la BD.
        ArrayList productos = new ArrayList();
        /*Creo una nueva instancia de la clase Producto, se usará para almacenar temporalmente 
        los datos de un producto antes de agregarlo a la lista productos.*/
        Producto mProducto = null;
        try {
            // Uso el objeto Sentencia de Statement para enviar consultas SQL a la BD
            Sentencia = Conection.createStatement();
            // Selecciono todos los registros de la tabla productos y se almacena en la variable Resultado de la clase ResultSet.
            Resultado = Sentencia.executeQuery("SELECT * FROM productos");
            // Recorro mediante un while cada fila de resultados en el objeto ResultSet
            while (Resultado.next()) {
                mProducto = new Producto();
                mProducto.setProducto_id(Resultado.getInt("producto_id"));
                mProducto.setNombre(Resultado.getString("nombre"));
                mProducto.setPrecio(Resultado.getFloat("precio"));
                mProducto.setTipo(Resultado.getString("tipo"));
                mProducto.setClave(Resultado.getString("clave"));
                mProducto.setCantidad(Resultado.getInt("cantidad"));
                productos.add(mProducto);
            }
        } catch (Exception e) {
            return null;
        }
        // Devuelvo como resultado de la funcion. Esta lista va a tener objetos Producto, cada uno
        // representando un registro de la tabla de productos de la BD.
        return productos;
    }

    public void eliminarProducto(int producto_id) {
        Statement consulta;
        try {
            consulta = Conection.createStatement(); // Crear consulta
            consulta.execute("DELETE FROM productos WHERE producto_id = " + producto_id);

        } catch (Exception ex) {

        }
    }
    
    public void truncarTabla() {
        Statement consulta;
        try {
            consulta = Conection.createStatement(); // Crear consulta
            consulta.execute("TRUNCATE TABLE productos");

        } catch (Exception ex) {

        }
    }
    
    public boolean modificarProducto(Producto mProducto) {
        Statement consulta;
        try {
            consulta = Conection.createStatement(); // Crear consulta
            consulta.execute("UPDATE productos SET nombre='" + mProducto.getNombre() + "'"
                    + ", precio='"+ mProducto.getPrecio() +"'"
                    + ", tipo='"+ mProducto.getTipo() +"'"
                    + ", cantidad='"+ mProducto.getCantidad() +"'"
                    + ", clave='"+ mProducto.getClave() +"' WHERE producto_id='"+ mProducto.getProducto_id() +"'");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public ArrayList filtrado(String busqueda){
        ArrayList productos = new ArrayList();
        Producto mProducto = null;
        try {
            Sentencia = Conection.createStatement();
            Resultado = Sentencia.executeQuery("SELECT * FROM productos "
                    + "WHERE producto_id LIKE " + "'%" + busqueda + "%'" + " "
                    + "OR nombre LIKE " + "'%" + busqueda + "%'" + " "
                    + "OR precio LIKE " + "'%" + busqueda + "%'" + " "
                    + "OR tipo LIKE " + "'%" + busqueda + "%'" + " "
                    + "OR clave LIKE " + "'%" + busqueda + "%'" + " "
                    + "OR cantidad LIKE " + "'%" + busqueda + "%'");                                                                                                                                            
            
            while (Resultado.next()) {
                mProducto = new Producto();
                mProducto.setProducto_id(Resultado.getInt("producto_id"));
                mProducto.setNombre(Resultado.getString("nombre"));
                mProducto.setPrecio(Resultado.getFloat("precio"));
                mProducto.setTipo(Resultado.getString("tipo"));
                mProducto.setClave(Resultado.getString("clave"));
                mProducto.setCantidad(Resultado.getInt("cantidad"));
                productos.add(mProducto);
            }
        } catch (Exception e) {
            return null;
        }
        return productos;
    }
}
