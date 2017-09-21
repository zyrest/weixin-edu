
var basePath ;
jQuery(document).ready(function () {
    var loc = (window.location+'').split('/');
    basePath = loc[0]+'//'+loc[2]
});

function fileSelected() {
    var file = document.getElementById('audio').files[0];
    if (file) {
        var fileSize = 0;
        if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
        else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

        $('#photoCover').val(file.name);
        document.getElementById('fileSize').innerHTML = '大小: ' + fileSize;
        document.getElementById('fileType').innerHTML = '类型: ' + file.type;
    }
}

$('#submitAudio').click(function() {
    var title = $('#title').val();
    var description = $('#description').val();
    var is_free = $('input[name="is_free"]:checked').val();
    var type = $('#type').find('option:selected').val();
    var audio = $('#audio')[0].files[0];

    var formData = new FormData();
    formData.append("title", title);
    formData.append("description", description);
    formData.append("is_free", is_free);
    formData.append("type", type);
    formData.append("audio", audio);

    // var data = {title:title, description:description, is_free:is_free, type:type, audio:formData};
    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", basePath + "/back/stories");
    xhr.send(formData);
    // $.ajax({
    //     url:basePath + "/back/stories",
    //     data:formData,
    //     type:"post",
    //     // // 告诉jQuery不要去处理发送的数据
    //     // processData : false,
    //     // // 告诉jQuery不要去设置Content-Type请求头
    //     // contentType : false,
    //     beforeSend:function() {
    //         console.log("开始上传")
    //     },
    //     success:function(result) {
    //         var dat = JSON.stringify(result);
    //         alert(dat);
    //         if(result && result.status !== 200) {
    //
    //         } else {
    //             console.log("上传成功");
    //         }
    //     },
    //     error:function(e, XMLResponse) {
    //         alert(XMLResponse.responseType);
    //         console.log(e, e.message);
    //         console.log("请看后台Java控制台，是否报错");
    //     }
    // });
});

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
    }
    else {
        document.getElementById('progressNumber').innerHTML = '无法计算';
    }
}

function uploadComplete(evt) {
    /* 当服务器响应后，这个事件就会被触发 */
    alert(evt.target.responseText);
}

function uploadFailed(evt) {
    alert("上传文件发生了错误尝试");
}

function uploadCanceled(evt) {
    alert("上传被用户取消或者浏览器断开连接");
}


