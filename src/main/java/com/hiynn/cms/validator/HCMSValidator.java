package com.hiynn.cms.validator;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.exception.ResultException;
import com.hiynn.cms.common.util.ValidatorUtils;
import com.hiynn.cms.dao.ValidatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 数据有效性校验 扩展与ValidatorUtils HCMS专用
 * <p>
 * 在ValidatorUtils 基础之上 加入了 异常返回 与持久层Mapper 调用做数据有效性校验
 *
 * @author 张朋
 * @date 2019/11/20 18:11
 */
@Component
public class HCMSValidator {

    private final ValidatorMapper validatorMapper;

    @Autowired
    public HCMSValidator(ValidatorMapper validatorMapper) {
        this.validatorMapper = validatorMapper;
    }

    /**
     * 参数空校验 如果为空 “” 则自动返回result
     *
     * @param param
     * @return void
     * @author 张朋
     * @date 2019/11/20 18:21
     */
    public static void isBlank(String... param) {
        Boolean blank = ValidatorUtils.isBlank(param);
        if (blank) {
            paramError();
        }
    }

    /**
     * 抛出自定义异常统-返回参数校验错误
     *
     * @author 张朋
     * @date 2019/11/20 18:20
     */
    public static void paramError() {
        throw new ResultException().
                setResult(HCMSConstants.ResultTemplate.PATAM_ERROR);
    }

    public void isEnum(Integer key, String type) {
        if (validatorMapper.isEnums(key, type) == 0) {
            paramError();
        }
    }
}
