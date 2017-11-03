
var basePath ;
jQuery(document).ready(function () {
    var loc = (window.location+'').split('/');
    basePath = loc[0]+'//'+loc[2]
});

function fileSelected() {
    var file = document.getElementById('video').files[0];
    // alert(file.name);
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

$('#submitVideo').click(function() {
    var title = $('#title').val();
    var stage = $('#stage').val();
    var description = $('#description').val();
    var is_free = $('input[name="is_free"]:checked').val();
    var video = $('#video')[0].files[0];

    var formData = new FormData();
    formData.append("title", title);
    formData.append("stage", stage);
    formData.append("description", description);
    formData.append("is_free", is_free);
    formData.append("video", video);

    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", basePath + "/back/interviewVideos");
    xhr.send(formData);

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
    // alert(evt.target.responseText);
    var result = JSON.parse(evt.target.responseText);
    var mes = '服务器发生错误！请确保上传文件为mp4格式！！';
    if (result && result.status === 201) mes = '上传成功';

    BootstrapDialog.show( {
        title : '消息',
        message: mes,
        buttons: [{
            label: '确认',
            action: function(dialogRef) {
                if (result && result.status === 201) clearInputs();
                dialogRef.close();
            }
        }]
    });
}

function uploadFailed(evt) {
    // alert("上传文件发生了错误尝试");
    BootstrapDialog.show( {
        title : '警告！',
        message: '上传文件发生了错误尝试',
        type: BootstrapDialog.TYPE_DANGER,
        buttons: [{
            label: '确认',
            action: function(dialogRef) {
                dialogRef.close();
            }
        }]
    });
}

function uploadCanceled(evt) {
    // alert("上传被用户取消或者浏览器断开连接");
    BootstrapDialog.show( {
        title : '取消',
        type: BootstrapDialog.TYPE_WARNING,
        message: '上传被用户取消或者浏览器断开连接',
        buttons: [{
            label: '确认',
            action: function(dialogRef) {
                dialogRef.close();
            }
        }]
    });
}

function clearInputs() {
    $('#title').val('');
    $('#stage').val('');
    $('#description').val('');
    $('#photoCover').val('');
    $('#fileSize').html('');
    $('#fileType').html('');
    $('#progressNumber').html('');
}


