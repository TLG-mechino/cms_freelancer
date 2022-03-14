package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.MarketDto;
import vn.compedia.website.dto.MarketSearchDto;
import vn.compedia.website.model.Market;
import vn.compedia.website.repository.MarketRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.StringUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Named
@Scope(value = "session")
public class MnMarketController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnMarketController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private MarketRepository marketRepository;

    private Market market;
    private String titleDialog;
    private MarketSearchDto searchDto;
    private MarketDto marketDto;
    private LazyDataModel<MarketDto> lazyDataModel;
    private MarketSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        market = new Market();
        marketDto = new MarketDto();
        searchDto = new MarketSearchDto();
        searchDtoTemp = new MarketSearchDto();
        onSearch();
    }

    public void resetDialog() {
        market = new Market();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<MarketDto>() {
            @Override
            public List<MarketDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return marketRepository.search(searchDto);
            }

            @Override
            public MarketDto getRowData(String rowKey) {
                List<MarketDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (MarketDto obj : requestRewardDtoList) {
                    if (obj.getMarketId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = marketRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public boolean validateDate() {
        if (StringUtils.isBlank(market.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã thị trường");
            return false;
        }

        long idCheck;
        if (null == market.getMarketId()) {
            idCheck = 0L;
        } else {
            idCheck = market.getMarketId();
        }

        List<Market> checkCodeExists = marketRepository.findByCodeExists(market.getCode(), idCheck);
        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
            FacesUtil.addErrorMessage("Mã thị trường đã tồn tại");
            return false;
        }

        if (StringUtils.isBlank(market.getName().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên thị trường");
            return false;
        }

        if (market.getPrefix() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập đầu số thị trường");
            return false;
        }

        if (market.getPrefix() < 1 || market.getPrefix() > 9999) {
            FacesUtil.addErrorMessage("Đầu số thị trường phải nằm trong khoảng từ 1 - 9999");
            return false;
        }

        List<Market> checkPrefixExists = marketRepository.findByPrefixExists(market.getPrefix(), idCheck);
        if (CollectionUtils.isNotEmpty(checkPrefixExists)) {
            FacesUtil.addErrorMessage("Đầu số thị trường đã tồn tại");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (market.getMarketId() == null) {
            market.setCreateDate(new Date());
            market.setCreateBy(authorizationController.getAccountDto().getAccountId());
        }
        market.setUpdateDate(new Date());
        market.setUpdateBy(authorizationController.getAccountDto().getAccountId());

        marketRepository.save(market);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(Market object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        market = new Market();
        BeanUtils.copyProperties(object, market);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long marketId) {
        if (marketId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        marketRepository.deleteById(marketId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_MARKET;
    }
}
