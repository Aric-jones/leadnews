package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 加载首页文章，加载更多
     * @param dto
     * @return
     */
    ResponseResult load(ArticleHomeDto dto, short type);
}
