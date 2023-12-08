
package crud;

import Icons.Icon;
import crud.clases.BD;
import crud.clases.Producto;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * PROYECTO CRUD 2.0 - YouTube - Canal: L-Code.
 *
 * @author pablo
 */
public class AltaProducto extends javax.swing.JFrame {
    
    BD bada;
    DefaultTableModel modelo;
    
    
    private final String agregar_icono_ruta = "/Icons/alta.png";
    private final String modificar_icono_ruta = "/Icons/modificar.png";
    private final String eliminar_icono_ruta = "/Icons/eliminar.png";
    private final String buscar_icono_ruta = "/Icons/buscar.png";
    
    
    public AltaProducto() {
        initComponents();
        
        apagarInterfaz();
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i = JOptionPane.showConfirmDialog(rootPane, "Estás por salir del sistema, ¿Salir?", "Salir del Sistema", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
                if(i == JOptionPane.YES_OPTION){
                    System.exit(0); // Cierra la Aplicación
                }
            }
        });
        
        this.setExtendedState(this.MAXIMIZED_BOTH); // PARA PANTALLA COMPLETA AL INICIAR EL PROGRAMA
        
        loadIcons();
        
        bada = new BD("productos_crud", "root", "");
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Tipo");
        modelo.addColumn("Clave");
        modelo.addColumn("Cantidad");
        cargarProductos();
    }

    
    public void loadIcons() {
        // Boton Alta
        Icon imgAlta = new Icon(panelAlta, agregar_icono_ruta);
        panelAlta.add(imgAlta).repaint();
        panelAlta.setOpaque(false);
        panelAlta.setBorder(null);
        panelAlta.setBackground(new Color(0, 0, 0, 64));
        
        // Boton Modificar
        Icon imgModificar = new Icon(panelModificar, modificar_icono_ruta);
        panelModificar.add(imgModificar).repaint();
        panelModificar.setOpaque(false);
        panelModificar.setBorder(null);
        panelModificar.setBackground(new Color(0, 0, 0, 64));
        
        // Boton Buscar
        Icon imgBuscar = new Icon(panelBuscar, buscar_icono_ruta);
        panelBuscar.add(imgBuscar).repaint();
        panelBuscar.setOpaque(false);
        panelBuscar.setBorder(null);
        panelBuscar.setBackground(new Color(0, 0, 0, 64));
        
        // Boton Eliminar
        Icon imgEliminar = new Icon(panelEliminar, eliminar_icono_ruta);
        panelEliminar.add(imgEliminar).repaint();
        panelEliminar.setOpaque(false);
        panelEliminar.setBorder(null);
        panelEliminar.setBackground(new Color(0, 0, 0, 64));
    }

    
    public void apagarInterfaz(){
        btnIngresarProducto.setEnabled(true);
        panelAlta.setEnabled(false);
        panelEliminar.setEnabled(false);
        panelModificar.setEnabled(false);
        panelBuscar.setEnabled(false);
        txtBusqueda.setEnabled(false);
        btnLimpiarTabla.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtTipo.setEnabled(false);
        txtClave.setEnabled(false);
        txtCantidad.setEnabled(false);
    }
    
    
    public void prenderInterfaz(){
        panelAlta.setEnabled(true);
        panelEliminar.setEnabled(true);
        panelModificar.setEnabled(true);
        panelBuscar.setEnabled(true);
        txtBusqueda.setEnabled(true);
        btnLimpiarTabla.setEnabled(true);
        txtNombre.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtTipo.setEnabled(true);
        txtClave.setEnabled(true);
        txtCantidad.setEnabled(true);
    }
    
    
    public void limpiar(){
        for(int i = 0; i < tblProductos.getRowCount(); i++){
            modelo.removeRow(i);
            i -= 1;
        }
    }
    
    
    public void cargarProductos(){
        ArrayList productos;
        Producto mProducto;
        if(bada.conectar()){
            String[] datos = new String[6];
            productos = bada.GetProductos();
            if(productos != null){
                for( int i = 0; i < productos.size(); i++ ){
                    mProducto = (Producto) productos.get(i);
                    datos[0] = String.valueOf(mProducto.getProducto_id());
                    datos[1] = mProducto.getNombre();
                    datos[2] = String.valueOf(mProducto.getPrecio());
                    datos[3] = mProducto.getTipo();
                    datos[4] = mProducto.getClave();
                    datos[5] = String.valueOf(mProducto.getCantidad());
                    modelo.addRow(datos);
                }
                
                // Configuro el ancho de mis columnas
                this.tblProductos.setModel(modelo);
                this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
                this.tblProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(3).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
                this.tblProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
                
                if(this.tblProductos.getRowCount() > 0){
                    this.tblProductos.setRowSelectionInterval(0,0);
                }
            }else{
                JOptionPane.showMessageDialog(null, "ERROR al recuperar los productos");
            }
        }else{
            JOptionPane.showMessageDialog(null, "ERROR al conectar");
        }
    }
    
    
    public void filtrados(String busqueda){
        ArrayList productos;
        Producto mProducto;
        if(bada.conectar()){
            String[] datos = new String[6];
            productos = bada.filtrado(busqueda);
            if(productos != null){
                for( int i = 0; i < productos.size(); i++ ){
                    mProducto = (Producto) productos.get(i);
                    datos[0] = String.valueOf(mProducto.getProducto_id());
                    datos[1] = mProducto.getNombre();
                    datos[2] = String.valueOf(mProducto.getPrecio());
                    datos[3] = mProducto.getTipo();
                    datos[4] = mProducto.getClave();
                    datos[5] = String.valueOf(mProducto.getCantidad());
                    modelo.addRow(datos);
                }
                
                this.tblProductos.setModel(modelo);
                this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
                this.tblProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(3).setPreferredWidth(100);
                this.tblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
                this.tblProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
                
                if(this.tblProductos.getRowCount() > 0){
                    this.tblProductos.setRowSelectionInterval(0,0);
                }
            }else{
                JOptionPane.showMessageDialog(null, "ERROR al recuperar los productos");
            }
        }else{
            JOptionPane.showMessageDialog(null, "ERROR al conectar");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelAlta = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        txtClave = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        panelModificar = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        panelBuscar = new javax.swing.JPanel();
        panelEliminar = new javax.swing.JPanel();
        btnIngresarProducto = new javax.swing.JButton();
        btnLimpiarTabla = new javax.swing.JButton();
        btnSalirSistema = new javax.swing.JButton();
        btnActualizarTabla = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Eras Bold ITC", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ALTA DE PRODUCTO");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Precio:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tipo:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Clave:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Cantidad:");

        panelAlta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAltaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAltaLayout = new javax.swing.GroupLayout(panelAlta);
        panelAlta.setLayout(panelAltaLayout);
        panelAltaLayout.setHorizontalGroup(
            panelAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );
        panelAltaLayout.setVerticalGroup(
            panelAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        txtNombre.setMinimumSize(new java.awt.Dimension(64, 25));

        txtPrecio.setMinimumSize(new java.awt.Dimension(64, 25));
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        txtTipo.setMinimumSize(new java.awt.Dimension(64, 25));

        txtClave.setMinimumSize(new java.awt.Dimension(64, 25));

        txtCantidad.setMinimumSize(new java.awt.Dimension(64, 25));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        panelModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelModificarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelModificarLayout = new javax.swing.GroupLayout(panelModificar);
        panelModificar.setLayout(panelModificarLayout);
        panelModificarLayout.setHorizontalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        panelModificarLayout.setVerticalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        panelBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBuscarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBuscarLayout = new javax.swing.GroupLayout(panelBuscar);
        panelBuscar.setLayout(panelBuscarLayout);
        panelBuscarLayout.setHorizontalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        panelBuscarLayout.setVerticalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEliminarLayout = new javax.swing.GroupLayout(panelEliminar);
        panelEliminar.setLayout(panelEliminarLayout);
        panelEliminarLayout.setHorizontalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        panelEliminarLayout.setVerticalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        btnIngresarProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnIngresarProducto.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnIngresarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresarProducto.setText("INGRESAR NUEVO PRODUCTO");
        btnIngresarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIngresarProductoMouseClicked(evt);
            }
        });
        btnIngresarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarProductoActionPerformed(evt);
            }
        });

        btnLimpiarTabla.setBackground(new java.awt.Color(255, 0, 0));
        btnLimpiarTabla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiarTabla.setForeground(new java.awt.Color(0, 0, 0));
        btnLimpiarTabla.setText("Borrar TODOS los datos de la tabla");
        btnLimpiarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTablaActionPerformed(evt);
            }
        });

        btnSalirSistema.setBackground(new java.awt.Color(51, 0, 0));
        btnSalirSistema.setFont(new java.awt.Font("Cooper Black", 0, 12)); // NOI18N
        btnSalirSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirSistema.setText("Salir del Sistema");
        btnSalirSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirSistemaActionPerformed(evt);
            }
        });

        btnActualizarTabla.setBackground(new java.awt.Color(51, 51, 255));
        btnActualizarTabla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizarTabla.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarTabla.setText("Actualizar Tabla");
        btnActualizarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(13, 13, 13)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(panelAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnIngresarProducto))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnActualizarTabla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiarTabla))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalirSistema))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 39, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(panelBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnIngresarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panelEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiarTabla)
                    .addComponent(btnActualizarTabla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(btnSalirSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        // VALIDACION PARA QUE SOLO SE INGRESEN NUMEROS.
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    
    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        // VALIDACION PARA PERMITIR QUE SE INGRESEN NUMEROS Y UNICAMENTE UN PUNTO (.)
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9'))
                && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != '.' || txtPrecio.getText().contains("."))) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    
    private void panelAltaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAltaMouseClicked
        Producto mProducto = new Producto();
        // VERIFICANDO QUE MI TXT NOMBRE NO SEA UN CAMPO VACIO QUITANDO LOS POSIBLES ESPACIOS
        if(!txtNombre.getText().trim().equals("") 
                && !txtPrecio.getText().trim().equals("")
                && !txtTipo.getText().trim().equals("")
                && !txtCantidad.getText().trim().equals("")
                && !txtClave.getText().trim().equals("")){
            if(bada.conectar()){
                mProducto.setNombre(txtNombre.getText().trim());
                mProducto.setPrecio(Float.parseFloat(txtPrecio.getText().trim()));
                mProducto.setTipo(txtTipo.getText().trim());
                mProducto.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
                mProducto.setClave(txtClave.getText().trim());
                if(bada.agregarProducto(mProducto)){
                    JOptionPane.showMessageDialog(null, "Se ha dado de alta el producto.");
                    limpiar();
                    cargarProductos();
                    txtNombre.setText("");
                    txtPrecio.setText("");
                    txtTipo.setText("");
                    txtClave.setText("");
                    txtCantidad.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "ERROR al dar de alta al producto.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "ERROR al conectar a la Base de Datos.");
            }
            
        }
    }//GEN-LAST:event_panelAltaMouseClicked

    
    private void panelModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseClicked
        int producto_id = 0;
        if(tblProductos.getSelectedRow() != -1){
            producto_id = Integer.parseInt(modelo.getValueAt(tblProductos.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(rootPane, "¿Deseas modificar este producto?", "Modificación de Producto", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ){
                if(bada.conectar()){
                    Producto mProducto = new Producto();
                    mProducto.setProducto_id(producto_id);
                    mProducto.setNombre(txtNombre.getText().trim());
                    mProducto.setPrecio(Float.parseFloat(txtPrecio.getText().trim()));
                    mProducto.setTipo(txtTipo.getText().trim());
                    mProducto.setClave(txtClave.getText().trim());
                    mProducto.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
                    bada.modificarProducto(mProducto);
                    limpiar();
                    cargarProductos();
                }
            }
        }
    }//GEN-LAST:event_panelModificarMouseClicked

    
    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        txtNombre.setText(modelo.getValueAt(tblProductos.getSelectedRow(), 1).toString());
        txtPrecio.setText(modelo.getValueAt(tblProductos.getSelectedRow(), 2).toString());
        txtTipo.setText(modelo.getValueAt(tblProductos.getSelectedRow(), 3).toString());
        txtClave.setText(modelo.getValueAt(tblProductos.getSelectedRow(), 4).toString());
        txtCantidad.setText(modelo.getValueAt(tblProductos.getSelectedRow(), 5).toString());
    }//GEN-LAST:event_tblProductosMouseClicked

    private void panelBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBuscarMouseClicked

    }//GEN-LAST:event_panelBuscarMouseClicked

    private void panelEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseClicked
        int producto_id = 0;
        if(tblProductos.getSelectedRow() != -1){
            producto_id = Integer.parseInt(modelo.getValueAt(tblProductos.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(rootPane, "¿Desea eliminar este producto?", "Eliminar Producto", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                if(bada.conectar()){
                    bada.eliminarProducto(producto_id);
                    limpiar();
                    cargarProductos();
                }
            }
        }
    }//GEN-LAST:event_panelEliminarMouseClicked

    
    private void btnIngresarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarProductoActionPerformed
        txtNombre.setText("");
        txtPrecio.setText("");
        txtTipo.setText("");
        txtClave.setText("");
        txtCantidad.setText("");
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnIngresarProductoActionPerformed

    
    private void btnLimpiarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTablaActionPerformed
        //limpiar();
        int confirmacion = JOptionPane.showConfirmDialog(rootPane, "PELIGRO: Estás por borrar todos los datos de la tabla, ¿Lo deseas?", "PRECAUSIÓN: ELIMINAR TODOS LOS PRODUCTOS", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if(confirmacion == JOptionPane.YES_OPTION){
            bada.truncarTabla();
        }
    }//GEN-LAST:event_btnLimpiarTablaActionPerformed

    
    private void btnSalirSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirSistemaActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(rootPane, "Estás por salir del sistema, ¿Salir?", "Salir del Sistema", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if(confirmacion == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_btnSalirSistemaActionPerformed

    
    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        limpiar();
        filtrados(txtBusqueda.getText().trim());
    }//GEN-LAST:event_txtBusquedaKeyReleased

    
    private void btnIngresarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarProductoMouseClicked
        prenderInterfaz();    
    }//GEN-LAST:event_btnIngresarProductoMouseClicked

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTablaActionPerformed
        limpiar();
        cargarProductos();
    }//GEN-LAST:event_btnActualizarTablaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarTabla;
    private javax.swing.JButton btnIngresarProducto;
    private javax.swing.JButton btnLimpiarTabla;
    private javax.swing.JButton btnSalirSistema;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAlta;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
