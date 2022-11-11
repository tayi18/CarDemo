$(".order_success_button").click(function () {
    layer.alert('是否已还车，并告知商家', {
        skin: 'layui-layer-molv' //样式类名 自定义样式
        , closeBtn: 1 // 是否显示关闭按钮
        , title : '确认还车！！' //标题
        , anim: 1 //动画类型
        , btn: ['确定', '取消'] //按钮
        , yes: function() {//这里也可以写响应的ajax请求
            var ordNumber = $("#ordNumber").val();

            var url = "/successOrder";
            $.ajax({
                type: "post",
                url: url,
                data:{
                    "ordNumber":ordNumber
                },
                success:function (response){

                    // console.log("sadsdas"+response.data)

                    console.log("成功");
                    // location.reload();
                    layer.closeAll();
                    layer.msg('还了')
                    displayWindow()


                }
            })


        }
        , btn2: function () {
            layer.msg('已取消');
        }
    })
});


// $(".comm-btn").click(function (){
//     saveComments()
// })
function saveComments(){
    var ordNumber = $("#ordNumber").val()
    var userId = $(".userId").val()
    var commContent = $(".comm-box").val()
    console.log(ordNumber)
    $.ajax({
        type: "post",
        url:"/saveComments",
        data: {
            ordNumber:ordNumber,
            userId:userId,
            commContent:commContent
        },
        success: function (commentResponse){
            if (commentResponse.resultcode == 200){
                layer.msg("评论成功")
                location.reload()
            }
        }
    })
}

function jixuzhifu() {
    if ($(".status").text() !== "预约中"){
        layer.msg("该订单不可继续支付")

    }

}