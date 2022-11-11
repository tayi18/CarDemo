
function moveAnimation1(ele, target) {
    // 使用DOM元素,用定时的id值来添加DOM元素属性
    clearInterval(ele.interId);
    // 获取定时器的id
    ele.interId = setInterval(function() {
        if (ele.offsetLeft === target) {
            clearInterval(ele.interId);
            // 向右走
        } else if (ele.offsetLeft < target) {
            // 给最后一步步长做判断
            var dis = (ele.offsetLeft + 13) > target ? target : (ele.offsetLeft + 13);
            ele.style.left = dis + 'px';
            // 向左走
        } else if (ele.offsetLeft > target) {
            // 给最后一步步长做判断
            var dis = (ele.offsetLeft - 13) < target ? target : (ele.offsetLeft - 13);
            ele.style.left = dis + 'px';
        };
    }, 50);
};
function moveAnimation2(ele, target) {
    // 使用DOM元素,用定时的id值来添加DOM元素属性
    clearInterval(ele.interId);
    // 获取定时器的id
    ele.interId = setInterval(function() {
        if (ele.offsetLeft == target) {
            clearInterval(ele.interId);
        } else {
            // 向右走向左走
            var slowStep = (target - ele.offsetLeft) / 10;
            // 向右走
            // 500  0  500/10  50
            // 500  50  450/10  45
            // 500  95   40.5
            // 500 496    0.4---1
            // 向左走
            // -0.5 ---- -1
            // 整数值
            slowStep = slowStep > 0 ? Math.ceil(slowStep) : Math.floor(slowStep);
            // 496 0.4
            ele.style.left = ele.offsetLeft + slowStep + 'px';
        }
    }, 20);
};
            // 获取元素
            // ul元素
            var viewpage = document.getElementsByClassName('viewpage')[0];
            // 所有小圆点
            var circles = document.getElementsByClassName('circle');
            // 左右按钮
            var prev = document.getElementsByClassName('prev')[0];
            var next = document.getElementsByClassName('next')[0];
            // 放置图片和左右按钮的区域
            var calList = document.getElementsByClassName('cal-list')[0];
            // 排他函数
            function backcircle(ele) {
            for (var j = 0; j < ele.length; j++) {
            ele[j].className = "circle";
        };
        }

            // 1.鼠标放置到图片区域时,显示左右按钮,自动轮播停止
            // 2.鼠标离开图片区域时,左右按钮消失,自动轮播开始
            // 3.给右边按钮绑定事件,ul移动,小圆点跟随图片变化
            // 4.给左边按钮绑定事件,ul移动,小圆点跟随图片变化
            // 5.自动轮播调用右边按钮事件就可以了
            // 6.焦点轮播

            // 1.鼠标放置到图片区域时,显示左右按钮,自动轮播停止
            calList.onmouseover = function() {
            prev.style.display = "block";
            next.style.display = "block";
            // 轮播停止
            clearInterval(viewpage.autoId);
        };
            // 2.鼠标离开图片区域时,左右按钮消失,自动轮播开始
            calList.onmouseout = function() {
            prev.style.display = "none";
            next.style.display = "none";
            // 轮播开始
            autoplay();
        };
            // 3.给右边按钮绑定事件,ul移动,小圆点跟随图片变化
            // li的数量和小圆点的数量是不相同的,所以需要2个计数器,分别指代li和小圆点当前的状态

            // 设置小圆点的计数器
            var flag = 0;
            // li目标走的位置
            var count = 0;
            // 给右边按钮绑定事件
            next.onclick = function() {
            // 变成第二元素为当前的状态
            flag++;//1
            count++;//1
            // 判断flag的值
            // 回滚到flag初始0
            if (flag === circles.length) {
            flag = 0;
        };
            // 判断count的值回归
            if (count === viewpage.children.length) {
            // 拉回到left=0
            viewpage.style.left = 0;
            console.log('拉回');
            // 设置count为1,这样就可以看到第二张图片了
            count = 1;
        };

            // ①设置小圆点的样式
            // 先排他
            backcircle(circles);
            circles[flag].className = "circle active";
            // ②移动当前的ul使图片进行移动
            var target = count * (-1519);
            // 走动画
            moveAnimation2(viewpage, target);
        };
            // 4.给左边按钮绑定事件,ul移动,小圆点跟随图片变化
            prev.onclick = function() {
            // 是否是第一张图片
            if (count === 0) {
            count = viewpage.children.length -2;//最后一个下标
            viewpage.style.left = count * (-1519) + 'px';
        };
            count--;
            flag--;
            if (flag < 0) {
            flag = circles.length - 1;
        };
            // ①设置小圆点的样式
            // 先排他
            backcircle(circles);
            circles[flag].className = "circle active";
            // ②移动当前的ul使图片进行移动
            var target = count * (-1519);
            // 走动画
            moveAnimation2(viewpage, target);
        };
            // 5. 自动轮播调用右边按钮事件就可以了
            function autoplay() {
            viewpage.autoId = setInterval(function() {
                // 调用右边按钮的点击事件
                // 变成第二元素为当前的状态
                flag++;//1
                count++;//1
                // 判断flag的值
                // 回滚到flag初始0
                if (flag === circles.length) {
                    flag = 0;
                };
                // 判断count的值回归
                if (count === viewpage.children.length) {
                    // 拉回到left=0
                    viewpage.style.left = 0;
                    // console.log('拉回');
                    // 设置count为1,这样就可以看到第二张图片了
                    count = 1;
                };

                // ①设置小圆点的样式
                // 先排他
                backcircle(circles);
                circles[flag].className = "circle active";
                // ②移动当前的ul使图片进行移动
                var target = count * (-1519);
                // 走动画
                moveAnimation2(viewpage, target);
            }, 3000);
        };
            autoplay();
            // 6.焦点轮播
            // 循环给小圆点绑定事件
            for (var i = 0; i < circles.length; i++) {
            // 保存i值
            circles[i].index = i;
            // 绑定事件
            circles[i].onclick = function() {
            // 设置同步
            flag = this.index;
            count = this.index;
            console.log(flag);
            console.log(count);
            // 1.样式
            // 先排他
            backcircle(circles);
            this.className = "circle active";
            // 2.ul进行移动
            var target = this.index * (-1519);
            // 走动画
            moveAnimation2(viewpage, target);
        }
            };

