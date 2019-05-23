package mapper;

import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.utils.MyMapper;

import java.util.List;
/***
 *
 *

 *
 * @author hulincloud
 * @date 2019/5/21 20:13
 */
public interface VideosMapperCustom extends MyMapper<Videos> {
    /***
     *
     *
     * @param
     * @return java.util.List<com.imooc.pojo.vo.VideosVO>
     * @author hulincloud
     * @date 2019/5/23 10:33
     */
    public List<VideosVO> queryAllVideos();

}