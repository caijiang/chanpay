package me.jiangcai.chanpay;

import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.City;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import me.jiangcai.chanpay.model.support.AbstractModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author CJ
 */
public class DictionaryTest {
    @Test
    public void findAll() throws Exception {
        Dictionary.findAll(Province.class)
                .forEach(System.out::println);

        Dictionary.findAll(Bank.class)
                .forEach(System.out::println);

//        Dictionary.findAll(SubBranch.class)
//                .forEach(System.out::println);

        System.out.println(Dictionary.findByName(Province.class, "浙江省"));

        System.out.println(Dictionary.findById(Province.class, "130"));
        System.out.println(Dictionary.findById(Bank.class, "CCB"));

        final Bank icbc = Dictionary.findByName(Bank.class, "中国工商银行");
        System.out.println(icbc);
        Dictionary.findAll(SubBranch.class).stream()
                .filter(subBranch -> subBranch.getBankId().equals(icbc.getId()))
                .filter(subBranch -> subBranch.getName().contains("德胜支行"))
                .forEach(System.out::println);

        final Bank ccb = Dictionary.findByName(Bank.class, "中国建设银行");
        System.out.println(ccb);
        Dictionary.findAll(SubBranch.class).stream()
                .filter(subBranch -> subBranch.getBankId().equals(ccb.getId()))
                .filter(subBranch -> subBranch.getName().contains("兴安支行"))
                .forEach(System.out::println);

        System.out.println("!!!");
        // 102372200063
        // 102372200063
        // 孟村支行
        // 中国工商银行股份有限公司广西壮族自治区分行（不对外办理业务）
        final Comparator<AbstractModel> comparator = (o1, o2) -> o1.getId().length() - o2.getId().length();
        final Comparator<AbstractModel> nameComparator = (o1, o2) -> o1.getName().length() - o2.getName().length();

        Dictionary.findAll(SubBranch.class).stream()
                .max(nameComparator)
                .ifPresent(System.out::println);

        System.out.println("!!!");
        Dictionary.findAll(Province.class).stream().sorted(comparator)
                .findFirst().ifPresent(System.out::println);

        ArrayList<City> cityArrayList = new ArrayList<>();
        Dictionary.findAll(Province.class).stream()
                .map(Province::getCityList)
                .forEach(cityArrayList::addAll);

        // 10103
        // 日喀则地区(地区所在地)
        cityArrayList.stream()
                .max(nameComparator)
                .ifPresent(System.out::println);

        System.out.println(Dictionary.findAll(SubBranch.class).size());


//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setRoundingMode(RoundingMode.HALF_UP);//设置四舍五入
//        nf.setMinimumFractionDigits(2);//设置最小保留几位小数
//        nf.setMaximumFractionDigits(2);//设置最大保留几位小数

    }

}