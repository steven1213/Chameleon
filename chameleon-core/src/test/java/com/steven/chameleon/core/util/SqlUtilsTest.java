package com.steven.chameleon.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SQL Utils Test")
class SqlUtilsTest {

    @Nested
    @DisplayName("SQL Validation Tests")
    class SqlValidationTests {
        @Test
        @DisplayName("Should validate SQL statements correctly")
        void testIsValidSql() {
            // Valid SQL
            assertTrue(SqlUtils.isValidSql("SELECT * FROM users"), 
                "Simple query should be valid");
            assertTrue(SqlUtils.isValidSql("SELECT id, name FROM users WHERE age > 18"), 
                "Conditional query should be valid");
            assertTrue(SqlUtils.isValidSql("INSERT INTO users (name, age) VALUES ('John', 25)"), 
                "Insert statement should be valid");
            assertTrue(SqlUtils.isValidSql("UPDATE users SET name = 'John' WHERE id = 1"), 
                "Update statement should be valid");
            assertTrue(SqlUtils.isValidSql("DELETE FROM users WHERE id = 1"), 
                "Delete statement should be valid");
            
            // Invalid SQL
            assertFalse(SqlUtils.isValidSql("DROP DATABASE test"), 
                "DROP statement should be invalid");
            assertFalse(SqlUtils.isValidSql("TRUNCATE TABLE users"), 
                "TRUNCATE statement should be invalid");
            assertFalse(SqlUtils.isValidSql("ALTER TABLE users ADD COLUMN email VARCHAR(100)"), 
                "ALTER statement should be invalid");
        }

        @ParameterizedTest(name = "Empty input test: {0}")
        @DisplayName("Should handle null and empty inputs")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void testIsValidSqlWithInvalidInput(String sql) {
            assertFalse(SqlUtils.isValidSql(sql), 
                "Null or empty input should be invalid");
        }
    }

    @Nested
    @DisplayName("Aggregate Function Tests")
    class AggregateFunctionTests {
        @Test
        @DisplayName("Should detect aggregate functions in SQL")
        void testContainsAggregateFunction() {
            // With aggregate functions
            assertTrue(SqlUtils.containsAggregateFunction("SELECT COUNT(*) FROM users"), 
                "Should detect COUNT function");
            assertTrue(SqlUtils.containsAggregateFunction("SELECT SUM(amount) FROM orders"), 
                "Should detect SUM function");
            assertTrue(SqlUtils.containsAggregateFunction("SELECT AVG(price) FROM products"), 
                "Should detect AVG function");
            
            // Without aggregate functions
            assertFalse(SqlUtils.containsAggregateFunction("SELECT * FROM users"), 
                "Should not detect aggregate function in simple query");
            assertFalse(SqlUtils.containsAggregateFunction("SELECT id, name FROM users"), 
                "Should not detect aggregate function in column query");
        }
    }

    @Nested
    @DisplayName("SQL Sanitization Tests")
    class SqlSanitizationTests {
        @ParameterizedTest(name = "Sanitization test #{index}")
        @DisplayName("Should sanitize SQL injection and clean input")
        @MethodSource("com.steven.chameleon.core.util.SqlUtilsTest#provideSqlForSanitization")
        void testSanitizeSql(String input, String expected) {
            assertEquals(expected, SqlUtils.sanitizeSql(input), 
                "SQL sanitization should match expected output");
        }
    }

    private static Stream<Arguments> provideSqlForSanitization() {
        return Stream.of(
            Arguments.of(
                "SELECT * FROM users WHERE id = 1; DROP TABLE users;", 
                "SELECT * FROM users WHERE id = 1",
                "Should remove SQL injection attempt"
            ),
            Arguments.of(
                "SELECT * FROM users -- comment", 
                "SELECT * FROM users",
                "Should remove line comments"
            ),
            Arguments.of(
                "SELECT * FROM users /* block comment */ WHERE id = 1", 
                "SELECT * FROM users WHERE id = 1",
                "Should remove block comments"
            )
        );
    }

    @Nested
    @DisplayName("Table Name Tests")
    class TableNameTests {
        @Test
        @DisplayName("Should extract table names from SQL")
        void testGetTableName() {
            assertEquals("users", SqlUtils.getTableName("SELECT * FROM users"), 
                "Should extract simple table name");
            assertEquals("users", SqlUtils.getTableName("SELECT * FROM users u"), 
                "Should extract table name with alias");
            assertEquals("public.users", SqlUtils.getTableName("SELECT * FROM public.users"), 
                "Should extract table name with schema");
        }
    }

    @Nested
    @DisplayName("Pagination and Sorting Tests")
    class PaginationAndSortingTests {
        @Test
        @DisplayName("Should add pagination parameters")
        void testAddLimitAndOffset() {
            assertEquals(
                "SELECT * FROM users LIMIT 10 OFFSET 0",
                SqlUtils.addLimitAndOffset("SELECT * FROM users", 10, 0),
                "Should add basic pagination parameters"
            );
            
            assertEquals(
                "SELECT * FROM users ORDER BY id DESC LIMIT 20 OFFSET 40",
                SqlUtils.addLimitAndOffset("SELECT * FROM users ORDER BY id DESC", 20, 40),
                "Should add pagination to ordered query"
            );
        }

        @Test
        @DisplayName("Should add sorting parameters")
        void testAddOrderBy() {
            assertEquals(
                "SELECT * FROM users ORDER BY id DESC",
                SqlUtils.addOrderBy("SELECT * FROM users", "id", "DESC"),
                "Should add basic sorting"
            );
            
            assertEquals(
                "SELECT * FROM users WHERE age > 18 ORDER BY name ASC",
                SqlUtils.addOrderBy("SELECT * FROM users WHERE age > 18", "name", "ASC"),
                "Should add sorting to conditional query"
            );
        }
    }

    @Nested
    @DisplayName("Count Query Tests")
    class CountQueryTests {
        @Test
        @DisplayName("Should convert to count queries")
        void testCountSql() {
            assertEquals(
                "SELECT COUNT(*) FROM users",
                SqlUtils.countSql("SELECT * FROM users"),
                "Should convert simple query to count"
            );
            
            assertEquals(
                "SELECT COUNT(*) FROM users WHERE age > 18",
                SqlUtils.countSql("SELECT id, name FROM users WHERE age > 18"),
                "Should convert conditional query to count"
            );
        }
    }
}