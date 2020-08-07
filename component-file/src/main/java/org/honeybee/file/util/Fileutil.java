package org.honeybee.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.holder.RequestHolder;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 文件工具类
 */
@Slf4j
public class Fileutil {

    /**
     * 项目本地 文件下载
     * @param filePath 文件子路径
     */
    public static void downloadFile(String fileName, String filePath) throws IOException {
        //创建输出流对象
        HttpServletResponse response = RequestHolder.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            //判断文件是否存在
            File file = new File(filePath);
            if (!file.exists()) {
                error(response, "文件不存在");
            }

            setResponse(fileName, response);
            byte[] bytes = FileUtil.readBytes(filePath);
            outputStream.write(bytes);
        } finally { //关闭流对象
            if(outputStream != null) {
                outputStream.flush();
                outputStream.close();
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
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    /**
     * 发生错误处理
     * @param response
     * @throws IOException
     */
    private static void error(HttpServletResponse response, String errMsg) throws IOException {
        // 重置response
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().println(JSONUtil.toJsonStr(ResponseMessage.fail(errMsg)));
    }

}
