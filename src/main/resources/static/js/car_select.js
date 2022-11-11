//品牌事件冒泡
document.querySelector(".car-name").onclick = function(event){
    var element = event.target;
    var name = element.innerText;
    var typeName =  element.nodeName;//标签名称
    $(".car-search").val(name);
    var type =  element.type;//标签类型

    var workArray = document.querySelector(".car-name").children;
    var temp = workArray[1].children[0].className
    // console.log(temp)
    $(".all").attr("class"," ")
    for(var i = 0;i<workArray.length;i++){
     workArray[i].children[0].className = " "
    }
    element.className = "active"
}
//加载Mysql到Elasticsearch数据库
LoadingCar()
function LoadingCar(){
    console.log("数据库数据加载")
    $.ajax({
        url: "/Es/loadCar",
        type: "get",
        success:function (result){
            showCar(result.data)
        }
    })
}

$(".pro-sumbit").click(function (){
    var content =  $(".car-search").val();
    getCarFilter(content)
    $(".pro-product").html(" ")
})



function getCarFilter(carKeyword){
    $.ajax({
        url:"/Es/getCar",
        type:"get",
        data:{carKeyword : carKeyword},
        success:function (result){
            console.log("a"+result.data+"a")
            if (result.data == ""){
                layer.msg("没有找到相关数据")
                return
            }
            showCar(result.data)
            layer.msg(result.reason)
        }

    })
}





//车辆渲染
function showCar(carArray) {
    for (var i = 0; i < carArray.length; i++) {
        var  car = carArray[i];
        var userId = $(".userId").val()
        console.log(car)
        var ele = "<div class=\"pro-rearly\">\n" +
            "                <a href=\"/details?carId="+car.carId+"&userId="+userId+"\">\n" +
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
            "                                <a>"+car.carDisp+"</a>\n" +
            "                                <a>"+car.carModel+"</a>\n" +
            "                                <a>"+car.carSeat+"</a>\n" +
            "                            </li>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <!-- 价格 -->\n" +
            "                    <div class=\"pro-price\">\n" +
            "                        <div class=\"pro-text\">\n" +
            "                            <a>¥</a>\n" +
            "                            <a>"+car.carPrice+"</a>\n" +
            "                            <span>/日</span>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                </a>\n" +
            "            </div>"
        $(".pro-product").append(ele);
    }

}
