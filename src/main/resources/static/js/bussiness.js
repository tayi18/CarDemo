
//地图区域样式切换
/*$(".new-area-box").click(function (event){
    var element = event.target;
    console.log(element);
    if (element == null)
    $(".area").each(function (){
        this.className = "";
    });
    element.className = "current area";
});*/

//点击左侧切换

var content1 = '<div class="info-title">寒山寺点</div><div class="info-content">' +
    '地址：苏州市姑苏区和园路197号102室<br/>' +
    '营业时间：8:00-20:00 <br/>' +
    '门店电话：18013116796 <br/>' +
    '</div>';
var infowindow1 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content1,
    position:[120.571608,31.307677],
    offset: new AMap.Pixel(16, -45)
});

var content2 = '<div class="info-title">东兴路便捷点</div><div class="info-content">' +
    '地址：苏州市工业园区东兴路118号栖庭花园商业地下停车场<br/>' +
    '营业时间：8:00-20:00 <br/>' +
    '门店电话：18120071550 <br/>' +
    '</div>';
var infowindow2 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content2,
    position:[120.535042,31.235405],
    offset: new AMap.Pixel(16, -45)
});
var content3 = '<div class="info-title">水上乐园送车点</div><div class="info-content">' +
    '地址：玉山路166号<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：17751434314 <br/>' +
    '</div>';
var infowindow3 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content3,
    position:[120.567445,31.260476],
    offset: new AMap.Pixel(16, -45)
});
var content4 = '<div class="info-title">留园送车点</div><div class="info-content">' +
    '地址：姑苏区留园路289号（姑苏人家）<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：18013116796 <br/>' +
    '</div>';
var infowindow4 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content4,
    position:[120.59328,31.314864],
    offset: new AMap.Pixel(16, -45)
});
var content5 = '<div class="info-title">木渎影视城送车点</div><div class="info-content">' +
    '地址：苏州市吴中区竹园路金山南路路口(送车点)<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：15312151522 <br/>' +
    '</div>';
var infowindow5 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content5,
    position:[120.519208,31.249103],
    offset: new AMap.Pixel(16, -45)
});
var content6 = '<div class="info-title">苏州新区高铁站送车点</div><div class="info-content">' +
    '地址：苏州城铁南路与浒莲路交叉口西南100米<br/>' +
    '营业时间：9:00-20:00 <br/>' +
    '门店电话：17315571696 <br/>' +
    '</div>';
var infowindow6 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content6,
    position:[120.523156,31.370466],
    offset: new AMap.Pixel(16, -45)
});
var content7 = '<div class="info-title">苏州园区高铁站送车点</div><div class="info-content">' +
    '地址：苏州市吴中区至和路园区站2层平台<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：18112722619 <br/>' +
    '</div>';
var infowindow7 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content7,
    position:[120.707177,31.340158],
    offset: new AMap.Pixel(16, -45)
});
var content8 = '<div class="info-title">绿宝广场送车点</div><div class="info-content">' +
    '地址：长江路436号绿宝广场<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：17751434314 <br/>' +
    '</div>';
var infowindow8 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content8,
    position:[120.543927,31.300417],
    offset: new AMap.Pixel(16, -45)
});
var content9 = '<div class="info-title">长江湾广场送车点</div><div class="info-content">' +
    '地址：长江路与余角里路交汇处南<br/>' +
    '营业时间：10:00-18:00 <br/>' +
    '门店电话：18013116796 <br/>' +
    '</div>';
var infowindow9 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content9,
    position:[120.497705,31.387492],
    offset: new AMap.Pixel(16, -45)
});
var content10 = '<div class="info-title">苏州世贸生活广场送车点</div><div class="info-content">' +
    '地址：苏州市吴中区宝带西路1177号<br/>' +
    '营业时间：9:00-19:00 <br/>' +
    '门店电话：18068006084 <br/>' +
    '</div>';
var infowindow10 = new AMap.AdvancedInfoWindow({
    isCustom: true,
    content: content10,
    position:[120.599374,31.259632],
    offset: new AMap.Pixel(16, -45)
});





//侧栏地图显示
selectAll();
function selectAll(){
    var url = "/bussiness";
    //侧栏地址显示
    $.get(url,function (result){
        $(".business-main-box-left").show();
        //console.log(result);
        //console.log(result.data);
        var data = result.data;
        for (var i = 0; i<data.length;i++){
            var bussiness = data[i];
            //console.log(bussiness);
            bussinessId = bussiness.busId;
            bussinessName = bussiness.busName;
            bussinessTel = bussiness.busTel;
            bussinessAddr = bussiness.busAddress;
            bussinessLal = bussiness.busLal;
            bussinessTime = bussiness.busTime;
            //console.log(bussinessId);
            //console.log(bussinessTel);
            //console.log(bussinessLal);
            var ele = " <div class=\"store-box\" onclick=\"infowindow"+bussinessId+".open(map,["+bussinessLal+"])\">\n" +
                "                <ul>\n" +
                "                    <li class=\"store-nummark\">"+bussinessId+"\n" +
                "                    <li class=\"store-name\">\n" +
                "                        "+bussinessName+"\n" +
                "                    <li class=\"store-address\">\n" +
                "                        "+bussinessAddr+"\n" +
                "                        <span></span>\n" +
                "                    <li class=\"store-phone\">\n" +
                "                        门店电话：<span> "+bussinessTel+"</span>\n" +
                "                    <li class=\"store-time\">\n" +
                "                        营业时间：<span>"+bussinessTime+"</span>\n" +
                "                </ul>\n" +
                "            </div>";
            $(".business-main-box-left").append(ele);
        }

    })

}


