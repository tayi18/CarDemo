
$(".sub-nav").click(function (event){
    //侧栏样式
    var element = event.target;
    $(".cc-change").each(function (){
        this.className = "cc-change";
    })
    element.className = "cc-change ck";
    var eId = element.id;
    //console.log(eId);
    //内容切换
    $(".information").each(function (){
        this.className = "information hide";
    });
    if (eId === "exchange-info1" || eId === "exchange1"){
        $("#info1").attr("class","information");
    }else if (eId === "exchange-info2"){
        $("#info2").attr("class","information");
    }else if (eId === "exchange-info3"){
        $("#info3").attr("class","information");
    }else if (eId === "exchange-info4" || eId === "exchange2"){
        $("#info4").attr("class","information");
    }else if (eId === "exchange-info5"){
        $("#info5").attr("class","information");
    }else if (eId === "exchange-info6") {
        $("#info6").attr("class", "information");
    }
});

$(".order_exchange").click(function (){
    //console.log("1a");
    $(".information").each(function (){
        this.className = "information hide";
    });
    $("#info6").attr("class","information");
});
//获取修改个人信息的值
$("#Submit").click(function(){
    var username = $(".username").val();
    //console.log(username);
    var usertel = $(".usertel").val();
   // console.log(usertel);
    var useremail = $(".useremail").val();
    //console.log(useremail);
    var useridcardIpt = $(".useridcardIpt").val();
    //console.log(useridcardIpt);
    var gender = $(".gerderBtn:checked").val();//单选按钮 radio
    //console.log(gender);
    var userdate = $(".userdate").val();
    //console.log(userdate);
});

//切换订单界面
$(function () {
    $('#myTab a:last').tab('show');
})
$('#myTab a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
})

//显示当前用户信息
showUser();
function showUser(){
    var url = "/user";
    var token = localStorage.getItem("token");

    //console.log(token);
    $.ajax({
        type: "get",
        url:url,
        headers:{'token':token},
        success:function (result){
            var user = result.data;
            //console.log(user);
            userId = user.userId;
            userTel = user.userTel;
            userPsd = user.userPsd;
            userGender = user.userGender;
            if (userGender === "0"){
                userGender = '男';
            }else{
                userGender = '女';
            }
            userIdcard = user.userIdcard;
            if (userIdcard === null){
                userIdcard = '';
            }
            userName = user.userName;
            if (userName === null){
                userName = ' ';
            }
            userEmail = user.userEmail;
            if (userEmail === null){
                userEmail = ' ';
            }
            userBir = user.userBir;
            if (userBir === null){
                userBir = '2000-01-01';
            }
            userPetname = user.userPetname;
            if (userPetname === null){
                userPetname = '';
            }

            var userEle = "<h2>用户信息</h2>\n" +
                "                <div class=\"mod information-index\">\n" +
                "                    <div class=\"ui-bfc information-index-panel\">\n" +
                "                        <!--头像暂定-->\n" +
                "                        <div class=\"ui-bfc-hd hd\">\n" +
                "                            <img src=\"../images/customer_photo.png\" width=\"99\" height=\"99\">\n" +
                "                        </div>\n" +
                "                        <!-- 个人信息-->\n" +
                "                        <div class=\"ui-bfc-bd bd\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"cell-title\">用户名：</td>\n" +
                "                                    <td>\n" +
                "                                        <p>"+userPetname+"</p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"cell-title\">电子邮箱：</td>\n" +
                "                                        <td>\n" +
                "                                            <p>"+userEmail+"</p>\n" +
                // "                                            <span class=\"gray\">(未验证)</span>\n" +
                // "                                            <a class=\"update-info\"  onclick=\"jump()\">修改</a>\n" +
                "                                        </td>\n" +
                "\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"cell-title\">手机号码：</td>\n" +
                "                                        <td>\n" +
                "                                            <p>"+userTel+"</p>\n" +
                // "                                            <span class=\"gray\">(未验证)</span>\n" +
                // "                                            <a class=\"update-info\"  onclick=\"jump()\">修改</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"cell-title\">身份证：</td>\n" +
                "                                    <td>\n" +
                "                                        <p>"+userIdcard+"</p>\n" +
                //"                                        <span class=\"gray\">(未验证)</span>\n" +
                // "                                        <a class=\"update-info\"  onclick=\"jump()\">修改</a>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"cell-title\">真实姓名：</td>\n" +
                "                                    <td>\n" +
                "                                        <p>"+userName+"</p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"cell-title\">生日：</td>\n" +
                "                                    <td>\n" +
                "                                        <p>"+userBir+" </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"cell-title\">性别：</td>\n" +
                "                                    <td>\n" +
                "                                        <p>"+userGender+" </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"operate-panel\">\n" +
                "                        <a class=\"ui-btn ui-btn-white-l update-info\"  onclick=\"jump()\">修改信息</a>\n" +
                "                    </div>\n" +
                "                </div>";
            $("#info1").append(userEle);
            $(".userId").val(userId);
            $("#userTel").val(userTel) ;
            $("#Psdword").val(userPsd) ;
            $("#userGender").val(userGender) ;
            $("#userIdcard").val(userIdcard) ;
            $("#userName").val(userName) ;
            $("#userEmail").val(userEmail) ;
            $("#userBir").val(userBir) ;
            $("#userPetname").val(userPetname) ;
        }
    })
}
//点击个人中心-用户信息中的修改跳到修改信息
function jump(){
    //侧栏样式
    $(".cc-change").each(function (){
        this.className = "cc-change";
    })
    $("#exchange-info3").attr("class","cc-change ck");
    //内容切换
    $(".information").each(function (){
        this.className = "information hide";
    });
    $("#info3").attr("class","information");
    //console.log("4111")
};

