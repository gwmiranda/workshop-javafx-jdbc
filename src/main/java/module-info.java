module example.workshopjavafxjdcb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;

    opens example.workshopjavafxjdcb to javafx.fxml;
    exports example.workshopjavafxjdcb;
}