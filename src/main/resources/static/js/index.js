layui.use('laydate', function(){
    var laydate = layui.laydate;
  //自定义格式
  laydate.render({
    elem: '#test6'
    //设置开始日期、日期日期的 input 选择器
    //数组格式为 2.6.6 开始新增，之前版本直接配置 true 或任意分割字符即可
    ,range: ['#test-startDate-1', '#test-endDate-1']
  });
    //限定可选时间
    laydate.render({
        elem: '#test-limit3'
        ,type: 'time'
        ,min: '09:30:00'
        ,max: '17:30:00'
        ,btns: ['clear', 'confirm']
      });
    });

    
$("#test11").change(selectTime)
function selectTime(){
    var laydate = layui.laydate;
    console.log("aa")
    console.log($("#test11").val())
    console.log( $(".layui-laydate-preview").text())
      //限定可选日期
  var ins22 = laydate.render({
    elem: '#test-limit1'
    ,min: '2019-10-19'
    ,max: '2080-10-14',
  });
  
}
 document.querySelector(".area").onclick = function showall(){
  var e = document.getElementById("showAll")
  console.log(e)
  if(e.style.display == 'none'){
    e.style.display = 'block'
    
    // e.style.z-index == 9999;
  }else{
    e.style.display = 'none'
    // e.style.
  }
  console.log("show")
  // document.querySelector(".show").className += " showAll"
}

document.querySelector(".area1").onclick = function showall1(){
  var e = document.querySelector(".showAll1")
  console.log(e)
  if(e.style.display == 'none'){
    e.style.display = 'block'
    
    // e.style.z-index == 9999;
  }else{
    e.style.display = 'none'
    // e.style.
  }
  console.log("show")
  // document.querySelector(".show").className += " showAll"
}

// ==========地区选择============
var json = {
    "苏州": [
        {"虎丘区": ["啡尝不可店", "随行店"]},
        {"工业园区": ["随行店1", "啡尝不可店"]},
        {"姑苏区": ["随行店2", "啡尝不可店1"]},
        {"虎丘区": ["随行店3", "啡尝不可店2"]},
        {"相城区": ["随行店4", "啡尝不可店3"]},
        {"吴江区": ["随行店5", "啡尝不可店4"]},
    ],
    "成都": [
      {"靖江去": ["啡尝不可店", "随行店"]},
      {"青羊区": ["随行店1", "啡尝不可店"]},
      {"金牛区": ["随行店2", "啡尝不可店1"]},
      {"武侯区": ["随行店3", "啡尝不可店2"]},
      {"成华区": ["随行店4", "啡尝不可店3"]},
      {"双流区": ["随行店5", "啡尝不可店4"]},
  ]
};

var sheng = document.getElementById("select-city");

            for (var i in json) { //使用for  in 迭代对象，变量i就是key
                /*  console.log(i); */
                var shengOpt = document.createElement("a");
                shengOpt.href = "javascript:void(0)"
                shengOpt.id = "city"
                shengOpt.appendChild(document.createTextNode(i));
                shengOpt.value = i;
                sheng.appendChild(shengOpt);
            }
//城市点击事件

document.querySelector(".select-city").onclick = function(event){
// var workArray = document.querySelector(".select").children;
var element = event.target;
var name = element.innerText;
var workArray =  document.querySelector(".select-city").children
for(var i = 0;i<workArray.length;i++){
    workArray[i].className = " "
}
 element.className = "active"
var shiArray = json[name]; //获取苏州对应的区名
var shi = document.getElementById("select-area");
shi.innerHTML = " ";
// var shiOpt = document.createElement("a");
for (let s of shiArray) {
    for (var i in s) {
        /* console.log(i); */
        var shiOpt = document.createElement("a");
        shiOpt.href = "javascript:void(0)"
        shiOpt.id = "area"
        shiOpt.appendChild(document.createTextNode(i));
        shiOpt.value = i;
        shi.appendChild(shiOpt);
    }
}
document.querySelector(".select-area").className+=" botto"
}
//区点击事件
document.querySelector(".select-area").onclick = function(event){
var workArray = document.querySelector(".select-area").children;
var element = event.target;
var name = element.innerText;
var city =   document.querySelector(".select-city .active").innerText //获取市名
var shi = json[city];   //获取市对应的区
var qu = document.getElementById("select-store");
qu.innerHTML = " ";
//添加请选择
var quOpt = document.createElement("a");
for (var item of shi) {
    var quval = item[name];
    if(quval){
        for (var item of quval) {
            var quOpt = document.createElement("a");
            quOpt.appendChild(document.createTextNode(item));
            quOpt.value = item;
            qu.appendChild(quOpt);
        }
    }
}
element.className = "active"
document.querySelector(".select-store").className+=" botto"
}
document.querySelector(".select-store").onclick = function(event){
  var city =   document.querySelector(".select-city .active").innerText
   document.querySelector(".left .area").value =  city
   event.target.className = "active"
  var e = document.getElementById("showAll")
  e.style.display = 'none'
  document.querySelector(".left .in-store").value =  event.target.innerText;
  
}




// ==========还车地点
var sheng1 = document.getElementById("select-city1");

            for (var i in json) { //使用for  in 迭代对象，变量i就是key
                /*  console.log(i); */
                var shengOpt = document.createElement("a");
                shengOpt.href = "javascript:void(0)"
                shengOpt.id = "city"
                shengOpt.appendChild(document.createTextNode(i));
                shengOpt.value = i;
                sheng1.appendChild(shengOpt);
            }
//城市点击事件

document.querySelector(".select-city1").onclick = function(event){
    // var workArray = document.querySelector(".select").children;
    var element = event.target;
    var name = element.innerText;
    var workArray =  document.querySelector(".select-city1").children
    for(var i = 0;i<workArray.length;i++){
        workArray[i].className = " "
    }
    element.className = "active"
    var shiArray = json[name]; //获取苏州对应的区名
    var shi = document.getElementById("select-area1");
    shi.innerHTML = " ";
    // var shiOpt = document.createElement("a");
    for (let s of shiArray) {
        for (var i in s) {
            /* console.log(i); */
            var shiOpt = document.createElement("a");
            shiOpt.href = "javascript:void(0)"
            shiOpt.id = "area"
            shiOpt.appendChild(document.createTextNode(i));
            shiOpt.value = i;
            shi.appendChild(shiOpt);
        }
    }
    document.querySelector(".select-area1").className+=" botto"
}

//区点击事件
document.querySelector(".select-area1").onclick = function(event){
var workArray = document.querySelector(".select-area1").children;
var element = event.target;
var name = element.innerText;
var city =   document.querySelector(".select-city1 .active").innerText //获取市名
var shi = json[city];   //获取市对应的区
var qu = document.getElementById("select-store1");
qu.innerHTML = " ";
//添加请选择
var quOpt = document.createElement("a");
for (var item of shi) {
    var quval = item[name];
    if(quval){
        for (var item of quval) {
            var quOpt = document.createElement("a");
            quOpt.appendChild(document.createTextNode(item));
            quOpt.value = item;
            qu.appendChild(quOpt);
        }
    }
}
element.className = "active"
document.querySelector(".select-store1").className+=" botto"
}
document.querySelector(".select-store1").onclick = function(event){
  var city =   document.querySelector(".select-city1 .active").innerText
   document.querySelector(".left1 .area1").value =  city
   event.target.className = "active"
  var e = document.getElementById("showAll1")
  e.style.display = 'none'
  console.log(event.target.innerText)
  document.querySelector(".in-store1").value =  event.target.innerText;
}


