package org.cgz.oseye.common;

/**
 * 简单的条件查询时的比较类型
 */
public enum QLCompare {
    GT(" > "),GE(" >= "),LT(" < "),LE(" <= "),EQ(" = "),NEQ("<>"),LIKE(" LIKE ");

    private String value;

    private QLCompare(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
