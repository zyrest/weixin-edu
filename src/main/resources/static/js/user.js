
jQuery(document).ready(function() {
    var location = (window.location+'').split('/');
    var basePath = location[0]+'//'+location[2];

    //登录操作
    $('#submitLogin').click(function() {

        var username = $('input[name = m_account]').val();
        var password = $('input[name = m_password]').val();
        var tip = $('#tip');

        //todo 校检数据格式是否正确

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
                alert(result);
                var dat = JSON.stringify(result);
                alert(dat);
                if(result && result.status !== 200) {
                    tip.innerHTML = result.message;
                    $('input[name = m_password]').val('');
                } else {
                    console.log("登陆成功");
                    window.location.href = result.redirectUrl;
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