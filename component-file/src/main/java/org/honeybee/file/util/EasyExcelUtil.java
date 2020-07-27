package org.honeybee.file.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
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
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.holder.RequestHolder;
import org.honeybee.file.common.SheetExcelData;
import org.honeybee.file.handler.CustomCellStyleStrategy;
import org.honeybee.file.listener.ExcelListener;
import org.springframework.http.HttpStatus;
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
        return null;
    }

    /**
     * 导出文件
     * 导出模板时，tList传一个空list即可
     * @param fileName
     * @param sheetName
     * @param tList 数据集
     * @param tClass 数据类型
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeSingleExcel(String fileName, String sheetName, List<T> tList, Class tClass) throws IOException {
        HttpServletResponse response = RequestHolder.getResponse();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            setResponse(fileName, response);
            EasyExcel.write(outputStream, tClass).autoCloseStream(Boolean.TRUE)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())   //设置自动列宽
                    .registerWriteHandler(new CustomCellStyleStrategy())   //设置自定义样式
                    .sheet(sheetName)
                    .doWrite(tList);
        } catch (Exception e) {
            errorWrite(response, e);
        }
    }

    /**
     * 导出文件
     * 导出模板时，tList传一个空list即可
     * @param fileName
     * @param sheetName
     * @param tList
     * @param tClass
     * @param customCellStyleStrategy
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeSingleExcel(String fileName, String sheetName, List<T> tList, Class tClass, CustomCellStyleStrategy customCellStyleStrategy) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            setResponse(fileName, response);
            EasyExcel.write(outputStream, tClass).autoCloseStream(Boolean.TRUE)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())   //设置自动列宽
                    .registerWriteHandler(customCellStyleStrategy)   //设置自定义样式
                    .sheet(sheetName)
                    .doWrite(tList);
        } catch (Exception e) {
            errorWrite(response, e);
        }
    }

    /**
     * 导出多sheet
     * @param fileName 文件名
     * @param sheetExcelDataList sheet对象
     * @throws IOException
     */
    public static void writeMultiExcel(String fileName, List<SheetExcelData> sheetExcelDataList) throws IOException {
        HttpServletResponse response = RequestHolder.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        setResponse(fileName, response);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(Boolean.FALSE).build();
        try {
            for (int i=0, length=sheetExcelDataList.size(); i<length; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i+1, sheetExcelDataList.get(i).getSheetName())
                        .head(sheetExcelDataList.get(i).getTClass())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                excelWriter.write(sheetExcelDataList.get(i).getDataList(), writeSheet);
            }
        } catch (Exception e) {
            errorWrite(response, e);
        }finally {
            // 刷新流，不加这句话，下载文件损坏打不开
            outputStream.flush();
            if(excelWriter != null){
                // 千万别忘记finish关闭流
                excelWriter.finish();
            }
        }
    }

    /**
     * 导出多sheet
     * @param fileName 文件名
     * @param sheetExcelDataList sheet对象
     * @throws IOException
     */
    public static void writeMultiExcel(String fileName, List<SheetExcelData> sheetExcelDataList, CustomCellStyleStrategy customCellStyleStrategy) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        setResponse(fileName, response);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(Boolean.FALSE).build();
        try {
            for (int i = 0,length = sheetExcelDataList.size(); i < length; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i+1, sheetExcelDataList.get(i).getSheetName())
                        .head(sheetExcelDataList.get(i).getTClass())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .registerWriteHandler(customCellStyleStrategy)
                        .build();
                excelWriter.write(sheetExcelDataList.get(i).getDataList(), writeSheet);
            }
        } catch (Exception e) {
            errorWrite(response, e);
        }finally {
            // 刷新流，不加这句话，下载文件损坏打不开
            outputStream.flush();
            if(excelWriter != null){
                // 千万别忘记finish关闭流
                excelWriter.finish();
            }
        }
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

    /**
     * 导出错误
     * @param response
     * @param e
     * @throws IOException
     */
    private static void errorWrite(HttpServletResponse response, Exception e) throws IOException {
        // 重置response
        log.error(e.getMessage(), e);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().println(JSONUtil.toJsonStr(ResponseMessage.fail("导出失败")));
    }

}
