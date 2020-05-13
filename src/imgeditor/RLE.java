package imgeditor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.util.ArrayList;

public class RLE {

    /**
     * Метод сжатия измененным алгоритмом RLE
     * @param red - красная компонента
     * @param green - зеленая компонента
     * @param blue - синяя компонента
     * @return - сжатый массив типа byte
     */
    public byte[] compression(byte[] red, byte[] green, byte[] blue) {

        byte batchLength;                      // Длина серии
        int tempPixelStorage;                  // Временное хранилище значения пикселя
        int k;
        int arraysLength = red.length;

        int[] pixels = new int[arraysLength];
        byte[] result;
        ArrayList<Integer> tempArrayStorage = new ArrayList<>();  // Временное хранилище RLE массива

        for (int i = 0; i < arraysLength; i++) {
            int rgb = 0;                              //Red Green Blue
            rgb += ((red[i] & 0xff));                      // red
            rgb += ((green[i] & 0xff) << 8);               // green
            rgb += ((blue[i] & 0xff) << 16);               // blue
            pixels[i] = rgb;
        }

        for (int i = 0; i < arraysLength; i++) {

            k = 0;
            batchLength = 0;
            tempPixelStorage = pixels[i];
            for (int j = 0; j < arraysLength - i; j++) {

                if (tempPixelStorage == pixels[j + i]) {
                    batchLength++;
                    k++;
                    if (batchLength == 127) {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (batchLength != 1) {
                i = i + k - 1;
            }

            tempPixelStorage += batchLength << 24;

            tempArrayStorage.add(tempPixelStorage);
        }

        result = new byte[tempArrayStorage.size() * 4];
        for (int i = 0, j = 0; i < tempArrayStorage.size() * 4; i += 4) {
            result[i] = (byte) (tempArrayStorage.get(j) >> 24);       // count
            result[i + 1] = (byte) (tempArrayStorage.get(j) >> 16);   // blue
            result[i + 2] = (byte) (tempArrayStorage.get(j) >> 8);    // green
            result[i + 3] = (byte)(tempArrayStorage.get(j) >> 0);     // red
            j++;
        }

        return result;
    } // compression

    /**
     * Метод разжатия измененным алгоритмом RLE
     * @param rle - сжатые данные
     * @return - объект класса BufferedImage
     */
    public BufferedImage decompression(byte[] rle) {

        byte batchLength;                    // Длина серии
        int  fullLength = 0;                 // Длина расжатого массива
        int t = 0;
        int z = 0;
        int rleLength = rle.length;
        int length = rleLength / 4;
        byte[] count = new byte[length];
        byte[] red = new byte[length];
        byte[] green = new byte[length];
        byte[] blue = new byte[length];

        // TODO: 13.06.2019 java.lang.ArrayIndexOutOfBoundsException: 50655 :  count[j] = rle[i];
        for (int i = 0, j = 0; i < rleLength; i+=4) {
            count[j] = rle[i];                   // count
            blue[j] = rle[i + 1];                // green
            green[j] = rle[i + 2];               // blue
            red[j] = rle[i + 3];                 // red
            j++;
        }

        for (int i = 0; i < length; i++) {
            fullLength += count[i];
        }

        byte[] redResult = new byte[fullLength];
        byte[] greenResult = new byte[fullLength];
        byte[] blueResult = new byte[fullLength];

        for (int i = 0; i < length; i++) {

            batchLength = count[i];
            for (int j = z; j < z + batchLength; j++) {

                redResult[j] = red[i];
                greenResult[j] = green[i];
                blueResult[j] = blue[i];
                t++;
            }

            z += t;
            t = 0;
        }

        final int pixelLength = 3;
        byte[] pixels = new byte[redResult.length * pixelLength];

        for (int pixel = 0, i = 0; pixel < redResult.length * pixelLength; pixel += pixelLength) {
            pixels[pixel] = blueResult[i];          // blue
            pixels[pixel + 1] = greenResult[i];     // green
            pixels[pixel + 2] = redResult[i];       // red
            i++;
        }

        BufferedImage img;

        int width = (int)Math.sqrt(pixels.length / 3);
        int height = width;
        img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(pixels, pixels.length), new Point()));

        return img;
    } // decompression
} // class
