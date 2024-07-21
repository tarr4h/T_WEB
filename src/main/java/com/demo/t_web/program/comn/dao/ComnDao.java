package com.demo.t_web.program.comn.dao;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.program.comn.model.ExcludeCategory;
import com.demo.t_web.program.comn.model.MapData;
import com.demo.t_web.program.comn.model.NaverMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    List<Tmap> selectMcidList(MapSearch c);

    List<Tmap> getRegion1(Map<String, Object> param);

    List<Tmap> getRegion2(Map<String, Object> param);

    List<MapData> getRegionMapData(Map<String, Object> param);

    int checkVisit(Map<String, Object> param);

    int insertVisitLog(Map<String, Object> param);

    int updateVisitLog(Map<String, Object> param);

    int checkVanish(Map<String, Object> param);

    int insertVanish(Map<String, Object> param);

    int updateVanish(Map<String, Object> param);

    MapData selectMapData(Map<String, Object> stringObjectMap);

    int insertLocationChange(Map<String, Object> cmap);

    int updateMapDataLocation(MapData mdt);

    List<ExcludeCategory> selectRelatedCategories(Map<String, Object> param);
}
