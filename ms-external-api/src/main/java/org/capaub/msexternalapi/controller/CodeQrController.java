package org.capaub.msexternalapi.controller;

import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.capaub.msexternalapi.service.ManageFilesService;
import org.capaub.msexternalapi.service.QrCodeGeneratorService;
import org.capaub.msexternalapi.service.dto.BatchQrDTO;
import org.capaub.msexternalapi.service.dto.VendingQrDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/externalApi/codeQrManager")
public class CodeQrController {
    private final QrCodeGeneratorService qrCodeGeneratorService;
    private final ManageFilesService manageFilesService;

    @PostMapping("/createBatchQr")
    public String createBatchQr(@RequestBody BatchQrDTO batchQrDTO) throws IOException, WriterException {

        String filepath = "ms-external-api/src/main/resources/static/batchQrcode/" + batchQrDTO.getFilename() + ".png";
        File file = new File(filepath);

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        Map<String, String> data = new HashMap<>();
        data.put("filename", batchQrDTO.getFilename());
        data.put("batch_id", batchQrDTO.getBatchId().toString());
        data.put("company_id", batchQrDTO.getCompanyId().toString());
        data.put("instant", batchQrDTO.getNow().toString());

        BufferedImage qrCode = qrCodeGeneratorService.qrCodeGenerator(data);
        manageFilesService.saveQrCodeImage(qrCode,"png", filepath);
        return "SUCCESS: qr code is create and save at " + filepath;

    }
    @PostMapping("/createVendingQr")
    public String createVendingQr(@RequestBody VendingQrDTO vendingQrDTO) throws IOException, WriterException {

        String filepath = "ms-external-api/src/main/resources/static/vendingQrcode/" + vendingQrDTO.getFilename() + ".png";
        File file = new File(filepath);

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        Map<String, String> data = new HashMap<>();
        data.put("filename", vendingQrDTO.getFilename());
        data.put("vending_id", vendingQrDTO.getVendingId().toString());
        data.put("customer_id", vendingQrDTO.getCustomerId().toString());
        data.put("company_id", vendingQrDTO.getCompanyId().toString());
        data.put("instant", vendingQrDTO.getNow().toString());

        BufferedImage qrCode = qrCodeGeneratorService.qrCodeGenerator(data);
        manageFilesService.saveQrCodeImage(qrCode,"png", filepath);
        return "SUCCESS: qr code is create and save at " + filepath;

    }
}