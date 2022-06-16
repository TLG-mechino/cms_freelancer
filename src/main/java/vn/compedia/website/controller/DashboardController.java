package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.response.TotalJobByDateResponse;
import vn.compedia.website.dto.response.TotalTransactionByDateResponse;
import vn.compedia.website.dto.response.TotalUserByDateResponse;
import vn.compedia.website.repository.JobRepository;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.repository.UserRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.FacesUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.*;

@Setter
@Getter
@Named
@ManagedBean
@ViewScoped
@Scope(value = "session")
public class DashboardController extends BaseController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private Date month;
    private Integer totalJob;
    private Integer totalUser;
    private Double totalMoney;
    private Integer daysInMonth;
    private Integer totalTransaction;
    private LineChartModel lineModel;

    private List<TotalJobByDateResponse> listJob;
    private List<TotalUserByDateResponse> listUser;
    private List<TotalTransactionByDateResponse> listTransaction;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            resetAll();
            createLineModel(month);
        }
    }

    public void resetAll() {
        month = new Date();

        totalJob = jobRepository.totalJob();
        totalUser = userRepository.totalUser();
        totalMoney = transactionRepository.totalMoney();
        totalTransaction = transactionRepository.totalTransaction();

        listJob = new ArrayList<>();
        listUser = new ArrayList<>();
        listTransaction = new ArrayList<>();
    }

    public List<String> getDayOfMonth(Date date) {
        String dateString = DateUtil.formatDatePattern(date, DateUtil.DDMMYYYY);
        assert dateString != null;
        List<String> dateList = Arrays.asList(dateString.split("/"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateList.get(0)));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateList.get(1)) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(dateList.get(2)));
        int totalDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<String> list = new ArrayList<>(dateList);
        list.add(String.valueOf(totalDay));
        return list;
    }

    public void createLineModel(Date date) {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        List<String> dateList = getDayOfMonth(date);
        listJob = jobRepository.countJobByDate(Integer.parseInt(dateList.get(1)), Integer.parseInt(dateList.get(2)));
        listUser = userRepository.countUserByDate(Integer.parseInt(dateList.get(1)), Integer.parseInt(dateList.get(2)));
        listTransaction = transactionRepository.countTransactionByDate(Integer.parseInt(dateList.get(1)), Integer.parseInt(dateList.get(2)));

        // Line jobs
        LineChartDataSet dataSetJob = new LineChartDataSet();
        dataSetJob.setFill(false);
        dataSetJob.setLabel("Số lượng dự án");
        dataSetJob.setBorderColor("rgb(55, 126, 184)");
        dataSetJob.setLineTension(0.1);
        data.addChartDataSet(dataSetJob);

        // Line users
        LineChartDataSet dataSetUser = new LineChartDataSet();
        dataSetUser.setFill(false);
        dataSetUser.setLabel("Số lượng người dùng");
        dataSetUser.setBorderColor("rgb(228, 26, 28)");
        dataSetUser.setLineTension(0.1);
        data.addChartDataSet(dataSetUser);

        // Line transactions
        LineChartDataSet dataSetTransaction = new LineChartDataSet();
        dataSetTransaction.setFill(false);
        dataSetTransaction.setLabel("Số lượng giao dịch");
        dataSetTransaction.setBorderColor("rgb(77, 175, 74)");
        dataSetTransaction.setLineTension(0.1);
        data.addChartDataSet(dataSetTransaction);

        // Values lines
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(dateList.get(3)); i++) {
            labels.add(i + "");
        }

        List<Object> valuesJob = new ArrayList<>();
        List<Object> valuesUser = new ArrayList<>();
        List<Object> valuesTransaction = new ArrayList<>();

        TotalJobByDateResponse result1;
        TotalUserByDateResponse result2;
        TotalTransactionByDateResponse result3;
        for (String label : labels) {

            result1 = listJob.stream().filter(x -> x.getDate() == Integer.parseInt(label)).findAny().orElse(null);
            if (result1 == null) {
                valuesJob.add(0);
            } else {
                valuesJob.add(result1.getTotal());
            }

            result2 = listUser.stream().filter(x -> x.getDate() == Integer.parseInt(label)).findAny().orElse(null);
            if (result2 == null) {
                valuesUser.add(0);
            } else {
                valuesUser.add(result2.getTotal());
            }

            result3 = listTransaction.stream().filter(x -> x.getDate() == Integer.parseInt(label)).findAny().orElse(null);
            if (result3 == null) {
                valuesTransaction.add(0);
            } else {
                valuesTransaction.add(result3.getTotal());
            }
        }

        // Set label line
        data.setLabels(labels);

        // Set value line
        dataSetJob.setData(valuesJob);
        dataSetUser.setData(valuesUser);
        dataSetTransaction.setData(valuesTransaction);

        // Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Báo cáo thống kê hệ thống theo tháng " + dateList.get(1) + "/" + dateList.get(2));
        title.setFontSize(20);
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);

        FacesUtil.updateView("chart");
    }

    @Override
    protected String getMenuId() {
        return Constant.DASHBOARD;
    }
}
