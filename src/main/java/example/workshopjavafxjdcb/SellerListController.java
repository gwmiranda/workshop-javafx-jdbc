package example.workshopjavafxjdcb;

import example.workshopjavafxjdcb.db.DbIntegrityException;
import example.workshopjavafxjdcb.gui.util.Alerts;
import example.workshopjavafxjdcb.gui.util.Utils;
import example.workshopjavafxjdcb.gui.util.listeners.DataChangeListener;
import example.workshopjavafxjdcb.model.entities.Seller;
import example.workshopjavafxjdcb.model.services.SellerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;


    @FXML
    private TableView<Seller> tableViewSellers;

    @FXML
    private TableColumn<Seller, String> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, String> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, String> tableColumnBaseSalary;


    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Seller obj = new Seller();
        createDialogForm(obj ,"SellerForm.fxml", parentStage);
    }

    public void setSellerService(SellerService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        if(tableViewSellers != null){
            tableColumnId.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId().toString()));
            tableColumnName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));
            tableColumnEmail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEmail()));
            tableColumnBirthDate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBirthDate().toString()));
            tableColumnBaseSalary.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBaseSalary().toString()));

            Stage stage = (Stage) Main.getMainScene().getWindow();
            tableViewSellers.prefHeightProperty().bind(stage.heightProperty());
        }
    }

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableList(list);
        tableViewSellers.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage){
//        try {
//            FXMLLoader loader = new FXMLLoader(Main.class.getResource(absoluteName));
//            Pane pane = loader.load();
//
//            SellerFormController controller = loader.getController();
//            controller.setSeller(obj);
//            controller.setSellerService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Enter Seller data");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//
//        }catch (IOException e){
//            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "SellerForm.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result =  Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if(result.get() == ButtonType.OK){
            if(service == null){
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            }catch (DbIntegrityException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
            }

        }
    }
}
