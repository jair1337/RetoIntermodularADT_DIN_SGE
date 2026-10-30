module Reto1Intermodular {
    exports main;
    opens com.mycompany.reto1intermodular to javafx.fxml;
    
    requires javafx.controlsEmpty;
    requires javafx.controls;
    requires javafx.graphicsEmpty;
    requires javafx.graphics;
    requires javafx.baseEmpty;
    requires javafx.base;
    requires javafx.fxmlEmpty;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires java.base;
}
