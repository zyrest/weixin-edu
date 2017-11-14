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

function _videoClick() {
    var clickV = $('#clickV');
    var clickR = $('#clickR');
    $('#_video').show();
    $('#_review').hide();
    clickV.removeClass("btn-default");
    clickV.addClass("btn-primary");
    clickR.removeClass("btn-primary");
    clickR.addClass("btn-default");
}

function _reviewClick() {
    var clickV = $('#clickV');
    var clickR = $('#clickR');
    $('#_video').hide();
    $('#_review').show();
    clickR.removeClass("btn-default");
    clickR.addClass("btn-primary");
    clickV.removeClass("btn-primary");
    clickV.addClass("btn-default");
}
function fileSelected() {
    var file = document.getElementById('video').files[0];
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
    var title = $('#v_title').val();
    var stage = $('#v_stage').val();
    var description = $('#v_description').val();
    var is_free = $('input[name="v_is_free"]:checked').val();
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
        error: function(result){

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
        progress: function(result){
            if (result.curr) {
                var percentComplete = Math.round(result.curr * 100);
                document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
            } else {
                document.getElementById('progressNumber').innerHTML = '无法计算';
            }
        },
        finish: function(result){
            var formData = new FormData();
            formData.append("title", title);
            formData.append("stage", stage);
            formData.append("description", description);
            formData.append("is_free", is_free);
            formData.append("fileid", result.fileId);
            // formData.append("videoName", result.videoName);
            formData.append("src", result.videoUrl);

            var xhr = new XMLHttpRequest();
            // xhr.upload.addEventListener("progress", uploadProgress, false);
            // xhr.addEventListener("load", uploadComplete, false);
            // xhr.addEventListener("error", uploadFailed, false);
            // xhr.addEventListener("abort", uploadCanceled, false);
            xhr.open("POST", basePath + "/back/enVideos");
            xhr.send(formData);
        }
    });

});

$('#submitReview').click(function () {
    var title = $('#r_title').val();
    var stage = $('#r_stage').val();
    var description = $('#r_description').val();
    var is_free = $('input[name="r_is_free"]:checked').val();
    var content = $('#r_content').val();

    var formData = new FormData();
    formData.append("title", title);
    formData.append("description", description);
    formData.append("is_free", is_free);
    formData.append("stage", stage);
    formData.append("content", content);

    $.ajax({
        url:basePath + "/back/enReviews",
        data:formData,
        type:"post",
        // 告诉jQuery不要去处理发送的数据
        processData : false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        beforeSend:function() {
            console.log("开始上传")
        },
        success:function(result) {
            // var dat = JSON.stringify(result);
            if(result && result.status === 201) {
                // alert("..." + JSON.stringify(result))
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
            } else {
                BootstrapDialog.show( {
                    title : '警告！',
                    message: '上传文件时服务器发生了错误！',
                    type: BootstrapDialog.TYPE_WARNING,
                    buttons: [{
                        label: '确认',
                        action: function(dialogRef) {
                            dialogRef.close();
                        }
                    }]
                });
            }
        },
        error:function(e, XMLResponse) {
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
            console.log(e, e.message);
            console.log("请看后台Java控制台，是否报错");
        }
    });
});

function clearInputs() {
    $('#v_title').val('');
    $('#v_stage').val('');
    $('#v_description').val('');
    $('#photoCover').val('');
    $('#fileSize').html('');
    $('#fileType').html('');
    $('#progressNumber').html('');
    $('#r_title').val('');
    $('#r_stage').val('');
    $('#r_description').val('');
    $('#r_content').val('');
}
