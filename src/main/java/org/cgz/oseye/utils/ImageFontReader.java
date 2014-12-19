package org.cgz.oseye.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 读取字体文件
 */
public class ImageFontReader {
    
    private String imageFont;

    public String getImageFont() {
        return imageFont;
    }
    
    private static class ImageFontReaderHolder {
        private static ImageFontReader instance = new ImageFontReader();
    }
    private ImageFontReader() {
        readFontFile();
    }
    
    public static ImageFontReader getInstance() {
        return ImageFontReaderHolder.instance;
    }
    
    private void readFontFile() {
        Properties properties = new Properties();
        InputStream in = null;
        in = ImageFontReader.class.getResourceAsStream("TFFFont.properties");
        try {
            properties.load(in);
            imageFont = properties.getProperty("imageFont");
        } catch (IOException ex) {
            Logger.getLogger(ImageFontReader.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ImageFontReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void main(String args[]) {
        System.out.print(ImageFontReader.getInstance().getImageFont());
    }
}
