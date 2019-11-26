package main.java.cn.lmc.collection.utils;

import org.apache.lucene.search.*;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Lucene8
 * 分组统计查询
 * @author limingcheng
 *
 */
public class GroupingUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupingUtil.class);

    public long iterGroupDocs(IndexSearcher indexSearcher, GroupDocs<BytesRef> groupDocs) throws IOException {
        TotalHits totalHits = groupDocs.totalHits;
        long rsvalue = totalHits.value;
        logger.info("\t#### 开始组内分页");
        logger.info("\t分组名称：" + groupDocs.groupValue.utf8ToString());
        ScoreDoc[] scoreDocs = groupDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
        	logger.info("\t\t组内记录：" + indexSearcher.doc(scoreDoc.doc));
        }
        logger.info("\t#### 结束组内分页");
        return rsvalue;
    }

    public TopGroups<BytesRef> group(IndexSearcher indexSearcher, Query query, String groupField,
                                     int groupDocsOffset, int groupDocsLimit, int groupOffset, int groupLimit) throws Exception {
        return group(indexSearcher, query, Sort.RELEVANCE, Sort.RELEVANCE, groupField, groupDocsOffset, groupDocsLimit, groupOffset, groupLimit);
    }

    /**
     * 分组统计查询
     * @param indexSearcher
     * @param query
     * @param groupSort
     * @param withinGroupSort
     * @param groupField
     * @param groupDocsOffset
     * @param groupDocsLimit
     * @param groupOffset
     * @param groupLimit
     * @return
     * @throws Exception
     */
    public TopGroups<BytesRef> group(IndexSearcher indexSearcher, Query query, Sort groupSort, Sort withinGroupSort, 
    		String groupField, int groupDocsOffset, int groupDocsLimit, int groupOffset, int groupLimit) 
    	throws Exception {
        //实例化GroupingSearch实例，传入分组域
        GroupingSearch groupingSearch = new GroupingSearch(groupField);
        //设置组间排序方式
        groupingSearch.setGroupSort(groupSort);
        //设置组内排序方式
        groupingSearch.setSortWithinGroup(withinGroupSort);
        //是否要填充每个返回的group和groups docs的排序field
//        groupingSearch.setFillSortFields(true);
        //设置用来缓存第二阶段搜索的最大内存，单位MB，第二个参数表示是否缓存评分
        groupingSearch.setCachingInMB(64.0, true);
        //是否计算符合查询条件的所有组
        groupingSearch.setAllGroups(true);
        groupingSearch.setAllGroupHeads(true);
        //设置一个分组内的上限
        groupingSearch.setGroupDocsLimit(groupDocsLimit);
        //设置一个分组内的偏移
        groupingSearch.setGroupDocsOffset(groupDocsOffset);
        TopGroups<BytesRef> result = groupingSearch.search(indexSearcher, query, groupOffset, groupLimit);
        return result;
    }
}
