package net.abcbook.framework.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author summer
 * @date 2017/12/6 下午4:30
 */
public class CodeGenerator {

    /**
     * 全局配置
     */
    /** 代码生成的输出路径 (定位到源码的定位点, 不包括包名和模块名) */
//    private static final String GC_OUTPUT_DIR = "/Users/lason/workspaces/idea/works/kuibu/dingtalk-property/src/main/java";
    private static final String GC_OUTPUT_DIR = "/Users/lason/Desktop/code/dingtalk-property/src/main/java";
    /** 开发者 */
    private static final String GC_AUTHOR = "summer";

    /**
     * 数据源配置
     */
    /** 用户名 */
//    private static final String DB_USERNAME = "root";
    private static final String DB_USERNAME = "root";
    /** 密码 */
//    private static final String DB_PASSWORD = "123123";
    private static final String DB_PASSWORD = "P2ssw0rd";
    /** 数据库地址 */
//    private static final String DB_URL = "jdbc:mysql://47.100.108.8:3306/private?characterEncoding=utf-8&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://10.0.1.11:3306/private?useUnicode=true&characterEncoding=utf-8";

    /**
     * 策略配置
     */
    /**
     * 需要生成的表名前缀
     * 加入前缀后, 生成的类(实体, service, dao) 均会去除前缀来命名
     */
    private static final String[] SC_TABLE_PREFIX = {"droid"};
    /** 需要生成的表 */
//    private static final String[] SC_INCLUDE = {"bus_owner_change","bus_category","bus_property","bus_user_property"};
    private static final String[] SC_INCLUDE = {"droid_node_change_log"};
    /** 排除生成的表 */
//    private static final String[] SC_EXCLUDE = {};
    /** [实体]是否创建 lombok 类型的实体类 */
//    private static final boolean SC_ENTITY_LOMBOK_MODEL = true;

    /**
     * 包配置
     */
    /** 包名 */
    private static final String PC_PARENT = "com.tinypace.jeesite.modules";
    /** 模块名 */
    private static final String PC_MODULE_NAME = "testdroid";

    /**
     * mappers 配置
     */
    /**  mapper 文件生成路径*/
//    private static final String FL_MAPPER_OUT_PUT_URL = "/Users/lason/workspaces/idea/works/kuibu/dingtalk-property/src/main/resources/mapper";
    private static final String FL_MAPPER_OUT_PUT_URL = "/Users/lason/Desktop/code/dingtalk-property/src/main/resources/mapper";


    /**
     * @author summer
     * @date 2017/12/31 下午10:53
     * @return void
     * @description 代码生成方法入口
     */
    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.autoGenerator();
    }

    /**
     * @author summer
     * @date 2017/12/31 下午10:53
     * @return com.baomidou.mybatisplus.generator.config.GlobalConfig
     * @description 全局配置设置
     */
    private GlobalConfig generateGlobalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setOutputDir(GC_OUTPUT_DIR);
        globalConfig.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        globalConfig.setActiveRecord(false);
        // XML 二级缓存
        globalConfig.setEnableCache(false);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        // XML columList
        globalConfig.setBaseColumnList(true);
