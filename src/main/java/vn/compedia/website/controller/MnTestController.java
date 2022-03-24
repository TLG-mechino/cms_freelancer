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
import vn.compedia.website.dto.ExamDto;
import vn.compedia.website.dto.ExamSearchDto;
import vn.compedia.website.dto.UploadWithFilenameDto;
import vn.compedia.website.model.Exam;
import vn.compedia.website.model.ExamFile;
import vn.compedia.website.model.ExamType;
import vn.compedia.website.repository.ExamFileRepository;
import vn.compedia.website.repository.ExamTypeRepository;
import vn.compedia.website.repository.TestRepository;
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
public class MnTestController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MnTestController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ExamTypeRepository examTypeRepository;
    @Inject
    private UploadMultipleImageWithFileNameController uploadMultipleImageFileNameController;
    @Inject
    private UploadSingleImageController uploadSingleImageController;
    @Autowired
    private ExamFileRepository examFileRepository;

    private Exam exam;
    private String titleDialog;
    private ExamSearchDto searchDto;
    private ExamDto examDto;
    private LazyDataModel<ExamDto> lazyDataModel;
    private ExamSearchDto searchDtoTemp;
    private List<ExamType> examTypeList;
    private List<ExamFile> listExamFile;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        exam = new Exam();
        examDto = new ExamDto();
        searchDto = new ExamSearchDto();
        searchDtoTemp = new ExamSearchDto();
        listExamFile = new ArrayList<>();
        examTypeList = (List<ExamType>) examTypeRepository.findAll();
        uploadSingleImageController.resetAll(null);
        uploadMultipleImageFileNameController.resetAll(null);
        onSearch();
    }

    public void resetDialog() {
        exam = new Exam();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<ExamDto>() {
            @Override
            public List<ExamDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return testRepository.search(searchDto);
            }

            @Override
            public ExamDto getRowData(String rowKey) {
                List<ExamDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (ExamDto obj : requestRewardDtoList) {
                    if (obj.getExamId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = testRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    private ExamFile copyValue(UploadWithFilenameDto uploadWithFilenameDto, ExamFile examFile, Exam exam) {
        examFile.setExamFileId(uploadWithFilenameDto.getId());
        examFile.setObjectId(exam.getExamId());
        examFile.setFileName(uploadWithFilenameDto.getFileName());
        examFile.setFilePath(uploadWithFilenameDto.getFilePath());
        examFile.setFileType(FilenameUtils.getExtension(uploadWithFilenameDto.getFileName()));
        examFile.setType(1);
        return examFile;
    }

    private UploadWithFilenameDto copyValue(ExamFile examFile, UploadWithFilenameDto uploadWithFilenameDto) {
        uploadWithFilenameDto.setId(examFile.getExamFileId());
        uploadWithFilenameDto.setFileName(examFile.getFileName());
        uploadWithFilenameDto.setFilePath(examFile.getFilePath());
        return uploadWithFilenameDto;
    }

    public boolean validateDate() {
        if (StringUtils.isBlank(examDto.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã hashtag");
            return false;
        }

        long idCheck;
        if (null == examDto.getExamId()) {
            idCheck = 0L;
        } else {
            idCheck = examDto.getExamId();
        }

        List<Exam> checkCodeExists = testRepository.findByCodeExists(examDto.getCode(), idCheck);
        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
            FacesUtil.addErrorMessage("Mã bài test đã tồn tại");
            return false;
        }

        if (StringUtils.isBlank(examDto.getTitleEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tiêu đề tiếng anh ");
            return false;
        }

        if (StringUtils.isBlank(examDto.getTitleVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tiêu đề tiếng việt ");
            return false;
        }
        if (StringUtils.isBlank(examDto.getDescriptionVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng việt ");
            return false;
        }
        if (StringUtils.isBlank(examDto.getDescriptionEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng anh ");
            return false;
        }
        if (StringUtils.isBlank(examDto.getContentVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập nội dung tiếng việt ");
            return false;
        }
        if (StringUtils.isBlank(examDto.getContentEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập nội dung tiếng anh ");
            return false;
        }
        if (CollectionUtils.isEmpty(uploadMultipleImageFileNameController.getUploadMultipleFileDto().getListToShow())) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn hình ảnh bài test");
            FacesUtil.updateView("growl");
            return false;
        }
        if (examDto.getScore() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập điểm ");
            return false;
        }
        return true;
    }

    private void onSaveExamTest(Exam exam) {
        if (CollectionUtils.isNotEmpty(uploadMultipleImageFileNameController.getUploadMultipleFileDto().getListToDelete())) {
            uploadMultipleImageFileNameController.getUploadMultipleFileDto().getListToDelete().forEach(var -> {
                examFileRepository.deleteById(var.getId());
            });
        }
        uploadMultipleImageFileNameController.getUploadMultipleFileDto().getListToShow().forEach(var -> {
            listExamFile.add(copyValue(var, new ExamFile(), exam));
        });

        examFileRepository.saveAll(listExamFile);
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (examDto.getHashtagId() == null) {
            examDto.setCreateDate(new Date());
            examDto.setCreateBy(authorizationController.getAccountDto().getUsername());
        }
        examDto.setUpdateDate(new Date());
        examDto.setUpdateBy(authorizationController.getAccountDto().getUsername());
        BeanUtils.copyProperties(examDto, exam);

        testRepository.save(exam);
        onSaveExamTest(exam);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(ExamDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        examDto = new ExamDto();
        List<ExamFile> list = examFileRepository.findAllByExamId(object.getExamId());
        List<UploadWithFilenameDto> uploadMutipleImage = new ArrayList<>();

        list.forEach(var -> {
            if (var.getType() == 1) {
                uploadMutipleImage.add(copyValue(var, new UploadWithFilenameDto()));
            }
        });
        uploadMultipleImageFileNameController.getUploadMultipleFileDto().setListToShow(uploadMutipleImage);
        BeanUtils.copyProperties(object, examDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long examId) {
        if (examId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        List<ExamFile> list = examFileRepository.findAllByExamId(examId);
        List<UploadWithFilenameDto> uploadMutipleImage = new ArrayList<>();

        list.forEach(var -> {
            if (var.getType() == 1) {
                uploadMutipleImage.add(copyValue(var, new UploadWithFilenameDto()));
            }
        });
        testRepository.deleteById(examId);
        uploadMultipleImageFileNameController.onRemoveImageList(uploadMutipleImage);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_TEST;
    }
}
