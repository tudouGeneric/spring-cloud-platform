package org.honeybee.mybatisplus.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;

/**
 * 学生身份转换器
 * （转换枚举示例）
 */
public class StudentIdentityConverter implements Converter<StudentIdentityEnum> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 导入至Java时转换数据格式
     * @param cellData
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     */
    @Override
    public StudentIdentityEnum convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        return StudentIdentityEnum.getEnumByDescp(cellData.getStringValue());
    }

    /**
     * 导出至Excel时转换数据格式
     * @param identityEnum
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     */
    @Override
    public CellData convertToExcelData(StudentIdentityEnum identityEnum, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData(identityEnum.getDescp());
    }

}
