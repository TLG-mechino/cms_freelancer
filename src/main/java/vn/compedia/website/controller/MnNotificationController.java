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
import vn.compedia.website.dto.NotificationDto;
import vn.compedia.website.dto.NotificationSearchDto;
import vn.compedia.website.model.Notification;
import vn.compedia.website.repository.NotificationRepository;
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
public class MnNotificationController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnNotificationController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Inject
    private MnUserController userController;

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification notification;
    private String titleDialog;
    private NotificationSearchDto searchDto;
    private NotificationDto notificationDto;
    private LazyDataModel<NotificationDto> lazyDataModel;
    private NotificationSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        notification = new Notification();
        notificationDto = new NotificationDto();
        searchDto = new NotificationSearchDto();
        searchDtoTemp = new NotificationSearchDto();
        onSearch();
    }

    public void resetDialog() {
        notificationDto= new NotificationDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<NotificationDto>() {
            @Override
            public List<NotificationDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return notificationRepository.getAllNotificationRpByUserName(userController.getUserDtoDetails().getId(), searchDto);
            }

            @Override
            public NotificationDto getRowData(String rowKey) {
                List<NotificationDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (NotificationDto obj : requestRewardDtoList) {
                    if (obj.getId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = notificationRepository.countSearchRpByUserName(userController.getUserDtoDetails().getId(), searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(Long notificationId) {
        if (notificationId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        notificationRepository.deleteById(notificationId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_NOTIFICATION;
    }
}
