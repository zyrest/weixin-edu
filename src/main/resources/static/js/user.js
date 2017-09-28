
$(function() {
    var location = (window.location+'').split('/');
    var basePath = location[0]+'//'+location[2];
    var submit = $('#submitLogin');

    //回车事件绑定
    document.onkeydown = function(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode === 13) {
            submit.click();
        }
    };

    //
    document.onkeyup = function () {
        var username = $('input[name = m_account]').val();
        var password = $('input[name = m_password]').val();

        if (username.length === 0 || password.length === 0) {
            submit.attr('disabled',"true");
        } else {
            submit.removeAttr("disabled");
        }
    };

    //登录操作
    submit.click(function() {
        var username = $('input[name = m_account]').val();
        var password = $('input[name = m_password]').val();

        var data = JSON.stringify({m_account:username, m_password:password});
        $.ajax({
            url:basePath + "/open/back/sublogin",
            data:data,
            dataType:'json',
            type:"post",
            contentType: "application/json",
            beforeSend:function() {
                console.log("开始登陆")
            },
            success:function(result) {
                // alert(result);
                // var dat = JSON.stringify(result);
                // alert(dat);
                if(result && result.status !== 200) {
                    BootstrapDialog.show({
                        message: result.message
                    });
                    $('input[name = m_password]').val('');
                } else {
                    console.log("登陆成功");
                    window.location.href = result.redirectUrl;
                }
            },
            error:function(e, XMLResponse) {
                BootstrapDialog.alert('登陆失败，请重试');
                console.log(e, e.message);
                console.log("请看后台Java控制台，是否报错");
            }
        });
    });
});