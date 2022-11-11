//btn();
function btn() {
    var t1 =  $(".t1").val()
    var t2 =  $(".t2").val()
    var t3 =  $(".t3").val()
    var t4 =  $(".t4").val()
    var start = t1 + " " + t2;
    var end = t3 + " " + t4;
    console.log(start)
    console.log(end)
    console.log(t1)
    console.log(t2)
    console.log(t3)
    console.log(t4)
    if (t1 == '' || t2 == '' ||t3 == '' ||t4 == ''){
        layer.msg("请先选择时间")

    }

    var carId =  $(".carId").val()
    console.log(carId)

    $("#start").val(start)
    $("#end").val(end)
    $("#carId1").val(carId)



}
