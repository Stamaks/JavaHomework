package karnaukhova;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public MenuItem menuItemHelp;
    public MenuItem menuItemAuthor;
    public Canvas mainCanvas;
    public Slider sliderSwan;
    public Slider sliderPike;
    public Slider sliderCrawfish;
    public TextField textSBottom;
    public TextField textSTop;
    public TextField textSleepBottom;
    public TextField textSleepTop;
    public TextField textDuration;
    public TextField textWaggonX;
    public TextField textWaggonY;
    public Button buttonStart;
    public Button buttonStop;
    public Button buttonReset;

    public void onMenuItemHelpAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
        Stage helpStage = new Stage();
        helpStage.setTitle("Руководство");
        helpStage.setScene(new Scene(root));
        helpStage.setResizable(false);
        helpStage.show();
    }

    public void onMenuItemAuthorAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("aboutAuthor.fxml"));
        Stage authorStage = new Stage();
        authorStage.setTitle("Об авторе");
        authorStage.setScene(new Scene(root));
        authorStage.setResizable(false);
        authorStage.show();
    }

    public void onDragSwanDone(DragEvent dragEvent) {

    }

    public void onDragPikeDone(DragEvent dragEvent) {

    }

    public void onDragCrawfishDone(DragEvent dragEvent) {

    }

    public void onTextSBottomAction(ActionEvent actionEvent) {

    }

    public void onTextSTopAction(ActionEvent actionEvent) {

    }

    public void onTextSleepBottomAction(ActionEvent actionEvent) {

    }

    public void onTextSleepTopAction(ActionEvent actionEvent) {

    }

    public void onTextDurationAction(ActionEvent actionEvent) {

    }

    public void onTextWaggonXAction(ActionEvent actionEvent) {

    }

    public void onTextWaggonYAction(ActionEvent actionEvent) {

    }

    public void onStartButtonAction(ActionEvent actionEvent) {

        // TODO: начало всего действия

        buttonStop.setDisable(false);

        setDisableAll(
                true, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );
    }

    public void onStopButtonAction(ActionEvent actionEvent) {

        //TODO: остановка всего действия и присваивание контроллерам значений
        buttonStop.setDisable(true);
        setDisableAll(
                false, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );
    }

    public void onResetButtonAction(ActionEvent actionEvent) {

        // TODO: остановка всего действия

        buttonStop.setDisable(true);
        setDisableAll(
                false, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );

        setControllersDefault();

    }

    private void setDisableAll(boolean state, Control ... controls) {
        for (Control control: controls)
            control.setDisable(state);
    }

    private void setControllersDefault() {
        sliderSwan.setValue(60);
        sliderPike.setValue(180);
        sliderCrawfish.setValue(300);
        textSBottom.setText("1");
        textSTop.setText("10");
        textSleepBottom.setText("1000");
        textSleepTop.setText("5000");
        textDuration.setText("25");
        textWaggonX.setText("0");
        textWaggonY.setText("0");
    }

    private void showErrorStage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("valueError.fxml"));
        Stage errorStage = new Stage();
        errorStage.setTitle("Ошибка!");
        errorStage.setScene(new Scene(root));
        errorStage.setResizable(false);
        errorStage.setAlwaysOnTop(true);
        errorStage.show();
    }
}
