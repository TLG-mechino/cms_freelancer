package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseSearchDto {
    protected String sortField;
    protected String sortOrder;
    protected int pageIndex;
    protected int pageSize;
    protected Date startTime;
    protected Date endTime;
    protected String keyword;
    protected Long roleId;
    protected String startDate;
    protected String endDate;
}