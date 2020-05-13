package imgeditor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageKeeper {

    private Image image;
    private BufferedImage bufImage;
    private int width;
    private int height;
    private int[] imagePixelsInt;
    private byte[] pixels;

    ImageKeeper() {

        image = null;
        bufImage = null;
        setImageSize();
    }

    ImageKeeper(BufferedImage bufImage) {

        this.bufImage = bufImage;
        setImageSize();
        setPixels();
    }

    // SETTERS
    public void setBufImage(BufferedImage bufImage) {
        this.bufImage = bufImage;
        setImageSize();
        setPixels();
        image = convertBufferedImageToImage(bufImage);
        pixels = ((DataBufferByte) bufImage.getRaster().getDataBuffer()).getData();
    }

    private void setImageSize() {
        if (bufImage != null) {
            width = bufImage.getWidth();
            height = bufImage.getHeight();
        } else {
            width = 0;
            height = 0;
        }
    }

    /**
     *  Метод для получения массива пикселей типа int
     */
    private void setPixels() {

        final byte[] pixels = ((DataBufferByte) bufImage.getRaster().getDataBuffer()).getData();
        final int width = bufImage.getWidth();
        final int height = bufImage.getHeight();
        final boolean hasAlphaChannel = bufImage.getAlphaRaster() != null;
        int len = height*width;
        int[] result = new int[len];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // red
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // blue
                result[row * width + col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                byte argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // red
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // blue
                result[row * width + col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        this.imagePixelsInt = result;
    } // setPixels

    public void setImagePixelsInt(int[] pixels) {

        this.imagePixelsInt = pixels;
        this.image = convertIntArrayToImage(pixels);
        this.bufImage = convertIntArrayToBufferedImage(pixels);
    }

    // GETTERS
    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getImagePixelsInt() {
        return imagePixelsInt;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public BufferedImage getBufImage() {
        return bufImage;
    }

    /**
     * Метод возвращает объект класса Image из массива пикселей типа int
     * @param pixels - массив пикселей типа int
     * @return объект класса Image
     */
    public Image convertIntArrayToImage(int[] pixels) {

        WritableImage img = new WritableImage(width, height);
        PixelWriter pw = img.getPixelWriter();
        pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        return img;
    }

    /**
     * Метод возвращает объект класса BufferedImage из массива пикселей типа int
     * @param imagePixelsInt - массив пикселей типа int
     * @return объект класса BufferedImage
     */
    public BufferedImage convertIntArrayToBufferedImage(int[] imagePixelsInt) {
        BufferedImage bufferedImage;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int argb = pixels[i * width + j];
                bufferedImage.setRGB(j, i, argb);
            }
        }

        return bufferedImage;
    }

    /**
     * Метод возвращает объект класса Image из объекта BufferedImage
     * @param bufferedImage - объект класса BufferedImage
     * @return объект класса Image
     */
    public Image convertBufferedImageToImage(BufferedImage bufferedImage) {

        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;
    }

    /**
     * Метод обнуляющий все поля объекта текущего объекта
     */
    public void clear() {
        this.image = null;
        this.bufImage = null;
        this.width = 0;
        this.height = 0;
        this.imagePixelsInt = null;
    }
} // class
