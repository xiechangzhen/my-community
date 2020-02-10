package life.showlin.community.mycommunity.service.impl;

import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.dto.QuestionQueryDTO;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.mapper.QuestionExMapper;
import life.showlin.community.mycommunity.mapper.QuestionMapper;
import life.showlin.community.mycommunity.mapper.TuserMapper;
import life.showlin.community.mycommunity.model.Question;
import life.showlin.community.mycommunity.model.QuestionExample;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yyt
 * @date 2019/9/26 13:54:55
 * @description
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExMapper questionExMapper;
    @Autowired
    private TuserMapper userMapper;

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);

            Integer rows = questionMapper.insert(question);
            if (rows != 1){
                throw new CustomizeException(CustomizeCodeImpl.QUESTION_INSERT_FAILU);
            }
        } else {
            // 更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int rows = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (rows != 1){
                throw new CustomizeException(CustomizeCodeImpl.QUESTION_UPDATE_FAILU);
            }
        }
    }

    @Override
    public PaginationDTO list(String search, Integer page ,Integer size) {
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search," ");
            String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);


        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        //根据creator得到问题总条数
        Integer totalNum = questionExMapper.countBySearch(questionQueryDTO);
        Integer totalPage = totalNum%size >0 ? totalNum/size + 1 : totalNum/size;
        paginationDTO.setTotalPage(totalPage);

        //防止页码超出范围
        page = page > totalPage ? totalPage : page;
        page = page < 1 ? 1 : page;
        //分页查询
        Integer offset = (page - 1) * size;

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");

//        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questionList = questionExMapper.selectBySearch(questionQueryDTO);
        //因为需要User的头像，所以要把user放入返回集合中
        if (questionList != null && questionList.size() > 0){
            for (Question question:questionList){
                Tuser user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                //把question的全部属性值赋值到questionDTO
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            //封装分页信息 questionList有值时才有分页
            paginationDTO.setPagination(totalPage,page,size);
        }
        //把questionDTOS放入分页返回信息中
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    @Override
    public PaginationDTO<QuestionDTO> list( Long creator, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        //根据creator得到问题总条数
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        questionExample.createCriteria().andCreatorEqualTo(creator);
        Integer totalNum = Integer.valueOf(questionMapper.countByExample(questionExample)+"");
        Integer totalPage = totalNum%size >0 ? totalNum/size + 1 : totalNum/size;
        paginationDTO.setTotalPage(totalPage);

        //防止页码超出范围
        page = page > totalPage ? totalPage : page;
        page = page < 1 ? 1 : page;
        //分页查询
        Integer offset = (page - 1) * size;

        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        //因为需要User的头像，所以要把user放入返回集合中
        if (questionList != null && questionList.size() > 0){
            for (Question question:questionList){

                Tuser user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                //把question的全部属性值赋值到questionDTO
                BeanUtils.copyProperties(question, questionDTO);
                BeanUtils.copyProperties(question, questionDTO);

                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            //封装分页信息 questionList有值时才有分页
            paginationDTO.setPagination(totalPage,page,size);
        }
        //把questionDTOS放入分页返回信息中
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    @Override
    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeCodeImpl.QUESTION_NOT_FOUND);
        }
        //根据提问id查找用户
        QuestionDTO questionDTO = new QuestionDTO();
        Tuser user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void addVieCount(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        questionExMapper.addViewCount(question);
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDto) {
        if (StringUtils.isBlank(questionDto.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDto.getTag(),",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexTag);
        List<Question> related = questionExMapper.selectRelated(question);
        List<QuestionDTO> collect = related.stream().map(m -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(m, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return collect;
    }
}

