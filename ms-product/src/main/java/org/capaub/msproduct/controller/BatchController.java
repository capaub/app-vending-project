package org.capaub.msproduct.controller;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.BatchService;
import org.capaub.msproduct.service.ManageFilesService;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;
    private final ManageFilesService manageFilesService;

    @PostMapping("/create")
    public BatchDTO createBatch(@RequestBody BatchDTO batchDTO) {
        return batchService.createBatch(batchDTO);
    }
}