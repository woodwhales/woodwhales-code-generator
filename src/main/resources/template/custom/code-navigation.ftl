<!doctype html>
<html class="x-admin-sm" xmlns:th="http://www.thymeleaf.org">
<head>
    <div th:replace="~{common/common::header}"></div>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a th:href="@{/}">WOODWHALES-CODE-GENERATOR</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">admin</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd><a onclick="xadmin.open('个人信息','https://www.woodwhales.cn')">个人信息</a></dd>
                <dd><a onclick="xadmin.open('切换帐号','https://www.woodwhales.cn')">切换帐号</a></dd>
                <dd><a href="./login.html">退出</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item to-index">
            <a th:href="@{/}">前台首页</a>
        </li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <!-- 数据库名称 -->
                    <i class="iconfont left-nav-li" lay-tips="${todo数据库名称}">&#xe723;</i>
                    <cite>${todo数据库名称}</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul>
                    <!-- TODO 循环遍历菜单-->
                    <li>
                        <a onclick="xadmin.add_tab('tab标题','tab链接',true)">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>cite名称</cite></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面</li></ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd></dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='./welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
</body>
<script th:inline="none">
    var openPage = function(pageTitle, pageUrl) {
        xadmin.add_tab(pageTitle, pageUrl,true);
    }

    layui.use(['util', 'layer'], function(){
        var util = layui.util,
            layer = layui.layer;

        util.fixbar({
            bar1: '<i class="layui-icon layui-icon-util" style="font-size: 40px;"></i>',
            css: {left: 10}
            ,click: function(type){
                if(type === 'bar1'){
                    layer.open({
                        type: 1,
                        shade: false,
                        area: ['240px', '230px'],
                        scrollbar: false,
                        moveOut: true,
                        title : '小黑板',
                        content: '<div class="layui-fluid">\n' +
                            '    <form class="layui-form" action="">\n' +
                            '        <textarea name="desc" class="layui-textarea"></textarea></br>\n' +
                            '        <button type="reset" class="layui-btn layui-btn-xs layui-btn-fluid">清空</button>\n' +
                            '    </form>\n' +
                            '</div>',
                        offset: [
                            ($(window).height()-10-300)
                            ,10
                        ],
                        zIndex: layer.zIndex,
                        success: function(layero) {
                            layer.setTop(layero);
                        }
                    });
                }
            }
        });
    });
</script>
</html>