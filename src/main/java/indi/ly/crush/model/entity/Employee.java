package indi.ly.crush.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import indi.ly.crush.excel.easy.annotation.ExcelEntity;
import indi.ly.crush.excel.easy.converter.LocalDateTimePropertyConverter;
import indi.ly.crush.excel.easy.converter.SexConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <h2><span style="color: red;">员工实体</span></h2>
 *
 * @since 1.0
 * @author 云上的云
 * @formatter:off
 */
@ExcelEntity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     员工唯一标识符.
	 * </p>
	 */
	@ExcelIgnore
	private Long employeeId;
	/**
	 * <p>
	 *     员工姓名.
	 * </p>
	 */
	@ExcelProperty(value = {"员工主题1", "姓名"}, index = 0)
	private String employeeName;
	/**
	 * <p>
	 *     员工性别.
	 * </p>
	 */
	@ExcelProperty(value = {"员工主题1", "性别"}, index = 1, converter = SexConverter.class)
	private Integer employeeGender;
	/**
	 * <p>
	 *     员工薪资.
	 * </p>
	 */
	@ExcelProperty(value = {"员工主题2", "薪资"}, index = 3)
	@NumberFormat(value = "#.##%")
	private BigDecimal employeeSalary;
	/**
	 * <p>
	 *     员工生日.
	 * </p>
	 */
	@ExcelProperty(value = {"员工主题3", "生日"}, index = 4, converter = LocalDateTimePropertyConverter.class)
	@DateTimeFormat(value = "yyyy年MM月dd日 HH时mm分ss秒")
	private LocalDateTime employeeBirthday;
	/**
	 * <p>
	 *     员工入职时间.
	 * </p>
	 */
	@ExcelProperty(value = {"员工主题2", "入职时间"}, index = 2, converter = LocalDateTimePropertyConverter.class)
	private LocalDateTime employeeEntryTime;
	/**
	 * <p>
	 *     这条员工记录的创建时间.
	 * </p>
	 */
	@ExcelIgnore
	private LocalDateTime gmtCreate;
	/**
	 * <p>
	 *     这条员工记录的修改时间.
	 * </p>
	 */
	@ExcelIgnore
	private LocalDateTime gmtModified;
}
