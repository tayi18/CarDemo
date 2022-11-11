
    var devInfo = null
    var lnglat = [120.58, 31.30];
    var map = createMap() //1.new 一个map对象
    map.clearMap(); //2.清空Map
    addMarker(); //3.创建点标记

    //1创建map对象，
function createMap() {
    //1.地图初始化时，在地图上添加一个marker标记,鼠标点击marker可弹出自定义的信息窗体
    var mapData = new AMap.Map("EhiMap", {
    resizeEnable: true,
    center: lnglat , //地图展示中心点位置
    zoom: 14
});
    return mapData
}
    AMap.plugin(['AMap.ToolBar','AMap.Driving','AMap.DragRoute'],function(){//异步同时加载多个插件
    var toolbar = new AMap.ToolBar();
    map.addControl(toolbar);
    var driving = new AMap.Driving();//驾车路线规划
    driving.search(/*参数*/);
    var dragRoute = new AMap.DragRoute();
    map.addControl(dragRoute);
});

    //2添加点标记
function addMarker() {
    var markerData = [{
    title: '寒山寺点',
    content:'苏州市姑苏区和园路197号102室',
    time:'8:00-20:00',
    tel:'18013116796',
    icon: "https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-1.png", //点标记图片路径
    position: [120.571608,31.307677],
    offset: new AMap.Pixel(-10, -40)
}, {
    title: '东兴路便捷点',
    content:'苏州市工业园区东兴路118号栖庭花园商业地下停车场',
    time:'8:00-20:00',
    tel:'18120071550',
    icon: "https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-2.png", //点标记图片路径
    position: [120.535042,31.235405],
        offset: new AMap.Pixel(-10, -40)
}, {
    title: '水上乐园送车点',
    content:'玉山路166号',
    time:'9:00-19:00',
    tel:'17751434314',
    icon: "https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-3.png", //点标记图片路径
    position: [120.567445,31.260476],
        offset: new AMap.Pixel(-10, -40)
}, {
    title: '留园送车点',
    content:'姑苏区留园路289号（姑苏人家）',
    time:'9:00-19:00',
    tel:'18013116796',
    icon: "https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-4.png", //点标记图片路径
    position: [120.59328,31.314864],
        offset: new AMap.Pixel(-10, -40)
}, {
    title: '木渎影视城送车点',
    content:'苏州市吴中区竹园路金山南路路口(送车点)',
    time:'9:00-19:00',
    tel:'15312151522',
    icon: "https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-5.png", //点标记图片路径
    position: [120.519208,31.249103],
        offset: new AMap.Pixel(-10, -40)
}, {
        title: '苏州新区高铁站送车点',
        content:'苏州城铁南路与浒莲路交叉口西南100米',
        time:'9:00-20:00',
        tel:'17315571696',
        icon: "http://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png", //点标记图片路径
        position: [120.523156,31.370466],
        offset: new AMap.Pixel(-10, -40)
    }, {
        title: '苏州园区高铁站送车点',
        content:'苏州市吴中区至和路园区站2层平台',
        time:'9:00-19:00',
        tel:'18112722619',
        icon: "http://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png", //点标记图片路径
        position: [120.707177,31.340158],
        offset: new AMap.Pixel(-10, -40)
    }, {
        title: '绿宝广场送车点',
        content:'长江路436号绿宝广场',
        time:'9:00-19:00',
        tel:'17751434314',
        icon: "http://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png", //点标记图片路径
        position: [120.543927,31.300417],
        offset: new AMap.Pixel(-10, -40)
    }, {
        title: '长江湾广场送车点',
        content:'长江路与余角里路交汇处南',
        time:'10:00-18:00',
        tel:'18013116796',
        icon: "http://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png", //点标记图片路径
        position: [120.497705,31.387492],
        offset: new AMap.Pixel(-10, -40)
    }, {
        title: '苏州世贸生活广场送车点',
        content:'苏州市吴中区宝带西路1177号',
        time:'9:00-19:00',
        tel:'18068006084',
        icon: "http://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png", //点标记图片路径
        position: [120.599374,31.259632],
        offset: new AMap.Pixel(-10, -40)
    }]
    var arr = []
    markerData.forEach((item) => {
    var marker = new AMap.Marker({
    icon: item.icon, //点标记图片路径
    position: item.position, //位置
    offset: item.offset //偏移
});
    arr.push(Object.assign(item, {
    mapId: marker._amap_id
}))
    marker.setMap(map);
    // 鼠标点击marker弹出自定义的信息窗体

    AMap.event.addListener(marker, 'click', function(e) {
    markerClick(arr, marker)
});
})
}
    //点击标记 获取所点击标记的信息以及窗体要展示的数据，创建信息窗体
