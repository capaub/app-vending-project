package org.capaub.mswebapp.service;

import org.capaub.mswebapp.service.dto.GoodsDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServerService {
    private static final String DIR_PRODUCTS_IMG = "uploads/products/";
    private static final String PREFIX_BATCH_IMG = "vendo_";

    public String saveImage(GoodsDTO goodsDTO) throws IOException {

        // URL de l'image à télécharger
        URL url = new URL(goodsDTO.getImgUrl());

        // Nom de fichier basé sur l'ID du produit
        String filename = PREFIX_BATCH_IMG + goodsDTO.getBarcode() + ".jpg";
        Path dirPath = Paths.get(DIR_PRODUCTS_IMG);
        Path filePath = dirPath.resolve(filename);

        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Ouvre un stream pour lire le contenu de l'image depuis l'URL
        try (InputStream in = url.openStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Retourne le chemin du fichier enregistré
        return filePath.toString();
    }
}
