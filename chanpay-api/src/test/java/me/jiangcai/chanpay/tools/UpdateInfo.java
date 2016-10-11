package me.jiangcai.chanpay.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.City;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import me.jiangcai.chanpay.tools.handler.QueryBranch;
import me.jiangcai.chanpay.tools.handler.QueryCity;
import me.jiangcai.chanpay.tools.handler.QueryProvince;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 省份列表
 * 城市列表（注意城市所属的省份id）
 * 银行列表
 * 支行列表（注意支行所属的城市id和银行id）
 *
 * @author CJ
 */
public class UpdateInfo {

    private final BasicCookieStore store;
    private ObjectMapper objectMapper = new ObjectMapper();

    private UpdateInfo() {
        store = new BasicCookieStore();
        BasicClientCookie cookie1 = new BasicClientCookie("JSESSIONID", "09D9F9D7C1E59288B88405F24D636D5F");
        cookie1.setDomain("pay.chanpay.com");
        store.addCookie(cookie1);

        BasicClientCookie cookie2 = new BasicClientCookie("route", "c2d56f757e0f31df0257da0ffef7b4d1");
        cookie2.setDomain("pay.chanpay.com");
        store.addCookie(cookie2);

        BasicClientCookie cookie3 = new BasicClientCookie("com.vfsso.cas.token", "4a513fadcdc84f3ca8fc4d5caa2b67d7");
        cookie3.setDomain("pay.chanpay.com");
        store.addCookie(cookie3);


    }

    public static void main(String[] args) {
        new UpdateInfo().go();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows(IOException.class)
    private void go() {
        try (CloseableHttpClient client = newClient()) {
            HttpGet queryProvince = new HttpGet("https://pay.chanpay.com/sitemember/security/queryProv.htm");
            Object[] queryProvinceResults = client.execute(queryProvince, new QueryProvince());

            List<Province> provinceList = (List<Province>) queryProvinceResults[0];
            List<Bank> bankList = (List<Bank>) queryProvinceResults[1];
            provinceList.stream().forEach(System.out::println);
            bankList.stream().forEach(System.out::println);

            //
            for (Province province : provinceList) {
                HttpPost queryCity = new HttpPost("https://pay.chanpay.com/sitemember/security/queryCity.htm");
                queryCity.setEntity(EntityBuilder.create()
                        .setContentType(ContentType.create("application/x-www-form-urlencoded", "UTF-8"))
                        .setParameters(new BasicNameValuePair("provId", province.toQuery()))
                        .build()
                );
                List<City> cities = client.execute(queryCity, new QueryCity());
                province.setCityList(cities);
            }

            System.out.println(provinceList);


            List<SubBranch> subBranchList = new ArrayList<>();

            for (Bank bank : bankList) {
                for (Province province : provinceList) {
                    System.out.println(province);
                    for (City city : province.getCityList()) {

                        HttpPost queryBranch = new HttpPost("https://pay.chanpay.com/sitemember/security/queryBranch.htm");
                        queryBranch.setEntity(EntityBuilder.create()
                                .setContentType(ContentType.create("application/x-www-form-urlencoded", "UTF-8"))
                                .setParameters(
                                        new BasicNameValuePair("bankCode", bank.toQuery())
                                        , new BasicNameValuePair("cityId", city.toQuery())
                                )
                                .build()
                        );

                        List<SubBranch> branches = client.execute(queryBranch, new QueryBranch());
                        branches.forEach(subBranch -> {
                            subBranch.setBankId(bank.getId());
                            subBranch.setCityId(city.getId());
                        });

//                        System.out.println(branches);

                        subBranchList.addAll(branches);
                    }
                }
            }


//            String code = client.execute(queryBranch, new BasicResponseHandler());
//            System.out.println(code);


            store("provinceList.json", provinceList);
            store("bankList.json", bankList);
            store("subBranchList.json", subBranchList);
        }
    }

    @SneakyThrows(IOException.class)
    private void store(String fileName, Object value) {

        objectMapper.writeValue(new File(fileName), value);
    }

    private CloseableHttpClient newClient() {
        return HttpClientBuilder.create()
                .setDefaultCookieStore(store)
//                .addInterceptorFirst(this)
//                .setDefaultRequestConfig()
//                .setDefaultCookieSpecRegistry()
//                .setDefaultCookieStore()
//                .setConnectionManager()
                .build();
    }

}
