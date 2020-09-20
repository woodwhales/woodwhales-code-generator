function closeTag(index) {
    closeTagInner(index);
}

function schemaDivIdHide() {
    $('#schemaDivId').hide();
}

function schemaDivIdShow() {
    $('#schemaDivId').show();
}

function pageNameAndPathIdHide() {
    $('#pageNameAndPathId').hide();
}

function pageNameAndPathIdShow() {
    $('#pageNameAndPathId').show();
}

function superClassDivIdHide() {
    $('#superClassDivId').hide();
}

function superClassDivIdShow() {
    $('#superClassDivId').show();
}

function interfaceDivIdHide() {
    $('#interfaceDivId').hide();
}

function interfaceDivIdShow() {
    $('#interfaceDivId').show();
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

function tableInfosDivShow() {
    $('#tableInfosDiv').show();
}

function tableInfosDivHide() {
    $('#tableInfosDiv').hide();
}

function tableInfoLabelIdShow() {
    $('#tableInfoLabelId').show();
}

function tableInfoLabelIdHide() {
    $('#tableInfoLabelId').hide();
}

function generateDirAddAttr() {
    $('#generateDir').attr('lay-verify','required');
}

function generateDirRemoveAttr() {
    $('#generateDir').removeAttr("lay-verify");
}

function packageNameRemoveAttr() {
    $('#packageName').removeAttr("lay-verify");
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

}