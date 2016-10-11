package me.jiangcai.chanpay.tools.handler;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.Province;

import java.util.ArrayList;

/**
 * @author CJ
 */
public class QueryProvince extends AbstractHandler<Object[]> {

    protected Object[] handleData(JsonNode data) {
        JsonNode province = data.get("province");
        ArrayList<Province> provinceArrayList = new ArrayList<>();
        for (JsonNode one : province) {
            provinceArrayList.add(toProvince(one));
        }

        JsonNode bank = data.get("bank");
        ArrayList<Bank> bankArrayList = new ArrayList<>();
        for (JsonNode one : bank) {
            bankArrayList.add(toBank(one));
        }

        return new Object[]{
                provinceArrayList, bankArrayList
        };
    }

    private Bank toBank(JsonNode node) {
        Bank bank = new Bank();
        bank.setId(node.get("bankId").asText());
        bank.setName(node.get("bankName").asText());
        return bank;
    }

    private Province toProvince(JsonNode node) {
        Province province = new Province();
        province.setId(node.get("provId").asText());
        province.setName(node.get("provName").asText());
        province.setShortName(node.get("provShortName").asText());
        return province;
    }
}
