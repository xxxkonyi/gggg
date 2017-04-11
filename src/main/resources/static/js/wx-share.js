/**
 * Created by sky on 2017/4/11.
 */
wx.ready(function () {
    wx.checkJsApi({
        jsApiList: [
            'onMenuShareAppMessage',
            'onMenuShareTimeline',
            'onMenuShareQQ',
            'onMenuShareWeibo'
        ],
        success: function (res) {
            console.log(res);
        }
    });
    wx.onMenuShareAppMessage({
        title: '高原之宝',
        desc: '为健康点赞，满38个赞，您将获得高原之宝有机全脂奶粉一罐哦！',
        link: window.location.href,
        imgUrl: 'http://www.xzgyzb.com/templets/gyzb/images/logo.gif',
        trigger: function (res) {
            console.log(res);
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            console.log(res);
            alert(JSON.stringify(res));
        }
    });
    wx.onMenuShareTimeline({
        title: '高原之宝',
        desc: '为健康点赞，满38个赞，您将获得高原之宝有机全脂奶粉一罐哦！',
        link: window.location.href,
        imgUrl: 'http://www.xzgyzb.com/templets/gyzb/images/logo.gif',
        trigger: function (res) {
            console.log(res);
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            console.log(res);
            alert(JSON.stringify(res));
        }
    });
    wx.onMenuShareQQ({
        title: '高原之宝',
        desc: '为健康点赞，满38个赞，您将获得高原之宝有机全脂奶粉一罐哦！',
        link: window.location.href,
        imgUrl: 'http://www.xzgyzb.com/templets/gyzb/images/logo.gif',
        trigger: function (res) {
            console.log(res);
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            console.log(res);
            alert(JSON.stringify(res));
        }
    });
    wx.onMenuShareWeibo({
        title: '高原之宝',
        desc: '为健康点赞，满38个赞，您将获得高原之宝有机全脂奶粉一罐哦！',
        link: window.location.href,
        imgUrl: 'http://www.xzgyzb.com/templets/gyzb/images/logo.gif',
        trigger: function (res) {
            console.log(res);
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            console.log(res);
            alert(JSON.stringify(res));
        }
    });
});
