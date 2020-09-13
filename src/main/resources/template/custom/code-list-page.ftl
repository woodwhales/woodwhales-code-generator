<!DOCTYPE html>
<html class="x-admin-sm" xmlns:th="http://www.thymeleaf.org">

<head>
    <div th:replace="~{common/common::header}"></div>
</head>

<body>
<#--TODO 设置导航名-->
<div th:replace="~{common/common::nav (navName ='${todo}')}"></div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <#--TODO 循环输出搜索框配置-->
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="${todo}" id="${todo}" placeholder="${todo}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="search"> <i class="layui-icon">&#xe615;</i></button>
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="resetBtn"><i class="layui-icon">&#xe666;</i></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script th:inline="none">
    layui.use(['table', 'form'], function() {
        var table = layui.table,
            form = layui.form;

        table.render({
            elem: '#tableList'
            <#--TODO table链接-->
            ,url: '${todo}'
            ,id: 'tableListId'
            ,limit: 20
            ,cols: [[
                <#--TODO 循环输出table列表配置-->
                {field:'${todo}', title: '${todo}'}
            ]]
            ,page: true
        });

        form.on('submit(search)', function(data){
            table.reload('tableListId', {
                page: {
                    curr: 1
                }
                ,where: data.field
            });
            return false;
        });

        form.on('submit(resetBtn)', function(data) {
            <#--TODO 循环输出重置搜索框配置-->
            $('${todo}').val("");
            table.reload('tableListId', {
                page: {
                    curr: 1
                }
                ,where: {
                    <#--TODO 循环输出重置搜索框配置-->
                    '${todo}': '',
                }
            });
            return false;
        });

    });
</script>

</html>