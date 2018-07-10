package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private VBox graphicVbox, graphicVboxMC, graphicVboxVelocityTerm;

    @FXML
    private Button btnGenerate, btnPlotMC, btnVelocityTerm, btnClean;

    @FXML
    private TextField txtTemperature, txtMass, txtVmin, txtVmax, txtNumberOfPoints;

    @FXML
    private TableView<IntegralValue> tableResultMC;

    @FXML
    private TableColumn columnNumberOfSteps, columnIntegralValue;


    private ScatterChart scatterVelocityDistribution, scatterMC, scatterVelocityTerm;

    private XYChart.Series data, dataMC, dataVelocityTerm;

    private Random random;

    private Scene currentScene;
    private Stage currentStage;

    private double temperature, mass, vMin, vMax;
    private int numberOfPoints;

    private MaxwellBoltzmann maxwellBoltzmann;

    private ObservableList<IntegralValue> integralValues = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initVelocityDistribution();
        initMC();
        initVelocityTerm();

        random = new Random();
        maxwellBoltzmann = new MaxwellBoltzmann();

        btnGenerate.setOnAction(e -> generate());
        btnPlotMC.setOnAction(e -> generateMC());
        btnVelocityTerm.setOnAction(e -> generateVelocityTerm());
        btnClean.setOnAction(e -> clean());

    }

    public void setStageAndScene(Stage stage, Scene scene)
    {
        this.currentStage = stage;
        this.currentScene = scene;
    }

    private void initVelocityDistribution()
    {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAnimated(false);
        yAxis.setAnimated(false);

        scatterVelocityDistribution = new ScatterChart<>(xAxis,yAxis);
        graphicVbox.getChildren().addAll(scatterVelocityDistribution);

        xAxis.setLabel("Velocity (m/s)");
        yAxis.setLabel("Probability (s/m)");


    }

    private void generate()
    {
        getValues();

        data = maxwellBoltzmann.getVelocityDistribution(mass, temperature, vMin, vMax, numberOfPoints);
        data.setName("T = " + temperature + "\nm = " + mass);

        scatterVelocityDistribution.getData().add(data);
    }

    private void initMC()
    {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAnimated(false);
        yAxis.setAnimated(false);

        scatterMC = new ScatterChart<>(xAxis,yAxis);
        graphicVboxMC.getChildren().addAll(scatterMC);

        xAxis.setLabel("N");
        yAxis.setLabel("Velocity Term");

        columnNumberOfSteps.setCellValueFactory(new PropertyValueFactory<>("step"));
        columnIntegralValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableResultMC.setItems(integralValues);

    }

    private void generateMC()
    {
        getValues();

        dataMC = maxwellBoltzmann.getMCValue(mass, temperature, vMin, vMax, numberOfPoints);
        dataMC.setName("T = " + temperature + "\nN = " + numberOfPoints);
        scatterMC.getData().add(dataMC);

        double integralValue = maxwellBoltzmann.getMCIntegralValue();
        integralValues.add(new IntegralValue(numberOfPoints, integralValue));
        tableResultMC.setItems(integralValues);

    }

    private void initVelocityTerm()
    {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAnimated(false);
        yAxis.setAnimated(false);

        scatterVelocityTerm = new ScatterChart<>(xAxis,yAxis);
        graphicVboxVelocityTerm.getChildren().addAll(scatterVelocityTerm);

        xAxis.setLabel("Velocity");
        yAxis.setLabel("G(v)");
    }

    private void generateVelocityTerm()
    {
        getValues();

        dataVelocityTerm = maxwellBoltzmann.getVelocityTermDistribution(mass, temperature, vMin, vMax, numberOfPoints);
        dataVelocityTerm.setName("T = " + temperature + "\nm = " + mass);
        scatterVelocityTerm.getData().add(dataVelocityTerm);
    }

    private void clean()
    {
        txtMass.clear();
        txtTemperature.clear();
        txtVmax.clear();
        txtVmin.clear();
        txtNumberOfPoints.clear();

        scatterVelocityDistribution.getData().clear();
        scatterMC.getData().clear();
        scatterVelocityTerm.getData().clear();
    }

    private void getValues()
    {
        try
        {
            temperature = Double.valueOf(txtTemperature.getText());
            vMax = Double.valueOf(txtVmax.getText());
            vMin = Double.valueOf(txtVmin.getText());
            mass = Double.valueOf(txtMass.getText());
            numberOfPoints = Integer.valueOf(txtNumberOfPoints.getText());
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Values");
            alert.setContentText("Please, check the input values!");

            alert.showAndWait();
        }
    }
}
