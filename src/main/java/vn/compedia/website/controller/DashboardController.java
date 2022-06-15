package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.response.MoneyByDateResponse;
import vn.compedia.website.dto.response.TotalJobByDateResponse;
import vn.compedia.website.dto.response.TotalTransactionByDateResponse;
import vn.compedia.website.dto.response.TotalUserByDateResponse;
import vn.compedia.website.repository.JobRepository;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.repository.UserRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.ValueUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

@Setter
@Getter
@Named
@Scope(value = "session")
@ManagedBean
@ViewScoped
public class DashboardController extends BaseController {

    private Date month;
    private Integer daysInMonth;

    private LineChartModel zoomModel;
    private LineChartModel lineModel1;

    private Integer totalJob;
    private Integer totalUser;
    private Integer totalTransaction;
    private Double totalMoney;

    private List<Integer> listTotal;
    private List<MoneyByDateResponse> listMoney;
    private List<TotalJobByDateResponse> listJob;
    private List<TotalUserByDateResponse> listUser;
    private List<TotalTransactionByDateResponse> listTransaction;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public void initData() throws ParseException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            resetAll();
            initDashBoard(month);
        }
    }

    public Integer getDateOfMonth(Date month) throws ParseException {

        Date m = month;
        Integer monInteger = month.getMonth() + 1;
        Integer yearInteger = month.getYear() + 1900;
        YearMonth yearMonthObject = YearMonth.of(yearInteger, monInteger);
        daysInMonth = yearMonthObject.lengthOfMonth();

        return daysInMonth;
    }

    public void resetAll(){
        month = new Date();
        listTotal = new ArrayList<>();
        totalJob = jobRepository.totalJob();
        totalUser = userRepository.totalUser();
        totalTransaction = transactionRepository.totalTransaction();
        totalMoney = transactionRepository.totalMoney();
        listJob = new ArrayList<>();
        listUser = new ArrayList<>();
        listTransaction = new ArrayList<>();
        listMoney = new ArrayList<>();
    }

    public void initDashBoard(Date month) throws ParseException {
        lineModel1 = initModel(month);

        Axis y = lineModel1.getAxis(AxisType.Y);
        y.setMin(0);
        y.setMax(ValueUtil.isDivisible(Collections.max(listTotal)));
        y.setTickInterval("5");
        lineModel1.setShowPointLabels(true);
        y.setLabel("Số lượng");

        Axis x = lineModel1.getAxis(AxisType.X);
        x.setMin(0);
        x.setMax(daysInMonth + 2);
        x.setTickInterval("2");
        x.setLabel("Ngày trong tháng");

        zoomModel = initModel(month);
        zoomModel.setTitle("Zoom");
        zoomModel.setZoom(true);
        zoomModel.setLegendPosition("e");
        y = zoomModel.getAxis(AxisType.Y);
        y.setMin(0);
        y.setMax("10");

    }

    private LineChartModel initModel(Date month) throws ParseException {
        getDateOfMonth(month);
        LineChartModel lineModel = new LineChartModel();
        listJob = jobRepository.countJobByDate(month.getMonth() + 1, month.getYear() + 1900);
        listUser = userRepository.countUserByDate(month.getMonth() + 1, month.getYear() + 1900);
        listTransaction = transactionRepository.countTransactionByDate(month.getMonth() + 1, month.getYear() + 1900);

        LineChartSeries s = new LineChartSeries();
        LineChartSeries u = new LineChartSeries();
        LineChartSeries t = new LineChartSeries();

        s.setLabel("Số lương dự án");
        u.setLabel("Số lượng người dùng");
        t.setLabel("Số lượng giao dịch");
        lineModel.setLegendPosition("e");

        for (int i = 1; i <= daysInMonth; i++) {
            s.set(i, 0);
            u.set(i, 0);
            t.set(i, 0);
        }
//        List<Integer> listTotal = new ArrayList<>();
        if (!listJob.isEmpty()) {
            listJob.forEach(var -> {
                s.set(var.getDate(), var.getTotal());
                listTotal.add(var.getTotal());
            });
        }
        if (!listUser.isEmpty()) {
            listUser.forEach(var -> {
                u.set(var.getDate(), var.getTotal());
                listTotal.add(var.getTotal());
            });
        }
        if (!listTransaction.isEmpty()) {
            listTransaction.forEach(var -> {
                t.set(var.getDate(), var.getTotal());
                listTotal.add(var.getTotal());
            });
        }

        lineModel.addSeries(s);
        lineModel.addSeries(u);
        lineModel.addSeries(t);

        return lineModel;
    }

    public LineChartModel getLineModel() {
        return lineModel1;
    }

    @Override
    protected String getMenuId() {
        return Constant.DASHBOARD;
    }
}
