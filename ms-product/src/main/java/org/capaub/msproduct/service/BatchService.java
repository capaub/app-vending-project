package org.capaub.msproduct.service;

import com.google.zxing.WriterException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class BatchService {
    private final ResourceLoader resourceLoader;
    private final QrCodeGeneratorService qrCodeGeneratorService;
    public BatchService(ResourceLoader resourceLoader, QrCodeGeneratorService qrCodeGeneratorService) {
        this.resourceLoader = resourceLoader;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    public String getQrcode(Map<String, String> data) throws IOException, WriterException {
        // Obtenir une référence au dossier "resources"
        File resourcesDirectory = resourceLoader.getResource("classpath:").getFile();

        // Créer un chemin relatif pour le fichier
        String filePath = resourcesDirectory.getAbsolutePath() + File.separator + data.get("filename");

        return qrCodeGeneratorService.generateAndSaveQrCode(data, filePath);
    }

}
