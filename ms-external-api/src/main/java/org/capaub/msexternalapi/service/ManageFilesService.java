package org.capaub.msexternalapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

@Service
public class ManageFilesService {

    public void saveQrCodeImage(BufferedImage image, String formatName, String filepath) throws IOException {
        ImageIO.write(image,formatName,new File(filepath));
    }

    public void saveQrCodeJson(BufferedImage image, String filepath) throws IOException {

        String base64Image = encodeImageToBase64(image);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("qrcode",base64Image);

        String jsonString = objectMapper.writeValueAsString(json);
        saveJsonToFile(jsonString,filepath);
    }

    private static String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private static void saveJsonToFile(String jsonString, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
        }
    }
}
