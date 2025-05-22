package org.functions.domain.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class BarcodeUtil {

    private BarcodeUtil(){}

    private static class InstanceHolder {
        public static BarcodeUtil getInstance = new BarcodeUtil();
    }

    public static BarcodeUtil getInstance(){
        return InstanceHolder.getInstance;
    }

    public String gerarImagemBase64(String code) throws Exception {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(code, BarcodeFormat.CODE_128, 400, 150);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);

        byte [] imageBytes = outputStream.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
