package example.workshopjavafxjdcb;

import example.workshopjavafxjdcb.gui.util.Alerts;
import example.workshopjavafxjdcb.gui.util.Utils;
import example.workshopjavafxjdcb.gui.util.listeners.DataChangeListener;
import example.workshopjavafxjdcb.model.entities.Department;
import example.workshopjavafxjdcb.model.services.DepartmentService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable, DataChangeListener {

    private DepartmentService service;


    @FXML
    private TableView<Department> tableViewDepartments;

    @FXML
    private TableColumn<Department, String> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj ,"DepartmentForm.fxml", parentStage);
    }

    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        if(tableViewDepartments != null){
            tableColumnId.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId().toString()));
            tableColumnName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));

            Stage stage = (Stage) Main.getMainScene().getWindow();
            tableViewDepartments.prefHeightProperty().bind(stage.heightProperty());
        }
    }

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableList(list);
        tableViewDepartments.setItems(obsList);
        initEditButtons();
    }

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(absoluteName));
            Pane pane = loader.load();

            DepartmentFormController controller = loader.getController();
            controller.setDepartment(obj);
            controller.setDepartmentService(new DepartmentService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "DepartmentForm.fxml",Utils.currentStage(event)));
            }
        });
    }
}
