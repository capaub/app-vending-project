package org.capaub.msproduct.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeGeneratorService {

    protected String generateAndSaveQrCode(Map<String, String> data, String filename) throws IOException, WriterException {
        String url = data.get("url");
        String tags;

        if (data.containsKey("batch_id")) {
            tags = data.get("batch_id") + "@" + data.get("batch_id");
            filename = "ms-product/src/main/resources/static/batchQrcode" + File.separator + tags + ".jpg";
        }

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 4);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300, hintMap);

        BufferedImage qrImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();

        Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 300, 300);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        ImageIO.write(qrImage, "JPG", new File(filename));

        return filename;
    }

}