//修改
function updateUser(){
    var userDate = $("#myinfo").serialize();
    //console.log(userDate)
    var url = "/user/update";
    var gender = $(".gerderBtn:checked").val();
    var token = localStorage.getItem("token");
    var userPsd = $("#ConfirmPassword").val();
    if (userPsd == null || userPsd === '' || userPsd === 0 ){
        userPsd = $("#Psdword").val()
    }

    //console.log(token);
    $.ajax({
        type:"post",
        url:url,
        headers:{'token':token},
        data:{
            userTel:$("#userTel").val(),
            userPsd:userPsd,
            userGender:gender,
            userIdcard:$("#userIdcard").val(),
            userName:$("#userName").val(),
            userEmail:$("#userEmail").val(),
            userBir:$("#userBir").val(),
            userPetname:$("#userPetname").val()
        },
        success:function (result){
            //console.log("result"+result);
            //window.location.href = "/customer/customer_center.html";
            //console.log("122345");
            layer.open({
                title: '个人中心',
                content: '保存信息成功',
                yes:function (){
                    location.reload();
                }
            });
        }
    })
}
//修改个人中心
$("#Submit").click(function (){
    //console.log(1);
    updateUser();
});

//验证用户名
if ($(".userPetname").val() != null){
    $(".userPetname").blur(checkUsername);
}
function checkUsername(){
    var userPetname = $(".userPetname").val();
    //console.log(username);
    if(!/^[\u4E00-\u9FA5A-Za-z0-9]+$/.test(userPetname)){
        $(".userPetnameTip").text("用户名只能有中文、英文、数字但不包括下划线等符号");
        return false;
    }else{
        $(".userPetnameTip").text("");
        return true;
    }
}
//验证手机号
if ($(".usertel").val() != null){
    $(".usertel").blur(checkUsertel);
}
function checkUsertel(){
    var usertel = $(".usertel").val();
    if(!/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(usertel)){
        $(".useretelTip").text("手机号格式不对");
        return false;
    }else{
        $(".useretelTip").text("");
        return true;
    }
}

//验证邮箱
if ($(".useremail").val() != null){
    $(".useremail").blur(checkUseremail);
}

function checkUseremail(){
    var useremail = $(".useremail").val();
    if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(useremail)){
        $(".useremailTip").text("邮箱格式不对");
        return false;
    }else{
        $(".useremailTip").text("");
        return true;
    }
}
//验证身份证
if($(".useridcardIpt").val() != null){
    $(".useridcardIpt").blur(checkUserIdCard);
}
function checkUserIdCard(){
    var useridcardIpt = $(".useridcardIpt").val();
    if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(useridcardIpt)){
        $(".userIdcardTip").text("身份证格式不对");
        return false;
    }else{
        $(".userIdcardTip").text("");
        return true;
    }
}

