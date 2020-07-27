package org.honeybee.file.common;

import lombok.Data;

import java.util.List;

/**
 * easyExcel多sheet导出辅助类
 * @param <T>
 */
@Data
public class SheetExcelData<T> {

    /**
     * 数据内容
     */
    private List<T> dataList;

    /**
     * sheet名称
     */
    private String sheetName;

    /**
     * 对象类型
     */
    private Class<T> tClass;

}
