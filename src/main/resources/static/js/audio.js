jQuery(document).ready(function () {
    var location = (window.location+'').split('/');
    var basePath = location[0]+'//'+location[2];

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

        $.ajax({
            url:basePath + "/back/stories",
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
                var dat = JSON.stringify(result);
                alert(dat);
                if(result && result.status !== 200) {

                } else {
                    console.log("上传成功");
                }
            },
            error:function(e, XMLResponse) {
                alert(XMLResponse.responseType);
                console.log(e, e.message);
                console.log("请看后台Java控制台，是否报错");
            }
        });
    });
});

