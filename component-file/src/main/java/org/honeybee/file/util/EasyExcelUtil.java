package org.honeybee.file.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.honeybee.base.holder.RequestHolder;
import org.honeybee.file.listener.ExcelListener;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel工具类
 */
@Slf4j
public class EasyExcelUtil {

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 得到Workbook对象
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        try (InputStream inputStream = file.getInputStream()) {
            String filename = file.getOriginalFilename();
            if(isExcel2003(filename)){
                workbook = new HSSFWorkbook(inputStream);
            }else {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return workbook;
    }

    /**
     * 获取文件头列名称
     *
     * @param file 文件
     * @param sheetNo sheet编号
     * @param rowNo 头行号，如果复杂头，取最下层头行号
     * @return
     */
    public static List<String> getColumnNames(MultipartFile file, Integer sheetNo, Integer rowNo) {
        List<String> columnNames = new ArrayList<>();
        Workbook workBook = getWorkBook(file);
        Iterator<Cell> iterator = workBook.getSheetAt(sheetNo).getRow(rowNo).iterator();
        while (true) {
            if(iterator.hasNext()) {
                String columnName = iterator.next().getStringCellValue();
                columnNames.add(columnName);
            } else {
                break;
            }
        }
        return columnNames;
    }

    /**
     * 读取单个sheet的excel文件
     * @param excel 文件
     * @param t 实体类型
     * @param headRowNumber 头行数
     * @return
     * @throws Exception
     */
    public static <T> List<T> readSingleExcel(MultipartFile excel, T t, int headRowNumber) throws IOException {
        return EasyExcel.read(excel.getInputStream(), t.getClass(), new ExcelListener())
                .sheet().headRowNumber(headRowNumber).doReadSync();
    }

    /**
     * 读取多个sheet的excel文件
     * @param excel 文件
     * @param t 实体类型
     * @param headRowNumber 头行数
     * @return
     * @throws Exception
     */
    @Deprecated
    public static <T> List<T> readMultiExcel(MultipartFile excel, T t, int headRowNumber, int sheetNumber) throws IOException {
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        /*ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(excel.getInputStream()).build();
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(sheetNumber).head(t.getClass()).headRowNumber(headRowNumber).registerReadListener(new ExcelListener()).build();

            excelReader.read(readSheet1);
            List<T> list1 = EasyExcel.readSheet(sheetNumber).head(t.getClass()).headRowNumber(headRowNumber)
                    .registerReadListener(new ExcelListener()).doReadSync();
        } finally {
            if(excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }*/
        return new ArrayList<>();
    }

    /**
     * 导出文件
     * 导出模板时，tList传一个空list即可
     * @param tList 数据集
     * @param tClass 数据类型
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeSingleExcel(String fileName,String sheetName, List<T> tList, Class tClass) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        setResponse(fileName, response);
        EasyExcel.write(outputStream, tClass).autoCloseStream(Boolean.TRUE)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())   //设置自动列宽
//                .registerWriteHandler(getCustomStyle())   //设置自定义样式
                .sheet(sheetName)
                .doWrite(tList);
    }

    /**
     * 设置导出信息
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    private static void setResponse(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 重置response
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        fileName = URLEncoder.encode(fileName + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss").format(LocalDateTime.now()) + ExcelTypeEnum.XLSX.getValue(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    private static HorizontalCellStyleStrategy getCustomStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景
        headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)25);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
