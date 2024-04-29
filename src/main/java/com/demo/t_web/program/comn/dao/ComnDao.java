package com.demo.t_web.program.comn.dao;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.program.comn.model.MapData;
import com.demo.t_web.program.comn.model.NaverMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.comn.dao.ComnDao
 *   - ComnDao.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : ComnDao
 * @description :
 * @date : 2023/10/19
 */
@Mapper
public interface ComnDao {

    int insertMapData(NaverMap map);

    int selectMapDataCount(NaverMap map);

    List<MapData> selectMapDataList(MapSearch c);
}
