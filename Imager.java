package dev.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Default
 * 22 July 2015
 * Description:
 * The beast, which managed to get through many
 * innovations in tools, managing work with
 * images, mostly BufferedImages. Proud of it.
 */

public class Imager {

    public static BufferedImage getMerged(BufferedImage image, BufferedImage overlay){

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();

        g.drawImage(image, 0, 0, width, height, null);
        g.drawImage(overlay, 0, 0, width, height, null);

        return result;

    }


    public static BufferedImage getMerged(java.util.List<BufferedImage> layers){

        int width = layers.get(0).getWidth();
        int height = layers.get(0).getHeight();


        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();

        for (BufferedImage layer : layers){

            g.drawImage(layer, 0, 0, width, height, null);

        }

        return result;

    }


    public static BufferedImage zoom(BufferedImage image, int coefficient){

        BufferedImage result = new BufferedImage(image.getWidth() * coefficient, image.getHeight() * coefficient, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();

        for (int y = 0; y < image.getHeight(); y++) {

            for (int x = 0; x < image.getWidth(); x++) {
                if (((image.getRGB(x, y) >> 24) &0xFF) != 0) {
                    g.setColor(new Color(image.getRGB(x, y)));
                    g.fillRect(x * coefficient, y * coefficient, coefficient, coefficient);
                }
            }

        }

        return result;

    }


    public static BufferedImage getMirrored(BufferedImage image){

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();

        for (int y = 0; y < height; y++) {

            for (int x = width - 1; x >= 0 ; x--) {

                if (image.getAlphaRaster().getDataBuffer().getElem(y * width + width - x - 1) != 0){
                    g.setColor(new Color(image.getRGB(width - x - 1, y)));
                    g.fillRect(x, y, 1, 1);
                }

            }

        }

        return result;

    }


    public static BufferedImage getScaledImage(BufferedImage image, int width, int height){
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
    }

}

