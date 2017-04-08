/**
 * Created by sky on 2017/4/8.
 */
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

var Pagination = function () {
    var that = this;
    var prev = function () {
        query();
        if (page > 0) {
            page--;
            window.location.search = '?page=' + page + '&size=' + size;
        }
    };
    var next = function () {
        query();
        if (page < that.totalPages - 1) {
            page++;
            window.location.search = '?page=' + page + '&size=' + size;
        }
    };
    var query = function () {
        that.page = getQueryString("page");
        that.size = getQueryString("size");
        if (null == that.page) {
            that.page = 0;
        }
        if (null == that.size) {
            that.size = 20;
        }
    };
    return {
        init: function (size, totalPages) {
            that.totalPages = totalPages;
            that.size = size;
            query();
        },
        prev: function () {
            prev();
        },
        next: function () {
            next();
        },
        isTop: function () {
            return that.page == 0;
        },
        isBottom: function () {
            return that.page + 1 >= that.totalPages;
        }
    }
}();

