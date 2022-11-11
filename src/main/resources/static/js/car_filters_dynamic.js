//设置默认时间
{
    var now = new Date();
    var  day;
    if (now.getDate()<10)
        day = "0"+now.getDate();
    var valuetime = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + day

    $(".t1").val(valuetime)
    if (now.getHours() < 10) {
        var hour = "0" + now.getHours()
    } else var hour = now.getHours()
    if (now.getMinutes() < 10) {
        var minu = "0" + now.getMinutes()
    } else var minu = now.getMinutes()
    if (now.getSeconds() < 10) {
        var seco = "0" + now.getSeconds()
    } else var seco = now.getSeconds()
    $(".t2").val(hour + ":" + minu + ":" + seco)

}







 getByPage(1);//默认查询第一页
//根据页数查询到数据
function getByPage(pageNum,carModel,carName,carPrice){
    $(".pro-product").html(" ");
       if(carPrice == ""||carPrice==undefined)
           carPrice = "no";
       if(carModel == ""||carModel==undefined)
           carModel = "no";
       if(carName == ""||carName==undefined)
           carName = "no";
       var url = "/Car/getCarByPage/"+pageNum +"/" +carModel+"/" +carName +"/" +carPrice;
       console.log(url)
       carSelect(url);
}


//车辆品牌冒泡
var carModel;
$(".car-model a").click(function (){
    carModel = $(this).text().replace(/\s*/g,"");
    var carPrice = $(".car-select .active").text().replace(/[\u4e00-\u9fa5]/g,"")
    var carBrand = $(".car-brand .active").text()
    getByPage(1,carModel,carBrand,carPrice)
})
//价格冒泡
$(".car-price a").click(function() {
    var temp = $(this);
    var price = temp.text().replace(/[\u4e00-\u9fa5]/g,"");
    console.log(price);
    var carBrand = $(".car-brand .active").text()
    getByPage(1,carModel,carBrand,price)
})
$(".car-name a").click(function() {
    var text = $(this);
    if(text.attr('class')==='active'){
        return
    }
    // console.log(text.text());
    var carName = text.text();
    var carBrand = $(".car-name .active").text()+carName
    console.log(carModel)
    var carPrice = $(".car-select .active").text().replace(/[\u4e00-\u9fa5]/g, "")
    console.log(carBrand)
    getByPage(1,carModel,carBrand,carPrice)
})

//数据查询
function carSelect(url){
    $.ajax({
        url:url,
        type:"get",
        success:function (response){
         //   console.log(response.data)
            var result = response.data;
            var CarArray =  response.data.records;
            // console.log(CarArray)
            showCar(CarArray)
            //页码信息;
            {
                $(".page-select .pages").val(result.pages)
               // $(".page-select .total").text(result.total)
                $(".page-select .current").val(result.current)
            }
        }
    })
}
//上一页，下一页按钮监听
// $(".upper").click(function () {
//
//     var carBrand = $(".car-name .active").text()
//     var carModel = $(".car-model .hot-active").text().replace(/\s*/g,""); //去除字符串空格
//     var carPrice = $(".car-select .active").text().replace(/[\u4e00-\u9fa5]/g, "")
//     var page = parseInt($(".page-select .current").text())-1
//     getByPage(page,carModel,carBrand,carPrice)
//     console.log("上一页")
// })
// $(".down").click(function () {
//
//   //  }
//     var carBrand = $(".car-name .active").text()
//    carModel = $(".car-model .hot-active").text().replace(/\s*/g,""); //去除字符串空格
//     var carPrice = $(".car-select .active").text().replace(/[\u4e00-\u9fa5]/g, "")
//     var page = parseInt($(".page-select .current").text()) + 1
//     if ($(this).attr("id") == "strat"&&$(".pagination .active").text()!=1)
//         now = parseInt(now)-1
//     console.log(now)
//     getByPage(page,carModel,carBrand,carPrice)
// })

