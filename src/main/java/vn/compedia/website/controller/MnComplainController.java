package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.ComplainDto;
import vn.compedia.website.dto.ComplainSearchDto;
import vn.compedia.website.model.ComplainType;
import vn.compedia.website.repository.ComplainRepository;
import vn.compedia.website.repository.ComplainTypeRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnComplainController extends BaseController {

    @Autowired
    private ComplainRepository complainRepository;
    @Autowired
    private ComplainTypeRepository complainTypeRepository;

    private ComplainSearchDto searchDto;
    private ComplainDto dto;
    private LazyDataModel<ComplainDto> lazyDataModel;
    private List<ComplainType> complainTypes;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        searchDto = new ComplainSearchDto();
        dto = new ComplainDto();
        complainTypes = complainTypeRepository.findAllByStatus(DbConstant.ACTIVE_STATUS);
        onSearch();
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<ComplainDto>() {
            @Override
            public List<ComplainDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                searchDto.setPageIndex(first);
                searchDto.setPageSize(pageSize);
                searchDto.setSortField(sortField);

                String sortOption = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sortOption = "ASC";
                } else {
                    sortOption = "DESC";
                }

                searchDto.setSortOrder(sortOption);
                return complainRepository.search(searchDto);
            }

            @Override
            public ComplainDto getRowData(String rowKey) {
                List<ComplainDto> list = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (ComplainDto obj : list) {
                    if (obj.getComplainId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = complainRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_COMPLAIN;
    }
}