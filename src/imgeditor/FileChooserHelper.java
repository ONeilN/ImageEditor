package imgeditor;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserHelper {
    private FileChooser fileChooser;
    enum Type { ZIP_COMPRESS, PNG_COMPRESS, NO_COMPRESS}

    FileChooserHelper() {
        fileChooser = new FileChooser();
        configuringFileChooser(fileChooser, Type.NO_COMPRESS);
    }

    FileChooserHelper(Type type) {
        fileChooser = new FileChooser();
        configuringFileChooser(fileChooser, type);
    }

    void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    FileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Настройка объекта класса FileChooser
     * @param fileChooser - объект класса FileChooser
     * @param type - Тип сохраняемого/загружаемого файла
     */
    static void configuringFileChooser(FileChooser fileChooser, Type type) {

        if (type == Type.ZIP_COMPRESS) {
            //Set title for FileChooser
            fileChooser.setTitle("Save ZIP");
            //Set Initial Directory For ZIP
            fileChooser.setInitialDirectory(new File("./Pictures/zip"));
            //Add Extension Filters For ZIP
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("ZIP", "*.zip"));
        } else if (type == Type.PNG_COMPRESS) {
            //Set title for FileChooser
            fileChooser.setTitle("Save PNG");
            //Set Initial Directory For PNG
            fileChooser.setInitialDirectory(new File("./Pictures/png"));
            //Add Extension Filters
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
        } else if (type == Type.NO_COMPRESS) {
            //Set title for FileChooser
            fileChooser.setTitle("Select Picture");
            //Set Initial Directory
            fileChooser.setInitialDirectory(new File("./Pictures"));
            //Add Extension Filters
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"));
        }

    } // configuringFileChooser
} // class
