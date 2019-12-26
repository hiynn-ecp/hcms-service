import com.hiynn.cms.model.vo.DataSourceTableColumnVO;
import com.hiynn.cms.model.vo.DataSourceTableVO;
import com.hiynn.cms.service.SysDataSourceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Mr.Zhang
 * @email zhangpeng@hiynn.com
 * @date: 2019/10/24 17:46
 */
public class HCMSTest extends BaseTest {
    @Autowired
    private SysDataSourceService dataSourceService;

    @Test
    public void run() {

        List<DataSourceTableColumnVO> dataSourceTableColumnVOS = dataSourceService.listTableColumn("1", "123");

        DataSourceTableVO dataSourceTableVO = dataSourceService.selectTable("1", "123");

        System.out.println(Arrays.toString(dataSourceTableColumnVOS.toArray()));

        System.out.println(dataSourceTableVO);
    }
}
