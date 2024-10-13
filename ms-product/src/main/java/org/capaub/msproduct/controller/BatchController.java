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

    @PostMapping("/create/{companyId}")
    public BatchDTO createBatch(@RequestBody BatchDTO batchDTO, @PathVariable Integer companyId) {
        return batchService.createBatch(batchDTO, companyId);
    }
}