$(".pagination a").click(function (){
    var now =  ($(this).text())
 //  var now = $(".pagination .active").text())«»

    var carBrand = $(".car-name .active").text()
   carModel = $(".car-model .hot-active").text().replace(/\s*/g,""); //去除字符串空格
    var carPrice = $(".car-select .active").text().replace(/[\u4e00-\u9fa5]/g, "")
    console.log($(this).attr("id"))
    if ($(this).attr("id") == "strat"&&$(".pagination .active").text()!="1")
        now = parseInt(now)+1
    if (now > $(".pages").val()) {
        layer.msg("当前已经是最后一页了")
        now = parseInt(now) - 1
    }

    console.log(now)
    // if (now$(".page-select .pages").val()) {
    //
    //     return;
    // }
    getByPage(now,carModel,carBrand,carPrice)


})
var start1 ;
var start2 ;
var start3 ;
var start4 ;

$(".pro-sumbit").click(function (){

    getByPage(1);
})

$(".pro-product").on("click",".pro-order",function (event){
    //地点
    f1 = $(".area").val();
    f2 = $(".area1").val();
    if (f1 == "" || f2 == ""){
        event.target.parentElement.href ="javascript:void(0)";
        layer.msg("请选择取车还车门店")
        return
    }
    //时间
    start1 = $(".t1").val();
    start2 = $(".t2").val();
    start3 = $(".t3").val();
    start4 = $(".t4").val();
    if (start1 == "" || start2 == "" ||start3 == "" ||start4 == ""){
        console.log("时间没选")
        event.target.parentElement.href ="javascript:void(0)";
        layer.msg("请先选择时间")
    }else if(event.target.parentElement.href =="javascript:void(0)"){
        layer.msg("记得点击确认选择按钮哦")
    }
})


//商品渲染
function showCar(carArray){

    var start1 = $(".t1").val();
    var start2 = $(".t2").val();
    var start3 = $(".t3").val();
    var start4 = $(".t4").val();

    var start = start1+" "+start2;
    var end = start3+" "+start4;
    if (start1 == null || start2 == null ||start3 == null ||start4 == null){
        layer.msg("请先选择时间")
    }
    // console.log(start+"111111")
    // console.log(end+"22222222")

    var userId = $(".userId").val();
    console.log(userId);

    for (var i =0 ; i<carArray.length;i++){
        var car = carArray[i];
       // console.log(car);
        var ele = " <div class=\"pro-rearly\">\n" +
            "                    <div class=\"img\">\n" +
            "                        <img src=\""+car.carImg+"\" alt=\"\">\n" +
            "                    </div>\n" +
            "                    <!-- 车辆信息 -->\n" +
            "                    <div class=\"information\">\n" +
            "                        <div class=\"in-top\">\n" +
            "                            <li><a class=\"car-name\">"+car.carName+"</a></li>\n" +
            "                        </div>\n" +
            "                        <div class=\"in-middle\">\n" +
            "                            <li class=\"info\">\n" +
            "                                <a>"+car.carDisp+"</a><span>|</span>\n" +
            "                                <a>"+car.carCase+"</a><span>|</span>\n" +
            "                                <a>"+car.carSeat+"座"+"</a><span>|</span>\n" +
            "                                <a href=\"/details?carId="+car.carId+"&userId="+userId+"\" class=\"info-detalis\">车辆详情></a>\n" +
            "                            </li>\n" +
            "                        </div>\n" +
            "                        <div class=\"in-bottom\">\n" +
            "                            <li class=\"lable\">\n" +
            "                                <a>省油</a>\n" +
            "                                <a>舒适</a>\n" +
            "                                <a>新能源</a>\n" +
            "                            </li>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <!-- 价格 -->\n" +
            "                    <div class=\"pro-price\">\n" +
            "                        <div class=\"pro-text\">\n" +
            "                            <a>¥</a>\n" +
            "                            <a>"+car.carPrice+"</a>\n" +
            "                            <span>/每日</span>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <!-- 预定按钮 -->\n" +
            "                    <div class=\"pro-bottom\">\n" +
            "\n" +
            "                       <a class='pro-order12' href=\"../dropOrder?carId="+car.carId+"&start="+start+"&end="+end+"\">\n" +
            "                            <div class=\"pro-order\" >\n" +
            "                                预购\n" +
            "                            </div>\n" +
            "                        </a>\n" +
            "                    </div>\n" +
            "                </div>"
        $(".pro-product").append(ele);
    }
}

