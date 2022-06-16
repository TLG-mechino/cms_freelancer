$(document).ready(function () {
    let pathCurrent = location.pathname;
    let classActive = 'active-menuitem active';
    let closeable = false;
    let isFE = location.pathname.indexOf('frontend') > -1;

    let menuClass = '.layout-menu li';

    $(menuClass).each(function (i, e) {
        let $this = $(e);
        let pathElement = $this.find('a').attr('href');
        let parentElement = $this.parent('ul').parent('li');
        let isSub = parentElement.length > 0;

        if (pathCurrent.indexOf(pathElement) > -1) {
            // set active current
            if (!isSub) {
                $this.addClass(classActive);
            }

            // set active menu parent

            parentElement.addClass(classActive);

            // set close in BE
            if (i === 0) closeable = true;
        } else {
            $this.removeClass(classActive);
            if (closeable && !isFE) {
                $this.find('ul').css('display', 'none');
            }
        }
    });
});

function showMessageCommon(type_warn, text_warn, title, message) {
    PrimeFaces.cw("Growl", "growl", {
        id: "growl",
        sticky: false,
        life: 5000,
        escape: true,
        keepAlive: false,
        msgs: [{
            summary: title,
            detail: message,
            severity: type_warn,
            severityText: text_warn
        }]
    });
}

function showErrorMessage(title, message) {
    showMessageCommon('error', 'Error', title, message);
}

function showSuccessMessage(title, message) {
    showMessageCommon('info', 'Information', title, message);
}

PrimeFaces.locales['vi'] = {
    closeText: 'Tắt',
    prevText: 'Tháng trước',
    nextText: 'Tháng sau',
    monthNames: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    monthNamesShort: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    dayNames: ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'],
    dayNamesShort: ['CN', 'Hai', 'Ba', 'Tư', 'Năm', 'Sáu', 'Bảy'],
    dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
    weekHeader: 'Tuần',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Chọn giờ',
    timeText: 'Giờ',
    hourText: 'Giờ',
    minuteText: 'Phút',
    secondText: 'Giây',
    currentText: 'Giờ hiện hành',
    ampm: false,
    month: 'Tháng',
    week: 'Tuần',
    day: 'Ngày',
    allDayText: 'Cả ngày'
};
