package com.project.spboard.board.service;

import com.project.spboard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    final private BoardRepository boardRepository;
    
}
