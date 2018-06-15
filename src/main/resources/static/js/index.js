var index = new Object();

/**
 * @author summer
 * @date 2018/6/15 下午6:23
 * @param null
 * @return null
 * @description 表前缀模态框显示时触发的方法
 */
$('#tablePrefixModal').on('show.bs.modal', function (e) {
    // 获取数据库配置
    var dataSourceConfig = iform.parseForm("dataSourceConfig");
    // 转换成 json 格式的字符串
    var dataResult = JSON.stringify(dataSourceConfig);

    // 执行请求,
    $.ajax({
        type: "POST",
        url: "/prefix",
        contentType:"application/json",
        dataType:"JSON",
        data: dataResult,
        success: function(data){
            var checkResult = index.showResult(data);
            if(checkResult){
                var prefixSet = data.data;
                var modalContent = index.mackTablePrefixModal(prefixSet);
                $("#tablePrefixModalBody").html(modalContent);
            }
        },
        error(xhr,status,error){
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
});

$('#tableModal').on('show.bs.modal', function (e) {
    // 获取数据库配置
    var dataSourceConfig = iform.parseForm("dataSourceConfig");
    var tablePrefixList = $('#tablePrefix').val().split(",");

    var sourceData = {};
    sourceData["dataSourceConfig"] = dataSourceConfig;
    sourceData["tablePrefixList"] = tablePrefixList;

    // 转换成 json 格式的字符串
    var dataResult = JSON.stringify(sourceData);
    // var dataResult = JSON.stringify(dataSourceConfig);

    // console.log(dataResult);

    // 执行请求,
    $.ajax({
        type: "POST",
        url: "/table",
        contentType:"application/json",
        dataType:"JSON",
        data: dataResult,
        success: function(data){
            // console.log(data);
            var modalContent = index.mackTableModal(data.data);
            $("#tableModalBody").html(modalContent);
            // var checkResult = index.showResult(data);
            // if(checkResult){
            //     var prefixSet = data.data;
            //     var modalContent = index.mackTablePrefixModal(prefixSet);
            //     $("#tablePrefixModalBody").html(modalContent);
            // }
        },
        error(xhr,status,error){
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
});

/**
 * @author summer
 * @date 2018/6/15 下午6:30
 * @param null
 * @return null
 * @description 表前缀中的数据发生改变的时候所触发的事件
 */
// 监听表前缀模态框, 表名前缀的改变事件
$(document).on('change','#tablePrefixModal input:checkbox', function (e) {
    // 获取被选中的标签
    var tablePrefixChecked = [];
    $("#tablePrefixModal :checkbox:checked").each(function(index, item) {
        var value = $(item).val();
        tablePrefixChecked.push(value);
    });
    $('#tablePrefix').val(tablePrefixChecked.toString());
});

$(document).on('change','#tableModal input:checkbox', function (e) {
    // 获取被选中的标签
    var tableChecked = [];
    $("#tableModal :checkbox:checked").each(function(index, item) {
        var value = $(item).val();
        tableChecked.push(value);
    });
    $('#include').val(tableChecked.toString());
});

/**
 * @author summer
 * @date 2018/6/15 下午6:32
 * @return null
 * @description 代码生成的主方法
 */
index.generator = function () {
    // 全局配置
    var globalConfig = iform.parseForm("globalConfig");
    // 数据库配置
    var dataSourceConfig = iform.parseForm("dataSourceConfig");
    // 策略配置
    var strategyConfig = iform.parseForm("strategyConfig");
    // 包配置
    var packageConfig = iform.parseForm("packageConfig");

    strategyConfig.tablePrefix = strategyConfig.tablePrefix.split(",");

    strategyConfig.include = strategyConfig.include.split(",");

    strategyConfig.superEntityColumns = strategyConfig.superEntityColumns.split(",");

    var data = {};
    data.globalConfig = globalConfig;
    data.dataSourceConfig = dataSourceConfig;
    data.strategyConfig = strategyConfig;
    data.packageConfig = packageConfig;
    // console.log(data);
    var dataResult = JSON.stringify(data);

    // console.log(dataResult);

    $.ajax({
        type: "POST",
        url: "/generator",
        contentType:"application/json",
        dataType:"JSON",
        data: dataResult,
        success: function(data){
            console.log(data);
        },
        error(xhr,status,error){
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });

};

/**
 * @author summer
 * @date 2018/6/15 下午6:32
 * @return
 * @description 连接数据库,并获取对应数据库的表前缀
 */
index.connectionAndGetTablePrefix = function () {

    // 数据库配置
    var dataSourceConfig = iform.parseForm("dataSourceConfig");

    var dataResult = JSON.stringify(dataSourceConfig);

    $.ajax({
        type: "POST",
        url: "/prefix",
        contentType:"application/json",
        dataType:"JSON",
        data: dataResult,
        success: function(data){
            index.showResult(data);
            // console.log(data);
        },
        error(xhr,status,error){
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
};

/**
 * @author summer
 * @date 2018/6/15 下午6:20
 * @param data 请求的返回值
 * @return boolean 根据返回值检查后的结果
 * @description 根据返回值, 检查返回值是否正确, 如果不正确则弹出提示, 然后返回 false
 * 如果正确, 直接返回 true
 */
index.showResult = function (data) {
    if (data === undefined || data == null){
        alert("result is null");
        return false;
    }
    if(data.code === undefined || data == null){
        alert("unknown error");
        return false;
    }
    if(data.code !== 200){
        alert(data.message);
        return false;
    }

    return true;
};

/**
 * @author summer
 * @date 2018/6/15 下午6:35
 * @param data 表前缀集合
 * @return
 * @description 根据表前端集合, 拼接出表前缀模态框的选择区域
 */
index.mackTablePrefixModal = function (data) {
    if(data === undefined || data == null){
        return "";
    }

    // 被选中的表前缀数组
    var checkedPrefixArr = $('#tablePrefix').val().split(",");

    var modalBody = "";
    data.forEach(function( val, index ) {
        var modalBodyInner = '';
        modalBodyInner += "<div class='form-check'>";
        modalBodyInner += "<input class='form-check-input' type='checkbox' value='"+ val +"' id='tablePrefixId"+ index + "' ";
        // console.log($.inArray(val, checkedPrefixArr));
        if($.inArray(val, checkedPrefixArr) >= 0){
            modalBodyInner += "checked";
        }
        modalBodyInner += " >";
        modalBodyInner += "<label class='form-check-label' for='tablePrefixId"+ index +"'>";
        modalBodyInner += val;
        modalBodyInner += "</label>";
        modalBodyInner += "</div>";

        // console.log(modalBodyInner);
        modalBody += modalBodyInner;
    });
    return modalBody;
};


index.mackTableModal = function (data) {
    if(data === undefined || data == null){
        return "";
    }

    // 被选中的表名数组
    var checkedTableArr = $('#include').val().split(",");

    var modalBody = "";

    for(var key in data){
        if(key === undefined || key == null || key === ""){
            continue;
        }
        // console.log(data);
        // console.log(key);
        var tableNames = data[key];
        if(tableNames === undefined || tableNames == null){
            continue;
        }

        var modalBodyInner = '';
        modalBodyInner += "<div class='content'>";
        modalBodyInner += "<label class='my-1 d-md-block'>" +key +"</label>";

        tableNames.forEach(function (val, index) {
            modalBodyInner += "<div class='form-check form-check-inline'>";
            modalBodyInner += "<input class='form-check-input' type='checkbox' id='"+key+"TableId"+ index +"' value='"+ val +"' ";
            if($.inArray(val, checkedTableArr) >= 0){
                modalBodyInner += "checked";
            }
            modalBodyInner += " >";
            modalBodyInner += "<label class='form-check-label' for='"+key+"TableId"+ index +"'>"+ val +"</label>";
            modalBodyInner += "</div>";
        });

        modalBodyInner += "</div>";

        modalBody += modalBodyInner;
    }

    return modalBody;
};


// /** 全局配置 */
// autoGenerator.setGlobalConfig(globalConfig);
//
// /** 数据源配置 */
// autoGenerator.setDataSource(dataSourceConfig);
//
// /** 策略配置 */
// autoGenerator.setStrategy(strategyConfig);
//
// /** 包配置 */
// autoGenerator.setPackageInfo(packageConfig);