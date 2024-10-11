package org.capaub.msexternalapi.controller;

import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.capaub.msexternalapi.service.ManageFilesService;
import org.capaub.msexternalapi.service.QrCodeGeneratorService;
import org.capaub.msexternalapi.service.dto.QrCodeDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/externalApi/codeQrManager")
public class CodeQrController {
    private final QrCodeGeneratorService qrCodeGeneratorService;
    private final ManageFilesService manageFilesService;

    @PostMapping("/createQr")
    public String createQr(@RequestBody QrCodeDTO qrCodeDto) throws IOException, WriterException {

        String filepath = "ms-external-api/src/main/resources/static/batchQrcode/" + qrCodeDto.getFilename() + ".txt";

        Map<String, String> data = new HashMap<>();
        data.put("filename", qrCodeDto.getFilename());
        data.put("batch_id", qrCodeDto.getBatchId().toString());
        data.put("instant", qrCodeDto.getNow().toString());

        BufferedImage qrCode = qrCodeGeneratorService.qrCodeGenerator(data);
        manageFilesService.saveQrCodeJson(qrCode, filepath);
        return "SUCCESS: qr code is create and save at " + filepath;

    }
}