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

 public List<VideosVO> queryAllVideo();

 public void addVideoLikeCount(String videoId);

 public void reduceVideoLikeCount(String videoId);

}