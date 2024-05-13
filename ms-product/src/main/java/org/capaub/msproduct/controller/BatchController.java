package org.capaub.msproduct.controller;

import com.google.zxing.WriterException;
import org.capaub.msproduct.service.BatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/createQr/{batch_id}")
    public String getQr(@PathVariable String batch_id) throws IOException, WriterException {
        Map<String,String> data = new HashMap<>();
        data.put("url","localhost");
        data.put("filename","test");
        data.put("batch_id",batch_id);
        return batchService.getQrcode(data);
    }
}
