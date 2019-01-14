import oracle.security.crypto.asn1.ASN1FormatException;
import oracle.security.pki.OracleSSOKeyStoreSpi;
import oracle.security.pki.OracleSecretStore;
import oracle.security.pki.OracleWallet;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Recover wallet by creating a new one with a known password.
 */
public class RecoverWallet {
    public static void main(String[] argv) {
        if (argv.length < 2) {
            System.out.println("Usage: java -jar recoverwallet.jar path/to/sourcewallet path/to/targetwallet [newPassword]");
        } else {
            try {
                OracleWallet targetOracleWallet = new OracleWallet();
                String sourcePath = argv[0];
                String targetPath = argv[1];
                char[] newPassword;
                if (argv.length == 3) {
                    newPassword = argv[2].toCharArray();
                } else {
                    newPassword = System.console().readPassword("Enter new wallet password:");
                }

                targetOracleWallet.create(newPassword);
                if (targetOracleWallet.exists(targetPath)) {
                    System.out.println("Wallet " + targetPath + " already exists");
                } else {
                    OracleSSOKeyStoreSpi secretKeyStore = new OracleSSOKeyStoreSpi();
                    try {
                        secretKeyStore.engineLoad(new FileInputStream(sourcePath + "/cwallet.sso"), null);
                    } catch (ASN1FormatException e) {
                        System.out.println("Unable to load wallet, this is an LSSO wallet");
                        System.out.println("Please make sure hostname and username match original wallet location");
                        System.out.println("Current hostname "+ InetAddress.getLocalHost().getHostName()+" current username "+System.getProperty("user.name"));
                        throw new IOException("Unable to load wallet, this is an LSSO wallet");
                    }
                    targetOracleWallet.saveAs(targetPath);
                    targetOracleWallet.saveSSO();

                    for (int i = 1; secretKeyStore.secretStoreContainsAlias("oracle.security.client.connect_string" + i); ++i) {
                        System.out.println(new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.connect_string" + i)) + " " + new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.username" + i)) + " " + new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.password" + i)));
                        OracleSecretStore secretStore = targetOracleWallet.getSecretStore();
                        secretStore.createCredential(secretKeyStore.secretStoreGetSecret("oracle.security.client.connect_string" + i), secretKeyStore.secretStoreGetSecret("oracle.security.client.username" + i), secretKeyStore.secretStoreGetSecret("oracle.security.client.password" + i));
                        targetOracleWallet.setSecretStore(secretStore);
                        targetOracleWallet.save();
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e + " " + e.getMessage());
            }
        }

    }
}
