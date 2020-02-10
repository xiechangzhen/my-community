package life.showlin.community.mycommunity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyt
 * @date 2019/9/26 16:41:41
 * @description
 */
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPreviousPage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    /**
     *
     * @param totalPage 总页数
     * @param page 当前页数
     * @param size 每页要显示的数量
     */
    public void setPagination(Integer totalPage, Integer page, Integer size) {
        this.page = page;
        pages.add(page);
        for (int i = 1;i < 3; i++){
            if (page - i >0){
                pages.add(0, page - i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //是否展示上一页
        showPreviousPage = page == 1 ?  false : true;
        //是否显示下一页
        showNextPage = page == totalPage ?  false : true;
        //是否显示第一页
        showFirstPage = pages.contains(1) ? false : true;
        //是否显示最后一页
        showEndPage = pages.contains(totalPage) ? false : true;
    }
}
