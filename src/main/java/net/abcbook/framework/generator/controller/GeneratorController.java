package net.abcbook.framework.generator.controller;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import net.abcbook.framework.generator.form.GeneratorForm;
import net.abcbook.framework.generator.form.TablePrefixForm;
import net.abcbook.framework.generator.utils.MysqlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author summer
 * @date 2018/6/8 上午11:39
 */
@Controller
@RequestMapping
public class GeneratorController {

    @GetMapping
    public String index(ModelMap modelMap){
        modelMap.addAttribute("dbTypes", DbType.values());
        modelMap.addAttribute("namingStrategyValues", NamingStrategy.values());

        return "/pages/index";
    }

    @PostMapping(value = "/prefix", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> connectionAndGetTablePrefix(@RequestBody DataSourceConfig dataSourceConfig){
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", "error");

        if (dataSourceConfig == null
                || StringUtils.isEmpty(dataSourceConfig.getUrl())) {
            return result;
        }

        List<String> tableNameLit = null;
        try {
            // jdbc:mysql://localhost:3306/tinypace_property?characterEncoding=utf-8&useSSL=false
            String database = MysqlUtils.getDatabaseByUrl(dataSourceConfig.getUrl());
            if(StringUtils.isEmpty(database)){
                return result;
            }

            tableNameLit = MysqlUtils.getTablesByPrefix(dataSourceConfig, database);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
            return result;
        }

        if(CollectionUtils.isEmpty(tableNameLit)){
            return result;
        }

        Set<String> prefixSet = tableNameLit.stream().map(tableName -> {
            int separatorIndex = tableName.indexOf("_");
            if(separatorIndex <= 0){
                return null;
            }

            return tableName.substring(0, separatorIndex);
        }).collect(Collectors.toSet());
        // 去除可能存入的 null;
        prefixSet.remove(null);


        result.put("code", 200);
        result.put("message", "success");
        result.put("data", prefixSet);
        return result;
    }

    @PostMapping(value = "/table", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> getTableList(@RequestBody TablePrefixForm tablePrefixForm){
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", "error");

        if(tablePrefixForm == null
                || tablePrefixForm.getDataSourceConfig() == null
                || StringUtils.isEmpty(tablePrefixForm.getTablePrefixList())){
            result.put("message", "数据库连接参数或表名前缀为空");
            return result;
        }

        Map<String, List<String>> tableNameMap = null;
        try {
            tableNameMap = MysqlUtils.listTableByTablePrefix(tablePrefixForm.getDataSourceConfig(),
                    tablePrefixForm.getTablePrefixList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.put("code", 200);
        result.put("message", "success");
        result.put("data", tableNameMap);

        return result;
    }

    public static void main(String[] args) {
        Set set = new HashSet();
        set.remove(null);

    }

    @PostMapping(value = "/generator", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> autoGenerator(
            @RequestBody GeneratorForm generatorForm
//            @RequestBody GlobalConfig globalConfig,
//                                             @RequestBody DataSourceConfig dataSourceConfig,
//                                             @RequestBody StrategyConfig strategyConfig,
//                                             @RequestBody PackageConfig packageConfig
//                             InjectionConfig injectionConfig,
//                             TemplateConfig templateConfig
    ){
//        "globalConfig":globalConfig,
//                "dataSourceConfig":dataSourceConfig,
//                "strategyConfig":strategyConfig,
//                "packageConfig":packageConfig,

        AutoGenerator autoGenerator = new AutoGenerator();

        /** 全局配置 */
        autoGenerator.setGlobalConfig(generatorForm.getGlobalConfig());

        /** 数据源配置 */
        autoGenerator.setDataSource(generatorForm.getDataSourceConfig());

        /** 策略配置 */
        autoGenerator.setStrategy(generatorForm.getStrategyConfig());

        /** 包配置 */
        autoGenerator.setPackageInfo(generatorForm.getPackageConfig());

        /** 自定义的配置 */
        autoGenerator.setCfg(generatorForm.getInjectionConfig());

        /** 定义模板 */
        autoGenerator.setTemplate(generatorForm.getTemplateConfig());

        /** 执行生成 */
        autoGenerator.execute();

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");

        return result;
    }

}
