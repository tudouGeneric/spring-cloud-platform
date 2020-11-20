package org.honeybee.base.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.enums.ICustomEnum;
import org.honeybee.base.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEnumWebSerializer extends JsonSerializer<ICustomEnum> {

    @Value("${enums.param.suffix}")
    private String suffix;

    @Override
    public void serialize(ICustomEnum value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        if(value == null) {
            jsonGenerator.writeNull();
        }
        jsonGenerator.writeObject(value.getValue());
        if(StringUtils.isBlank(suffix)) {
            throw new ServiceException("枚举描述后缀配置值为空");
        }
        jsonGenerator.writeFieldName(jsonGenerator.getOutputContext().getCurrentName() + suffix);
        jsonGenerator.writeString(value.getDescription());
    }

    @Override
    public Class<ICustomEnum> handledType() {
        return ICustomEnum.class;
    }

}
