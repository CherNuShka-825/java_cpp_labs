package ru.nsu.ccfit.kombarov.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public final class FactoryView {

    @FXML private ProgressBar bodyStorageBar;
    @FXML private Label bodyStorageLabel;
    @FXML private ProgressBar motorStorageBar;
    @FXML private Label motorStorageLabel;
    @FXML private ProgressBar accessoryStorageBar;
    @FXML private Label accessoryStorageLabel;
    @FXML private ProgressBar autoStorageBar;
    @FXML private Label autoStorageLabel;

    @FXML private Slider bodyDelaySlider;
    @FXML private Label bodyDelayValue;
    @FXML private Slider motorDelaySlider;
    @FXML private Label motorDelayValue;
    @FXML private Slider accessoryDelaySlider;
    @FXML private Label accessoryDelayValue;
    @FXML private Slider dealerDelaySlider;
    @FXML private Label dealerDelayValue;
    @FXML private Slider workerDelaySlider;
    @FXML private Label workerDelayValue;

    @FXML private Label totalAutosLabel;
    @FXML private Label queueSizeLabel;

    public void setBodyStorage(int current, int capacity) {
        bodyStorageBar.setProgress((double) current / capacity);
        bodyStorageLabel.setText(current + "/" + capacity);
    }

    public void setMotorStorage(int current, int capacity) {
        motorStorageBar.setProgress((double) current / capacity);
        motorStorageLabel.setText(current + "/" + capacity);
    }

    public void setAccessoryStorage(int current, int capacity) {
        accessoryStorageBar.setProgress((double) current / capacity);
        accessoryStorageLabel.setText(current + "/" + capacity);
    }

    public void setAutoStorage(int current, int capacity) {
        autoStorageBar.setProgress((double) current / capacity);
        autoStorageLabel.setText(current + "/" + capacity);
    }

    public void setStatistics(int sold, int tasks) {
        totalAutosLabel.setText(String.valueOf(sold));
        queueSizeLabel.setText(String.valueOf(tasks));
    }

    public void updateDelayLabels() {
        bodyDelayValue.setText((int) bodyDelaySlider.getValue() + " ms");
        motorDelayValue.setText((int) motorDelaySlider.getValue() + " ms");
        accessoryDelayValue.setText((int) accessoryDelaySlider.getValue() + " ms");
        dealerDelayValue.setText((int) dealerDelaySlider.getValue() + " ms");
        workerDelayValue.setText((int) workerDelaySlider.getValue() + "ms");
    }

    public Slider getBodyDelaySlider() { return bodyDelaySlider; }
    public Slider getMotorDelaySlider() { return motorDelaySlider; }
    public Slider getAccessoryDelaySlider() { return accessoryDelaySlider; }
    public Slider getDealerDelaySlider() { return dealerDelaySlider; }
    public Slider getWorkerDelaySlider() {return workerDelaySlider; }
}