function markerClick(arr, marker) {
    var arrNew = arr.filter(x => x.mapId == marker._amap_id)

    devInfo = arrNew && arrNew[0]
    var infoWindow = createInfoWindow()
    openInfoWindow(infoWindow, marker)
}
    //构建自定义窗体
function createInfoWindow() {
    var infoWindowData = new AMap.AdvancedInfoWindow({
    isCustom: true, //使用自定义窗体
    content: getContent(),
    offset: new AMap.Pixel(16, -45)
});
    return infoWindowData
}
//处理窗体内容
function getContent() {
    var content = '<div class="info-title">'+devInfo.title+'</div><div class="info-content">' +
    '地址：'+ devInfo.content+' <br/>' +
    '营业时间：'+ devInfo.time+' <br/>' +
    '门店电话：'+ devInfo.tel+' <br/>' +
    '</div>'
    return content
}
    //打开窗体
function openInfoWindow(infoWindow, marker) {
    debugger
    infoWindow.open(map, marker.getPosition());
}
    //关闭窗体
    function closeInfoWindow() {
    map.clearInfoWindow();
}




/*
//地图区域样式切换
$(".new-area-box").click(function (event){
    var element = event.target;
    console.log(element);
    $(".area").each(function (){
        this.className = "";
    });
    element.className = "current area";
});

//点击左侧切换
/!*$(".business-main-box-left").click(function (){
    console.log(markerData[0].title);
    var infoWindow = createInfoWindow();
    openInfoWindow(infoWindow, marker);
    //关闭窗体
    closeInfoWindow();
});
    console.log(markerData[1].title);*!/
    var content1 = '<div class="info-title">寒山寺</div><div class="info-content">' +
        '地址：苏州市姑苏区和园路197号102室<br/>' +
        '营业时间：8:00-20:00 <br/>' +
        '门店电话：18013116796 <br/>' +
        '</div>';
    var infowindow1 = new AMap.AdvancedInfoWindow({
        isCustom: true,
        content: content1,
        position:[120.585467, 31.317761],
        offset: new AMap.Pixel(16, -45)
    });

    var content2 = '<div class="info-title">水上乐园送车点</div><div class="info-content">' +
        '地址：苏州市姑苏区和园路197号102室<br/>' +
        '营业时间：8:00-20:00 <br/>' +
        '门店电话：18013116796 <br/>' +
        '</div>';
    var infowindow2 = new AMap.AdvancedInfoWindow({
        isCustom: true,
        content: content2,
        position:[120.562363,31.288968],
        offset: new AMap.Pixel(16, -45)
    });

    var content3 = '<div class="info-title">东兴路便捷点</div><div class="info-content">' +
        '地址：苏州市姑苏区和园路197号102室<br/>' +
        '营业时间：8:00-20:00 <br/>' +
        '门店电话：18013116796 <br/>' +
        '</div>';
    var infowindow3 = new AMap.AdvancedInfoWindow({
        isCustom: true,
        content: content3,
        position:[120.585467, 31.307761],
        offset: new AMap.Pixel(16, -45)
    });*/
