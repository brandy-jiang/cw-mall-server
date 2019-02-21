package com.chenwang.mall.common.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class QRCodeUtils {
    @Value("${qr.code.path}")
    String projectPath;
    private static final Logger LOG = LogManager.getLogger(QRCodeUtils.class);
//    private static String projectPath = null;
//    static {
//        try {
//            projectPath = QRCodeUtils.class.getClassLoader().getResource(".").getPath();
//        }catch (Exception e){
//            LOG.info(" resource path not found . so switch to system path");
//            //projectPath = PropertiesFileUtils.getValue("application", "linux.project.root");
//        }
//    }

    public void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = Paths.get(new File(projectPath + filePath).getPath());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public byte[] loadImage(String path){
        InputStream in = null;
        try {
            byte[] b = new byte[1024];
            in = new FileInputStream(new File(projectPath + "images/" + path));
            int i =  in.read(b);
            if(i >= 0)
                return b;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

}