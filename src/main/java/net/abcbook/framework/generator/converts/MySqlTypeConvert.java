package net.abcbook.framework.generator.converts;

import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import lombok.NoArgsConstructor;
import net.abcbook.framework.generator.constant.MySqlTypeConstant;

/**
 * @author summer
 * @date 2017/12/31 下午11:56
 * MySql 和 Java 的数据类型转换器
 */
@NoArgsConstructor
public class MySqlTypeConvert implements ITypeConvert {

    /**
     * @author summer
     * @date 2018/6/8 上午10:21
     * @param fieldType MySql 的数据类型
     * @return com.baomidou.mybatisplus.generator.config.rules.DbColumnType
     * @description 根据传入的 MySql 的数据类型, 返回对应的 Java 的数据类型
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {

        //获取数据库中文件的类型
        String t = fieldType.toLowerCase();

        // 如果不是文本
        if (!t.contains(MySqlTypeConstant.CHAR) && !t.contains(MySqlTypeConstant.TEXT)) {
            if (t.contains(MySqlTypeConstant.BIGINT)) {
                return DbColumnType.LONG;
            } else if (t.contains(MySqlTypeConstant.TINYINT_1)) {
                return DbColumnType.BOOLEAN;
            } else if (t.contains(MySqlTypeConstant.INT)) {
                return DbColumnType.INTEGER;
            } else if (!t.contains(MySqlTypeConstant.DATE)
                    && !t.contains(MySqlTypeConstant.TIME)
                    && !t.contains(MySqlTypeConstant.YEAR)) {
                if (t.contains(MySqlTypeConstant.TEXT)) {
                    return DbColumnType.STRING;
                } else if (t.contains(MySqlTypeConstant.BIT)) {
                    return DbColumnType.BOOLEAN;
                } else if (t.contains(MySqlTypeConstant.DECIMAL)) {
                    return DbColumnType.BIG_DECIMAL;
                } else if (t.contains(MySqlTypeConstant.CLOB)) {
                    return DbColumnType.CLOB;
                } else if (t.contains(MySqlTypeConstant.BLOB)) {
                    return DbColumnType.BLOB;
                } else if (t.contains(MySqlTypeConstant.BINARY)) {
                    return DbColumnType.BYTE_ARRAY;
                } else if (t.contains(MySqlTypeConstant.FLOAT)) {
                    return DbColumnType.FLOAT;
                } else if (t.contains(MySqlTypeConstant.DOUBLE)) {
                    return DbColumnType.DOUBLE;
                } else {
                    return DbColumnType.STRING;
                }
            } else {
                return DbColumnType.DATE;
            }
        } else {
            return DbColumnType.STRING;
        }
    }
}
