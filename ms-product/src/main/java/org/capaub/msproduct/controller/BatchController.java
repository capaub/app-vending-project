package org.capaub.msproduct.controller;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.BatchService;
import org.capaub.msproduct.service.ManageFilesService;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;
    private final ManageFilesService manageFilesService;


}

