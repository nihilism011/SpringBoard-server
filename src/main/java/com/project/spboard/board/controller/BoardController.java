package com.project.spboard.board.controller;

import com.project.spboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    final private BoardService boardService;
}
