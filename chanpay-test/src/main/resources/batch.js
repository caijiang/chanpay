/**
 * 此文件是为了检测到底发生了什么事
 * Created by CJ on 9/30/16.
 */
$.ajaxSetup({async: false});

var _ajax = $.ajax;

$.ajax = function (param) {
    var _success = param.success;
    param.success = function (response) {
        console.error('[DEBUG]', response);
        _success(response);
    };
    _ajax(param);
};
