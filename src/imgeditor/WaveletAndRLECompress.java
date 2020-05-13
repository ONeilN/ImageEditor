package imgeditor;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class WaveletAndRLECompress {

    WaveletTransform waveletTransform = new WaveletTransform();
    enum TypeOfCompression {

        PNG,
        ZIP
    }

    /**
     * Функция для масштабирования значения цветового компонента.
     * @param fromMin
     * @param fromMax
     * @param toMin
     * @param toMax
     * @param x - значений цветового компанента
     * @return масштабированное значние
     */
    public double Scale(double fromMin, double fromMax, double toMin, double toMax, double x) {

        if (fromMax - fromMin == 0) return 0;
        double value = (toMax - toMin) * (x - fromMin) / (fromMax - fromMin) + toMin;
        if (value > toMax) {
            value = toMax;
        }
        if (value < toMin) {
            value = toMin;
        }
        return value;
    } // Scale

    /**
     * Сохранение данных в памяти компьютера в формате zip
     * @param data - данные типа byte
     * @param outputFile - объект класса File
     */
    private void GZIPSave(byte[] data, File outputFile) {

        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream(outputFile);
            GZIPOutputStream gzipOutputStream1 = new GZIPOutputStream(fileOutputStream1);
            gzipOutputStream1.write(data);
            gzipOutputStream1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // GZIPSave

    /**
     * Сохранение данных в памяти компьютера в формате png
     * @param bufferedImage - объект класса BufferedImage
     * @param outputFile - объект класса File
     */
    private void PNGSave(BufferedImage bufferedImage, File outputFile) {

        try {
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // PNGSave

    /**
     * Разработанный алгоритм сжатия изображений
     * @param iterations - количество итераций вейвлет-преобразования
     * @param bufferedImage - объект класса BufferedImage
     * @param compressionRatio - значение для обнуления
     * @param type - формат в котором будут сохранены данные
     * @param outputFile - объект класса File
     */
    public void forwardTransform(int iterations, BufferedImage bufferedImage, double compressionRatio, TypeOfCompression type, File outputFile) {

        int width = bufferedImage.getWidth();    // Ширина изображения
        int height = bufferedImage.getHeight();  // Высота изображения

        Color c;            // Переменная для хранения цвета пикселя

        double[][] Red = new double[width][height];
        double[][] Green = new double[width][height];
        double[][] Blue = new double[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                c = new Color(bufferedImage.getRGB(i, j));
                Red[i][j] = Scale(0, 255, -1, 1, c.getRed());
                Green[i][j] = Scale(0, 255, -1, 1, c.getGreen());
                Blue[i][j] = Scale(0, 255, -1, 1, c.getBlue());
            }
        }

        waveletTransform.FWT(Red, iterations, width, height);
        waveletTransform.FWT(Green, iterations, width, height);
        waveletTransform.FWT(Blue, iterations, width, height);

        if (compressionRatio > 0.015 || compressionRatio < 0) {

            compressionRatio = 0.015;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Red[i][j] <= compressionRatio && Red[i][j] >= -compressionRatio) {
                    Red[i][j] = 0;
                }
                if (Green[i][j] <= compressionRatio && Green[i][j] >= -compressionRatio) {
                    Green[i][j] = 0;
                }
                if (Blue[i][j] <= compressionRatio && Blue[i][j] >= -compressionRatio) {
                    Blue[i][j] = 0;
                }
            }
        }

        if (type == TypeOfCompression.PNG) {

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    c = new Color((int) Scale(-1, 1, 0, 255, Red[i][j]),
                            (int) Scale(-1, 1, 0, 255, Green[i][j]),
                            (int) Scale(-1, 1, 0, 255, Blue[i][j]));
                    bufferedImage.setRGB(i, j, c.getRGB());
                }
            }

            PNGSave(bufferedImage, outputFile);
        } else if (type == TypeOfCompression.ZIP) {

            RLE rle = new RLE();

            byte[] redRLE = new byte[width * height];
            byte[] greenRLE = new byte[width * height];
            byte[] blueRLE = new byte[width * height];

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    redRLE[j * width + i] = (byte) Scale(-1, 1, 0, 255, Red[i][j]);
                    greenRLE[j * width + i] = (byte) Scale(-1, 1, 0, 255, Green[i][j]);
                    blueRLE[j * width + i] = (byte) Scale(-1, 1, 0, 255, Blue[i][j]);
                }
            }

            byte[] resultRLE = rle.compression(redRLE, greenRLE, blueRLE);
            GZIPSave(resultRLE, outputFile);
        }
    } // forwardTransform

    /**
     * Метод разжатия разработанного алгоритма сжатия
     * @param iterations - количество итераций вейвлет-преобразования
     * @param type - формат в котором из которого загружаются данные
     * @param inputFile - объект класса File
     */
    public BufferedImage reverseTransformPNG(int iterations, TypeOfCompression type, File inputFile) {

        BufferedImage bufferedImage = null;

        if (type == TypeOfCompression.PNG) {
            bufferedImage = openPNG(inputFile);
        } else if (type == TypeOfCompression.ZIP) {
            bufferedImage = openGZIP(inputFile);
        }

        int width = bufferedImage.getWidth();    // Ширина изображения
        int height = bufferedImage.getHeight();  // Высота изображения

        Color c;            // Переменная для хранения цвета пикселя

        double[][] Red = new double[width][height];
        double[][] Green = new double[width][height];
        double[][] Blue = new double[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                c = new Color(bufferedImage.getRGB(i, j));
                Red[i][j] = Scale(0, 255, -1, 1, c.getRed());
                Green[i][j] = Scale(0, 255, -1, 1, c.getGreen());
                Blue[i][j] = Scale(0, 255, -1, 1, c.getBlue());
            }
        }

        waveletTransform.IWT(Red, iterations, width, height);
        waveletTransform.IWT(Green, iterations, width, height);
        waveletTransform.IWT(Blue, iterations, width, height);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                c = new Color((int) Scale(-1, 1, 0, 255, Red[i][j]), (int) Scale(-1, 1, 0, 255, Green[i][j]), (int) Scale(-1, 1, 0, 255, Blue[i][j]));
                bufferedImage.setRGB(i, j, c.getRGB());
            }
        }

        return bufferedImage;
    } // reverseTransformPNG

    /**
     * Метод для загрузки данных из формата png
     * @param inputFile - объект класса File
     * @return - объект класса BufferedImage
     */
    private BufferedImage openPNG(File inputFile) {

        BufferedImage bufImg = null;
        try {
            bufImg = ImageIO.read(inputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return bufImg;
    } // openPNG

    /**
     * Метод для загрузки данных из формата zip
     * @param inputFile - объект класса File
     * @return - объект класса BufferedImage
     */
    private BufferedImage openGZIP(File inputFile) {

        int i = 0;
        byte[] beforeRLEReverse;
        ArrayList<Byte> data = new ArrayList<>();
        RLE rle = new RLE();
        BufferedImage bufferedImage;

        GZIPInputStream gzipInputStream = null;
        try {
            InputStream fileInputStream = new FileInputStream(inputFile);
            gzipInputStream = new GZIPInputStream(fileInputStream);
            data.add((byte)gzipInputStream.read());
            while(data.get(i) != -1){
                data.add((byte)gzipInputStream.read());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                gzipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        data.remove(data.size() - 1);
        beforeRLEReverse = new byte[data.size()];

        for (int j = 0; j < data.size(); j++) {
            beforeRLEReverse[j] = data.get(j);
        }

        bufferedImage = rle.decompression(beforeRLEReverse);

        return bufferedImage;
    } // openGZIP

} // class