/*推荐车辆*/
showRecommend();
function showRecommend(){
    var url = "/recommend/recommendByUser";
    var token = localStorage.getItem("token");
    $.ajax({
        type: "get",
        url: url,
        headers: {'token': token},
        success: function (result) {
            for (var i=0; i<result.length;i++) {
                var data = result[i];
                carName = data.carName;
                carId = data.carId;
                carImg = data.carImg;
                carPrice = data.carPrice;
                var recomendEle = "<div class=\"fq-car\">\n" +
                    "                                <div class=\"right-middle\">\n" +
                    "                                    <a href=\"/details/"+carId+"\">\n" +
                    "                                        <img src=\""+carImg+"\">\n" +
                    "                                    </a>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"right-under\">\n" +
                    "                                    <a href=\"javascript:void (0)\" class=\"to-buy\">\n" +
                    "                                     <div class=\"name\">"+carName+"</div>\n" +
                    "                                     <div class=\"price\">\n" +
                    "                                         <div class=\"tejia\">\n" +
                    "                                             特价卖\n" +
                    "                                         </div>\n" +
                    "                                         <div class=\"price-real\">\n" +
                    "                                             <a>¥</a>\n" +
                    "                                             <a>"+carPrice+"</a>\n" +
                    "                                         </div>\n" +
                    "                                     </div>\n" +
                    "                                    </a>\n" +
                    "                                </div>\n" +
                    "                            </div>";
                $(".recommend").append(recomendEle);

            }
        }
    })
}
