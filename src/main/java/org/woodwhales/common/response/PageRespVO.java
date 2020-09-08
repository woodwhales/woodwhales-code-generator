package org.woodwhales.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.8 21:50
 * @description:
 */
@Data
@NoArgsConstructor
public class PageRespVO<T> extends RespVO {

    private Integer count;

    public static <T> PageRespVO<T> success(List<T> data) {
        if(CollectionUtils.isNotEmpty(data)) {
            return success(data.size(), data);
        }
        return success(0, data);
    }

    public static <T> PageRespVO<T> success(Integer count, List<T> data) {
        PageRespVO<T> pageRespVO = new PageRespVO<>();
        pageRespVO.setCode(0);
        pageRespVO.setCount(count);
        if(CollectionUtils.isEmpty(data)) {
            data = Collections.emptyList();
        }
        pageRespVO.setData(data);
        pageRespVO.setMsg("success");
        return pageRespVO;
    }
}
