

function userShow(){
    var token = localStorage.getItem("token");
    console.log(token)
    if(token != null) {
        $.ajax({
            type: "post",
            url: "/customer/userVerification/" + token,  //用户验证,
            // data:{token:token},
            success: function (result) {
                var user = result.data
                // if (userName!=null){
                //更新页面
                console.log(user)
                var userPetname = user.userPetname;
                console.log(userPetname)
                $(".loginAfter").text("欢迎回来!");
                $(".loginAfter").attr("href", "../customer/customer_center.html")
                $(".registAfter").attr("href", "../customer/customer_center.html")
                if(userPetname != null){
                    $(".registAfter").text(user.userPetname);
                }else {
                    $(".registAfter").text(" ");
                }


                $(".left").append("<input class=\"userId\" value=\"\" type=\"hidden\">");
                $(".userId").val(user.userId)
                $("#userId").val(user.userId)
                $(".bye").attr("href", "../index.html")
                $(".bye").attr("class", "bye")
                let ele ="<a href=\"../customer/customer_center.html\">我的消息</a>" ;
                 $(".header-top .left .name").html(ele);
                $.ajax({
                    type: "get",
                    url: "/getUserMsg",
                    data: {
                        userId: user.userId
                    },
                    success: function (result) {
                        var msgArray = result.data
                        var temp =0;
                        for (var i = 0; i < msgArray.length; i++) {
                            if (msgArray[i].userMsgStatus==0){
                                temp++;
                            }
                        }
                        if (temp!=0)
                            $(".header-top .left .name").append("<a>(您有"+temp+"条新消息)</a>");

                    }
                })
                //  }
            }
        })
    }
}
userShow();

$(".bye").click(function (){
    localStorage.removeItem("token");
    $.ajax({
        type: "get",
        url:"/customer/logout"
    })
});


var ws;
var userId ;
var userName;
websocketShow();
function websocketShow (){
    //检查浏览器是否支持
    if ("WebSocket" in window) {
        console.log("你的浏览器支持")
        var token = localStorage.getItem("token");
        if (token != null) {
            $.ajax({
                type: "post",
                url: "/customer/userVerification/" + token,  //用户验证,
                // data:{token:token},
                success: function (result) {
                    var user = result.data
                    userId = user.userId;
                    userName = user.userName;
                    OpenWebsocket();
                }
            })
        }
    }else{
        layer.msg("你的浏览器不支持websocket!garbage")
    }
}

function OpenWebsocket(){
    //与客户端建立连接
    ws = new WebSocket("ws://localhost:8089/WebSocket/" + userId);
    console.log(ws);
    //    与服务连接时触发
    console.log(ws)
    ws.onopen = function (){
      //  layer.msg("与服务器连接成功，客户端ID:"+userId);
        ws.send("你好服务端！！我是客户端"+userId);

    }
//接受消息时触发
    ws.onmessage = function (evt){
        let breakMsg = evt.data;
        console.log(breakMsg)
        layer.msg("从客户端接收到消息"+breakMsg);
    }

    ws.onclose = function (){
        layer.msg("服务已关闭")
    }

}

//关闭窗口后自动清楚token
// window.onload = function () {
//     let lastTime = localStorage.getItem("lastTime");
//     const interval = 3 * 1000;
//     // 如果没有上一次离开的时间或者时间间隔大于3s，就清除token
//     if (!lastTime || new Date().getTime() - lastTime > interval) {
//         localStorage.removeItem("token");
//         console.log("清除token")
//     }else{
//         console.log("时间过短不清除token")
//     }
// };
// window.onunload = function () {
//     localStorage.setItem("lastTime", new Date().getTime());
// };