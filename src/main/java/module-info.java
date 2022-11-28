module com.dan.spatialdirectorypreviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    requires org.geotools.shapefile;

    opens com.dan.spatialdirectorypreviewer to javafx.fxml;
    exports com.dan.spatialdirectorypreviewer;
}