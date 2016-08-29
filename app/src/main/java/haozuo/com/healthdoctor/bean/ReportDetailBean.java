package haozuo.com.healthdoctor.bean;

import java.util.List;

/**
 * Created by xiongwei1 on 2016/8/22.
 */
public class ReportDetailBean {
    //基本信息
    public BaseInfoVM ReportInfoVM;

    //体检汇总对比
    public List<SummaryInfo> GeneralSummarysForApp;

    // 体检异常数组
    public List<CheckResult> AnomalyCheckResult;


    //科室检查及检查结果
    public List<DepartmentInfoVM> DepartmentCheck;

    //总检查师
    public String MasterDotor;

    public class BaseInfoVM {
        public String WorkNo;

        public String OrderId;

        public String GroupCheckUnitName;

        public String CustomerName;

        public String ReportDate;

        public String RegDate;

        public int Age;

        public int Sex;

        public String CheckUnitName;

        public int AbnormalCount;
    }

    public class DepartmentInfoVM {
        public String DepartmentName;
        public List<CheckItem> CheckItems;
    }

    public class SummaryInfo {
        public String SummaryName;

        public String SummaryDescription;
    }

    public class CheckItem {
        public String CheckItemName;
        public String CheckItemCode;
        public String DepartmentName;
        public String SalePrice;
        public int CheckStateID;
        public String CheckUserName;
        public List<CheckResult> CheckResults;
    }

    public class CheckResult {
        public String CheckIndexName;
        public String CheckIndexCode;
        public String ResultValue;
        public String AppendInfo;
        public boolean IsCalc;
        public String Unit;
        public String TextRef;
        public boolean IsAnomaly;
        public boolean IsAbandon;
        public int ResultTypeID;
        public int ResultFlagID;
        public String LowValueRef;
        public String HighValueRef;
    }
}
