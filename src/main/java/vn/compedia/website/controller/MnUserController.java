package vn.compedia.website.controller;

import lombok.Getter;
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
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.repository.UserRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Named
@Scope(value = "session")
public class MnUserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnUserController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private UserRepository userRepository;


    private String titleDialog;
    private LazyDataModel<UserDto>lazyDataModel;
    private UserSearchDto searchUserDto;
    private UserSearchDto searchUserTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        searchUserDto = new UserSearchDto();
        searchUserTemp = new UserSearchDto();
        onSearch();

    }

    @Override
    protected String getMenuId() {
        return Constant.MN_USER;
    }



    public void onSearch(){
        lazyDataModel = new LazyDataModel<UserDto>() {
            @Override
            public List<UserDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                searchUserDto.setPageIndex(first);
                searchUserDto.setPageSize(pageSize);
                searchUserDto.setSortField(sortField);
                String sort = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sort = "ASC";
                } else {
                    sort = "DESC";
                }
                searchUserDto.setSortOrder(sort);
                BeanUtils.copyProperties(searchUserDto, searchUserTemp);
                return userRepository.search(searchUserDto);
            }

            @Override
            public UserDto getRowData(String rowKey) {
                List<UserDto>dtos = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for(UserDto userDto : dtos){
                    if(userDto.getAccountId().equals(value)){
                        return userDto;
                    }
                }
                return null;
            }
        };
        int count = userRepository.countSearch(searchUserDto,DbConstant.ACCOUNT_USER);
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchFrom");
    }

    public void onEdit(UserDto resultDto) {
    }


    public void onDelete(UserDto resultDto){

    }
}
