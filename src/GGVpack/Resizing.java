package GGVpack;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Resizing {

    public ImageIcon resizingImg(String path, int newWitdh, int newHeight) {

        ImageIcon originalImgData = new ImageIcon(path);
        Image resizedImage = originalImgData.getImage();
        resizedImage = resizedImage.getScaledInstance(newWitdh, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizeImgData = new ImageIcon(resizedImage);
        return resizeImgData;
    }
}
