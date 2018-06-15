package net.abcbook.framework.generator.form;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author summer
 * @date 2018/6/11 上午12:55
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeneratorForm implements Serializable {
    private GlobalConfig globalConfig;
    private DataSourceConfig dataSourceConfig;
    private StrategyConfig strategyConfig;
    private PackageConfig packageConfig;

    private InjectionConfig injectionConfig;
    private TemplateConfig templateConfig;

}
