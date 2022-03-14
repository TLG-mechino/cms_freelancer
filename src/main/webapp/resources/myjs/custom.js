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
    PrimeFaces.cw("Growl","growl", {
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
    timeOnlyTitle: 'Chọn thời gian',
    timeText: 'Thời gian',
    hourText: 'Giờ:',
    minuteText: 'Phút:',
};
