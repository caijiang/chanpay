package me.jiangcai.chanpay.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.jiangcai.chanpay.support.ChanpayXmlMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author CJ
 */
public class Sign {

    private static final Log log = LogFactory.getLog(Sign.class);
    private static final String SIGN_ALGORITHM = "SHA1withRSA";
    private static final String BouncyCastleProvider_NAME = "BC";
    private final PrivateKey privateKey;
    private final X509Certificate publicKey;
    private final X509Certificate cjServerPublicKey;
    private final XmlMapper xmlMapper = new ChanpayXmlMapper();
    private final CMSSignedDataGenerator signGenerator;

    public Sign(InputStream keyStream, String keyPassword, InputStream certStream)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException
            , UnrecoverableKeyException, InvalidKeyException, NoSuchProviderException, CMSException, OperatorCreationException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(keyStream, keyPassword.toCharArray());

        Enumeration aliasesEnum = keyStore.aliases();
        aliasesEnum.hasMoreElements();
        String alias = (String) aliasesEnum.nextElement();

        privateKey = (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
//        log.debug("加载商户私钥：" + U.substringByByte(privateKey + "", 128));

        publicKey = (X509Certificate) keyStore.getCertificate(alias);
        log.debug("加载商户证书：" + publicKey.getSerialNumber().toString(16).toUpperCase());
        log.debug("加载商户证书：" + publicKey.getSubjectDN());

        //------------------------------------------------------------

        CertificateFactory factory = CertificateFactory.getInstance("X509");
        cjServerPublicKey = (X509Certificate) factory.generateCertificate(certStream);
        log.debug("加载CJ证书：" + cjServerPublicKey.getSerialNumber().toString(16).toUpperCase());
        log.debug("加载CJ证书：" + cjServerPublicKey.getSubjectDN());
        //------------------------------------------------------------

        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);

        Signature verifySignature = Signature.getInstance(SIGN_ALGORITHM);
        verifySignature.initVerify(cjServerPublicKey);

        signGenerator = buildCmsSignedDataGenerator();
    }

    public boolean verify(Signable signable) throws JsonProcessingException, SignatureException {
        String message = signable.getSignedMessage();
        signable.setSignedMessage("");
        String xml = xmlMapper.writeValueAsString(signable);
        try {
            return verify(xml.getBytes("UTF-8"), Base64.getDecoder().decode(message));
        } catch (UnsupportedEncodingException e) {
            throw new InternalError("NEVER", e);
        }
    }

    public boolean verify(byte[] plainText, byte[] signed) throws SignatureException {
        byte[] sha1Hash;
        try {
            sha1Hash = MessageDigest.getInstance("SHA1").digest(plainText);
        } catch (NoSuchAlgorithmException e) {
            throw new InternalError("NEVER", e);
        }
        Map<String, Object> hashes = new HashMap<>();
        hashes.put("1.3.14.3.2.26", sha1Hash);
        hashes.put("1.2.156.10197.1.410", sha1Hash);

        CMSSignedData sign;
        try {
            sign = new CMSSignedData(hashes, signed);
        } catch (CMSException e) {
            throw new SignatureException(e);
        }

        //假定只有一个证书签名者
        SignerInformation signer = (SignerInformation) sign.getSignerInfos().getSigners().iterator().next();
        SignerInformationVerifier signerInformationVerifier = null;
        try {
            signerInformationVerifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider_NAME).build(
                    this.cjServerPublicKey);
        } catch (OperatorCreationException e) {
            throw new InternalError("NEVER", e);
        }

        //使用本地证书验证签名
        log.debug("使用CJ服务器证书验证：" + cjServerPublicKey.getSerialNumber().toString(16).toUpperCase());

        try {
            return signer.verify(signerInformationVerifier);
        } catch (CMSException e) {
            throw new SignatureException(e);
        }
//        Signature verifySignature;
//        try {
//            verifySignature = Signature.getInstance(SIGN_ALGORITHM);
//            verifySignature.initVerify(cjServerPublicKey);
//        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
//            throw new InternalError("NEVER", ex);
//        }
//
//        verifySignature.update(plainText);
//        return verifySignature.verify(signed);
    }

    public void sign(Signable signable) throws JsonProcessingException, SignatureException {
        //转成XML格式
        signable.setSignedMessage("");
        String xml = xmlMapper.writeValueAsString(signable);
        byte[] signedData;
        try {
            signedData = sign(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new InternalError(e);
        }

        String result = Base64.getEncoder().encodeToString(signedData);
        signable.setSignedMessage(result);
    }

    @SuppressWarnings("WeakerAccess")
    public byte[] sign(byte[] plainText) throws SignatureException {
        return bcSign(plainText);
    }

    private byte[] bcSign(byte[] plainText) throws SignatureException {
        try {
            return signGenerator.generate(new CMSProcessableByteArray(plainText), true).getEncoded();
        } catch (IOException e) {
            throw new InternalError(e);
        } catch (CMSException e) {
            throw new SignatureException(e);
        }
    }

    private byte[] jdkSign(byte[] plainText) throws SignatureException {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new InternalError("NEVER", ex);
        }

        signature.update(plainText);
        byte[] signedData = signature.sign();

        //load X500Name
        X500Name xName = X500Name.asX500Name(publicKey.getSubjectX500Principal());
        //load serial number
        BigInteger serial = publicKey.getSerialNumber();
        //laod digest algorithm
        AlgorithmId digestAlgorithmId = new AlgorithmId(AlgorithmId.SHA_oid);
        //load signing algorithm
        AlgorithmId signAlgorithmId = new AlgorithmId(AlgorithmId.RSAEncryption_oid);

        //Create SignerInfo:
        SignerInfo sInfo = new SignerInfo(xName, serial, digestAlgorithmId, signAlgorithmId, signedData);
        //Create ContentInfo:
        ContentInfo cInfo = new ContentInfo(ContentInfo.DIGESTED_DATA_OID, new DerValue(DerValue.tag_OctetString, signedData));
        //Create PKCS7 Signed data
        PKCS7 p7 = new PKCS7(new AlgorithmId[]{digestAlgorithmId}, cInfo,
                new java.security.cert.X509Certificate[]{publicKey},
                new SignerInfo[]{sInfo});
        //Write PKCS7 to bYteArray
        ByteArrayOutputStream bOut = new DerOutputStream();
        try {
            p7.encodeSignedData(bOut);
        } catch (IOException e) {
            throw new InternalError(e);
        }

        return bOut.toByteArray();
    }

    private CMSSignedDataGenerator buildCmsSignedDataGenerator()
            throws OperatorCreationException, CertificateEncodingException, CMSException {
        BouncyCastleProvider bouncyCastlePd = new BouncyCastleProvider();
        Security.addProvider(bouncyCastlePd);
        log.debug("设置加密提供者：" + bouncyCastlePd.getName());

        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(SIGN_ALGORITHM).setProvider(bouncyCastlePd.getName());
        ContentSigner signer = jcaContentSignerBuilder.build(privateKey);

        DigestCalculatorProvider digestCalculatorProvider = new JcaDigestCalculatorProviderBuilder().setProvider(bouncyCastlePd.getName()).build();
        JcaSignerInfoGeneratorBuilder jcaBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);
        SignerInfoGenerator signGen = jcaBuilder.build(signer, publicKey);

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        generator.addSignerInfoGenerator(signGen);

        List<Certificate> certChainList = new LinkedList<>();
        certChainList.add(publicKey);
        Store certitude = new JcaCertStore(certChainList);
        generator.addCertificates(certitude);

        return generator;
    }//method

}
