package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 加载文章列表
     * @param dto
     * @return
     */
    public List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto);

    /**
     * 加载更多文章列表
     * @param dto
     * @return
     */
    public List<ApArticle> loadNewArticleList(@Param("dto") ArticleHomeDto dto);
}