//修改密码
$("#btnSubmit").click(function (){
    //console.log(2);
    //console.log(checkUserNewpassword());
    //console.log(checkUserpassword());
    if(checkUserNewpassword() && checkUserpassword()){
        updateUser();
    }
});
//验证新密码
$("#NewPassword").blur(checkUserpassword);
function checkUserpassword(){
    var NewPassword =  $("#NewPassword").val();
    var Password = $("#userPsd").val();
    if(NewPassword===""||NewPassword==null){
        $(".NewPasswordwordTip").text("密码不能为空");
        return false;
    }else if(!/^[a-zA-Z]\w{5,17}$/.test(NewPassword)){
        $(".NewPasswordwordTip").text("密码只能是字母、数字、下划线组成，长度为6-18个字符");
        return false;
    }else if(Password === NewPassword) {
        $(".NewPasswordwordTip").text("密码与原密码一致");
        return false;
    }else{
        $(".NewPasswordwordTip").text("");
        return true;
    }
}
//验证新密码
$("#ConfirmPassword").blur(checkUserNewpassword);
function checkUserNewpassword(){
    var ConfirmPassword =  $("#ConfirmPassword").val();
    var NewPassword =  $("#NewPassword").val();
    if(ConfirmPassword===""||ConfirmPassword==null){
        $(".ConfirmPasswordwordTip").text("密码不能为空");
        return false;
    }else if(!/^[a-zA-Z]\w{5,17}$/.test(ConfirmPassword)){
        $(".ConfirmPasswordwordTip").text("密码只能是字母、数字、下划线组成，长度为6-18个字符");
        return false;
    }else if(ConfirmPassword !== NewPassword) {
        $(".ConfirmPasswordwordTip").text("密码与上一个不一致");
        return false;
    }else{
        $(".ConfirmPasswordwordTip").text("");
        return true;
    }
}

//优惠券显示
showCoupon();
function showCoupon(){
    var url = "/usercoupon";
    var token = localStorage.getItem("token");
    $.ajax({
        type: "get",
        url: url,
        headers: {'token': token},
        success:function (result){
            var coupon = result.data;
            //console.log(coupon);
            for (var i=0; i<coupon.length;i++){
                var couponUser = coupon[i];
                //console.log(couponUser);
                var couponusercounum = couponUser['couponUserCouNum'];
                var couponexplain = couponUser['couponExplain'];
                var coupontimeend = couponUser['couponTimeEnd'];
                //console.log(coupontimeend);
                var couponstate = couponUser['couponState'];
                if (couponstate === "0"){
                    couponstate = "未使用";
                }else if (couponstate === "1"){
                    couponstate = "已使用";
                }
                var couponEle = "<tr>\n" +
                    "                                    <td class=\"row-first-cell\" >"+couponusercounum+"</td>\n" +
                    "                                    <td>"+coupontimeend+"</td>\n" +
                    "                                    <td>\n" +
                    "                                        <span class=\"green bold\">"+couponstate+"</span>\n" +
                    "                                    </td>\n" +
                    "                                    <td class=\"btn-show-rules\">\n" +
                    "                                        <a data-toggle=\"modal\" data-target=\"#myModal\">"+couponexplain+"</a>\n" +
                    "                                    </td>\n" +
                    "                                </tr>";
                $(".coupon-text").append(couponEle);

            }
        }

    })

}

//全部订单显示
showOrderAll();
function showOrderAll(){
    var url = "/userorder";
    var token = localStorage.getItem("token");
    $.ajax({
        type: "get",
        url: url,
        headers: {'token': token},
        success:function (result) {
            var orderUser = result.data;

            for (var i=0; i<orderUser.length;i++){
                var order = orderUser[i];
                var ordId = order['ordId'];
                var ordNumber = order['ordNumber'];
                var ordSatus = order['ordSatus'];
                var ordPicTime = order['ordPicTime'];
                var ordDroTime = order['ordDroTime'];
                var carName = order['carName'];
                var carModel = order['carModel'];
                var carCase = order['carCase'];
                var carDisp = order['carDisp'];
                var orderEle = "<tr>\n" +
                    "          <td><a class=\"order_exchange\" href=\"/orderdetail/"+ordNumber+"\">"+ordNumber+"</a></td>\n" +
                    "          <td>"+carName+"/"+carModel+"/"+carCase+" /"+carDisp+"</td>\n" +
                    "          <td>"+ordPicTime+"</td>\n" +
                    "          <td>"+ordDroTime+"</td>\n" +
                    "          <td>"+ordSatus+"</td>\n" +
                    "          </tr>";
                $(".order-text1").append(orderEle);
            }
        }
    })
}


function showMSg() {
    var token = localStorage.getItem("token");
    console.log(token)
    if(token != null) {
        $.ajax({
            type: "post",
            url: "/customer/userVerification/" + token,  //用户验证,
            success: function (result) {
                var user = result.data
                    //更新页面
              //  console.log(user)
                $(".userId").val(user.userId)
                Id  = user.userId;
                $(".registAfter").text(user.userName);
                $.ajax({
                    type:"get",
                    url:"/getUserMsg",
                    data: {
                        userId: Id
                    },
                    success:function (result){

                    //    console.log(result.data)
                        var msgArray = result.data
                        msgshow(msgArray);

                    }




                })

            }
        })
    }
}


