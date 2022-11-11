//验证用户名
$(".userTel").blur(checkUsername)

//验证用户名
function checkUsername(){
    var username = $(".userTel").val()

    if(username === "" || username == null){
        $(".usernameTip").text("手机号不能为空")
        return false;
    }else if( !/^[0-9]\w{10}$/.test(username)){
        $(".usernameTip").text("手机号格式不正确")
        return false;
    }else{

        $(".usernameTip").text("");
        return true;
    }
}

//验证密码
$(".userPsd").blur(checkuserpwd);

function checkuserpwd(){
    var userPsd = $(".userPsd").val()

    if(userPsd === "" || userPsd == null){
        $(".psdTip").text("密码不能为空")
        return false;
    }else if( !/^[a-zA-Z0-9]\w{5,100}$/.test(userPsd)){
        $(".psdTip").text("密码格式错误")
        return false;
    }else{

        $(".psdTip").text("");
        return true;
    }
}
//验证手机号
$(".phoneNo").blur(checkPhoneNo)
function checkPhoneNo() {
    var phoneNo = $(".phoneNo").val()

    if (phoneNo === "" || phoneNo == null) {
        $(".phoneTip").text("手机号不能为空")
        return false;
    } else if (!/^[0-9]\w{10}$/.test(phoneNo)) {
        $(".phoneTip").text("手机号格式错误")
        return false;
    } else {

        $(".phoneTip").text("");
        return true;
    }

}
// function getLoginValue() {
//     var userTel = document.querySelector(".userTel").value;
//     var userPwd = document.querySelector(".userPsd").value;
//     var box = document.querySelector("input[type = checkbox]").checked;
//     console.log("userTel:" + userTel);
//     console.log("userPwd:" + userPwd);
//     if (!userTel || !userPwd) {
//         console.log("no password or telNo id error")
//         document.querySelector(".error").text = "登陆账号或者密码错误！"
//         return false;
//     } else if (!box) {
//         console.log("no box!");
//         document.querySelector(".no-agree").text = "请勾选服务！";
//         return false;
//     } else if (!/^[0-9]\w{10}$/.test(phoneNo)){
//         console.log("不符合条件")
//         return false;
//     } else{
//         console.log("all none")
//         login();
//     }
// }


$(".btn").click(function (){
    login()
})
function login(){
        $.ajax({
            type:"post",
            url:"../customer/login",
            data:{
                userTel:$(".userTel").val(),
                userPsd:md5($(".userPsd").val())
                // box:$(".loginAgree").val()
            },
            success:function (result) {
                console.log("result:"+result);
                var box = document.querySelector("input[type = checkbox]").checked;
                var username = $(".userTel").val()


                if (result.resultcode != 200) {
                    console.log("no password or telNo id error")
                    $("#loginfail").text("手机号或密码不正确！")
                } else if (!box) {
                    console.log("no box!");
                    $("#loginfail").text("请勾选服务！")
                } else if (!/^[0-9]\w{10}$/.test(username)){
                    console.log("不符合条件")
                    $("#loginfail").text("格式不正确！")

                } else{
                    var token = result.data;
                    console.log("token:"+token);
                    localStorage.setItem("token",token);
                    window.location.href = "../index.html";
                }
                // if(result.resultcode == 200 ){
                //     var token = result.data;
                //     console.log("token:"+token);
                //     localStorage.setItem("token",token);
                //     window.location.href = "../index.html";
                // }else {
                //     $("#loginfail").text("手机号或密码不正确！")
                // }



            }
        })
}

var button = document.getElementById('loginCodeBtn');
button.onclick = function() {
    button.disabled = 'disabled';
    var time = 60;
    var timer = setInterval(function() {
        if (time == -1) {
            clearInterval(timer)
            button.disabled = '';
            button.value = '获取验证码';
        } else {
            button.value = time + '秒后重新获取';
            time--;
        }
    }, 1000)
}

var sms = "";
//发送验证码
$(".smscode").click(function (){
    sendCode()
})

function sendCode(){

    // var sms = "";
    var phone = $("#loginPhone").val();
    console.log("登陆的手机号:"+phone);
    if (phone != ""){

        $.ajax({
            url : "../customer/loginSend",
            type : "get",
            dataType : "text",
            async:false,
            data: {
                "phone" : phone
            },
            success:function (result) {
                console.log("............");
                console.log(result)

                console.log(typeof result)

                sms = JSON.stringify(result).slice(52,58);
                console.log(typeof sms[2]);
                console.log(sms)
                console.log("code:"+ sms);
                //    console.log("sms:"+sms)
                layer.msg("验证码已发送");

            }
        });

        return sms;
        // console.log("sms:"+sms);
    }else {
        $("#loginfail1").text("请输入手机号")
        return false;
    }




}
// $(document).on('click',".smscode",function (){
//
// });
//点击登录
// $(document).on('click','.codebtn',function(){
//
//
//     // var sms = sendCode();
//     console.log("sms:"+sms)
//     // console.log("sms:"+sms);
//     var code = $("#loginCode").val();
//     console.log("code:"+code)
//     if (code == "") {
//         $("#loginfail1").text("请输入验证码！")
//     }else if ( sms != code){
//         $("#loginfail1").text("验证码错误！")
//     }else {
//         window.location.href = "../index.html";
//     }
//
// });
//根据手机号快速登陆
$(".codebtn").click(function (){
    loginByTel()
})
function loginByTel(){
    $.ajax({
        type:"post",
        url:"../customer/loginByTel",
        data:{
            userTel: $(".phoneNo").val()
        },
        success:function (responseTel){
            console.log("responseTel:"+responseTel);
            var phoneNo = $(".phoneNo")
            var code = $("#loginCode").val();

            if (responseTel.resultcode !=200){
                console.log("手机号还没有注册，请先注册")
            } else if (code == "") {
                $("#loginfail1").text("请输入验证码！")
            }else if ( sms != code){
                $("#loginfail1").text("验证码错误！")
            }else {
                var token = responseTel.data;
                console.log("token:"+token);
                localStorage.setItem("token",token);
                window.location.href = "../index.html";
            }
        }
    })
}