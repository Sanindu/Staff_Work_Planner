module org.uon.workplanning {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.uon.workplanning to javafx.fxml;
    exports org.uon.workplanning;
}