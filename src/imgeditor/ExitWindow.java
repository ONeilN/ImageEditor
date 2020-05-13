package imgeditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExitWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage exitStage = new Stage();
        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        Pane pane = new Pane();
        Scene exitScene = new Scene(root, 300, 100);


        Label informationLabel = new Label("Вы уверены, что хотите выйти?");

        Button exitButton = new Button("Выйти");
        Button cancelButton = new Button("Отмена");


        hBox.setLayoutX(185);
        hBox.setLayoutY(-5);
        hBox.setSpacing(5);
        root.setStyle("-fx-background-color: #3c3f41;");
        informationLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        exitButton.setStyle(" -fx-background-color: \n" +
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
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");
        cancelButton.setStyle(" -fx-background-color: \n" +
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
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitStage.close();
            }
        });

        hBox.getChildren().add(exitButton);
        hBox.getChildren().add(cancelButton);

        pane.getChildren().add(hBox);

        root.setCenter(informationLabel);
        root.setBottom(pane);

        exitStage.setTitle("Подтверждение выхода");
        exitStage.getIcons().add(new Image("././icons/exitIcon.png"));
        exitStage.setScene(exitScene);
        exitStage.show();

    } // start
} // class
