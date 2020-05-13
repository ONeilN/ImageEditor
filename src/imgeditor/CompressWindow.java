package imgeditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

public class CompressWindow extends Application {

    BufferedImage bufferedImage = null;

    public void startWindow(Stage stage, BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage exitStage = new Stage();
        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        Pane pane = new Pane();
        VBox vBox = new VBox();
        Scene exitScene = new Scene(root, 235, 100);
        WaveletAndRLECompress waveletAndRLECompress = new WaveletAndRLECompress();

        Button zipButton = new Button("ZIP");
        Button pngButton = new Button("PNG");

        hBox.setSpacing(30);
        Label label = new Label("Степень сжатия");

        Slider slider = new Slider(0.0, 0.015, 0);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(0.001);
        slider.setMajorTickUnit(0.0075);
        slider.setMinorTickCount(4);
        slider.setSnapToTicks(true);

        root.setStyle("-fx-background-color: #3c3f41;");
        label.setStyle("-fx-text-fill: white;\n" +
                " -fx-font-weight: bold;\n" +
                " -fx-font-size: 17px;\n" +
                " -fx-padding: 2 45 2 45;");
        slider.setStyle("-fx-text-fill: white;\n" +
                " -fx-font-size: 12px;\n" +
                " -fx-padding: 0 10 0 10;\n");
        zipButton.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                " -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                " -fx-font-size: 18px;\n" +
                " -fx-padding: 5 35 5 35;\n" +
                " -fx-font-weight: bold;");
        pngButton.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                " -fx-font-size: 18px;\n" +
                " -fx-padding: 5 35 5 35;\n" +
                " -fx-font-weight: bold;");


        zipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooserHelper zipFileChooser = new FileChooserHelper(FileChooserHelper.Type.ZIP_COMPRESS);
                File outputFile = zipFileChooser.getFileChooser().showSaveDialog(primaryStage);

                waveletAndRLECompress.forwardTransform(2, bufferedImage, slider.getValue(), WaveletAndRLECompress.TypeOfCompression.ZIP, outputFile);
                exitStage.close();
            }
        });

        pngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooserHelper pngFileChooser = new FileChooserHelper(FileChooserHelper.Type.PNG_COMPRESS);
                File outputFile = pngFileChooser.getFileChooser().showSaveDialog(primaryStage);

                waveletAndRLECompress.forwardTransform(2, bufferedImage, slider.getValue(), WaveletAndRLECompress.TypeOfCompression.PNG, outputFile);
                exitStage.close();
            }
        });

        vBox.getChildren().add(label);
        vBox.getChildren().add(slider);

        hBox.getChildren().add(zipButton);
        hBox.getChildren().add(pngButton);

        pane.getChildren().add(hBox);

        root.setBottom(pane);
        root.setCenter(vBox);

        exitStage.setTitle("Сжатие");
        exitStage.getIcons().add(new Image("././icons/buttons/changesize.png"));
        exitStage.setScene(exitScene);
        exitStage.show();

    } // start
} //class
