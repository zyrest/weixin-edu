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
                    url : "/back/interviewVideos/" + id,
                    type : "DELETE",
                    success : function (result) {
                        alert(JSON.stringify(result)) //todo
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
        url : "/page/interviewVideos/page/" + pageNum,
        type : "GET",
        success : function (result) {
            $('#tbody_').html("");
            var data_ = result.data;
            $.each(data_, function (i, n) {
                var id = n.id;
                var title = n.title;
                var stage = n.stage;
                var src = n.src;
                var is_free = n.is_free;
                var post_date = n.post_date;
                var description = n.description;

                //tr标签
                var html = '<tr>';

                html += '<td style="display: none">';
                html += id ;
                html += '</td>';

                html += '<td>';
                html += title ;
                html += '</td>';

                html += '<td>';
                html += stage ;
                html += '</td>';

                html += '<td>';
                html += is_free === 1 ? "免费" : "收费" ;
                html += '</td>';

                html += '<td>';
                html += new Date(post_date).toLocaleString();
                html += '</td>';

                html += '<td>';
                html += description ;
                html += '</td>';

                html += '<td>';
                html += '<input type="button" class="btn btn-primary" value="查看" onclick="viewVideo(\'' + src + '\');" />';
                html += '</td>';

                html += '<td>';
                html += '<input type="button" class="btn btn-warning" value="删除" onclick="deleteOne(' + id + ');" />';
                html += '</td>';

                $("#tbody_").append($(html));
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
        setContent("/page/interviewVideos/page/" + i);
        nowPage = i;
        console.log(i);
    }
});
function getPageNum() {
    var pageNum = 1;
    $.ajax({
        url : "/page/interviewVideos/pageNum",
        type : "GET",
        async : false,
        success : function (result) {
            pageNum = result.data;
        }
    });
    return pageNum;
}

function viewVideo(src) {

    var content__ = '<div style="margin:auto;">';
    content__ += '<p align="center">';
    content__ += '<video src="' + src +'" controls="controls" style="object-fit: fill">浏览器不支持</video>';
    content__ += '</p>';
    content__ += '</div>';

    BootstrapDialog.show( {
        title: '查看视频',
        message: $(content__),
        cssClass : 'videoClass',
        buttons: [{
            label: '关闭',
            action: function(dialogRef) {
                dialogRef.close();
            }
        }]
    });
}