
//  优惠活动
    $(".select").click(function () {
        $(".coupon").show();
        $(".new_user").hide();
    })

    $(".other_select").click(function () {
        $(".coupon").hide();
        $(".new_user").show();
    })

    $(".oth").click(function () {
        $(".new_user").hide();
        $(".coupon").hide();
    })

    $(".change_img").click(function () {

        $(".expenses").show();
    })

//优惠券
    $(document).ready(function () {
        if ($(".oth").click(function () {
            document.querySelector(".t_price").innerText =
                parseFloat(document.querySelector(".price_one").innerText) +
                parseFloat(document.querySelector(".price_two").innerText) +
                parseFloat(document.querySelector(".expenses_p").innerText) ;
        }))

        $(".coupon1").click(function () {
            $(".coupon1").css({"background": "lightgrey"});

            $(this).css({"background": "orange"});
            var cou_money = $(this).find(".cou_money").text();
            var couId = $(this).find(".couId").val();
            var couMax = $(this).find(".couMax").val();
            $("#couId").val(couId);
            console.log($("#couId").val(couId))

            console.log(parseFloat(document.querySelector(".t_price").innerText));

            //优惠券使用条件判断
            if (parseFloat(document.querySelector(".t_price").innerText) < couMax
            ){
                $("#ordCouMoney").val(0);
                $(this).css({"background": "lightgrey"});
            }else{
                $("#ordCouMoney").val(cou_money);
            }

            document.querySelector(".t_price").innerText =
                parseFloat(document.querySelector(".price_one").innerText) +
                parseFloat(document.querySelector(".price_two").innerText) +
                parseFloat(document.querySelector(".expenses_p").innerText) -
                $("#ordCouMoney").val();


        });






    })

function qq() {
    $(".t_price").val(document.querySelector(".t_price").innerText);
    console.log($(".t_price").val())

}



select();
    function select() {
        var url = "/coupon";
        var token = localStorage.getItem("token");
        console.log(token);
        $.ajax({
            type:"get",
            url:url,
            async:false,
            headers:{'token':token},
            success:function (result) {
                var coupon = result.data;
                //var userId = coupon[0].userId;
                console.log(coupon)
                //console.log(userId)

                for (var i=0; i<coupon.length;i++) {
                    var coupon1 = coupon[i];
                    var couId = coupon1["couId"];
                    var couPrice = coupon1["couPrice"];
                    //过期时间
                    var userCouEnd = coupon1["userCouEnd"];
                    var couExplain = coupon1["couExplain"];
                    //生效时间
                    var userCouStart = coupon1["userCouStart"];
                    //使用状态
                    var userCouState = coupon1["userCouState"];
                    //使用条件
                    var couMax = coupon1["couMax"];

                    var couponEle = "<div class=\"coupon1\">\n" +"" +
                        "<input type=\"hidden\" value=\" " + userCouStart + " \" name=\"userCouStart\" class=\"userCouStart\">\n" +
                        "    <input type=\"hidden\" value=\" " + userCouState + " \" name=\"userCouState\" class=\"userCouState\">\n" +
                        "    <input type=\"hidden\" value=\" " + couMax + " \" name=\"couMax\" class=\"couMax\">\n" +
                        "<input type=\"hidden\" value=\" " + couId + " \" name=\"couId\" class=\" couId \">" +
                        "                        <h1>￥<span class=\"cou_money\">" + couPrice + "</span></h1>\n" +
                        "                        <p>有效期至<span>" + userCouEnd + "</span></p>\n" +
                        "                        <div class=\"part_coupon1\">\n" +
                        "                            <h5>" + couExplain + "</h5>\n" +
                        "                        </div>\n" +
                        "                    </div>"
                    $(".coupon").append(couponEle);
                }
            }
        })

    }

console.log($("#couId").val()+"123456")

console.log()


function sdc(){
    var userId = $(".userId").val();
    console.log(userId)
    $("#userId").val(userId);
    console.log($("#userId").val())
}
sdc();


