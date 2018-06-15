package net.abcbook.framework.generator.form;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author summer
 * @date 2018/6/15 下午7:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TablePrefixForm  implements Serializable {
    private DataSourceConfig dataSourceConfig;
    private List<String> tablePrefixList;
}
