/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pizza40crud.logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author imlau
 */
public class Clientes {

    Conexion c1 = new Conexion();

    public MongoCollection ColeccionClientes() {

        MongoCollection<Document> collection = c1.ConexionBaseDatos().getCollection("Clientes");

        return collection;

    }

    public void Insert(String nombre, String direccion, String telefono, String email, String fechaRegistro) {
        Document nuevoCliente = new Document("Nombre", nombre)
                .append("Dirección", direccion)
                .append("Teléfono", telefono)
                .append("Email", email)
                .append("Fecha_registro", fechaRegistro);

        MongoCollection<Document> collection = ColeccionClientes();
        collection.insertOne(nuevoCliente);
        c1.cerrarConexion();
    }

    public void eliminarCliente(String _id) {
        ObjectId objectId = new ObjectId(_id);
        ColeccionClientes().deleteOne(Filters.eq("_id", objectId));
    }

    public DefaultTableModel reedTablas() {
        DefaultTableModel tabla = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String titulos[] = {"_id", "Nombre", "Direción", "Teléfono", "Email", "Fecha_registro"};
        tabla.setColumnIdentifiers(titulos);

        MongoCursor<Document> cursor = ColeccionClientes().find().iterator();

        if (cursor != null) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Object[] fila = new Object[6];
                fila[0] = doc.get("_id");
                fila[1] = doc.get("Nombre");
                fila[2] = doc.get("Dirección");
                fila[3] = doc.get("Teléfono");
                fila[4] = doc.get("Email");
                fila[5] = doc.get("Fecha_registro");
                tabla.addRow(fila);
            }
        }
        return tabla;
    }

    public void actualizarCliente(String _id, String nombre, String direccion, String telefono, String email, String fechaRegistro) {
        ObjectId objectId = new ObjectId(_id);
        

        Document update = new Document("$set", new Document("Nombre", nombre)
                .append("Dirección", direccion)
                .append("Teléfono", telefono)
                .append("Email", email)
                .append("Fecha_registro", fechaRegistro));

        ColeccionClientes().updateOne(Filters.eq("_id", objectId), update);
    }
    
    
    public Document obtenerClientePorId(String _id) {
        ObjectId objectId = new ObjectId(_id);
        return (Document) ColeccionClientes().find(Filters.eq("_id", objectId)).first();
    }
}
