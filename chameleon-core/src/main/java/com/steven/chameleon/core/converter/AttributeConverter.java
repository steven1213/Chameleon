package com.steven.chameleon.core.converter;

/**
 * 属性转换器接口，用于在数据库类型和 Java 类型之间进行转换
 * @param <X> Java 类型
 * @param <Y> 数据库类型
 *
 * @author: steven
 * @date: 2024/11/15 13:52
 */
public interface AttributeConverter<X, Y> {
    
    /**
     * 将 Java 类型转换为数据库类型
     * @param attribute Java 属性值
     * @return 转换后的数据库值
     */
    Y convertToDatabaseColumn(X attribute);

    /**
     * 将数据库类型转换为 Java 类型
     * @param dbData 数据库值
     * @return 转换后的 Java 属性值
     */
    X convertToEntityAttribute(Y dbData);
}
