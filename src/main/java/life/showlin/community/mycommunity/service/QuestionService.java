package life.showlin.community.mycommunity.service;

import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.model.Question;

import java.util.List;

/**
 * @author yyt
 * @date 2019/9/26 13:54:32
 * @description
 */
public interface QuestionService {

    void createOrUpdate(Question question);

    PaginationDTO list(String search, Integer page ,Integer size);

    PaginationDTO<QuestionDTO> list(Long creator, Integer page, Integer size);

    /**
     * 根据提问id得到一条提问
     * @param id 提问的id
     * @return 提问
     */
    QuestionDTO findById(Long id);

    void addVieCount(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDto);
}
