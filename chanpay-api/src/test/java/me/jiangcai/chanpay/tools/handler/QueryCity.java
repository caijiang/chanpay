package me.jiangcai.chanpay.tools.handler;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 */
public class QueryCity extends AbstractHandler<List<City>> {

    protected List<City> handleData(JsonNode data) {
        JsonNode city = data.get("city");
        ArrayList<City> cities = new ArrayList<>();
        for (JsonNode one : city) {
            cities.add(toCity(one));
        }

        return cities;
    }

    private City toCity(JsonNode node) {
        City city = new City();
        city.setId(node.get("cityId").asText());
        city.setName(node.get("cityName").asText());
        city.setShortName(node.get("cityShortName").asText());
        return city;
    }

}
