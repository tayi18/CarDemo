//查询所有优惠卷
//.records    JSON转数组
getCoupno()
// var couArray ; //返回的信息
function getCoupno(){
    $.get("/getAllCoupon",function (result){
        couArray = result.data;
        console.log(couArray)
        for (var i = 0; i <couArray.length ; i++) {
            var coupno = couArray[i];
            var ele = "     <div class=\"coupno-real\">\n" +
                "               <div class=\"coupno-left\">\n" +
                "                   <div class=\"coupno-img\">\n" +
                "                       <img src=\"../images/coupno/coupon2.png\">\n" +
                "                   </div>\n" +
                "                   <div class=\"coupno-price\">\n" +
                "                       <div class=\"price\">¥"+coupno.couPrice+"</div>\n" +
                "                       <div class=\"price-plus\">"+coupno.couExplain+"</div>\n" +
                "                   </div>\n" +
                "                   <div class=\"coupno-condition\">"+coupno.couType+"</div>\n" +
                "               </div>\n" +
                "               <div class=\"coupno-right\">\n" +
                "    <input type=\"hidden\" value=\""+coupno.couId+"\">"+
                "<a href=\"javascript:void (0)\" id=\"cou_receive\">立即领取</a>" +
                "               </div>\n" +
                "           </div>"
            $(".coupno-receive").append(ele);

        }
    })
}


$(".special_coupno").on('click',"#cou_receive",function(event){
    var couid = event.target.previousElementSibling.value;
  //  console.log(tel)  //prev()前一个元素

    var userId =  $(".userId").val()
    // var couid =  $(".couId").prev().val()
    console.log(userId)
    if (userId == "") {
        layer.msg("请先登陆")
        return
    }
    $.ajax({
        url:"/userRecCoupon",
        type:"get",
        data:{
            userId:userId,
            couId:couid
        },
        success:function (result){
            console.log(result)
            layer.msg(result.reason)
        }
    })
})

//为领取优惠卷按钮添加点击事件
