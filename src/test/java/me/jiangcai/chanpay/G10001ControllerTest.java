package me.jiangcai.chanpay;

import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.model.AccountProperty;
import me.jiangcai.chanpay.service.TransactionService;
import me.jiangcai.chanpay.test.TestTransaction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.SignatureException;

/**
 * 实名：卡号62开头的成功，否则退回
 * 协议：开户行号403558000044或710584000001成功，否则退回
 * 代收：卡号62开头的成功，否则退回
 * 代付：卡号62开头的成功，否则退回
 *
 * @author CJ
 */
public class G10001ControllerTest extends AbstractTestBase {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void doIt() throws IOException, SignatureException {

        TestTransaction testTransaction = new TestTransaction();
        testTransaction.setAccountProperty(AccountProperty.personal);
        testTransaction.setCorporateAccountNo("403558000044");//CORP_ACCT_NO必须非空;
        testTransaction.setBankGeneralName("中国工商银行");//BANK_GENERAL_NAME必须非空
        testTransaction.setBusinessCode("1");//BUSINESS_CODE必须非空;
        testTransaction.setProductCode("50010001");//[50010001, 60020001, 60020002, 70020001]
        testTransaction.setAccountNo("62x");//ACCOUNT_NO必须非空
        testTransaction.setAccountName("匿名");
        testTransaction.setAmount(1);
        testTransaction.setProtocolNo("1");

        Request request = new Request("5ee26ddb404a590", testTransaction);
//        System.out.println(xmlMapper.writeValueAsString(request));

        Response response = transactionService.execute(request);

        System.out.println(response);
//        G10001Bean bean = new G10001Bean();
//        testPackage(bean);

//        g10001Controller.sendMessage(bean);
//        assertThat(bean.getErrMsg())
//                .isEmpty();
    }

}