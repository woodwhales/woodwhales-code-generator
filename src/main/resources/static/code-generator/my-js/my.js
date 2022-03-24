function closeTag(index) {
    closeTagInner(index);
}

function hideItem(item, index) {
    $('#'+item).hide();
}

function showItem(item, index) {
    $('#'+item).show();
}

function updateBtnToTest() {
    $('#btnId').html('测试');
}

function updateBtnToSubmit() {
    $('#btnId').html('提交');
}

function updateBtnToGenerate() {
    $('#btnId').html('生成');
}

function generateDirAddAttr() {
    $('#generateDir').attr('lay-verify','required');
}

function myBatisPlusAddAttr() {
    $('#myBatisPlus').attr('lay-verify','otherReq');
}

function myBatisAddAttr() {
    $('#myBatis').attr('lay-verify','otherReq');
}

function removeAttr(item, index) {
    $('#'+item).removeAttr("lay-verify");
}

function sidAddAttr() {
    $('#sidDivId').show();
    $('#sid').attr('lay-verify','required');
    $('#sid').show();
}

function sidRemoveAttr() {
    $('#sid').removeAttr("lay-verify");
    $('#sid').hide();
    $('#sidDivId').hide();
}

function packageNameAddAttr() {
    $('#packageName').attr('lay-verify','required');
}

function packageNameAddAttr() {
    $('#packageName').attr('lay-verify','required');
}

function packageNameAddAttr() {
    $('#packageName').attr('lay-verify','required');
}

/**
 * 将开关文本框内容转数值
 * @param data
 */
function replaceSwitchBtnValue(data) {

    if(data.field.generateCode === "on") {
        data.field.generateCode = true;
    } else {
        data.field.generateCode = false;
    }

    if(data.field.generateMarkdown === "on") {
        data.field.generateMarkdown = true;
    } else {
        data.field.generateMarkdown = false;
    }

    if(data.field.overCode === "on") {
        data.field.overCode = true;
    } else {
        data.field.overCode = false;
    }

    if(data.field.overMarkdown === "on") {
        data.field.overMarkdown = true;
    } else {
        data.field.overMarkdown = false;
    }

    if(data.field.generateController === "on") {
        data.field.generateController = true;
    } else {
        data.field.generateController = false;
    }

    if(data.field.generateService === "on") {
        data.field.generateService = true;
    } else {
        data.field.generateService = false;
    }

    if(data.field.generateBatchMapper === "on") {
        data.field.generateBatchMapper = true;
    } else {
        data.field.generateBatchMapper = false;
    }

}