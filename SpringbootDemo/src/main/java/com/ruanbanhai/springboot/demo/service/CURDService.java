package com.ruanbanhai.springboot.demo.service;//package com.ruanbanhai.springboot.demo.service;


import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.ruanbanhai.springboot.demo.pojo.User;

public interface CURDService {

    @Cached(cacheType = CacheType.REMOTE,expire = 10)
    @CacheRefresh(refresh = 5,stopRefreshAfterLastAccess = 20)
    void insertData(User User);
}
