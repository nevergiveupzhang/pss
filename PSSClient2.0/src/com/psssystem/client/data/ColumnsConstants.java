package com.psssystem.client.data;

public class ColumnsConstants {
	/* 经营历程显示单据的表格列信息 */
	public final static String[] ALARM_COLUMNS = new String[] { "ID", "商品ID",
			"商品名称", "创建日期", "状态" };
	public final static String[] GIFT_LOSS_OVERFLOW_COLUMNS = new String[] {
			"编号","商品ID", "商品名称", "数量", "创建日期", "状态" };
	public final static String[] PAYMENT_COLUMNS = new String[] { "ID", "客户ID","客户名",
			"操作员ID","操作员名", "账户名", "总额", "创建日期", "状态" };
	public final static String[] RECEIPTS_COLUMNS = new String[] { "ID",
			"客户ID","客户名", "操作员ID","操作员名", "总额", "创建日期", "状态" };
	public final static String[] PURCHASING_COLUMNS = new String[] { "ID",
			"客户ID","客户名", "操作员ID","操作员名", "业务员", "备注", "总额", "创建日期", "状态" };
	public final static String[] SALES_COLUMNS = new String[] { "ID", "客户ID",
			"客户名","操作员ID", "操作员名","业务员", "备注", "折算前", "折算后", "折让", "代金券", "创建日期", "状态" };

	/* 审批 */
	public final static String[] ALARM_COLUMNS_APPROVAL = new String[] { "ID",
			"商品ID", "商品名称", "创建日期", "状态", "选择" };
	public final static String[] GIFT_LOSS_OVERFLOW_COLUMNS_APPROVAL = new String[] {
		"编号","商品ID", "商品名称", "数量", "创建日期", "状态", "选择" };
	public final static String[] PAYMENT_COLUMNS_APPROVAL = new String[] {
			"ID", "客户ID", "操作员ID", "账户名", "总额", "创建日期", "状态", "选择" };
	public final static String[] RECEIPTS_COLUMNS_APPROVAL = new String[] {
			"ID", "客户ID", "操作员ID", "总额", "创建日期", "状态", "选择" };
	public final static String[] PURCHASING_COLUMNS_APPROVAL = new String[] {
			"ID", "客户ID", "操作员ID", "业务员", "备注", "总额", "创建日期", "状态", "选择" };
	public final static String[] SALES_COLUMNS_APPROVAL = new String[] { "ID",
			"客户ID", "操作员ID", "业务员", "备注", "折算前", "折算后", "折让", "代金券", "创建日期",
			"状态", "选择" };

	/* 显示数据库对应的数据 */
	public final static String[] STORAGE_COLUMNS = { "商品编号", "商品名称", "数量",
			"金额", "时间", "类型" };
	public final static String[] CUSTOMER_COLUMNS = { "姓名", "分类", "级别", "电话",
			"地址", "邮编", "电子邮箱", "默认业务员", "选择" };
	public final static String[] ACCOUNT_COLUMNS = { "名称", "余额" };
	public final static String[] INVENTORY_COLUMNS = { "行号", "批次", "批号",
			"商品名称", "商品型号", "库存数量" };
	public final static String[] SALESDETAIL_COLUMNS = { "时间", "商品名", "型号",
			"数量", "单价", "总额" };

	/* 创建单据的表格列信息 */
	public final static String[] ALARM_COLUMNS_CREATE = { "商品编号", "商品名称",
			"库存数量", "警戒线" };
	public final static String[] WARINGLINE_COLUMNS_CREATE = { "商品编号", "商品名称",
			"商品型号", "库存数量", "警戒线", "选择" };
	public final static String[] COMMODITY_COLUMNS_CREATE = { "商品编号", "商品名称",
			"商品型号", "所属分类", "库存数量", "进价", "零售价", "最近进价", "最近零售价", "警戒线" };
	public final static String[] GIFT_COLUMNS_CREATE = { "商品编号", "商品名称", "赠送数量" };
	public final static String[] LOSS_OVERFLOW_COLUMNS_CREATE = { "商品编号",
			"商品名称", "商品数量" };
	public final static String[] CUSTOMER_COLUMNS_CREATE = { "姓名", "分类", "级别",
			"电话", "地址", "邮编", "电子邮箱", "默认业务员" };
	public final static String[] PURCHASING_SALES_COLUMNS_CREATE = { "编号",
			"名称", "型号", "数量", "单价", "金额", "备注" };
	public final static String[] ACCOUNT_COLUMNS_CREATE = { "名称", "总额" };
	public final static String[] PAYMENT_COLUMNS_CREATE = { "条目名", "金额", "备注" };
	public final static String[] RECEIPTS_COLUMNS_CREATE = { "账户名称",
			"转账金额", "备注" };
	public final static String[] USER_COLUMNS = { "编号", "用户名", "密码", "用户身份",
			"选择" };

	/* 带有选择项的表格列信息 */
	public final static String[] ACCOUNT_COLUMNS_MODIFY = { "名称", "余额", "选择" };

	private ColumnsConstants() {

	}
}
