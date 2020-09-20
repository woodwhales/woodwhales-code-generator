<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<head th:fragment="header">
    <meta charset="UTF-8">
    <title>growth-help</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" th:href="@{/static/css/font.css}">
    <link rel="stylesheet" th:href="@{/static/css/xadmin.css}">
    <script th:src="@{/static/lib/layui/layui.js}" charset="utf-8"></script>
    <script type="text/javascript" th:src="@{/static/js/xadmin.js}"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>

<div class="x-nav" th:fragment="nav (navName)">
    <span class="layui-breadcrumb">
        <a th:href="@{/}">首页</a>
        <a><cite th:text="${navName}"></cite></a>
    </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i>
    </a>
</div>

</body>
</html>