package imgeditor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;

public class ImageActions {

    /**
     * Метод для загрузки изображения из памяти компьютера
     * @param stage - окно на котором откроется диалог для выбора файла
     * @return объект класса BufferedImage
     */
    BufferedImage openImage(Stage stage) {
        FileChooserHelper fileChooserHelper = new FileChooserHelper();
        FileChooser fileChooser = fileChooserHelper.getFileChooser();
        File selectImage = fileChooser.showOpenDialog(stage);
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(selectImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufImage;
    } // openImage

    /**
     * Метод для поворота изображения по часовой стрелке
     * @param pixels - массвив пикселей типа int
     * @param imageWidth - ширина изображения
     * @param imageHeight - высота изображения
     * @return измененный массив писелей типа int
     */
    int[] rotateImageRight(int[] pixels, int imageWidth, int imageHeight) {

        int numberOfPixels = pixels.length;
        int[] resultArray = new int[numberOfPixels];
        int resultArrayColumn, pixelsColumn = 0, i = 0, t = 0;

        while (t < imageHeight) {

            resultArrayColumn = 0;
            for (int row = (imageWidth - 1); row >= 0; row--) {
                resultArray[i * imageWidth + resultArrayColumn] = pixels[row * imageWidth + pixelsColumn];
                resultArrayColumn++;
            }
            pixelsColumn++;
            i++;
            t++;
        }

        return resultArray;
    } // rotateImageRight

    /**
     * Метод для поворота изображения против часовой стрелки
     * @param pixels - массвив пикселей типа int
     * @param imageWidth - ширина изображения
     * @param imageHeight - высота изображения
     * @return измененный массив писелей типа int
     */
    int[] rotateImageLeft(int[] pixels, int imageWidth, int imageHeight) {

        int numberOfPixels = pixels.length;
        int[] resultArray = new int[numberOfPixels];

        for (int i = 0, row = (imageWidth-1); i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                resultArray[i * imageWidth + j] = pixels[j * imageWidth + row];
            }
            row = row - 1;
        }
        return resultArray;
    } // rotateImageLeft

    /**
     * Метод для получения зеркальной копии изображения по вертикали
     * @param pixels - массвив пикселей типа int
     * @param imageWidth - ширина изображения
     * @param imageHeight - высота изображения
     * @return измененный массив писелей типа int
     */
    int[] verticalMirror(int[] pixels, int imageWidth, int imageHeight) {
        int[] resultArray = new int[pixels.length];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                resultArray[i * imageWidth + j] = pixels[i * imageWidth + (imageWidth - 1 - j)];
            }
        }
        return resultArray;
    } // verticalMirror

    /**
     * Метод для получения зеркальной копии изображения по горизонтали
     * @param pixels - массвив пикселей типа int
     * @param imageWidth - ширина изображения
     * @param imageHeight - высота изображения
     * @return измененный массив писелей типа int
     */
    int[] horizontalMirror(int[] pixels, int imageWidth, int imageHeight) {
        int[] resultArray = new int[pixels.length];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                resultArray[i * imageWidth + j] = pixels[(imageHeight-1-i) * imageWidth + j];
            }
        }
        return resultArray;
    } // horizontalMirror

    /**
     * Метод для сохранения изображения в память компьютера
     * @param image - объект класса Image
     * @param outputFile - объект класса File
     * @param format - переменная типа String хранящая название формата
     * @throws IOException
     */
    void save(Image image, File outputFile, String format) throws IOException {

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, outputFile);
    } // save
} // class
