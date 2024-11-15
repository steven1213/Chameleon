package com.steven.chameleon.core.exception;

/**
 * 系统错误码枚举
 * @author: steven
 * @date: 2024/11/14 16:49
 */
public enum ErrorCode {
    // 未知错误 (0-99)
    UNKNOWN(0, "Unknown error", ErrorLevel.ERROR),

    // 系统错误 (100-199)
    SYSTEM_ERROR(100, "System error", ErrorLevel.ERROR),
    INITIALIZATION_ERROR(101, "Initialization error", ErrorLevel.FATAL),

    // 配置错误 (200-299)
    CONFIG_ERROR(200, "Configuration error", ErrorLevel.ERROR),
    CONFIG_MISSING(201, "Configuration missing", ErrorLevel.ERROR),
    CONFIG_INVALID(202, "Invalid configuration", ErrorLevel.ERROR),

    // 数据类型错误 (300-399)
    DATA_TYPE_ERROR(300, "Data type error", ErrorLevel.ERROR),
    DATA_TYPE_MISMATCH(301, "Data type mismatch", ErrorLevel.ERROR),
    DATA_TYPE_UNSUPPORTED(302, "Unsupported data type", ErrorLevel.ERROR),
    DATA_TYPE_CONVERSION_ERROR(303, "Data type conversion error", ErrorLevel.ERROR),
    DATA_TYPE_VALIDATION_ERROR(304, "Data type validation error", ErrorLevel.ERROR),
    DATA_TYPE_NULL_ERROR(305, "Data type null error", ErrorLevel.ERROR),
    DATA_TYPE_CAST_ERROR(306, "Data type cast error", ErrorLevel.ERROR),
    DATA_TYPE_FORMAT_ERROR(307, "Data type format error", ErrorLevel.ERROR),
    DATA_TYPE_RANGE_ERROR(308, "Data type range error", ErrorLevel.ERROR),
    DATA_TYPE_PRECISION_ERROR(309, "Data type precision error", ErrorLevel.ERROR),

    // 数据转换错误 (400-499)
    DATA_CONVERSION_ERROR(400, "Data conversion error", ErrorLevel.ERROR),
    DATA_FORMAT_ERROR(401, "Data format error", ErrorLevel.ERROR),
    DATA_PARSE_ERROR(402, "Data parse error", ErrorLevel.ERROR),
    DATA_SERIALIZE_ERROR(403, "Data serialization error", ErrorLevel.ERROR),

    // 连接错误 (500-599)
    CONNECTION_ERROR(500, "Database connection error", ErrorLevel.ERROR),
    CONNECTION_TIMEOUT(501, "Connection timeout", ErrorLevel.ERROR),
    CONNECTION_CLOSED(502, "Connection is closed", ErrorLevel.ERROR),

    // SQL错误 (600-699)
    SQL_ERROR(600, "SQL error", ErrorLevel.ERROR),
    SQL_SYNTAX_ERROR(601, "SQL syntax error", ErrorLevel.ERROR),
    SQL_EXECUTION_ERROR(602, "SQL execution error", ErrorLevel.ERROR),

    // 事务错误 (700-799)
    TRANSACTION_ERROR(700, "Transaction error", ErrorLevel.ERROR),
    TRANSACTION_NOT_ACTIVE(701, "Transaction is not active", ErrorLevel.ERROR),
    TRANSACTION_ALREADY_ACTIVE(702, "Transaction is already active", ErrorLevel.ERROR),


    SERIALIZATION_ERROR(800, "Serialization error", ErrorLevel.ERROR);

    private final int code;
    private final String message;
    private final ErrorLevel level;

    ErrorCode(int code, String message, ErrorLevel level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ErrorLevel getLevel() {
        return level;
    }

    /**
     * 判断是否为系统错误
     */
    public boolean isSystemError() {
        return code >= 100 && code < 200;
    }

    /**
     * 判断是否为配置错误
     */
    public boolean isConfigError() {
        return code >= 200 && code < 300;
    }

    /**
     * 判断是否为数据类型错误
     */
    public boolean isDataTypeError() {
        return code >= 300 && code < 400;
    }

    /**
     * 判断是否为数据转换错误
     */
    public boolean isDataConversionError() {
        return code >= 400 && code < 500;
    }

    /**
     * 判断是否为连接错误
     */
    public boolean isConnectionError() {
        return code >= 500 && code < 600;
    }

    /**
     * 判断是否为SQL错误
     */
    public boolean isSqlError() {
        return code >= 600 && code < 700;
    }

    /**
     * 判断是否为事务错误
     */
    public boolean isTransactionError() {
        return code >= 700 && code < 800;
    }

    /**
     * 获取错误详细信息
     */
    public String getDetailedMessage() {
        return String.format("[%d] %s - Level: %s", code, message, level);
    }

    /**
     * 创建带有额外信息的错误消息
     */
    public String formatMessage(String additionalInfo) {
        return String.format("%s: %s", message, additionalInfo);
    }

    @Override
    public String toString() {
        return String.format("ErrorCode[%d]: %s (%s)", code, message, level);
    }

    /**
     * 根据错误码获取枚举值
     */
    public static ErrorCode valueOf(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return UNKNOWN;
    }
}

/**
 * 错误级别枚举
 */
enum ErrorLevel {
    DEBUG("Debug level error"),
    INFO("Informational error"),
    WARN("Warning level error"),
    ERROR("Error level error"),
    FATAL("Fatal error");

    private final String description;

    ErrorLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMoreSevereThan(ErrorLevel other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean isLessSevereThan(ErrorLevel other) {
        return this.ordinal() < other.ordinal();
    }
}