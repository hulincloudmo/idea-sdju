package mapper;

import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

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
    public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
/***
 *
 * @Description: 视频喜欢数量增加或减少
 * @param videoId
 * @return void
 * @author hulincloud
 * @date 2019/6/6 21:16
 */
    public void addVideoLikeCount(String videoId);

    public void reduceVideoLikeCount(String videoId);

}