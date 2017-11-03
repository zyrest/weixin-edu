var nowPage = 1;

function deleteOne(id) {
    BootstrapDialog.show( {
        title: '危险！',
        message: '是否删除该视频？',
        buttons: [{
            label: '关闭',
            cssClass : 'btn',
            action: function(dialogRef) {
                dialogRef.close();
            }
        }, {
            label: '确定',
            cssClass : 'btn btn-warning',
            action: function(dialogRef) {
                $.ajax({
                    url : "/back/stories/" + id,
                    type : "DELETE",
                    success : function (result) {
                        alert('删除成功');
                        location.reload();
                    }
                });
                setContent(nowPage);
                dialogRef.close();
            }
        }]
    });

}
function setContent(pageNum) {
    $.ajax({
        url : "/page/stories/page/" + pageNum,
        type : "GET",
        success : function (result) {
            $('#tbody_').html("");
            var data_ = result.data;
            $.each(data_, function (i, n) {
                var id = n.id;
                var title = n.title;
                var type = n.type;
                var src = n.src;
                var is_free = n.is_free;
                var post_date = n.post_date;
                var description = n.description;

                //tr标签
                var trNode = document.createElement("tr");

                var idTd = document.createElement("td");
                $(idTd).html(id);
                idTd.style.display = "none";
                trNode.appendChild(idTd);

                var titleTd = document.createElement("td");
                $(titleTd).html(title);
                trNode.appendChild(titleTd);

                var typeTd = document.createElement("td");
                $(typeTd).html(type);
                trNode.appendChild(typeTd);

                var freeTd = document.createElement("td");
                $(freeTd).html(is_free === 1 ? "免费" : "收费");
                trNode.appendChild(freeTd);

                var dateTd = document.createElement("td");
                var date = new Date(post_date);
                $(dateTd).html(date.toLocaleString());
                trNode.appendChild(dateTd);

                var descTd = document.createElement("td");
                $(descTd).html(description);
                trNode.appendChild(descTd);

                var srcTd = document.createElement("td");
                var audioTar = document.createElement("audio");
                audioTar.setAttribute("src", src);
                audioTar.setAttribute("controls", "controls");
                srcTd.appendChild(audioTar);
                $(srcTd).val("浏览器不支持");
                trNode.appendChild(srcTd);

                var deleteTd = document.createElement("td");
                deleteTd.innerHTML = '<input type="button" class="btn btn-warning" value="删除" onclick="deleteOne(' + id + ');" />';
                trNode.appendChild(deleteTd);

                document.getElementById("tbody_").appendChild(trNode);
            })
        }
    })
}

jQuery(document).ready(function () {
    setContent(1);
});

page({
    box : 'pagination',//存放分页的容器
    count : parseInt(getPageNum()),//getPageNum(),//总页数
    num : 10,//页面展示的页码个数
    step : 6,//每次更新页码个数
    callBack:function(i){
        setContent("/page/stories/page/" + i);
        nowPage = i;
        console.log(i);
    }
});
function getPageNum() {
    var pageNum = 1;
    $.ajax({
        url : "/page/stories/pageNum",
        type : "GET",
        async : false,
        success : function (result) {
            pageNum = result.data;
        }
    });
    return pageNum;
}