package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {
    @Autowired
    private PostSearchMapper productSearchMapper;
    @Override
    public List<PostDTO> getProduct(PostDTO productDTO, CategoryDTO categoryDTO) {
        List<PostDTO> postDTOList = productSearchMapper.selectPosts(productDTO,categoryDTO);
        return postDTOList;
    }
}
