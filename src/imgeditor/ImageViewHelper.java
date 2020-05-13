package imgeditor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewHelper {

    private ImageView imgView;

    ImageViewHelper() {

        imgView = null;
    }

    void setImageView(Image img) {
        imgView = new ImageView(img);
    }

    ImageView getImgView() {
        return imgView;
    }
}
