/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pizza40crud.logica;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author imlau
 */
public class Conexion {

    private MongoClient mongoClient;

    public MongoDatabase ConexionBaseDatos() {
        String uri = "mongodb+srv://imlauerpablo:54321@cluster0.bmsfu6q.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("Pizza40");
        return database;

    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
