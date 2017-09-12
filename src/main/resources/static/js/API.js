(function (global,factory) {
    if (global.define){
        global.define(factory);
    } else {
        global.utils = global.utils || {};
        global.utils.API = factory();
    }
} (window, function (require) {

    var location = (window.location+'').split('/');
    var basePath = location[0]+'//'+location[2];
    var HOST = "";
    var HTTP_ADDRESS = "http://" + HOST;

    return {
        login:match(basePath + "/open/back/sublogin"),
        logout:match(basePath + "/back/logout")

    };
    // curry参数模板替换
    function match(s) {
        var reg = /{[^}]+}/;

        function __match() {
            var args = [].slice.call(arguments);

            var __s = s;
            for (var arg in args) {
                __s = __s.replace(reg, args[arg]);
            }

            function _match() {

                var _s = __s;

                for (var arg in arguments) {
                    _s = _s.replace(reg, arguments[arg]);
                }

                if (reg.test(_s)) {
                    return __match.apply(null,args.concat([].slice.call(arguments)));
                } else {
                    return _s;
                }
            }

            _match.valueOf = function () {
                return __s;
            };

            return _match;

        }

        return __match.apply(null, Array.prototype.slice.call(arguments, 1));
    }
}));