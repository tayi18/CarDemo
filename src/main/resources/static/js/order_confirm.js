var oBox= document.querySelector('.time_dao');
var maxtime = 15 * 60;
function CountDown() {
    if (maxtime >= 0) {
        minutes = Math.floor(maxtime / 60);
        seconds = Math.floor(maxtime % 60);
        msg = minutes + "分" + seconds + "秒，逾期订单将提交失败。";
        oBox.innerHTML = msg;
        --maxtime;
    } else{
        clearInterval(timer);
        alert("已逾期，订单提交失败！");
    }
}
timer = setInterval("CountDown()", 1000);

