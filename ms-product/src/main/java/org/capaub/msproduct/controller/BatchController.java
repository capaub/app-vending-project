package org.capaub.msproduct.controller;

import com.google.zxing.WriterException;
import org.capaub.msproduct.service.BatchService;
import org.capaub.msproduct.service.ManageFilesService;
import org.capaub.msproduct.service.QrCodeGeneratorService;
import org.capaub.msproduct.service.dto.QrCodeDto;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class BatchController {
    private final BatchService batchService;
    private final QrCodeGeneratorService qrCodeGeneratorService;
    private final ManageFilesService manageFilesService;

    public BatchController(BatchService batchService, QrCodeGeneratorService qrCodeGeneratorService, ManageFilesService manageFilesService) {
        this.batchService = batchService;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
        this.manageFilesService = manageFilesService;
    }

    @PostMapping("/createQr")
    public String createQr(@RequestBody QrCodeDto qrCodeDto) throws IOException, WriterException {

        String filepath = "ms-product/src/main/resources/static/batchQrcode/" + qrCodeDto.getFilename() + ".txt";

        Map<String, String> data = new HashMap<>();
        data.put("filename", qrCodeDto.getFilename());
        data.put("batch_id", qrCodeDto.getBatchId().toString());
        data.put("instant", qrCodeDto.getNow().toString());

        BufferedImage qrCode = qrCodeGeneratorService.qrCodeGenerator(data);
        manageFilesService.saveQrCodeJson(qrCode, filepath);
        return "SUCCESS: qr code is create and save at " + filepath;

    }

}

