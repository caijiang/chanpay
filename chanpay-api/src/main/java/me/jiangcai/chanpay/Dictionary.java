package me.jiangcai.chanpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import me.jiangcai.chanpay.model.support.AbstractModel;
import me.jiangcai.chanpay.support.ChanpayObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * 字典
 *
 * @author CJ
 */
public class Dictionary {

    private static final ObjectMapper objectMapper = new ChanpayObjectMapper();

    public static <T extends AbstractModel> T findByName(Class<T> targetClass, String name) {
        return findAll(targetClass).stream()
                .filter(model -> name.equals(model.getName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static <T extends AbstractModel> T findById(Class<T> targetClass, String id) {
        return findAll(targetClass).stream()
                .filter(model -> id.equals(model.getId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows(IOException.class)
    public static <T extends AbstractModel> Collection<T> findAll(Class<T> targetClass) {
        if (targetClass == Province.class) {
            return toCollection("/pay/provinceList.json", Province[].class);
        }
        if (targetClass == Bank.class) {
            return toCollection("/pay/bankList.json", Bank[].class);
        }
        if (targetClass == SubBranch.class) {
            return toCollection("/pay/subBranchList.json", SubBranch[].class);
        }
        throw new IllegalArgumentException("unknown of " + targetClass);
    }

    private static <T> Collection<T> toCollection(String path, Class valueType) throws IOException {
        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
            Object provinces = objectMapper.readValue(inputStream, valueType);
            return Arrays.asList((T[]) provinces);
        }
    }

}