//我的消息
$("#exchange-info6").click(function (){
    showMSg()
})
$(".personal-prent").on('click',"#yidu-botton-rrr",function(event) {
    var userId = event.target.previousElementSibling.previousElementSibling.value;
    var msgId = event.target.previousElementSibling.value;
    console.log(msgId+userId)
    $.ajax({
        type:"get",
        url:"/msgUpdate",
        data:{
            userId:userId,
            msgId:msgId
        },
        success:function (result){
            msgshow(result.data)
        }
    })
})


function msgshow(msgArray){
    $(".personal-prent").html(" ")
    for (var i = 0; i < msgArray.length; i++) {
        if (msgArray[i].userMsgType == 0)
            var msgType = "系统消息"
        if (msgArray[i].userMsgType == 1)
            msgType = "商家消息"
        if (msgArray[i].userMsgType == 2)
            msgType = "活动消息"
        var eleplus ;

        if (msgArray[i].userMsgStatus == 0) {
            eleplus =  "<input type=\"hidden\" class=\"user-id\" value=\""+msgArray[i].userId+"\">\n" +
                "                            <input type=\"hidden\" class=\"user-msg-id\" value=\""+msgArray[i].userMsgId+"\">\n" +
                "                            <a href=\"javascript:void(0)\" id=\"yidu-botton-rrr\">确认已读</a>"
        }else {
            eleplus = "";
        }

        var ele  = " <div class=\"personal\">\n" +
            "                        <div class=\"fasongzhe\">\n" +
            "                            <span>"+msgType+"</span>\n" +
            "                        </div>\n" +
            "                        <div class=\"msg-text\">\n" +
            "                            <span>\n" +
            "                               "+msgArray[i].userMsgContent+"\n" +
            "                            </span>\n" +
            "                        </div>\n" +
            "                        <div class=\"yidu-botton\">\n" +
            eleplus
            +
            "                        </div>\n" +
            "\n" +
            "                    </div>"
        $(".personal-prent").append(ele);
    }

}


function showMSg() {
    var token = localStorage.getItem("token");
    console.log(token)
    if(token != null) {
        $.ajax({
            type: "post",
            url: "/customer/userVerification/" + token,  //用户验证,
            success: function (result) {
                var user = result.data
                    //更新页面
              //  console.log(user)
                $(".userId").val(user.userId)
                Id  = user.userId;
                $(".registAfter").text(user.userName);
                $.ajax({
                    type:"get",
                    url:"/getUserMsg",
                    data: {
                        userId: Id
                    },
                    success:function (result){

                    //    console.log(result.data)
                        var msgArray = result.data
                        msgshow(msgArray);

                    }




                })

            }
        })
    }
}


//我的消息
$("#exchange-info6").click(function (){
    showMSg()
})
$(".personal-prent").on('click',"#yidu-botton-rrr",function(event) {
    var userId = event.target.previousElementSibling.previousElementSibling.value;
    var msgId = event.target.previousElementSibling.value;
    console.log(msgId+userId)
    $.ajax({
        type:"get",
        url:"/msgUpdate",
        data:{
            userId:userId,
            msgId:msgId
        },
        success:function (result){
            msgshow(result.data)
        }
    })
})


function msgshow(msgArray){
    $(".personal-prent").html(" ")
    for (var i = 0; i < msgArray.length; i++) {
        if (msgArray[i].userMsgType == 0)
            var msgType = "系统消息"
        if (msgArray[i].userMsgType == 1)
            msgType = "商家消息"
        if (msgArray[i].userMsgType == 2)
            msgType = "活动消息"
        var eleplus ;

        if (msgArray[i].userMsgStatus == 0) {
            eleplus =  "<input type=\"hidden\" class=\"user-id\" value=\""+msgArray[i].userId+"\">\n" +
                "                            <input type=\"hidden\" class=\"user-msg-id\" value=\""+msgArray[i].userMsgId+"\">\n" +
                "                            <a href=\"javascript:void (0)\" id=\"yidu-botton-rrr\">收到</a>"
        }else {
            eleplus = "";
        }

        var ele  = " <div class=\"personal\">\n" +
            "                        <div class=\"fasongzhe\">\n" +
            "                            <span>"+msgType+"</span>\n" +
            "                        </div>\n" +
            "                        <div class=\"msg-text\">\n" +
            "                            <span>\n" +
            "                               "+msgArray[i].userMsgContent+"\n" +
            "                            </span>\n" +
            "                        </div>\n" +
            "                        <div class=\"yidu-botton\">\n" +
            eleplus
            +
            "                        </div>\n" +
            "\n" +
            "                    </div>"
        $(".personal-prent").append(ele);
    }

}








