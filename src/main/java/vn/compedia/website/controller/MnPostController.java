package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;

import vn.compedia.website.dto.PostDto;
import vn.compedia.website.dto.PostSearchDto;
import vn.compedia.website.model.Post;

import vn.compedia.website.repository.PostRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnPostController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnPostController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Inject
    private MnUserController userController;

    @Autowired
    private PostRepository postRepository;

    private Post post;
    private String titleDialog;
    private PostSearchDto searchDto;
    private PostDto postDto;
    private LazyDataModel<PostDto> lazyDataModel;
    private PostSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        post = new Post();
        postDto = new PostDto();
        searchDto = new PostSearchDto();
        searchDtoTemp = new PostSearchDto();
        onSearch();
    }

    public void resetDialog() {
        post = new Post();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<PostDto>() {
            @Override
            public List<PostDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
                searchDto.setPageIndex(first);
                searchDto.setPageSize(pageSize);
                searchDto.setSortField(sortField);
                String sort = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sort = "ASC";
                } else {
                    sort = "DESC";
                }
                searchDto.setSortOrder(sort);
                BeanUtils.copyProperties(searchDto, searchDtoTemp);
                return postRepository.getAllPostByUserName(userController.getUserDtoDetails().getId(), searchDto);
            }

            @Override
            public PostDto getRowData(String rowKey) {
                List<PostDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (PostDto obj : requestRewardDtoList) {
                    if (obj.getId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = postRepository.countSearchRpByUserName(userController.getUserDtoDetails().getId(), searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(Long postId) {
        if (postId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        postRepository.deleteById(postId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_POST;
    }
}
