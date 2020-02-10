package life.showlin.community.mycommunity.mapper;

import life.showlin.community.mycommunity.dto.QuestionQueryDTO;
import life.showlin.community.mycommunity.model.Question;

import java.util.List;

public interface QuestionExMapper {
    // 增加浏览数
    Integer addViewCount(Question question);

    Integer addCommentCount(Question record);

    List<Question> selectRelated(Question record);

    // 根据搜索条件 查询相关的问题数量
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    // 根据搜索条件 查询分页的列表
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

}