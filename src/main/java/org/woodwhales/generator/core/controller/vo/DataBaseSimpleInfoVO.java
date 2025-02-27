package org.woodwhales.generator.core.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author woodwhales on 2021-12-14 17:12
 */
@Data
@NoArgsConstructor
public class DataBaseSimpleInfoVO {

    private String dbVersion;

    private List<String> schemas;

    public DataBaseSimpleInfoVO(String dbVersion, List<String> schemas) {
        this.dbVersion = dbVersion;
        this.schemas = schemas;
    }
}
