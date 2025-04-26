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
public class Pedidos {

    Conexion c1 = new Conexion();

    public MongoCollection ColeccionPedidos() {

        MongoCollection<Document> collection = c1.ConexionBaseDatos().getCollection("Pedidos");

        return collection;

    }

    public void Insert(String IDCliente, String fecha_pedido, boolean entregado, boolean enProceso, boolean cancelado, String metodoDePago, String total) {
        ObjectId objectId = new ObjectId(IDCliente);
        Double totalDouble = Double.parseDouble(total);
        Document nuevoPedido = new Document("ID_cliente", objectId)
                .append("Fecha_pedido", fecha_pedido)
                .append("Estado_pedido", new Document("Entregado", entregado)
                        .append("En_proceso", enProceso)
                        .append("Cancelado", cancelado))
                .append("Método_pago", metodoDePago)
                .append("Total", totalDouble);

        MongoCollection<Document> collection = ColeccionPedidos();
        collection.insertOne(nuevoPedido);
        c1.cerrarConexion();
    }

    public void eliminarPedido(String _id) {
        ObjectId objectId = new ObjectId(_id);
        ColeccionPedidos().deleteOne(Filters.eq("_id", objectId));
    }

    public DefaultTableModel reedTablas() {
        DefaultTableModel tabla = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String titulos[] = {"_id", "ID_cliente", "Fecha_pedido", "Entregado", "En_proceso", "Cancelado", "Método_pago", "Total"};
        tabla.setColumnIdentifiers(titulos);

        MongoCursor<Document> cursor = ColeccionPedidos().find().iterator();

        if (cursor != null) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Document Estado_pedido = doc.get("Estado_pedido", Document.class);
                Object[] fila = new Object[8];
                fila[0] = doc.get("_id");
                fila[1] = doc.get("ID_cliente");
                fila[2] = doc.get("Fecha_pedido");
                fila[3] = Estado_pedido != null ? Estado_pedido.get("Entregado") : "";
                fila[4] = Estado_pedido != null ? Estado_pedido.get("En_proceso") : "";
                fila[5] = Estado_pedido != null ? Estado_pedido.get("Cancelado") : "";
                fila[6] = doc.get("Método_pago");
                fila[7] = doc.get("Total");
                tabla.addRow(fila);
            }
        }
        return tabla;
    }

    public Document obtenerPedidoPorId(String _id) {
        ObjectId objectId = new ObjectId(_id);
        return (Document) ColeccionPedidos().find(Filters.eq("_id", objectId)).first();
    }

    public void actualizarPedidos(String _id, String IDCliente, String fecha_pedido, boolean entregado, boolean enProceso, boolean cancelado, String metodoDePago, String total) {
        ObjectId objectId = new ObjectId(_id);
        ObjectId objectIdCliente = new ObjectId(IDCliente);
        Double totalDouble = Double.parseDouble(total);

        Document update = new Document("$set", new Document("ID_cliente", objectIdCliente)
                .append("Fecha_pedido", fecha_pedido)
                .append("Estado_pedido", new Document("Entregado", entregado)
                        .append("En_proceso", enProceso)
                        .append("Cancelado", cancelado))
                .append("Método_pago", metodoDePago)
                .append("Total", totalDouble));

        ColeccionPedidos().updateOne(Filters.eq("_id", objectId), update);
    }
}
