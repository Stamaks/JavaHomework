package karnaukhova;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final String NEGATIVE_NUMBER_MESSAGE = "Значение этого параметра не может быть отрицательным";
    private final String WRONG_NUMBER_FORMAT_MESSAGE = "Проверьте правильность значения параметра";
    private final String LEFT_BOUND_MESSAGE = "Значение нижней границы не может превышать значения верхней границы";

    public MenuItem menuItemHelp;
    public MenuItem menuItemAuthor;
    public MenuItem menuItemExit;
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
    public Pane drawPane;
    public ImageView pikeImage;
    public ImageView waggonImage;
    public ImageView crawfishImage;

    private Observer observer;
    private Thread observerThread;
    private Animation animation;

    public void initialize() {
        setOnFocusLostListeners();
        ImageView swanImage = new ImageView(new Image("karnaukhova/img/swan.png"));
        ImageView pikeImage = new ImageView(new Image("karnaukhova/img/pike.png"));
        ImageView crawfishImage = new ImageView(new Image("karnaukhova/img/crawfish.png"));
        ImageView waggonImage = new ImageView(new Image("karnaukhova/img/waggon.png"));
        swanImage.setFitWidth(80);
        swanImage.setFitHeight(50);
        pikeImage.setFitWidth(80);
        pikeImage.setFitHeight(50);
        crawfishImage.setFitWidth(80);
        crawfishImage.setFitHeight(50);
        waggonImage.setFitWidth(80);
        waggonImage.setFitHeight(50);

        double centerX = drawPane.getWidth() / 2;
        double centerY = drawPane.getHeight() / 2;

        waggonImage.relocate(0, 0);
        swanImage.relocate(-waggonImage.getFitWidth() / 2, 0);
//        pikeImage.relocate(waggonImage.);
        drawPane.getChildren().addAll(swanImage, pikeImage, crawfishImage, waggonImage);
    }


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

    public void onMenuItemExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onTextSBottomAction(ActionEvent actionEvent) throws IOException {
        checkLeftBoundary(textSBottom, textSTop);
    }

    public void onTextSTopAction(ActionEvent actionEvent) throws IOException {
        checkRightBoundary(textSTop, textSBottom);
    }

    public void onTextSleepBottomAction(ActionEvent actionEvent) throws IOException {
        checkLeftBoundary(textSleepBottom, textSleepTop);
    }

    public void onTextSleepTopAction(ActionEvent actionEvent) throws IOException {
        checkRightBoundary(textSleepTop, textSleepBottom);
    }

    public void onTextDurationAction(ActionEvent actionEvent) throws IOException {
        try {
            Integer.parseInt(textDuration.getText());
        }
        catch (NumberFormatException e) {
            showErrorStage(textDuration, WRONG_NUMBER_FORMAT_MESSAGE);
        }
    }

    public void onTextWaggonXAction(ActionEvent actionEvent) throws IOException {
        try {
            Double.parseDouble(textWaggonX.getText());
        }
        catch (NumberFormatException e) {
            showErrorStage(textWaggonX, WRONG_NUMBER_FORMAT_MESSAGE);
        }
    }

    public void onTextWaggonYAction(ActionEvent actionEvent) throws IOException {
        try {
            Double.parseDouble(textWaggonY.getText());
        }
        catch (NumberFormatException e) {
            showErrorStage(textWaggonY, WRONG_NUMBER_FORMAT_MESSAGE);
        }
    }

    public void onStartButtonAction(ActionEvent actionEvent) {


        // TODO: сделать проверку на превышение интеджера

        crawfishImage.setVisible(true);
        crawfishImage.toFront();
        crawfishImage.setX(drawPane.getWidth() / 2);
        crawfishImage.setY(drawPane.getHeight() / 2);
        drawPane.getChildren().clear();
        drawPane.getChildren().addAll(crawfishImage, pikeImage);

        drawPane.getChildren().clear();

        initializeObserver();
        observerThread = new Thread(observer);
        long duration = Integer.parseInt(textDuration.getText());

        animation = new Transition() {
            double[] oldCoordinates = observer.getWaggonCoordinates();
            double[] newCoordinates;
            double centerX = drawPane.getWidth() / 2;
            double centerY = drawPane.getHeight() / 2;
            List<Line> lines = new ArrayList<>();
            {
                setDelay(Duration.ZERO);
                setCycleDuration(Duration.millis(200));
                setCycleCount((int) (duration * 1000 / 200 + 1)); // Кол-во секунд делить на сайкл дюрейшн
                observerThread.start();
            }

            @Override
            protected void interpolate(double frac) {
                // Спрашиваем координаты, отрисовываем новое положение тележки
                newCoordinates = observer.getWaggonCoordinates();
                //TODO: Не создавать каждый раз объект, когда не надо
//                if (lines.size() != 0 && lines.get(lines.size() - 1).getStartX() == oldCoordinates[0] + centerX &&
//                ){
//                    double prevLineStartX = lines.get(lines.size() - 1).getStartX();
//                    double prevLineStartY = lines.get(lines.size() - 1).getStartY();
//                    double prevLineEndX = lines.get(lines.size() - 1).getEndX();
//                    double prevLineEndY = lines.get(lines.size() - 1).getEndY();
//
//                    if (prevLineEndX)
//                }
                Line line = new Line(oldCoordinates[0] + centerX, oldCoordinates[1] + centerY,
                        newCoordinates[0] + centerX, newCoordinates[1] + centerY);

                drawPane.getChildren().add(line);
                textWaggonX.setText(String.format("%.2f", newCoordinates[0]));
                textWaggonY.setText(String.format("%.2f", newCoordinates[1]));
                oldCoordinates[0] = newCoordinates[0];
                oldCoordinates[1] = newCoordinates[1];
            }
        };

        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                afterAnimationStop();
            }
        });

        animation.play();


        buttonStop.setDisable(false);

        setDisableAll(
                true, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );
    }

    public void onStopButtonAction(ActionEvent actionEvent) {

        if (observerThread.isAlive()) {
            observer.killAll();
            observerThread.interrupt();
            animation.stop();
        }

        afterAnimationStop();
    }

    public void onResetButtonAction(ActionEvent actionEvent) {

        try {
            if (observerThread.isAlive()) {
                observer.killAll();
                observerThread.interrupt();
                animation.stop();
            }
        } catch (NullPointerException e) {}

        drawPane.getChildren().clear();

        buttonStop.setDisable(true);
        setDisableAll(
                false, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );

        setControllersDefault();

    }

    private void setOnFocusLostListeners(){
        textSBottom.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextSBottomAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textSTop.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextSTopAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textSleepBottom.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextSleepBottomAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textSleepTop.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextSleepTopAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textDuration.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextDurationAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textWaggonX.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextWaggonXAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textWaggonY.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                try {
                    onTextWaggonYAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkLeftBoundary(TextField textFieldLeft, TextField textFieldRight) throws IOException {
        try {
            int result = Integer.parseInt(textFieldLeft.getText());
            if (result < 0)
                showErrorStage(textFieldLeft, NEGATIVE_NUMBER_MESSAGE);
            else if (result > Integer.parseInt(textFieldRight.getText()))
                showErrorStage(textFieldLeft, LEFT_BOUND_MESSAGE);

        }
        catch (NumberFormatException e) {
            showErrorStage(textFieldLeft, WRONG_NUMBER_FORMAT_MESSAGE);
        }
    }

    private void checkRightBoundary(TextField textFieldRight, TextField textFieldLeft) throws IOException {
        try {
            int result = Integer.parseInt(textFieldRight.getText());
            if (result < 0)
                showErrorStage(textFieldRight, NEGATIVE_NUMBER_MESSAGE);
            else if (result < Integer.parseInt(textFieldLeft.getText()))
                showErrorStage(textFieldRight, LEFT_BOUND_MESSAGE);

        }
        catch (NumberFormatException e) {
            showErrorStage(textFieldRight, WRONG_NUMBER_FORMAT_MESSAGE);
        }
    }

    private void setDisableAll(boolean state, Control ... controls) {
        for (Control control: controls)
            control.setDisable(state);
    }

    private void initializeObserver() {
        double[] waggonStartCoordinates = new double[2];
        waggonStartCoordinates[0] = Double.parseDouble(textWaggonX.getText());
        waggonStartCoordinates[1] = Double.parseDouble(textWaggonY.getText());
        int swanAngle = (int) Math.round(sliderSwan.getValue());
        int pikeAngle = (int) Math.round(sliderPike.getValue());
        int crawfishAngle = (int) Math.round(sliderCrawfish.getValue());
        int sBottom = Integer.parseInt(textSBottom.getText());
        int sTop = Integer.parseInt(textSTop.getText());
        int sleepBottom = Integer.parseInt(textSleepBottom.getText());
        int sleepTop = Integer.parseInt(textSleepTop.getText());
        long duration = Integer.parseInt(textDuration.getText()) * 1000;

        observer = new Observer(
                waggonStartCoordinates, swanAngle, pikeAngle, crawfishAngle, sBottom, sTop, sleepBottom, sleepTop, duration
        );
    }

    private void afterAnimationStop() {
        double[] waggonCoordinates = observer.getWaggonCoordinates();

        textWaggonX.setText(java.lang.String.format("%.2f", waggonCoordinates[0]));
        textWaggonY.setText(java.lang.String.format("%.2f", waggonCoordinates[1]));

        buttonStop.setDisable(true);
        setDisableAll(
                false, buttonStart, sliderSwan, sliderCrawfish, sliderPike, textSBottom,
                textSTop, textSleepBottom, textSleepTop, textDuration, textWaggonX, textWaggonY
        );
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

    private void showErrorStage(Control control, String message) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Такое значение параметра задать нельзя :(");
        alert.setContentText(message);

        alert.showAndWait();

        control.requestFocus();
    }
}
