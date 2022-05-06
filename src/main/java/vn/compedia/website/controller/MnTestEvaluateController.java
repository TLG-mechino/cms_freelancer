package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.controller.common.UploadMultipleImageWithFileNameController;
import vn.compedia.website.controller.common.UploadSingleImageController;
import vn.compedia.website.dto.*;
import vn.compedia.website.model.Exam;
import vn.compedia.website.model.ExamFile;
import vn.compedia.website.model.ExamType;
import vn.compedia.website.model.UserExam;
import vn.compedia.website.repository.*;
import vn.compedia.website.service.NotificationSystemService;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnTestEvaluateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MnTestEvaluateController.class);

    @Inject
    private AuthorizationController authorizationController;
    @Inject
    private UploadMultipleImageWithFileNameController uploadMultipleImageFileNameController;

    @Autowired
    private TestEvaluateRepository testEvaluateRepository;
    @Autowired
    private ExamFileRepository examFileRepository;
    @Autowired
    private UserExamRepository userExamRepository;
    @Autowired
    private NotificationSystemService notificationSystemService;

    private UserExam userExam;
    private String titleDialog;
    private UserExamSearchDto searchDto;
    private UserExamDto userExamDto;
    private LazyDataModel<UserExamDto> lazyDataModel;
    private UserExamSearchDto searchDtoTemp;
    private UserExamDto userExamDtoDetail;
    private List<ExamFile> examFileList;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        userExam = new UserExam();
        userExamDto = new UserExamDto();
        searchDto = new UserExamSearchDto();
        searchDtoTemp = new UserExamSearchDto();
        onSearch();
    }

    public void resetDialog() {
        userExamDto = new UserExamDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<UserExamDto>() {
            @Override
            public List<UserExamDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return testEvaluateRepository.search(searchDto);
            }

            @Override
            public UserExamDto getRowData(String rowKey) {
                List<UserExamDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (UserExamDto obj : requestRewardDtoList) {
                    if (obj.getExamId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = testEvaluateRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    private UploadWithFilenameDto copyValue(ExamFile examFile, UploadWithFilenameDto uploadWithFilenameDto) {
        uploadWithFilenameDto.setId(examFile.getExamFileId());
        uploadWithFilenameDto.setFileName(examFile.getFileName());
        uploadWithFilenameDto.setFilePath(examFile.getFilePath());
        return uploadWithFilenameDto;
    }

    public void onEdit(UserExamDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        userExamDto = new UserExamDto();
        List<ExamFile> list = examFileRepository.findAllByUserExamId(object.getUserExamId());
        List<UploadWithFilenameDto> uploadMutipleImage = new ArrayList<>();

        list.forEach(var -> {
            if (var.getType() == 1) {
                uploadMutipleImage.add(copyValue(var, new UploadWithFilenameDto()));
            }
        });
        uploadMultipleImageFileNameController.getUploadMultipleFileDto().setListToShow(uploadMutipleImage);
        BeanUtils.copyProperties(object, userExamDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long userExamId) {
        if (userExamId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        List<ExamFile> list = examFileRepository.findAllByUserExamId(userExamId);
        List<UploadWithFilenameDto> uploadMutipleImage = new ArrayList<>();
        list.forEach(var -> {
            if (var.getType() == 1) {
                uploadMutipleImage.add(copyValue(var, new UploadWithFilenameDto()));
            }
        });
        testEvaluateRepository.deleteById(userExamId);
        uploadMultipleImageFileNameController.onRemoveImageList(uploadMutipleImage);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void findByIdAndExamCode(Long userExamId){
        userExamDtoDetail = testEvaluateRepository.findByIdAndExamCode(userExamId);
        examFileList = examFileRepository.findAllByUserExamId(userExamId);
        FacesUtil.redirect("/evaluate-details.xhtml");
    }

    public void onEditEvaluateDetails(UserExamDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        userExam = userExamRepository.findById(object.getUserExamId()).get();
        if(StringUtils.isBlank(userExamDtoDetail.getNote())){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập ghi chú");
            FacesUtil.updateView("growl");
            return;
        }
        if(userExamDtoDetail.getScore() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số điểm đánh giá");
            FacesUtil.updateView("growl");
            return;
        }
        userExam.setScore(object.getScore());
        userExam.setNote(object.getNote());
        userExam.setStatus(DbConstant.COMPLETE_EVALUATE_SCORE);
        userExam.setSubmitTime(object.getSubmitTime());
        userExamRepository.save(userExam);
        titleDialog = "Sửa";
        FacesUtil.addSuccessMessage("Đánh giá bài làm thành công");
        FacesUtil.updateView("growl");
        FacesUtil.redirect("/evaluate-test.xhtml");

        //add notification
        List<String> usernameList = new ArrayList<>();
        usernameList.add(userExam.getUsername());
        notificationSystemService.saveNotification(authorizationController.getAccountDto().getUsername(),"Điểm bài thi",
                "Bài thi của bạn đã được chấm điểm", 8, userExam.getUserExamId(), usernameList);

    }

    @Override
    protected String getMenuId() {
        return Constant.MN_TEST_EVALUATE;
    }
}


