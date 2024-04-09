package com.wxj.SimpleMybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 10:09:46
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private Integer age;
}
