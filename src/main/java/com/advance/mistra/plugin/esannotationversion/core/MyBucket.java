package com.advance.mistra.plugin.esannotationversion.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBucket {

    private Object key;

    private Long docCount;
}
