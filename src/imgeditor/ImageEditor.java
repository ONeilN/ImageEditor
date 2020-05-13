package imgeditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #3c3f41");
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #2b2b2b");
        Scene mainScene = new Scene(root, 800, 650);
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-text-fill: white");
        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: #2b2b2b");

        ImageKeeper imageKeeper = new ImageKeeper();
        ImageActions imageActions = new ImageActions();
        ImageViewHelper imageViewHelper = new ImageViewHelper();
        FileChooserHelper fileChooserHelper = new FileChooserHelper();
        WaveletAndRLECompress waveletAndRLECompress = new WaveletAndRLECompress();


        Menu fileMenu = new Menu("Файл");
        Menu actionsMenu = new Menu("Действия");
        Menu helpMenu = new Menu("Помощь");
        Menu decompressMenu = new Menu("Разжать");

        // Для "Файл"
        MenuItem openImageMenuItem = new MenuItem("Открыть");
        MenuItem saveImageMenuItem = new MenuItem("Сохранить");
        MenuItem exitMenuItem = new MenuItem("Выход");

        // Для "Действия"
        MenuItem compressImageMenuItem = new MenuItem("Сжать");
        MenuItem zipDecompress = new MenuItem("ZIP");
        MenuItem pngDecompress = new MenuItem("PNG");
        MenuItem rotateImageRightMenuItem = new MenuItem("Повернуть на право");
        MenuItem rotateImageLeftMenuItem = new MenuItem("Повернуть на лево");
        MenuItem verticalMirrorMenuItem = new MenuItem("Отзеркалить по вертикали");
        MenuItem horizontalMirrorMenuItem = new MenuItem("Отзеркалить по горизонтали");

        // Для "Помощь"
        MenuItem documentationMenuItem = new MenuItem("Документация");

        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        saveImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        openImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        File fileOpenImage = new File("./src/icons/buttons/openimage.png");
        Image iconOpenImageButton = new Image(fileOpenImage.toURI().toString());
        Button buttonOpenImage = new Button("", new ImageView(iconOpenImageButton));
        buttonOpenImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileCompressImage = new File("./src/icons/buttons/compress.png");
        Image iconCompressImageButton = new Image(fileCompressImage.toURI().toString());
        Button buttonCompressImage = new Button("", new ImageView(iconCompressImageButton));
        buttonCompressImage.setStyle(" -fx-background-color: \n" +
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

        File fileSaveImage = new File("./src/icons/buttons/saveimage.png");
        Image iconSaveImageButton = new Image(fileSaveImage.toURI().toString());
        Button buttonSaveImage = new Button("", new ImageView(iconSaveImageButton));
        buttonSaveImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileRotateImage = new File("./src/icons/buttons/rotate.png");
        Image iconRotateImageButton = new Image(fileRotateImage.toURI().toString());
        Button buttonRotateImage = new Button("", new ImageView(iconRotateImageButton));
        buttonRotateImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileVerticalMirror = new File("./src/icons/buttons/MirrorVertical.png");
        Image iconVerticalMirrorButton = new Image(fileVerticalMirror.toURI().toString());
        Button buttonVerticalMirror = new Button("", new ImageView(iconVerticalMirrorButton));
        buttonVerticalMirror.setStyle(" -fx-background-color: \n" +
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

        File fileHorizontalMirror = new File("./src/icons/buttons/MirrorHorizontal.png");
        Image iconHorizontalMirrorButton = new Image(fileHorizontalMirror.toURI().toString());
        Button buttonHorizontalMirror = new Button("", new ImageView(iconHorizontalMirrorButton));
        buttonHorizontalMirror.setStyle(" -fx-background-color: \n" +
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

        File fileClearButton = new File("./src/icons/buttons/clear.png");
        Image iconClearButton = new Image(fileClearButton.toURI().toString());
        Button buttonClear = new Button("", new ImageView(iconClearButton));
        buttonClear.setStyle(" -fx-background-color: \n" +
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

        File fileExitButton = new File("./src/icons/buttons/exit.png");
        Image iconExitButton = new Image(fileExitButton.toURI().toString());
        Button buttonExit = new Button("", new ImageView(iconExitButton));
        buttonExit.setLayoutY(265);
        buttonExit.setStyle(" -fx-background-color: \n" +
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


        // MENU ITEMS ACTIONS

        openImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // openImageMenuItem

        compressImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CompressWindow compress = new CompressWindow();
                if (imageKeeper.getImage() != null) {
                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());

                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                imageKeeper.clear();
            } // handle
        }); // compressImageMenuItem

        zipDecompress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getBufImage() != null) {
                    imageKeeper.clear();
                    imageViewHelper.getImgView().setImage(null);
                }

                FileChooserHelper zipFileChooser = new FileChooserHelper(FileChooserHelper.Type.ZIP_COMPRESS);
                File inputFile = zipFileChooser.getFileChooser().showOpenDialog(primaryStage);

                BufferedImage bufferedImage;
                bufferedImage = waveletAndRLECompress.reverseTransformPNG(2,
                                                                WaveletAndRLECompress.TypeOfCompression.ZIP, inputFile);

                imageKeeper.setBufImage(bufferedImage);
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // decompressImageMenuItem

        pngDecompress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getBufImage() != null) {
                    imageKeeper.clear();
                    imageViewHelper.getImgView().setImage(null);
                }

                FileChooserHelper zipFileChooser = new FileChooserHelper(FileChooserHelper.Type.PNG_COMPRESS);
                File inputFile = zipFileChooser.getFileChooser().showOpenDialog(primaryStage);

                BufferedImage bufferedImage;
                bufferedImage = waveletAndRLECompress.reverseTransformPNG(2,
                        WaveletAndRLECompress.TypeOfCompression.PNG, inputFile);

                imageKeeper.setBufImage(bufferedImage);
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // decompressImageMenuItem

        saveImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {

                    File selectImage = fileChooserHelper.getFileChooser().showSaveDialog(primaryStage);

                    try {
                        imageActions.save(imageKeeper.getImage(), selectImage, "png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // if else
            } // handle
        }); // saveImageMenuItem

        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (imageKeeper.getImage() != null) {
                    ExitWindow exit = new ExitWindow();
                    try {
                        exit.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.exit(0);
                }
            } // handle
        }); // exitMenuItem

        rotateImageRightMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.rotateImageRight(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // rotateImageRightMenuItem

        rotateImageLeftMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.rotateImageLeft(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // rotateImageLeftMenuItem

        verticalMirrorMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.verticalMirror(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // verticalMirrorMenuItem

        horizontalMirrorMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.horizontalMirror(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // horizontalMirrorMenuItem

        documentationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File("./Documentation.docx"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } // try catch
            } // handle
        }); // documentationMenuItem


        /**
         *   BUTTONS ACTIONS
         */

        buttonOpenImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // buttonOpenImage

        buttonCompressImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CompressWindow compress = new CompressWindow();
                if (imageKeeper.getImage() != null) {
                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());

                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                imageKeeper.clear();
            } // handle
        }); // buttonCompressImage

        buttonSaveImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {

                    File selectImage = fileChooserHelper.getFileChooser().showSaveDialog(primaryStage);

                    try {
                        imageActions.save(imageKeeper.getImage(), selectImage, "png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // if else
            } // handle
        }); // buttonSaveImage

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() != null) {
                    ExitWindow exit = new ExitWindow();
                    try {
                        exit.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.exit(0);
                }
            } // handle
        }); // buttonExit

        buttonRotateImage.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.rotateImageRight(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // buttonRotateImage

        // Событие для кнопки "Отзеркалить по вертикали"
        buttonVerticalMirror.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.verticalMirror(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // buttonVerticalMirror

        // Событие для кнопки "Отзеркалить по горизонтали"
        buttonHorizontalMirror.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {
                    int imgWidth = imageKeeper.getWidth();
                    int imgHeight = imageKeeper.getHeight();
                    int[] pixels;

                    pixels = imageActions.horizontalMirror(imageKeeper.getImagePixelsInt(), imgWidth, imgHeight);
                    imageKeeper.setImagePixelsInt(pixels);

                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                } // if else
            } // handle
        }); // buttonHorizontalMirror

        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                imageKeeper.clear();
                imageViewHelper.getImgView().setImage(null);
            } // handle
        }); // buttonClear



        // Добавление элементов на MenuBar
        fileMenu.getItems().addAll(openImageMenuItem, saveImageMenuItem, exitMenuItem);
        actionsMenu.getItems().addAll(compressImageMenuItem, decompressMenu, rotateImageRightMenuItem,
                                    rotateImageLeftMenuItem, verticalMirrorMenuItem, horizontalMirrorMenuItem);
        decompressMenu.getItems().addAll(zipDecompress, pngDecompress);
        helpMenu.getItems().add(documentationMenuItem);
        menuBar.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
        root.setTop(menuBar);


        // Добавление кнопок на ToolBar
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().add(buttonOpenImage);
        toolBar.getItems().add(buttonCompressImage);
        toolBar.getItems().add(buttonSaveImage);
        toolBar.getItems().add(buttonRotateImage);
        toolBar.getItems().add(buttonVerticalMirror);
        toolBar.getItems().add(buttonHorizontalMirror);
        toolBar.getItems().add(buttonClear);
        pane.getChildren().add(buttonExit);
        toolBar.getItems().add(pane);
        root.setLeft(toolBar);


        primaryStage.setTitle("Image Editor");
        primaryStage.getIcons().add(new Image("icons/mainIcon.png"));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    } // void start - end
} // class ImageEditor - end
