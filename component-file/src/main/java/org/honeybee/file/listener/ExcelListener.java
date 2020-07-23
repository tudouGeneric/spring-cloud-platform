package org.honeybee.file.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel 导入excel监听器
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener {


//    private List<T> data = new ArrayList<>();

    /**
     * 这个每一条数据解析都会来调用
     * @param object
     * @param analysisContext 可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSONUtil.toJsonStr(object));
        //数据存储到List
//        data.add((T) object);
    }

    /**
     * 所有数据解析完成了,都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

//    public List<T> getDatas() {
//        return data;
//    }

}
