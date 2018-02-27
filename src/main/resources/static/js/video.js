var ue = UE.getEditor("description");

/**
 * 计算签名
 **/
var getSignature = function(callback){
    $.ajax({
        url: '/cloud/signature',
        type: 'GET',
        dataType: 'json',
        success: function(res){
            if(res && res.data) {
                callback(res.data);
            } else {
                return '获取签名失败';
            }

        }
    });
};

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
    // alert("start");
    var title = $('#title').val();
    var stage = $('#stage').val();
    var description = ue.getContent();
        // $('#description').val();
    var is_free = $('input[name="is_free"]:checked').val();
    var video = $('#video')[0].files[0];

    qcVideo.ugcUploader.start({
        videoFile: video,
        getSignature: getSignature,
        success: function(result){

            BootstrapDialog.show( {
                title : '消息',
                message: '上传成功',
                buttons: [{
                    label: '确认',
                    action: function(dialogRef) {
                        clearInputs();
                        dialogRef.close();
                    }
                }]
            });
        },
        error: function(result) {
            // alert(JSON.stringify(result));
            BootstrapDialog.show( {
                title : '警告！',
                message: '上传文件发生了错误尝试，原因为：' + result.msg + '，上传文件类型为：' + result.type,
                type: BootstrapDialog.TYPE_DANGER,
                buttons: [{
                    label: '确认',
                    action: function(dialogRef) {
                        dialogRef.close();
                    }
                }]
            });
        },
        progress: function(result) {
            if (result.curr) {
                var percentComplete = Math.round(result.curr * 100);
                document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
            } else {
                document.getElementById('progressNumber').innerHTML = '无法计算';
            }
        },
        finish: function(result) {
            var formData = new FormData();
            formData.append("title", title);
            formData.append("stage", stage);
            formData.append("description", description);
            formData.append("is_free", is_free);
            formData.append("fileid", result.fileId);
            // formData.append("videoName", result.videoName);
            formData.append("src", result.videoUrl);

            // alert("success");
            var xhr = new XMLHttpRequest();
            // xhr.upload.addEventListener("progress", uploadProgress, false);
            // xhr.addEventListener("load", uploadComplete, false);
            // xhr.addEventListener("error", uploadFailed, false);
            // xhr.addEventListener("abort", uploadCanceled, false);
            xhr.open("POST", basePath + "/back/interviewVideos");
            xhr.send(formData);
        }
    });
});

function clearInputs() {
    $('#title').val('');
    $('#stage').val('');
    $('#description').val('');
    $('#photoCover').val('');
    $('#fileSize').html('');
    $('#fileType').html('');
    $('#progressNumber').html('');
}


