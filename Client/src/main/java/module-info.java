module com.krizhanovsky.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires lombok;

    opens com.krizhanovsky.client to javafx.fxml;
    exports com.krizhanovsky.client;
    exports com.krizhanovsky.client.entity;
}