// 是否生成 kotlin 代码
//        globalConfig.setKotlin(true);
        globalConfig.setAuthor(GC_AUTHOR);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");

        return globalConfig;
    }

    /**
     * @author summer
     * @date 2017/12/31 下午10:59
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig
     * @description 获取数据源配置的方法
     */
    private DataSourceConfig generateDataSourceConfig(){
        /**
         * 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
//                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername(DB_USERNAME);
        dataSourceConfig.setPassword(DB_PASSWORD);
        dataSourceConfig.setUrl(DB_URL);

        return dataSourceConfig;
    }

    /**
     * @author summer
     * @date 2017/12/31 下午11:02
     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig
     * @description 生成策略配置的方法
     */
    private StrategyConfig generateStrategyConfig(){
        /**
         * 策略配置
         */
        StrategyConfig strategyConfig = new StrategyConfig();

        // 全局大写命名 ORACLE 注意
        // strategyConfig.setCapitalMode(true);
        // 此处可以修改为您的表前缀
        strategyConfig.setTablePrefix(SC_TABLE_PREFIX);
        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategyConfig.setInclude(SC_INCLUDE);
        // 排除生成的表
        // strategyConfig.setExclude(SC_EXCLUDE);
        // 自定义实体父类
        strategyConfig.setSuperEntityClass("com.tinypace.framework.base.model.BaseModel");
        // 自定义实体，公共字段
        strategyConfig.setSuperEntityColumns(new String[] { "id", "create_time", "update_time", "is_deleted" });
        // 自定义 mapper 父类
        strategyConfig.setSuperMapperClass("com.tinypace.framework.base.mapper.BaseMapper");
        // 自定义 service 父类
        strategyConfig.setSuperServiceClass("com.tinypace.framework.base.service.BaseService");
        // 自定义 service 实现类父类
        strategyConfig.setSuperServiceImplClass("com.tinypace.framework.base.service.impl.BaseServiceImpl");
        // 自定义 controller 父类
        // strategyConfig.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategyConfig.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
//         strategyConfig.setEntityBuilderModel(false);
        // [实体]是否创建 lombok 类型的实体类
        strategyConfig.setEntityLombokModel(true);

        return strategyConfig;
    }

    /**
     * @author summer
     * @date 2017/12/31 下午11:08
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
     * @description 获取包信息的相关配置
     */
    private PackageConfig generatePackageConfig(){
        /**
         * 包配置
         */
        PackageConfig packageConfig = new PackageConfig();

        // 包名
        packageConfig.setParent(PC_PARENT);
        // 模块名
        packageConfig.setModuleName(PC_MODULE_NAME);
        // 设置实体类的包名
        packageConfig.setEntity("model");

        return packageConfig;
    }


    /**
     * @author summer
     * @date 2017/12/31 下午11:12
     * @return com.baomidou.mybatisplus.generator.InjectionConfig
     * @description 自定义配置的注入
     */
    private InjectionConfig generateInjectionConfig(){

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "/Users/lason/Downloads/upload_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        autoGenerator.setCfg(cfg);

        /**
         * 定义自定义输出文件的集合,用于封装自定义输出文件
         */
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        /**
         * 调整 xml 生成目录演示
         */
        focList.add(getMapperFileOutConfig());

        /**
         * 把自定义的输出文件加入到自定义配置中
         */
        injectionConfig.setFileOutConfigList(focList);

        return injectionConfig;
    }

    /**
     * @author summer
     * @date 2017/12/31 下午11:30
     * @return com.baomidou.mybatisplus.generator.config.FileOutConfig
     * @description 定义 mapper 文件的生成路径及模板信息
     */
    private FileOutConfig getMapperFileOutConfig(){
        return new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return FL_MAPPER_OUT_PUT_URL + "/" + PC_MODULE_NAME + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        };
    }

    /**
     * @author summer
     * @date 2018/1/1 上午11:34
     * @return com.baomidou.mybatisplus.generator.config.TemplateConfig
     * @description 定义对应的模板
     */
    private TemplateConfig generateTemplateConfig(){
        /**
         * 关闭默认 xml 生成，调整生成 至 根目录
         */
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // TODO 在此处设置生成那些类型的文件
//        templateConfig.setEntity(null);
//        templateConfig.setMapper(null);
//        templateConfig.setServiceImpl(null);
//        templateConfig.setService(null);
//        templateConfig.setController(null);


        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // autoGenerator.setTemplate(tc);

        return templateConfig;
    }
    public void autoGenerator(){
        AutoGenerator autoGenerator = new AutoGenerator();

        /** 全局配置 */
        autoGenerator.setGlobalConfig(generateGlobalConfig());

        /** 数据源配置 */
        autoGenerator.setDataSource(generateDataSourceConfig());

        /** 策略配置 */
        autoGenerator.setStrategy(generateStrategyConfig());

        /** 包配置 */
        autoGenerator.setPackageInfo(generatePackageConfig());

        /** 自定义的配置 */
        autoGenerator.setCfg(generateInjectionConfig());

        /** 定义模板 */
        autoGenerator.setTemplate(generateTemplateConfig());

        /** 执行生成 */
        autoGenerator.execute();

        // 打印注入设置【可无】
//        System.err.println(autoGenerator.getCfg().getMap().get("abc"));
    }

}
