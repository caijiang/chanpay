package me.jiangcai.chanpay;

import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import org.junit.Test;

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

        Dictionary.findAll(SubBranch.class)
                .forEach(System.out::println);

        System.out.println(Dictionary.findByName(Province.class, "浙江省"));
        System.out.println(Dictionary.findByName(Bank.class, "中国工商银行"));
        System.out.println(Dictionary.findById(Province.class, "130"));
        System.out.println(Dictionary.findById(Bank.class, "CCB"));
    }

}