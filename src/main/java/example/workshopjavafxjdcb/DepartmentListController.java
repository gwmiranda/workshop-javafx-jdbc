package example.workshopjavafxjdcb;

import example.workshopjavafxjdcb.model.entities.Department;
import example.workshopjavafxjdcb.model.services.DepartmentService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartments;

    @FXML
    private TableColumn<Department, String> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;

    @FXML
    public void onBtNewAction(){
        System.out.println("onBtNewAction");
    }

    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId().toString()));
        tableColumnName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartments.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableList(list);
        tableViewDepartments.setItems(obsList);
    }
}
