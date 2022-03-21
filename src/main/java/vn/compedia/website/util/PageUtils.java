package vn.compedia.website.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtils {
    public static Pageable buildPage(Integer pageNo,Integer pageSize) {
        return PageRequest.of(pageNo,pageSize);
    }
}
