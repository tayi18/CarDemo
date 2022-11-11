(function (para) {
    var p = para.sdk_url, n = para.name, w = window, d = document, s = 'script', x = null, y = null;
    if (typeof (w['sensorsDataAnalytic201505']) !== 'undefined') {
        return false;
    }
    w['sensorsDataAnalytic201505'] = n;
    w[n] = w[n] || function (a) { return function () { (w[n]._q = w[n]._q || []).push([a, arguments]); } };
    var ifs = ['track', 'quick', 'register', 'registerPage', 'registerOnce', 'trackSignup', 'trackAbtest', 'setProfile', 'setOnceProfile', 'appendProfile', 'incrementProfile', 'deleteProfile', 'unsetProfile', 'identify', 'login', 'logout', 'trackLink', 'clearAllRegister', 'getAppStatus'];
    for (var i = 0; i < ifs.length; i++) {
        w[n][ifs[i]] = w[n].call(null, ifs[i]);
    }
    if (!w[n]._t) {
        x = d.createElement(s), y = d.getElementsByTagName(s)[0];
        x.async = 0;
        x.src = p;
        x.setAttribute('charset', 'UTF-8');
        y.parentNode.insertBefore(x, y);
        w[n].para = para;
    }
})({
    sdk_url: 'https://front.1hai.cn/npm/sensorsdata@1.12.3/sensorsdata.min.js',
    heatmap_url: 'https://front.1hai.cn/npm/sensorsdata@1.12.3/heatmap.min.js',
    name: 'ehiSensors',
    web_url: 'https://sensors.1hai.cn:9443/?project=production',
    server_url: 'https://sensors.1hai.cn:11621/sa?project=production',
    heatmap: {}
});
ehiSensors.registerPage({
    app_name: '一嗨租车PC'
});
ehiSensors.quick('autoTrack');
var ehiId = "2240512074";
if (ehiId.length > 0) {
    ehiSensors.setProfile({ vip_no: ehiId, fname:"付朝红"});
    ehiSensors.login(ehiId);
}

function PromotionCode(type) {
    var requestPara = {
        pageIndex: 1,
        pageSize: 10
    };
    if (type === "Chauffeur") {
        $("#Chauffeur").attr("class", "ck");
        $("#Code").removeAttr("class");
        $.post(URL.ChauffeurPromotionCodeList,
            requestPara,
            function (data) {

                $('#divPromotions').html(data);

            });
    } else {
        $("#Code").attr("class", "ck");
        $("#Chauffeur").removeAttr("class");
        $.post(URL.PromotionCodeList,
            requestPara,
            function (data) {

                $('#divPromotions').html(data);

            });
    }
}
$(function () {
    //绑定优惠券
    $('#btnBind').click(function () {
        var promotionCode = $('#txtPromotionCode').val();
        if (!promotionCode) {
            $('#txtPromotionCode').focus();
            alert('请输入优惠编码');
            return false;
        }
        var requestPara = {
            code: promotionCode
        };
        $.post(URL.BindCode, requestPara, function (data) {
            if (!data.Success) {
                $('#txtPromotionCode').focus();
                alert(data.Message);
                return false;
            } else {
                $('#txtPromotionCode').val("");
                alert(data.Message);
                $('#divPromotions').load(URL.PromotionCodeList);
            }

        });
    });

    //分页查询优惠券
    $("#divPromotions").on("click", ".pages a", function () {
        var param = {
            pageIndex: $(this).data("page-index")
        };
        $("#divPromotions").load(window.URL.PromotionCodeList, param, function (data) {
            $("#divPromotions").html(data);
        });
        //查看优惠券规则
    }).on("click", ".btn-show-rules a", function () {
        var $rules = $(this).siblings("script");
        $(".alert-rulebox-rules").html($rules.html());
    });
});