package com.example.thecommunityapi.controller;

import com.example.thecommunityapi.dto.*;
import com.example.thecommunityapi.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class UtilController {
    @Autowired
    UtilService utilService;

    @GetMapping("/stats")
    public UtilDto getStats(){
        return utilService.getAllStats();
    }

    @PostMapping("/search")
    public ResponseEntity<List<PostResponseDto>> serch(@RequestBody SearchDto searchDto){
        System.out.println("Hello");
        return ResponseEntity.status(HttpStatus.OK).body(utilService.search(searchDto));

    }
}
