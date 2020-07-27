package org.honeybee.file.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * excel 单元自定义样式
 */
@Slf4j
public class CustomCellStyleStrategy extends AbstractCellStyleStrategy {

    /**
     * 操作行
     */
    private List<Integer> columnIndexes;

    /**
     * 操作列
     */
    private List<Integer> rowIndexes;

    /**
     * 颜色
     */
    private Short colorIndex;


    public CustomCellStyleStrategy() {

    }

    public CustomCellStyleStrategy(List<Integer> rowIndexes, List<Integer> columnIndexes, Short colorIndex) {
        this.rowIndexes = rowIndexes;
        this.columnIndexes = columnIndexes;
        this.colorIndex = colorIndex;
    }

    @Override
    protected void initCellStyle(Workbook workbook) {

    }

    /**
     * 自定义头部样式
     *
     * @param cell
     * @param head
     * @param integer
     */
    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer integer) {
        // 获取workbook
        Workbook workbook = cell.getSheet().getWorkbook();
        // 获取样式实例
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 获取字体实例
        WriteFont headWriteFont = new WriteFont();
        // 设置字体样式
        headWriteFont.setFontName("宋体");
        // 设置字体大小
        headWriteFont.setFontHeightInPoints((short)14);
        // 边框
        headWriteFont.setBold(true);
        // 设置背景颜色为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        if (CollectionUtils.isNotEmpty(columnIndexes) && columnIndexes.contains(cell.getColumnIndex())
                && CollectionUtils.isNotEmpty(rowIndexes) && rowIndexes.contains(cell.getRowIndex())
                && colorIndex != null) {
            // 设置指定单元格字体自定义颜色
            headWriteFont.setColor(colorIndex);
        }
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 获取样式实例
        CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
        // 单元格设置样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 自定义内容样式
     *
     * @param cell
     * @param head
     * @param integer
     */
    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer integer) {

    }

}