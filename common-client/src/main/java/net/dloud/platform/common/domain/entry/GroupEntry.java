package net.dloud.platform.common.domain.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.dloud.platform.common.domain.BaseEntry;

import java.io.Serializable;

/**
 * @author QuDasheng
 * @create 2018-09-11 11:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupEntry extends BaseEntry {
    /**
     * 系统id
     */
    private Integer systemId;

    /**
     * 系统名
     */
    private String systemName;

    /**
     * 运行模式
     */
    private String runMode;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 版本名
     */
    private String versionInfo;
}
