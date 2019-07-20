package mapper;

import com.imooc.pojo.SearchRecords;
import com.imooc.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    /**
     * @descr:
     * @return
     */
    public List<String> getHotWords();
}