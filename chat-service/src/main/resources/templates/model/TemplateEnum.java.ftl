<#assign firstChar = entity?substring(0, 1)?lower_case>
<#assign restChars = entity?substring(1)>
<#assign entityObj = firstChar + restChars>
package ${packageEnum}.${entityObj};

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * ${table.comment!}枚举
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@AllArgsConstructor
public enum ${entity}Enum {
    USER("", 0);
    private final String desc;
    private final int value;

    /**
     * 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param value 键
     */
    public static ${entity}Enum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (${entity}Enum ${entityObj}Enum : ${entity}Enum.values()) {
                if (${entityObj}Enum.value == value) {
                    return ${entityObj}Enum;
                }
            }
        }
        return null;
    }
}
